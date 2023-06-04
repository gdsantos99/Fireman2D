package pt.iul.poo.firefight.starterpack;

import java.util.List;
import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class FireTruck extends VehicleObject {
	
	public FireTruck(Point2D position) {
		super(position);
		beingUsed = false;
		setName("firetruck");
	}

	@Override
	public void move() { 
		int tecla = ImageMatrixGUI.getInstance().keyPressed();
		Direction dir = Direction.directionFor(tecla);
		Point2D currentPosition = getPosition();
		currentPosition = currentPosition.plus(dir.asVector());
		changeImage(dir);
		if (canMoveTo(currentPosition) && !GameEngine.getInstance().getPositions(o -> o instanceof MovableObject).contains(currentPosition)) {
			setPosition(currentPosition);
		}
		GameEngine.getInstance().getObjects(o -> o instanceof Fire).forEach(f -> ((Fire) f).add());
		removeFires(dir);
	}

	@Override
	public int getLayer() {
		return 1;
	}
	
	
	public void removeFires(Direction d) {
		GameEngine.getInstance().getObjects(o -> o instanceof Fire).forEach(f -> ((Fire) f).update());
		List<Point2D> fires = getPosition().getFrontRect(d, 3, 2);
		fires.forEach(f -> Fire.remove(f,d));
	}

	@Override
	public void changeImage(Direction dir) {
		switch (dir) {
		case LEFT:
			setName("firetruck_left");
			break;
		case RIGHT:
			setName("firetruck_right");
			break;
		default:
			break;
		}
	}
}
