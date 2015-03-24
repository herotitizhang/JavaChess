package GUI;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;

import javax.swing.JButton;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.JLabel;

/**
 * 
 * @author Tony Zhang
 * TODO add comments
 */
public class WaitForConnectionPanel extends JPanel{
	
	
	// test panel
	public WaitForConnectionPanel() {
		setLayout(new GridLayout(3, 1));
		
		JLabel waiting = new JLabel("Waiting...",SwingConstants.CENTER);
		waiting.setFont(new Font(Font.SERIF, Font.BOLD, 80));
		add(waiting);
		
		JLabel ip = new JLabel("The IP address is...");
		add(ip);
		
		JButton btnNewButton = new JButton("Cancel");
		add(btnNewButton);
	}
	
	/*
	public ConnectionPanel(State state) {
		
		
		if (state==State.CONNECTING) {
			
		} else if (state==State.WAITING) {
			
		}
	}
	*/
	
	// for testing only
	public static void main (String args[]) {
		JFrame jf = new JFrame();
		jf.add(new WaitForConnectionPanel());
		jf.setSize(ChessConstants.GAME_INTERFACE_WIDTH, ChessConstants.GAME_INTERFACE_HGIGHT);
		jf.setLocation(400, 500);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setVisible(true);
		jf.setResizable(false);
	}
}
