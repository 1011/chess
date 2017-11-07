package chess;

public class Knight extends Piece{

	public Knight(char color) {
		super(color, 'N');
	}
	
	/*
	 * @param	src	->	Current square of the knight
	 * @param	dest ->	Requested square to move the knight to
	 * @return	True if the knight can move to the requested square, false otherwise
	 * */
	public boolean validMove(String src, String dest) {
		int src_xIndex = src.charAt(0) - 'a';
		int src_yIndex = Integer.parseInt(src.charAt(1) + "") - 1;
		int dest_xIndex = dest.charAt(0) - 'a';
		int dest_yIndex = Integer.parseInt(dest.charAt(1) + "") - 1;
		// 8 possible moves for a knight in best case
		
		if(Game.board[dest_xIndex][dest_yIndex].occupied && Game.board[dest_xIndex][dest_yIndex].occupyingPiece.color == this.color) {
			return false;
		}
		
		// Move 1: 2L1U
		if(dest_yIndex - src_yIndex == 1 && src_xIndex - dest_yIndex == 2) {
			return true;
		}
		
		// Move 2: 1L2U
		if(dest_yIndex - src_yIndex == 2 && src_xIndex - dest_xIndex == 1) {
			return true;
		}
		
		// Move 3: 2U1R
		if(dest_yIndex - src_yIndex == 2 && dest_xIndex - src_xIndex == 1) {
			return true;
		}
		
		// Move 4: 1U2R
		if(dest_yIndex - src_yIndex == 1 && dest_xIndex - src_xIndex == 2) {
			return true;
		}
		
		// Move 5: 1D2R
		if(src_yIndex - dest_yIndex == 1 && dest_xIndex - src_xIndex == 2) {
			return true;
		}
		
		// Move 6: 2D1R
		if(src_yIndex - dest_yIndex == 2 && dest_xIndex - src_xIndex == 1) {
			return true;
		}
		
		// Move 7: 2D1L
		if(src_yIndex - dest_yIndex == 2 && src_xIndex - dest_xIndex == 1) {
			return true;
		}
		
		// Move 8: 1D2L
		if(src_yIndex - dest_yIndex == 1 && src_xIndex - dest_xIndex == 2) {
			return true;
		}
		
		return false;
	}
}
