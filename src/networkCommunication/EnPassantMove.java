package networkCommunication;

import java.io.Serializable;

public class EnPassantMove implements Serializable {
	
	private int fromRow = -1, fromColumn = -1, toRow = -1, toColumn = -1,
			takenPieceRow = -1, takenPieceColumn = -1;
	
	public EnPassantMove(int fromRow, int fromColumn, int toRow, int toColumn,
			int takenPieceRow, int takenPieceColumn) {
		this.fromRow = fromRow;
		this.fromColumn = fromColumn;
		this.toRow = toRow;
		this.toColumn = toColumn;
		this.takenPieceRow = takenPieceRow;
		this.takenPieceColumn = takenPieceColumn;
	}
	
	public void translateToEnemyCoordinates() {
		fromRow = 7- fromRow;
		fromColumn = 7- fromColumn;
		toRow = 7- toRow;
		toColumn = 7- toColumn;
		takenPieceRow = 7- takenPieceRow;
		takenPieceColumn = 7- takenPieceColumn;
	}

	public int getFromRow() {
		return fromRow;
	}

	public void setFromRow(int fromRow) {
		this.fromRow = fromRow;
	}

	public int getFromColumn() {
		return fromColumn;
	}

	public void setFromColumn(int fromColumn) {
		this.fromColumn = fromColumn;
	}

	public int getToRow() {
		return toRow;
	}

	public void setToRow(int toRow) {
		this.toRow = toRow;
	}

	public int getToColumn() {
		return toColumn;
	}

	public void setToColumn(int toColumn) {
		this.toColumn = toColumn;
	}

	public int getTakenPieceRow() {
		return takenPieceRow;
	}

	public void setTakenPieceRow(int takenPieceRow) {
		this.takenPieceRow = takenPieceRow;
	}

	public int getTakenPieceColumn() {
		return takenPieceColumn;
	}

	public void setTakenPieceColumn(int takenPieceColumn) {
		this.takenPieceColumn = takenPieceColumn;
	}
}
