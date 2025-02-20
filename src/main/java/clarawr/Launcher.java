package clarawr;

import javafx.application.Application;

/**
 * A launcher class to work around classpath issues when running a JavaFX application.
 * This class serves as the entry point to start the application by calling {@link Main}.
 */
public class Launcher {

	/**
	 * The main method that launches the JavaFX application.
	 *
	 * @param args Command-line arguments passed to the application.
	 */
	public static void main(String[] args) {
		assert args != null : "Command-line arguments should not be null";
		assert args.length == 0 : "Command-line arguments should be provided if required";

		Application.launch(Main.class, args);
	}
}
