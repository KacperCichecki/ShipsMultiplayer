package main.java.model;

import java.util.Random;

public class Enemy extends Player {

	private Random rand = null;

	public Enemy(Map map) {
		super(map);
	}

	// draw random coordinates and hit my field with this coordinates
	public Field hitMe(Player me) {

		int x1 = 0;
		int y1 = 0;
		Field field;
		State state;

		// hit only field that are EMPTY or SHIP (can't hit fields HIT or MISSED)
		do { x1 =  rand.nextInt(6)+1;
			y1 =  rand.nextInt(6)+1;
			XY xy = new XY(x1, y1);
			field = me.getMap().getField(xy);
			state = field.getState();
		} while (state == State.HIT || state == State.MISSED);

		if (state == State.EMPTY) {
			field.setState(State.MISSED);
		} else {
			field.setState(State.HIT);
			points++;
		}
		return field;
	}

}
