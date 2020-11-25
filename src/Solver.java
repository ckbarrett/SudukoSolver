import java.util.*;

public class Solver {
	private static final int[][] exampleSuduko = 
		{{0,6,0,0,0,2,4,5,0},
		 {0,0,0,0,9,5,0,0,7},
		 {0,0,0,7,0,4,0,0,3},
		 {1,0,0,0,2,0,0,4,6},
		 {3,9,4,0,7,0,1,2,8},
		 {6,2,0,0,1,0,0,0,5},
		 {9,0,0,6,0,1,0,0,0},
		 {5,0,0,9,4,0,0,0,0},
		 {0,3,8,2,0,0,0,1,0}};
	
	public static void main(String[] args) {
		int[][] puzzle = readSuduko(exampleSuduko);
		if(!solveSuduko(puzzle, 0,0)) System.out.println("Not solvable");
		else printSuduko(puzzle);
	}
	private static int[][] readSuduko(int[][] puzzle){
		if(!verifySuduko(puzzle)) {
			throw new InvalidPuzzleException("Invalid puzzle");
		}
		else return puzzle;
	}
	private static boolean solveSuduko(int[][] puzzle, int row, int col) {
		if(row > 8) return true;
		if(puzzle[row][col] != 0) {
			row += ++col / 9;
			col = col % 9;
			return solveSuduko(puzzle, row, col);
		}
		else {
			for(int i = 1; i <= 9; i++) {
				puzzle[row][col] = i;
				if(verifySuduko(puzzle)) {
					int tmpcol = col + 1;
					int tmprow = row + (tmpcol / 9);
					tmpcol = tmpcol % 9;
					if(solveSuduko(puzzle, tmprow, tmpcol)) {
						return true;
					}
				}
			}
			puzzle[row][col] = 0;
			return false;
		}
	}
	private static void printSuduko(int[][] puzzle) {
		for(int row = 0; row < 9; row++) {
			for(int col = 0; col < 9; col++) {
				System.out.print(puzzle[row][col]);
				if(col != 8) System.out.print(" | ");
				else System.out.println();
			}
			if(row != 8) System.out.println("---------------------------------");
		}
	}
	private static boolean verifySuduko(int[][] puzzle) {
		// Check rows
		for(int row = 0; row < 9; row++) {
			if(containsMultipleInstances(puzzle[row])) return false;
		}
		// Check cols
		for(int col = 0; col < 9; col++) {
			int[] tmpRow = new int[9];
			for(int row = 0; row < 9; row++) {
				tmpRow[row] = puzzle[row][col];
			}
			if(containsMultipleInstances(tmpRow)) return false;
		}
		// Check 3x3 boxes
		int[] rowDirection = {-1,-1,-1,0,0,1,1,1};
		int[] colDirection = {-1,0,1,-1,1,-1,0,1};
		for(int row = 1; row < 8; row += 3) {
			for(int col = 1; col < 8; col +=3 ) {
				int[] tmpRow = new int[9];
				tmpRow[8] = puzzle[row][col];
				for(int i = 0; i < 8; i++) {
					tmpRow[i] = puzzle[row + rowDirection[i]][col + colDirection[i]];
				}
				if(containsMultipleInstances(tmpRow)) return false;
			}
		}
		return true;
	}
	private static boolean containsMultipleInstances(int[] row) {
		Set<Integer> set = new HashSet<Integer>();
		for(int i = 0; i < row.length; i++) {
			if(set.contains(row[i]) && row[i] != 0) {
				return true;
			}
			else set.add(row[i]);
		}
		return false;
	}
}