public class chessSpot {
    public int row;
    public int col;
    private chessPiece piece = null;
    public chessSpot(int row, int col) {
        this.row = row;
        this.col = col;
    }
    public void setPiece(chessPiece piece) {
        this.piece = piece;
    }
    public chessPiece getPiece() {
        return this.piece;
    }
}
