package GUI;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class GameInterface extends JFrame{
	
	private Socket socket = null;
	private JPanel mainPanel = null;
	private InitialPanel initialPanel = null;
	private ChessBoardPanel chessBoardPanel = null;

	public GameInterface() {
		
		//  add all panels
		mainPanel = new JPanel(new CardLayout());
		initialPanel = new InitialPanel(this);
		mainPanel.add(initialPanel, ChessConstants.INITIALPANEL);
		chessBoardPanel = new ChessBoardPanel();
		mainPanel.add(chessBoardPanel, ChessConstants.CHESSBOARDPANEL);
		this.add(mainPanel);
		
//		launchInitialPanel();
		launchChessBoardPanel(true);
		
		
		// set up the frame
		Dimension dim = Toolkit. getDefaultToolkit().getScreenSize();
		this.setSize(ChessConstants.GAME_INTERFACE_WIDTH, ChessConstants.GAME_INTERFACE_HGIGHT);
		this.setLocation(dim.width /2-this.getSize().width/2, dim.height/2-this .getSize().height/2);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		this.setResizable(false);
	}
	
	public void launchInitialPanel() {
		((CardLayout)mainPanel.getLayout()).show(mainPanel, ChessConstants.INITIALPANEL);
	}
	
	public void launchChessBoardPanel(boolean moveFirst) {
		chessBoardPanel.initializePanel(moveFirst);
		((CardLayout)mainPanel.getLayout()).show(mainPanel, ChessConstants.CHESSBOARDPANEL);
	}
	
	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	
	public JPanel getMainPanel() {
		return mainPanel;
	}
	
	public static void main(String[] args) {
		GameInterface gi = new GameInterface();
	}

}
