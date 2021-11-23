/**
 * @authors Aaron Argueta and Yash Shah
 */
package pieces;

import board.Block;
import util.CollisionChecker;

public class Bishop extends Pieces implements CollisionChecker {

	/**
	 * 
	 * @param color
	 */
	public Bishop(String color) {
		super(color);
		type = "bishop";
	}

	/**
	 * 
	 * isValidMove()
	 * @param input
	 * @param chessboard
	 * @return boolean
	 * 
	 * Checks whether the move is valid for Bishop
	 * 
	 */
	@Override
	public boolean isValidMove(String input, Block[][] chessboard) {
		String[] args = input.split(" ");
		char currFile = args[0].charAt(0); char currRank = args[0].charAt(1);
		char nextFile = args[1].charAt(0); char nextRank = args[1].charAt(1);
		
		if (Math.abs(currFile - nextFile) == Math.abs(currRank - nextRank) 
				&& !hasPiecesInbetween(currFile, currRank, nextFile, nextRank, chessboard)) {
			if (chessboard[nextFile-'a'][nextRank-49].getPiece() == null)
				return true;
			if (chessboard[nextFile-'a'][nextRank-49].getPiece().isWhite() != chessboard[currFile-'a'][currRank-49].getPiece().isWhite())
				return true;
		}
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
	 */
	@Override
	public Block[][] move(String input, Block[][] chessboard) {
		String[] args = input.split(" ");
		char currFile = args[0].charAt(0); char currRank = args[0].charAt(1);
		char nextFile = args[1].charAt(0); char nextRank = args[1].charAt(1);
		
		Pieces currPiece = chessboard[currFile-'a'][currRank-49].getPiece();
		chessboard[nextFile-'a'][nextRank-49].setPiece(currPiece);
		chessboard[currFile-'a'][currRank-49].setPiece(null);
		
		moved(); // set has Moved to true
		return chessboard;
	}

}
