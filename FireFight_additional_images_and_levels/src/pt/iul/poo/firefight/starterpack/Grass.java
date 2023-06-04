package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.utils.Point2D;

public class Grass extends ForestObject {

	public Grass(Point2D position) {
		super(position);
		this.setName("grass");
	}
	
	@Override
	public void update() {
		cycles++;
		if(cycles == 3) {
			GameEngine.getInstance().decrementPoints(2);
			burn();
		}
	}
	
	@Override
	public void burn() {
		this.setName("burntgrass");
		setStatus(Status.BURNT);
		GameEngine.getInstance().addObject(this);
	}
	
	
	@Override
	public int getLayer() {
		return 0;
	}

	@Override
	public boolean getProbability() {
		return Math.random() <= 0.15;
	}
	
}
