package pt.iul.poo.firefight.starterpack;


import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class Fireman extends MovableObject {

	public boolean onScreen = true;

	public Fireman(Point2D position) {
		super(position);
		setName("fireman");
	}

	@Override
	public int getLayer() {
		return 2;
	}

	public void move() {
		int tecla = ImageMatrixGUI.getInstance().keyPressed();
		Direction dir = Direction.directionFor(tecla);
		Point2D currentPosition = getPosition();
		currentPosition = currentPosition.plus(dir.asVector());
		changeImage(dir);
		if (canMoveTo(currentPosition)) {
			setPosition(currentPosition);
		}
		GameEngine.getInstance().getObjects(o -> o instanceof Fire).forEach(f -> ((Fire) f).add());
		Fire.remove(currentPosition,dir);
	}

	public boolean isOnScreen() {
		return onScreen;
	}

	public void vanish() {
		this.setPosition(new Point2D(-5, -5));
		onScreen = false;
	}

	public void comeBack() {
		for (GameElements o : GameEngine.getInstance().getObjects(o -> o instanceof VehicleObject)) {
			if (((VehicleObject) o).isBeingUsed()) {
				this.setPosition(o.getPosition());
			}
		}
		onScreen = true;
	}

	@Override
	public void changeImage(Direction dir) {
		switch (dir) {
		case LEFT:
			setName("fireman_left");
			break;
		case RIGHT:
			setName("fireman_right");
		default:
			break;
		}
	}

}
