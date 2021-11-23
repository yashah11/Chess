/**
 * @authors Aaron Argueta and Yash Shah
 */
package util;

import board.Block;

/**
 * CollisionChecker
 * 
 *
 */
public interface CollisionChecker {
	/**
	 * hasPieceInbetween()
	 * Checks if there are pieces between the current and next location
	 * @param currFile
	 * @param currRank
	 * @param nextFile
	 * @param nextRank
	 * @param board
	 * @return boolean
	 */
	default public boolean hasPiecesInbetween(char currFile, char currRank, char nextFile, char nextRank, Block[][] board) {
		int file,rank;
		int initialRank = Character.getNumericValue(currRank);
		int finRank = Character.getNumericValue(nextRank);
		
		
		// if it's diagonal movement
		int rankDif = initialRank - finRank;
		int fileDif = currFile - nextFile;
		if (Math.abs(rankDif) == Math.abs(fileDif)) {
			// up right if both quantities are negative
			if (fileDif < 0 && rankDif < 0) {
				rank = initialRank;
				for (file = currFile-'a'+1; file < nextFile-'a'; file++) {
					
					if (board[file][rank].getPiece() != null)
						return true;
					rank++;
				}
			}
			// up left if the fileDif is positive and the rankDif is negative
			if (fileDif > 0 && rankDif < 0) {
				rank = initialRank;
				for (file = currFile-'a'-1; file > nextFile-'a'; file--) {
					if (board[file][rank].getPiece() != null)
						return true;
					rank++;
				}
			}
			// down right if the fileDif is negative and the rankDif is positive
			if (fileDif < 0 && rankDif > 0) {
				rank = initialRank-2;
				for (file = currFile-'a'+1; file < nextFile-'a'; file++) {
					if (board[file][rank].getPiece() != null) {
						return true;
					}
					rank--;
				}
			}
			// downleft if both quantities are positive
			if (fileDif > 0 && rankDif > 0) {

				rank= initialRank-2;
				for (file = currFile-'a'-1; file > nextFile-'a'; file--) {
					if (board[file][rank].getPiece() != null)
						return true;
					rank--;
				}
			}
			// if it's horizontal movement
			if (initialRank == finRank && currFile != nextFile) {
				if (currFile < nextFile) //movement to the right
					for (file = currFile-'a'+1; file < nextFile-'a'; file++)
						if (board[file][initialRank-1].getPiece() != null)
							return true;
				if (currFile > nextFile) //movement to the left
					for (file = currFile-'a'-1; file > nextFile-'a'; file--)
						if (board[file][initialRank-1].getPiece() != null)
							return true;
			}
			// if it's vertical movement
			if (initialRank != finRank && currFile == nextFile) {
				if (initialRank < finRank) //movement upwards
					for (rank = initialRank; rank < finRank - 1; rank++)
						if (board[currFile-'a'][rank].getPiece() != null)
							return true;
				if (initialRank > finRank) //movement downwards
					for (rank = initialRank-2; rank >= finRank; rank--)
						if (board[currFile-'a'][rank].getPiece() != null)
							return true;
			}
		}
		
		return false;
	}
}