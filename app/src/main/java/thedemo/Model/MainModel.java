package thedemo.Model;

import java.util.Random;

import java.net.URL;

public class MainModel {
	static int numImages = 7;
	boolean recentIsHotdog = false;
	Random randObj = new Random();

	public URL getRandomImage() {
		// Should we call for a hotdog or nothotdog?
		this.recentIsHotdog = this.randObj.nextBoolean();

		// Determine first part of file path based on what we are choosing
		String hotdogString = this.recentIsHotdog ? "dog" : "not";

		// Determine random number to use for hotdog images
		String hotdogNum = String.valueOf(this.randObj.nextInt(numImages - 1) + 1);

		// Concatenates the strings to build the hotdog/nothotdog image name
		hotdogString = hotdogString + hotdogNum + ".jpg";

		// Returns the get image
		return getImageURL(hotdogString);
	}

	// Returns if the image was a hotdog
	public boolean wasHotdog() {
		return this.recentIsHotdog;
	}

	private URL getImageURL(String imageName) {
		// Gets the image (explain the getResource thing)
		return MainModel.class.getResource("/hotdogImages/" + imageName);
	}

}
