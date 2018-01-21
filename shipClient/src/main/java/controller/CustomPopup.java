package controller;

import java.awt.Dimension;
import java.awt.Toolkit;

import config.Messages;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import repository.DAO;

public class CustomPopup {

	private Stage stage;
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private int centerX = screenSize.width / 2;
	private int centerY = screenSize.height / 2;
	DAO dao = DAO.getInstance();

	protected CustomPopup(Stage stage){
		this.stage = stage;
	}

	public void showAnnouncement(String message) {

		Toolkit.getDefaultToolkit().beep();

		final Popup popup = new Popup();
		popup.setX(centerX - 200);
		popup.setY(centerY - 250);

		Button hideButton = new Button("OK");
		hideButton.setId("okButton");
		hideButton.setOnAction((a) -> popup.hide());

		Label label = new Label(message);
		label.setId("popLabel");

		VBox layout1 = new VBox();
		layout1.setId("popup");
		layout1.setMinHeight(300);
		layout1.setMinWidth(400);
		layout1.setAlignment(Pos.CENTER);
		layout1.getChildren().addAll(label, hideButton);

		popup.getContent().addAll(layout1);
		popup.show(stage);
	}

	public void showInfo() {

		final Popup popup = new Popup();
		popup.setX(centerX - 200);
		popup.setY(centerY - 250);

		Button hideButton = new Button("OK");
		hideButton.setId("okButton");
		hideButton.setOnAction((a) -> popup.hide());

		Label label = new Label(Messages.getMessage("program.info"));
		label.setId("popLabe2");
		VBox layout1 = new VBox();
		layout1.setId("popup");
		layout1.setMinHeight(300);
		layout1.setMinWidth(400);
		layout1.setAlignment(Pos.CENTER);
		layout1.getChildren().addAll(label, hideButton);

		popup.getContent().addAll(layout1);
		popup.show(stage);

	}

	public void showStatistics() {
		int winGamesAmount = dao.getUserWinSum("me");
		int looseGameAmount = dao.getUserLooseSum("me");
		final Popup popup = new Popup();
		popup.setX(centerX - 200);
		popup.setY(centerY - 250);

		Button hideButton = new Button("OK");
		hideButton.setId("okButton");
		hideButton.setOnAction((a) -> popup.hide());

		String winGamesLine = Messages.getMessage("game.win");
		String looseGamesLine = Messages.getMessage("game.loose");
		StringBuilder sb = new StringBuilder(winGamesLine);
		sb.append(winGamesAmount + "\n");
		sb.append(looseGamesLine);
		sb.append(looseGameAmount);

		Label label = new Label(sb.toString());
		label.setId("popLabe2");
		VBox layout1 = new VBox();
		layout1.setId("popup");
		layout1.setMinHeight(300);
		layout1.setMinWidth(400);
		layout1.setAlignment(Pos.CENTER);
		layout1.getChildren().addAll(label, hideButton);

		popup.getContent().addAll(layout1);
		popup.show(stage);


	}
}
