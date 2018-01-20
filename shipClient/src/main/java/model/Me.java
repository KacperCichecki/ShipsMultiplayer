package main.java.model;

import main.java.controller.CommunicationService;

import java.util.Random;

public class Me extends Player {

    private Random rand = null;
    private CommunicationService communicationService = CommunicationService.INSTANCE;

	public Me(Map map) {
		super(map);
        rand = new Random();
	}

	// hit enemy's field with given coordinates and
	// return state of this field after hit
	public State hitEnemy(XY xy) {
		State stateAfterHit = communicationService.hitAndReturnState(xy);

		if (stateAfterHit == State.ENEMYHIT) {
			points++;
		}
		return stateAfterHit;
	}

	// draw filed for ships and return these ships
	public Ship[] drawShip() {
		// first ship
		Ship ship1 = null;
		int x1 = rand.nextInt(8);
		int y1 = rand.nextInt(8);
		XY xy1 = new XY(x1, y1);
		XY xy2;
		int x2 = 0;
		int y2 = 0;
		// second ship
		Ship ship2 = null;
		XY xy3;
		int x3 = 0;
		int y3 = 0;
		XY xy4;
		int x4 = 0;
		int y4 = 0;
		// third ship
		Ship ship3 = null;
		XY xy5;
		int x5 = 0;
		int y5 = 0;
		XY xy6;
		int x6 = 0;
		int y6 = 0;
		XY xy7;
		int x7 = 0;
		int y7 = 0;

		// if 1 horizontal, if 0 vertical
		int direction = rand.nextInt(2);

		if (direction == 1) {
			if (x1 < 7) {
				x2 = x1 + 1;
			} else {
				x2 = x1 - 1;
			}
			y2 = y1;
		} else {
			if (y1 < 7) {
				y2 = y1 + 1;
			} else {
				y2 = y1 - 1;
			}
			x2 = x1;
		}
		xy2 = new XY(x2, y2);

		// setting xy3 and xy4
		while (true) {
			x3 = rand.nextInt(8);
			y3 = rand.nextInt(8);
			xy3 = new XY(x3, y3);

			if ((xy3.equals(xy1)) || (xy3.equals(xy2))) {
				continue;
			}

			// if 1 horizontal, if 0 vertical
			int direction2 = rand.nextInt(2);

			if (direction2 == 1) {
				if (x3 < 7) {
					x4 = x3 + 1;
				} else {
					x4 = x3 - 1;
				}
				y4 = y3;
			} else {
				if (y3 < 7) {
					y4 = y3 + 1;
				} else {
					y4 = y3 - 1;
				}
				x4 = x3;
			}
			xy4 = new XY(x4, y4);

			if ((xy4.equals(xy1)) || (xy4.equals(xy2))) {
				continue;
			}
			break;
		}

		// setting xy5, xy6 and xy7
		while (true) {
			x5 = rand.nextInt(8);
			y5 = rand.nextInt(8);
			xy5 = new XY(x5, y5);

			if ((xy5.equals(xy1)) || (xy5.equals(xy2)) || (xy5.equals(xy3)) || (xy5.equals(xy4))) {
				continue;
			}

			// if 1 horizontal, if 0 vertical
			int direction2 = rand.nextInt(2);

			if (direction2 == 1) {
				if (x5 < 6) {
					x6 = x5 + 1;
					x7 = x5 + 2;
				} else {
					x6 = x5 - 1;
					x7 = x5 - 2;
				}
				y6 = y5;
				y7 = y5;
			} else {
				if (y5 < 6) {
					y6 = y5 + 1;
					y7 = y5 + 2;
				} else {
					y6 = y5 - 1;
					y7 = y5 - 2;
				}
				x6 = x5;
				x7 = x5;
			}
			xy6 = new XY(x6, y6);
			if ((xy6.equals(xy1)) || (xy6.equals(xy2)) || (xy6.equals(xy3))|| (xy6.equals(xy4))) {
				continue;
			}

			xy7 = new XY(x7, y7);
			if ((xy7.equals(xy1)) || (xy7.equals(xy2)) || (xy7.equals(xy3))|| (xy7.equals(xy4))) {
				continue;
			}
			break;
		}

		ship1 = new Ship(xy1, xy2);
		ship2 = new Ship(xy3, xy4);
		ship3 = new Ship(xy5, xy6, xy7);

		System.out.println(ship1);
		System.out.println(ship2);
		System.out.println(ship3);

		return new Ship[] { ship1, ship2, ship3 };
	}

}
