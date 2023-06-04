package pt.iul.poo.firefight.starterpack;

import java.util.List;

import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class Plane extends MovableObject {

	public Plane(Point2D position) {
		super(position);

	}

	@Override
	public String getName() {
		return "plane";
	}

	@Override
	public int getLayer() {
		return 2;
	}
	
	/** calcula a coluna com mais fogos*/
	public static Plane add() {
		int Maxcount = 0;
		int column = 0;
		int count;
		List<GameElements> fires = GameEngine.getInstance().getObjects(o -> o instanceof Fire);
		for (int i = 0; i < GameEngine.GRID_WIDTH; i++) {
			count = 0;
			for (GameElements f : fires) {
				if (f.getPosition().getX() == i) {
					count++;
					if (count > Maxcount) {
						Maxcount = count;
						column = i;
					}
				}
			}
		}
		Plane p = new Plane(new Point2D(column, 9));
		GameEngine.getInstance().addObject(p);
		Fire.remove(p.getPosition(),Direction.DOWN);
		return p;
	}
	
	/** move plane no eixo y*/
	@Override
	public void move() {
		int x = getPosition().getX();
		int y = getPosition().getY();
		GameEngine.getInstance().removeObject(this);
		if (y >= 0) {
			Plane airplane = new Plane(new Point2D(x, y - 1));
			Fire.remove(airplane.getPosition(),Direction.DOWN);
			GameEngine.getInstance().addObject(airplane);
		}
	}

	@Override
	public void changeImage(Direction dir) {
	}

}
