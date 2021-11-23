/**
 * @authors Aaron Argueta and Yash Shah
 */
package pieces;

import board.Block;
import util.CollisionChecker;

public class King extends Pieces implements CollisionChecker {

	/**
	 *
	 * @param color
	 */
	public King(String color) {
		super(color);
		type = "king";
	}

	/**
	 * 
	 * isValidMove()
	 * @param input
	 * @param chessboard
	 * @return boolean
	 * 
	 * Checks whether the move is valid for King and returns boolean
	 *
	 * 
	 */
	@Override
	public boolean isValidMove(String input, Block[][] chessboard) {
		String[] args = input.split(" ");
		char currFile = args[0].charAt(0); char currRank = args[0].charAt(1);
		char nextFile = args[1].charAt(0); char nextRank = args[1].charAt(1);
		
		if (currFile == nextFile 
				&& Math.abs(currRank - nextRank) == 1 
				&& (chessboard[nextFile-'a'][nextRank-49].getPiece() == null 
				|| chessboard[nextFile-'a'][nextRank-49].getPiece().isWhite() != isWhite()))
			return true;
		if (currRank == nextRank 
				&& Math.abs(currFile - nextFile) == 1 
				&& (chessboard[nextFile-'a'][nextRank-49].getPiece() == null 
				|| chessboard[nextFile-'a'][nextRank-49].getPiece().isWhite() != isWhite()))
			return true;
		if( Math.abs(currFile - nextFile) == 1 
				&& Math.abs(currRank-nextRank) ==1
				&& (chessboard[nextFile-'a'][nextRank-49].getPiece() == null 
				|| chessboard[nextFile-'a'][nextRank-49].getPiece().isWhite() != isWhite()))
			return true;
		if (!hasMoved()){
			if(nextFile == 'g' 
					&& (nextRank-48 == 1 || nextRank-48 == 8)
					&& chessboard[7][nextRank-49].getPiece() instanceof Rook
					&& !chessboard[7][nextRank-49].getPiece().hasMoved()
					&& chessboard[7][nextRank-49].getPiece().isWhite() == isWhite()
					&& !hasPiecesInbetween(currFile, currRank, 'h', nextRank, chessboard)){
				return true;
				
			}
			if(nextFile == 'c' 
					&& (nextRank-48 == 1 || nextRank-48 == 8)
					&& chessboard[0][nextRank-49].getPiece() instanceof Rook
					&& !chessboard[0][nextRank-49].getPiece().hasMoved()
					&& chessboard[0][nextRank-49].getPiece().isWhite() == isWhite()
					&& !hasPiecesInbetween(currFile, currRank, 'a', nextRank, chessboard)){
				return true;
				
			}
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
	 * 
	 */
	@Override
	public Block[][] move(String input, Block[][] chessboard) {
		String[] args = input.split(" ");
		char currFile = args[0].charAt(0); char currRank = args[0].charAt(1);
		char nextFile = args[1].charAt(0); char nextRank = args[1].charAt(1);
		
		if (!hasMoved() && nextFile == 'g' 
					&& (nextRank-48 == 1 || nextRank-48 == 8)
					&& chessboard[7][nextRank-49].getPiece() instanceof Rook
					&& !chessboard[7][nextRank-49].getPiece().hasMoved()
					&& chessboard[7][nextRank-49].getPiece().isWhite() == isWhite()
					&& !hasPiecesInbetween(currFile, currRank, 'h', nextRank, chessboard)){
				chessboard = castling(currFile, currRank, 'h', nextRank, chessboard);
		}
		else if (!hasMoved() && nextFile == 'c' 
					&& (nextRank-48 == 1 || nextRank-48 == 8)
					&& chessboard[0][nextRank-49].getPiece() instanceof Rook
					&& !chessboard[0][nextRank-49].getPiece().hasMoved()
					&& chessboard[0][nextRank-49].getPiece().isWhite() == isWhite()
					&& !hasPiecesInbetween(currFile, currRank, 'a', nextRank, chessboard)){
				chessboard = castling(currFile, currRank, 'a', nextRank, chessboard);
		}		
		else {
			Pieces currPiece = chessboard[currFile-'a'][currRank-49].getPiece();
		
			chessboard[nextFile-'a'][nextRank-49].setPiece(currPiece);
			chessboard[currFile-'a'][currRank-49].setPiece(null);
		}
		
		moved(); // set hasMoved to true
		return chessboard;
	}
	/**
	 * 
	 * castling()
	 * @param currFile
	 * @param currRank
	 * @param nextFile
	 * @param nextRank
	 * @param chessboard
	 * @return Block[][]
	 * 
	 * Moves pieces according to castling rules
	 *
	 * 
	 */
	public Block[][] castling(char currFile, char currRank, char nextFile, char nextRank, Block[][] chessboard) {
		Pieces king = chessboard[currFile-'a'][currRank-49].getPiece();
		Pieces rook = chessboard[nextFile-'a'][nextRank-49].getPiece();
		
		if (nextFile == 'h') {
			chessboard[6][nextRank-49].setPiece(king); chessboard[5][nextRank-49].setPiece(rook);
			chessboard[7][nextRank-49].setPiece(null); chessboard[4][nextRank-49].setPiece(null);
		}
		if (nextFile == 'a') {
			chessboard[nextFile-'a'+2][nextRank-49].setPiece(king); chessboard[nextFile-'a'+3][nextRank-49].setPiece(rook);
			chessboard[currFile-'a'][currRank-49].setPiece(null); chessboard[nextFile-'a'][nextRank-49].setPiece(null);
		}
		
		return chessboard;
	}
}
