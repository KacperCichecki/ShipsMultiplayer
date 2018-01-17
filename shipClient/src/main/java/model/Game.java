package main.java.model;

public class Game {

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

	// return fields which were hit by me and enemy,
	//first field is enemy's field and second is mine
	public Field[] nextRound(XY xy) {

		Field[] fields = new Field[2];

		if(!waitingForResponse){

			//return enemy's field after my hit
			State afterMyShot = me.hitEnemy(xy);

			if (afterMyShot == State.ENEMYHIT) {
				System.out.println("I have points: " + me.getPoints());
			}

			fields[0] = new Field(xy, afterMyShot);

			fields[1] = enemy.hitMe(me);

			State afterEnemyShot = fields[1].getState();

			if (afterEnemyShot == State.HIT) {
				System.out.println("Enemy have points: " + enemy.getPoints());
			}
		}
		System.out.println("Game didn't hit because waiting for response");
		return fields;
	}
}
