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
		ChessPiece myRook = new ChessPiece(ChessType.ROOK, true, meMoveFirst);
		ChessPiece myKnight = new ChessPiece(ChessType.KNIGHT, true, meMoveFirst);
		ChessPiece myBishop = new ChessPiece(ChessType.BISHOP, true, meMoveFirst);
		ChessPiece myQueen = new ChessPiece(ChessType.QUEEN, true, meMoveFirst);
		ChessPiece myKing = new ChessPiece(ChessType.KING, true, meMoveFirst);
		ChessPiece myPawn = new ChessPiece(ChessType.PAWN, true, meMoveFirst);

		
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
}
