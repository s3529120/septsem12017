package menu.main;

import accounts.operations.UserRegistrationController;
import accounts.operations.UserRegistrationView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainMenuView {
	private Stage stage;
	private MainMenuController cont;

	/**
	 * Constructor, sets stage.
	 * 
	 * @param stage Window to manipulate.
	 */
	public MainMenuView(Stage stage) {
		this.stage = stage;
	}

	/**
	 * Updates associated window.
	 */
	public void updateView() {

		BorderPane border = new BorderPane();

		// Headings
		Text txt = new Text("Dog Groomer");
		HBox heading = new HBox(txt);
		heading.setAlignment(Pos.CENTER);

		// Login button
		Button loginbtn = new Button("Login");
		loginbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				cont.loginEvent();
			}
		});

		// Register button
		Button registerbtn = new Button("Register");
		registerbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				UserRegistrationView view = new UserRegistrationView(stage);
				UserRegistrationController cont = new UserRegistrationController(view);
				cont.updateView();
			}
		});

		// Layout

		VBox vbox = new VBox(15, heading, registerbtn, loginbtn);

		BorderPane.setAlignment(vbox, Pos.CENTER);
		BorderPane.setMargin(vbox, new Insets(12, 12, 12, 12));
		border.setCenter(vbox);

		HBox tophbox = new HBox();
		tophbox.setPadding(new Insets(100, 12, 15, 12));
		border.setTop(tophbox);

		VBox leftvbox = new VBox();
		leftvbox.setPadding(new Insets(15, 400, 15, 12));
		border.setLeft(leftvbox);

		VBox rightvbox = new VBox();
		rightvbox.setPadding(new Insets(15, 400, 15, 12));
		border.setRight(rightvbox);

		HBox bottomhbox = new HBox();
		bottomhbox.setPadding(new Insets(100, 12, 15, 12));
		border.setBottom(bottomhbox);

		// Styles
		vbox.setId("mainMenuVBox");
		border.setId("border");
		registerbtn.getStyleClass().add("orangebtn");
		loginbtn.getStyleClass().add("orangebtn");
		heading.getStyleClass().add("main-heading");

		// Adding layout to scene
		Scene scene = new Scene(border);
		scene.getStylesheets().add(getClass().getResource("/resources/display/css/styles.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Sets associated controller
	 * 
	 * @param cont
	 *            Controller to associate.
	 * @return True upon success.
	 */
	public Boolean setController(MainMenuController cont) {
		this.cont = cont;
		return true;
	}
}
