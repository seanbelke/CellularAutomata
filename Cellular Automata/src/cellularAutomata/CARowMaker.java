package cellularAutomata;

/* This class will generate new rows of the 1-dimensional cellular automaton
 * specified by the user.  
 */
public class CARowMaker {

	/* The 1-dimensional cellular automaton is determined by 8 "cases," each
	 * of which corresponds to one of the 8 configurations of any particular
	 * cell's three neighbors (where each can be alive or dead).  These 8 cases
	 * can be represented by the numbers 0 through 7, which in binary span the 
	 * range 000 through 111.  Each of these booleans determine, for the case
	 * corresponding to their name, whether the cell will be alive or dead in the
	 * next iteration.  For example, the number five in binary is 101.  So, if the
	 * boolean variable 'five' is set to true, then any cell whose left and right
	 * neighbors are alive but whose middle neighbor is dead will be alive in the 
	 * next iteration.  These values are set in the constructor, and are then 
	 * essentially treated as symbolic constants when computing new rows of the cellular
	 * automata.
	 */
	private final boolean zero, one, two, three, four, five, six, seven;

	/* Constructor for CARowMaker.  The parameter 'rule' is used to determine the values
	 * of the eight boolean variables above.  The only valid rules are from 0 to 255, 
	 * since those are the possible values represented by 8 bits.  The constructor extracts
	 * each bit from the rule in order to set the values of the boolean variables.
	 */
	public CARowMaker(int rule) {
		
		/* if an illegal rule number was entered, throw an exception.  
		 * This should be handled by the front end.
		 */
		if (rule < 0 || rule > 255) {
			throw new IllegalArgumentException("Illegal Rule Number: " + rule);
		}
		
		/* Extract each bit from the parameter.  Each individual bit corresponds
		 * to one of the 8 cases for this cellular automaton.  For example, if the
		 * rightmost bit is a 0, then a cell will be dead in the next iteration if
		 * all three of its neighbors are dead.  As another example, if the leftmost
		 * bit is 1, then a cell will be alive in the next iteration if all three of 
		 * its neighbors are alive.
		 */
		zero  = ((rule & 1) == 1)? true : false;
		one   = (((rule >>> 1) & 1) == 1)? true : false;
		two   = (((rule >>> 2) & 1) == 1)? true : false;
		three = (((rule >>> 3) & 1) == 1)? true : false;
		four  = (((rule >>> 4) & 1) == 1)? true : false;
		five  = (((rule >>> 5) & 1) == 1)? true : false;
		six   = (((rule >>> 6) & 1) == 1)? true : false;
		seven = (((rule >>> 7) & 1) == 1)? true : false;		

	}

