package controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.Field;
import model.Game;
import model.State;
import model.XY;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repository.DAO;

public class Controller implements Initializable {

	private static final Logger logger = LogManager.getLogger();

	// todo zapiąć locka na obiekcie game
    private Game game = new Game();
	private DAO dao = DAO.getInstance();
    private Stage stage = null;
    private CustomPopup pop = null;
    private Thread initialThread = null;
    private boolean hitThreadIsRunning = false;

	@FXML
	private ProgressBar progressBarMe, progressBarEnemy;

	@FXML
	private GridPane enemyField;

	@FXML
	private Button eButton00, eButton10, eButton20, eButton30, eButton40, eButton50, eButton60, eButton70;
	@FXML
	private Button eButton01, eButton11, eButton21, eButton31, eButton41, eButton51, eButton61, eButton71;
	@FXML
	private Button eButton02, eButton12, eButton22, eButton32, eButton42, eButton52, eButton62, eButton72;
	@FXML
	private Button eButton03, eButton13, eButton23, eButton33, eButton43, eButton53, eButton63, eButton73;
	@FXML
	private Button eButton04, eButton14, eButton24, eButton34, eButton44, eButton54, eButton64, eButton74;
	@FXML
	private Button eButton05, eButton15, eButton25, eButton35, eButton45, eButton55, eButton65, eButton75;
	@FXML
	private Button eButton06, eButton16, eButton26, eButton36, eButton46, eButton56, eButton66, eButton76;
	@FXML
	private Button eButton07, eButton17, eButton27, eButton37, eButton47, eButton57, eButton67, eButton77;

	@FXML
	private GridPane myField;

	@FXML
	private Button button00, button10, button20, button30, button40, button50, button60, button70;
	@FXML
	private Button button01, button11, button21, button31, button41, button51, button61, button71;
	@FXML
	private Button button02, button12, button22, button32, button42, button52, button62, button72;
	@FXML
	private Button button03, button13, button23, button33, button43, button53, button63, button73;
	@FXML
	private Button button04, button14, button24, button34, button44, button54, button64, button74;
	@FXML
	private Button button05, button15, button25, button35, button45, button55, button65, button75;
	@FXML
	private Button button06, button16, button26, button36, button46, button56, button66, button76;
	@FXML
	private Button button07, button17, button27, button37, button47, button57, button67, button77;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		progressBarMe.setProgress(1);
		progressBarEnemy.setProgress(1);
        setUpMyFields();

