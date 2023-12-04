package com.product.app;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.*;
import java.util.Stack;

public class App extends Application {
    //label declaration
    Label title = new Label();
    Label id = new Label();
    Label name = new Label();
    Label company = new Label();
    Label price = new Label();

    Label errorlabel = new Label();

    //Text field
    TextField id_text = new TextField();
    TextField name_text = new TextField();
    TextField company_text = new TextField();
    TextField price_text = new TextField();

    //table view declaration
    public TableView<Product> productTableView;

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
    private Boolean checkNameValidation(String name) {
        if (name.isEmpty() || name.isBlank()) {
            errorlabel.setText("Please enter product name!");
            return false;
        }
        return true;
    }

    //method to check company is empty or blank
    private Boolean checkCompanyValidation(String company) {
        if (company.isEmpty() || company.isBlank()) {
            errorlabel.setText("Please enter the company name! ");
            return false;
        }
        return true;
    }
    //method to check price is empty or blank
    private Boolean checkPriceValidation(String price) {
        if (price.isEmpty() || price.isBlank()) {
            errorlabel.setText("Please enter the amount!");
            return false;
        }
        return true;
    }
    //method to check id is empty or blank
    private Boolean checkIdValidation(String id){
        if(id.isEmpty() || id.isBlank()){
            errorlabel.setText("Please enter product Id!");
        }
        return true;
    }
    //method to check the product is available for given id or not
    private Boolean checkRecordExist(int id){
        try{
            PreparedStatement selectWhereStatement = connection.prepareStatement("select * from product where product_id=?");
            selectWhereStatement.setInt(1,id);
            ResultSet resultSet = selectWhereStatement.executeQuery();

            if(resultSet.next()){
                return true;
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        errorlabel.setText("The product with the given id " + id + " is not available.");
        return false;
    }

    //method to load product data into table view
    private void loadProductInformation(){
        //declare observable list to store data from database
        ObservableList<Product> productObservableList = FXCollections.observableArrayList();
        try {
            Statement selectStatement = connection.createStatement();
            //declare result set to store data which we get from database
            ResultSet resultSet = selectStatement.executeQuery("select * from product");
            //loop to store data from result set to observable list
            while (resultSet.next()){
                int id = resultSet.getInt("product_id");
                String name = resultSet.getString("product_name");
                String company = resultSet.getString("product_company");
                String price = resultSet.getString("product_price");

                //add data into the list in form of product object
                productObservableList.add(new Product(id,name,company,price));

            }
            //bind the list into table view
            productTableView.setItems(productObservableList);
            System.out.println("Product displayed!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } ;
    }

    public void insert_product() {
        try {

            String name = name_text.getText();
            String company = company_text.getText();
            String price = price_text.getText();

            //validation - name and company should not be empty or blank
            if (checkNameValidation(name) && checkCompanyValidation(company) && checkPriceValidation(price)) {
                //prepare insert statement to insert data into the database
                PreparedStatement insertStatement = connection.prepareStatement("insert into product (product_name, product_company,product_price) values(?,?,?)");
                insertStatement.setString(1, name);
                insertStatement.setString(2, company);
                insertStatement.setString(3, price);
                insertStatement.executeUpdate();
                System.out.println("Product inserted");
                clearAllData();
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
    public void update_product() {
        try {
            String name = name_text.getText();
            String company = company_text.getText();
            String price = price_text.getText();

            if (checkNameValidation(name) && checkCompanyValidation(company) && checkPriceValidation(price)) {
                int id = Integer.parseInt(id_text.getText());

                //product should exist with given id
                if(checkRecordExist(id)){
                    //prepare insert statement to insert data into the database
                    PreparedStatement updateStatement = connection.prepareStatement("update product set product_name=?,product_company=?, product_price= ? where product_id=?");
                    updateStatement.setString(1, name);
                    updateStatement.setString(2, company);
                    updateStatement.setString(3, price);
                    updateStatement.setInt(4,id);
                    updateStatement.executeUpdate();
                    System.out.println("Product updated!");
                    display_product();
                    clearAllData();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    //method to delete product information from database
    private void delete_product(){

        try{
            if(checkIdValidation(id_text.getText())){
                int id = Integer.parseInt(id_text.getText());
                if(checkRecordExist(id)){
                    PreparedStatement deleteStatement = connection.prepareStatement("delete from product where product_id =?");
                    deleteStatement.setInt(1,id);
                    deleteStatement.executeUpdate();
                    System.out.println("Product deleted!");
                    clearAllData();
                    display_product();
                }
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
    //method to display the product information
    private void display_product(){
        loadProductInformation();

    }
    private void reset(){
        clearAllData();
    }
    //method to clear the text fields
    private void clearAllData(){
        id_text.clear();
        name_text.clear();
        company_text.clear();
        price_text.clear();
        errorlabel.setText("");
    }


        @Override
    public void start(Stage stage) throws Exception {
        //calling this method to connect to product database
        setConnection();

        //initialize table view
        productTableView = new TableView<>();


            //table view structure - to store data in terms of row and column
        //columns
        TableColumn<Product, Integer> idColumn = new TableColumn<>("Id");
        TableColumn<Product, String> nameColumn = new TableColumn<>("Name");
        TableColumn<Product, String> companyColumn = new TableColumn<>("Company");
        TableColumn<Product, String> priceColumn = new TableColumn<>("Price");

        //rows
        idColumn.setCellValueFactory(new PropertyValueFactory<>("productId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("productName"));
        companyColumn.setCellValueFactory(new PropertyValueFactory<>("productCompany"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("productPrice"));

        //add columns in table view
        productTableView.getColumns().addAll(idColumn,nameColumn,companyColumn,priceColumn);

        //initialization of labels
        title = new Label("Product   Management  Application");
        id = new Label("ID: ");
        name = new Label("Name: ");
        company = new Label("Company Name: ");
        price = new Label("Price: ");

        //set font
        Font font = Font.font("Arial", FontWeight.BOLD,15);
        title.setFont(font);
        //text field declaration
        id_text= new TextField();
        id_text.setPromptText("Enter ID");
        name_text = new TextField();
        name_text.setPromptText("Enter name");
        company_text=new TextField();
        company_text.setPromptText("Enter company name");
        price_text = new TextField();
        price_text.setPromptText("Enter price");

        //buttons
        Button insert_button = new Button("Insert Product");
        insert_button.setOnAction(actionEvent -> insert_product());
        Button update_button = new Button("Update Product");
        update_button.setOnAction(actionEvent -> update_product());
        Button delete_button = new Button("Delete Product");
        delete_button.setOnAction(actionEvent -> delete_product());
        Button reset_button = new Button("Reset");
        reset_button.setOnAction(actionEvent -> reset());
        Button display_button = new Button("Display Product");
        display_button.setOnAction(actionEvent -> display_product());

        //layout manager
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(title);

        // Grid pane
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10,10,10,10));
        gridPane.add(id,0,0);
        gridPane.add(id_text,1,0);
        gridPane.add(name,0,1);
        gridPane.add(name_text,1,1);
        gridPane.add(company,0,2);
        gridPane.add(company_text,1,2);
        gridPane.add(price,0,3);
        gridPane.add(price_text,1,3);

        //HBox
        HBox hBox = new HBox(20,insert_button,update_button,delete_button,display_button);
        hBox.setPadding(new Insets(20));

        //BorderPane
        BorderPane display_borderPane = new BorderPane();
        display_borderPane.setCenter(reset_button);

        //VBox
        VBox vBox = new VBox(errorlabel);
        //BorderPane
        BorderPane borderPane1 = new BorderPane();
        borderPane1.setCenter(vBox);

        //root of layout manager
        VBox root;
        root = new VBox(10,borderPane,gridPane,hBox,display_borderPane,borderPane1,productTableView);

        //add
        //create scene
        Scene scene = new Scene(root,500,600);
        //set scene into stage
        stage.setScene(scene);
        stage.setTitle("  Product  Management  Application  ");
        stage.show();

        //call the load productInformation to display the data in the table view.
    }
    public static void main (String[]args)
    {
        launch(args);
    }
}
