package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class FiremanBot extends MovableObject {

	public FiremanBot(Point2D position) {
		super(position);
		setName("firemanbot");
	}

	@Override
	public void move() {
		Direction randDir = Direction.random();
		Point2D currentPosition = getPosition();
		currentPosition = currentPosition.plus(randDir.asVector());
		changeImage(randDir);
		if (canMoveTo(currentPosition) && !GameEngine.getInstance().getPositions(o -> o instanceof MovableObject).contains(currentPosition)
				&& !currentPosition.equals(GameEngine.getInstance().getFiremanPos())) {
			setPosition(currentPosition);
		}
		GameEngine.getInstance().getObjects(o -> o instanceof Fire).forEach(f -> ((Fire) f).add());
		Fire.remove(currentPosition,randDir);
	}

	@Override
	public int getLayer() {
		return 1;
	}

	@Override
	public void changeImage(Direction dir) {
		switch (dir) {
		case LEFT:
			setName("firemanbot_left");
			break;
		case RIGHT:
			setName("firemanbot_right");
			break;
		default:
			break;
		}
	}

}
