// singletn chess board class

import java.util.Map;

public class ChessBoard {

    private static ChessBoard instance = null;
    private chessSpot[][] board = new chessSpot[8][8];
    player[] players = new player[2];
    private ChessBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = new chessSpot(i, j);
                initializeBoard(board);
            }
        }
        players[0] = new player(colorPiece.WHITE, true);
        players[1] = new player(colorPiece.BLACK, false);
    }

    private void initializeBoard(chessSpot[][] board) {
        // initialize white pieces
        board[0][0].setPiece(new chessPiece(pieceType.ROOK, colorPiece.WHITE));
        board[0][1].setPiece(new chessPiece(pieceType.KNIGHT, colorPiece.WHITE));
        board[0][2].setPiece(new chessPiece(pieceType.BISHOP, colorPiece.WHITE));
        board[0][3].setPiece(new chessPiece(pieceType.QUEEN, colorPiece.WHITE));
        board[0][4].setPiece(new chessPiece(pieceType.KING, colorPiece.WHITE));
        board[0][5].setPiece(new chessPiece(pieceType.BISHOP, colorPiece.WHITE));
        board[0][6].setPiece(new chessPiece(pieceType.KNIGHT, colorPiece.WHITE));
        board[0][7].setPiece(new chessPiece(pieceType.ROOK, colorPiece.WHITE));
        for (int i = 0; i < 8; i++) {
            board[1][i].setPiece(new chessPiece(pieceType.PAWN, colorPiece.WHITE));
        }
        // initialize black pieces
        board[7][0].setPiece(new chessPiece(pieceType.ROOK, colorPiece.BLACK));
        board[7][1].setPiece(new chessPiece(pieceType.KNIGHT, colorPiece.BLACK));
        board[7][2].setPiece(new chessPiece(pieceType.BISHOP, colorPiece.BLACK));
        board[7][3].setPiece(new chessPiece(pieceType.QUEEN, colorPiece.BLACK));
        board[7][4].setPiece(new chessPiece(pieceType.KING, colorPiece.BLACK));
        board[7][5].setPiece(new chessPiece(pieceType.BISHOP, colorPiece.BLACK));
        board[7][6].setPiece(new chessPiece(pieceType.KNIGHT, colorPiece.BLACK));
        board[7][7].setPiece(new chessPiece(pieceType.ROOK, colorPiece.BLACK));
        for (int i = 0; i < 8; i++) {
            board[6][i].setPiece(new chessPiece(pieceType.PAWN, colorPiece.BLACK));
        }
    }

    public static ChessBoard getInstance() {
        if (instance == null) {
            instance = new ChessBoard();
        }
        return instance;
    }
    public chessSpot getSpot(int row, int col) {
        return board[row][col];
    }
    public void resetBoard() {
        // reset all pieces to original positions
        initializeBoard(this.getInstance().board);
    }
    public void printBoard() {
        // print the board
        // TODO
    }

    public boolean isLegal(chessSpot from, chessSpot to,chessPiece piece) {
        if (from.getPiece() == null) {
            System.out.println("No piece at this spot");
            return false;
        }
        if (from.getPiece().color != piece.color) {
            System.out.println("Not your turn");
            return false;
        }
        // check if the move is legal
        // TODO
        int fromRow = from.row;
        int fromCol = from.col;
        int toRow = to.row;
        int toCol = to.col;

        pieceType Type = piece.type;

        // Check if the move is legal for the specific piece type
        switch (Type) {
            case PAWN:
                if (isPawnMoveLegal(fromRow, fromCol, toRow, toCol, piece.color)) {
                    // Move is legal for pawn
                    return true;
                }
                break;
            case ROOK:
                if (isRookMoveLegal(fromRow, fromCol, toRow, toCol)) {
                    // Move is legal for rook
                    return true;
                }
                break;
            case KNIGHT:
                if (isKnightMoveLegal(fromRow, fromCol, toRow, toCol)) {
                    // Move is legal for knight
                    return true;
                }
                break;
            case BISHOP:
                if (isBishopMoveLegal(fromRow, fromCol, toRow, toCol)) {
                    // Move is legal for bishop
                    return true;
                }
                break;
            case QUEEN:
                if (isQueenMoveLegal(fromRow, fromCol, toRow, toCol)) {
                    // Move is legal for queen
                    return true;
                }
                break;
            case KING:
                if (isKingMoveLegal(fromRow, fromCol, toRow, toCol)) {
                    // Move is legal for king
                    return true;
                }
                break;
        }

        // If the move was not handled by the piece-specific logic, it's an illegal move
        System.out.println("Illegal move");
        return false;
    }

    private boolean isPawnMoveLegal(int fromRow, int fromCol, int toRow, int toCol, colorPiece pieceColor) {
        int forwardDir = (pieceColor == colorPiece.WHITE) ? -1 : 1;

        // Pawn move: 1 square forward
        if (fromCol == toCol && fromRow + forwardDir == toRow && board[toRow][toCol].getPiece() == null) {
            return true;
        }

        // Pawn move: 2 squares forward (only from initial position)
        if (fromCol == toCol && fromRow + 2 * forwardDir == toRow && fromRow == (pieceColor == colorPiece.WHITE ? 6 : 1) && board[toRow][toCol].getPiece() == null) {
            return true;
        }

        // Pawn capture: diagonally 1 square forward
        if (Math.abs(fromCol - toCol) == 1 && fromRow + forwardDir == toRow && board[toRow][toCol].getPiece() != null && board[toRow][toCol].getPiece().color != pieceColor) {
            return true;
        }

        // TODO: Implement en passant, promotion, and other pawn movement rules

        return false;
    }

    private boolean isRookMoveLegal(int fromRow, int fromCol, int toRow, int toCol) {
        // Rook move: vertical or horizontal
        if ((fromRow == toRow && fromCol != toCol) || (fromRow != toRow && fromCol == toCol)) {
            // Check if there are any pieces in the way
            int rowStep = (fromRow < toRow) ? 1 : -1;
            int colStep = (fromCol < toCol) ? 1 : -1;

            // Check vertical movement
            if (fromRow != toRow) {
                for (int row = fromRow + rowStep; row != toRow; row += rowStep) {
                    if (board[row][fromCol].getPiece() != null) {
                        return false; // Piece blocking the path
                    }
                }
            }

            // Check horizontal movement
            if (fromCol != toCol) {
                for (int col = fromCol + colStep; col != toCol; col += colStep) {
                    if (board[fromRow][col].getPiece() != null) {
                        return false; // Piece blocking the path
                    }
                }
            }

            return true;
        }

        return false;
    }

    private boolean isKnightMoveLegal(int fromRow, int fromCol, int toRow, int toCol) {
        int rowDiff = Math.abs(toRow - fromRow);
        int colDiff = Math.abs(toCol - fromCol);

        // Knight move: L-shape (2 squares in one direction and 1 square in the perpendicular direction)
        if ((rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2)) {
            return true;
        }

        return false;
    }

    private boolean isBishopMoveLegal(int fromRow, int fromCol, int toRow, int toCol) {
        int rowDiff = Math.abs(toRow - fromRow);
        int colDiff = Math.abs(toCol - fromCol);

        // Bishop move: diagonal
        if (rowDiff == colDiff) {
            int rowStep = (fromRow < toRow) ? 1 : -1;
            int colStep = (fromCol < toCol) ? 1 : -1;

            for (int i = 1; i < rowDiff; i++) {
                if (board[fromRow + i * rowStep][fromCol + i * colStep].getPiece() != null) {
                    return false; // Piece blocking the path
                }
            }

            return true;
        }

        return false;
    }

    private boolean isQueenMoveLegal(int fromRow, int fromCol, int toRow, int toCol) {
        // Queen move: combination of rook and bishop moves
        if (isRookMoveLegal(fromRow, fromCol, toRow, toCol) || isBishopMoveLegal(fromRow, fromCol, toRow, toCol)) {
            return true;
        }

        return false;
    }

    private boolean isKingMoveLegal(int fromRow, int fromCol, int toRow, int toCol) {
        int rowDiff = Math.abs(toRow - fromRow);
        int colDiff = Math.abs(toCol - fromCol);

        // King move: 1 square in any direction
        if (rowDiff <= 1 && colDiff <= 1) {
            return true;
        }

        return false;
    }

}
