package main.java.model;

import java.util.Arrays;

public class Map {
	private final Field[] fields = new Field[64];
	private Ship[] ships = new Ship[3];

	// construct map with fields with given state
	public Map(State state) {
		for (int i = 0; i < fields.length; i++) {
			fields[i] = new Field(new XY(i % 8, i / 8), state);
		}
	}

	// change state of fields where given ship is located
	public void setShip(Ship ship, int i, State state) {
		ships[i] = ship;
		int length = ship.getHp();
		if (length == 2) {
			fields[ship.getXY(0).getY() * 8 + ship.getXY(0).getX()].setState(state);
			fields[ship.getXY(1).getY() * 8 + ship.getXY(1).getX()].setState(state);
		}else{
			fields[ship.getXY(0).getY() * 8 + ship.getXY(0).getX()].setState(state);
			fields[ship.getXY(1).getY() * 8 + ship.getXY(1).getX()].setState(state);
			fields[ship.getXY(2).getY() * 8 + ship.getXY(2).getX()].setState(state);
		}
	}

	public Field getField(XY xy) {
		int x = xy.getX();
		int y = xy.getY();
		return fields[y * 8 + x];
	}

	public Field getField(int x, int y) {
		return fields[y * 8 + x];
	}

	public void setFieldState(XY xy, State state) {
		int x = xy.getX();
		int y = xy.getY();
		fields[y * 8 + x].setState(state);
	}

	@Override
	public String toString() {
		return "Map [fields=" + Arrays.toString(fields) + "]";
	}

}
