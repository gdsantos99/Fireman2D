package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class Bulldozer extends VehicleObject {

	public Bulldozer(Point2D position) {
		super(position);
		setName("bulldozer");
		beingUsed = false;
	}

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
		GameEngine.getInstance().getObjects(o -> o instanceof Fire).forEach(f -> ((Fire) f).update());
		ForestObject.toLand();
	}

	@Override
	public void changeImage(Direction dir) {
		switch (dir) {
		case LEFT:
			setName("bulldozer_left");
			break;
		case RIGHT:
			setName("bulldozer_right");
			break;
		case UP:
			setName("bulldozer_up");
			break;
		case DOWN:
			setName("bulldozer_down");
			break;
		default:
			setName("bulldozer");
		}
	}

}
