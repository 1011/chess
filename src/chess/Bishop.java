package chess;

public class Bishop extends Piece{

	public Bishop(char color) {
		super(color, 'B');
	}

	/*
	 * @param	src	->	Current square of the bishop
	 * @param	dest ->	Requested square to move the bishop to
	 * @return	True if the bishop can move to the requested square, false otherwise
	 * */
	public boolean validMove(String src, String dest) {
		int src_xIndex = src.charAt(0) - 'a';
		int src_yIndex = Integer.parseInt(src.charAt(1) + "") - 1;
		int dest_xIndex = dest.charAt(0) - 'a';
		int dest_yIndex = Integer.parseInt(dest.charAt(1) + "") - 1;
		
		if(Game.board[dest_xIndex][dest_yIndex].occupied && this.color == Game.board[dest_xIndex][dest_yIndex].occupyingPiece.color) {
			return false;
		}
		
		return checkPath(src, dest);
	}
	
	/*
	 * @param	src	->	Current square of the bishop
	 * @param	dest ->	Requested square to move the bishop to
	 * @return	True if the path from src to dest is unobstructed, false otherwise
	 * */
	public boolean checkPath(String src, String dest) {
		int src_xIndex = src.charAt(0) - 'a';
		int src_yIndex = Integer.parseInt(src.charAt(1) + "") - 1;
		int dest_xIndex = dest.charAt(0) - 'a';
		int dest_yIndex = Integer.parseInt(dest.charAt(1) + "") - 1;
		
		//System.out.println(Math.abs(src_xIndex - dest_xIndex));
		//System.out.println(Math.abs(src_yIndex - dest_yIndex));
		// Check if path is diagonal
		if(Math.abs(src_xIndex - dest_xIndex) == Math.abs(src_yIndex - dest_yIndex)) {
			// 4 possible directions
			// UL
			if(src_xIndex > dest_xIndex && dest_yIndex > src_yIndex) {
				int x = src_xIndex - 1;
				int y = src_yIndex + 1;
				while(x != dest_xIndex && y != dest_yIndex) {
					if(Game.board[x][y].occupied) {
						return false;
					}
					x--;
					y++;
				}
			}
			
			//UR
			if(src_xIndex < dest_xIndex && dest_yIndex > src_yIndex) {
				int x = src_xIndex + 1;
				int y = src_yIndex + 1;
				while(x != dest_xIndex && y != dest_yIndex) {
					if(Game.board[x][y].occupied) {
						return false;
					}
					x++;
					y++;
				}
			}
			
			//DR
			if(src_xIndex < dest_xIndex && dest_yIndex < src_yIndex) {
				int x = src_xIndex + 1;
				int y = src_yIndex - 1;
				while(x != dest_xIndex && y != dest_yIndex) {
					if(Game.board[x][y].occupied) {
						return false;
					}
					x++;
					y--;
				}
			}
			
			//DL
			if(src_xIndex > dest_xIndex && dest_yIndex < src_yIndex) {
				int x = src_xIndex - 1;
				int y = src_yIndex - 1;
				while(x != dest_xIndex && y != dest_yIndex) {
					if(Game.board[x][y].occupied) {
						return false;
					}
					x--;
					y--;
				}
			}
			
			return true;
		}
		
		return false;
	}
}
