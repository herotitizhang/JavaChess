package GUI;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class GameInterface extends JFrame{
	
	private Socket socket;
	
	public GameInterface() {

//		ChessBoard chessBoard = new ChessBoard();
//		LayerUI<JLabel> chessBoardUI = new ChessBoardUI(); // <JLabel> because ChessBoard extends JLabel
//		JLayer<JLabel> chessBoardUILayer = new JLayer<JLabel>(chessBoard, chessBoardUI);
//		this.add(chessBoardUILayer);
		
//		this.setLayout();
	
		
		//TODO use cardlayout (look at tutorial)
		
		this.setContentPane(new InitialPanel(this));
		
		
		
		
		
//		this.setContentPane(chessBoardContainer);
		
		
		
		
		
		// set up the frame
		Dimension dim = Toolkit. getDefaultToolkit().getScreenSize();
		this.setSize(ChessConstants.GAME_INTERFACE_WIDTH, ChessConstants.GAME_INTERFACE_HGIGHT);
		this.setLocation(dim.width /2-this.getSize().width/2, dim.height/2-this .getSize().height/2);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
	}
	
	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	
	public static void main(String[] args) {
		GameInterface gi = new GameInterface();
	}

}
