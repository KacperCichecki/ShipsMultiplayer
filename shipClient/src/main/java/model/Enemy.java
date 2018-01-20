package main.java.model;

import main.java.controller.CommunicationService;

public class Enemy extends Player {

	private CommunicationService communicationService = CommunicationService.INSTANCE;
	private boolean sentFirsRequest = false;

	public Enemy(Map map) {
		super(map);
	}

	public void setSentFirsRequest(boolean sentFirsRequest) {
		this.sentFirsRequest = sentFirsRequest;
	}

	// checkout which filed enemy wants to hit and change state of this field and return state of this field
	public Field hitMe(Player me) {
		String xy = communicationService.getHit();
		System.out.println("Got shot from enemy: " + xy);
		Field field = me.getMap().getField(new XY(xy));
		State beforeHit = field.getState();

		if(beforeHit == State.SHIP) {
			field.setState(State.HIT);
			points++;
			System.out.println("Enemy hit my ship. Enemy's ponts: " + points);

		}else {
			field.setState(State.MISSED);
			System.out.println("Enemy missed");
		}
		communicationService.sendAnswer(field.getState());
		return field;
	}

	// checkout which filed enemy wants to hit, change state of this field, return state of this field and send answer
	public Field hitMeFirstTime(Player me) {
		String xy = communicationService.getHitFirstTime();

		if ("".equals(xy)) return null;

		Field field = me.getMap().getField(new XY(xy));
		State beforeHit = field.getState();

		if(beforeHit == State.SHIP) {
			field.setState(State.HIT);
			points++;
			System.out.println("Enemy hit my ship. Enemy's ponts: " + points);

		}else {
			field.setState(State.MISSED);
			System.out.println("Enemy missed");
		}
		communicationService.sendAnswer(field.getState());
		return field;
	}
}
