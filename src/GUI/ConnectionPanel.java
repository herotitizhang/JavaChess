package GUI;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class ConnectionPanel extends JPanel {

	public ConnectionPanel() {
		setLayout(new GridLayout(3, 1));

		JLabel enterIP = new JLabel("Please enter the IP address",SwingConstants.CENTER);
		enterIP.setFont(new Font(Font.SERIF, Font.BOLD, 35));
		add(enterIP);

		JLabel ip = new JLabel("The IP address is...");
		add(ip);

		JButton btnNewButton = new JButton("Cancel");
		add(btnNewButton);
	}
	
	
	// for testing only
	public static void main (String args[]) {
		JFrame jf = new JFrame();
		jf.add(new ConnectionPanel());
		jf.setSize(ChessConstants.GAME_INTERFACE_WIDTH, ChessConstants.GAME_INTERFACE_HGIGHT);
		jf.setLocation(400, 500);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setVisible(true);
		jf.setResizable(false);
	}
}
