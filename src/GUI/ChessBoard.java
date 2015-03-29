package GUI;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.net.URISyntaxException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import networkCommunication.DataPackage;
import networkCommunication.NetworkCommunicator;
import Utilities.IOSystem;
import backend.ChessLogic;
import backend.ChessPiece;
import backend.ChessPiece.ChessType;

/*
 * TODO ChessBoard should only be in charge of UI, sound effect, etc.
 */
public class ChessBoard extends JLabel implements MouseListener{
	
	// TODO change to private. public is only for testing
	private ChessPiece[][] board = null;
	
	// for interacting with the opponent
	private Socket socket = null;
	private MoveOpponentPieceTask moveOpponentPieceTask = null;
	
	// for chess piece movement
	private boolean chessPieceSelected = false; // indicates if one of the player's chess pieces is selected
	private boolean firstClick = true; // for putting a pointer
	private int selectedRow = -1, selectedColumn = -1;
	
	// put the following in the memory to avoid disk access
	private ImageIcon chessBoardImage = null;
	private Image pointerImage = null;
	
	public ChessBoard(boolean moveFirst, Socket socket) {
		super();
		
		// initialize chess pieces
		board = ChessLogic.initializePieces(moveFirst); 
		
		// set chessboard imageIcon
		chessBoardImage = new ImageIcon(IOSystem.getScaledImage(
				ChessBoard.class.getResource(ChessConstants.CLASSIC_CHESSBOARD), 
				ChessConstants.CLASSIC_CHESSBOARD_LENGTH, ChessConstants.CLASSIC_CHESSBOARD_LENGTH));
		this.setIcon(chessBoardImage);
		
		// set pointer imageIcon
		pointerImage = IOSystem.getScaledImage(
				ChessBoard.class.getResource(ChessConstants.POINTER), 
				(int)ChessConstants.CLASSIC_CHESSBOARD_GRID_WIDTH/2, (int)ChessConstants.CLASSIC_CHESSBOARD_GRID_WIDTH/2);
		
		// set up things for battling an opponent
		this.socket = socket;
		
		// add mouse listener
		this.addMouseListener(this);
		
		
		
		
		
		
		
		
		
		moveOpponentPieceTask = new MoveOpponentPieceTask(this, moveFirst);
		new Thread(moveOpponentPieceTask).start();

		
		// wait for the opponent to move first
//		if (!moveFirst) {
//			DataPackage response = null;
//			while (true) {
//				try {
//					if (socket.getInputStream().available() > 0) {
//						InputStream is = socket.getInputStream();
//						byte[] firstMove = new byte[is.available()];
//						is.read(firstMove);
//						response = (DataPackage) IOSystem.getObject(firstMove);
//						break;
//					}
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//			updateChessBoardBasedOnResponse(response);
//			repaint();
//		}
		
	}
	
	// invoked when repaint() is called
	@Override
	public void paintComponent(Graphics graphics) {
		
		super.paintComponent(graphics); // draw the chess board
		
		// draw the chess pieces
		for (int row = 0; row < 8; row++) {
			for (int column = 0; column < 8; column++) {
				if (board[row][column] == null) continue;
					
				graphics.drawImage(
						board[row][column].getImage(), 
						(int)(ChessConstants.CLASSIC_CHESSBOARD_MARGIN+ column* ChessConstants.CLASSIC_CHESSBOARD_GRID_WIDTH), 
						(int)(ChessConstants.CLASSIC_CHESSBOARD_MARGIN+ row* ChessConstants.CLASSIC_CHESSBOARD_GRID_WIDTH), 
						null);
			}
		}	
		
		// draw the pointer
		if ( firstClick && (selectedRow != -1 || selectedColumn != -1) ) {
			System.out.println("A chess piece is selected: "+chessPieceSelected);
			System.out.println("draw");
			graphics.drawImage(pointerImage,
					(int)(ChessConstants.CLASSIC_CHESSBOARD_MARGIN+ selectedColumn* ChessConstants.CLASSIC_CHESSBOARD_GRID_WIDTH+ChessConstants.CLASSIC_CHESSBOARD_GRID_WIDTH/2), 
					(int)(ChessConstants.CLASSIC_CHESSBOARD_MARGIN+ selectedRow* ChessConstants.CLASSIC_CHESSBOARD_GRID_WIDTH+ChessConstants.CLASSIC_CHESSBOARD_GRID_WIDTH/2), 
					null);
		}
		
	}
	
	public ChessPiece[][] getBoard() {
		return board;
	}
	
	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

//	public boolean isMouseClickEnabled() {
//		return mouseClickEnabled;
//	}
//
//	public void setMouseClickEnabled(boolean mouseClickEnabled) {
//		this.mouseClickEnabled = mouseClickEnabled;
//	}
	
	
	
	//////////////////////////////////////////
	//// Methods from MouseListener below ////
	//////////////////////////////////////////
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
		if (!moveOpponentPieceTask.isMouseClickEnabled()) return;
		
