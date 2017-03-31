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

		Text dateerrortxt = new Text("Please Select a date");
		HBox dateerrorbox = new HBox();

		Text employeeerrortxt = new Text("Employee not selected");
		HBox employeeerrorbox = new HBox();

		Text emptyerrortxt = new Text("No employees to edit");
		HBox emptyerrorbox = new HBox();

		Text timeerrortxt = new Text("Invalid time selection");
		HBox timenerrorbox = new HBox();

		Text donetxt = new Text("Successfully added");
		HBox donebox = new HBox();

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
		box1.getChildren().add(dateerrorbox);


		box1.setId("form");

		//Box2
		String emps[];
		ComboBox<String> employee = new ComboBox<String>();
		employee.setPromptText("Employee");
		emps=cont.getEmployees();
		if(emps==null){
			employee.getItems().add("None Exist");
			if (!emptyerrorbox.getChildren().contains(emptyerrortxt)) {
				emptyerrorbox.getChildren().add(emptyerrortxt);
			}
		}else{
			for(int i=0;i<emps.length;i++){
				employee.getItems().add(emps[i]);
			}
			if (emptyerrorbox.getChildren().contains(emptyerrortxt)) {
				emptyerrorbox.getChildren().remove(emptyerrortxt);
			}
		}
		HBox employeebox = new HBox(employee);
		employeebox.setId("form");

		Label startlbl = new Label("Start Time: ");
		ComboBox<String> starttimes = new ComboBox<String>();
		starttimes.getItems().addAll(cont.getPossibleTimes());
		starttimes.visibleRowCountProperty().setValue(cont.getPossibleTimes().length);
		starttimes.getSelectionModel().selectFirst();
		HBox startbox = new HBox(startlbl,starttimes);
		startbox.setId("form");

		Label finishlbl = new Label("End Time: ");
		ComboBox<String> finishtimes = new ComboBox<String>();
		finishtimes.getItems().addAll(cont.getPossibleTimes());
		finishtimes.visibleRowCountProperty().setValue(cont.getPossibleTimes().length);
		finishtimes.getSelectionModel().selectFirst();
		HBox finishbox = new HBox(finishlbl,finishtimes);
		finishbox.setId("form");

		Button savebtn = new Button("Save");
		savebtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent e){

				if (cont.validateEntries(
						cont.getEmail(employee.getSelectionModel().getSelectedItem()), employeebox,
						availDatePicker.getValue(), box1,
						starttimes.getSelectionModel().getSelectedItem(), startbox,
						finishtimes.getSelectionModel().getSelectedItem(), finishbox,
						dateerrortxt,dateerrorbox,
						employeeerrortxt,employeeerrorbox,
						timeerrortxt,timenerrorbox)) {

					cont.addAvailability( 
							cont.getEmail(employee.getSelectionModel().getSelectedItem()), 
							availDatePicker.getValue(), 
							starttimes.getSelectionModel().getSelectedItem(),
							finishtimes.getSelectionModel().getSelectedItem());
					insertconfirm.getText().replaceAll(".*?",employee.getSelectionModel().getSelectedItem().toString()
							.concat("'s available time added"));
					if (!donebox.getChildren().contains(donetxt)) {
						donebox.getChildren().add(donetxt);

					}

				} else {
					if (donebox.getChildren().contains(donetxt)) {
						donebox.getChildren().remove(donetxt);
					}
				}
			}
		});

		VBox box2 = new VBox(employeebox,startbox,finishbox,savebtn,emptyerrorbox,employeeerrorbox,timenerrorbox,donebox);
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


		StackPane pane = new StackPane(page,insertconfirm);

		Scene scene = new Scene(pane);
		scene.getStylesheets().add(getClass().getResource("css/styles.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
}
