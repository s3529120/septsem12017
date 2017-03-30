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
      
      backbtn.getStyleClass().add("redbtn");
      
      
    //Confirmation message and heading
      
      Text empadded = new Text("");
      
      Text h1 = new Text("Add Employee");
      h1.setId("heading");

      ////////Vertical box 1 - user info
      
      TextField fnamefield = new TextField();
      fnamefield.setPromptText("First Name");
      fnamefield.getStyleClass().add("textField");
      
      TextField snamefield = new TextField();
      snamefield.setPromptText("Last Name");
      snamefield.getStyleClass().add("textField");
      
      TextField emailfield = new TextField();
      emailfield.setPromptText("Email");
      emailfield.getStyleClass().add("textField");
      
      TextField contactnofield = new TextField();
      contactnofield.setPromptText("Contact no.");
      contactnofield.getStyleClass().add("textField");
      
      // Add above elements to vertical box
      VBox userInfo = new VBox(backbtn, empadded, h1, fnamefield, snamefield, emailfield, contactnofield);
      userInfo.getStyleClass().add("addEmpVbox");
      
      /////// Vertical box 2 - address
      TextField streetaddfield = new TextField();
      streetaddfield.setPromptText("Street Address");
      streetaddfield.getStyleClass().add("textField");
      
      TextField cityfield = new TextField();
      cityfield.setPromptText("City");
      cityfield.getStyleClass().add("textField");
      
      ComboBox<String> statebox = new ComboBox<String>();
      statebox.setPromptText("State");
      statebox.getItems().addAll("A.C.T","N.S.W","Queensland","South Australia",
                                 "Tasmainia","Victoria","Western Australia");
      statebox.getStyleClass().add("textField");
      
      TextField pcodefield = new TextField();
      pcodefield.setPromptText("Post Code");
      pcodefield.getStyleClass().add("textField");
      
      //Submit button
      Button subbtn = new Button("Add");
      subbtn.setOnAction(new EventHandler<ActionEvent>(){
         @Override public void handle(ActionEvent e){
            cont.addEmployee(fnamefield.getText().concat(snamefield.getText()), 
                             contactnofield.getText(), emailfield.getText(), 
                             streetaddfield.getText(), cityfield.getText(), 
                             statebox.getValue(), pcodefield.getText());
            empadded.getText().replaceAll(".*?", fnamefield.getText().
                                          concat(snamefield.getText()).concat(" added to Employees"));
         }
      });
      
      subbtn.getStyleClass().add("bluebtn");
      
      // Add above elements to vertical box
      VBox addressInfo = new VBox(streetaddfield, cityfield, pcodefield, statebox, subbtn);
      addressInfo.setId("empAddressVbox");


      //Layout
      
      HBox addEmployeeBox = new HBox(userInfo, addressInfo);
      addEmployeeBox.setId("addEmpPageBox");
      
      StackPane pane = new StackPane();
      
      pane.getChildren().addAll(addEmployeeBox);
      Scene scene = new Scene(pane, 850, 450);
      scene.getStylesheets().add(getClass().getResource("css/styles.css").toExternalForm());
      stage.setScene(scene);
      stage.show();
      
   }
}
