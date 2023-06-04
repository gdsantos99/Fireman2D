package pt.iul.poo.firefight.starterpack;

import pt.iul.ista.poo.utils.Point2D;

public abstract class VehicleObject extends MovableObject{
	
	
	public VehicleObject(Point2D position) {
		super(position);
	}
	
	public void setUsed() {
		this.beingUsed = true;
	}
	
	public void setNotUsed() {
		this.beingUsed = false;
	}
	
	public boolean isBeingUsed() {
		return beingUsed;
	}
	
	public int getLayer() {
		return 1;
	}

}
