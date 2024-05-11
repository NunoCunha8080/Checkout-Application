package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {



    //Tabs
    @FXML private TabPane tabPane;
    public Tab ShoppingCartTab;

    //Configure List
    //@FXML private ListView lstCartQueue;
    public ListView lstCartQueue;
    public ListView lstCheckOutList;
    private ArrayList<CartQueue> cartQueueP;

    //Configure table
    @FXML private TableView<Products> tableView;
    @FXML private TableColumn<Products, String> nameColumn;
    @FXML private TableColumn<Products, Double> priceColumn;
    @FXML private TableColumn<Products, Integer> codeColumn;

    //Configure buttons
    @FXML private Button btnGo;
    @FXML private Button btnAdd;
    @FXML private Button btnRemove;
    @FXML private Button btnConfirm;

    //Labels
    public Label lblPriceTotal;

    //Global Variable
    private double priceTotal;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Hide Confirm Button
        btnConfirm.setVisible(false);

        //Final product list not visible on start
        lstCheckOutList.setVisible(false);

        //Load Shopping Cart List
        cartQueueP = new ArrayList<>();

        //Buttons
        //Remove Item Button
        btnRemove.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                removeProduct();
            }
        });

        //Go to Purchase Screen Button
        btnGo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                goToCheckout();
                btnConfirm.setVisible(true);
            }
        });

        //Add Product Button
        btnAdd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                addProd();
            }
        });

        btnConfirm.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                confirmPurchase();

                //Return to Menu
                Parent root;

                try{
                    root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle("supermarket Program");
                    stage.setScene(new Scene(root));
                    stage.show();

                    //Hide the Menu Window
                    ((Node)(event.getSource())).getScene().getWindow().hide();
                }
                catch (IOException e){e.printStackTrace();}
            }
        });

        //Sets up the columns in the table
        nameColumn.setCellValueFactory(new PropertyValueFactory<Products, String>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Products, Double>("price"));
        codeColumn.setCellValueFactory(new PropertyValueFactory<Products, Integer>("code"));

        //Load Data From Product List Into Table
        tableView.setItems(getProducts());
    }

    //Remove Products From The Cart
    private void removeProduct() {
            //Get Product Selected From The Table
            Products productSelected = tableView.getSelectionModel().getSelectedItem();
            //Get Queued Product On The CartQueue
            CartQueue cartQueueProdSelected = (CartQueue) lstCartQueue.getSelectionModel().getSelectedItem();

            //Local Variable
            int idx = 0;

            while(idx < cartQueueP.size())
            {
                //Get Product Index Position on CartQueue List
                CartQueue cartQueueProd = cartQueueP.get(idx);

                //Check If Product Code on CartQueue Matches The Product Code on Product List
                if(cartQueueProd.getCode() == productSelected.getCode() || cartQueueProd.getCode()==cartQueueProdSelected.getCode())
                {
                    //Check if Product amount is equal to 1
                    if(cartQueueProd.getAmount() == 1 || cartQueueProdSelected.getAmount()==1)
                    {
                        //Remove Entire Product from CartQueueList
                        cartQueueP.remove(idx);
                    }
                    else
                    {
                        //Remove The "Copies" of The Product Of The CartQueue List
                        cartQueueProd.setAmount(cartQueueProd.getAmount()-1);
                    }
                }
                idx++;
            }
            //Update Total Value on Label
            updateProducts(-productSelected.getPrice());
    }

    //Add Product to Cart
    private void addProd() {

            //Local Variables
            boolean found=false;
            int idx = 0;

            //Get Product Selected From The Table
            Products productsSelected = tableView.getSelectionModel().getSelectedItem();

            //While Product Is Selected and Number of Queued Products is Lesser Grater Than Index
            while (!found && idx < cartQueueP.size())
            {
                //Get Queued Product Index
                CartQueue cartQueueProduct = cartQueueP.get(idx);

                //If Queued Product Code is equal to Product Selected on ProductList Code
                if(cartQueueProduct.getCode() == productsSelected.getCode())
                {
                    //Product Was Found
                    found = true;
                    //Product Amount is Increased
                    cartQueueProduct.setAmount(cartQueueProduct.getAmount()+1);
                }
                idx++;
            }
            //Check If Product Selected ID Was Found
            if (!found)
            {
                //AddProduct Selected to CartQueue List
                cartQueueP.add(new CartQueue(productsSelected));
            }
             //Update Total Value on Label
            updateProducts(productsSelected.getPrice());
    }

    //Function to Update the Total Price of Products
    private void updateProducts(double priceDifferance) {
        //Get Items on ArrayList CartQueue and Insert on lstCartQueue
        lstCartQueue.getItems().setAll(cartQueueP);

        //Get Items on CarQueue and Insert on lstCheckOutList
        lstCheckOutList.getItems().setAll(cartQueueP);

        priceTotal = priceTotal + priceDifferance;
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        lblPriceTotal.setText("TOTAL: "+decimalFormat.format(priceTotal)+" GBP");
    }

    //Tabs
    //Purchase Confirmation Tab
    private void goToCheckout() {
        //Disable Shopping Cart Tab. Cart tab, Add and Remove Product Buttons
        ShoppingCartTab.setDisable(true);
        btnRemove.setVisible(false);
        btnAdd.setVisible(false);

        //Enable final list
        lstCheckOutList.setVisible(true);

        //Renames Every New Tab
        Tab tab = new Tab("Checkout ");
        //Adds a New Tab Into the tabPane
        tabPane.getTabs().add(tab);
        //Puts the lstCheckOutList Items Into the New Tab Content
        tab.setContent(lstCheckOutList);
    }

    private void confirmPurchase()
    {
        //Clear ListView Items
        lstCheckOutList.getItems().removeAll();
        lstCartQueue.getItems().removeAll();
    }

    //This Method Creates a Product List and Returns a list of Product Objects
    public ObservableList<Products> getProducts()
    {
        ObservableList<Products> products = FXCollections.observableArrayList();
        products.add(new Products("Maple Syrup", 0.54,1000));
        products.add(new Products("Pizza",1.55,2000));
        products.add(new Products("Waffles",2.86,3000));
        products.add(new Products("Pop Corn",0.92,4000));
        products.add(new Products("Canned Soup",1.00,5000));
        products.add(new Products("Orange Juice",0.39,6000));
        products.add(new Products("Chicken Fillets",1.45,7000));
        products.add(new Products("Mac'n'Cheese Noodles ",0.90,2000));
        products.add(new Products("Lamb Mince",1.38,1000));
        products.add(new Products("Pork Mince",1.57,1100));

        return products;
    }
}
