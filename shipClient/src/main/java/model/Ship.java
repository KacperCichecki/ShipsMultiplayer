package main.java.model;

import java.util.Arrays;

public class Ship {

	private int hp = 0;
	private XY[] xy;

	public Ship(XY xy1, XY xy2) {
		this.hp = 2;
		xy = new XY[2];
		xy[0] = xy1;
		xy[1] = xy2;
	}

	public Ship(XY xy1, XY xy2, XY xy3) {
		this.hp = 3;
		xy = new XY[3];
		xy[0] = xy1;
		xy[1] = xy2;
		xy[2] = xy3;
	}

	public XY getXY(int i) {
		return xy[i];
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	@Override
	public String toString() {
		return "Ship [hp=" + hp + ", xy=" + Arrays.toString(xy) + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Ship)) {
			return false;
		}
		Ship other = (Ship) obj;
		if (!Arrays.equals(xy, other.xy)) {
			return false;
		}
		return true;
	}
}
