package backend;

import java.awt.Image;

import GUI.ChessBoard;
import GUI.ChessConstants;
import Utilities.IOSystem;

public class ChessPiece {
	
//	MY_KING, MY_QUEEN, MY_KNIGHT, MY_BISHOP, MY_ROOK, MY_PAWN,
//	ENEMY_KING, ENEMY_QUEEN, ENEMY_KNIGHT, ENEMY_BISHOP, ENEMY_ROOK, ENEMY_PAWN;
	
	private boolean hasBeenMoved = false; // check if the chess has been moved.
	private boolean isEnemy = false; // it can not be controlled by the player if it's an enemy piece
	private boolean isLight = false;
	private ChessType type = null;
	private Image image = null; // save it in the memory to avoid disk access
	
	public ChessPiece(ChessType type, boolean isEnemy, boolean isLight) { // isLight == moveFirst
		this.isEnemy = isEnemy;
		this.type = type;
		this.isLight = isLight;
		if (isLight) {
			if (this.type == ChessType.KING) {
				this.image = IOSystem.getScaledImage(
						ChessBoard.class.getResource(ChessConstants.CLASSIC_LIGHT_KING), 
						(int)ChessConstants.CLASSIC_CHESSBOARD_GRID_WIDTH, (int)ChessConstants.CLASSIC_CHESSBOARD_GRID_WIDTH);
			} else if (this.type == ChessType.QUEEN) {
				this.image = IOSystem.getScaledImage(
						ChessBoard.class.getResource(ChessConstants.CLASSIC_LIGHT_QUEEN), 
						(int)ChessConstants.CLASSIC_CHESSBOARD_GRID_WIDTH, (int)ChessConstants.CLASSIC_CHESSBOARD_GRID_WIDTH);
			} else if (this.type == ChessType.KNIGHT) {
				this.image = IOSystem.getScaledImage(
						ChessBoard.class.getResource(ChessConstants.CLASSIC_LIGHT_KNIGHT), 
						(int)ChessConstants.CLASSIC_CHESSBOARD_GRID_WIDTH, (int)ChessConstants.CLASSIC_CHESSBOARD_GRID_WIDTH);
			} else if (this.type == ChessType.BISHOP) {
				this.image = IOSystem.getScaledImage(
						ChessBoard.class.getResource(ChessConstants.CLASSIC_LIGHT_BISHOP), 
						(int)ChessConstants.CLASSIC_CHESSBOARD_GRID_WIDTH, (int)ChessConstants.CLASSIC_CHESSBOARD_GRID_WIDTH);
			} else if (this.type == ChessType.ROOK) {
				this.image = IOSystem.getScaledImage(
						ChessBoard.class.getResource(ChessConstants.CLASSIC_LIGHT_ROOK), 
						(int)ChessConstants.CLASSIC_CHESSBOARD_GRID_WIDTH, (int)ChessConstants.CLASSIC_CHESSBOARD_GRID_WIDTH);
			} else if (this.type == ChessType.PAWN) {
				this.image = IOSystem.getScaledImage(
						ChessBoard.class.getResource(ChessConstants.CLASSIC_LIGHT_PAWN), 
						(int)ChessConstants.CLASSIC_CHESSBOARD_GRID_WIDTH, (int)ChessConstants.CLASSIC_CHESSBOARD_GRID_WIDTH);
			}
		} else {
			if (this.type == ChessType.KING) {
				this.image = IOSystem.getScaledImage(
						ChessBoard.class.getResource(ChessConstants.CLASSIC_DARK_KING), 
						(int)ChessConstants.CLASSIC_CHESSBOARD_GRID_WIDTH, (int)ChessConstants.CLASSIC_CHESSBOARD_GRID_WIDTH);
			} else if (this.type == ChessType.QUEEN) {
				this.image = IOSystem.getScaledImage(
						ChessBoard.class.getResource(ChessConstants.CLASSIC_DARK_QUEEN), 
						(int)ChessConstants.CLASSIC_CHESSBOARD_GRID_WIDTH, (int)ChessConstants.CLASSIC_CHESSBOARD_GRID_WIDTH);
			} else if (this.type == ChessType.KNIGHT) {
				this.image = IOSystem.getScaledImage(
						ChessBoard.class.getResource(ChessConstants.CLASSIC_DARK_KNIGHT), 
						(int)ChessConstants.CLASSIC_CHESSBOARD_GRID_WIDTH, (int)ChessConstants.CLASSIC_CHESSBOARD_GRID_WIDTH);
			} else if (this.type == ChessType.BISHOP) {
				this.image = IOSystem.getScaledImage(
						ChessBoard.class.getResource(ChessConstants.CLASSIC_DARK_BISHOP), 
						(int)ChessConstants.CLASSIC_CHESSBOARD_GRID_WIDTH, (int)ChessConstants.CLASSIC_CHESSBOARD_GRID_WIDTH);
			} else if (this.type == ChessType.ROOK) {
				this.image = IOSystem.getScaledImage(
						ChessBoard.class.getResource(ChessConstants.CLASSIC_DARK_ROOK), 
						(int)ChessConstants.CLASSIC_CHESSBOARD_GRID_WIDTH, (int)ChessConstants.CLASSIC_CHESSBOARD_GRID_WIDTH);
			} else if (this.type == ChessType.PAWN) {
				this.image = IOSystem.getScaledImage(
						ChessBoard.class.getResource(ChessConstants.CLASSIC_DARK_PAWN), 
						(int)ChessConstants.CLASSIC_CHESSBOARD_GRID_WIDTH, (int)ChessConstants.CLASSIC_CHESSBOARD_GRID_WIDTH);
			}
		}
	}
	
	public boolean isEnemy() {
		return isEnemy;
	}
	
	public Image getImage() {
		return image;
	}
	
	public void setImage(Image image) {
		this.image = image;
	}
	
	public boolean isLight() {
		return isLight;
	}

	public ChessType getType() {
		return type;
	}

	public void setType(ChessType type) {
		this.type = type;
	}

	public boolean isHasBeenMoved() {
		return hasBeenMoved;
	}

	public void setHasBeenMoved(boolean hasBeenMoved) {
		this.hasBeenMoved = hasBeenMoved;
	}
	
	public enum ChessType {
		KING, QUEEN, KNIGHT, BISHOP, ROOK, PAWN;
	}
}
