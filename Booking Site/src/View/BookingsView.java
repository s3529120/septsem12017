package View;

import Controller.BookingController;
import Model.BookingModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.ScrollPane;
import java.util.List;

public class BookingsView {

	final ScrollPane sp = new ScrollPane();
	private String currentdate;

	private BookingController cont;
	public Stage stage;

	public BookingsView(Stage stage) {
		this.stage = stage;
	}

	public Boolean setController(BookingController controller) {
		this.cont = controller;
		return true;
	}

	public void updateView() {

		// heading
		Button backbtn = new Button("Go Back");
		backbtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				stage.close();
			}
		});

		backbtn.getStyleClass().add("redbtn");

		Text h1 = new Text("View Bookings");
		h1.setId("bookingsh1");
		HBox bookingstitle = new HBox(h1);
		bookingstitle.setId("bookingstitle");
		
		HBox heading = new HBox(bookingstitle, backbtn);
		//VBox headingcontainer = new VBox(heading);
		heading.setId("bookingsHeader");

		// Bookings

		List<BookingModel> bookings = cont.getBookings();

		VBox bookingsList = new VBox();

		if (bookings.isEmpty()) {
			System.out.println("it's empty");
		}

		bookings.forEach(booking -> {
			String newdate = booking.getDate().toString();
			if (currentdate == null || !currentdate.equals(newdate)) {
				currentdate = newdate;
				Text date = new Text(currentdate);
				date.setId("bookingDate");
				HBox datebox = new HBox(date);
				datebox.setId("datebox");
				bookingsList.getChildren().add(datebox);
			}

			Text startTime = new Text("Start: " + booking.getStartTime().toString());
			Text finishTime = new Text("End: " + booking.getFinishTime().toString());
			Text employee = new Text("Employee: " + cont.getNameFromEmail(booking.getEmployee()));

			VBox who = new VBox(employee);
			VBox bookingType = new VBox();

			if (booking.getUser() != "Unfilled") {
				Text customer = new Text("Customer: " + booking.getUser());
				Text type = new Text("Type: " + booking.getType());
				who.getChildren().add(customer);
				bookingType.getChildren().add(type);
			}

			VBox when = new VBox(startTime, finishTime);

			who.getStyleClass().add("bookingcol");
			when.getStyleClass().add("bookingcol");
			bookingType.getStyleClass().add("bookingcol");
			
			
			HBox bookingBox = new HBox(who, when, bookingType);
			bookingBox.setId("bookingBox");

			bookingsList.getChildren().add(bookingBox);

		});
		HBox bookingscontainer = new HBox(bookingsList);
		bookingscontainer.setId("bookingscontainer");

		// Layout

		VBox main = new VBox(heading, bookingscontainer);
		main.setId("bookingsMain");

		sp.setVmax(440);
		sp.setPrefSize(115, 150);
		sp.setContent(main);
		Scene scene = new Scene(sp, 750, 500);
		scene.getStylesheets().add(getClass().getResource("css/styles.css").toExternalForm());
		stage.setScene(scene);
		stage.show();

	}

}
