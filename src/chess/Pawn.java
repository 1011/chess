package chess;

import java.util.ArrayList;

public class Pawn extends Piece{
	
	boolean promoted = false;
	boolean justJumped = false;
	boolean enpassant = false;
	public Pawn(char color) {
		super(color, 'p');
	}
	
	/*
	 * @param	src	->	Current square of the pawn
	 * @param	dest ->	Requested square to move the pawn to
	 * @return	True if the pawn can move to the requested square, false otherwise
	 * */
	 public boolean validMove(String src, String dest){
		int src_xIndex = src.charAt(0) - 'a';
		int src_yIndex = Integer.parseInt(src.charAt(1) + "") - 1;
		int dest_xIndex = dest.charAt(0) - 'a';
		int dest_yIndex = Integer.parseInt(dest.charAt(1) + "") - 1;
		 
		//ensure travel in correct direction
		if(Game.board[src_xIndex][src_yIndex].occupyingPiece.color == 'w' && dest_yIndex < src_yIndex) {
			return false;
		}else if (Game.board[src_xIndex][src_yIndex].occupyingPiece.color == 'b' && dest_yIndex > src_yIndex) {
			return false;
		}
		
		if(src_xIndex == dest_xIndex) {
			
			//pawn must be attempting to advance 1 or two spaces
			if(Math.abs(src_yIndex - dest_yIndex) == 1){
				if(Game.board[dest_xIndex][dest_yIndex].occupied) {
					return false;
				}
				
				((Pawn)Game.board[src_xIndex][src_yIndex].occupyingPiece).justJumped = false;
			}else if(Math.abs(src_yIndex - dest_yIndex) == 2) {
				if(src_yIndex == 1 && !Game.board[src_xIndex][src_yIndex].occupyingPiece.hasMoved) {
					//white pawn attempting to advance 2
					if(Game.board[dest_xIndex][dest_yIndex].occupied || Game.board[dest_xIndex][dest_yIndex - 1].occupied) {
						return false;
					}
				}else if(src_yIndex == 6 && !Game.board[src_xIndex][src_yIndex].occupyingPiece.hasMoved) {
					//black pawn attempting to decrease 2
					if(Game.board[dest_xIndex][dest_yIndex].occupied || Game.board[dest_xIndex][dest_yIndex + 1].occupied) {
						return false;
					}
				}
				((Pawn)Game.board[src_xIndex][src_yIndex].occupyingPiece).justJumped = true;
			}else {
				return false;
			}
		}else {
			//pawn is attempting to capture on a diagonal or enpassant
			if(Math.abs(dest_xIndex - src_xIndex) == 1 && Math.abs(dest_yIndex - src_yIndex) == 1) {
				//System.out.println("were");
				if(Game.board[src_xIndex][src_yIndex].occupyingPiece.color == 'w' && !Game.board[dest_xIndex][dest_yIndex].occupied && Game.board[dest_xIndex][dest_yIndex-1].occupied ) {
					if(Game.board[dest_xIndex][dest_yIndex-1].occupyingPiece.color == 'b' && ((Pawn)Game.board[dest_xIndex][dest_yIndex-1].occupyingPiece).justJumped) {
						//System.out.println("there");
						Game.board[dest_xIndex][dest_yIndex-1].occupyingPiece = null;
						Game.board[dest_xIndex][dest_yIndex-1].occupied = false;
						((Pawn)Game.board[src_xIndex][src_yIndex].occupyingPiece).enpassant = true;
						return true;
					}
				}else if(Game.board[src_xIndex][src_yIndex].occupyingPiece.color == 'b' && !Game.board[dest_xIndex][dest_yIndex].occupied && Game.board[dest_xIndex][dest_yIndex+1].occupied ) {
					if (Game.board[dest_xIndex][dest_yIndex+1].occupyingPiece.color == 'w' && ((Pawn)Game.board[dest_xIndex][dest_yIndex+1].occupyingPiece).justJumped) {
						Game.board[dest_xIndex][dest_yIndex+1].occupyingPiece = null;
						Game.board[dest_xIndex][dest_yIndex+1].occupied = false;
						((Pawn)Game.board[src_xIndex][src_yIndex].occupyingPiece).enpassant = true;
						return true;
					}
				}
				if(!Game.board[dest_xIndex][dest_yIndex].occupied || Game.board[dest_xIndex][dest_yIndex].occupyingPiece.color == Game.board[src_xIndex][src_yIndex].occupyingPiece.color) {
					return false;
				}
			}else {
				return false;
			}
		}
		//check to see if promoting
		if(Game.board[src_xIndex][src_yIndex].occupyingPiece.color == 'w' && dest_yIndex == 7 || Game.board[src_xIndex][src_yIndex].occupyingPiece.color == 'b' && dest_yIndex == 0) {
			((Pawn)Game.board[src_xIndex][src_yIndex].occupyingPiece).promoted = true;
		}
		return true;
	 }
	
