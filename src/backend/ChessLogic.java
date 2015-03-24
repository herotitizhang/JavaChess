package backend;

// accepts the moves and check if they are valid (including hitting empty space)
// implements state pattern
public class ChessLogic {
	

	public ChessLogic() {
		
		
	}
	
	public static ChessPiece[][] initializePieces() {
		ChessPiece[][] board = new ChessPiece[8][8];
		
		board[0][0] = ChessPiece.ENEMY_ROOK;
		board[0][1] = ChessPiece.ENEMY_KNIGHT;
		board[0][2] = ChessPiece.ENEMY_BISHOP;
		board[0][3] = ChessPiece.ENEMY_QUEEN;
		board[0][4] = ChessPiece.ENEMY_KING;
		board[0][5] = ChessPiece.ENEMY_KNIGHT;
		board[0][6] = ChessPiece.ENEMY_BISHOP;
		board[0][7] = ChessPiece.ENEMY_ROOK;
		
		for (int i = 0; i < 8; i++) board[1][i] = ChessPiece.ENEMY_PAWN;

		for (int i = 2; i <= 5; i++) {
			for (int j = 0; j < 8; j++) {
				board[i][j] = null;
			}
		}
		
		for (int i = 0; i < 8; i++) board[6][i] = ChessPiece.MY_PAWN;
		
		board[7][0] = ChessPiece.MY_ROOK;
		board[7][1] = ChessPiece.MY_KNIGHT;
		board[7][2] = ChessPiece.MY_BISHOP;
		board[7][3] = ChessPiece.MY_QUEEN;
		board[7][4] = ChessPiece.MY_KING;
		board[7][5] = ChessPiece.MY_KNIGHT;
		board[7][6] = ChessPiece.MY_BISHOP;
		board[7][7] = ChessPiece.MY_ROOK;
		
		return board;
	}
}
