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
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import networkCommunication.DataPackage;
import networkCommunication.Move;
import networkCommunication.NetworkCommunicator;
import Utilities.IOSystem;
import backend.ChessLogic;
import backend.ChessPiece;
import backend.ChessPiece.ChessType;

/*
 * TODO ChessBoard should only be in charge of UI, sound effect, etc.
 */
public class ChessBoard extends JLabel implements MouseListener{

	private GameInterface topFrame = null;
	
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
	
	public ChessBoard(GameInterface topFrame, boolean moveFirst, Socket socket) {
		super();
		
		this.topFrame = topFrame;
		
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
	
	public GameInterface getTopFrame() {
		return topFrame;
	}
	
	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	
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
			
		} else {
			if (selectedRow == row && selectedColumn == column) return;
			
			firstClick = false;
			chessPieceSelected = false;
			
			boolean endGame = false;
			
			// validMove and castlingIsAllowed cannot be both true
			boolean validMove = ChessLogic.validateChessPieceMovement(board, selectedRow, selectedColumn, row, column);
			boolean castlingIsAllowed = ChessLogic.castlingIsAllowed(board, selectedRow, selectedColumn, row, column);
			
			
			// a move is made
			if (validMove || castlingIsAllowed) { 
				DataPackage toBeSent = new DataPackage();
				
				if (board[row][column] != null && board[row][column].isEnemy()
						&& board[row][column].getType() == ChessType.KING) {
						JOptionPane.showMessageDialog(this, "You won!"); 
						endGame = true;
				}
				
				if (validMove) {
					// make the movement
					board[row][column] = board[selectedRow][selectedColumn];
					board[selectedRow][selectedColumn] = null;
					
					Move move = new Move(selectedRow, selectedColumn, row, column);
					toBeSent.getMoves().add(move);
					
					ChessPiece mostRecentlyMovedPiece = board[row][column];
					
					// indicates that a chesspiece has been moved once
					if (!mostRecentlyMovedPiece.isHasBeenMoved()) {
						mostRecentlyMovedPiece.setHasBeenMoved();
					}
					// special case 1: promotion
					if (row == 0 && mostRecentlyMovedPiece.getType() == ChessType.PAWN) {
						String[] options ={"Knight", "Bishop", "Rook", "Queen"};  
						String newPiece = options[JOptionPane.showOptionDialog(this, "Which chess piece do you want it to be promoted to?", null, JOptionPane.OK_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, "Queen")];
						ChessLogic.promotePawn(mostRecentlyMovedPiece, newPiece);
						
						// datapackage update
						move.pawnPromoteTo(newPiece);
					}
					
					// TODO if after the movement, the player is still being checked by the enemy, then the game ends
					

					
				} else if (castlingIsAllowed) { // special case 2: castling
					
					// get rook's position
					int rookColumn = -1;
					if (selectedColumn == 4) {
						rookColumn = column;
					} else {
						rookColumn = selectedColumn;
					}
					
					ChessPiece king = board[7][4], rook = board[7][rookColumn];
					
					// castling logic begins
					
					// make the movement
					if (rookColumn == 0) {
						board[7][2] = king;						
						toBeSent.getMoves().add(new Move(7, 4, 7, 2));

						board[7][3] = rook;
						toBeSent.getMoves().add(new Move(7, rookColumn, 7, 3));

					} else if (rookColumn == 7) {
						board[7][6] = king;
						toBeSent.getMoves().add(new Move(7, 4, 7, 6));

						board[7][5] = rook;
						toBeSent.getMoves().add(new Move(7, rookColumn, 7, 5));

					} 
					board[row][column] = null;
					board[selectedRow][selectedColumn] = null;
					
					king.setHasBeenMoved();
					rook.setHasBeenMoved();
				}
				
				// datapackage update
				for (Move move: toBeSent.getMoves()) {
					if (ChessLogic.checkingEnemy(board, move.getToRow(), move.getToColumn())) {
						move.setCheckingEnemy(); 
					}
				}
				
				NetworkCommunicator.sendDataPackage(socket, toBeSent);
				moveOpponentPieceTask.setMouseClickEnabled(false);
				
				// TODO doesn't work.
				if (ChessLogic.beingChecked(board)) {
					JOptionPane.showMessageDialog(this, "You are being checked!");
				}
				
				if (endGame) {
					System.exit(0);
				}

			}
			
		}
		
		repaint();	

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
	private boolean beingChecked = false;
	
	public MoveOpponentPieceTask(ChessBoard cb, boolean moveFirst) {
		this.cb = cb;
		mouseClickEnabled = moveFirst;
	}
	
	@Override
	public void run() {
		
		while (true) {
			if (!mouseClickEnabled) {
				updateChessBoardBasedOnResponse(NetworkCommunicator.receiveDataPackage(cb.getSocket()));
				mouseClickEnabled = true;
				cb.repaint();
				
				// leave the code here so that pawn can be promoted before the message is shown
				if (beingChecked) {
					JOptionPane.showMessageDialog(cb, "You are being checked!");
					beingChecked = false;
				}
				
			}
			
			// TODO 
			// if the following code appears right after cb.repaint() above instead of 
			// where it is now, then after the player who uses the dark pieces makes 
			// the first move, the interface becomes unresponsive.
			// Can't figure out why....
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
		
	}

	// modify the board variable
	private void updateChessBoardBasedOnResponse(DataPackage response) {

		System.out.println(response.getMoves().size());
		
		for (Move move: response.getMoves()) {
			
			if (cb.getBoard()[7-move.getToRow()][7-move.getToColumn()] != null 
				&& cb.getBoard()[7-move.getToRow()][7-move.getToColumn()].getType() == ChessType.KING) {
				JOptionPane.showMessageDialog(cb, "You lost!"); 
				System.exit(0);
			}
			
			// make the movement
			cb.getBoard()[7-move.getToRow()][7-move.getToColumn()] =
					cb.getBoard()[7-move.getFromRow()][7-move.getFromColumn()];
			cb.getBoard()[7-move.getFromRow()][7-move.getFromColumn()] = null;
			
			ChessPiece enemyPiece = cb.getBoard()[7-move.getToRow()][7-move.getToColumn()];

			
			// set hasBeenMoved to true
			if (!enemyPiece.isHasBeenMoved()) {
				enemyPiece.setHasBeenMoved();
			}
			
			// promote the pawn
			if (move.getPawnPromoteType() != null) {
				ChessLogic.promotePawn(enemyPiece, move.getPawnPromoteType());
			}
			
			// is being checked
			if (move.isCheckingEnemy() && !beingChecked) {
				beingChecked = true;
			}
		}
		
	}
	
	public boolean isMouseClickEnabled() {
		return mouseClickEnabled;
	}

	public void setMouseClickEnabled(boolean mouseClickEnabled) {
		this.mouseClickEnabled = mouseClickEnabled;
	}
}
