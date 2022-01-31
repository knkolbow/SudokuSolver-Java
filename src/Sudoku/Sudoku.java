package Sudoku;

import java.io.PrintWriter;
import java.util.Scanner;

public class Sudoku {
	// Data fields
		private char[][] board;
		private static final int SIZE = 9;
		private static final char BLANK = '.';
		private boolean solved;
		
		// Constructors
		public Sudoku() { board = new char[SIZE][SIZE]; } // Default constructor
		
		// Methods
		public void loadData(Scanner scanner) {
			/** Reads in the cell values from an input file */
			for (int row = 0; row < SIZE; row++) {
				String currentRow = scanner.nextLine();
				for (int col = 0; col < SIZE; col++) {
					board[row][col] = currentRow.charAt(col);
				}
			}
		}
		
		private int nextRowIndex(int row, int col) {
			/** Returns the row index of the next cell (row-major order) of the current cell */
			if (row == SIZE - 1 && col == SIZE - 1) {
				// We are already at the last cell of the board.
				return -1; // There is no next cell.
			}
			if (col == SIZE - 1) {
				// We reached the end of the row
				return row + 1;
			} else { // We have not reached the end of the row.
				return row;
			}
		}

		private int nextColIndex(int row, int col) {
			/** Returns the column index of the next cell (row-major order) of the current cell. */
			if (row == SIZE - 1 && col == SIZE - 1) {
				// We are already at the last cell of the board
				return -1; // There is not next cell
			}
			if (col == SIZE - 1) {
				// We reached the end of the row.
				return 0;
			} else { // We have not reached the end of the row.
				return col + 1;
			}
		}
		
		private boolean inSameRow(int row, char digit) {
			/** Returns true if digit appears in the row; false otherwise. */
			for (int col = 0; col < SIZE; col++) {
				if (board[row][col] == digit) {
					return true;
				}
			}
			return false;
		}
		
		private boolean inSameCol(int col, char digit) {
			/** Returns true if digit appears in the column; false otherwise */
			for (int row = 0; row < SIZE; row++) {
				if (board[row][col] == digit) {
					return true;
				}
			}
			return false;
		}
		
		private boolean inSameGrid(int row, int col, char digit) {
			/** Returns true if digit appears in the 3 by 3 grid that board[row][col] resides in. */
			int gridStartRow = row / 3 * 3, gridStartCol = col / 3 * 3;
			for (int i = gridStartRow; i < gridStartRow + 3; i++) {
				for (int j = gridStartCol; j < gridStartCol + 3; j++) {
					if (board[i][j] == digit) {
						return true;
					}
				}
			}
			return false;
		}
		
		private void solve(int row, int col) {
			/** Solve the puzzle starting from board[row][col]. */
			if (row == -1 || col == -1) {
				// The entire board is completed.
				solved = true;
				return;
			}
			
			// If board[row][col] is not blank...
			if (board[row][col] != BLANK) {
				// Move to the next cell.
				solve(nextRowIndex(row, col), nextColIndex(row, col));
			} else { // The current cell is blank.
				for (char digit = '1'; digit <= '9'; digit++) {
					if (inSameRow(row, digit)) { continue; }
					if (inSameCol(col, digit)) { continue; }
					if (inSameGrid(row, col, digit)) { continue; }
					
					// Fill the blank
					board[row][col] = digit;
					
					// After filling the blank, go to the next cell.
					solve(nextRowIndex(row, col), nextColIndex(row, col));
					
					// If no solution found, change back to blank and try the next digit.
					if (!solved) {
						board[row][col] = BLANK;
					}
				}
			}
		}
		
		// Wrapper method
		public void solve() {
			solve(0, 0);
		}
		
		public void printSolution(PrintWriter writer) throws Exception {
			/** Print the solution to an output file */
			if (!solved) {
				throw new Exception("Attempt to output unsolved puzzle.");
			}
			
			for (int row = 0; row < SIZE; row++) {
				for (int col = 0; col < SIZE; col++) {
					writer.print(board[row][col]);
				}
				writer.println();
			}
		}

}
