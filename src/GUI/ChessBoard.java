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
import java.net.URISyntaxException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import Utilities.IOSystem;
import backend.ChessLogic;
import backend.ChessPiece;
import backend.ChessPiece.ChessType;

/*
 * TODO ChessBoard should only be in charge of UI, sound effect, etc.
 */
public class ChessBoard extends JLabel implements MouseListener{
	
	private ChessPiece[][] board = null;
	
	// for chess piece movement
	private boolean chessPieceSelected = false; // indicates if one of the player's chess pieces is selected
	private boolean firstClick = true; // for putting a pointer
	private int selectedRow = -1, selectedColumn = -1;
	
	// put the following in the memory to avoid disk access
	private ImageIcon chessBoardImage = null;
	private Image pointerImage = null;
	
	public ChessBoard(boolean moveFirst) {
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
		
		// add mouse listener
		this.addMouseListener(this);
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
	
	
	//////////////////////////////////////////
	//// Methods from MouseListener below ////
	//////////////////////////////////////////
	
	@Override
	public void mouseClicked(MouseEvent e) {
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
			
			
			boolean validMove = ChessLogic.validateChessPieceMovement(board, selectedRow, selectedColumn, row, column);
			boolean castlingIsAllowed = ChessLogic.castlingIsAllowed(board, selectedRow, selectedColumn, row, column);
			
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
				} else if (castlingIsAllowed) {

				
				}
				
				// check that if the player is checking the opponent
				if (ChessLogic.checkingEnemy(board, row, column)) {
					System.out.println("Checked!"); //TODO include it in the special case
				}
				
				// TODO send the request to include all changes
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
