package View;

import Controller.AvailabilitiesController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
		HBox backbox = new HBox(backbtn);
		Text h1 = new Text("Manage Availabilities");

		Text insertconfirm = new Text("");
		
		HBox top = new HBox(h1,backbox);

		//Box 1
		DatePicker availDatePicker = new DatePicker();

		GridPane gridPane = new GridPane();
		gridPane.setHgap(10);
		gridPane.setVgap(10);

		Label checkInlabel = new Label("Available:");
		gridPane.add(checkInlabel, 0, 0);


		VBox box1 = new VBox(20);
		GridPane.setHalignment(checkInlabel, HPos.LEFT);
		gridPane.add(availDatePicker, 0, 1);
		box1.getChildren().add(gridPane);

		//Box2
		String emps[];
		ComboBox<String> employee = new ComboBox<String>();
		employee.setPromptText("Employee");
		emps=cont.getEmployees();
		if(emps==null){
			employee.getItems().add("None Exist");
		}else{
			for(int i=0;i<emps.length;i++){
				employee.getItems().add(emps[i]);
			}
		}

		Label startlbl = new Label("Start Time: ");
		ComboBox<String> starttimes = new ComboBox<String>();
		starttimes.getItems().addAll(cont.getPossibleTimes());
		starttimes.getSelectionModel().selectFirst();
		HBox startbox = new HBox(startlbl,starttimes);

		Label finishlbl = new Label("End Time: ");
		ComboBox<String> finishtimes = new ComboBox<String>();
		finishtimes.getItems().addAll(cont.getPossibleTimes());
		finishtimes.getSelectionModel().selectFirst();
		HBox finishbox = new HBox(finishlbl,finishtimes);

		Button savebtn = new Button("Save");
		savebtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent e){

				cont.addAvailability( cont.getEmail(employee.getSelectionModel().getSelectedItem()), 
						availDatePicker.getValue(), starttimes.getSelectionModel().getSelectedItem(),
						finishtimes.getSelectionModel().getSelectedItem());
				insertconfirm.getText().replaceAll(".*?",employee.getSelectionModel().getSelectedItem().toString()
						.concat("'s available time added"));
			}
		});

		VBox box2 = new VBox(employee,startbox,finishbox,savebtn);
		HBox body = new HBox(box1,box2);
		VBox page = new VBox(top,body);
		
		page.getStyleClass().add("loginpageBox");
		box1.getStyleClass().add("editavailBox");
		box2.getStyleClass().add("editavailBox");
		backbtn.setId("largebtn");
		savebtn.setId("largebtn");
		backbtn.setId("logoutbtn");
		backbox.setId("logoutbox");
		h1.setId("heading");
		employee.setId("form");
		startbox.setId("form");
		finishbox.setId("form");
		

		StackPane pane = new StackPane(page,insertconfirm);

		Scene scene = new Scene(pane);
		scene.getStylesheets().add(getClass().getResource("css/styles.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
}
