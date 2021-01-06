package gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;

/* Container for the GridPanel, which displays the grid of cells for the 
 * animation, as well as the BottomPanel, which contains options for the
 * user to modify the appearance of the animation.  
 */
public class MainPanel extends JPanel{	
	
	private static final long serialVersionUID = 1L;

	/* Constructor for the MainPanel.  Sets the layoutmanager to BorderLayout, since
	 * the GridPanel will take up the majority of the screen (so I'll place that in
	 * BorderLayout.CENTER, which is given any extra space) and the BottomPanel will
	 * naturally go in BorderLayout.SOUTH */
	public MainPanel() {
		setLayout(new BorderLayout());
		GridPanel gridPanel = new GridPanel();
		add(gridPanel, BorderLayout.CENTER);
		add(new BottomPanel(gridPanel), BorderLayout.SOUTH);
		
	}

}
