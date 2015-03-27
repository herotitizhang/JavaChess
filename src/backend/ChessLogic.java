package backend;

import GUI.ChessBoard;
import GUI.ChessConstants;
import Utilities.IOSystem;
import backend.ChessPiece.ChessType;

// accepts the moves and check if they are valid (including hitting empty space)
// implements state pattern
public class ChessLogic {
	

	public ChessLogic() {
		
		
	}
	
	public static ChessPiece[][] initializePieces(boolean meMoveFirst) {
		ChessPiece[][] board = new ChessPiece[8][8];
		
		ChessPiece enemyRook1 = new ChessPiece(ChessType.ROOK, true, !meMoveFirst);
		ChessPiece enemyRook2 = new ChessPiece(ChessType.ROOK, true, !meMoveFirst);
		ChessPiece enemyKnight1 = new ChessPiece(ChessType.KNIGHT, true, !meMoveFirst);
		ChessPiece enemyKnight2 = new ChessPiece(ChessType.KNIGHT, true, !meMoveFirst);
		ChessPiece enemyBishop1 = new ChessPiece(ChessType.BISHOP, true, !meMoveFirst);
		ChessPiece enemyBishop2 = new ChessPiece(ChessType.BISHOP, true, !meMoveFirst);
		ChessPiece enemyQueen = new ChessPiece(ChessType.QUEEN, true, !meMoveFirst);
		ChessPiece enemyKing = new ChessPiece(ChessType.KING, true, !meMoveFirst);
		ChessPiece enemyPawn1 = new ChessPiece(ChessType.PAWN, true, !meMoveFirst);
		ChessPiece enemyPawn2 = new ChessPiece(ChessType.PAWN, true, !meMoveFirst);
		ChessPiece enemyPawn3 = new ChessPiece(ChessType.PAWN, true, !meMoveFirst);
		ChessPiece enemyPawn4 = new ChessPiece(ChessType.PAWN, true, !meMoveFirst);
		ChessPiece enemyPawn5 = new ChessPiece(ChessType.PAWN, true, !meMoveFirst);
		ChessPiece enemyPawn6 = new ChessPiece(ChessType.PAWN, true, !meMoveFirst);
		ChessPiece enemyPawn7 = new ChessPiece(ChessType.PAWN, true, !meMoveFirst);
		ChessPiece enemyPawn8 = new ChessPiece(ChessType.PAWN, true, !meMoveFirst);
		ChessPiece myRook1 = new ChessPiece(ChessType.ROOK, false, meMoveFirst);
		ChessPiece myRook2 = new ChessPiece(ChessType.ROOK, false, meMoveFirst);
		ChessPiece myKnight1 = new ChessPiece(ChessType.KNIGHT, false, meMoveFirst);
		ChessPiece myKnight2 = new ChessPiece(ChessType.KNIGHT, false, meMoveFirst);
		ChessPiece myBishop1 = new ChessPiece(ChessType.BISHOP, false, meMoveFirst);
		ChessPiece myBishop2 = new ChessPiece(ChessType.BISHOP, false, meMoveFirst);
		ChessPiece myQueen = new ChessPiece(ChessType.QUEEN, false, meMoveFirst);
		ChessPiece myKing = new ChessPiece(ChessType.KING, false, meMoveFirst);
		ChessPiece myPawn1 = new ChessPiece(ChessType.PAWN, false, meMoveFirst);
		ChessPiece myPawn2 = new ChessPiece(ChessType.PAWN, false, meMoveFirst);
		ChessPiece myPawn3 = new ChessPiece(ChessType.PAWN, false, meMoveFirst);
		ChessPiece myPawn4 = new ChessPiece(ChessType.PAWN, false, meMoveFirst);
		ChessPiece myPawn5 = new ChessPiece(ChessType.PAWN, false, meMoveFirst);
		ChessPiece myPawn6 = new ChessPiece(ChessType.PAWN, false, meMoveFirst);
		ChessPiece myPawn7 = new ChessPiece(ChessType.PAWN, false, meMoveFirst);
		ChessPiece myPawn8 = new ChessPiece(ChessType.PAWN, false, meMoveFirst);

		
		board[0][0] = enemyRook1;
		board[0][1] = enemyKnight1;
		board[0][2] = enemyBishop1;
		if (meMoveFirst) {
			board[0][3] = enemyQueen;
			board[0][4] = enemyKing;
		} else {
			board[0][3] = enemyKing;
			board[0][4] = enemyQueen;
		}
		board[0][5] = enemyBishop2;
		board[0][6] = enemyKnight2;
		board[0][7] = enemyRook2;
		
		board[1][0] = enemyPawn1;
		board[1][1] = enemyPawn2;
		board[1][2] = enemyPawn3;
		board[1][3] = enemyPawn4;
		board[1][4] = enemyPawn5;
		board[1][5] = enemyPawn6;
		board[1][6] = enemyPawn7;
		board[1][7] = enemyPawn8;

		for (int i = 2; i <= 5; i++) {
			for (int j = 0; j < 8; j++) {
				board[i][j] = null;
			}
		}
		
		board[6][0] = myPawn1;
		board[6][1] = myPawn2;
		board[6][2] = myPawn3;
		board[6][3] = myPawn4;
		board[6][4] = myPawn5;
		board[6][5] = myPawn6;
		board[6][6] = myPawn7;
		board[6][7] = myPawn8;
		
		board[7][0] = myRook1;
		board[7][1] = myKnight1;
		board[7][2] = myBishop1;
		if (meMoveFirst) {
			board[7][3] = myQueen;
			board[7][4] = myKing;
		} else {
			board[7][3] = myKing;
			board[7][4] = myQueen;
		}
		board[7][5] = myBishop2;
		board[7][6] = myKnight2;
		board[7][7] = myRook2;
		
		return board;
	}
	
