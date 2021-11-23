/**
 * @authors Aaron Argueta and Yash Shah
 */
package board;

import pieces.Pieces;

public class Block implements Cloneable {

	
	public Pieces ontheblockPiece;
	private boolean isBlack;
	
	public Block(String color) {
		this(null, color);
	}
	
	public Block(Pieces piece, String color) {
		isBlack = color.equals("black");
		ontheblockPiece = piece;
	}
	
	/**
	 * getPieceType()
	 * @return String
	 * 
	 * Returns class type of the piece occupying the block
	 */
	public String getPieceType()
	{
		switch(ontheblockPiece.type)
		{
			case "rook":  return "R";
			case "knight": return "N";
			case "bishop": return "B";
			case "pawn": return "p";
			case "king": return "K";
			case "queen": return "Q";
			default: return "";
		}
	}
	
	/**
	 * This sets a piece to that block
	 * @param piece 
	 * this is the piece on the block
	 */
	public void setPiece(Pieces piece) {
		ontheblockPiece = piece;
	}
	/**
	 * This prints information of the piece color and piece type
	 * @return String
	 * 
	 * Returns string of piece color and type of piece occupying the square
	 */	
	public String toString() {
		return getPieceColor() + getPieceType();
	}
	
	public Object clone() throws CloneNotSupportedException {
	    return super.clone();
	}
	
	/**
	 * This makes sure to see if the block is black or not
	 * @return boolean
	 * 
	 * Returns true if square is black
	 */
	public boolean isSquareBlack()
	{
		return isBlack;
	}

	/**
	 * Gets the piece that is on the block
	 * @return Piece
	 * 
	 * Returns the piece that is on the block
	 */
	public Pieces getPiece()
	{
		return ontheblockPiece;
	}
	
	/**
	 * this gets the piece color
	 * @return String
	 * 
	 * 
	 */
	public String getPieceColor()
	{
		if(ontheblockPiece.isWhite)
			return "w";
		else
			return "b";
	}
}