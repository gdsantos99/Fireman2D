package pt.iul.poo.firefight.starterpack;


public class PlayerObject {

	private String nickname;
	private int points;

	public PlayerObject(String nickname, int points) {
		this.setNickname(nickname);
		this.setPoints(points);
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	@Override
	public String toString() {
		return "Player " + nickname + " got " + points + " points";
	}
}
