package Clarawr;

import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for the main GUI.
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

	private Image userImage = new Image(this.getClass().getResourceAsStream("/images/userIcon.png"));
	private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/cuteLion.png"));

	/**
	 * Initializes the MainWindow.
	 * Binds the vertical scroll property of the ScrollPane to the height of the dialog container,
	 * ensuring that the latest messages are always visible.
	 */
	@FXML
	public void initialize() {
		scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
	}

	/**
	 * Sets the Clarawr instance to be used by this GUI.
	 *
	 * @param c The Clarawr instance that handles user input and generates responses.
	 */
	public void setClarawr(Clarawr c) {
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
		String input = userInput.getText();
		String response = clarawr.getResponse(input);
		dialogContainer.getChildren().addAll(
				DialogBox.getUserDialog(input, userImage),
				DialogBox.getClarawrDialog(response, dukeImage)
		);
		userInput.clear();
	}
}

