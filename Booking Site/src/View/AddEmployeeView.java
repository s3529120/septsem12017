package View;

import Controller.EmployeeController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AddEmployeeView
{
   public Stage stage;
   private EmployeeController cont;
   
   public AddEmployeeView(Stage stage){
      this.stage=stage;
   }
   
   public Boolean setController(EmployeeController cont){
      this.cont=cont;
      return true;
   }
   
   public void updateView(){
      Button backbtn = new Button("Go Back");
      backbtn.setOnAction(new EventHandler<ActionEvent>(){
         @Override public void handle(ActionEvent e){
            stage.close();
         }
      });
      Text h1 = new Text("Add Employee");
      
      //Boxes
      //Row 1
      TextField fnamefield = new TextField();
      fnamefield.setPromptText("First Name");
      
      TextField streetaddfield = new TextField();
      streetaddfield.setPromptText("Street Address");
      
      HBox r1 = new HBox(fnamefield,streetaddfield);
      
      //Row 2
      TextField snamefield = new TextField();
      snamefield.setPromptText("Last Name");
      
      TextField cityfield = new TextField();
      cityfield.setPromptText("City");
      
      HBox r2 = new HBox(snamefield,cityfield);
      
      //Row 3
      TextField emailfield = new TextField();
      emailfield.setPromptText("Email");
      
      ComboBox<String> statebox = new ComboBox<String>();
      statebox.setPromptText("State");
      statebox.getItems().addAll("A.C.T","N.S.W","Queensland","South Australia",
                                 "Tasmainia","Victoria","Western Australia");
      
      HBox r3 = new HBox(emailfield,statebox);
      
      //Row 4
      TextField contactnofield = new TextField();
      fnamefield.setPromptText("Contact no.");
      
      TextField pcodefield = new TextField();
      pcodefield.setPromptText("Post Code");
      
      HBox r4 = new HBox(contactnofield,pcodefield);
      
      //Boxes
      VBox boxes = new VBox(r1,r2,r3,r4);
      
      //Submit button
      Button subbtn = new Button("View Bookings");
      subbtn.setOnAction(new EventHandler<ActionEvent>(){
         @Override public void handle(ActionEvent e){
            cont.addEmployee(fnamefield.getText()+snamefield.getText(), 
                             contactnofield.getText(), emailfield.getText(), 
                             streetaddfield.getText(), cityfield.getText(), 
                             statebox.getValue(), pcodefield.getText());
         }
      });
     
      //Layout
      StackPane pane = new StackPane();
      
      pane.getChildren().addAll(backbtn,h1,boxes,subbtn);
      Scene scene = new Scene(pane);
      stage.setScene(scene);
      stage.show();
      
   }
}
