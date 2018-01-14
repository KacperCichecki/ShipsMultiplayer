package main.java.model;

public class Me extends Player {


	public Me(Map map) {
		super(map);
	}

	// hit enemy's field with given coordinates and
	// return state of this field after hit
	public Field hitEnemy(XY xy, Player enemy) {
		Field enemyField = enemy.getMap().getField(xy);

		if (enemyField.getState() == State.ENEMYSHIP) {
			enemyField.setState(State.ENEMYHIT);
			points++;
		} else {
			enemyField.setState(State.MISSED);
		}
		return enemy.getMap().getField(xy);
	}

}
