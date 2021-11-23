/**
 * @authors Aaron Argueta and Yash Shah
 */
package pieces;

import board.Block;

public class Pawn extends Pieces {

	private boolean enpassant;
	private boolean willEnpassant;
	/**
	 * 
	 * @param color
	 */
	public Pawn(String color) {
		super(color);
		type = "pawn";
		enpassant = true;
		willEnpassant = false;
	}

	
	/**
	 * move()
	 * @param input
	 * @param chessboard
	 * @return Block[][]
	 * 
	 * Returns updated chessboard after moving pawn accordingly
	 */
	@Override
	public Block[][] move(String input, Block[][] chessboard) {
		
		String[] args = input.split(" ");
		char currFile = args[0].charAt(0); char currRank = 	args[0].charAt(1);
		char nextFile = args[1].charAt(0); char nextRank = 	args[1].charAt(1);
		
		Pieces currPiece = chessboard[currFile-'a'][currRank-49].getPiece();
		chessboard[nextFile-'a'][nextRank-49].setPiece(currPiece);
		chessboard[currFile-'a'][currRank-49].setPiece(null);
		
	
		if (currFile != nextFile && willEnpassant) { 
			if (isWhite && currRank+1 == nextRank && currFile+1 == nextFile) {
				chessboard[currFile-'a'+1][currRank-49].setPiece(null);}
			if (isWhite && currRank+1 == nextRank && currFile-1 == nextFile) {
				chessboard[currFile-'a'-1][currRank-49].setPiece(null);}
			if (!isWhite && currRank-1 == nextRank && currFile+1 == nextFile) {
				chessboard[currFile-'a'+1][currRank-49].setPiece(null);}
			if (!isWhite && currRank-1 == nextRank && currFile-1 == nextFile) {
				chessboard[currFile-'a'-1][currRank-49].setPiece(null);}
		}
		if (isWhite && nextRank == '8') {
			promotion(input, nextFile, nextRank, chessboard, "white");}
		if (!isWhite && nextRank == '1') {
			promotion(input, nextFile, nextRank, chessboard, "black");}
		
		moved(); 
		return chessboard;
	}
	/**
	 * promotion()
	 * Changes pawn into queen, knight, rook or bishop
	 * @param input
	 * @param nextFile
	 * @param nextRank
	 * @param chessboard
	 * @param pieceColor
	 * 
	 */
	public void promotion(String input, char nextFile, char nextRank, Block[][] chessboard, String pieceColor) {
		String[] args = input.split(" ");
		if (args.length == 3) {
			switch(args[2])
			{
				case "Q":
					chessboard[nextFile-'a'][nextRank-49].setPiece(new Queen(pieceColor));
					return;
				case "N":
					chessboard[nextFile-'a'][nextRank-49].setPiece(new Knight(pieceColor));
					return;
				case "R":
					chessboard[nextFile-'a'][nextRank-49].setPiece(new Rook(pieceColor));
					return;
				case "B":
					chessboard[nextFile-'a'][nextRank-49].setPiece(new Bishop(pieceColor));
					return;
			}
		}
		chessboard[nextFile-'a'][nextRank-49].setPiece(new Queen(pieceColor));
	}
	
