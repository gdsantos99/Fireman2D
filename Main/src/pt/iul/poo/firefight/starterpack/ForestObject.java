package pt.iul.poo.firefight.starterpack;

import java.util.List;
import pt.iul.ista.poo.utils.Point2D;

public abstract class ForestObject extends GameElements implements Updatable, Burnable {

	protected String name;
	private Status status = Status.NOFIRE;
	protected int cycles = 0;

	public ForestObject(Point2D position) {
		super(position);
	}

	public abstract boolean getProbability();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCycles() {
		return cycles;
	}

	public void update() {
		cycles++;
	}

	public void resetCycles() {
		cycles = 0;
	}
	
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	/** Bulldozer transforma celulas em terra sem vegetacao*/
	public static void toLand() {
		List<GameElements> plots = GameEngine.getInstance().getObjects(o -> o instanceof ForestObject);
		plots.forEach(t -> {
			if (t.getPosition().equals(GameEngine.getInstance().getBulldozerPos())) {
				GameEngine.getInstance().removeObject(t);
				GameEngine.getInstance().addObject(new Land(t.getPosition()));
			}
		});
	}
	/** Incrementa ciclos dos terrenos a arder*/
	public static void incrementCycles() {
		List<GameElements> plots = GameEngine.getInstance().getObjects(o -> o instanceof ForestObject);
		for (GameElements t : plots) {
			if (((ForestObject) t).getStatus() == Status.BURNING)
				((ForestObject) t).update();
		}
	}
}
