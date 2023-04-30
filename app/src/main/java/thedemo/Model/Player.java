package thedemo.Model;

public class Player {
	int playerScore = 0;

	// Pretty self explanatory I hope...
	public int addPoint() {
		this.playerScore += 1;
		return this.playerScore;
	}

	public int getScore() {
		return this.playerScore;
	}

	public void clearScore() {
		this.playerScore = 0;
	}
}