	public static void promotePawn(ChessPiece chessPiece, String newPiece) {
		if (chessPiece.getType() != ChessType.PAWN) {
			return;
		}
		
		if (newPiece.equals("Queen")) {
			chessPiece.setType(ChessType.QUEEN);
			chessPiece.setImage(IOSystem.getScaledImage(
				ChessBoard.class.getResource(ChessConstants.CLASSIC_LIGHT_QUEEN), 
				(int)ChessConstants.CLASSIC_CHESSBOARD_GRID_WIDTH, (int)ChessConstants.CLASSIC_CHESSBOARD_GRID_WIDTH));
		} else if (newPiece.equals("Rook")) {
			chessPiece.setType(ChessType.ROOK);
			chessPiece.setImage(IOSystem.getScaledImage(
				ChessBoard.class.getResource(ChessConstants.CLASSIC_LIGHT_ROOK), 
				(int)ChessConstants.CLASSIC_CHESSBOARD_GRID_WIDTH, (int)ChessConstants.CLASSIC_CHESSBOARD_GRID_WIDTH));
		} else if (newPiece.equals("Bishop")) {
			chessPiece.setType(ChessType.BISHOP);
			chessPiece.setImage(IOSystem.getScaledImage(
				ChessBoard.class.getResource(ChessConstants.CLASSIC_LIGHT_BISHOP), 
				(int)ChessConstants.CLASSIC_CHESSBOARD_GRID_WIDTH, (int)ChessConstants.CLASSIC_CHESSBOARD_GRID_WIDTH));
		} else if (newPiece.equals("Knight")) {
			chessPiece.setType(ChessType.KNIGHT);
			chessPiece.setImage(IOSystem.getScaledImage(
				ChessBoard.class.getResource(ChessConstants.CLASSIC_LIGHT_KNIGHT), 
				(int)ChessConstants.CLASSIC_CHESSBOARD_GRID_WIDTH, (int)ChessConstants.CLASSIC_CHESSBOARD_GRID_WIDTH));
		}
	}
	
