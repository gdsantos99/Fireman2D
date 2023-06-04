package pt.iul.poo.firefight.starterpack;

import java.util.List;

import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public abstract class MovableObject extends GameElements implements Movable {
	
	protected String name;
	protected boolean beingUsed;
	
	public MovableObject(Point2D position) {
		super(position);
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public abstract void changeImage(Direction dir);

	public abstract void move();

	public boolean canMoveTo(Point2D p) {
		List<GameElements> fires = GameEngine.getInstance().getObjects(o -> o instanceof Fire);
		for (GameElements f : fires) {
			if (f.getPosition().equals(p)) {
				return false;
			}
		}
		
		for (GameElements b : GameEngine.getInstance().getObjects(o -> o instanceof FiremanBot)) {
			if (b.getPosition().equals(p)) {
				return false;
			}
		}
		
		if (p.getX() < 0)
			return false;
		if (p.getY() < 0)
			return false;
		if (p.getX() >= GameEngine.GRID_WIDTH)
			return false;
		if (p.getY() >= GameEngine.GRID_HEIGHT)
			return false;
		return true;
	}

}
