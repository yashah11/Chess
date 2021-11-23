/**
 * @authors Aaron Argueta and Yash Shah
 */
package board;

import pieces.Bishop;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Queen;
import pieces.Rook;

public class ChessBoard {
	
	private Block[][] chessboard;
	private boolean isDone, isStalemate, isResign, isWhiteWins, isWhitesMove, isWhiteInCheck, isBlackInCheck, isInCheck, isDrawAvailable;
	
	public ChessBoard() {
		
		chessboard = new Block[8][8];
		
		chessboard[0][7] = new Block(new Rook("black"),"black");
		chessboard[7][7] = new Block(new Rook("black"),"white");
		chessboard[1][7] = new Block(new Knight("black"),"white");
		chessboard[6][7] = new Block(new Knight("black"),"black");
		chessboard[2][7] = new Block(new Bishop("black"),"black");
		chessboard[5][7] = new Block(new Bishop("black"),"white");
		chessboard[3][7] = new Block(new Queen("black"),"white");
		chessboard[4][7] = new Block(new King("black"),"black");
		
		int file, rank;
		rank = 6; 
		for(file = 0; file < 8; file++)
		{
			if (file%2 == 0)
				chessboard[file][rank] = new Block(new Pawn("black"),"white");
			else
				chessboard[file][rank] = new Block(new Pawn("black"),"black");	
		}
		
		/*Initialize Ranks 3-6*/

		for(rank = 5; rank>=2; rank--){
			for(file = 0; file<8; file++){
				if ((file%2 == 0 && rank%2 == 0) || (file%2 != 0 && rank%2 != 0))
					chessboard[file][rank] = new Block("white");
				else
					chessboard[file][rank] = new Block("black");
			}
		}
		
		/*Initialize Rank 2*/
		rank = 1; 
		for(file = 0; file < 8; file++)
		{
			if (file%2 == 0)
				chessboard[file][rank] = new Block(new Pawn("white"),"black");
			else
				chessboard[file][rank] = new Block(new Pawn("white"),"white");	
		}
		
		/*Initialize Rank 1*/
		chessboard[0][0] = new Block(new Rook("white"),"white");
		chessboard[7][0] = new Block(new Rook("white"),"black");
		chessboard[1][0] = new Block(new Knight("white"),"black");
		chessboard[6][0] = new Block(new Knight("white"),"white");
		chessboard[2][0] = new Block(new Bishop("white"),"white");
		chessboard[5][0] = new Block(new Bishop("white"),"black");
		chessboard[3][0] = new Block(new Queen("white"),"black");
		chessboard[4][0] = new Block(new King("white"),"white");
		
		isDone = false; isStalemate = false; isResign = false; isWhiteWins = false; isWhitesMove = true;
		isWhiteInCheck = false; isBlackInCheck = false; isInCheck = false; isDrawAvailable = false;
	}
	
	public void drawBoard()
	{
		int rank, file;

		for(rank = 7; rank >= 0; rank--)
		{
			for(file = 0; file < 8; file++)
			{
				if(chessboard[file][rank].getPiece() != null){
					System.out.print(chessboard[file][rank] + " ");
				}
				else{
					if(chessboard[file][rank].isSquareBlack())
						System.out.print("   ");
					else
						System.out.print("## ");
				}
				
			}
			System.out.println(" " + (rank+1));
		}
		System.out.println(" a  b  c  d  e  f  g  h");
		System.out.println();
	}
	
	/**
	 * move()
	 * 
	 * @param input  parse input and move 
	 * 
	 */
	public void move(String input) {
		String[] args = input.split(" ");
		char currFile = args[0].charAt(0); char currRank = args[0].charAt(1);
		char nextFile = args[1].charAt(0); char nextRank = args[1].charAt(1);
		
		isDrawAvailable = false;
		if (args.length == 3 && args[2].equals("draw?"))
			isDrawAvailable = true;
		chessboard = chessboard[currFile-'a'][currRank-49].getPiece().move(input, chessboard);
		
		testForCheck(chessboard); //tests for check and check mate
		changePlayer();
	}
	