		initialThread = new Thread(() -> {
			Field field = game.listenForInitialRequest();
			if (field != null){
				setMyField(field);
				logger.info("end of initial thread, field set up");
			} else {
				logger.info("end of initial thread, without setting field");
			}

		});
		initialThread.setDaemon(true);
		initialThread.start();
	}

    private void setUpMyFields() {

        for (Field field : game.getMyMap().getFields()) {
            XY xy = field.getXY();
            int x = xy.getX();
            int y = xy.getY();
            String buttonNr = "" + x + y;
            String state = field.getState().toString();
            ObservableList<Node> buttons = myField.getChildren();
            buttons.stream().filter(b -> b instanceof Button).filter(b -> ((Button) b).getText().endsWith(buttonNr))
                    .forEach(b -> b.setId(state));
        }
    }

    public void restartGame() {
		game.startGame();
		progressBarMe.setProgress(1);
		progressBarEnemy.setProgress(1);

		// reset enemy fields
		List<Node> buttons = enemyField.getChildren().stream().filter(b -> b instanceof Button)
				.collect(Collectors.toList());

		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				String xy = "" + x + y;
				State state = game.getEnemyMap().getField(new XY(x, y)).getState();
				buttons.stream().filter(b -> ((Button) b).getText().endsWith(xy)).forEach(b -> {
					b.setId(state.toString());
					b.setDisable(false);
				});
			}
		}

		// reset my fields
		List<Node> buttons2 = myField.getChildren().stream().filter(b -> b instanceof Button)
				.collect(Collectors.toList());

		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				String xy = "" + x + y;
				State state = game.getMyMap().getField(new XY(x, y)).getState();
				buttons2.stream()
						.filter(b -> ((Button) b).getText().endsWith(xy))
						.forEach(b -> b.setId(state.toString()));
			}
		}
	}

	// hit enemy's field with given number then set fields (enemy and my) with
	// results
	@FXML
	private void hitField(ActionEvent e) {

		if (initialThread.isAlive()){
			logger.info("Controller: initialThread.isAlive()");
			initialThread.interrupt();
		}

		if (!hitThreadIsRunning) {
			new Thread(() -> {
				hitThreadIsRunning = true;
				logger.info("Starting new hitthread...");
				Button button = (Button) e.getSource();
				int number = Integer.parseInt(button.getText());
				int x = number / 10;
				int y = number % 10;
				// first field is enemy's field and second is mine
				Field[] fields = game.nextRound(new XY(x, y));
				setEnemyField(fields[0]);
				setMyField(fields[1]);
				logger.info("... end of hitthread");
				hitThreadIsRunning = false;
			}).start();
		} else {
			logger.warn("New hitthread didn't start - waitinig for previous thread");
		}
	}

	// set enemy's field which was hit by me
	private void setEnemyField(Field field) {

		State state = field.getState();
		if (state == State.ENEMYHIT) {
			int points = game.getMe().getPoints();
			progressBarEnemy.setProgress((double) (7 - points) / 7);
			if (points == 7) {
				showAnnouncement("YOU WON\n\n Congratulation!");
				dao.saveResult("me", "enemy");
			}
		}

		XY xy = field.getXY();
		int x = xy.getX();
		int y = xy.getY();
		String buttonNr = "" + x + y;

		ObservableList<Node> buttons = enemyField.getChildren();
		buttons.stream()
				.filter(b -> b instanceof Button)
				.filter(b -> ((Button) b).getText().endsWith(buttonNr))
				.forEach(b -> {
					b.setId(state.toString());
					b.setDisable(true);
				});
	}

	// set field which was hit by enemy
	private void setMyField(Field field) {

		if (field != null) {
			State state = field.getState();
			if (state == State.HIT) {
				int points = game.getEnemy().getPoints();
				progressBarMe.setProgress((double) (7 - points) / 7);

			}

			XY xy = field.getXY();
			int x = xy.getX();
			int y = xy.getY();
			String buttonNr = "" + x + y;

			ObservableList<Node> buttons = myField.getChildren();
			buttons.stream()
					.filter(b -> b instanceof Button)
					.filter(b -> ((Button) b).getText().endsWith(buttonNr))
					.forEach(b -> b.setId(state.toString()));
		} else if (game.getMe().getPoints() > 6) {
			showAnnouncement("YOU LOST\n\n Learn how to play!");
			dao.saveResult("enemy", "me");
		}

	}

	// show Pop up window with custom text
	private void showAnnouncement(String message) {
		if (stage == null) {
			stage = (Stage) enemyField.getScene().getWindow();
		}
		if (pop == null) {
			pop = new CustomPopup(stage);
		}
		pop.showAnnouncement(message);

		// make enemy's buttons disabled
		List<Node> buttons = enemyField.getChildren().stream().filter(b -> b instanceof Button)
				.collect(Collectors.toList());

		for (int y = 0; y < 8; y++) {
			for (int x = 0; x < 8; x++) {
				String xy = "" + x + y;
				State state = game.getEnemyMap().getField(new XY(x, y)).getState();
				buttons.stream()
						.filter(b -> ((Button) b).getText().endsWith(xy))
						.forEach(b -> b.setDisable(true));
			}
		}

	}

	// show program info
	@FXML
	private void showGameInfo() {
		if (stage == null) {
			stage = (Stage) enemyField.getScene().getWindow();
		}
		if (pop == null) {
			pop = new CustomPopup(stage);
		}

		pop.showInfo();
	}

	// show game statistics
	@FXML
	public void showGameStats(ActionEvent actionEvent) {
		if (stage == null) {
			stage = (Stage) enemyField.getScene().getWindow();
		}
		if (pop == null) {
			pop = new CustomPopup(stage);
		}

		pop.showStatistics();

	}
}
