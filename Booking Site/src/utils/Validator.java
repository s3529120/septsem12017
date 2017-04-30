package utils;

import java.util.HashMap;
import java.util.Map;

import Controller.AccountController;
import Controller.EmployeeController;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class Validator {
	
	/**
	 * Set error text's on appropriate hboxes if there are any errors
	 * @param errorMap A map containing the error names, returned from validateUserEntries, and validateEmpEntries
	 * @param textMap The Text messages to append to the HBoxes for a given error, such as hasEmpty, fnameError, etc
	 * @param hboxMap The HBoxes passed in to append/remove the error messages
	 * @return True if no errors
	 */
	public boolean setFormIds(Map<String,Boolean> errorMap, Map<String,Text> textMap, 
			Map<String,HBox> hboxMap){
		
		if (errorMap.get("hasEmpty")) {
			if (! hboxMap.get("emptyerrorbox").getChildren().contains(textMap.get("emptyerrortxt")) ) {
				hboxMap.get("emptyerrorbox").getChildren().add(textMap.get("emptyerrortxt"));
			}
		} else {
			//empaddedhbox.getChildren().add(empaddedtxt);
			if ( hboxMap.get("emptyerrorbox").getChildren().contains(textMap.get("emptyerrortxt")) ) {
				hboxMap.get("emptyerrorbox").getChildren().remove(textMap.get("emptyerrortxt"));
		}

		// checking if the employee email has been taken
		if (econt.checkEmployee(emailTrim)) {
			if (!takenerrorbox.getChildren().contains(takenerrortxt)) {
				takenerrorbox.getChildren().add(takenerrortxt);
			}
		} else {
			if (takenerrorbox.getChildren().contains(takenerrortxt)) {
				takenerrorbox.getChildren().remove(takenerrortxt);
			}
		}

		//Set error text for incorrect first name
		if(fnameerror){
			if(!fnameerrorbox.getChildren().contains(fnameerrortxt)){
				fnameerrorbox.getChildren().add(fnameerrortxt);
			}
		}else{
			if (fnameerrorbox.getChildren().contains(fnameerrortxt)) {
				fnameerrorbox.getChildren().remove(fnameerrortxt);
			}
		}

		//Set error text for incorrect last name
		if(snameerror){
			if(!snameerrorbox.getChildren().contains(snameerrortxt)){
				snameerrorbox.getChildren().add(snameerrortxt);
			}
		}else{
			if (snameerrorbox.getChildren().contains(snameerrortxt)) {
				snameerrorbox.getChildren().remove(snameerrortxt);
			}
		}

		//check if number is wrong
		if(numerror){
			if(!phoneerrorbox.getChildren().contains(phoneerrortxt)){
				phoneerrorbox.getChildren().add(phoneerrortxt);
			}
		}else{
			if(phoneerrorbox.getChildren().contains(phoneerrortxt)){
				phoneerrorbox.getChildren().remove(phoneerrortxt);
			}
		}

		//check if street address is wrong
		if(adderror){
			if(!streeterrorbox.getChildren().contains(streeterrortxt)){
				streeterrorbox.getChildren().add(streeterrortxt);
			}
		}else{
			if(streeterrorbox.getChildren().contains(streeterrortxt)){
				streeterrorbox.getChildren().remove(streeterrortxt);
			}
		}
		
		// Check city error
		if(cityerror){
			if(!cityerrorbox.getChildren().contains(cityerrortxt)){
				cityerrorbox.getChildren().add(cityerrortxt);
			}
		} else {
			if(cityerrorbox.getChildren().contains(cityerrortxt)){
				cityerrorbox.getChildren().remove(cityerrortxt);
			}
		}

		//check if mail is wrong
		if(emailerror){
			if(!emailerrorbox.getChildren().contains(emailerrortxt)){
				emailerrorbox.getChildren().add(emailerrortxt);
			}
		}else{
			if(emailerrorbox.getChildren().contains(emailerrortxt)){
				emailerrorbox.getChildren().remove(emailerrortxt);
			}
		}
		
		if(pcodeerror){
			if(!postcerrorbox.getChildren().contains(postcerrortxt)){
				postcerrorbox.getChildren().add(postcerrortxt);
			}
		} else {
			if(postcerrorbox.getChildren().contains(postcerrortxt)){
				postcerrorbox.getChildren().remove(postcerrortxt);
			}
		}
		
		if(!(numerror || fnameerror || 
				snameerror || adderror || pcodeerror ||
				emailerror || cityerror)){
			return true;
		}
		
		return false;
	}
	
	/**Validates the user registration entries, passed from the AddEmployeeView
	 * @param fname
	 * @param sname
	 * @param address
	 * @param pcode
	 * @param contactNo
	 * @param email
	 * @param city
	 * @return True if no no fields are empty
	 */
	public Boolean checkEmpValues(TextField fname, TextField sname, TextField address, TextField pcode,
			TextField contactNo, TextField email, TextField city) {

		if (fname.getText().isEmpty() || sname.getText().isEmpty() || address.getText().isEmpty()
				|| pcode.getText().isEmpty() || contactNo.getText().isEmpty()
				|| email.getText().isEmpty()) {
			return false;
		} else {
			return true;
		}

	}
	/**Validates the user registration entries, passed from the AddEmployeeView
	 * 
	 * @param fname
	 * @param fnamehbox
	 * @param sname
	 * @param snamehbox
	 * @param address
	 * @param addresshbox
	 * @param pcode
	 * @param pcodehbox
	 * @param contactno
	 * @param contactnohbox
	 * @param email
	 * @param emailhbox
	 * @param city
	 * @param cityhbox
	 * @param emptyerrortxt
	 * @param emptyerrorbox
	 * @param empaddedtxt
	 * @param empaddedhbox
	 * @param state
	 * @return True if no invalid patterns
	 */
	public boolean validateEmpValues(
			TextField fname, HBox fnamehbox, 
			TextField sname, HBox snamehbox, 
			TextField address, HBox addresshbox, 
			TextField pcode, HBox pcodehbox,
			TextField contactno, HBox contactnohbox, 
			TextField email, HBox emailhbox,
			TextField city, HBox cityhbox,
			Text emptyerrortxt, HBox emptyerrorbox, 
			Text empaddedtxt, HBox empaddedhbox,
			Text takenerrortxt, HBox takenerrorbox,
			Text fnameerrortxt, HBox fnameerrorbox,
			Text snameerrortxt, HBox snameerrorbox,
			Text emailerrortxt, HBox emailerrorbox,
			Text phoneerrortxt, HBox phoneerrorbox,
			Text streeterrortxt, HBox streeterrorbox,
			Text cityerrortxt, HBox cityerrorbox,
			Text postcerrortxt, HBox postcerrorbox,
			String state) {
		
		EmployeeController econt = new EmployeeController();
		// checking for empty
		boolean hasEmpty = false, numerror = false, fnameerror = false, 
				snameerror = false, adderror = false, pcodeerror = false,
				emailerror = false, cityerror = false;

		//Stringify all inputs
		String fnameTrim = fname.getText().trim(), snameTrim = sname.getText().trim(),
				addTrim = address.getText().trim(), pcodeTrim = pcode.getText().trim(),
				contactnoTrim = contactno.getText().trim(), emailTrim = email.getText().trim(),
				cityTrim = city.getText().trim();

		if (fnameTrim.equals("")) {
			fnamehbox.setId("incorrectForm");
			hasEmpty = true;
			fnameerror = true;
		} else if(!DataMatcher.fnameMatcher(fnameTrim)){
			fnamehbox.setId("incorrectForm");
			fnameerror = true;
		} else {
			fnameerror = false;
			fnamehbox.setId("form");
		}

		if (snameTrim.equals("")) {
			snamehbox.setId("incorrectForm");
			hasEmpty = true;
			snameerror = true;
		} else if(!DataMatcher.snameMatcher(snameTrim)){
			snamehbox.setId("incorrectForm");
			snameerror = true;
		} else {
			snameerror = false;
			snamehbox.setId("form");
		}

		if (addTrim.equals("")) {
			addresshbox.setId("incorrectForm");
			hasEmpty = true;
			adderror = true;
		} else if(!DataMatcher.addMatcher(addTrim)) {
			addresshbox.setId("incorrectForm");
			adderror = true;
		} else {
			adderror = false;
			address.setId("form");
		}

		if (pcodeTrim.equals("")) {
			pcodehbox.setId("incorrectForm");
			hasEmpty = true;
			pcodeerror = true;
		} else if(!DataMatcher.postcMatcher(pcodeTrim, state)){
			pcodehbox.setId("incorrectForm");
			pcodeerror = true;
		} else {
			pcodeerror = false;
			pcode.setId("form");
		}

		if (contactnoTrim.equals("")) {
			contactnohbox.setId("incorrectForm");
			hasEmpty = true;
			numerror = true;
		} else if(!DataMatcher.phoneMatcher(contactnoTrim)){
			contactnohbox.setId("incorrectForm");
			numerror = true;
		}else{
			numerror = false;
			contactno.setId("form");
		}

		if (emailTrim.equals("")) {
			emailhbox.setId("incorrectForm");
			hasEmpty = true;
			emailerror = true;
		} else if(!DataMatcher.emailMatcher(emailTrim)){
			emailhbox.setId("incorrectForm");
			emailerror = true;
		}else{
			emailerror = false;
			emailhbox.setId("form");
		}

		if (cityTrim.equals("")) {
			cityhbox.setId("incorrectForm");
			hasEmpty = true;
			cityerror = true;
		} else if(!DataMatcher.cityMatcher(cityTrim)){
			cityhbox.setId("incorrectForm");
			cityerror = true;
		} else {
			cityerror = false;
			cityhbox.setId("form");
		}
		if(!(numerror || fnameerror || 
				snameerror || adderror || pcodeerror ||
				emailerror || cityerror)){
			return true;
		} else {
			return false;
		}
	}
	
	public Boolean checkUserValues(TextField uname, TextField pname, PasswordField pword, PasswordField pwordcon,
			TextField address, TextField contactNo, TextField email){
		Boolean userExists;	
		
		AccountController acon = new AccountController();
		userExists=acon.checkUsername(uname.getText());

		if(uname.getText().isEmpty() || pname.getText().isEmpty() || pword.getText().isEmpty() || pwordcon.getText().isEmpty() ||
				address.getText().isEmpty() || contactNo.getText().isEmpty() || email.getText().isEmpty()){
			return false;
		}else if(userExists==true){
			return false;
		}else if (!pword.getText().equals(pwordcon.getText())) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean validateUserEntries(
			TextField uname, HBox unamehbox,
			TextField pname, HBox pnamehbox,
			PasswordField pword, HBox pwordhbox,
			PasswordField pwordcon,HBox pwordhboxcon,
			TextField address, HBox addhbox,
			TextField contactNo, HBox numhbox,
			TextField email, HBox mailhbox,
			Text emptyerrortxt, HBox emptyerrorbox,
			Text passerrortxt, HBox passerrorbox,
			Text unameerrortxt, HBox unameerrorbox,
			Text pnameerrortxt, HBox pnameerrorbox,
			Text emailerrortxt, HBox emailerrorbox,
			Text phoneerrortxt, HBox phoneerrorbox,
			Text streeterrortxt, HBox streeterrorbox
			){

		// Create bool vars to store state of entered data matching
		boolean hasEmpty = false, numerror = false, unameerror = false, 
				pnameerror = false, passerror = false, passconerror = false, 
				adderror = false, emailerror = false;

		// checking for empty as well as if the pattern is matched
		if(uname.getText().trim().equals("")) {
			unamehbox.setId("incorrectForm");
			hasEmpty = true;
			unameerror = true;
		} else if(!DataMatcher.unameMatcher(uname.getText().trim())) { 
			unamehbox.setId("incorrectForm");
			unameerror = true;
		} else {
			unameerror = false;
			unamehbox.setId("form");
		}
		
		if(pname.getText().trim().equals("")) {
			pnamehbox.setId("incorrectForm");
			hasEmpty = true;
			pnameerror = true;
		} else if(!DataMatcher.nameMatcher(pname.getText().trim())) {
			pnamehbox.setId("incorrectForm");
			pnameerror = true;
		} else {
			pnameerror = false;
			pnamehbox.setId("form");
		}
		
		if(pword.getText().trim().equals("")) {
			pwordhbox.setId("incorrectForm");
			hasEmpty = true;
			passerror = true;
			passconerror = true;
		} else if(!DataMatcher.passMatcher(pword.getText().trim())) {
			pwordhbox.setId("incorrectForm");
			passerror = true;
			passconerror = true;
		} else {
			passerror = false;
			pwordhbox.setId("form");
		}
		
		if(pwordcon.getText().trim().equals("")) {
			pwordhboxcon.setId("incorrectForm");
			hasEmpty = true;
			passconerror = true;
			passerror = true;
		} else if(pword.getText().trim().compareTo(pwordcon.getText().trim()) != 0){
			passerror = true;
			passconerror = true;
			pwordhboxcon.setId("incorrectForm");
			pwordhbox.setId("incorrectForm");
		} else if(!DataMatcher.passMatcher(pwordcon.getText().trim())) {
			pwordhboxcon.setId("incorrectForm");
			pwordhbox.setId("incorrectForm");
			passerror = true;
			passconerror = true;
		} else {
			passconerror = false;
			pwordhboxcon.setId("form");
		}
		if(contactNo.getText().trim().equals("")){
			numhbox.setId("incorrectForm");
			hasEmpty = true;
			numerror = true;
		} else if(!DataMatcher.phoneMatcher(contactNo.getText().trim())) {
			numhbox.setId("incorrectForm");
			numerror = true;
		} else {
			numerror = false;
			numhbox.setId("form");
		}
		
		if(address.getText().trim().equals("")){
			addhbox.setId("incorrectForm");
			hasEmpty = true;
			adderror = true;
		} else if(!DataMatcher.addMatcher(address.getText().trim())) {
			addhbox.setId("incorrectForm");
			adderror = true;
		} else {
			adderror = false;
			addhbox.setId("form");
		}
		
		if(email.getText().trim().equals("")){
			mailhbox.setId("incorrectForm");
			hasEmpty = true;
			emailerror = true;
		} else if(!DataMatcher.emailMatcher(email.getText().trim())) {
			mailhbox.setId("incorrectForm");
			emailerror = true;
		} else {
			emailerror = false;
			mailhbox.setId("form");
		}

		//checking if the password fields are what cause the reject and if it is add the "pass error text"
		if (!pword.getText().equals(pwordcon.getText())) {
			if (!passerrorbox.getChildren().contains(passerrortxt)) {
				passerrorbox.getChildren().add(passerrortxt);
			}
		} else {
			if (passerrorbox.getChildren().contains(passerrortxt)) {
				passerrorbox.getChildren().remove(passerrortxt);
			}
		}
		
		if (passerror) {
			if (!passerrorbox.getChildren().contains(passerrortxt)) {
				passerrorbox.getChildren().add(passerrortxt);
			}
		} else {
			if (passerrorbox.getChildren().contains(passerrortxt)) {
				passerrorbox.getChildren().remove(passerrortxt);
			}
		}
		
		// Check if the confirmation password matches the pattern
		if (passconerror) {
			if (!passerrorbox.getChildren().contains(passerrortxt)) {
				passerrorbox.getChildren().add(passerrortxt);
			}
		} else {
			if (passerrorbox.getChildren().contains(passerrortxt)) {
				passerrorbox.getChildren().remove(passerrortxt);
			}
		}

		//checking if any of the fields were empty and if they were add the "empty error text"
		if (hasEmpty) {
			if (!emptyerrorbox.getChildren().contains(emptyerrortxt)) {
				emptyerrorbox.getChildren().add(emptyerrortxt);
			}
		} else {
			if (emptyerrorbox.getChildren().contains(emptyerrortxt)) {
				emptyerrorbox.getChildren().remove(emptyerrortxt);
			}
		}

		//check if number is wrong
		if(numerror){
			if(!phoneerrorbox.getChildren().contains(phoneerrortxt)){
				phoneerrorbox.getChildren().add(phoneerrortxt);
			}
		}else{
			if(phoneerrorbox.getChildren().contains(phoneerrortxt)){
				phoneerrorbox.getChildren().remove(phoneerrortxt);
			}
		}

		//check if street address is wrong
		if(adderror){
			if(!streeterrorbox.getChildren().contains(streeterrortxt)){
				streeterrorbox.getChildren().add(streeterrortxt);
			}
		}else{
			if(streeterrorbox.getChildren().contains(streeterrortxt)){
				streeterrorbox.getChildren().remove(streeterrortxt);
			}
		}

		//check if mail is wrong
		if(emailerror){
			if(!emailerrorbox.getChildren().contains(emailerrortxt)){
				emailerrorbox.getChildren().add(emailerrortxt);
			}
		}else{
			if(emailerrorbox.getChildren().contains(emailerrortxt)){
				emailerrorbox.getChildren().remove(emailerrortxt);
			}
		}

		// Check if the the name is invalid
		if(pnameerror){
			if(!pnameerrorbox.getChildren().contains(pnameerrortxt)){
				pnameerrorbox.getChildren().add(pnameerrortxt);
			}
		}else{
			if(pnameerrorbox.getChildren().contains(pnameerrortxt)){
				pnameerrorbox.getChildren().remove(pnameerrortxt);
			}
		}

		//checking if the account already exists and if it does adding the "taken error text"
		AccountController acon = new AccountController();
		boolean accExists=acon.checkUsername(uname.getText());
		if (accExists) {
			if (!unameerrorbox.getChildren().contains(unameerrortxt)) {
				unameerrorbox.getChildren().add(unameerrortxt);
			}
		} else {
			if (unameerrorbox.getChildren().contains(unameerrortxt)){
				unameerrorbox.getChildren().remove(unameerrortxt);
			}
		}
		
		if(!(numerror || unameerror || 
				pnameerror || passerror || passconerror || 
				adderror || emailerror)){
			return true;
		}
		
		return false;
	}
	
}
