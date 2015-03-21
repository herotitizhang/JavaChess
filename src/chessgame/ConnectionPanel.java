package chessgame;

import javax.swing.JPanel;

/**
 * 
 * @author Tony Zhang
 * TODO add comments
 */
public class ConnectionPanel extends JPanel{
	
	public enum State{CONNECTING, WAITING};
	
	
	// test panel
	public ConnectionPanel() {
		
	}
	
	
	public ConnectionPanel(State state) {
		
		
		if (state==State.CONNECTING) {
			
		} else if (state==State.WAITING) {
			
		}
	}

}
