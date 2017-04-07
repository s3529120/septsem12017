package View;

import Controller.BookingController;
import Controller.DefaultController;
import Controller.EmployeeController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class BookingsView {
    
    private BookingController cont;
    public Stage stage;
    private Parent bookingsLayout;
    
    @FXML
   private Button backbtn;

   @FXML
   private Text header;
    
    public BookingsView(Stage stage) {
        this.stage=stage;
    }
    
    public Boolean setController(BookingController controller){
        this.cont=controller;
        return true;
    }
    
    public void updateView() {

    	//heading
    	 Button backbtn = new Button("Go Back");
         backbtn.setOnAction(new EventHandler<ActionEvent>(){
            @Override public void handle(ActionEvent e){
               stage.close();
            }
         });
         
         backbtn.getStyleClass().add("redbtn");
         
         Text h1 = new Text("View Bookings");
         h1.setId("heading");
         
         VBox heading = new VBox(backbtn, h1);
         heading.setId("bookingsHeader");
         
        //Bookings
         Text noBookings = new Text("No current bookings.");
         
         VBox bookingsList = new VBox(noBookings);
         bookingsList.setId("bookingsList");
         
       //Layout
         
         HBox main = new HBox(heading, bookingsList);
         main.setId("bookingsMain");
         
         StackPane pane = new StackPane();
         
         pane.getChildren().addAll(main);
         Scene scene = new Scene(pane, 850, 450);
         scene.getStylesheets().add(getClass().getResource("css/styles.css").toExternalForm());
         stage.setScene(scene);
         stage.show();
        
    }
	
}
