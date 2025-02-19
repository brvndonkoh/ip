package clarawr;

import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for the main GUI.
 * Handles user input and display dialog boxes for the user and Clarawr.
 */
public class MainWindow extends AnchorPane {
	@FXML
	private ScrollPane scrollPane;
	@FXML
	private VBox dialogContainer;
	@FXML
	private TextField userInput;
	@FXML
	private Button sendButton;

	private Clarawr clarawr;

	private final Image userImage = new Image(this.getClass().getResourceAsStream("/images/tarzanBoy.png"));
	private final Image clarawrImage = new Image(this.getClass().getResourceAsStream("/images/simbaLion.jpg"));

	/**
	 * Initializes the MainWindow.
	 * Binds the vertical scroll property of the ScrollPane to the height of the dialog container,
	 * ensuring that the latest messages are always visible.
	 */
	@FXML
	public void initialize() {
		assert scrollPane != null : "ScrollPane is not initialized";
		assert dialogContainer != null : "DialogContainer is not initialized";
		assert userInput != null : "UserInput is not initialized";
		assert sendButton != null : "SendButton is not initialized";

		scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
	}

	/**
	 * Sets the Clarawr instance to be used by this GUI.
	 *
	 * @param c The Clarawr instance that handles user input and generates responses.
	 */
	public void setClarawr(Clarawr c) {

		assert c != null : "Clarawr instance is not initialized";
		clarawr = c;
	}

	/**
	 * Handles user input by creating two dialog boxes:
	 * - One for the user's input
	 * - One for Clarawr's response
	 * The dialog boxes are appended to the dialog container, and the user input field is cleared afterward.
	 */
	@FXML
	private void handleUserInput() {

		assert clarawr != null : "Clarawr instance is not initialized";

		String input = userInput.getText();
		String response = clarawr.getResponse(input);
		assert response != null && !response.isEmpty() : "Clarawr's response is null or empty";

		dialogContainer.getChildren().addAll(
				DialogBox.getUserDialog(input, userImage),
				DialogBox.getClarawrDialog(response, clarawrImage)
		);
		userInput.clear();
	}
}