	/* This method generates a single row of the cellular automaton given 
	 * the previous row.  Each location examines its three neighbors from 
	 * the previous row to determine whether or not it will be dead or 
	 * alive in the newly generated row.  The boolean instance variables
	 * determine this for each case, as is described in comments near these
	 * variables.  
	 * 
	 * Note that the row is an array of integers, rather than an array of 
	 * booleans.  This is because the end goal of this project is for dead
	 * cells to be colored in differently on the GUI depending on how long 
	 * they have been dead for.  Therefore, we must use an integer type to 
	 * keep track of how long it has been since a cell died.  Specifically,
	 * a value of 0 will represent a living cell, and a positive value will
	 * represent a dead cell.  The only case in which a cell's value would 
	 * be negative is if the program ran for so long that a cell's value 
	 * overflowed, but due to the range provided by an int, this would not
	 * happen for a very long time.  The positive value of a cell represents
	 * the number of time steps since the cell died.  So, if a cell's value 
	 * is 6, it has been dead for 6 time steps.  
	 * 
	 * Finally, note that the left and right sides "wrap around."  In other
	 * words, the right neighbor of the cell in the rightmost location in any
	 * given row is the cell in the leftmost location in that row. 
	 */
	public int[] nextRow(int[] row) {
		
		/* initialize the new row to be the same length as the old row */		
		int[] newRow = new int[row.length];

		/* instead of examining all three neighbors for every cell, we can
		 * just examine the rightmost neighbor each time, and shift the other
		 * neighbors over.  This is because the middle and right neighbors of
		 * the cell in location i are the left and middle neighbors of the cell
		 * in location (i + 1), respectively.  
		 */
		
		/* initialize the three neighbor variables.  Notice how they are being 
		 * turned into bits.  This is so that they can be easily formed into a
		 * 3-bit binary number to determine which case to use (0-7).
		 */
		int left = (row[row.length - 1] == 0)? 1 : 0;
		int middle = (row[0] == 0)? 1 : 0;
		int right = (row[1] == 0)? 1 : 0;
		
		/* Use bit operations to form a 3-bit binary number */
		int cellCase = ((((0 | left) << 1) | middle) << 1) | right;

		/* This loop handles every case except for the rightmost two cells */
		for (int i = 0; i < newRow.length - 2; i++) {
			
			/* Use the cellCase (the three bit number) to determine whether the cell
			 * will be alive or dead.  If it is dead, just make the value one greater
			 * than the value from the previous row.
			 */
			switch(cellCase) {
			case 0:
				if (zero) {
					newRow[i] = 0;
				} else {
					newRow[i] = (row[i] + 1);
				}
				break;
			case 1:
				if (one) {
					newRow[i] = 0;
				} else {
					newRow[i] = (row[i] + 1);
				}
				break;
			case 2:
				if (two) {
					newRow[i] = 0;
				} else {
					newRow[i] = (row[i] + 1);
				}
				break;
			case 3:
				if (three) {
					newRow[i] = 0;
				} else {
					newRow[i] = (row[i] + 1);
				}
				break;
			case 4:
				if (four) {
					newRow[i] = 0;
				} else {
					newRow[i] = (row[i] + 1);
				}
				break;
			case 5:
				if (five) {
					newRow[i] = 0;
				} else {
					newRow[i] = (row[i] + 1);
				}
				break;
			case 6:
				if (six) {
					newRow[i] = 0;
				} else {
					newRow[i] = (row[i] + 1);
				}
				break;
			case 7:
				if (seven) {
					newRow[i] = 0;
				} else {
					newRow[i] = (row[i] + 1);
				}
				break;
			}
			
			/* Update the neighbors, examine the new right neighbor, and form
			 * the new cellCase number.
			 */
			left = middle;
			middle = right;
			right = (row[i + 2] == 0)? 1 : 0;

			cellCase = ((((0 | left) << 1) | middle) << 1) | right;

		}

		/* I could have used a branch inside of the loop, but I decided to 
		 * have two special cases instead, which does unfortunately make this
		 * method have a bit of redundancy.
		 */
		
		/* Handle the second to last cell */
		int i = newRow.length - 2;

		switch(cellCase) {
		case 0:
			if (zero) {
				newRow[i] = 0;
			} else {
				newRow[i] = (row[i] + 1);
			}
			break;
		case 1:
			if (one) {
				newRow[i] = 0;
			} else {
				newRow[i] = (row[i] + 1);
			}
			break;
		case 2:
			if (two) {
				newRow[i] = 0;
			} else {
				newRow[i] = (row[i] + 1);
			}
			break;
		case 3:
			if (three) {
				newRow[i] = 0;
			} else {
				newRow[i] = (row[i] + 1);
			}
			break;
		case 4:
			if (four) {
				newRow[i] = 0;
			} else {
				newRow[i] = (row[i] + 1);
			}
			break;
		case 5:
			if (five) {
				newRow[i] = 0;
			} else {
				newRow[i] = (row[i] + 1);
			}
			break;
		case 6:
			if (six) {
				newRow[i] = 0;
			} else {
				newRow[i] = (row[i] + 1);
			}
			break;
		case 7:
			if (seven) {
				newRow[i] = 0;
			} else {
				newRow[i] = (row[i] + 1);
			}
			break;
		}

		i++;

		/* Handle the final cell.  Note the wrap-around to the left side for the
		 * right neighbor */
		left = middle;
		middle = right;
		right = (row[0] == 0)? 1 : 0;

		cellCase = ((((0 | left) << 1) | middle) << 1) | right;

		
		switch(cellCase) {
		case 0:
			if (zero) {
				newRow[i] = 0;
			} else {
				newRow[i] = (row[i] + 1);
			}
			break;
		case 1:
			if (one) {
				newRow[i] = 0;
			} else {
				newRow[i] = (row[i] + 1);
			}
			break;
		case 2:
			if (two) {
				newRow[i] = 0;
			} else {
				newRow[i] = (row[i] + 1);
			}
			break;
		case 3:
			if (three) {
				newRow[i] = 0;
			} else {
				newRow[i] = (row[i] + 1);
			}
			break;
		case 4:
			if (four) {
				newRow[i] = 0;
			} else {
				newRow[i] = (row[i] + 1);
			}
			break;
		case 5:
			if (five) {
				newRow[i] = 0;
			} else {
				newRow[i] = (row[i] + 1);
			}
			break;
		case 6:
			if (six) {
				newRow[i] = 0;
			} else {
				newRow[i] = (row[i] + 1);
			}
			break;
		case 7:
			if (seven) {
				newRow[i] = 0;
			} else {
				newRow[i] = (row[i] + 1);
			}
			break;
		}
		
		/* Return the completed row */
		return newRow;

	}
}