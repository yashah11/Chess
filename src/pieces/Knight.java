/**
 * @authors Aaron Argueta and Yash Shah
 */
package pieces;

import board.Block;

public class Knight extends Pieces {

	/**\
	 * 
	 * @param color
	 */
	public Knight(String color) {
		super(color);
		type = "knight";
	}

	/**
	 * 
	 * isValidMove()
	 * @param input
	 * @param chessboard
	 * @return boolean
	 * 
	 * Checks whether the move is valid for Knight and returns boolean
	 *
	 * 
	 */
	@Override
	public boolean isValidMove(String input, Block[][] chessboard) {
		String[] args = input.split(" ");
		char currFile = args[0].charAt(0); char currRank = args[0].charAt(1);
		char nextFile = args[1].charAt(0); char nextRank = args[1].charAt(1);
		
		if (Math.abs(currFile - nextFile) == 1 
				&& Math.abs(currRank - nextRank) == 2 
				&& chessboard[nextFile-'a'][nextRank-49].getPiece() == null)
			return true;
		else if (Math.abs(currFile - nextFile) == 2 
				&& Math.abs(currRank - nextRank) == 1 
				&& chessboard[nextFile-'a'][nextRank-49].getPiece() == null)
			return true;
		else if (Math.abs(currFile - nextFile) == 1 
				&& Math.abs(currRank - nextRank) == 2 
				&& chessboard[nextFile-'a'][nextRank-49].getPiece().isWhite() != chessboard[currFile-'a'][currRank-49].getPiece().isWhite())
			return true;
		else if (Math.abs(currFile - nextFile) == 2 
				&& Math.abs(currRank - nextRank) == 1 
				&& chessboard[nextFile-'a'][nextRank-49].getPiece().isWhite() != chessboard[currFile-'a'][currRank-49].getPiece().isWhite())
			return true;
		else
			return false;
	}

	/**
	 * 
	 * move()
	 * @param input
	 * @param chessboard
	 * @return Block[][]
	 * 
	 * Moves the pieces according to the input and updates the board
	 *
	 * 
	 */
	@Override
	public Block[][] move(String input, Block[][] chessboard) {
		String[] args = input.split(" ");
		char currFile = args[0].charAt(0); char currRank = args[0].charAt(1);
		char nextFile = args[1].charAt(0); char nextRank = args[1].charAt(1);
		
		Pieces currPiece = chessboard[currFile-'a'][currRank-49].getPiece();
		
		chessboard[nextFile-'a'][nextRank-49].setPiece(currPiece);
		chessboard[currFile-'a'][currRank-49].setPiece(null);
		
		moved(); // set hasMoved to true
		
		return chessboard;
	}
	
}
