package GUI;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import networkCommunication.NetworkCommunicator;
import Utilities.CountDownTimer;
import Utilities.IOSystem;
import Utilities.StringUtils;


//TODO too ugly
public class InitialPanel extends JPanel{

	GameInterface topFrame;
	JLabel prompt;
	JButton connectorButton, waiterButton;
	
	public InitialPanel(GameInterface gameInterface) {
		topFrame = gameInterface;
		
		prompt = new JLabel("<html>Do you want to connect to your opponent's machine <br>or wait to be connected by your opponent?</html>");
		prompt.setFont(new Font("Arial", Font.BOLD, 20));
		prompt.setBorder(new EmptyBorder(8, 40, 8, 0));
		
		connectorButton = new JButton("<html>&nbsp;Connect<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;the<br> opponent</html>");
		connectorButton.setFont(new Font("Arial", Font.BOLD, 30));
		connectorButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				// get the IP
				String ipAddress = JOptionPane.showInputDialog("Please enter the IP address of your opponent's machine.");
				
				if ((ipAddress != null) && (ipAddress.length() > 0)) { //OK button pressed and something is filled in
					if (!StringUtils.validateIP(ipAddress)){
						JOptionPane.showMessageDialog(topFrame, "Invalid IP address!");
						return;
					}
					
					// max weight: 5 seconds
					Socket socket = NetworkCommunicator.connect(ipAddress);
					
					if (socket == null) {
						JOptionPane.showMessageDialog(topFrame, "Connection failed!");
					} else {
						topFrame.setSocket(socket);
						topFrame.launchChessBoardPanel(false); // use black chess pieces
						// switch to the chessboard
						((CardLayout) (topFrame.getMainPanel().getLayout() ) ).show(topFrame.getMainPanel(), ChessConstants.CHESSBOARDPANEL);
					}
					
				} // else : Cancel button was pressed or OK button pressed and nothing was filled in. Do nothing

			}
			
		});
		
		waiterButton = new JButton("<html>&nbsp;Wait for<br> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;your<br> opponent</html>");
		waiterButton.setFont(new Font("Arial", Font.BOLD,30));
		waiterButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String ipAddress = ""; 
				try {
//				ipAddress = IOSystem.getPublicIPAddress(); // TODO may need to implement a server
					ipAddress = InetAddress.getLocalHost().getHostAddress();
				} catch (UnknownHostException e1) {
					e1.printStackTrace();
				} 
				String secs = JOptionPane.showInputDialog("Your IP address is "+ipAddress+". Please specify how long you want to wait for incoming clients.");
				
				if ((secs != null)) { //OK button pressed and something is filled in
					if (!StringUtils.isInteger(secs) ) { // the 2 last checks are for the second dialog
						JOptionPane.showMessageDialog(topFrame, "That's not a valid number!");
						return;
					}
					
					Socket socket = NetworkCommunicator.getConnected(Integer.parseInt(secs));
					if (socket == null) {
						JOptionPane.showMessageDialog(topFrame, "Connection failed!");
						return;
					} else {
						topFrame.setSocket(socket);
						topFrame.launchChessBoardPanel(true);
						// switch to the chessboard
						((CardLayout) (topFrame.getMainPanel().getLayout() ) ).show(topFrame.getMainPanel(), ChessConstants.CHESSBOARDPANEL);
						
						
					}
				} // else: Cancel button was pressed or OK button pressed and nothing was filled in. Do nothing

				
			}
			
		});
		
		this.setLayout(new GridLayout(2,1));
		this.add(prompt);
		JPanel buttonPanel = new JPanel(new GridLayout(1,2));
		buttonPanel.add(connectorButton);
		buttonPanel.add(waiterButton);
		this.add(buttonPanel);

	}
	
	
	
}
