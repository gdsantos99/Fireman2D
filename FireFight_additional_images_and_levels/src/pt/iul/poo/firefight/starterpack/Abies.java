package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.utils.Point2D;

public class Abies extends ForestObject{

	public Abies(Point2D position) {
		super(position);
		this.setName("abies");
	}

	@Override
	public void update() {
		cycles++;
		if (cycles == 20) {
			GameEngine.getInstance().decrementPoints(2);
			burn();
		}
	}

	@Override
	public void burn() {
		this.setName("burntabies");
		setStatus(Status.BURNT);
		GameEngine.getInstance().addObject(this);
	}

	@Override
	public int getLayer() {
		return 0;
	}

	@Override
	public boolean getProbability() {
		return Math.random() <= 0.05;
	}

}
