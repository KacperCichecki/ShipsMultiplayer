package model;

public class Field {

	private final XY XY;
	private State state;

	public Field(XY xy, State state) {
		this.XY = xy;
		this.state = state;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public XY getXY() {
		return XY;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Field)) {
			return false;
		}
		Field other = (Field) obj;
		if (XY == null) {
			if (other.XY != null) {
				return false;
			}
		} else if (!XY.equals(other.XY)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Field [XY=" + XY + ", state=" + state + "]";
	}

	
}
