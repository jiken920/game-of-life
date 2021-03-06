package com.kenji;

import java.util.Scanner;

/**
 * Implementation of the Game of Life.
 *
 * Description:
 *
 * The universe of the Game of Life is an infinite two­dimensional orthogonal grid of square cells, each of which
 * is in one of two possible states, alive or dead. Every cell interacts with its eight neighbours, which are the
 * cells that are horizontally, vertically, or diagonally adjacent.
 *
 * Rules:
 *
 * At each step in time, the following transitions occur:
 *
 * 1. Any live cell with fewer than two live neighbours dies, as if caused by under­population.
 * 2. Any live cell with two or three live neighbours lives on to the next generation.
 * 3. Any live cell with more than three live neighbours dies, as if by overcrowding.
 * 4. Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.
 */
public final class GameOfLife {
    private boolean[][] board;
    private final int ROWS;
    private final int COLS;

    /**
     * Creates a new instance of the Game of Life.
     *
     * @param rows The desired number of rows in the game board.
     * @param cols The desired number of columns in the game board.
     * @param pattern The seed com.kenji.Pattern to use.
     */
    public GameOfLife(int rows, int cols, Pattern pattern) {
        ROWS = rows;
        COLS = cols;
        board = new boolean[rows][cols];

        try {
            pattern.init(board);
            printBoard(board);
        } catch (IllegalArgumentException e) {
            System.out.println("There was an error initializing the Game of Life. " +
                    "Please check your board size and try again.");
            e.printStackTrace();
        }
    }

    /**
     * Advances the game by one generation.
     *
     * The current state of the game board is cloned into a new board, and the
     * state of new board is generated by applying the rules of the game to the existing board.
     */
    private void nextGeneration() {
        boolean[][] nextBoard = cloneBoard();

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                int neighbors = getLivingNeighborCount(board, i, j);
                if(board[i][j] && (neighbors < 2 || neighbors > 3)) { // Rules 1 and 3
                    nextBoard[i][j] = false;
                } else if(board[i][j] && neighbors == 2 || board[i][j] && neighbors == 3) { // Rule 2
                    nextBoard[i][j] = true;
                } else if(!board[i][j] && neighbors == 3) { // Rule 4
                    nextBoard[i][j] = true;
                }
            }
        }

        this.board = nextBoard;
        printBoard(this.board);
    }

    /**
     * Helper function that creates a deep copy of the current game board.
     */
    private boolean[][] cloneBoard() {
        boolean[][] newBoard = new boolean[ROWS][COLS];
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                newBoard[i][j] = board[i][j];
            }
        }

        return newBoard;
    }

    /**
     * Returns the number of living cells that neighbor a given cell.
     *
     * @param board The game board.
     * @param row The row index of the cell to check.
     * @param col The column index of the cell to check.
     * @return An integer representing the number of living cells that border the given cell.
     */
    private int getLivingNeighborCount(boolean[][] board, int row, int col) {
        final int top = row - 1;
        final int bottom = row + 1;
        final int left = col - 1;
        final int right = col + 1;

        int neighborCount = 0;

        // Check top-left cell
        if(top > 0 && left > 0 && board[top][left]) {
            neighborCount++;
        }

        // Check top cell
        if(top > 0 && board[top][col]) {
            neighborCount++;
        }

        // Check top-right cell
        if(top > 0 && right < COLS - 1 && board[top][right]) {
            neighborCount++;
        }

        // Check right cell
        if(right < COLS - 1 && board[row][right]) {
            neighborCount++;
        }

        // Check bottom-right cell
        if(bottom < ROWS - 1 && right < COLS - 1 && board[bottom][right]) {
            neighborCount++;
        }

        // Check bottom cell
        if(bottom < ROWS - 1 && board[bottom][col]) {
            neighborCount++;
        }

        // Check bottom-left cell
        if(bottom < ROWS - 1 && left > 0 && board[bottom][left]) {
            neighborCount++;
        }

        // Check left cell
        if(left > 0 && board[row][left]) {
            neighborCount++;
        }

        return neighborCount;
    }

    /**
     * Prints the state of the game board to the console.
     */
    private void printBoard(boolean[][] board) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {

                // Print the ends of the columns
                if(j == 0 || j == COLS - 1) {
                    System.out.print("*");
                }
                // Print the header and footer
                else if(i == 0 || i == ROWS - 1) {
                    System.out.print("*");
                } else {
                    if(board[i][j]) {
                        System.out.print("O");
                    } else {
                        System.out.print(" ");
                    }
                }

            }
            System.out.print("\n");
        }
    }

    public static void main(String[] args) {
        System.out.println("Welcome to the Game of Life.\n" +
                "Please enter the height and width of the game board you'd like to create:");

        Scanner scanner = new Scanner(System.in);
        int height = Integer.parseInt(scanner.nextLine());
        int width = Integer.parseInt(scanner.nextLine());

        GameOfLife game = new GameOfLife(height, width, new GliderPattern());

        while(true) {
            System.out.println("Press \"ENTER\" to move to the next generation.\n");
            scanner.nextLine();
            game.nextGeneration();
        }
    }
}


