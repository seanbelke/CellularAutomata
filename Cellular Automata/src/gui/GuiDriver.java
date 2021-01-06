package gui;

import javax.swing.JFrame;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/* Starts the Graphical User Interface.  
 * Includes the main method for this project. */
public class GuiDriver {

	public static void main(String[] args) {
		/* Send a request to the Event-Dispatching Thread to initialize the
		 * Graphical User Interface. The main thread then dies and the rest
		 * is up to the EDT.
		 */
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
				createAndDisplayFrame();
			}
		});
	}
	
	/* This is the method called by a Runnable object instantiated by the Event-Dispatching
	 * Thread in the main method. It initializes the GUI by creating the JFrame and the panel
	 * inside of it. This panel will be an instance of the class MainPanel, which will take care
	 * of the rest of the work needed to get the animation started inside of its constructor.
	 */
	public static void createAndDisplayFrame() {
		/* Set the Look and Feel of the interface to be the system default look and feel.
		 * This program was written on a Windows PC, so it's possible that this will 
		 * introduce some minor misalignments on other systems.  However, I still think that
		 * it's worth it since I do not like the way the buttons look on the Cross-Platform 
		 * Look and Feel (the Java Default).
		 */
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		/* Fairly standard paradigm for initializing the JFrame for a GUI. */
		JFrame frame = new JFrame("Cellular Automata and Conway's Game of Life");
		frame.setContentPane(new MainPanel());  // Sets the panel inside the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setSize(890, 726); // determined through trial and error.
		
	}

}