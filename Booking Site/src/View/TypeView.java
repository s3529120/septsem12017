package View;

import java.util.List;

import Controller.AvailabilitiesController;
import Controller.BookingController;
import Controller.DefaultController;
import Controller.EmployeeController;
import Controller.TypeController;
import Controller.UserRegistrationController;
import Model.TypeModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utils.AppData;

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
			rembox.getItems().add(x.getName());
		});
		Button rembtn = new Button("Remove");
		rembtn.setOnAction(new EventHandler<ActionEvent>(){
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

		//current specialisations
		String specString = "Currently consists of";
		for (TypeModel type: known) {
			specString += " " + type.getName() + ",";
		}
		specString = specString.substring(0, specString.length() - 1) + ".";
		Text specText = new Text(specString);

		//Layout
		StackPane pane = new StackPane();
		VBox all = new VBox(h1,specText,addVbox,remVbox,done);
		pane.getChildren().addAll(all);
		Scene scene = new Scene(pane);
		scene.getStylesheets().add(getClass().getResource("css/styles.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}

	public void updateTypeView(){
		//Header init
		Text heading = new Text("Booking Site");
		//Add employee
		Button addempbtn = new Button("Add Employee");
		addempbtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent e){
				EmployeeController empcont = new EmployeeController();
				empcont.setView(new AddEmployeeView(stage));
				empcont.getView().setController(empcont);
				empcont.updateView();
			}
		});

		//Edit availability
		Button editavailbtn = new Button("Edit Employee");
		editavailbtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent e){
				AvailabilitiesController acont =  new AvailabilitiesController();
				acont.setView(new EditAvailabilitiesView(stage));
				acont.getView().setController(acont);
				acont.updateView();
			}
		});




		//View Bookings
		Button viewbookbtn = new Button("View Bookings");
		viewbookbtn.setUserData(cont);
		viewbookbtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent e){
				BookingController bcont = new BookingController();
				bcont.setView(new BookingsView(stage));
				bcont.getView().setController(bcont);
				bcont.updateView();
			}
		});
		//Logout button
		Button logoutbtn = new Button("Logout");
		logoutbtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent e){
				MainMenuView mainview = new MainMenuView(stage);
				DefaultController maincont = new DefaultController(stage,mainview);
				AppData.CALLER=null;
				maincont.updateView();
			}
		});
		HBox header = new HBox(heading,viewbookbtn,addempbtn,editavailbtn,logoutbtn);
		Text h1 = new Text("Edit Services");

		//Add
		Text h2 = new Text("Add service");
		Label typelbl = new Label("Service name: ");
		TextField typefield = new TextField();
		Label numlbl = new Label("Duration in minutes: ");
		TextField numfield = new TextField();
		Button addbtn = new Button("Add");
		addbtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent e){
				cont.addType(typefield.getText(),Integer.parseInt(numfield.getText()));
				updateTypeView();
			}
		});
		HBox row1 = new HBox(typelbl,typefield);
		HBox row2 = new HBox(numlbl,numfield);
		VBox addVbox = new VBox(h2,row1,row2,addbtn);

		//Remove
		List<TypeModel> known = TypeController.getAllTypes();
		Label remlbl = new Label("Service to remove");
		ComboBox<String> rembox = new ComboBox<String>();
		known.forEach(x->{
			rembox.getItems().add(x.getName());
		});
		Button rembtn = new Button("Remove");
		rembtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent e){
				cont.removeType(rembox.getSelectionModel().getSelectedItem());
				updateTypeView();
			}
		});
		VBox remVbox = new VBox(remlbl,rembox,rembtn);

		//current types
		String servicesString = "The current avaialable services are";
		for (TypeModel type: known) {
			servicesString += " " + type.getName() + ",";
		}
		servicesString = servicesString.substring(0, servicesString.length() - 1) + ".";
		Text servicesText = new Text(servicesString);

		//Layout
		StackPane pane = new StackPane();
		VBox all = new VBox(header,h1,servicesText,addVbox,remVbox);
		pane.getChildren().addAll(all);
		Scene scene = new Scene(pane);
		scene.getStylesheets().add(getClass().getResource("css/styles.css").toExternalForm());
		stage.setScene(scene);
		stage.show();
	}
}
