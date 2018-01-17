package main.java.model;

public class XY {

	private final int x;
	private final int y;

	public XY(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}

	@Override
	public String toString() {
		return "x=" + x + ", y=" + y;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof XY)) {
			return false;
		}
		XY other = (XY) obj;
		if (x != other.x) {
			return false;
		}
		if (y != other.y) {
			return false;
		}
		return true;
	}



}
