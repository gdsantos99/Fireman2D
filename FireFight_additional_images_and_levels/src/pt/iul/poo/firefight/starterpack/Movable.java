package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public interface Movable {
	
	void move();
	boolean canMoveTo(Point2D p);
	void changeImage(Direction dir);
}
