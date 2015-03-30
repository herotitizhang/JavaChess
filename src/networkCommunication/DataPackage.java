package networkCommunication;

import java.io.Serializable;
import java.util.ArrayList;


// Note: all coordinates are from sender's point of view, so they need to be translated afterwards
public class DataPackage implements Serializable {
	
	private ArrayList<Move> moves = new ArrayList<Move>();
	private ArrayList<EnPassantMove> enPassantMoves = new ArrayList<EnPassantMove>();

	// indicates the coordinate of the lost pawn due to en passant. 
	// the pawn should be taken out of the chessboard
	private int enPassantPawnRow = -1, enPassantPawnColumn = -1;
	
	
	public ArrayList<Move> getMoves() {
		return moves;
	}

	public ArrayList<EnPassantMove> getEnPassantMoves() {
		return enPassantMoves;
	}

	public int getEnPassantPawnRow() {
		return enPassantPawnRow;
	}

	public void setEnPassantPawnRow(int enPassantPawnRow) {
		this.enPassantPawnRow = enPassantPawnRow;
	}

	public int getEnPassantPawnColumn() {
		return enPassantPawnColumn;
	}

	public void setEnPassantPawnColumn(int enPassantPawnColumn) {
		this.enPassantPawnColumn = enPassantPawnColumn;
	}
	
}
