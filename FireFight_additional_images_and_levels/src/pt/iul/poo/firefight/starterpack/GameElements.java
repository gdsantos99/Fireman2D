package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Point2D;

public abstract class GameElements implements ImageTile {

	private Point2D position;

	public GameElements(Point2D position) {
		this.position = position;
	}

	public void setPosition(Point2D position) {
		this.position = position;

	}

	public Point2D getPosition() {
		return position;
	}

	public abstract String getName();

	public abstract int getLayer();

	public static GameElements setElement(Point2D position, String s) {
		switch (s) {
		case "m":
			return new Grass(position);
		case "p":
			return new Pine(position);
		case "_":
			return new Land(position);
		case "e":
			return new Eucalyptus(position);
		case "a":
			return new Abies(position);
		case "b":
			return new FuelBarrel(position);
		case "Fireman":
			return new Fireman(position);
		case "Bulldozer":
			return new Bulldozer(position);
		case "Fire":
			return new Fire(position);	
		case "FireTruck":
			return new FireTruck(position);
		case "FiremanBot":
			return new FiremanBot(position);
		default:
			return null;
		}
	}

	
	
}
