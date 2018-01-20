package main.java.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;


public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("../../resources/view.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("../../resources/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("JAVA Ships");
			primaryStage.setResizable(false);
			primaryStage.getIcons().add(new Image(getClass().getResource("../../resources/ship.png").toString()));
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
