package admin;

import accounts.AdminAccountModel;
import admin.AdminController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utils.AppData;

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

		// Heading
		Text h1;
	
		h1 = new Text("Administration Panel");
		
		h1.setId("bookingsh1");
		HBox bookingstitle = new HBox(h1);
		bookingstitle.setId("bookingstitle");
		Button switchbtn = new Button();
		HBox head;

		if (AppData.CALLER instanceof AdminAccountModel){
			switchbtn.setId("switchbtn");
			head = new HBox(bookingstitle, switchbtn);
		}


		// Create the stage and apply css
		Scene scene = new Scene(sp);
		scene.getStylesheets().add(getClass().getResource("/resources/display/css/styles.css").toExternalForm());

		stage.setScene(scene);
		stage.show();
	}
}