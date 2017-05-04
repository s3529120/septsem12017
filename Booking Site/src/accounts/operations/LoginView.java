package accounts.operations;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import menu.main.MainMenuController;
import menu.main.MainMenuView;

public class LoginView {
	private LoginController cont;
	public Stage stage;

	/**
	 * Constructor, sets stage.
	 * 
	 * @param stage
	 *            Window to manipulate.
	 */
	public LoginView(Stage stage) {
		this.stage = stage;
	}

	/**
	 * Sets associated controller.
	 * 
	 * @param controller
	 *            Controller to associate.
	 * @return True upon success.
	 */
	public Boolean setController(LoginController controller) {
		this.cont = controller;
		return true;
	}

	/**
	 * Update associated window.
	 */
	public void updateView() {

		// Register button
		Text regtxt = new Text("Register with us.");
		Button registerbtn = new Button("Register");
		registerbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				UserRegistrationView view = new UserRegistrationView(stage);
				UserRegistrationController cont = new UserRegistrationController(view);
				cont.updateView();
			}
		});

		// Return button
		Button returnbtn = new Button("Return");
		returnbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
			   MainMenuController cont = new MainMenuController(stage, new MainMenuView(stage));
				cont.updateView();
			}
		});

		// Register box
		VBox regbox = new VBox(regtxt, registerbtn);

		// Welcome text
		Text welcometxt = new Text("Welcome back!");

		// Username field
		TextField usernamefield = new TextField();
		HBox usernameBox = new HBox();
		usernamefield.setPromptText("Username");
		usernameBox.getChildren().add(usernamefield);

		// Password field
		PasswordField pwordfield = new PasswordField();
		HBox pwordBox = new HBox();
		pwordfield.setPromptText("Password");
		pwordBox.getChildren().add(pwordfield);

		// Empty error box
		Text emptyerrortxt = new Text("All fields must be filled");
		HBox emptyerrorbox = new HBox();

		// Incorrect entry box
		Text failerrortxt = new Text("Incorrect username and password combination");
		HBox failerrorbox = new HBox();

		// Login button
		Button loginbtn = new Button("Login");
		loginbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if (cont.isNotEmpty(usernamefield.getText(), pwordfield.getText())) {
					if (!cont.login(usernamefield.getText(), pwordfield.getText())) {
						if (!failerrorbox.getChildren().contains(failerrortxt)) {
							failerrorbox.getChildren().add(failerrortxt);
						}
						cont.validateEntries(usernamefield, usernameBox, pwordfield, pwordBox, emptyerrortxt,
								emptyerrorbox);
						usernameBox.setId("form");
						pwordBox.setId("form");
					}
				} else {
					cont.validateEntries(usernamefield, usernameBox, pwordfield, pwordBox, emptyerrortxt,
							emptyerrorbox);
					if (failerrorbox.getChildren().contains(failerrortxt)) {
						failerrorbox.getChildren().remove(failerrortxt);
					}
				}
			}
		});

		// Layout

		VBox vbox = new VBox(returnbtn, welcometxt, usernameBox, pwordBox, loginbtn, failerrorbox, emptyerrorbox);

		HBox loginpageBox = new HBox(regbox, vbox);

		// Styles
		loginpageBox.getStyleClass().add("loginpageBox");
		vbox.getStyleClass().add("vbox");
		returnbtn.setId("loginbtn");
		regbox.getStyleClass().add("regbox");
		usernameBox.setId("form");
		pwordBox.setId("form");
		registerbtn.setId("registerbtn");
		regtxt.setId("heading");
		welcometxt.setId("heading");
		loginbtn.setId("loginbtn");

		StackPane pane = new StackPane(loginpageBox);

		Scene scene = new Scene(pane);
		scene.getStylesheets().add(getClass().getResource("/resources/display/css/styles.css").toExternalForm());
		stage.setScene(scene);
	}

}
