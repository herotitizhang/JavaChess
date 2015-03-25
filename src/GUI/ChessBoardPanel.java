package GUI;

import java.awt.BorderLayout;

import javax.swing.JPanel;

public class ChessBoardPanel extends JPanel{
	
	private ChessBoard chessBoard;
	
	public void initializePanel(boolean moveFirst) {
		// switch to a chessboard TODO to be moved to somewhere else
		this.setLayout(new BorderLayout());
		chessBoard = new ChessBoard(moveFirst);
		this.add(chessBoard, BorderLayout.WEST);
	}

}