	/**
	 * isValidMove()
	 * @param input parse input and checks if it is a valid move
	 * @return boolean
	 * 		true if input is valid, else false
	 */
	public boolean isValidMove(String input) {
		String[] args = input.split(" ");
		String currPos = args[0];
		String nextPos = args[1];
		char currFile = currPos.charAt(0); char currRank = currPos.charAt(1);
		char nextFile = nextPos.charAt(0); char nextRank = nextPos.charAt(1);
		
		// if the position is the same or there's no piece in the current file or that piece is not the correct player's piece
		if (currPos.equals(nextPos)
				|| chessboard[currFile-'a'][currRank-49].getPiece() == null
				|| chessboard[currFile-'a'][currRank-49].getPiece().isWhite() != isWhitesMove()
				|| chessboard[nextFile-'a'][nextRank-49].getPiece() instanceof King)
			return false;
		
		// if there's a third argument, and the piece isn't a pawn, return false (for promotion)
		if (args.length == 3 && !(chessboard[currFile-'a'][currRank-49].getPiece() instanceof Pawn))
			return false;
		
		boolean isValidMove = chessboard[currFile-'a'][currRank-49].getPiece().isValidMove(input, chessboard);
		
		
		// ensure that the player does not bring themselves into a check
		if (isValidMove) {
			Block[][] clone = createClone(chessboard);
			boolean hasMoved = chessboard[currFile-'a'][currRank-49].getPiece().hasMoved();
			clone = clone[currFile-'a'][currRank-49].getPiece().move(input, clone);
			chessboard[currFile-'a'][currRank-49].getPiece().setHasMoved(hasMoved);
			return !isKingInCheck(clone, isWhitesMove());
		}
		
		return isValidMove;
	}
	
	/**
	 * askForInput
	 * asks for input depending on who's move it is
	 */
	public void askForInput() {
		if (isWhitesMove())
			System.out.print("White's move: ");
		else
			System.out.print("Black's move: ");
	}
	
	/**
	 * changePlayer
	 */
	public void changePlayer() {
		isWhitesMove = !isWhitesMove;
	}
	
	/**
	 * testForCheck()
	 * @param b
	 * first checks if king is in check
	 * then, if it is in check, check for checkmate
	 */
	public void testForCheck(Block[][] b) {
		if (isKingInCheck(chessboard, !isWhitesMove()))
			setCheck();
		else
			unsetCheck();
			
	}
	
	/**
	 * setCheck
	 * sets isInCheck to true and checks for checkmate
	 */
	public void setCheck() {
		isInCheck = true;
		System.out.println();
		if (!testForCheckmate())
			System.out.println("Check");
	}
	
	/**
	 * unsetCheck
	 * sets isInCheck to false and checks for stalemate
	 */
	public void unsetCheck() {
		isInCheck = false;
		testForStalemate();
	}
	
	/**
	 * isInCheck
	 * @return true if isInCheck, else false
	 */
	public boolean isInCheck() {
		return isInCheck;
	}
	
	/**
	 * isDone Accessor
	 * @return boolean
	 * 			true if game is done, else false
	 */
	public boolean isDone() {
		return isDone;
	}
	/**
	 * isStalemate Accessor
	 * @return	boolean
	 * 		true if game is stalemate, else false
	 */
	public boolean isStalemate() {
		return isStalemate;
	}
	/**
	 * isResign Accessor
	 * @return	boolean
	 * 	true if game is resign, else false
	 */
	public boolean isResign() {
		return isResign;
	}
	/**
	 * isWhiteWinner Accessor
	 * Might be unnecessary
	 * @return	boolean
	 * 	true if white won, else false
	 */
	public boolean isWhiteWinner() {
		return isWhiteWins;
	}
	/**
	 * isWhitesMove Accessor
	 * @return	boolean
	 * 		true if it is White's move, else false
	 */
	public boolean isWhitesMove() {
		return isWhitesMove;
	}
	
	/**
	 * isDrawAvailable
	 * @return boolean
	 * 		true if draw is available, else false
	 */
	public boolean isDrawAvailable() {
		return isDrawAvailable;
	}
	
	
	
	public boolean testForStalemate()
	{
		char currFile, currRank;
		for (int f = 0; f < 8; f++) {
			for (int r = 0; r < 8; r++) {
				currFile = (char) f;
				currFile += 'a';
				currRank = (char) r;
				currRank += 49;
				if (chessboard[f][r].getPiece() != null && chessboard[f][r].getPiece().isWhite() != isWhitesMove()) {
					if (hasValidMoves(currFile, currRank)) {
						return false;
					}
				}		
			}
		}
		
		System.out.println("Stalemate");
		isDone = true;
		
		return true;
	}
	
