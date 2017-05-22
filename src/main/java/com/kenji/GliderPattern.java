package com.kenji;

public final class GliderPattern implements Pattern {

    public void init(boolean[][] board) {
        if(board == null) {
            throw new IllegalArgumentException("A valid game board must be provided.");
        }

        int rows = board.length;
        int cols = board[0].length;

        // The Glider pattern requires a board at least 3x3 in size
        if(rows < 3 || cols < 3) {
            throw new IllegalArgumentException("The game board must be at least 3x3 in size.");
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int middleRow = rows / 2;
                int middleCol = cols / 2;
                if((i == middleRow - 1 && j == middleCol) ||
                        (i == middleRow && j == middleCol + 1) ||
                        (i == middleRow + 1 && j == middleCol - 1) ||
                        (i == middleRow + 1 && j == middleCol) ||
                        (i == middleRow + 1 && j == middleCol + 1)) {
                    board[i][j] = true;
                } else {
                    board[i][j] = false;
                }
            }
        }
    }
}
