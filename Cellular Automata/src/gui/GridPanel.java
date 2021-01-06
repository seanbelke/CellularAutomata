package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

import cellularAutomata.Grid;

public class GridPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private static final int BOX_SIZE = 3;
	private static final int BLACK_THRESHOLD = 5000;
	
	/* The following colors will be changed depending on what color
	 * scheme is chosen.  However, there will always be a fixed number
	 * of colors used for each color scheme.  color1 is the first major
	 * color used for dead cells.  trans1_1 and trans1_2 are the 
	 * transition colors between white and color1.  trans2_1 and trans2_2
	 * are the transition colors between color1 and the starting color
	 * of the finalFade.  The finalFade is a progression of colors with
	 * similar hue but decreasing luminosity.  For example, the first color
	 * in finalFade might be a light blue, and in that case the subsequent
	 * colors would be darker and darker shades of blue.  
	 */
	private Color color1;
	private Color trans1_1;
	private Color trans1_2;
	private Color trans2_1;
	private Color trans2_2;
	private Color[] finalFade;
	
	Grid grid; // stores the information displayed by the GridPanel
	
	public GridPanel() {
		grid = new Grid();
		
		/* set default color scheme (defaults to "Lilac") */
		setColorTheme(0);
		
		/* Every 35 milliseconds, move to the next time-step (update the grid)
		 * and then repaint the grid
		 */
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				grid.update();
				repaint();
			}
		}, 0, 35);
	}
	
	/* This method is used to set the color scheme being used to display the living
	 * and dead cells.  This is used in the constructor for this class.  It is also
	 * called in the BottomPanel class to change the color scheme when another color
	 * is selected by the user and the animation is restarted.  6 color schemes are 
	 * supported, and they are described briefly in comments within the method.
	 */
	public void setColorTheme(int colorCode) {
		switch(colorCode) {
		case 0: // default color scheme (pink and blue) - named "Lilac"
			color1 = new Color(0xff09db); 
			trans1_1 = new Color(0xff80ec); 
			trans1_2 = new Color(0xff54e5); 
			trans2_1 = new Color(0xc73fff); 
			trans2_2 = new Color(0x935cff); 
			
			finalFade = new Color[5000]; 

			float r = 30f, g = 240f, b = 254f;

			for (int i = 0; i < finalFade.length; i++) {
				finalFade[i] = new Color((int)r, (int)g, (int)b);
				if (b > 150) {
					r *= 0.99;
					g *= 0.966;
					b *= 0.99;
				} else if (b > 90) {
					r *= 0.998;
					g *= 0.996;
					b *= 0.998;
				} else {
					r *= 0.999;
					g *= 0.999;
					b *= 0.999;
				}
			}
			break;
		case 1: // summer to autumn color scheme - named "September"
			color1 = new Color(0x37a13a); 
			trans1_1 = new Color(0x5eab60); 
			trans1_2 = new Color(0x4fab51); 
			trans2_1 = new Color(0xacc756); 
			trans2_2 = new Color(0xc7a756); 
			
			finalFade = new Color[5000]; 

			r = 219f; 
			g = 135f; 
			b = 79f;

			for (int i = 0; i < finalFade.length; i++) {
				finalFade[i] = new Color((int)r, (int)g, (int)b);
				if (r > 150) {
					r *= 0.99;
					g *= 0.966;
					b *= 0.966;
				} else if (r > 90) {
					r *= 0.998;
					g *= 0.996;
					b *= 0.996;
				} else {
					r *= 0.999;
					g *= 0.999;
					b *= 0.999;
				}
			}
			break;
		case 2: // orange/red hues to purple/blue hues - named "Sunset"
			color1 = new Color(0xec7034); 
			trans1_1 = new Color(0xeda02d); 
			trans1_2 = new Color(0xd48633); 
			trans2_1 = new Color(0xb835b4); 
			trans2_2 = new Color(0xb8357d); 
			
			finalFade = new Color[5000]; 

			r = 184f; 
			g = 53f; 
			b = 53f;

			for (int i = 0; i < finalFade.length; i++) {
				finalFade[i] = new Color((int)r, (int)g, (int)b);
				if (r > 150) {
					r *= 0.99;
					g *= 0.966;
					b *= 0.99;
				} else if (r > 100) {
					r *= 0.99;
					g *= 0.90;
					b *= 1.01;
				} else {
					r *= 0.999;
					b *= 0.9995;
				}
			}
			break;
		case 3: // pale pinks to deeper reds
			color1 = new Color(0xffb7c5); 
			trans1_1 = new Color(0xe8dfe4); 
			trans1_2 = new Color(0xe8d3d1); 
			trans2_1 = new Color(0xdfb1b6); 
			trans2_2 = new Color(0xcea19f); 
			
			finalFade = new Color[5000]; 

			r = 191f; 
			g = 120f; 
			b = 133f;

			for (int i = 0; i < finalFade.length; i++) {
				finalFade[i] = new Color((int)r, (int)g, (int)b);
				if (b > 150) {
					r *= 0.99;
					g *= 0.99;
					b *= 0.99;
				} else if (b > 100) {
					r *= 0.996;
					g *= 0.996;
					b *= 0.996;
				} else {
					r *= 0.999;
					g *= 0.9975;
					b *= 0.9985;
				}
			}
			break;
		case 4: // beach - yellow/orange for the sand/sun and then teal/blue for the ocean
			color1 = new Color(0xffaa01); 
			trans1_1 = new Color(0xe1ef7e);
			trans1_2 = new Color(0xEFCDBB); 
			trans2_1 = new Color(0xae8f60); 
			trans2_2 = new Color(0x8cae60); 
			
			finalFade = new Color[5000];

			r = 18f; 
			g = 178f; 
			b = 151f;

			for (int i = 0; i < finalFade.length; i++) {
				finalFade[i] = new Color((int)r, (int)g, (int)b);
				if (g > 123) {
					r *= 0.99;
					g *= 0.99;
					b *= 1.001;
				} else if (b > 70) {
					r *= 0.996;
					g *= 0.996;
					b *= 0.996;
				} else {
					r *= 0.999;
					g *= 0.999;
					b *= 0.999;
				}
			}
			break;
		case 5: // neon - this one is a lot different than the others
			color1 = new Color(0x011ffd); 
			trans1_1 = new Color(0x75d5fd);
			trans1_2 = new Color(0x5b79f5); 
			trans2_1 = new Color(0x9f6cfd); 
			trans2_2 = new Color(0xb76cfd); 
			
			finalFade = new Color[5000]; 

			r = 159f; 
			g = 108f; 
			b = 253f;
			
			int i = 0; 
			
			while (r < 241) {
				r *= 1.01;
				finalFade[i++] = new Color((int)r, (int)g, (int)b);
			}
			
			while (g > 39) {
				r *= 0.992;
				g *= 0.95;
				b *= 0.95;
				finalFade[i++] = new Color((int)r, (int)g, (int)b);
			}
			
			while (g < 104) {
				g *= 1.01;
				b *= 0.98;
				finalFade[i++] = new Color((int)r, (int)g, (int)b);
			}
			
			while (g < 224) {
				g *= 1.02;
				r *= 1.001;
				finalFade[i++] = new Color((int)r, (int)g, (int)b);
			}
			
			while (b < 224) {
				r *= 0.97;
				b *= 1.015;
				finalFade[i++] = new Color((int)r, (int)g, (int)b);
			}
			
			while (i < finalFade.length) {
				r *= 0.985;
				g *= 0.985;
				b *= 0.985;
				finalFade[i++] = new Color((int)r, (int)g, (int)b);
			}
			break;
			
			
		}
	}
	
	/* This method is called to paint this component.  It uses the currently
	 * selected color scheme to color in each cell either white, if it is
	 * alive, or some other color (which depends on the color scheme) if it 
	 * is dead.  
	 */
	public void paint(Graphics g) {
		int startingX = ((-2) * BOX_SIZE), startingY = ((-5) * BOX_SIZE);

		/* paint the gameOfLifeRegion first */
		
		int[][] life = grid.getGameOfLifeRegion();
		
		int val = 0;
		for (int row = 5; row < Grid.NUM_GRID_ROWS; row++) {
			for (int col = 2; col < Grid.NUM_GRID_COLS - 2; col++) {
				val = life[row][col];
				if (val == 0) {
					g.setColor(Color.WHITE);
				} else if (val > BLACK_THRESHOLD) {
					g.setColor(Color.BLACK);
				} else if (val == 1) {
					g.setColor(trans1_1);
				} else if (val == 2) {
					g.setColor(trans1_2);
				} else if (val < 6) {
					g.setColor(color1);
				} else if (val == 6) {
					g.setColor(trans2_1);
				} else if (val == 7) {
					g.setColor(trans2_2);
				} else if (val < BLACK_THRESHOLD) {
					g.setColor(finalFade[val - 8]);
				}
				g.fillRect(startingX + (col * BOX_SIZE), startingY + (row * BOX_SIZE), 
						BOX_SIZE, BOX_SIZE);
			}
		}
		
		startingY = (Grid.NUM_GRID_ROWS - 6) * BOX_SIZE;
		
		/* Now paint the input section */
		
		int[][] input = grid.getInputRegion();
		
		for (int row = 0; row < Grid.NUM_INPUT_ROWS; row++) {
			for (int col = 2; col < Grid.NUM_GRID_COLS - 2; col++) {
				val = input[row][col];
				if (val == 0) {
					g.setColor(Color.WHITE);
				} else if (val > BLACK_THRESHOLD) {
					g.setColor(Color.BLACK);
				} else if (val == 1) {
					g.setColor(trans1_1);
				} else if (val == 2) {
					g.setColor(trans1_2);
				} else if (val < 6) {
					g.setColor(color1);
				} else if (val == 6) {
					g.setColor(trans2_1);
				} else if (val == 7) {
					g.setColor(trans2_2);
				} else if (val < BLACK_THRESHOLD) {
					g.setColor(finalFade[val - 8]);
				}
				g.fillRect(startingX + (col * BOX_SIZE), startingY + (row * BOX_SIZE), 
						BOX_SIZE, BOX_SIZE);
			}
		}
	}

}
