/**
 * @authors Aaron Argueta and Yash Shah
 */
package pieces;

import board.Block;

public abstract class Pieces implements Cloneable {

	public boolean isWhite;
	public String type;
	boolean hasMoved;
	/**
	 * 
	 */
	public Pieces(String color) {
		isWhite = color.equals("white");
		type = null;
		hasMoved = false;
	}
	
	/**
	 * isWhite()
	 * Checks to see whether a piece is white, returns true if white
	 * @return boolean
	 * 
	 * 
	 */
	public boolean isWhite() {
		return isWhite;
	}
	
	/**
	 * hasMoved()
	 * Checks to see whether a piece has moved previous, returns true if yes
	 * @return boolean
	 * 
	 * 
	 */
	public boolean hasMoved() {
		return hasMoved;
	}
	
	/**
	 * moved()
	 * Updates hasMoved boolean as true to indicated a piece has moved
	 * @return void
	 * 
	 */
	public void moved() {
		hasMoved = true;
	}
	
	/**
	 * testForCheck()
	 * Checks whether a piece puts enemy king in check, returns true if king is in check
	 * @return boolean
	 * 
	 * 
	 */
	public boolean testForCheck(char file, char rank, Block[][] board) {
		String f = file+"", r = rank+"";
		String testCheckInput = f + r + " " + getEnemyKingLocation(board, isWhite());
		
		return isValidMove(testCheckInput, board);
	}
	
	/**
	 * getEnemyKingLocation()
	 * Gets string of enemy kings location
	 * @return string
	 * 
	 * 
	 */
	
	public String getEnemyKingLocation(Block[][] board, boolean isCurrentlyWhite) {
		String location = "";
		char kingFile, kingRank;
		for (int f = 0; f < 8; f++) {
			for (int r = 0; r < 8; r++) {
				if (board[f][r].getPiece() != null 
						&& board[f][r].getPiece().isWhite() != isCurrentlyWhite
						&& board[f][r].getPiece() instanceof King) {
					kingFile = (char)(f + 'a');
					kingRank = (char)(r + 49);
					location = "" + kingFile + kingRank;
					return location;
				}
			}
		}
		
		return null;
	}
	
	/**
	 * isValidMove
	 * checks to see if move is valid
	 * @param input
	 * @param board
	 * @return boolean
	 */
	public abstract boolean isValidMove(String input, Block[][] board);
	
	/**
	 * move
	 * moves piece and destroys any pieces if applicable
	 * @param input
	 * @param board
	 * @return Block[][]
	 */
	public abstract Block[][] move(String input, Block[][] board);
	
	/**
	 * canEnpassant()
	 * @return boolean
	 * 
	 * Returns boolean indicating whether this piece can enpassant
	 */
	public boolean canEnpassant()
	{
		return false;
	}
	
	/**
	 * getEnpassant()
	 * @return boolean
	 * 
	 * Returns boolean indicating whether this piece can be captured by enpassant
	 */
	public boolean getEnpassant()
	{
		return false;
	}
	
	public Object clone() throws CloneNotSupportedException {
	    return super.clone();
	}
	
	/**
	 * setHasMoved
	 * @param m  
	 * true if has moved, else false
	 */
	public void setHasMoved(boolean m) {
		hasMoved = m;
	}
	
}
