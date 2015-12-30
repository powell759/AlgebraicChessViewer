import java.io.PrintStream;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;

public class Interpreter {

	public enum Content {
		EMPTY, PAWN, KNIGHT, BISHOP, ROOK, QUEEN, KING
	}
	public enum Color {
		WHITE, BLACK, EMPTY
	}

	/*	private class PieceClass{
		private Content content;
		private int rank;
	        private int file;

		public PieceClass(Content t, int r, int f){
			this.content = t;
			this.rank = r;
			this.file = f;
		}

		public int getRank(){
			return this.rank;
		}

		public int getFile(){
			return this.file;
		}

		public void setRank(int r){
			this.rank = r;
		}

		public void setFile(int f){
			this.File = f;
		}
	}*/

	private static class Square{
		private Content content;
		private Color color;
		private int rank;
		private int file;

		public Square(int rank, int file, Content content, Color color){
			this.content = content;
			this.color = color;
			this.rank = rank;
			this.file = file;
		}

		public int getRank(){
			return this.rank;
		}

		public void setRank(int newRank){
			this.rank = newRank;
		}

		public int getFile(){
			return this.file;
		}

		public void setFile(int newFile){
			this.file = newFile;
		}

		public Content getContent(){
			return this.content;
		}

		public void setContent(Content newContent){
			this.content = newContent;
		}

		public Color getColor(){
			return this.color;
		}

		public void setColor(Color newColor){
			this.color = newColor;
		}
	}


	private static class ChessBoard{
		private Square[][] board = new Square[8][8];

		public ChessBoard(){
			//Setting standard chess board
			/*
			 * Board Indexing
			 *
			 *   _0__1__2__3__4__5__6__7
			 * 0| a1 a2 a3 a4 a5 a6 a7 a8
			 * 1| 
			 * 2| 
			 * 3| 
			 * 4| 
			 * 5|
			 * 6|
			 * 7|
			 * 8|
			 */

			//Setting White Peices
			board[0][0] = new Square(0,0,Content.ROOK, Color.WHITE);
			board[1][0] = new Square(0,1,Content.KNIGHT, Color.WHITE);
			board[2][0] = new Square(0,2,Content.BISHOP, Color.WHITE);
			board[3][0] = new Square(0,3,Content.QUEEN, Color.WHITE);
			board[4][0] = new Square(0,4,Content.KING, Color.WHITE);
			board[5][0] = new Square(0,5,Content.BISHOP, Color.WHITE);
			board[6][0] = new Square(0,6,Content.KNIGHT, Color.WHITE);
			board[7][0] = new Square(0,7,Content.ROOK, Color.WHITE);

			//Setting Black Peices
			board[0][7] = new Square(7,0,Content.ROOK, Color.BLACK);
			board[1][7] = new Square(7,1,Content.KNIGHT, Color.BLACK);
			board[2][7] = new Square(7,2,Content.BISHOP, Color.BLACK);
			board[3][7] = new Square(7,3,Content.QUEEN, Color.BLACK);
			board[4][7] = new Square(7,4,Content.KING, Color.BLACK);
			board[5][7] = new Square(7,5,Content.BISHOP, Color.BLACK);
			board[6][7] = new Square(7,6,Content.KNIGHT, Color.BLACK);
			board[7][7] = new Square(7,7,Content.ROOK, Color.BLACK);

			//Setting All Pawns
			for(int i = 0; i < 8; i++){
				board[i][1] = new Square(1,i,Content.PAWN, Color.WHITE);
				board[i][6] = new Square(6,i,Content.PAWN, Color.BLACK);
			}

			//Setting Empty Spaces
			for(int i = 0; i < 8; i++){
				for (int j = 2; j < 6; j++){
					board[i][j] = new Square(i,j,Content.EMPTY, Color.EMPTY);
				}
			}
		}

		public Square getSquare(int i, int j){
			return this.board[i][j];
		}
	}

	public static void displayBoard(ChessBoard board){
		for (int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				Square thisSquare = board.getSquare(j,7-i);
				switch (thisSquare.getContent()){
				case BISHOP:
					if(thisSquare.getColor() == Color.WHITE){
						System.out.print("\u2657");
					} else {
						System.out.print("\u265d");
					}
					break;
				case EMPTY:
					System.out.print("X");
					break;
				case KING:
					if(thisSquare.getColor() == Color.WHITE){
						System.out.print("\u2654");
					} else {
						System.out.print("\u265b");
					}
					break;
				case KNIGHT:
					if(thisSquare.getColor() == Color.WHITE){
						System.out.print("\u2658");
					} else {
						System.out.print("\u265e");
					}
					break;
				case PAWN:
					if(thisSquare.getColor() == Color.WHITE){
						System.out.print("\u2659");
					} else {
						System.out.print("\u265f");
					}
					break;
				case QUEEN:
					if(thisSquare.getColor() == Color.WHITE){
						System.out.print("\u2655");
					} else {
						System.out.print("\u265b");
					}
					break;
				case ROOK:
					if(thisSquare.getColor() == Color.WHITE){
						System.out.print("\u2656");
					} else {
						System.out.print("\u265c");
					}
					break;
				default:
					break;

				}
				System.out.print(" ");
			}
			System.out.println("");
		}
	}

	public static void updateBoard(String move, ChessBoard board){
		int rank = 0;
		int file = 0;
		int newRank = 0;
		int newFile = 0;

		switch (move.length()){
		case 5:
			rank = move.charAt(0) - 97;
			file = move.charAt(1) - 49;
			newRank = move.charAt(3) - 97;
			newFile = move.charAt(4) - 49;
			break;
		default:
			rank = move.charAt(1) - 97;
			file = move.charAt(2) - 49;
			newRank = move.charAt(4) - 97;
			newFile = move.charAt(5) - 49;
			break;
		}

		Square currentSquare = board.getSquare(rank, file);
		Square nextSquare = board.getSquare(newRank, newFile);
		Content content = currentSquare.getContent();
		Color color = currentSquare.getColor();

		//Emptying current square
		currentSquare.setContent(Content.EMPTY);
		currentSquare.setColor(Color.EMPTY);

		//Moving piece into next square
		nextSquare.setContent(content);
		nextSquare.setColor(color);


	}

	public static String nextMove(int pos, String moves){
		String move = "";
		if (moves.charAt(pos) != ' '){
			while (moves.charAt(pos) != ' '){
				move += moves.charAt(pos);
			}
		}
		System.out.println(move);
		return move;
	}

	public static void main(String[] args){
		ChessBoard board = new ChessBoard();
		Scanner in = new Scanner(System.in);

		System.out.print("Enter a valid filename: ");
		Scanner fileIn = new Scanner(in.nextLine());
		Queue<String> moveQ = new ArrayDeque<>();

//		while(fileIn.hasNext()){
//			String turn = fileIn.nextLine();
//			int pos = 0;
//			while (pos < turn.length()){
//				if(!nextMove(pos, turn).equals(" ")){
//					System.out.println("one" + nextMove(pos, turn));
//					moveQ.add(nextMove(pos, turn));
//				}
//				pos++;
//			}
//			fileIn.close();
//		}
		moveQ.add("f2-f3");
		moveQ.add("e7-e5");
		moveQ.add("g2-g4");
		moveQ.add("e8-h4");
		displayBoard(board);
		while(!moveQ.isEmpty()){
			updateBoard(moveQ.remove(), board);
			displayBoard(board);
		}

		in.close();
	}
}
