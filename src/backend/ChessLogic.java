package backend;

import backend.ChessPiece.ChessType;

// accepts the moves and check if they are valid (including hitting empty space)
// implements state pattern
public class ChessLogic {
	

	public ChessLogic() {
		
		
	}
	
	public static ChessPiece[][] initializePieces(boolean meMoveFirst) {
		ChessPiece[][] board = new ChessPiece[8][8];
		
		ChessPiece enemyRook = new ChessPiece(ChessType.ROOK, true, !meMoveFirst);
		ChessPiece enemyKnight = new ChessPiece(ChessType.KNIGHT, true, !meMoveFirst);
		ChessPiece enemyBishop = new ChessPiece(ChessType.BISHOP, true, !meMoveFirst);
		ChessPiece enemyQueen = new ChessPiece(ChessType.QUEEN, true, !meMoveFirst);
		ChessPiece enemyKing = new ChessPiece(ChessType.KING, true, !meMoveFirst);
		ChessPiece enemyPawn = new ChessPiece(ChessType.PAWN, true, !meMoveFirst);
		ChessPiece myRook = new ChessPiece(ChessType.ROOK, false, meMoveFirst);
		ChessPiece myKnight = new ChessPiece(ChessType.KNIGHT, false, meMoveFirst);
		ChessPiece myBishop = new ChessPiece(ChessType.BISHOP, false, meMoveFirst);
		ChessPiece myQueen = new ChessPiece(ChessType.QUEEN, false, meMoveFirst);
		ChessPiece myKing = new ChessPiece(ChessType.KING, false, meMoveFirst);
		ChessPiece myPawn = new ChessPiece(ChessType.PAWN, false, meMoveFirst);

		
		board[0][0] = enemyRook;
		board[0][1] = enemyKnight;
		board[0][2] = enemyBishop;
		if (meMoveFirst) {
			board[0][3] = enemyQueen;
			board[0][4] = enemyKing;
		} else {
			board[0][3] = enemyKing;
			board[0][4] = enemyQueen;
		}
		board[0][5] = enemyBishop;
		board[0][6] = enemyKnight;
		board[0][7] = enemyRook;
		
		for (int i = 0; i < 8; i++) board[1][i] = enemyPawn;

		for (int i = 2; i <= 5; i++) {
			for (int j = 0; j < 8; j++) {
				board[i][j] = null;
			}
		}
		
		for (int i = 0; i < 8; i++) board[6][i] = myPawn;
		
		board[7][0] = myRook;
		board[7][1] = myKnight;
		board[7][2] = myBishop;
		if (meMoveFirst) {
			board[7][3] = myQueen;
			board[7][4] = myKing;
		} else {
			board[7][3] = myKing;
			board[7][4] = myQueen;
		}
		board[7][5] = myBishop;
		board[7][6] = myKnight;
		board[7][7] = myRook;
		
		return board;
	}
	
	public static boolean validateChessPiece(ChessPiece[][] chessBoard, int startRow, int startColumn, int toRow, int toColumn) {
		
		if (chessBoard[startRow][startColumn].getType() == ChessType.ROOK) {
			return validateRook(chessBoard, startRow, startColumn, toRow, toColumn);
		} else if (chessBoard[startRow][startColumn].getType() == ChessType.KNIGHT) {
			return validateKnight(chessBoard, startRow, startColumn, toRow, toColumn);
		} else if (chessBoard[startRow][startColumn].getType() == ChessType.BISHOP) {
			return validateBishop(chessBoard, startRow, startColumn, toRow, toColumn);
		} else if (chessBoard[startRow][startColumn].getType() == ChessType.PAWN) {
			return validatePawn(chessBoard, startRow, startColumn, toRow, toColumn);
		} else if (chessBoard[startRow][startColumn].getType() == ChessType.KING) {
			return validateKing(chessBoard, startRow, startColumn, toRow, toColumn);
		} else if (chessBoard[startRow][startColumn].getType() == ChessType.QUEEN) {
			return validateQueen(chessBoard, startRow, startColumn, toRow, toColumn);
		}
		
		System.out.println("Weird case in validateChessPiece");
		return false;
	}
	
	private static boolean validateRook(ChessPiece[][] chessBoard, int startRow, int startColumn, int toRow, int toColumn) {
		// check if the rook goes straight
		if (startRow != toRow && startColumn != toColumn) return false;
		
		// check if the destination is my own piece 
		ChessPiece destination = chessBoard[toRow][toColumn];
		if (destination != null && !destination.isEnemy()) return false;
		
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
	
	private static boolean validateKnight(ChessPiece[][] chessBoard, int startRow, int startColumn, int toRow, int toColumn) {
		return false;
	}
	
	private static boolean validateBishop(ChessPiece[][] chessBoard, int startRow, int startColumn, int toRow, int toColumn) {
		return false;
	}	
	
	private static boolean validatePawn(ChessPiece[][] chessBoard, int startRow, int startColumn, int toRow, int toColumn) {
		return true;
	}	
	
	private static boolean validateKing(ChessPiece[][] chessBoard, int startRow, int startColumn, int toRow, int toColumn) {
		return false;
	}
	
	private static boolean validateQueen(ChessPiece[][] chessBoard, int startRow, int startColumn, int toRow, int toColumn) {
		return false;
	}	
	
	
	
}
