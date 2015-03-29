package networkCommunication;

import java.io.Serializable;


public class Move implements Serializable {
	private int fromRow = -1, fromColumn = -1, toRow = -1, toColumn = -1;
	private String pawnPromoteType = null;
	private boolean checkingEnemy = false;
	
	public Move(int fromRow, int fromColumn, int toRow, int toColumn) {
		this.fromRow = fromRow;
		this.fromColumn = fromColumn;
		this.toRow = toRow;
		this.toColumn = toColumn;
	}
	
	public void pawnPromoteTo (String type) {
		pawnPromoteType = type;
	}
	
	public boolean isCheckingEnemy() {
		return checkingEnemy;
	}

	public int getFromRow() {
		return fromRow;
	}

	public int getFromColumn() {
		return fromColumn;
	}

	public int getToRow() {
		return toRow;
	}

	public int getToColumn() {
		return toColumn;
	}

	public String getPawnPromoteType() {
		return pawnPromoteType;
	}

	public void setCheckingEnemy() {
		this.checkingEnemy = true;
	}
}