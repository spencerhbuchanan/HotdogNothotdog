package thedemo.View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class RootWindow {

	private JFrame window; // Main window for UI elements to reside in
	private JPanel gamePanel; // Central game panel
	private JPanel scorePanel; // Top Panel used to hold the score
	private JLabel scoreLabel; // Label for the score
	private JPanel controls; // Botom panale to hold the controls
	private JLabel hotdogImage; // Label used ot display the image
	private int initX = 800; // Initial sizes for the window
	private int initY = 400;

	// Map to store the buttons for future reference
	Map<String, JButton> controlButtons = new HashMap<String, JButton>();

	public RootWindow() {
		// Setting up the main window frame for the program. We instantiate a new
		// JFrame, which is a Swing container that is a top level item that has the main
		// features and capabilities of a window in a GUI application, such as the title
		// bar, can have menu bar (file, edit, etc), and acts as the top-level container
		// for other items in the GUI
		this.window = new JFrame();
		this.window.setTitle("Is Hotdog?");

		// Sets the default close operation. Normally, it is set up so that the window simply
		// dissapears from view and your program will still run in the background
		// (Rob, this is why you had to terminate the program every time you closed the
		// window. The cloe button was not ending the progoram)
		this.window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// Setting the intial size for the window, and setting it to be resizeable
		this.window.setSize(initX, initY);
		this.window.setResizable(true);

		// Setting the layout manager to a boder layout with a padding of 5 between element 'zones'
		this.window.setLayout(new BorderLayout(5, 5));

		// Initializing the game panels
		initScorePanel(this.window);
		initGamePanel(this.window);
		initBottomControls(this.window);

		// Passing 'null' to the set relative to will put the window in the center of the screen
		this.window.setLocationRelativeTo(null);
	}

	public void show() {
		this.window.setVisible(true);

		postInit();
	}

	public void postInit() {
		initImage();
	}

	public void update() {
		// Called to refresh/update the window
		this.window.revalidate();
	}

	private void initScorePanel(JFrame parent) {
		// Adds a new JPanel for the score panel, using a centered flow layout with 10 horizontal
		// and 5 vertical padding (I think. 'FlowLayout' does not have proper documentation for the
		// parameters...)
		this.scorePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
		// Adding the score panel to the parent panel, in the north zone
		parent.add(this.scorePanel, BorderLayout.NORTH);

		// Setting the initial value of the score label and adding it to the score panel
		this.scoreLabel = new JLabel("Score : 0");
		this.scorePanel.add(this.scoreLabel);
	}

	public void setScore(int score) {
		// Setting the score, using string concatenation
		this.scoreLabel.setText("Score : " + String.valueOf(score));
	}

	private void initGamePanel(JFrame parent) {
		// Adds a new JPanel for the game window, adding it to the parent frame in the center zone,
		// setting the color of the background to cyan, and setting the layout manager fo r the game
		// panel
		this.gamePanel = new JPanel();
		parent.add(this.gamePanel, BorderLayout.CENTER);
		this.gamePanel.setBackground(Color.CYAN);
		this.gamePanel.setLayout(new BorderLayout());
	}

	public void initImage() {
		// Try to add an image, required due to "throws" BS...
		try {
			// Load the default image
			BufferedImage rawImage = ImageIO.read(this.getClass().getResource("/hotdogImages/firstDog.jpg"));

			// Scale the image to fit within the frame, and scale down to 0.75 size after
			Image dispImage = scaleImageToFit(rawImage, this.gamePanel.getWidth(), this.gamePanel.getHeight(), 0.75f);

			// Adding the image to the label
			this.hotdogImage = new JLabel(new ImageIcon(dispImage));

			// Adding JLabel to the game panel
			this.gamePanel.add(this.hotdogImage, BorderLayout.CENTER);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void changeImage(URL imagePath) {
		try {
			// Scale the passed image down to fit and then 75% size
			Image dispImage = scaleImageToFit(ImageIO.read(imagePath), this.gamePanel.getWidth(),
					this.gamePanel.getHeight(), 0.75f);

			// Changing the image shown on the label
			this.hotdogImage.setIcon(new ImageIcon(dispImage));
		} catch (IOException e) {
			e.printStackTrace();
		}

		update();
	}

	// Unused as of now
	// private Image scaleImageToFit(BufferedImage rawImage, float fitW, float fitH) {
	// return scaleImageToFit(rawImage, fitW, fitH, 1f);
	// }

	private Image scaleImageToFit(BufferedImage rawImage, float fitW, float fitH, float additionalScaling) {
		// Get the size of the passed image
		float imgW = rawImage.getWidth();
		float imgH = rawImage.getHeight();

		// Determien aspect ratios
		float imgAspectRatio = imgW / imgH;
		float fitAspectRatio = fitW / fitH;

		// Init the scale factor
		float scaleFactor = 1.0f;

		// Determine how we should scale the image based on each items aspect ratio
		if (fitAspectRatio < imgAspectRatio) {
			// Scale down width, height will fit due to aspect ratio
			scaleFactor = fitW / imgW;
		} else {
			// Scale down height, width will fit due to aspect ratio
			scaleFactor = fitH / imgH;
		}

		// Apply any additional scaling passed
		scaleFactor *= additionalScaling;

		// Scale the image
		Image retImage = rawImage.getScaledInstance((int) (scaleFactor * imgW), (int) (scaleFactor * imgH),
				Image.SCALE_SMOOTH);

		return retImage;
	}

	private void initBottomControls(JFrame parent) {
		// New JPanel, layout manager, you get it...
		this.controls = new JPanel();
		this.controls.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));

		parent.add(this.controls, BorderLayout.SOUTH);
		this.controls.setBackground(Color.RED);

	}

	public void addControlButton(String buttonName, ActionListener listener) {
		// Adding a new control button
		JButton addedButton = new JButton(buttonName);
		// Removign the focusable so we do not have a box around it
		addedButton.setFocusable(false);
		// Adding to the controls
		this.controls.add(addedButton);
		// Adding to the Map for later
		this.controlButtons.put(buttonName, addedButton);
		// Setup the handler
		addControlClickHandler(buttonName, listener);

		// Update the window
		update();
	}

	public void removeControl(String buttonName) {
		// Remove the specified button from the controls and map
		this.controls.remove(this.controlButtons.get(buttonName));
		this.controlButtons.remove(buttonName);

		update();
	}

	public void addControlClickHandler(String controlName, ActionListener listener) {
		// Adding the listener to the button
		this.controlButtons.get(controlName).addActionListener(listener);
	}
}
