import Controller.BookingController;
import Controller.DefaultController;
import View.MainMenuView;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BookingSite extends Application {

	@Override
	public void start(Stage primaryStage) {
	   //Set Stage properties 
		primaryStage.setTitle("Booking Site");
		primaryStage.setWidth(1024);
		primaryStage.setHeight(720);
		
		//Seed data for demonstration
		Seed.initialize();
		
		//Generate bookings
		BookingController bcont=new BookingController();
		bcont.updateBookings();
		
		//Startup methods
		//Generate bookings as needed
		DefaultController cont = new DefaultController(primaryStage, 
				new MainMenuView(primaryStage));
		cont.updateView();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
