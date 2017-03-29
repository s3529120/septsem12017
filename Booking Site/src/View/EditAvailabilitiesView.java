package View;

import Controller.AvailabilitiesController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class EditAvailabilitiesView
{
   public Stage stage;
   private AvailabilitiesController cont;
   
   public EditAvailabilitiesView(Stage stage){
      this.stage=stage;
   }
   
   public Boolean setController(AvailabilitiesController cont){
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
      
      DatePicker checkInDatePicker = new DatePicker();

      GridPane gridPane = new GridPane();
      gridPane.setHgap(10);
      gridPane.setVgap(10);

      Label checkInlabel = new Label("Available:");
      gridPane.add(checkInlabel, 0, 0);

      
      VBox vbox = new VBox(20);
      GridPane.setHalignment(checkInlabel, HPos.LEFT);
      gridPane.add(checkInDatePicker, 0, 1);
      vbox.getChildren().add(gridPane);
      
      StackPane pane = new StackPane(backbtn,h1,vbox);
      
      Scene scene = new Scene(pane);
      stage.setScene(scene);
      stage.show();
   }
}