	/*
	 * @param	src	->	Current square of the pawn
	 * @param	dest ->	Requested square to move the pawn to
	 * @return	True if the pawn can move to the requested square, false otherwise
	 * */
	public boolean validMove_2(String src, String dest) {
		int src_xIndex = src.charAt(0) - 'a';
		int src_yIndex = Integer.parseInt(src.charAt(1) + "") - 1;
		int dest_xIndex = dest.charAt(0) - 'a';
		int dest_yIndex = Integer.parseInt(dest.charAt(1) + "") - 1;
		
		// Pawn trying to occupy space on the same y axis when a piece is already present
		if(Game.board[dest_xIndex][dest_yIndex].occupied && dest_xIndex == src_xIndex) {
			return false;
		}
		
		// Data structure holing all possible legal moves for the pawn 
		ArrayList<String> possibleMoves = new ArrayList<String>();
		
		if(checkPath(src, dest)) {
			// White pawn moves
			if(this.color == 'w') {
				String temp = src.charAt(0) + "";
				
				// Move 1 space
				possibleMoves.add(temp + Integer.toString(src_yIndex + 1));
				
				// Can move two spaces
				if(src_yIndex == 1) {
					possibleMoves.add(temp + Integer.toString(src_yIndex + 2));
				}
				
				
				// left diagonal
				if(src_xIndex > 0) {
					char leftDiagonal = (char) (src.charAt(0) - 1);
					temp = leftDiagonal + "";
					if(Game.board[src_xIndex - 1][src_yIndex + 1].occupied && Game.board[src_xIndex - 1][src_yIndex + 1].occupyingPiece.color == 'b') {
						possibleMoves.add(temp + Integer.toString(src_yIndex + 1));
					}
				}
				
				// right diagonal
				if(src_xIndex < 7) {
					char rightDiagonal = (char) (src.charAt(0) + 1);
					temp = rightDiagonal + "";
					if(Game.board[src_xIndex + 1][src_yIndex + 1].occupied && Game.board[src_xIndex + 1][src_yIndex + 1].occupyingPiece.color == 'b') {
						System.out.println("check");
						possibleMoves.add(temp + Integer.toString(src_yIndex + 1));
					}
				}
			}
			
			// Black pawn moves
			else {
				String temp = src.charAt(0) + "";
				// Move 1 space
				possibleMoves.add(temp+Integer.toString(src_yIndex-1));
				
				// Can move two spaces
				if(src_yIndex == 6) {
					possibleMoves.add(temp+Integer.toString(src_yIndex - 2));
				}
				
				// left diagonal
				if(src_xIndex > 0) {
					char leftDiagonal = (char) (src.charAt(0) - 1);
					temp = leftDiagonal + "";
					if(Game.board[src_xIndex - 1][src_yIndex - 1].occupied && Game.board[src_xIndex - 1][src_yIndex - 1].occupyingPiece.color == 'w') {
						possibleMoves.add(temp + Integer.toString(src_yIndex-1));
					}
				}
				
				// right diagonal
				if(src_xIndex < 7) {
					char rightDiagonal = (char) (src.charAt(0) + 1);
					temp = rightDiagonal + "";
					if(Game.board[src_xIndex + 1][src_yIndex - 1].occupied && Game.board[src_xIndex + 1][src_yIndex - 1].occupyingPiece.color == 'w') {
						possibleMoves.add(temp + Integer.toString(src_yIndex-1));
					}
				}
			}
		}
		else {
			return false;
		}
		
		if(possibleMoves.contains(dest)) {
			if(dest_yIndex == 7 || dest_yIndex == 0) {
				this.promoted = true;
			}
			return true;
		}
		
		return false;
	}
	
	/*
	 * @param	src	->	Current square of the pawn
	 * @param	dest ->	Requested square to move the pawn to
	 * @return	True if the path from src to dest is unobstructed, false otherwise
	 * */
	public boolean checkPath(String src, String dest) {
		int src_xIndex = src.charAt(0) - 'a';
		int src_yIndex = Integer.parseInt(src.charAt(1) + "") - 1;
		int dest_xIndex = dest.charAt(0) - 'a';
		int dest_yIndex = Integer.parseInt(dest.charAt(1) + "") - 1;
		
		if(src_xIndex == dest_xIndex) {
			
			// white pawn
			if(this.color == 'w') {
				if(src_yIndex == 6 && Game.board[src_xIndex][src_yIndex + 1].occupied) {
					return false;
				}
				if(src_yIndex != 6 && src_yIndex != 7) {
					if(Game.board[src_xIndex][src_yIndex + 1].occupied || Game.board[src_xIndex][src_yIndex + 2].occupied) {
						return false;
					}
				}	
			}
			
			//black pawn
			else {
				if(src_yIndex == 1 && Game.board[src_xIndex][src_yIndex - 1].occupied) {
					return false;
				}
				if(src_yIndex != 0 && src_yIndex != 1) {
					if(Game.board[src_xIndex][src_yIndex - 1].occupied || Game.board[src_xIndex][src_yIndex - 2].occupied) {
						return false;
					}
				}
			}
		}
		return true;
	}

}
