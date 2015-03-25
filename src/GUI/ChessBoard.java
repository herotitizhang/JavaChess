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

import Utilities.IOSystem;
import backend.ChessLogic;
import backend.ChessPiece;

/*
 * TODO ChessBoard should only be in charge of UI, sound effect, etc.
 */
public class ChessBoard extends JLabel implements MouseListener{
	
	private ChessPiece[][] board = null;
	
	// for chess piece movement
	private boolean chessPieceSelected = false;
	private boolean firstClick = true; // for putting a pointer
	private int selectedRow = -1, selectedColumn = -1;
	
	// put the following in the memory to avoid disk access
	private ImageIcon chessBoardImage = null;
	private Image pointerImage = null;
	
	
	// for the first click pointer icon
//	private int selectedRow = 0, selectedColumn = 0;
	
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
	
	@Override
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics); // draw the chess board
		
		
		// draw the chess pieces
		for (int row = 0; row < 8; row++) {
			for (int column = 0; column < 8; column++) {
				if (board[row][column] == null) continue;
				
//				String imagePath = null;
//				if (board[row][column] == ChessPiece.ENEMY_ROOK) imagePath = ChessConstants.CLASSIC_DARK_ROOK; // TODO enemy does not have to be dark
//				else if (board[row][column] == ChessPiece.ENEMY_BISHOP) imagePath = ChessConstants.CLASSIC_DARK_BISHOP;   
//				else if (board[row][column] == ChessPiece.ENEMY_KNIGHT) imagePath = ChessConstants.CLASSIC_DARK_KNIGHT;   
//				else if (board[row][column] == ChessPiece.ENEMY_QUEEN) imagePath = ChessConstants.CLASSIC_DARK_QUEEN;   
//				else if (board[row][column] == ChessPiece.ENEMY_KING) imagePath = ChessConstants.CLASSIC_DARK_KING;   
//				else if (board[row][column] == ChessPiece.ENEMY_PAWN) imagePath = ChessConstants.CLASSIC_DARK_PAWN;   
//				else if (board[row][column] == ChessPiece.MY_ROOK) imagePath = ChessConstants.CLASSIC_LIGHT_ROOK;   
//				else if (board[row][column] == ChessPiece.MY_KNIGHT) imagePath = ChessConstants.CLASSIC_LIGHT_KNIGHT;   
//				else if (board[row][column] == ChessPiece.MY_BISHOP) imagePath = ChessConstants.CLASSIC_LIGHT_BISHOP;   
//				else if (board[row][column] == ChessPiece.MY_QUEEN) imagePath = ChessConstants.CLASSIC_LIGHT_QUEEN;   
//				else if (board[row][column] == ChessPiece.MY_KING) imagePath = ChessConstants.CLASSIC_LIGHT_KING;   
//				else if (board[row][column] == ChessPiece.MY_PAWN) imagePath = ChessConstants.CLASSIC_LIGHT_PAWN;   
					
				graphics.drawImage(
						board[row][column].getImage(), 
						(int)(ChessConstants.CLASSIC_CHESSBOARD_MARGIN+ column* ChessConstants.CLASSIC_CHESSBOARD_GRID_WIDTH), 
						(int)(ChessConstants.CLASSIC_CHESSBOARD_MARGIN+ row* ChessConstants.CLASSIC_CHESSBOARD_GRID_WIDTH), 
						null);
			}
		}	
		
		// draw the pointer
		if ( firstClick && (selectedRow != -1 || selectedColumn != -1) ) {
			System.out.println(chessPieceSelected);
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
		if (row < 0 || row > 7 || column < 0 || column > 7) return;
		
		
		if (!chessPieceSelected) {
			firstClick = true;
			selectedRow = row;
			selectedColumn = column;
			
			if (board[row][column] != null) {
				chessPieceSelected = true;
				
			}
		} else {
			if (selectedRow == row && selectedColumn == column) return;
			
			firstClick = false;
			chessPieceSelected = false;
			board[row][column] = board[selectedRow][selectedColumn];
			board[selectedRow][selectedColumn] = null;
			
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
