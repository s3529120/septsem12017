package View;

import java.util.List;

import Controller.TypeController;
import Controller.UserRegistrationController;
import Model.TypeModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TypeView
{
   private TypeController cont;
   public Stage stage;
   
   public TypeView(Stage stage){
      this.stage=stage;
   }
   
   //Mutators
   public void setCont(TypeController cont){
      this.cont=cont;
   }
   
   
   //Methods
   public void updateView(){
      Text  h1=new Text(cont.getEmpname()+"'s Specializations");
      
      //Add
      List<TypeModel> unknown = cont.getUnknownTypes();
      Label addlbl = new Label("Specialization to add");
      ComboBox<String> addbox = new ComboBox<String>();
      unknown.forEach(x->{
         addbox.getItems().add(x.getName());
      });
      Button addbtn = new Button("Add");
      addbtn.setOnAction(new EventHandler<ActionEvent>(){
         @Override public void handle(ActionEvent e){
            cont.addSpec(addbox.getSelectionModel().getSelectedItem());
         }
      });
      VBox addVbox = new VBox(addlbl,addbox,addbtn);
      
      //Remove
      List<TypeModel> known = cont.getSetTypes();
      Label remlbl = new Label("Specialization to remove");
      ComboBox<String> rembox = new ComboBox<String>();
      known.forEach(x->{
         addbox.getItems().add(x.getName());
      });
      Button rembtn = new Button("Remove");
      addbtn.setOnAction(new EventHandler<ActionEvent>(){
         @Override public void handle(ActionEvent e){
            cont.removeSpec(rembox.getSelectionModel().getSelectedItem());
         }
      });
      VBox remVbox = new VBox(remlbl,rembox,rembtn);
      
      //Done
      Button done = new Button("Done");
      done.setOnAction(new EventHandler<ActionEvent>(){
         @Override public void handle(ActionEvent e){
            stage.close();
         }
      });
      
      //Layout
      StackPane pane = new StackPane();
      pane.getChildren().addAll(h1,addVbox,remVbox,done);
      Scene scene = new Scene(pane);
      stage.setScene(scene);
      stage.show();
   }
}
