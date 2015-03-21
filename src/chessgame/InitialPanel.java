package chessgame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


//TODO too ugly
public class InitialPanel extends JPanel{

	JLabel prompt;
	JButton connectorButton, waiterButton;
	
	public InitialPanel() {
		prompt = new JLabel("<html>Do you want to connect to your opponent's machine <br>or wait to be connected by your opponent?</html>");
		prompt.setFont(new Font("Arial", Font.BOLD, 20));
		prompt.setBorder(new EmptyBorder(8, 40, 8, 0));
		
		connectorButton = new JButton("<html>&nbsp;Connect<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;the<br> opponent</html>");
		connectorButton.setFont(new Font("Arial", Font.BOLD, 30));
		
		waiterButton = new JButton("<html>&nbsp;Wait for<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;the<br> opponent</html>");
		waiterButton.setFont(new Font("Arial", Font.BOLD,30));
		
		
		this.setLayout(new GridLayout(2,1));
		this.add(prompt);
		JPanel buttonPanel = new JPanel(new GridLayout(1,2));
		buttonPanel.add(connectorButton);
		buttonPanel.add(waiterButton);
		this.add(buttonPanel);
//		this.add(connectorButton, BorderLayout.WEST);
//		this.add(waiterButton, BorderLayout.EAST);
//		this.add(null, BorderLayout.CENTER);
		
	}
	
}
