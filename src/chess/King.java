package chess;

public class King extends Piece {
	
	//boolean moved = false;
	boolean castling = false;
	
	public King(char color) {
		super(color, 'K');
	}

	/*
	 * @param	src	->	Current square of the king
	 * @param	dest ->	Requested square to move the king to
	 * @return	True if the king can move to the requested square, false otherwise
	 * */
	public boolean validMove(String src, String dest) {
		int src_xIndex = src.charAt(0) - 'a';
		int src_yIndex = Integer.parseInt(src.charAt(1) + "") - 1;
		int dest_xIndex = dest.charAt(0) - 'a';
		int dest_yIndex = Integer.parseInt(dest.charAt(1) + "") - 1;
		
		// Need to add castling. Adding castling will have to update updateBoard(), because 2 pieces will be moved. 
		
		if(src_xIndex + 2 == dest_xIndex || src_xIndex - 2 == dest_xIndex) {
			//System.out.println("one");
			//attempting to castle
			if(!this.hasMoved) {
	 			if(dest_xIndex == 6) {
	 				//System.out.println("step");
	 				if(Game.board[5][src_yIndex].occupied || Game.board[6][src_yIndex].occupied || !(Game.board[7][src_yIndex].occupyingPiece instanceof Rook) || Game.board[7][src_yIndex].occupyingPiece.hasMoved) {
	 					//System.out.println("NOT HERE");
	 					return false;
	 				}
	 			}else if (dest_xIndex == 2) {
	 				if(Game.board[1][src_yIndex].occupied || Game.board[2][src_yIndex].occupied || Game.board[3][src_yIndex].occupied || !(Game.board[0][src_yIndex].occupyingPiece instanceof Rook) || Game.board[0][src_yIndex].occupyingPiece.hasMoved) {
	 					return false;
	 				}
	 			}
	 			//System.out.println("closer");
	 			this.castling = true;
	 		}
		}else if(Math.abs(src_xIndex - dest_xIndex) > 1 || Math.abs(src_yIndex - dest_yIndex) > 1) {
			return false;
		}
		
		if(Game.board[dest_xIndex][dest_yIndex].occupied && this.color == Game.board[dest_xIndex][dest_yIndex].occupyingPiece.color) {
			return false;
		}
		
		return true;
	}
}
	/*
	 * @param	src	->	Current square of the king
	 * @param	dest ->	Requested square to move the king to
	 * @return	True if the king is able to castle, false otherwise
	 * *
     public boolean checkCastle(String src, String dest) {
    	int src_xIndex = src.charAt(0) - 'a';
 		int src_yIndex = Integer.parseInt(src.charAt(1) + "") - 1;
 		int dest_xIndex = dest.charAt(0) - 'a';
 		int dest_yIndex = Integer.parseInt(dest.charAt(1) + "") - 1;
 		
 	 
 		if(!this.hasMoved) {
 			if(dest_xIndex == 6) {
 				if(Game.board[src_xIndex][5].occupied || Game.board[src_xIndex][6].occupied || !(Game.board[src_xIndex][7].occupyingPiece instanceof Rook) || Game.board[src_xIndex][7].occupyingPiece.hasMoved) {
 					return false;
 				}
 			}else if (dest_xIndex == 2) {
 				if(Game.board[src_xIndex][1].occupied || Game.board[src_xIndex][2].occupied || Game.board[src_xIndex][3].occupied || !(Game.board[src_xIndex][0].occupyingPiece instanceof Rook) || Game.board[src_xIndex][0].occupyingPiece.hasMoved) {
 					return false;
 				}
 			}
 		}			 				
 		return true;	
     }
}
*/
