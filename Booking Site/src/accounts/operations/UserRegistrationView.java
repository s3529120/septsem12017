package accounts.operations;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import menu.main.MainMenuController;
import menu.main.MainMenuView;

public class UserRegistrationView {
	public Stage stage;
	private UserRegistrationController cont;

	/**
	 * Constructor, sets stage.
	 * 
	 * @param stage
	 *            Window to manipulate.
	 */
	public UserRegistrationView(Stage stage) {
		this.stage = stage;
	}

	/**
	 * Sets associated controller
	 * 
	 * @param cont
	 *            Controller to associate.
	 * @return True upon success.
	 */
	public Boolean setController(UserRegistrationController cont) {
		this.cont = cont;
		return true;
	}

	/**
	 * Updates associated window.
	 */
	public void updateView() {

		// Heading
		Text logtxt = new Text("Already have an account?");

		// Login button
		Button loginbtn = new Button("Login");
		loginbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				LoginController cont = new LoginController(new LoginView(stage));
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

		//VBox logbox = new VBox(logtxt, loginbtn);

		// Heading
		Text regtxt = new Text("Register with us.");

		// Username
		TextField usertxtfield = new TextField();
		usertxtfield.setPromptText("Username");
		HBox unamehbox = new HBox(usertxtfield);
		usertxtfield.setId("form");
		unamehbox.setAlignment(Pos.CENTER);

		// Name
		TextField pnametxtfield = new TextField();
		pnametxtfield.setPromptText("Full Name");
		HBox pnamehbox = new HBox(pnametxtfield);
		pnametxtfield.setId("form");
		pnamehbox.setAlignment(Pos.CENTER);

		// Address
		TextField addtxtfield = new TextField();
		addtxtfield.setPromptText("Address");
		HBox addhbox = new HBox(addtxtfield);
		addtxtfield.setId("form");
		addhbox.setAlignment(Pos.CENTER);

		// Contact number
		TextField numtxtfield = new TextField();
		numtxtfield.setPromptText("Contact Number");
		HBox numhbox = new HBox(numtxtfield);
		numtxtfield.setId("form");
		numhbox.setAlignment(Pos.CENTER);

		// Email address
		TextField mailtxtfield = new TextField();
		mailtxtfield.setPromptText("Email Address");
		HBox mailhbox = new HBox(mailtxtfield);
		mailtxtfield.setId("form");
		mailhbox.setAlignment(Pos.CENTER);

		// Password
		PasswordField pwordfield = new PasswordField();
		pwordfield.setPromptText("Password");
		HBox pwordhbox = new HBox(pwordfield);
		pwordfield.setId("form");
		pwordhbox.setAlignment(Pos.CENTER);

		// Confirm password
		PasswordField pwordfieldcon = new PasswordField();
		pwordfieldcon.setPromptText("Confirm Password");
		HBox pwordhboxcon = new HBox(pwordfieldcon);
		pwordfieldcon.setId("form");
		pwordhboxcon.setAlignment(Pos.CENTER);

		// Error boxes

		// Unfilled box
		Text emptyerrortxt = new Text("All fields must be filled");
		HBox emptyerrorbox = new HBox();
		emptyerrorbox.setAlignment(Pos.CENTER);

		// Password mismatch
		Text passerrortxt = new Text("Entered passwords do not match");
		HBox passerrorbox = new HBox();
		passerrorbox.setAlignment(Pos.CENTER);

		// Username taken
		Text unameerrortxt = new Text("That username is unavailable");
		HBox unameerrorbox = new HBox();
		unameerrorbox.setAlignment(Pos.CENTER);

		// invalid name
		Text pnamerrortxt = new Text("Please enter a name using only letters, spaces, and hyphens");
		HBox pnameerrorbox = new HBox();
		pnameerrorbox.setAlignment(Pos.CENTER);

		// Invalid email
		Text emailerrortxt = new Text("Please enter an email in the correct format");
		HBox emailerrorbox = new HBox();
		emailerrorbox.setAlignment(Pos.CENTER);

		// Phone too long
		Text phoneerrortxt = new Text("Please enter a valid phone number only 10 digits long");
		HBox phoneerrorbox = new HBox();
		phoneerrorbox.setAlignment(Pos.CENTER);

		// Street address invalid
		Text streeterrortxt = new Text("Please enter a street number followed by a street name");
		HBox streeterrorbox = new HBox();
		streeterrorbox.setAlignment(Pos.CENTER);

		// Register button
		Button registerbtn = new Button("Register");
		registerbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {

				// checking to make sure all fields are filled
				if (cont.checkValues(usertxtfield, pnametxtfield, pwordfield, pwordfieldcon, numtxtfield, addtxtfield,
						mailtxtfield)) {
					cont.register(usertxtfield.getText(), pnametxtfield.getText(), pwordfield.getText(),
							numtxtfield.getText(), addtxtfield.getText(), mailtxtfield.getText());
				} else {
					// checking for empty
					cont.validateEntries(usertxtfield, unamehbox, pnametxtfield, pnamehbox, pwordfield, pwordhbox,
							pwordfieldcon, pwordhboxcon, addtxtfield, addhbox, numtxtfield, numhbox, mailtxtfield,
							mailhbox, emptyerrortxt, emptyerrorbox, passerrortxt, passerrorbox, unameerrortxt,
							unameerrorbox, pnamerrortxt, pnameerrorbox, emailerrortxt, emailerrorbox, phoneerrortxt,
							phoneerrorbox, streeterrortxt, streeterrorbox);
				}
			}
		});
		HBox btnbox = new HBox(registerbtn);
		btnbox.setAlignment(Pos.CENTER);

		// Layout
		
		BorderPane border = new BorderPane();
		VBox body = new VBox(15, regtxt, unamehbox, unameerrorbox, pnamehbox, pnameerrorbox, addhbox, streeterrorbox, numhbox, phoneerrorbox, mailhbox, emailerrorbox, pwordhbox,
				pwordhboxcon, passerrorbox, btnbox, emptyerrorbox, loginbtn);

		BorderPane.setAlignment(body, Pos.CENTER);
		BorderPane.setMargin(body, new Insets(12, 12, 12, 12));
		border.setCenter(body);

		HBox tophbox = new HBox();
		tophbox.setPadding(new Insets(20, 12, 15, 12));
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
		
		

		//HBox regmenubox = new HBox(vbox, logbox);

		// Styles
		body.setId("mainMenuVBox");
		border.setId("border");
		registerbtn.getStyleClass().add("orangebtn");
		returnbtn.setId("largebtn");
		loginbtn.getStyleClass().add("linkbtn");
		regtxt.getStyleClass().add("main-heading");
		logtxt.setId("heading");

		//StackPane pane = new StackPane(body);

		Scene scene = new Scene(border);
		scene.getStylesheets().add(getClass().getResource("css/styles.css").toExternalForm());
		stage.setScene(scene);
	}
}
