package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.utils.Point2D;

public class FuelBarrel extends ForestObject {
	
	public FuelBarrel(Point2D position) {
		super(position);
		this.setName("fuelbarrel");
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
		Explosion e = new Explosion(getPosition());
		e.add();
		this.setName("burntfuelbarrel");
		setStatus(Status.BURNT);
		GameEngine.getInstance().addObject(this);
		Fire.barrelExplosion(getPosition());
	}

	@Override
	public int getLayer() {
		return 0;
	}
	
	@Override
	public boolean getProbability() {
		return Math.random() <= 0.9;
	}
}
