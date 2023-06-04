package pt.iul.poo.firefight.starterpack;

import java.util.List;

import pt.iul.ista.poo.utils.Point2D;

public abstract class ActiveObject extends GameElements implements ActiveElement {

	private String name;
	private int counter = 0;
	
	public ActiveObject(Point2D position) {
		super(position);
	}
	
	@Override
	public int getLayer() {
		return 1;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void incrementCounter() {
		counter++;
	}
	
	public boolean canRemove() {
		return counter > 1;
	}
	
	
	public void add(){
		GameEngine.getInstance().addObject(this);
	}
	
	/** remove elemento uma jogada depois de ser add*/
	public static void remove() {
		List<GameElements> elements = GameEngine.getInstance().getObjects(o -> o instanceof ActiveObject);
		for (GameElements ao : elements) {
			if (((ActiveObject) ao).canRemove()) {
				GameEngine.getInstance().removeObject(ao);
			}
		}
	} 
	/** incrementa contador por cada jogada*/
	public static void increment() {
		List<GameElements> elements = GameEngine.getInstance().getObjects(o -> o instanceof ActiveObject);
		elements.forEach(e -> ((ActiveObject) e).incrementCounter());
	}
		
}
