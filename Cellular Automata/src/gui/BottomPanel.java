package gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;

import cellularAutomata.CARowMaker;

/* This handles the bottom bar on the graphical interface, which contains
 * options for the user when running the GUI.  This contains three main
 * sections.  The left, which allows the user to enter a rule to use as input,
 * the middle, which allows the user to select a color theme, and the right,
 * which allows the user to restart the animation with the settings selected.  
 */
public class BottomPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private static final Font BOTTOM_PANEL_FONT = new Font("Calibri", Font.PLAIN, 18);

	
	private GridPanel gridPanel;
	String colorThemeSelected = "Lilac (Default)"; // starts as the default color theme

	/* Constructor for the BottomPanel.  Sets the instance variable gridPanel to the parameter, 
	 * sets the Layout Manager to GridLayout, which by default creates equally-sized components
	 * in a single row.  The constructor then adds the three sub-components described in the 
	 * comment above the class.
	 */
	public BottomPanel(GridPanel gridPanel) {
		this.gridPanel = gridPanel;
		setLayout(new GridLayout());
		RulePrompt rulePrompt = new RulePrompt();
		add(rulePrompt);
		ArrayList<String> colorChoices = new ArrayList<>();
		colorChoices.add("Lilac (Default)");
		colorChoices.add("September");
		colorChoices.add("Sunset");
		colorChoices.add("Cherry Blossoms");
		colorChoices.add("Beach");
		colorChoices.add("Neon");
		add(new ColorPicker<String>(colorChoices));
		add(new RestartButton(rulePrompt));

		this.setPreferredSize(new Dimension(900, 50));
		this.setFocusable(false);

	}

	/* This private class represents the button on the right of the bottom
	 * panel of the GUI.  It allows the user to click it and restart the
	 * animation. 
	 */
	private class RestartButton extends JButton {

		private static final long serialVersionUID = 1L;
		
		/* This is used in the MouseListener for this button */
		private RulePrompt rulePrompt;
		private boolean mousePressed = false;

		/* Constructor for the RestartButton.  The parameter is used to extract the 
		 * rule number that the user entered at the moment when the restart button 
		 * is pressed.
		 */
		private RestartButton(RulePrompt rulePrompt) {
			super("Restart/Update Animation");
			this.rulePrompt = rulePrompt;
			setFocusPainted(false);
			setFocusable(false);
			setFont(BOTTOM_PANEL_FONT);
			addMouseListener(new MouseListener() {

				/* Restarts the animation when the mouse is clicked.  This must
				 * determine which rule number was entered first.
				 */
				@Override
				public void mouseClicked(MouseEvent arg0) {
					/* Using mousePressed() and mouseReleased() instead so that
					 * it still works if the mouse moves slightly in between 
					 * pressing and releasing, in which case it's technically not a 
					 * "mouse click"
					 */

				}

				@Override
				public void mouseEntered(MouseEvent arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mouseExited(MouseEvent arg0) {
					// TODO Auto-generated method stub

				}

				@Override
				public void mousePressed(MouseEvent arg0) {
					mousePressed = true;

				}

				@Override
				public void mouseReleased(MouseEvent e) {
					if (mousePressed) {
						int rule = 0;
						/* The rule entered could either be 3, 2, or 1 digit(s), hence 
						 * the three cases.
						 */
						try {
							String input = rulePrompt.getText(27, 3).replaceAll(" ", "");
							rule = Integer.valueOf(input);
						} catch (BadLocationException | NumberFormatException e1) {
							try {
								String input = rulePrompt.getText(27, 2).replaceAll(" ", "");
								rule = Integer.valueOf(input);
							} catch (BadLocationException | NumberFormatException e2) { 
								try {
									String input = rulePrompt.getText(27, 1).replaceAll(" ", "");
									rule = Integer.valueOf(input);
								} catch (BadLocationException | NumberFormatException e3) {
									rule = 30;

								}

							}

						}
						/* Now use the extracted rule to update the grid */
						try {
							gridPanel.grid.updateRule(new CARowMaker(rule));
						} catch (IllegalArgumentException e5) {
							gridPanel.grid.updateRule(new CARowMaker(30));
							rulePrompt.setText("Enter Rule Number (0-255): 30");
						}
						
						/* use the selection, updated by the ColorPicker, to set the color
						 * theme as needed.
						 */
						switch(colorThemeSelected) {
						case "Lilac (Default)":
							gridPanel.setColorTheme(0);
							break;
						case "September":
							gridPanel.setColorTheme(1);
							break;
						case "Sunset":
							gridPanel.setColorTheme(2);
							break;
						case "Cherry Blossoms":
							gridPanel.setColorTheme(3);
							break;
						case "Beach":
							gridPanel.setColorTheme(4);
							break;
						case "Neon":
							gridPanel.setColorTheme(5);
							break;
						}
						mousePressed = false;
					}
					
				}

			});
		}

	}

	/* This class handles the bottom left portion of the GUI, which allows the user to 
	 * choose a 1-dimensional cellular automata rule to use as input into the game of life.
	 * This class uses a lot of checks, some of which may be inelegant, to ensure that the
	 * user doesn't screw up the prompt too badly so that fatal exceptions are not thrown.
	 */
	private class RulePrompt extends JTextField {
		
		private static final long serialVersionUID = 1L;

		/* The netKeysTyped variable is used to make sure that the user doesn't backspace
		 * too far (and delete part of the prompt).
		 */
		private int netKeysTyped = 0;
		
		private RulePrompt() {
			super("Enter Rule Number (0-255): ");
			setFont(BOTTOM_PANEL_FONT);
			
			/* This keyListener performs some checks every time a key is typed.
			 * This ensures that the prompt remains intact */
			addKeyListener(new KeyListener() {
				 
			    @Override
			    public void keyTyped(KeyEvent event) {
			    	char keyChar = event.getKeyChar();
			        if (keyChar == '\b') {
			        	if (netKeysTyped == 0) {
			        		setText("Enter Rule Number (0-255): ");
			        	} else {
			        		netKeysTyped--;
			        	}
			        } else if (keyChar != '\n'){
			        		netKeysTyped++;
			        }
			        
			        if (getText().length() > 29) {
			    		netKeysTyped--;
			    		setText(getText().substring(0,29));
			    	}
			        
			    }
			 
			    @Override
			    public void keyReleased(KeyEvent event) {
			    	if (getText().length() < 28) {
			        	netKeysTyped = 0;
			        	setText("Enter Rule Number (0-255): ");
			        }
			    	
			    }
			 
			    @Override
			    public void keyPressed(KeyEvent event) {
			    	if (getText().length() < 28) {
			        	netKeysTyped = 0;
			        	setText("Enter Rule Number (0-255): ");
			        }
			    }
			});
		}
	}

	/* This handles the middle portion of the BottomPanel, which allows the user to 
	 * select one of six color themes.  This appears as a drop-down menu whose options
	 * are the names of the color theme.  
	 */
	private class ColorPicker<T> extends JComboBox<T> {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private ColorPicker(ArrayList<T> items) {
			for (T t : items) {
				addItem(t);
			}

			setFont(BOTTOM_PANEL_FONT);
			addItemListener(new ItemListener() {

				/* Whenever a new item is selected, the String variable 
				 * colorThemeSelected (of the BottomPanel object) will
				 * be updated to whichever theme was just selected.
				 * This is then read by the RestartButton when clicked
				 * to determine which color theme to use. 
				 */
				@Override
				public void itemStateChanged(ItemEvent arg0) {
					if (arg0.getStateChange() == ItemEvent.SELECTED) {
						colorThemeSelected = arg0.getItem().toString();
					}

				}

			});
		}
	}


}
