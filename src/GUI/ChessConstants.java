package GUI;

public interface ChessConstants {
	
	static final int GAME_INTERFACE_WIDTH = 600;
	static final int GAME_INTERFACE_HGIGHT = 528; // set according to BOARD_LENGTH
	
	static final String RESOURCES_FOLDER = "/resources/";
	
	// for the classic chess board
	static final String CLASSIC_CHESSBOARD = RESOURCES_FOLDER+"classic_board.png"; // the path of the image
	static final int CLASSIC_CHESSBOARD_LENGTH = 500;
	static final int CLASSIC_CHESSBOARD_MARGIN = CLASSIC_CHESSBOARD_LENGTH * 27 / 500; // includes the black border line
	static final double CLASSIC_CHESSBOARD_GRID_WIDTH = CLASSIC_CHESSBOARD_LENGTH * 55.5 / 500;
	
	// for accessing main panel's card layout
	static final String INITIALPANEL = "initialPanel";
	static final String CHESSBOARDPANEL = "chessboardPanel";
	
	// for the chess pieces
	static final String CLASSIC_DARK_PAWN = RESOURCES_FOLDER+"classic_dark_pawn.png"; 
	static final String CLASSIC_DARK_ROOK = RESOURCES_FOLDER+"classic_dark_rook.png"; 
	static final String CLASSIC_DARK_BISHOP = RESOURCES_FOLDER+"classic_dark_bishop.png"; 
	static final String CLASSIC_DARK_KNIGHT = RESOURCES_FOLDER+"classic_dark_knight.png"; 
	static final String CLASSIC_DARK_QUEEN = RESOURCES_FOLDER+"classic_dark_queen.png"; 
	static final String CLASSIC_DARK_KING = RESOURCES_FOLDER+"classic_dark_king.png"; 
	static final String CLASSIC_LIGHT_PAWN = RESOURCES_FOLDER+"classic_light_pawn.png"; 
	static final String CLASSIC_LIGHT_ROOK = RESOURCES_FOLDER+"classic_light_rook.png"; 
	static final String CLASSIC_LIGHT_BISHOP = RESOURCES_FOLDER+"classic_light_bishop.png"; 
	static final String CLASSIC_LIGHT_KNIGHT = RESOURCES_FOLDER+"classic_light_knight.png"; 
	static final String CLASSIC_LIGHT_KING = RESOURCES_FOLDER+"classic_light_king.png"; 
	static final String CLASSIC_LIGHT_QUEEN = RESOURCES_FOLDER+"classic_light_queen.png"; 
	
	static final String POINTER = RESOURCES_FOLDER+"pointer.png";
	
}
