package controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioMenuItem;
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
	private GridPane myField;

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
		//  żeby podczas wyłączenie okienka, wątek został ubity
		initialThread.setDaemon(true);
		initialThread.start();
	}

	// ustawiamy id każdego pola na mojej mapie: sprawdzam jaki stan ma to pole i
	// ustawiam id tego buttona zgodne ze stanem
	// (powoduje to zmianę koloru na taki, jaki jest zdefiniowany w arkuszu css)
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
			Thread hitThread =
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
			});
			hitThread.setDaemon(true);
			hitThread.start();
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
				Platform.runLater(() -> {
					showAnnouncement("YOU WON\n\n Congratulation!");
					dao.saveResult("me", "enemy");
				});
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
		// w javiefx trzeba użyć Platform.runLater kiedy wątek inny niż główny wątek aplikacji chce zmienić elementy graficzne
			Platform.runLater(() -> {
				showAnnouncement("YOU LOST\n\n Learn how to play!");
				dao.saveResult("enemy", "me");
			});
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

	@FXML
    public void showWeatherInfo(ActionEvent actionEvent) {
		if (stage == null) {
			stage = (Stage) enemyField.getScene().getWindow();
		}
		if (pop == null) {
			pop = new CustomPopup(stage);
		}
		pop.showWeatherInfo();
    }

	@FXML
	public void chooseTheme(ActionEvent actionEvent) {
		if (stage == null) {
			stage = (Stage) enemyField.getScene().getWindow();
		}
		RadioMenuItem radioMenuItem = (RadioMenuItem) actionEvent.getSource();
		Scene scene = stage.getScene();
		scene.getStylesheets().clear();
		scene.getStylesheets().add(radioMenuItem.getText() + ".css");
	}
}
