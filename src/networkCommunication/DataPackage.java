package networkCommunication;

import java.io.Serializable;
import java.util.ArrayList;

public class DataPackage implements Serializable {
	
	private ArrayList<Move> moves = new ArrayList<Move>();
	private ArrayList<EnPassantMove> enPassantMoves = new ArrayList<EnPassantMove>();

	public ArrayList<Move> getMoves() {
		return moves;
	}

	public ArrayList<EnPassantMove> getEnPassantMoves() {
		return enPassantMoves;
	}
	
}