	public static boolean castlingIsAllowed(ChessPiece[][] chessBoard, int startRow, int startColumn, int toRow, int toColumn) {
		ChessPiece startPiece = chessBoard[startRow][startColumn];
		ChessPiece destinationPiece = chessBoard[toRow][toColumn];
		int kingRow = -1, kingColumn = -1;
		
		if (startPiece == null || destinationPiece == null) return false; 
		
		// rue1: neither piece has been moved before
		if (startPiece.isHasBeenMoved() || destinationPiece.isHasBeenMoved()) return false;
		
		// make sure one is rook and the other is king
		if (startPiece.getType() == ChessType.ROOK) { // selected rook, destination is not king
			if (destinationPiece.getType() != ChessType.KING) {
				return false;
			} else {
				kingRow = toRow;
				kingColumn = toColumn;
			}
		} else if (startPiece.getType() == ChessType.KING)  { // selected king, destination is not rook
			if (destinationPiece.getType() != ChessType.ROOK) {
				return false;
			} else {
				kingRow = toRow;
				kingColumn = toColumn;
			}
		} else { // the selected piece is not a rook or a king
			return false;
		}
		
		// rule2: no piece in between
		// rule3: the path is not under attack
		if (startColumn < toColumn) {
			
			for (int i = startColumn; i<= toColumn; i++) {
				// no piece in between
				if (i != startColumn && i != toColumn && chessBoard[startRow][i] != null) {
					return false;
				}
				
				for (int m = 0; m < 7; m++) {
					for (int n = 0; n < 7; n++) {
						ChessPiece chessPiece = chessBoard[m][n];
						if (chessPiece != null && chessPiece.isEnemy()) {
							if (validateChessPieceMovement(chessBoard, m, n, startRow, i)){
								return false;
							}
								
						}
					}
				}
			}
			
		} else {
			for (int i = startColumn-1; i > toColumn; i--) {
				// no piece in between
				if (i != startColumn && i != toColumn && chessBoard[startRow][i] != null) {
					return false;
				}
				
				for (int m = 0; m < 7; m++) {
					for (int n = 0; n < 7; n++) {
						ChessPiece chessPiece = chessBoard[m][n];
						if (chessPiece != null && chessPiece.isEnemy()) {
							if (validateChessPieceMovement(chessBoard, m, n, startRow, i)){
								return false;
							}
								
						}
					}
				}
				
			}
		}
		
		return true;
	}
	
