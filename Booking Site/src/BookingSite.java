import Controller.DefaultController;
import View.MainMenuView;
import javafx.application.Application;
import javafx.stage.Stage;

public class BookingSite extends Application {

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Booking Site");
		primaryStage.setWidth(800);
		primaryStage.setHeight(600);
		Seed.initialize();
		
		DefaultController cont = new DefaultController(primaryStage, 
				new MainMenuView(primaryStage));
		cont.updateView();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
