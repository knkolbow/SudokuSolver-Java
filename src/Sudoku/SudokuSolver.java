package Sudoku;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class SudokuSolver {
	public static void main(String[] args) throws Exception {
		FileInputStream inputFile =new FileInputStream("puzzle_1.txt");
		Scanner scanner = new Scanner(inputFile);
		FileOutputStream outputFile = new FileOutputStream("SudokuSolution.txt");
		PrintWriter writer = new PrintWriter(outputFile);
		
		Sudoku puzzle = new Sudoku();
		puzzle.loadData(scanner);
		puzzle.solve();
		puzzle.printSolution(writer);
		
		writer.close();
		outputFile.close();
		scanner.close();
		inputFile.close();
	}

}
