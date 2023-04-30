package thedemo.Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

import thedemo.Model.MainModel;
import thedemo.Model.Player;
import thedemo.View.RootWindow;

public class MainControl {

	RootWindow root; // Root window display
	MainModel mainModel = new MainModel(); // The model used to get the images
	Player player = new Player(); // Player model
	boolean gameStarted = false; // Used to determine what the start/restart button should be

	public MainControl() {
		// Setup the invocation for the runnable window object
		SwingUtilities.invokeLater(new RunThis());
	}

	// Create a runnable for the window, used to keep the UI in a single thread (Swing recommen)
	private class RunThis implements Runnable {

		@Override
		public void run() {
			// Setup the root window
			root = new RootWindow();
			root.show();

			// Add the start button
			root.addControlButton("Start", new StartGameListener());
		}

	}

	public void playerSelected(boolean choseIsHotdog) {
		// Called when the player makes an input, called from the hotdog/nothotdog listeners
		// Checks whether the image was a hotdog
		boolean wasHotdog = mainModel.wasHotdog();

		// If player chose correctly, increase score and get new image
		if (wasHotdog == choseIsHotdog) {
			player.addPoint();
			root.setScore(player.getScore());
			root.changeImage(mainModel.getRandomImage());
		} else {
			// If player chose wrong, clear score and go to playerLost function
			player.clearScore();
			playerLost();
		}
	}

	public void playerLost() {
		// Removing the buttons
		root.removeControl("Hotdog");
		root.removeControl("Not Hotdog");
		// Setting the Loss.jpg image
		root.changeImage(MainControl.class.getResource("/hotdogImages/loss.jpg"));

		// Adding the restart button and its handler
		root.addControlButton("Restart", new StartGameListener());
	}

	// Hotdog listeners, listens for buton presses and which one was pressed. Then calls
	// "playerSelected" with the choice if the player chose "is" or "isnot" hotdog
	class HotdogListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			playerSelected(true);
		}

	}

	class NotHotdogListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			playerSelected(false);
		}

	}

	// Start game listener called when playe selects start or restart?
	class StartGameListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// Check if the game was started before, and whether we should remove the start or
			// restart button
			if (gameStarted) {
				root.removeControl("Restart");
			} else {
				root.removeControl("Start");
			}

			// Resets the score display (allows round score to be displayed)
			root.setScore(0);

			// Adds the controls
			root.addControlButton("Hotdog", new HotdogListener());
			root.addControlButton("Not Hotdog", new NotHotdogListener());

			// Updates the image
			root.changeImage(mainModel.getRandomImage());

			// Sets that the game has started
			gameStarted = true;
		}
	}
}
