package model;

public abstract class Player {

	protected int points = 0;
	protected final Map map;

	protected Player(Map map) {
		this.map = map;
	}

	public int getPoints() {
		return points;
	}

	public Map getMap() {
		return map;
	}

}
