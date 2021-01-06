package cellularAutomata;

/* This class will deal with updating the portion of the grid where
 * Conway's Game of Life is being run.  
 */
public class GameOfLife {

	/* This method will update the portion of the grid where Conway's 
	 * Game of Life is being run.  The rules for Conway's game of life are
	 * as follows: 
	 *     - if a cell is surrounded by exactly 3 living neighbors, it will
	 * be given life on the next iteration
	 *     - if a cell is surrounded by fewer than 2 living neighbors, or 
	 * more than 3 living neighbors, it will be dead in the next iteration.
	 *     - if a cell has exactly 2 living neighbors, it will not change in
	 * the next iteration.  If it was previously alive, it will still be alive.
	 * If it was previously dead, it will still be dead.  
	 * 
	 * Since this project involves colors, dead cells are represented with a number
	 * that indicates the number of iterations for which they have been dead.  As 
	 * such, killing a cell (regardless of whether it was dead already or not) will
	 * amount to incrementing that cell's value (represented by a location in the
	 * first array parameter).  
	 * 
	 * The second parameter is the top row of the 1D cellular automaton being fed into 
	 * the game of life as "input."  This is necessary for the function to have access
	 * to because it affects the bottom row of the Game of Life.  This function must
	 * not modify the input array.
	 * 
	 * Since Game of Life is represented theoretically by an infinite grid, we must find
	 * some way to deal with the edges.  The bottom edge will be dealt with by interacting
	 * with the input row.  The left and right edges will wrap around to the opposite side
	 * of the board.  For example, the cell in the 5th row on the far left side of the board
	 * will consider its left neighbor to be the cell in the 5th row on the far right side
	 * of the board.  Cells that reach the top of the grid will simply be killed.  There 
	 * will be a "margin" - a section of the grid on the left, right, and top - that is not
	 * displayed in the animation, but that is stored for the purposes of iterating through
	 * the game.  This will make for a smoother animation where the user doesn't notice as 
	 * much weird behavior around the edges. 
	 */
	public static int[][] updateGrid(int[][] grid, int[] input) {
		
		/* The new grid must have the same dimensions as the old grid. */
		int[][] newGrid = new int[grid.length][grid[0].length];

		/* Everything except the top and bottom row.  The left and 
		 * right edges wrap around. */
		for (int row = 1; row < grid.length - 1; row++) {
			for (int col = 0; col < grid[0].length; col++) {
				
				/* used to keep track of how many living neighbors
				 * a cell has.  This information will be used to
				 * determine whether or not this cell will be dead
				 * or alive in this time step.
				 */
				int numAliveNeighbors = 0;
				
				if (col == 0) { // left edge
					if (grid[row - 1][col] == 0) {
						numAliveNeighbors++;
					}
					if (grid[row - 1][col + 1] == 0) {
						numAliveNeighbors++;
					} 
					if (grid[row - 1][grid[0].length - 1] == 0) {
						numAliveNeighbors++;
					}
					if (grid[row][col + 1] == 0) {
						numAliveNeighbors++;
					}
					if (grid[row][grid[0].length - 1] == 0) {
						numAliveNeighbors++;
					}
					if (grid[row + 1][col] == 0) {
						numAliveNeighbors++;
					}
					if (grid[row + 1][col + 1] == 0) {
						numAliveNeighbors++;
					}
					if (grid[row + 1][grid[0].length - 1] == 0) {
						numAliveNeighbors++;
					}
				} else if (col == grid[0].length - 1) { // right edge
					if (grid[row - 1][col - 1] == 0) {
						numAliveNeighbors++;
					}
					if (grid[row - 1][col] == 0) {
						numAliveNeighbors++;
					}
					if (grid[row - 1][0] == 0) {
						numAliveNeighbors++;
					}
					if (grid[row][col - 1] == 0) {
						numAliveNeighbors++;
					}
					if (grid[row][0] == 0) {
						numAliveNeighbors++;
					}
					if (grid[row + 1][col - 1] == 0) {
						numAliveNeighbors++;
					}
					if (grid[row + 1][col] == 0) {
						numAliveNeighbors++;
					}
					if (grid[row + 1][0] == 0) {
						numAliveNeighbors++;
					}
				} else { // somewhere in the middle
					if (grid[row - 1][col - 1] == 0) {
						numAliveNeighbors++;
					}
					if (grid[row - 1][col] == 0) {
						numAliveNeighbors++;
					}
					if (grid[row - 1][col + 1] == 0) {
						numAliveNeighbors++;
					}
					if (grid[row][col - 1] == 0) {
						numAliveNeighbors++;
					}
					if (grid[row][col + 1] == 0) {
						numAliveNeighbors++;
					}
					if (grid[row + 1][col - 1] == 0) {
						numAliveNeighbors++;
					}
					if (grid[row + 1][col] == 0) {
						numAliveNeighbors++;
					}
					if (grid[row + 1][col + 1] == 0) {
						numAliveNeighbors++;
					}
				}
				
				/* Use the number of living neighbors to determine if the
				 * next cell should be living or dead in this time step. 
				 * If the cell should be dead, just make the value of the cell
				 * one greater than the value of the cell in the old grid.
				 */
				if (numAliveNeighbors < 2 || numAliveNeighbors > 3) {
					newGrid[row][col] = (grid[row][col] + 1);
				} else if (numAliveNeighbors == 2) {
					if (grid[row][col] > 0) {
						newGrid[row][col] = (grid[row][col] + 1);
					} else {
						newGrid[row][col] = 0;
					}
				} else {
					newGrid[row][col] = 0;
				}
				
			}
		}

		/* Top row - kill everything */

		for (int col = 0; col < grid[0].length; col++) {
			newGrid[0][col] = grid[0][col] + 1;
		}
		
		
		/* bottom row - the bottom corners are special cases since
		 * they wrap around on the left and right sides.  Notice how
		 * the input is being used in place of the row below it, since
		 * this is the bottom row.  Other than that, this is essentially
		 * the same as the loop above. */
		
		int row = grid.length - 1;
		
		for (int col = 0; col < grid[0].length; col++) {
			
			int numAliveNeighbors = 0;
			
			if (col == 0) { // left edge
				if (grid[row - 1][col] == 0) {
					numAliveNeighbors++;
				}
				if (grid[row - 1][col + 1] == 0) {
					numAliveNeighbors++;
				}
				if (grid[row - 1][grid[0].length - 1] == 0) {
					numAliveNeighbors++;
				}
				if (grid[row][col + 1] == 0) {
					numAliveNeighbors++;
				}
				if (grid[row][grid[0].length - 1] == 0) {
					numAliveNeighbors++;
				}
				if (input[input.length - 1] == 0) {
					numAliveNeighbors++;
				}
				if (input[col] == 0) {
					numAliveNeighbors++;
				}
				if (input[col + 1] == 0) {
					numAliveNeighbors++;
				}				
			} else if (col == grid[0].length - 1) { // right edge
				if (grid[row - 1][0] == 0) {
					numAliveNeighbors++;
				}
				if (grid[row - 1][col - 1] == 0) {
					numAliveNeighbors++;
				}
				if (grid[row - 1][col] == 0) {
					numAliveNeighbors++;
				}
				if (grid[row][0] == 0) {
					numAliveNeighbors++;
				}
				if (grid[row][col - 1] == 0) {
					numAliveNeighbors++;
				}
				if (input[0] == 0) {
					numAliveNeighbors++;
				}
				if (input[col - 1] == 0) {
					numAliveNeighbors++;
				}
				if (input[col] == 0) {
					numAliveNeighbors++;
				}
			} else { // neither edge
				if (grid[row - 1][col - 1] == 0) {
					numAliveNeighbors++;
				}
				if (grid[row - 1][col] == 0) {
					numAliveNeighbors++;
				}
				if (grid[row - 1][col + 1] == 0) {
					numAliveNeighbors++;
				}
				if (grid[row][col - 1] == 0) {
					numAliveNeighbors++;
				}
				if (grid[row][col + 1] == 0) {
					numAliveNeighbors++;
				}
				if (input[col - 1] == 0) {
					numAliveNeighbors++;
				}
				if (input[col] == 0) {
					numAliveNeighbors++;
				}
				if (input[col + 1] == 0) {
					numAliveNeighbors++;
				}
			}
			
			if (numAliveNeighbors < 2 || numAliveNeighbors > 3) {
				newGrid[row][col] = grid[row][col] + 1;
			} else if (numAliveNeighbors == 2) {
				if (grid[row][col] > 0) {
					newGrid[row][col] = grid[row][col] + 1;
				} else {
					newGrid[row][col] = 0;
				}
			} else {
				newGrid[row][col] = 0;
			}
		}
		
		return newGrid;
	}

	
	
	

}
