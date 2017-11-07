package chess;

import java.util.Scanner;

public class Chess {
	
	static Game game;
	
	public static void main(String[] args) {
		
		String input;
		
		Scanner scan = new Scanner(System.in);
		game = new Game();
		
		game.setBoard();
		game.drawBoard();
		
		while(!game.gameOver) {

			//game.drawBoard();
			
			if(game.turn() == 'w') {
				System.out.print("White's turn: ");
			}
			else {
				System.out.print("Black's turn: ");
			}
			//ADD check for check and checkmate and stalemate
			
			if(checkKing(game.board)) {
				//game.setTestboard(game.turn());
				if(checkEscape()) {
					//look for checkmate here, else:
					System.out.print("Check!");
				}else if(stalemate()) {
					System.out.println("Stalemate");
					return;
				}else {
					System.out.print("Checkmate!");
					return;
				}
			}
			
			input = scan.nextLine();
			
			if(game.gameOver) {
				//System.out.println("Game Over!");
				//break;
				return;
			}if(input.equals("resign")) {
				if(game.turn() == 'w') {
					System.out.print("Black Wins!");
				}
				else {
					System.out.print("White Wins!");
				}
				return;
			}
			if(input.equals("draw")) {
				System.out.println("Draw");
				return;
			}
			if(!validateInput(input)) {
				System.out.println("Invalid Input! (expected: fileRank fileRank -options)");
				continue;
			}else {
				//check to see if the move choice is valid
				if(!checkValidMove(input,game.board, game.turn())) {
					System.out.println("Invalid Move, try again!");
					continue;
				}				
				
				// Update the board (execute move)
				game.updateBoard(input);
				game.drawBoard();
								
				game.nextTurn();
			}
		}
		scan.close();
	}
	
	/*
	 * @return	Returns true if stalemate, false otherwise
	 * */
	public static boolean stalemate() {
			// Iterating through the board
			for(int y = 7; y >= 0; y--) {
				for (int x = 0; x < 8; x++) {
					game.setTestboard(game.turn());
					// If the square has a piece on it
					if(game.testboard[x][y].occupied) {
						// If the turn is the same as the color of the piece
						if(game.testboard[x][y].occupyingPiece.color == game.turn()) {
							Piece piece = game.testboard[x][y].occupyingPiece;
							// Stalemate only possible when pieces are pawns or kings
							if(piece instanceof Queen || piece instanceof Rook || piece instanceof Knight || piece instanceof Bishop) {
								return false;
							}
							// If piece is pawn
							if(piece instanceof Pawn) {
								// white pawn
								if(piece.color == 'w') {
									return game.testboard[x][y + 1].occupied;
								}
								// black pawn
								else {
									return game.testboard[x][y - 1].occupied;
								}
							}
							// If piece is king
							else {
								
								char src_x = (char)('a' + x);
								char src_y = (char)('1' + y);
								for(int i = -1; i < 2; i++) {
									for(int j = -1; j < 2; j++) {
										char dest_x = (char)('a' + x + i);
										char dest_y = (char)('1' + y + j);
										String input = "" + src_x + src_y + " " + dest_x + dest_y;
										if(!validateInput(input)) {
											continue;
										}
										if(!checkValidMove(input,game.testboard, game.turn())) {
											continue;
										}
										
										game.updateTestBoard(input);
										
										if(!checkKing(game.testboard)) {
											return false;
										}
									}
								}
								
								return true;
								//input.concat(a + b + " " + king_x + king_y);
								
							}
						}
					}
				}	
		}
		return false;
	}
	
