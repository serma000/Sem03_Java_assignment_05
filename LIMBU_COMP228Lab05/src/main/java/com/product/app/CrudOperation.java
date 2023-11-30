package com.product.app;
import javafx.scene.control.*;

import java.sql.*;

public class CrudOperation {
    UI userInterface = new UI();
    private Connection connection;

    //method to connect to database
    public void setConnection() {
        String databaseURL = "jdbc:mysql://localhost:3306/product";
        String username = "root";
        String password = "root";

        try {
            //declare connection object
            connection = DriverManager.getConnection(databaseURL, username, password);
            System.out.println("Successfully Connected to the database.");
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    //method to check name is empty or blank
    private Boolean checkNameValidation(String name){
        if(name.isEmpty() || name.isBlank()){
            userInterface.errorlabel.setText("Please enter employee name");
            return false;
        }
        return true;
    }

    //method to check department is empty or blank
    private Boolean checkCompanyValidation(String department){
        if(department.isEmpty() || department.isBlank()){
            userInterface.errorlabel.setText("Please enter employee department");
            return false;
        }
        return true;
    }
    public void insert_product(){
        try{

            String name = userInterface.name_text.getText();
            String company = userInterface.company_text.getText();
            String price = userInterface.price_text.getText();

            //validation - name and company should not be empty or blank
            if(checkNameValidation(name) && checkCompanyValidation(company)){
                //prepare insert statement to insert data into the database
                PreparedStatement insertStatement = connection.prepareStatement("insert into product (product_name, product_company,product_price) values(?,?,?)");
                insertStatement.setString(1, name);
                insertStatement.setString(2, company);
                insertStatement.setString(2, price);

                insertStatement.executeUpdate();
                System.out.println("Record inserted");

            }
        }catch (SQLException exception){
            exception.printStackTrace();
        }
    }
}
