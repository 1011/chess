package chess;

import java.util.ArrayList;
import java.util.Scanner;

public class Game {
	
	static Square[][] board;
	static Square[][] testboard;
	boolean gameOver;
	private char turn = 'w';					//assuming this is either 'b' or 'w'
	
	/*
	 * board[x][y] is indexed as such:
	 * 
		7|	bR bN bB bQ bK bB bN bR 8
		6|	bp bp bp bp ## bp bp bp 7
		5|	   ##    ##    ##    ## 6
		4|	##    ##    bp    ##    5
		3|	   ##    ## wp ##    ## 4
		2|	##    ##    ##    ##    3
		1|	wp wp wp wp    wp wp wp 2
		0|	wR wN wB wQ wK wB wN wR 1
		 |	 a  b  c  d  e  f  g  h
		y|____________________________
		 x   0  1  2  3  4  5  6  7	
	 *
	 */

	public Game() {
		board = new Square[8][8];
		testboard = new Square[8][8];
		gameOver = false;
		turn = 'w';
	}
	
	public void setTestboard(char turn) {
		
		for(int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				if(board[x][y].occupied) {
					testboard[x][y] = new Square(board[x][y].occupyingPiece, board[x][y].s_color);
				}else {
					testboard[x][y] = new Square(board[x][y].s_color);
				}
			}
		}		
	}
	
	/*
	 * @param		None
	 * @returns		Nothing. Sets up initial chess board with pieces on appropriate spaces as a 2d matrix
	 * */
	public void setBoard() {
		for(int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				
				//determine square color
				
				char s_color;
				if((x + y) % 2 == 0) {			//square is black
					s_color = 'b';
				}else {							//square is white
					s_color = 'w';
				}
				
				//determine which piece, if any, belongs
				//create pieces and squares as necessary
				
				if(y == 1) {						//make white pawns for each x
					Piece pawn = new Pawn('w');
					board[x][y] = new Square(pawn, s_color);
				}else if(y == 0) {					//make whites elite pieces
					if(x == 0 || x == 7) {			//make rooks
						Piece rook = new Rook('w');
						board[x][y] = new Square(rook, s_color);	
					}else if(x == 1 || x == 6) {	//make knights
						Piece knight = new Knight('w');
						board[x][y] = new Square(knight, s_color);
					}else if(x == 2 || x == 5) {	//make bishops
						Piece bishop = new Bishop('w');
						board[x][y] = new Square(bishop, s_color);
					}else if(x == 3) {				//make queen
						Piece queen = new Queen('w');
						board[x][y] = new Square(queen, s_color);
					}else if(x == 4) {				//make king
						Piece king = new King('w');
						board[x][y] = new Square(king, s_color);
					}
				}else if(y == 6) {					//make black pawns for each x
					Piece pawn = new Pawn('b');
					board[x][y] = new Square(pawn, s_color);
				}else if(y == 7) {					//make blacks elite pieces
					if(x == 0 || x == 7) {			//make rooks
						Piece rook = new Rook('b');
						board[x][y] = new Square(rook, s_color);	
					}else if(x == 1 || x == 6) {	//make knights
						Piece knight = new Knight('b');
						board[x][y] = new Square(knight, s_color);
					}else if(x == 2 || x == 5) {	//make bishops
						Piece bishop = new Bishop('b');
						board[x][y] = new Square(bishop, s_color);
					}else if(x == 3) {				//make queen
						Piece queen = new Queen('b');
						board[x][y] = new Square(queen, s_color);
					}else if(x == 4) {				//make king
						Piece king = new King('b');
						board[x][y] = new Square(king, s_color);
					}
				}else {								//no mans zone, no pieces here just make squares with colors
					board[x][y] = new Square(s_color);
				}
			}
		}
	}
	
	/*
	 * @param	Input string for source and destination
	 * @return	Nothing. Updates the testboard after the move is validated
	 * */
	public void updateTestBoard(String input) {
		//dissect input
		String[] args = input.split(" ");
		//String src = args[0];
		//String dest = args[1];
		/*if (args.length == 3) {
			String special = args[2];
		}*/
		int src_xIndex = args[0].charAt(0) - 'a';
		int src_yIndex = Integer.parseInt(args[0].charAt(1) + "") - 1;
		int dest_xIndex = args[1].charAt(0) - 'a';
		int dest_yIndex = Integer.parseInt(args[1].charAt(1) + "") - 1;

		Square src_square = testboard[src_xIndex][src_yIndex];
		Square dest_square = testboard[dest_xIndex][dest_yIndex];
		
		// Special case for castling
		if(src_square.occupyingPiece instanceof King) {
			if(((King)src_square.occupyingPiece).castling) {
				Square castle_start = null;
				Square castle_end = null;
				if(dest_xIndex > src_xIndex) {				//castle right
					castle_start = testboard[7][src_yIndex];
					castle_end = testboard[5][src_yIndex];
				}else{										//castle left
					castle_start = testboard[0][src_yIndex];
					castle_end = testboard[3][src_yIndex];
				}
				castle_end.occupied = true;					//move the rook piece to the kings side
				castle_end.occupyingPiece = castle_start.occupyingPiece;
				castle_start.occupyingPiece.hasMoved = true;
				castle_start.occupyingPiece = null;
				castle_start.occupied = false;
				((King)src_square.occupyingPiece).castling = false;
			}
		}
		
		// Special case for promotion
		if(src_square.occupyingPiece instanceof Pawn) {
			if(((Pawn) src_square.occupyingPiece).promoted) {
				//boolean temp = true;
				String special = "";
				if(args.length == 3) {
					special = args[2];
				}
				
				switch(special) {
				
				case "R":
					Piece rook = new Rook(turn());
					dest_square.occupyingPiece = rook;
					dest_square.occupied = true;
					src_square.occupyingPiece = null;
					src_square.occupied = false;
					return;
				case "B":
					Piece bishop = new Bishop(turn());
					dest_square.occupyingPiece = bishop;
					dest_square.occupied = true;
					src_square.occupyingPiece = null;
					src_square.occupied = false;
					return;
				case "N":
					Piece knight = new Knight(turn());
					dest_square.occupyingPiece = knight;
					dest_square.occupied = true;
					src_square.occupyingPiece = null;
					src_square.occupied = false;
					return;
				case "Q":
				default:
					Piece queen = new Queen(turn());
					dest_square.occupyingPiece = queen;
					dest_square.occupied = true;
					src_square.occupyingPiece = null;
					src_square.occupied = false;
					return;
				}
				
			}
			
		}
		
		// swapping squares
		src_square.occupyingPiece.hasMoved = true;
		dest_square.occupied = true;
		dest_square.occupyingPiece = src_square.occupyingPiece;
		src_square.occupyingPiece = null;
		src_square.occupied = false;
	}
	
	/*
	 * @param	Input string for source and destination
	 * @return	Nothing. Updates the board after the move is validated
	 * */
	public void updateBoard(String input) {
		//dissect input
		String[] args = input.split(" ");
		//String src = args[0];
		//String dest = args[1];
		/*if (args.length == 3) {
			String special = args[2];
		}*/
		int src_xIndex = args[0].charAt(0) - 'a';
		int src_yIndex = Integer.parseInt(args[0].charAt(1) + "") - 1;
		int dest_xIndex = args[1].charAt(0) - 'a';
		int dest_yIndex = Integer.parseInt(args[1].charAt(1) + "") - 1;

		Square src_square = board[src_xIndex][src_yIndex];
		Square dest_square = board[dest_xIndex][dest_yIndex];
		
		// Special case for castling
		if(src_square.occupyingPiece instanceof King) {
			if(((King)src_square.occupyingPiece).castling) {
				Square castle_start = null;
				Square castle_end = null;
				if(dest_xIndex > src_xIndex) {				//castle right
					castle_start = board[7][src_yIndex];
					castle_end = board[5][src_yIndex];
				}else{										//castle left
					castle_start = board[0][src_yIndex];
					castle_end = board[3][src_yIndex];
				}
				castle_end.occupied = true;					//move the rook piece to the kings side
				castle_end.occupyingPiece = castle_start.occupyingPiece;
				castle_start.occupyingPiece.hasMoved = true;
				castle_start.occupyingPiece = null;
				castle_start.occupied = false;
				((King)src_square.occupyingPiece).castling = false;
			}
		}
		
		// Special case for promotion
		if(src_square.occupyingPiece instanceof Pawn) {
			if(((Pawn) src_square.occupyingPiece).promoted) {
				//boolean temp = true;
				String special = "";
				if(args.length == 3) {
					special = args[2];
				}
				
				switch(special) {
				
				case "R":
					Piece rook = new Rook(turn());
					dest_square.occupyingPiece = rook;
					dest_square.occupied = true;
					src_square.occupyingPiece = null;
					src_square.occupied = false;
					return;
				case "B":
					Piece bishop = new Bishop(turn());
					dest_square.occupyingPiece = bishop;
					dest_square.occupied = true;
					src_square.occupyingPiece = null;
					src_square.occupied = false;
					return;
				case "N":
					Piece knight = new Knight(turn());
					dest_square.occupyingPiece = knight;
					dest_square.occupied = true;
					src_square.occupyingPiece = null;
					src_square.occupied = false;
					return;
				case "Q":
				default:
					Piece queen = new Queen(turn());
					dest_square.occupyingPiece = queen;
					dest_square.occupied = true;
					src_square.occupyingPiece = null;
					src_square.occupied = false;
					return;
				}
				
			}
			
		}
		
		// swapping squares
		src_square.occupyingPiece.hasMoved = true;
		dest_square.occupied = true;
		dest_square.occupyingPiece = src_square.occupyingPiece;
		src_square.occupyingPiece = null;
		src_square.occupied = false;
	}
	
	/*
	 * @param		None
	 * @returns		Draws initial chess board and the board after every turn
	 * */
	public void drawBoard() {
		//char[] letters = {'a','b','c','d','e','f','g','h'};
		//String letters = " a  b  c  d  e  f  g  h";
		for(int y = 7; y >= 0; y--) {
			for (int x = 0; x < 8; x++) {
				if(board[x][y].occupied) {
					Piece tmp = board[x][y].getPiece();
					System.out.print(tmp.name + " ");
				}else {
					char color = board[x][y].getColor();
					if(color == 'b') {
						System.out.print("## ");
					}else {
						System.out.print("   ");
					}
				}
			}
			int j = y + 1;
			System.out.println(j);
		}
		System.out.println(" a  b  c  d  e  f  g  h");
	}
	
	/*
	 * If the game is over, sets the value of the global boolean to true to stop the execution
	 * */
	public void gameOver() {
		gameOver = true;
	}
	
	/*
	 * Shuffle turns 
	 * */
	public void nextTurn() {
		if(turn == 'w') {
			turn = 'b';
		}else {
			turn = 'w';
		}
		//reset all enpassant values of new turns color
		for(int y = 7; y >= 0; y--) {
			for (int x = 0; x < 8; x++) {
				if(board[x][y].occupied) {
					if(board[x][y].occupyingPiece.color == turn && board[x][y].occupyingPiece instanceof Pawn) {
						((Pawn)board[x][y].occupyingPiece).justJumped = false;
					}
				}
			}
		}
	}
	
	public char turn() {
		return turn;
	}
}