	public static boolean checkEscape() {
		
		game.setTestboard(game.turn());
		for(int y = 7; y >= 0; y--) {
			for (int x = 0; x < 8; x++) {
				if(game.testboard[x][y].occupied) {
					if(game.testboard[x][y].occupyingPiece.color == game.turn()) {
						//check if there is a valid path to previously found King
						char a = (char)('a' + x);
						char b = (char)('1' + y);
						//input.concat(a + b + " " + king_x + king_y);
						for(int q = 7; q >= 0; q--) {
							for (int r = 0; r < 8; r++) {
								game.setTestboard(game.turn());
								char u = (char)('a' + q);
								char v = (char)('1' + r);
								String input = "" + a + b + " " + u + v;
								if(checkValidMove(input,game.testboard, game.turn())) {
									game.updateTestBoard(input);
									if(!checkKing(game.testboard)) {
										return true;
									}
								}
							}
						}		
						
					}
				}
			}
		}
	return false;
}
	
	/*
	 * @param		Input string - the move that the player wants to make
	 * @return		Returns true if players King is in check, false elsewise
	 * */
	public static boolean checkKing(Square[][] board) {
		
		char king_x = 0;
		char king_y = 0;
		for(int y = 7; y >= 0; y--) {
			for (int x = 0; x < 8; x++) {
				if(board[x][y].occupied) {
					if(board[x][y].occupyingPiece.color == game.turn() && board[x][y].occupyingPiece instanceof King) {
						//found the current players king
						king_x = (char)('a' + x);
						king_y = (char)('1' + y);
						//System.out.print("found King " + king_x + king_y);
					}
				}
			}
		}
		for(int y = 7; y >= 0; y--) {
			for (int x = 0; x < 8; x++) {
				if(board[x][y].occupied) {
					if(board[x][y].occupyingPiece.color != game.turn()) {
						//check if there is a valid path to previously found King
						char a = (char)('a' + x);
						char b = (char)('1' + y);
						//input.concat(a + b + " " + king_x + king_y);
						String input = "" + a + b + " " + king_x + king_y;
						//System.out.println(input);
						char turn;
						if(game.turn() == 'w') {
							turn = 'b';
						}else {
							turn = 'w';
						}
						if(checkValidMove(input,board, turn)) {
							//System.out.print("check");
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	/*
	 * @param		Input string - the move that the player wants to make
	 * @return		Returns true if the input of the player is valid, false if invalid
	 * */
	public static boolean validateInput(String input) {
		
		if(input.trim().equalsIgnoreCase("resign")) {
			if(game.turn() == 'w') {
				System.out.println("Black wins!");
			}else {
				System.out.println("White wins!");
			}
			game.gameOver();
		}
		
		else if(input.trim().equalsIgnoreCase("draw")) {
			System.out.println("Draw");
			game.gameOver();
		}
		
		return input.matches("[abcdefgh][12345678] [abcdefgh][12345678]") 
				|| input.matches("[abcdefgh][12345678] [abcdefgh][12345678] [QNRB]")
				|| input.matches("[abcdefgh][12345678] [abcdefgh][12345678] draw[?]");
	}
	
	/*
	 * @param		input for the move performed
	 * @return		True if the move is valid for the piece, false otherwise
	 * */
	public static boolean checkValidMove(String input, Square[][] board, char turn) {
		
		//dissect input
		String[] args = input.split(" ");
		if(args.length > 3 || args.length < 2) {					
			return false;						// Should never get here after validation
		}
		else if (args.length == 3) {
		//	String special = args[2];
		}
		int src_xIndex = args[0].charAt(0) - 'a';
		int src_yIndex = Integer.parseInt(args[0].charAt(1) + "") - 1;
		//int dest_xIndex = args[1].charAt(0) - 'a';
		//int dest_yIndex = Integer.parseInt(args[1].charAt(1) + "") - 1;

		String src = args[0];
		String dest = args[1];

		if(src.equals(dest)) {
			return false;
		}
		//int xIndex = src.charAt(0) - 'a';
		//int yIndex = Integer.parseInt(src.charAt(1) + "") - 1;
		Piece piece = board[src_xIndex][src_yIndex].getPiece();
		
		if(piece == null) {
			return false;
		}
		if(piece.color != turn) {
			return false;
		}
		
		return piece.validMove(src, dest);
	}
	
}
