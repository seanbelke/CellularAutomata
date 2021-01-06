package cellularAutomata;

/* This class will store all of the information about the current
 * state of the grid being displayed by the graphical interface.
 * This includes the entire region of the screen where Conway's
 * Game of Life is being run, as well as the region of the screen
 * where the input rows are displayed, as well as a small margin
 * of extra information that is not being displayed by the screen.
 */
public class Grid {
	
	/* The values for the symbolic constants below were determined by 
	 * trial and error, in conjunction with the size of the GUI's JFrame.
	 */
	public static final int NUM_GRID_ROWS = 172;
	public static final int NUM_GRID_COLS = 299;
	public static final int NUM_INPUT_ROWS = 50;
	
	private CARowMaker rowMaker; // Creates new input rows
	
	private int[][] gameOfLifeRegion; // main section of the grid
	private int[][] inputRows; // input section (below the gameOfLifeRegion)
	
	/* Instance initializer block that sets the entire grid to be full of dead cells, 
	 * except for a single living cell at the bottom of the input.
	 */
	{
		gameOfLifeRegion = new int[NUM_GRID_ROWS][NUM_GRID_COLS]; 
		for (int i = 0; i < NUM_GRID_ROWS; i++) {
			for (int j = 0; j < NUM_GRID_COLS; j++) {
				gameOfLifeRegion[i][j] = 100000; // all cells start as dead
			}
		}
		
		inputRows = new int[NUM_INPUT_ROWS][NUM_GRID_COLS];
		for (int i = 0; i < NUM_INPUT_ROWS; i++) {
			for (int j = 0; j < NUM_GRID_COLS; j++) {
				inputRows[i][j] = 100000; // all cells start as dead
			}
		}
		
		// place the single living cell in the center of the bottom row
		inputRows[NUM_INPUT_ROWS - 1][NUM_GRID_COLS / 2] = 0;
		
	}
	
	/* Constructor that has one parameter, a CARowMaker object, and uses
	 * that parameter to generate new input rows.
	 */
	public Grid(CARowMaker rowMaker) {
		this.rowMaker = rowMaker;		
	}
	
	/* No-arg constructor, defaults to rule 30 for the input. */
	public Grid() {
		this(new CARowMaker(30));
	}
	
	/* getters for the two sections of the grid.  Note that these getters introduce a 
	 * privacy leak, and as such the caller of these methods must not modify the returned
	 * arrays.  This is for efficiency, since making a deep copy every time these are 
	 * called (which happens every time step) would be quite time consuming.  Another 
	 * possible option would have been to only provide methods to query a specific location
	 * on the grid. */
	
	public int[][] getGameOfLifeRegion() {
		return gameOfLifeRegion;
	}
	
	public int[][] getInputRegion() {
		return inputRows;
	}
	
	/* This method performs a single time step in the game.  It will shift the input rows up, 
	 * create a new bottom row for the input using the CARowMaker instance variable,
	 * and then use the new top row of input to update the gameOfLifeRegion. */
	public void update() {
		
		/* shift the rows up */
		for (int i = 0; i < NUM_INPUT_ROWS - 1; i++) {
			inputRows[i] = inputRows[i + 1];
		}
		
		/* create the new bottom row */
		inputRows[NUM_INPUT_ROWS - 1] = rowMaker.nextRow(inputRows[NUM_INPUT_ROWS - 1]);
		
		/* update the gameOfLifeRegion using the new top input row */
		gameOfLifeRegion = GameOfLife.updateGrid(gameOfLifeRegion, inputRows[0]);
		
	}
	
	/* This method is essentially identical to the instance initializer block at the top
	 * of this class.  It sets the grid to be full of all dead cells, apart from a single
	 * living cell in the bottom of the input.  
	 */
	public void resetGrid() {
		gameOfLifeRegion = new int[NUM_GRID_ROWS][NUM_GRID_COLS]; 
		for (int i = 0; i < NUM_GRID_ROWS; i++) {
			for (int j = 0; j < NUM_GRID_COLS; j++) {
				gameOfLifeRegion[i][j] = 100000; // all cells start as dead
			}
		}
		
		inputRows = new int[NUM_INPUT_ROWS][NUM_GRID_COLS];
		for (int i = 0; i < NUM_INPUT_ROWS; i++) {
			for (int j = 0; j < NUM_GRID_COLS; j++) {
				inputRows[i][j] = 100000; // all cells start as dead
			}
		}
		
		// place the single living cell in the center of the bottom row
		inputRows[NUM_INPUT_ROWS - 1][NUM_GRID_COLS / 2] = 0;
	}
	
	/* This method allows the caller to change the Cellular Automata rule being used
	 * to generate new input rules.  This method also forces the grid to be reset, 
	 * so it does not support changing the rule used in the middle of the animation.
	 */
	public void updateRule(CARowMaker newRule) {
		rowMaker = newRule;
		resetGrid();
	}

}
