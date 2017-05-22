package com.kenji;

/**
 * A common interface for seed patterns used in the Game of Life.
 */
public interface Pattern {
    void init(boolean[][] board);
}
