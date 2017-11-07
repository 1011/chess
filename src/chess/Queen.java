package chess;

public class Queen extends Piece{

	public Queen(char color) {
		super(color, 'Q');
	}

	/*
	 * @param	src	->	Current square of the queen
	 * @param	dest ->	Requested square to move the queen to
	 * @return	True if the queen can move to the requested square, false otherwise
	 * */
	public boolean validMove(String src, String dest) {
		
		// Is there a better way to do this @Chris?
		Bishop b = new Bishop(this.color);
		Rook r = new Rook(this.color);
		return b.validMove(src, dest) || r.validMove(src, dest);
	}
}
