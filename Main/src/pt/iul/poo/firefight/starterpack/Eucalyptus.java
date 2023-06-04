package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.utils.Point2D;

public class Eucalyptus extends ForestObject {

	public Eucalyptus(Point2D position) {
		super(position);
		this.setName("eucaliptus");
	}
	
	@Override
	public void update() {
		cycles++;
		if(cycles == 5) {
			GameEngine.getInstance().decrementPoints(2);
			burn();
		}
	}
	
	@Override
	public void burn() {
		this.setName("burnteucaliptus");
		setStatus(Status.BURNT);
		GameEngine.getInstance().addObject(this);
	}

	@Override
	public int getLayer() {
		return 0;
	}

	@Override
	public boolean getProbability() {
		return Math.random() <= 0.1;
	}

}
