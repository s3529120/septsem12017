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

	/**Constructor, associates stage.
	 * @param stage Window to manipulate.
	 */
	public AddEmployeeView(Stage stage){
		this.stage=stage;
	}

	/**Sets associated controller.
	 * @param cont Controller to associate.
	 * @return True upon success.
	 */
	public Boolean setController(EmployeeController cont){
		this.cont=cont;
		return true;
	}

	/**Updates display of window.
	 */
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

		//Vertical box 1 - user info

		//First name
		TextField fnamefield = new TextField();
		fnamefield.setPromptText("First Name");
		fnamefield.getStyleClass().add("textField");
		HBox fnamehbox = new HBox(fnamefield);
		fnamehbox.setId("form");

		//Surname
		TextField snamefield = new TextField();
		snamefield.setPromptText("Last Name");
		snamefield.getStyleClass().add("textField");
		HBox snamehbox = new HBox(snamefield);
		snamehbox.setId("form");

		//Email address
		TextField emailfield = new TextField();
		emailfield.setPromptText("Email");
		emailfield.getStyleClass().add("textField");
		HBox emailhbox = new HBox(emailfield);
		emailhbox.setId("form");

		//Contact number
		TextField contactnofield = new TextField();
		contactnofield.setPromptText("Contact no.");
		contactnofield.getStyleClass().add("textField");
		HBox contactnohbox = new HBox(contactnofield);
		contactnohbox.setId("form");

		// Add above elements to vertical box
		VBox userInfo = new VBox(backbtn, empadded, h1, fnamehbox, snamehbox, emailhbox, contactnohbox);
		userInfo.getStyleClass().add("addEmpVbox");

		// Vertical box 2 - address
		
		//Street address
		TextField streetaddfield = new TextField();
		streetaddfield.setPromptText("Street Address");
		streetaddfield.getStyleClass().add("textField");
		HBox streetaddhbox = new HBox(streetaddfield);
		streetaddhbox.setId("form");

		//City
		TextField cityfield = new TextField();
		cityfield.setPromptText("City");
		cityfield.getStyleClass().add("textField");
		HBox cityhbox = new HBox(cityfield);
		cityhbox.setId("form");

		//State
		ComboBox<String> statebox = new ComboBox<String>();
		statebox.setPromptText("State");
		statebox.getItems().addAll("A.C.T","N.S.W","Queensland","South Australia",
				"Tasmainia","Victoria","Western Australia");
		statebox.getStyleClass().add("textField");
		HBox statehbox = new HBox(statebox);
		statehbox.setId("form");

		//Postcode
		TextField pcodefield = new TextField();
		pcodefield.setPromptText("Post Code");
		pcodefield.getStyleClass().add("textField");
		HBox pcodehbox = new HBox(pcodefield);
		pcodehbox.setId("form");

		//Error text box
		Text emptyerrortxt = new Text("All fields must be filled.");
		HBox emptyerrorbox = new HBox();

		//Success box
		Text empaddedtxt = new Text("Employee has been successfully added! :)");
		HBox empaddedhbox = new HBox();

		//Email taken error box
		Text takenerrortxt = new Text("Email already in use.");
		HBox takenerrorbox = new HBox();

		//Submit button
		Button subbtn = new Button("Add");
		subbtn.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent e){         

				//checking to make sure all fields are filled
				if(cont.checkValues(fnamefield, snamefield, streetaddfield, pcodefield,
						contactnofield, emailfield, cityfield)){
					if (cont.addEmployee(fnamefield.getText().concat(snamefield.getText()), 
							contactnofield.getText(), emailfield.getText(), 
							streetaddfield.getText(), cityfield.getText(), 
							statebox.getValue(), pcodefield.getText())) {
						System.out.println("Successully added " + emailfield.getText());
						if (!empaddedhbox.getChildren().contains(empaddedtxt)) {
							empaddedhbox.getChildren().add(empaddedtxt);
						}
						
					} else {
						System.out.println("failed to added " + emailfield.getText());
						if (empaddedhbox.getChildren().contains(empaddedtxt)) {
							empaddedhbox.getChildren().remove(empaddedtxt);
						}
					}
					//cont.empAddedMessage(empaddedhbox, empaddedtxt);
					cont.validateEntries(
							fnamefield, fnamehbox, 
							snamefield, snamehbox, 
							streetaddfield, streetaddhbox, 
							pcodefield, pcodehbox, 
							contactnofield, contactnohbox, 
							emailfield, emailhbox,
							cityfield, cityhbox,
							emptyerrortxt, emptyerrorbox,
							empaddedtxt, empaddedhbox,
							takenerrortxt, takenerrorbox);
				}else{
					//checking for empty
					cont.validateEntries(
							fnamefield, fnamehbox, 
							snamefield, snamehbox, 
							streetaddfield, streetaddhbox, 
							pcodefield, pcodehbox, 
							contactnofield, contactnohbox, 
							emailfield, emailhbox,
							cityfield, cityhbox,
							emptyerrortxt, emptyerrorbox,
							empaddedtxt, empaddedhbox,
							takenerrortxt, takenerrorbox);
				}
			}


		});
		subbtn.getStyleClass().add("bluebtn");

		// Add above elements to vertical box
		VBox addressInfo = new VBox(streetaddhbox, cityhbox, pcodehbox, statehbox, subbtn, empaddedhbox, emptyerrorbox,takenerrorbox);
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
