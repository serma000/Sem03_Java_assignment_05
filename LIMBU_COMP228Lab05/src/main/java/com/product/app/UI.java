package com.product.app;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class UI{
    //code for CRUD operation
    CrudOperation crudOperation;
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

    //buttons
    Button insert_button = new Button("Insert Product");
    Button update_button = new Button("Update Product");
    Button delete_button = new Button("Delete Product");
    Button reset_button = new Button("Reset");
    Button display_button = new Button("Display Product");

    //table view declaration
    //private TableView<Product> productTableView;
    public void initialize_UI(Stage stage){

        crudOperation = new CrudOperation();
        crudOperation.setConnection();

        //crudOperation.setConnection();

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

        //event handlers for buttons
       insert_button.setOnAction(actionEvent -> {
           crudOperation.insert_product();
        });



        //initialize table view
        //productTableView = new TableView<>();
        //create scene
        Scene scene = new Scene(layout(),500,600);
        //set scene into stage
        stage.setScene(scene);
        stage.setTitle("  Product  Management  Application  ");
        stage.show();
    }
    private VBox layout(){

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
        root = new VBox(10,borderPane,gridPane,hBox,display_borderPane,borderPane1);

        return root;
    }

}
