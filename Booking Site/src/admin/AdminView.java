package admin;

import admin.AdminController;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AdminView {


	private ScrollPane sp;
	private String currentdate;

	private AdminController cont;
	public Stage stage;

	public AdminView(Stage stage) {
		this.stage = stage;
	}

	/**
	 * Sets associated controller
	 * 
	 * @param controller
	 * @return
	 */
	public Boolean setController(AdminController controller) {
		this.cont = controller;
		return true;
	}

	public void updateView() {
		sp = new ScrollPane();

		// Header init
		Text heading = new Text("Booking Site");
		heading.getStyleClass().add("main-heading");
		
		Scene scene = new Scene(sp);
		scene.getStylesheets().add(getClass().getResource("/resources/display/css/styles.css").toExternalForm());

		stage.setScene(scene);
		stage.show();
	}
}