package backend;

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
	
	// assume there is no out-of-bound movement
	public static boolean validateChessPiece(ChessPiece[][] chessBoard, int startRow, int startColumn, int toRow, int toColumn) {
		
		// check if the destination is my own piece 
		ChessPiece destination = chessBoard[toRow][toColumn];
		if (destination != null && !destination.isEnemy()) return false;
		
		// check each condition
		if (chessBoard[startRow][startColumn].getType() == ChessType.ROOK) {
			return validateRook(chessBoard, startRow, startColumn, toRow, toColumn);
		} else if (chessBoard[startRow][startColumn].getType() == ChessType.KNIGHT) {
			return validateKnight(startRow, startColumn, toRow, toColumn);
		} else if (chessBoard[startRow][startColumn].getType() == ChessType.BISHOP) {
			return validateBishop(chessBoard, startRow, startColumn, toRow, toColumn);
		} else if (chessBoard[startRow][startColumn].getType() == ChessType.PAWN) {
			return validatePawn(chessBoard, startRow, startColumn, toRow, toColumn);
		} else if (chessBoard[startRow][startColumn].getType() == ChessType.KING) {
			return validateKing(startRow, startColumn, toRow, toColumn);
		} else if (chessBoard[startRow][startColumn].getType() == ChessType.QUEEN) {
			return validateQueen(chessBoard, startRow, startColumn, toRow, toColumn);
		}
		
		System.out.println("Weird case in validateChessPiece()");
		return false;
	}
	
	private static boolean validateRook(ChessPiece[][] chessBoard, int startRow, int startColumn, int toRow, int toColumn) {
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
	
	private static boolean validateKnight(int startRow, int startColumn, int toRow, int toColumn) {
		
		if (Math.abs(startRow - toRow) == 1) {
			return Math.abs(startColumn - toColumn) == 2;
		} else if (Math.abs(startRow - toRow) == 2) {
			return Math.abs(startColumn - toColumn) == 1;
		}
		return false;
	}
	
	private static boolean validateBishop(ChessPiece[][] chessBoard, int startRow, int startColumn, int toRow, int toColumn) {
		
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
	
	private static boolean validatePawn(ChessPiece[][] chessBoard, int startRow, int startColumn, int toRow, int toColumn) {
		
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
			
		}
		
		
		return true;
	}	
	
	private static boolean validateKing(int startRow, int startColumn, int toRow, int toColumn) {
		if (Math.abs(toRow-startRow) <= 1 &&  Math.abs(toColumn-startColumn) <= 1) {
			return true;
		}
		return false;
	}
	
	private static boolean validateQueen(ChessPiece[][] chessBoard, int startRow, int startColumn, int toRow, int toColumn) {
		return (validateRook(chessBoard, startRow, startColumn, toRow, toColumn) ||
				validateBishop(chessBoard, startRow, startColumn, toRow, toColumn));
	}	
	
	
	
}
