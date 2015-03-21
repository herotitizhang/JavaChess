package chessgame;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayer;
import javax.swing.JPanel;
import javax.swing.plaf.LayerUI;

// TODO add the libraries physically into the folder
public class GameInterface extends JFrame{
	
	public GameInterface() {

//		ChessBoard chessBoard = new ChessBoard();
//		LayerUI<JLabel> chessBoardUI = new ChessBoardUI(); // <JLabel> because ChessBoard extends JLabel
//		JLayer<JLabel> chessBoardUILayer = new JLayer<JLabel>(chessBoard, chessBoardUI);
//		this.add(chessBoardUILayer);
		
//		this.setLayout();
	
		
		this.setContentPane(new InitialPanel());
		
		
		
		
		// switch to a chessboard TODO to be moved to somewhere else
		ChessBoard chessBoard = new ChessBoard();
		JPanel chessBoardContainer = new JPanel(new BorderLayout());
		chessBoardContainer.add(chessBoard, BorderLayout.WEST);
		this.setContentPane(chessBoardContainer);
		
		
		
		
		
		// set up the frame
		Dimension dim = Toolkit. getDefaultToolkit().getScreenSize();
		this.setSize(ChessConstants.GAME_INTERFACE_WIDTH, ChessConstants.GAME_INTERFACE_HGIGHT);
		this.setLocation(dim.width /2-this.getSize().width/2, dim.height/2-this .getSize().height/2);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
	}
	
	public static void main(String[] args) {
		GameInterface gi = new GameInterface();
	}

}
