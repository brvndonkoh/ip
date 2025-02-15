package Clarawr;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * The main entry point for the Clarawr GUI application.
 * This class initializes and launches the JavaFX application.
 */
public class Main extends Application {

	private final Clarawr clarawr = new Clarawr();

	/**
	 * Starts the JavaFX application by setting up the primary stage.
	 * Loads the FXML layout and injects the Clarawr instance into the MainWindow controller.
	 *
	 * @param stage The primary stage for this application.
	 */
	@Override
	public void start(Stage stage) {

		assert clarawr != null : "Clarawr instance should not be null";
		try {
			stage.setMinHeight(220);
			stage.setMinWidth(417);

			FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
			assert fxmlLoader.getLocation() != null : "FXML file could not be loaded from /view/MainWindow.fxml";

			AnchorPane ap = fxmlLoader.load();
			Scene scene = new Scene(ap);

			stage.setScene(scene);
			fxmlLoader.<MainWindow>getController().setClarawr(clarawr);

			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
