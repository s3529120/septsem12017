package View;

import Controller.AvailabilitiesController;
import Controller.BusinessAccountMenuController;
import Controller.DefaultController;
import Controller.EmployeeController;
import Model.BusinessAccountModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class BusinessAccountMenuView {

	public Stage stage;
	private BusinessAccountMenuController cont;
	
	public BusinessAccountMenuView(Stage stage) {
		this.stage=stage;
	}
	
	public Boolean setController(BusinessAccountMenuController controller){
		this.cont=controller;
		return true;
	}
	
	public void updateView(BusinessAccountModel model){
	   
	   //Logout button
	   Button logoutbtn = new Button("Logout");
      logoutbtn.setOnAction(new EventHandler<ActionEvent>(){
         @Override public void handle(ActionEvent e){
            MainMenuView mainview = new MainMenuView(stage);
            DefaultController maincont = new DefaultController(stage,mainview);
            maincont.updateView();
         }
      });
	   
      //Headings and text
		Text h1 = new Text("Welcome "+model.getBusinessName()+"!");
		Text h2 = new Text("Select from the menu options below.");
		
		//Functionality buttons
		Button addempbtn = new Button("Add Employee");
      addempbtn.setOnAction(new EventHandler<ActionEvent>(){
         @Override public void handle(ActionEvent e){
            EmployeeController empcont = new EmployeeController();
            empcont.setView(new AddEmployeeView(new Stage()));
            empcont.updateView();
         }
      });
      Button editavailbtn = new Button("Edit Availabilities");
      editavailbtn.setOnAction(new EventHandler<ActionEvent>(){
         @Override public void handle(ActionEvent e){
            AvailabilitiesController cont =  new AvailabilitiesController();
            cont.setView(new EditAvailabilitiesView(new Stage()));
            cont.getView().setController(cont);
            cont.updateView();
         }
      });
      HBox toprow = new HBox(addempbtn,editavailbtn);
      
      Button viewavailbtn = new Button("View Availabilities");
      viewavailbtn.setOnAction(new EventHandler<ActionEvent>(){
         @Override public void handle(ActionEvent e){
         }
      });
      Button viewbookbtn = new Button("View Bookings");
      viewbookbtn.setOnAction(new EventHandler<ActionEvent>(){
         @Override public void handle(ActionEvent e){
         }
      });
      HBox botrow = new HBox(viewavailbtn,viewbookbtn);
		
      VBox headings = new  VBox(h1,h2);
      VBox buttons = new  VBox(toprow,botrow);
		
		StackPane pane = new StackPane();
		pane.getChildren().add(logoutbtn);
		pane.getChildren().add(headings);
		pane.getChildren().add(buttons);
		Scene scene = new Scene(pane);
		stage.setScene(scene);
	}

}