		int column = (int)((e.getX() - ChessConstants.CLASSIC_CHESSBOARD_MARGIN) / ChessConstants.CLASSIC_CHESSBOARD_GRID_WIDTH);
		int row = (int)((e.getY() - ChessConstants.CLASSIC_CHESSBOARD_MARGIN) / ChessConstants.CLASSIC_CHESSBOARD_GRID_WIDTH);
		
		
		System.out.println(column+" "+row);
		// goes out of bound
		if (row < 0 || row > 7 || column < 0 || column > 7) return;
		
		
		if (!chessPieceSelected) {
			firstClick = true;
			selectedRow = row;
			selectedColumn = column;
			
			// can select only the player's game pieces
			if (board[row][column] != null && !board[row][column].isEnemy()) {
				chessPieceSelected = true;
			}
			
			repaint();
		} else {
			if (selectedRow == row && selectedColumn == column) return;
			
			firstClick = false;
			chessPieceSelected = false;
			
			// validMove and castlingIsAllowed cannot be both true
			boolean validMove = ChessLogic.validateChessPieceMovement(board, selectedRow, selectedColumn, row, column);
			boolean castlingIsAllowed = ChessLogic.castlingIsAllowed(board, selectedRow, selectedColumn, row, column);
			
			
			// a move is made
			if (validMove || castlingIsAllowed) { 
				
				if (validMove) {
					// make the movement
					board[row][column] = board[selectedRow][selectedColumn];
					board[selectedRow][selectedColumn] = null;
					
					ChessPiece mostRecentlyMovedPiece = board[row][column];
					
					// indicates that a chesspiece has been moved once
					if (!mostRecentlyMovedPiece.isHasBeenMoved()) {
						mostRecentlyMovedPiece.setHasBeenMoved(true);
					}
					// special case 1: promotion
					if (row == 0 && mostRecentlyMovedPiece.getType() == ChessType.PAWN) {
						String[] options ={"Knight", "Bishop", "Rook", "Queen"};  
						String newPiece = options[JOptionPane.showOptionDialog(this, "Which chess piece do you want it to be promoted to?", null, JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, "Queen")];
						ChessLogic.promotePawn(mostRecentlyMovedPiece, newPiece);
					}
					
					// TODO if after the movement, the player is still being checked by the enemy, then the game ends
					
					// TODO maybe move it to below castlingIsAllowed? 
					// check that if the player is checking the opponent
					if (ChessLogic.checkingEnemy(board, row, column)) {
						System.out.println("Checked!"); //TODO include it in the special case
					}
					
				} else if (castlingIsAllowed) { // special case 2: castling
					
					// get rook's position
					int rookColumn = -1;
					if (selectedColumn == 4) {
						rookColumn = column;
					} else {
						rookColumn = selectedColumn;
					}
					
					ChessPiece king = board[7][4], rook = board[7][rookColumn];
					
					
					// TODO encapsulate it in a method
					// make the movement
					if (rookColumn == 0) {
						board[7][2] = king;
						board[7][3] = rook;
						
						// TODO maybe move it to below castlingIsAllowed? 
						if (ChessLogic.checkingEnemy(board, 7, 2) || ChessLogic.checkingEnemy(board, 7, 3)) {
							System.out.println("Checked!"); //TODO include it in the special case
						}
					} else if (rookColumn == 7) {
						board[7][6] = king;
						board[7][5] = rook;
						
						// TODO maybe move it to below castlingIsAllowed? 
						if (ChessLogic.checkingEnemy(board, 7, 5) || ChessLogic.checkingEnemy(board, 7, 6)) {
							System.out.println("Checked!"); //TODO include it in the special case
						}
					} 
					board[row][column] = null;
					board[selectedRow][selectedColumn] = null;
					
					king.setHasBeenMoved(true);
					rook.setHasBeenMoved(true);
				}
				
				// TODO send the request to include all changes
				// add the changes to the dataPackage
				// send the changes to datapackage.
				moveOpponentPieceTask.setMouseClickEnabled(false);
				NetworkCommunicator.sendDataPackage(socket, new DataPackage());
				// send dataPackage.
				
				repaint();

			}
			
			
		}

	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

}


// Why we need this class: we need to launch another thread in
// ChessBoard's constructor that updates the GUI upon receiving 
// messages from the opponent. Calling recall() in ChessBoard's
// mouseClicked() does not update the GUI immediately, so we do
// it in a separate thread, and that thread should be created 
// and started in the constructor instead of in any other method
// like paintComponent()

// Also refer to the commment of repaint() - the lightweight/
// heavy weight part
class MoveOpponentPieceTask implements Runnable {
	
	private ChessBoard cb = null;
	private boolean mouseClickEnabled = false;
	
	private boolean flip = true;
	ChessPiece temp = null;
	
	public MoveOpponentPieceTask(ChessBoard cb, boolean moveFirst) {
		this.cb = cb;
		mouseClickEnabled = moveFirst;
		
		temp = cb.getBoard()[7][7];
	}
	
	@Override
	public void run() {
		
		while (true) {
			if (!mouseClickEnabled) {
				updateChessBoardBasedOnResponse(NetworkCommunicator.receiveDataPackage(cb.getSocket()));
				mouseClickEnabled = true;
				System.out.println("Enabled");
				cb.repaint();
			}
			
			// TODO Don't know why....
			// if the following code appears right after cb.repaint() above instead of 
			// where it is now, then after the player who uses the dark pieces makes 
			// the first move, the interface becomes unresponsive.
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
		
	}

	// modify the board variable
	private void updateChessBoardBasedOnResponse(DataPackage response) {
		if (flip) {
			cb.getBoard()[7][7] = null;
		} else {
			cb.getBoard()[7][7] = temp;
		}
		flip = !flip;
	}
	
	public boolean isMouseClickEnabled() {
		return mouseClickEnabled;
	}

	public void setMouseClickEnabled(boolean mouseClickEnabled) {
		this.mouseClickEnabled = mouseClickEnabled;
	}
}
