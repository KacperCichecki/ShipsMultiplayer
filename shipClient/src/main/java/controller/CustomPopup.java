package main.java.controller;

import java.awt.Dimension;
import java.awt.Toolkit;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class CustomPopup {

	private Stage stage;
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private int centerX = screenSize.width / 2;
	private int centerY = screenSize.height / 2;

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

		Label label = new Label("This is simple battle ships game.\n"
				+ "This is game for one player who try to guess the\n"
				+ "location of the opponent's ships.\n"
				+ "Player take turns choosing field in an attempt to find\n"
				+ "a square that contains an opponent's ship"
				+ "\n\n All rights reserved \n Author:\n");
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
