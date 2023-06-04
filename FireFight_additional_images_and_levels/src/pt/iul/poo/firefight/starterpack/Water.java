package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class Water extends ActiveObject {

	public Water(Point2D position) {
		super(position);
		this.setName("water");
	}
	
	public void changePic(Direction dir) {
		switch (dir) {
		case LEFT:
			setName("water_left");
			break;
		case RIGHT:
			setName("water_right");
			break;
		case UP:
			setName("water_up");
			break;
		case DOWN:
			setName("water_down");
			break;
		default:
			setName("water");
		}
	}

}
