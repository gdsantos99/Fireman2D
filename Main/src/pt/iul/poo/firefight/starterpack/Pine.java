package pt.iul.poo.firefight.starterpack;
import pt.iul.ista.poo.utils.Point2D;

public class Pine extends ForestObject {
	
	public Pine(Point2D position) {
		super(position);
		this.setName("pine");
	}

	@Override
	public void update() {
		cycles++;
		if(cycles == 10) {
			GameEngine.getInstance().decrementPoints(2);
			burn();
		}
	}
	
	@Override
	public void burn() {
		this.setName("burntpine");
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