	public static boolean checkingEnemy(ChessPiece[][] chessBoard, int row, int column) {
		int enemyKingRow = -1, enemyKingColumn = -1;
		
		outerloop:
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 7; j++) {
				ChessPiece chessPiece = chessBoard[i][j];
				if (chessPiece != null && chessPiece.getType() == ChessType.KING && chessPiece.isEnemy()) {
					enemyKingRow = i;
					enemyKingColumn = j;
					break outerloop;
				}
			}
		}
		
		if (enemyKingRow == -1 || enemyKingColumn == -1) return false;
		
		return validateChessPieceMovement(chessBoard, row, column, enemyKingRow, enemyKingColumn);
	}
	
	/**
	 *  assume there is no out-of-bound movement
	 *  assume a chess piece has been selected (chessBoard[startRow][startColumn] != null)
	 *  can be used to check both the opponent and player's own movement
	 *  does not check castling
	 * @param chessBoard
	 * @param startRow
	 * @param startColumn
	 * @param toRow
	 * @param toColumn
	 * @return
	 */
	public static boolean validateChessPieceMovement(ChessPiece[][] chessBoard, int startRow, int startColumn, int toRow, int toColumn) {
		
		// check if the destination is my own piece 
		ChessPiece startChess = chessBoard[startRow][startColumn];
		ChessPiece destination = chessBoard[toRow][toColumn];
		if (destination != null && (startChess.isEnemy() == destination.isEnemy())) return false;
		
		// check each condition
		if (chessBoard[startRow][startColumn].getType() == ChessType.ROOK) {
			return validateRookMovement(chessBoard, startRow, startColumn, toRow, toColumn);
		} else if (chessBoard[startRow][startColumn].getType() == ChessType.KNIGHT) {
			return validateKnightMovement(startRow, startColumn, toRow, toColumn);
		} else if (chessBoard[startRow][startColumn].getType() == ChessType.BISHOP) {
			return validateBishopMovement(chessBoard, startRow, startColumn, toRow, toColumn);
		} else if (chessBoard[startRow][startColumn].getType() == ChessType.PAWN) {
			return validatePawnMovement(chessBoard, startRow, startColumn, toRow, toColumn);
		} else if (chessBoard[startRow][startColumn].getType() == ChessType.KING) {
			return validateKingMovement(startRow, startColumn, toRow, toColumn);
		} else if (chessBoard[startRow][startColumn].getType() == ChessType.QUEEN) {
			return validateQueenMovement(chessBoard, startRow, startColumn, toRow, toColumn);
		}
		
		System.out.println("Weird case in validateChessPiece()");
		return false;
	}
	
	private static boolean validateRookMovement(ChessPiece[][] chessBoard, int startRow, int startColumn, int toRow, int toColumn) {
		// check if the rook goes straight
		if (startRow != toRow && startColumn != toColumn) return false;
		
		// check if there is any chess piece in the way
		if (startRow == toRow) {
			if (startColumn < toColumn) {
				for (int i = startColumn+1; i < toColumn; i++) {
					if (chessBoard[startRow][i] != null) {
						return false;
					}
				}
			} else {
				for (int i = startColumn-1; i > toColumn; i--) {
					if (chessBoard[startRow][i] != null) {
						return false;
					}
				}
			}
		} else if (startColumn == toColumn) {
			if (startRow < toRow) {
				for (int i = startRow+1; i < toRow; i++) {
					if (chessBoard[i][startColumn] != null) {
						return false;
					}
				}
			} else {
				for (int i = startRow-1; i > toRow; i--) {
					if (chessBoard[i][startColumn] != null) {
						return false;
					}
				}
			}
		}
		
		return true;
	}
	
	private static boolean validateKnightMovement(int startRow, int startColumn, int toRow, int toColumn) {
		
		if (Math.abs(startRow - toRow) == 1) {
			return Math.abs(startColumn - toColumn) == 2;
		} else if (Math.abs(startRow - toRow) == 2) {
			return Math.abs(startColumn - toColumn) == 1;
		}
		return false;
	}
	
	private static boolean validateBishopMovement(ChessPiece[][] chessBoard, int startRow, int startColumn, int toRow, int toColumn) {
		
		int rowGap = Math.abs(startRow - toRow); // can be used in other places if passed the moving-diagonally test
		
		// check if the bishop goes diagonally
		if (rowGap != Math.abs(startColumn - toColumn) ) {
			return false;
		}
		
		// check if there is any chess piece in the way
		boolean forward; // determine if the movement is / or \
		int smallerRow, smallerColumn, biggerColumn = -1;

		if (startRow < toRow) {
			smallerRow = startRow;
			if (startColumn < toColumn) {
				forward = false; // \ 
				smallerColumn = startColumn;
				biggerColumn = toColumn;
			} else {
				forward = true; // /
				smallerColumn = toColumn;
				biggerColumn = startColumn;
			}
		} else {
			smallerRow = toRow;
			if (startColumn < toColumn) {
				forward = true; // \ 
				smallerColumn = startColumn;
				biggerColumn = toColumn;
			} else {
				forward = false; // /
				smallerColumn = toColumn;
				biggerColumn = startColumn;
			}
		}
		
		if (forward) {
			for (int i = 1; i < rowGap; i++) {
				if (chessBoard[smallerRow+i][biggerColumn-i] != null) {
					return false;
				}
			}
		} else {
			for (int i = 1; i < rowGap; i++) {
				if (chessBoard[smallerRow+i][smallerColumn+i] != null) {
					return false;
				}
			}
		}
		
		return true;
	}	
	
	private static boolean validatePawnMovement(ChessPiece[][] chessBoard, int startRow, int startColumn, int toRow, int toColumn) {
		
		// does not take any enemy piece
		if (chessBoard[toRow][toColumn] == null) {
			if (startColumn != toColumn) { // not moving straight
				return false;
			} else {
				if (chessBoard[startRow][startColumn].isHasBeenMoved()) {
					return (startRow - toRow == 1); // can only move forward one grid
				} else {
					return (startRow - toRow == 1 || startRow - toRow == 2); // can move forward two grids in the very beginning
				}
			}
		} else { // take away an enemy piece in a diagonal direction
			return (startRow - toRow == 1 && Math.abs(toColumn - startColumn) == 1);
		}
		
	}	
	
	private static boolean validateKingMovement(int startRow, int startColumn, int toRow, int toColumn) {
		if (Math.abs(toRow-startRow) <= 1 &&  Math.abs(toColumn-startColumn) <= 1) {
			return true;
		}
		return false;
	}
	
	private static boolean validateQueenMovement(ChessPiece[][] chessBoard, int startRow, int startColumn, int toRow, int toColumn) {
		return (validateRookMovement(chessBoard, startRow, startColumn, toRow, toColumn) ||
				validateBishopMovement(chessBoard, startRow, startColumn, toRow, toColumn));
	}	
	
	
	
}
