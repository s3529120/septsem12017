import com.sun.glass.ui.Screen;
import booking.BookingController;
import javafx.application.Application;
import javafx.stage.Stage;
import menu.MainMenuController;
import menu.MainMenuView;

@SuppressWarnings("restriction")
public class BookingSite extends Application {
	
	
	@Override
	public void start(Stage primaryStage) {
	   //Set Stage properties 
		primaryStage.setTitle("Booking Site");
		int primaryScreenBoundsx = Screen.getMainScreen().getWidth();
		int primaryScreenBoundsy = Screen.getMainScreen().getHeight();
		
        //set Stage boundaries to visible bounds of the main screen
		primaryStage.setX(0);
        primaryStage.setY(0);
        primaryStage.setWidth(primaryScreenBoundsx);
        primaryStage.setHeight(primaryScreenBoundsy);
		
		//Seed data for demonstration
		//Seed.initialize();
		
		//Generate bookings
		BookingController bcont=new BookingController();
		bcont.updateBookings();
		
		//Startup methods
		//Generate bookings as needed
		MainMenuController cont = new MainMenuController(primaryStage, 
				new MainMenuView(primaryStage));
		cont.updateView();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
