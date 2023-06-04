package pt.iul.poo.firefight.starterpack;

import java.util.List;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class Fire extends GameElements implements Updatable {

	public Fire(Point2D position) {
		super(position);
	}

	public String getName() {
		return "fire";
	}

	public int getLayer() {
		return 1;
	}

	/** adiciona fogo */
	public void add() {
		List<Point2D> closePlots = getPosition().getNeighbourhoodPoints();
		List<GameElements> plots = GameEngine.getInstance().getObjects(o -> o instanceof ForestObject);
		for (Point2D pos : closePlots) {
			for (GameElements t : plots) {
				if (((ForestObject) t).getProbability() && t.getPosition().equals(pos)
						&& !GameEngine.getInstance().getPositions(o -> o instanceof MovableObject).contains(pos)
						&& !GameEngine.getInstance().getPositions(o -> o instanceof Water).contains(pos) && ((ForestObject) t).getStatus() == Status.NOFIRE) {
					GameEngine.getInstance().addObject(new Fire(pos));
					((ForestObject) t).setStatus(Status.BURNING);
				}
			}
		}
		update();
	}

	/** remove fogo      se o terreno nao estiver queimado faz reset aos ciclos desse terreno */
	public static void remove(Point2D p, Direction dir) {
		List<GameElements> fires = GameEngine.getInstance().getObjects(o -> o instanceof Fire);
		List<GameElements> plots = GameEngine.getInstance().getObjects(o -> o instanceof ForestObject);
		fires.forEach(f -> {
			((Fire) f).update();
			if (f.getPosition().equals(p)) {
				plots.forEach(t -> {
					if (t.getPosition().equals(p)) {
						((ForestObject) t).setStatus(Status.NOFIRE);
						((ForestObject) t).resetCycles();
					}
				});
				GameEngine.getInstance().removeObject(f);
				GameEngine.getInstance().incrementPoints(5);
				Water w = new Water(f.getPosition());
				w.changePic(dir);
				w.add();
			}
		});
	}

	/** remove fogo dos terrenos queimados */
	public void update() {
		List<GameElements> plots = GameEngine.getInstance().getObjects(o -> o instanceof ForestObject);
		for (GameElements t : plots) {
			if (((ForestObject) t).getStatus() == Status.BURNT && t.getPosition().equals(getPosition())) {
				GameEngine.getInstance().removeObject(this);
			}
		}
	}

	/** Explosao do barril */
	public static void barrelExplosion(Point2D p) {
		List<Point2D> validPos = p.getWideNeighbourhoodPoints();
		List<GameElements> plots = GameEngine.getInstance().getObjects(o -> o instanceof ForestObject);
		for (Point2D pos : validPos) {
			for (GameElements t : plots) {
				Fire f = new Fire(pos);
				if (t.getPosition().equals(pos) && !GameEngine.getInstance().getPositions(o -> o instanceof MovableObject).contains(pos)
						&& !GameEngine.getInstance().getPositions(o -> o instanceof Water).contains(pos) && ((ForestObject) t).getStatus() == Status.NOFIRE) {
					GameEngine.getInstance().addObject(f);
					((ForestObject) t).setStatus(Status.BURNING);
				}
			}
		}
	}
}
