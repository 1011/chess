package chess;

public class Rook extends Piece{

	boolean moved = false;
	public Rook(char color) {
		super(color, 'R');
	}
	
	/*
	 * @param	src	->	Current square of the rook
	 * @param	dest ->	Requested square to move the rook to
	 * @return	True if the rook can move to the requested square, false otherwise
	 * */
	public boolean validMove(String src, String dest) {
		int src_xIndex = src.charAt(0) - 'a';
		int src_yIndex = Integer.parseInt(src.charAt(1) + "") - 1;
		int dest_xIndex = dest.charAt(0) - 'a';
		int dest_yIndex = Integer.parseInt(dest.charAt(1) + "") - 1;
		
		// Easiest check
		if(src_xIndex != dest_xIndex && src_yIndex != dest_yIndex) {
			return false;
		}
		
		// Moving to a square where a piece already exists, and is of the same color
		if(Game.board[dest_xIndex][dest_yIndex].occupied && this.color == Game.board[dest_xIndex][dest_yIndex].occupyingPiece.color) {
			return false;
		}
		
		return checkPath(src, dest);
		
	}
	
	/*
	 * @param	src	->	Current square of the rook
	 * @param	dest ->	Requested square to move the rook to
	 * @return	True if the path from src to dest is unobstructed, false otherwise
	 * */
	public boolean checkPath(String src, String dest) {
		int src_xIndex = src.charAt(0) - 'a';
		int src_yIndex = Integer.parseInt(src.charAt(1) + "") - 1;
		int dest_xIndex = dest.charAt(0) - 'a';
		int dest_yIndex = Integer.parseInt(dest.charAt(1) + "") - 1;
		
		//Moving horizontally
		if(src_yIndex == dest_yIndex) {
			if(src_xIndex > dest_xIndex) {
				for(int i = dest_xIndex + 1; i < src_xIndex; i++) {
					if(Game.board[i][dest_yIndex].occupied) {
						return false;
					}
				}
			}
			else {
				for(int i = src_xIndex + 1; i < dest_xIndex; i++) {
					if(Game.board[i][dest_yIndex].occupied) {
						return false;
					}
				}
			}
			
		}
		// Moving vertically
		else {
			if(src_yIndex > dest_yIndex) {
				for(int i = dest_yIndex + 1; i < src_yIndex; i++) {
					if(Game.board[dest_xIndex][i].occupied) {
						return false;
					}
				}
			}
			else {
				for(int i = src_yIndex + 1; i < dest_yIndex; i++) {
					if(Game.board[dest_xIndex][i].occupied) {
						return false;
					}
				}
			}
		}
		
		return true;
	}
}