	/**
	 * testForCheckmate
	 * @return boolean
	 * true if checkmate, else false
	 */
	public boolean testForCheckmate() 
	{
		char currFile, currRank;
		for (int f = 0; f < 8; f++) {
			for (int r = 0; r < 8; r++) {
				currFile = (char) f;
				currFile += 'a';
				currRank = (char) r;
				currRank += 49;
				if (chessboard[f][r].getPiece() != null && chessboard[f][r].getPiece().isWhite() != isWhitesMove()) {
					if (hasValidMoves(currFile, currRank)) {
						return false;
					}
				}		
			}
		}
		
		if(isWhitesMove){
			isWhiteWins = true;
			System.out.println("Checkmate");
			System.out.println("White wins");
			isDone = true;
		}
		else{
			isWhiteWins = false;
			System.out.println("Checkmate");
			System.out.println("Black wins");
			isDone = true;
		}
		
		return true;
	}
	
	/**
	 * isKingInCheck
	 * @param b parses Block[][]
	 * @param isWhite is a boolean
	 * @return true if king is in check, false otherwise
	 */
	public boolean isKingInCheck(Block[][] b, boolean isWhite) 
	{
		String kingLoc = getKingLocation(b, isWhite);
		char currFile, currRank;
		String currInput = "";
		String testCheckInput = "";
		for (int f = 0; f < 8; f++) {
			for (int r = 0; r < 8; r++) {
				currFile = (char) f;
				currFile += 'a';
				currRank = (char) r;
				currRank += 49;
				currInput = currFile + "" + currRank + "";
				testCheckInput = currInput + " " + kingLoc;
				//System.out.println(testCheckInput);
				/* DO NOT LET isValidMove return true if location is king */
				if (b[f][r].getPiece() != null && !kingLoc.equals(currInput)) {
					if (b[f][r].getPiece().isValidMove(testCheckInput, b)) {
						return true;
					}
				}
			}
		}
		
		return false;
	}
	

	
	/**
	 * getKingLocation
	 * @param board Block[][]
	 * @param isWhite boolean
	 * @return king location of white king if isWhite is true, else black king
	 */
	public String getKingLocation(Block[][] board, boolean isWhite) {
		String location = "";
		char kingFile, kingRank;
		for (int f = 0; f < 8; f++) {
			for (int r = 0; r < 8; r++) {
				if (board[f][r].getPiece() != null 
						&& board[f][r].getPiece().isWhite() == isWhite
						&& board[f][r].getPiece() instanceof King) {
					kingFile = (char)(f + 'a');
					kingRank = (char)(r + 49);
					location = "" + kingFile + "" + kingRank;
					//return location;
				}
			}
		}
		return location;
	}
	
	/**
	 * hasValidMoves
	 * @param file char
	 * @param rank	char
	 * @return if piece at that location has valid moves, return true. Else, false
	 */
	public boolean hasValidMoves(char file, char rank) {
		char nextFile, nextRank;
		String nextInput = "";
		String currInput = file + "" + rank + " ";
		
		for (int f = 0; f < 8; f++) {
			for (int r = 0; r < 8; r++) {
				if (chessboard[f][r].getPiece() instanceof King)
					continue;
				nextFile = (char) f;
				nextFile += 'a';
				nextRank = (char) r;
				nextRank += 49;
				nextInput = nextFile + "" + nextRank + "";
				if (chessboard[file-'a'][rank-49].getPiece().isValidMove(currInput + nextInput, chessboard)) {
					Block[][] clone = createClone(chessboard);
					clone = clone[file-'a'][rank-49].getPiece().move(currInput + nextInput, clone);
					if (!isKingInCheck(clone, !isWhitesMove()) && !isKingInCheck(clone, isWhitesMove())) {
						//System.out.println(currInput + nextInput);
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	/**
	 * createClone
	 * @param board Block[][]
	 * @return a deep clone of board, for checking moves
	 */
	public Block[][] createClone(Block[][] board) {
		Block[][] clone = new Block[8][8];
		
		for (int f = 0; f < 8; f++) {
			for (int r = 0; r < 8; r++) {
				try {
					clone[f][r] = (Block) board[f][r].clone();
				} catch (CloneNotSupportedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		return clone;
	}

}
