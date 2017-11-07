package chess;

public abstract class Piece {

	char color;			//b - black, w - white
	char rank;			//r - rook, n - knight, b - bishop, q - queen, k - king, p - pawn
	String name;		//color + rank, for parsing output
	boolean hasMoved;	//false until this instance of this piece is moved for first time, then true

	
	public Piece(char p_color, char p_rank) {
		color = p_color;
		rank = p_rank;
		name = "" + color + rank;
		hasMoved = false;
	}
	
	public boolean validMove(String src, String dest) {
		return true;
	}
}

