package chess;

public class Square {
	
	boolean occupied;
	Piece occupyingPiece;
	char s_color;

	public Square(Piece piece, char color) {
		occupied = true;
		occupyingPiece = piece;
		s_color = color;
	}
	
	public Square(char color) {
		occupied = false;
		occupyingPiece = null;
		s_color = color;
	}
	
	public Piece getPiece() {
		return occupyingPiece;
	}
	
	public char getColor() {
		return s_color;
	}
	
	public char getPieceColor() {
		return occupyingPiece.color;
	}
	
	public char getPieceRank() {
		return occupyingPiece.rank;
	}

}
