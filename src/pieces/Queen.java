/**
 * @authors Aaron Argueta and Yash Shah
 */
package pieces;

import board.Block;
import util.CollisionChecker;

public class Queen extends Pieces implements CollisionChecker {

	public Queen(String color) {
		super(color);
		type = "queen";
		
	}
	
	/**
	 * 
	 * isValidMove()
	 * @param input
	 * @param chessboard
	 * @return boolean
	 * 
	 * Checks whether the move is valid for Queen and returns boolean
	 * 
	 */

	@Override
	public boolean isValidMove(String input, Block[][] chessboard) {
		String[] args = input.split(" ");
		char currFile = args[0].charAt(0); char currRank = args[0].charAt(1);
		char nextFile = args[1].charAt(0); char nextRank = args[1].charAt(1);
		//check if valid long movement
		
		if ((Math.abs(currFile - nextFile) == Math.abs(currRank - nextRank) 
				|| ((currFile == nextFile && currRank != nextRank) 
				|| (currFile != nextFile && currRank == nextRank)))
				&& !hasPiecesInbetween(currFile, currRank, nextFile, nextRank, chessboard)) {
			if (chessboard[nextFile-'a'][nextRank-49].getPiece() == null
				|| chessboard[nextFile-'a'][nextRank-49].getPiece().isWhite() != isWhite()) {
				return true;
			}
		}
		
		return false;
	}

	/**
	 * 
	 * move()
	 * @param input
	 * @param board
	 * @return square
	 * 
	 * Moves the pieces according to the input and updates the board
	 *
	 * 
	 */
	@Override
	public Block[][] move(String input, Block[][] board) {
		String[] args = input.split(" ");
		char currFile = args[0].charAt(0); char currRank = args[0].charAt(1);
		char nextFile = args[1].charAt(0); char nextRank = args[1].charAt(1);
		
		Pieces currPiece = board[currFile-'a'][currRank-49].getPiece();
		board[nextFile-'a'][nextRank-49].setPiece(currPiece);
		board[currFile-'a'][currRank-49].setPiece(null);
		
		moved(); // set hasMoved to true
		return board;
	}
}
