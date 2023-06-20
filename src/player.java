public class player {
    public colorPiece color;
    public boolean isTurn;
    public player Opponent;

    public player(colorPiece color, boolean isTurn) {
        this.color = color;
        this.isTurn = isTurn;
    }

    public void setOpponent(player Opponent) {
        this.Opponent = Opponent;
    }

    public void makeMove(chessSpot from, chessSpot to) {
        ChessBoard board = ChessBoard.getInstance();
        if (isTurn) {
            if (board.isLegal(from, to, from.getPiece())) {
                to.setPiece(from.getPiece());
                from.setPiece(null);
                isTurn = false;
                Opponent.isTurn = true;
            }
        }

    }


}
