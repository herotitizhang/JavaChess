package networkCommunication;

import java.io.Serializable;
import java.util.ArrayList;

public class DataPackage implements Serializable {
	
	private ArrayList<Move> moves = new ArrayList<Move>();

	public ArrayList<Move> getMoves() {
		return moves;
	}

	
}
