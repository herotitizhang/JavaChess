package GUI;

import java.awt.BorderLayout;

import javax.swing.JPanel;

public class ChessBoardPanel extends JPanel{
	
	public ChessBoardPanel() {
		// switch to a chessboard TODO to be moved to somewhere else
		ChessBoard chessBoard = new ChessBoard();
		JPanel chessBoardContainer = new JPanel(new BorderLayout());
		chessBoardContainer.add(chessBoard, BorderLayout.WEST);
	}

}
