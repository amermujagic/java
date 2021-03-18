package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class SampleController {
	@FXML
	private Button btnShowAllICars;
	@FXML
	private Label lblCars;
	@FXML
	private Label lbltest1;
	
	@FXML
	private Label lblSubmited;
	@FXML
	private TextField item_quantity;
	@FXML
	private RadioButton rbFiatStilo;
	@FXML
	private RadioButton rbSkodaOctavia;
	@FXML
	private RadioButton rbGolf7;
	@FXML
	private TextField txtNewCarMake;
	@FXML
	private TextField numberOfDays;
	
	@FXML
	private TextField txtNewCarModel;
	@FXML
	private TextField txtNewCarPricePerDay;
	@FXML
	private Label lblAddNewItemCompleted;

	String stored_value;

// CARS

	public void showAllCars(ActionEvent event) {
		try {
			// 1. Connection
			Connection myConn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/rentacar_db?autoReconnect=true&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"root", "root");
			// 2. Statement
			Statement myStatement = myConn.createStatement();
			// 3. Execute query
			ResultSet myResult = myStatement.executeQuery("select * from car");
			// 4. Process result set
			String tempResult = "";
			String result;
			int count = 1;
			while (myResult.next()) {
				result = count + ".) " + " Car Make: " + myResult.getString("car_make") + "             Car Model: "
						+ myResult.getString("car_model") + "\n";
				tempResult += result;
				lblCars.setText(tempResult);
				count++;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	

// RENT A CAR

	public void importQuantity(ActionEvent event) {
		double price = Double.parseDouble(numberOfDays.getText());
		int item_id;
		if (rbFiatStilo.isSelected()) {
			item_id = 1;
		} else if (rbSkodaOctavia.isSelected()) {
			item_id = 2;
		} else if (rbGolf7.isSelected()) {
			item_id = 3;
		} else {
			lblSubmited.setText("You have not selected item");
			return;
		}
		try {
			// 1. Connection
			Connection myConn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/rentacar_db?autoReconnect=true&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"root", "root");
			// 2. Statement
			Statement myStatement = myConn.createStatement();
			// 3. Execute query
			ResultSet myRes = myStatement.executeQuery("select * from car where car_id="+item_id);
			// 4. Execute query
			double rezultat=1;
			while (myRes.next()) {
				rezultat=myRes.getDouble("price_per_day");
				double brojDana = Double.parseDouble(numberOfDays.getText());
				double ukupanRezultat=rezultat*brojDana;
				lblSubmited.setText("Total amount for " + brojDana + " day(s): "+ukupanRezultat);
				rezultat=1;
				
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("nah1");
			
		}
	}

	// ADD A NEW CAR

	public void addNewItem(ActionEvent event) {
		String newCarMake = txtNewCarMake.getText();
		String newCarModel = txtNewCarModel.getText();
		double newCarPricePerDay = Double.parseDouble(txtNewCarPricePerDay.getText());
		try {
			// 1. Connection
			Connection myConn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/rentacar_db?autoReconnect=true&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"root", "root");
			// 2. Statement
			Statement myStatement = myConn.createStatement();
			// 3. Execute query
			String addNewItem = "INSERT INTO car (car_make, car_model, price_per_day) VALUES (\"" + newCarMake +  "\","
					+ "\"" + newCarModel + "\"," + newCarPricePerDay + ");";
			myStatement.executeUpdate(addNewItem);
			lblAddNewItemCompleted.setText("You have succesfully added new item!");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("nah");
		}
	}

}