	/**
	 * isValidMove()
	 * @param input
	 * @param chessboard
	 * @return boolean
	 * 
	 * Tells whether the move indicated for this Pawn is valid
	 */
	@Override
	public boolean isValidMove(String input, Block[][] chessboard) {
		String[] args = input.split(" ");
		char currFile = args[0].charAt(0); char currRank = args[0].charAt(1);
		char nextFile = args[1].charAt(0); char nextRank = args[1].charAt(1);
		
		willEnpassant = false;
		
		// straight pawn move
		if (currFile == nextFile) {
			if (isWhite && currRank+1 == nextRank 
					&& chessboard[nextFile-'a'][nextRank-49].getPiece() == null){
				enpassant = false;
				return true;
			}
			
			// it can move up to 2 spots only if it's white and is it's first move
			if (isWhite && currRank == '2' 
					&& currRank+2 == nextRank 
					&& chessboard[nextFile-'a'][nextRank-49].getPiece() == null){
				enpassant = true;
				return true;
			}
			
			if (!isWhite && currRank-1 == nextRank 
					&& chessboard[nextFile-'a'][nextRank-49].getPiece() == null)
			{
				enpassant = false;
				return true;
			}
			
			if (!isWhite && currRank == '7' 
					&& currRank-2 == nextRank 
					&& chessboard[nextFile-'a'][nextRank-49].getPiece() == null){
				enpassant = true;
				return true;
			}
		}
		
		
		//Diagonal move
		if (isWhite && currRank+1 == nextRank 
				&& currFile+1 == nextFile 
				&& chessboard[currFile-'a'+1][currRank-48].getPiece() != null 
				&& !chessboard[currFile-'a'+1][currRank-48].getPiece().isWhite())
		{
			enpassant = false;
			return true;
		}
		if (isWhite && currRank+1 == nextRank 
				&& currFile-1 == nextFile 
				&& chessboard[currFile-'a'-1][currRank-48].getPiece() != null 
				&& !chessboard[currFile-'a'-1][currRank-48].getPiece().isWhite())
		{
			enpassant = false;
			return true;
		}
		
		if (!isWhite && currRank-1 == nextRank 
				&& currFile+1 == nextFile 
				&& chessboard[currFile-'a'+1][currRank-50].getPiece() != null 
				&& chessboard[currFile-'a'+1][currRank-50].getPiece().isWhite())
		{
			enpassant = false;
			return true;
		}
		if (!isWhite && currRank-1 == nextRank 
				&& currFile-1 == nextFile 
				&& chessboard[currFile-'a'-1][currRank-50].getPiece() != null 
				&& chessboard[currFile-'a'-1][currRank-50].getPiece().isWhite())
		{
			enpassant = false;
			return true;
		}
		
		
		if (isWhite && currRank == '5' 
				&& nextRank =='6' 
				&& currFile+1 == nextFile 
				&& chessboard[currFile-'a'+1][currRank-49].getPiece() != null 
				&& !chessboard[currFile-'a'+1][currRank-49].getPiece().isWhite() 
				&& canEnpassant(currFile-'a'+1, currRank-49,chessboard) 
				&& chessboard[nextFile-'a'][nextRank-49].getPiece() == null)
		{
			enpassant = false;
			willEnpassant = true;
			return true;
		}
		if (isWhite && currRank == '5' 
				&& nextRank =='6' 
				&& currFile-1 == nextFile 
				&& chessboard[currFile-'a'-1][currRank-49].getPiece() != null 
				&& !chessboard[currFile-'a'-1][currRank-49].getPiece().isWhite() 
				&& canEnpassant(currFile-'a'-1, currRank-49,chessboard) 
				&& chessboard[nextFile-'a'][nextRank-49].getPiece() == null)
		{
			enpassant = false;
			willEnpassant = true;
			return true;
		}
		if (!isWhite && currRank == '4' 
				&& nextRank =='3'
				&& currFile+1 == nextFile 
				&& chessboard[currFile-'a'+1][currRank-49].getPiece() != null 
				&& chessboard[currFile-'a'+1][currRank-49].getPiece().isWhite() 
				&& canEnpassant(currFile-'a'+1, currRank-49,chessboard) 
				&& chessboard[nextFile-'a'][nextRank-49].getPiece() == null)
		{
			enpassant = false;
			willEnpassant = true;
			return true;
		}
		if (!isWhite && currRank == '4' 
				&& nextRank =='3'
				&& currFile-1 == nextFile 
				&& chessboard[currFile-'a'-1][currRank-49].getPiece() != null 
				&& chessboard[currFile-'a'-1][currRank-49].getPiece().isWhite() 
				&& canEnpassant(currFile-'a'-1, currRank-49, chessboard) 
				&& chessboard[nextFile-'a'][nextRank-49].getPiece() == null)
		{
			
			enpassant = false;
			willEnpassant = true;
			return true;
		}
		
		return false;
	}

	
	/**
	 * canEnpassant
	 * Tells whether a pawn can perform enpassant or not
	 * @param file
	 * @param rank
	 * @param chessboard
	 * @return boolean
	 */
	public boolean canEnpassant(int file, int rank, Block[][] chessboard)
	{
		boolean wouldBeUnderAttack = false;
		boolean isWhite = chessboard[file][rank].getPiece().isWhite();

		//Checks to see if piece would have been under attack if it only moved one space up instead of two
		if(isWhite && rank!=0 && file!=7 
				&& chessboard[file + 1][rank].getPiece()!= null 
				&& !chessboard[file + 1][rank].getPiece().isWhite()){
			wouldBeUnderAttack = true;
		}
		else if(isWhite && rank!=0 && file!=0 
				&& chessboard[file - 1][rank].getPiece()!= null 
				&& !chessboard[file -1][rank].getPiece().isWhite()){
			wouldBeUnderAttack = true;
		}
		else if(!isWhite && rank!=7 && file!=7 
				&& chessboard[file + 1][rank].getPiece()!= null 
				&& chessboard[file+1][rank].getPiece().isWhite()){
			wouldBeUnderAttack = true;
		}
		else if(!isWhite && rank!=7 && file!=0 
				&&chessboard[file - 1][rank].getPiece()!= null 
				&& chessboard[file-1][rank].getPiece().isWhite()){
			wouldBeUnderAttack = true;
		}

		return chessboard[file][rank].getPiece().getEnpassant() && wouldBeUnderAttack;
	}
	
	
	/**
	 * canEnpassant
	 * Returns whether it can capture it or not 
	 * @return boolean
	 */
	public boolean getEnpassant()
	{
		return enpassant;
	}
}