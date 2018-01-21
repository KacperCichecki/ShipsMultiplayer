package model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.*;

public class Game {

	private static final Logger logger = LogManager.getLogger();

	private Me me;
	private Enemy enemy;
	private Map myMap;
	private Map enemyMap;
	private boolean waitingForResponse = false;

	public Player getMe() {
		return me;
	}

	public void setMe(Me me) {
		this.me = me;
	}

	public Player getEnemy() {
		return enemy;
	}

	public void setEnemy(Enemy enemy) {
		this.enemy = enemy;
	}

	public Map getMyMap() {
		return myMap;
	}

	public void setMyMap(Map myMap) {
		this.myMap = myMap;
	}

	public Map getEnemyMap() {
		return enemyMap;
	}

	public void setEnemyMap(Map enemyMap) {
		this.enemyMap = enemyMap;
	}

	public Game() {
		startGame();
	}

	public void startGame() {

		myMap = new Map(State.EMPTY);
		me = new Me(myMap);

		Ship[] drawedShips = me.drawShip();

		myMap.setShip(drawedShips[0], 0);
		myMap.setShip(drawedShips[1], 1);
		myMap.setShip(drawedShips[2], 2);

		enemyMap = new Map(State.ENEMYEMPTY);
		enemy = new Enemy(enemyMap);
	}

	// get initial hit and send response
	public Field listenForInitialRequest() {
		return enemy.hitMeFirstTime(me);
	}

	// return fields which were hit by me and enemy,
	//first field is enemy's field and second is mine
	public Field[] nextRound(XY xy) {

		enemy.setSentFirsRequest(true);

		Field[] fields = new Field[2];

		if (!waitingForResponse){
            waitingForResponse = true;
			//return enemy's field's state after my hit
			State afterMyShot = me.hitEnemy(xy);
			logger.info("enemy's field after my shot: " + afterMyShot);
			if (afterMyShot == State.ENEMYHIT) {
				logger.info("I have points: " + me.getPoints());
			}
			fields[0] = new Field(xy, afterMyShot);

			if(me.getPoints() < 7){
				fields[1] = enemy.hitMe(me);
			}

            waitingForResponse = false;
		} else {
			logger.warn("Game didn't hit because waiting for response");
		}
		return fields;
	}
}
