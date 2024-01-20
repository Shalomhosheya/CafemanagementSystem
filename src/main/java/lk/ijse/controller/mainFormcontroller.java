package lk.ijse.controller;

import com.fasterxml.jackson.databind.node.DoubleNode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lk.ijse.db.DbConnection;
import lk.ijse.dto.BillData;
import lk.ijse.dto.customersData;
import lk.ijse.dto.data;
import lk.ijse.dto.productData;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import java.util.List;

public class mainFormcontroller implements Initializable {

    @FXML
    private Button customer_btn;
    @FXML
    private TableColumn< productData,String> customers_col_cashier;

    @FXML
    private TableColumn<productData,String> customers_col_customerID;

    @FXML
    private TableColumn<productData,String> customers_col_date;

    @FXML
    private TableColumn<productData,String> customers_col_total;

    @FXML
    private AnchorPane customers_form;

    @FXML
    private TableView<customersData> customers_tableView;


    @FXML
    private Button dashboard_Btn;

    @FXML
    private Button employee_btn;

    @FXML
    private Button inventory_UpdateBtn;

    @FXML
    private Button inventory_addBtn;

    @FXML
    private Button inventory_btn;

    @FXML
    private Button inventory_clearBtn;

    @FXML
    private TableColumn<?, ?> inventory_co_date;

    @FXML
    private TableColumn<?, ?> inventory_co_diproduct;

    @FXML
    private TableColumn<?, ?> inventory_co_price;

    @FXML
    private TableColumn<?, ?> inventory_co_productname;

    @FXML
    private TableColumn<?, ?> inventory_co_status;

    @FXML
    private TableColumn<?, ?> inventory_co_stock;

    @FXML
    private TableColumn<?, ?> inventory_co_type;

    @FXML
    private Button inventory_deleteBtn;

    @FXML
    private AnchorPane inventory_form;

    @FXML
    private ImageView inventory_imageView;

    @FXML
    private Button inventory_importBtn;

    @FXML
    private TextField inventory_price;

    @FXML
    private TextField inventory_productId;

    @FXML
    private TextField inventory_productName;

    @FXML
    private ComboBox<?> inventory_status;

    @FXML
    private TextField inventory_stock;

    @FXML
    private TableView<productData> inventory_tableview;

    @FXML
    private ComboBox<?> inventory_type;

    @FXML
    private Button logout_btn;

    @FXML
    private AnchorPane main_form;

    @FXML
    private TextField menu_amount;

    @FXML
    private Button menu_btn;

    @FXML
    private Label menu_change;

    @FXML
    private TableColumn<productData, String> menu_co_price;

    @FXML
    private TableColumn<productData, String> menu_co_productName;

    @FXML
    private TableColumn<productData, String> menu_co_quantity;

    @FXML
    private AnchorPane menu_form;

    @FXML
    private GridPane menu_gridPane;

    @FXML
    private Button menu_payBtn;

    @FXML
    private Button menu_receiptBtn;

    @FXML
    private Button menu_removeBtn;

    @FXML
    private ScrollPane menu_scrollPane;

    @FXML
    private TableView<productData> menu_tableView;

    @FXML
    private Label menu_total;

    @FXML
    private BarChart<customersData, String> dashboard_CustomerChart;

    @FXML
    private Label dashboard_NC;

    @FXML
    private Label dashboard_NCP;

    @FXML
    private Label dashboard_TI;

    @FXML
    private Label dashboard_Total1;

    @FXML
    private AnchorPane dashboard_form;

    @FXML
    private AreaChart<customersData, String> dashboard_incomechart;


    @FXML
    private Label username;
    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;
    private Alert alert;
    private Image image;

    private int getid;

    private ObservableList<customersData> customersListData;

    private ObservableList<productData> cardListData = FXCollections.observableArrayList();


    private  ObservableList<productData> menuOrderListData;


    public void dashboardDisplayNC() {
        String sql = "SELECT COUNT(id) FROM receipt";
        this.connect = DbConnection.connectDB();

        try {
            int nc = 0;
            this.prepare = this.connect.prepareStatement(sql);
            this.result = this.prepare.executeQuery();
            if (this.result.next()) {
                nc = this.result.getInt("COUNT(id)");
            }

            this.dashboard_NC.setText(String.valueOf(nc));
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

    public void dashboardDisplayTI() {
        Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        String sql = "SELECT SUM(total) FROM receipt WHERE date = '" + sqlDate + "'";
        this.connect = DbConnection.connectDB();

        try {
            double ti = 0.0D;
            this.prepare = this.connect.prepareStatement(sql);
            this.result = this.prepare.executeQuery();
            if (this.result.next()) {
                ti = this.result.getDouble("SUM(total)");
            }

            this.dashboard_TI.setText("Rs " + ti);
        } catch (Exception var6) {
            var6.printStackTrace();
        }

    }

    public void dashboardTotalI() {
        String sql = "SELECT SUM(total) FROM receipt";
        this.connect = DbConnection.connectDB();

        try {
            float ti = 0.0F;
            this.prepare = this.connect.prepareStatement(sql);
            this.result = this.prepare.executeQuery();
            if (this.result.next()) {
                ti = this.result.getFloat("SUM(total)");
            }

            this.dashboard_Total1.setText("Rs " + ti);
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

    public void dashboardNSP() {
        String sql = "SELECT COUNT(quantity) FROM customer";
        this.connect = DbConnection.connectDB();

        try {
            int q = 0;
            this.prepare = this.connect.prepareStatement(sql);
            this.result = this.prepare.executeQuery();
            if (this.result.next()) {
                q = this.result.getInt("COUNT(quantity)");
            }

            this.dashboard_NCP.setText(String.valueOf(q));
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

   public void dashboardIncomeChart() {
        dashboard_incomechart.getData().clear();
        String sql = "SELECT date, SUM(total) FROM receipt GROUP BY date ORDER BY TIMESTAMP(date)";
        connect = DbConnection.connectDB();
       XYChart.Series chart = new XYChart.Series();

        try {
            prepare = this.connect.prepareStatement(sql);
            result = this.prepare.executeQuery();

            while(result.next()) {
                chart.getData().add(new XYChart.Data<>(result.getString(1), this.result.getFloat(2)));

             }
            dashboard_incomechart.getData().add(chart);
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    public void dashboardCustomerChart() {
        this.dashboard_CustomerChart.getData().clear();
        String sql = "SELECT date, COUNT(id) FROM receipt GROUP BY date ORDER BY TIMESTAMP(date)";
        this.connect = DbConnection.connectDB();
        XYChart.Series chart = new XYChart.Series();

        try {
            this.prepare = this.connect.prepareStatement(sql);
            this.result = this.prepare.executeQuery();

            while(this.result.next()) {
                chart.getData().add(new XYChart.Data<>(this.result.getString(1), this.result.getInt(2)));
            }

            this.dashboard_CustomerChart.getData().add(chart);
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }


    public ObservableList<productData> menuGetData() {

        String sql = "SELECT * FROM product";

        ObservableList<productData> listData = FXCollections.observableArrayList();
        connect = DbConnection.connectDB();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            productData prod;

            while (result.next()) {
                prod = new productData(result.getInt("id"),
                        result.getString("pro_id"),
                        result.getString("pro_name"),
                        result.getString("type"),
                        result.getInt("stock"),
                        result.getDouble("price"),
                        result.getString("image"),
                        result.getDate("date"));

                listData .add(prod);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listData ;
    }
    public void menuDisplayCard() {

        cardListData.clear();
        cardListData.addAll(menuGetData());

        int row = 0;
        int column = 0;

        menu_gridPane.getChildren().clear();
        menu_gridPane.getRowConstraints().clear();
        menu_gridPane.getColumnConstraints().clear();

        for (int q = 0; q < cardListData.size(); q++) {

            try {
                FXMLLoader load = new FXMLLoader();
                load.setLocation(getClass().getResource("/view/cardProduct.fxml"));
                AnchorPane pane = load.load();
                cardProductController cardC = load.getController();
                cardC.setData(cardListData.get(q));

                if (column == 3) {
                    column = 0;
                    row += 1;
                }

                menu_gridPane.add(pane, column++, row);


          GridPane.setMargin(pane, new Insets(10));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void switchForm(ActionEvent event) {

        if (event.getSource() == dashboard_Btn) {
            dashboard_form.setVisible(true);
            inventory_form.setVisible(false);
            menu_form.setVisible(false);
            customers_form.setVisible(false);

            dashboardDisplayNC();
            dashboardDisplayTI();
            dashboardTotalI();
            dashboardNSP();
            dashboardIncomeChart();
            dashboardCustomerChart();

        } else if (event.getSource() == inventory_btn) {
            dashboard_form.setVisible(false);
            inventory_form.setVisible(true);
            menu_form.setVisible(false);
            customers_form.setVisible(false);

            inventoryType();
            inventoryStatusList();
            inventoryShowData();
        } else if (event.getSource() == menu_btn) {
            dashboard_form.setVisible(false);
            inventory_form.setVisible(false);
            menu_form.setVisible(true);
           customers_form.setVisible(false);

           menuDisplayCard();
           menuDisplayTotal();
         //  menuShowOrderData();
         //  clearFelids();

        }else if (event.getSource()==customer_btn){
            dashboard_form.setVisible(false);
            inventory_form.setVisible(false);
            menu_form.setVisible(false);
            customers_form.setVisible(true);

            customersShowData();
        }


    }
    public ObservableList<productData> inventoryDataList() {

        ObservableList<productData> listData = FXCollections.observableArrayList();

        String sql = "SELECT * FROM product";

        connect = DbConnection.connectDB();

        try {

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            productData prodData;

            while (result.next()) {

                prodData = new productData(result.getInt("id"),
                        result.getString("pro_id"),
                        result.getString("pro_name"),
                        result.getString("type"),
                        result.getInt("stock"),
                        result.getDouble("price"),
                        result.getString("status"),
                        result.getString("image"),
                        result.getDate("date"));

                listData.add(prodData);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<productData> inventoryListData;

    public void inventoryShowData() {
        inventoryListData = inventoryDataList();

        inventory_co_diproduct.setCellValueFactory(new PropertyValueFactory<>("productId"));
        inventory_co_productname.setCellValueFactory(new PropertyValueFactory<>("productName"));
        inventory_co_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        inventory_co_stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        inventory_co_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        inventory_co_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        inventory_co_date.setCellValueFactory(new PropertyValueFactory<>("date"));

        inventory_tableview.setItems(inventoryListData);

    }




    private String[] typelist ={"Meals","Drinks"};
    public void inventoryType(){
        List<String>typeL= new ArrayList<>();
     for(String data:typelist){
         typeL.add(data);
     }
        ObservableList listData = FXCollections.observableArrayList(typeL);
        inventory_type.setItems(listData);
    }
    private String[] statusList = {"Available", "Unavailable"};

    public void inventoryStatusList() {

        List<String> statusL = new ArrayList<>();

        for (String data : statusList) {
            statusL.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(statusL);
        inventory_status.setItems(listData);

    }
    public void displayUsername() {

        String user = data.username;
        user = user.substring(0, 1).toUpperCase() + user.substring(1);

        username.setText(user);

    }
    public void logout() {
        try {
            this.alert = new Alert(Alert.AlertType.CONFIRMATION);
            this.alert.setTitle("Error Message");
            this.alert.setHeaderText((String) null);
            this.alert.setContentText("Are you sure you want to logout?");
            Optional<ButtonType> option = this.alert.showAndWait();
            if (((ButtonType) option.get()).equals(ButtonType.OK)) {
                this.logout_btn.getScene().getWindow().hide();
                Parent root = FXMLLoader.load(this.getClass().getResource("/view/loginform.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setTitle("Cafe Management System");
                stage.setScene(scene);
                stage.show();
            }
        } catch (Exception var5) {
            var5.printStackTrace();
        }
    }
        public ObservableList<productData> inventoryDatalist(){

            ObservableList<productData> listData = FXCollections.observableArrayList();

            String sql = "SELECT * FROM product";

            connect = DbConnection.connectDB();

            try {

                prepare = connect.prepareStatement(sql);
                result = prepare.executeQuery();

                productData prodData;

                while (result.next()) {

                    prodData = new productData(result.getInt("id"),
                            result.getString("pro_id"),
                            result.getString("prod_name"),
                            result.getString("type"),
                            result.getInt("stock"),
                            result.getDouble("price"),
                            result.getString("status"),
                            result.getString("image"),
                            result.getDate("date"));

                    listData.add(prodData);

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return listData;
        }
    private ObservableList<productData> inventoryListdata;

    public void inventoryshowData() {
        inventoryListData = inventoryDataList();

        inventory_co_diproduct.setCellValueFactory(new PropertyValueFactory<>("productId"));
        inventory_co_productname.setCellValueFactory(new PropertyValueFactory<>("productName"));
        inventory_co_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        inventory_co_stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        inventory_co_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        inventory_co_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        inventory_co_date.setCellValueFactory(new PropertyValueFactory<>("date"));

        inventory_tableview.setItems(inventoryListData);

    }
    public void inventoryAddBtn() {

        if (inventory_productId.getText().isEmpty()
                || inventory_productName.getText().isEmpty()
                || inventory_type.getSelectionModel().getSelectedItem() == null
                || inventory_stock.getText().isEmpty()
                || inventory_price.getText().isEmpty()
                || inventory_status.getSelectionModel().getSelectedItem() == null
                || data.path == null) {

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();

        } else {

            // CHECK PRODUCT ID
            String checkProdID = "SELECT pro_id FROM product WHERE pro_id = '"
                    + inventory_productId.getText() + "'";

            connect = DbConnection.connectDB();

            try {

                statement = connect.createStatement();
                result = statement.executeQuery(checkProdID);

                if (result.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText(inventory_productId.getText() + " is already taken");
                    alert.showAndWait();
                } else {
                    String insertData = "INSERT INTO product "
                            + "(pro_id, pro_name, type, stock, price, status, image, date) "
                            + "VALUES(?,?,?,?,?,?,?,?)";

                    prepare = connect.prepareStatement(insertData);
                    prepare.setString(1, inventory_productId.getText());
                    prepare.setString(2, inventory_productName.getText());
                    prepare.setString(3, (String) inventory_type.getSelectionModel().getSelectedItem());
                    prepare.setString(4, inventory_stock.getText());
                    prepare.setString(5, inventory_price.getText());
                    prepare.setString(6, (String) inventory_status.getSelectionModel().getSelectedItem());

                    String path = data.path;
                    path = path.replace("\\", "\\\\");

                    prepare.setString(7, path);

                    // TO GET CURRENT DATE
                    Date date = new Date();
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                    prepare.setString(8, String.valueOf(sqlDate));

                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();

                    inventoryshowData();
                    inventoryClearBtn();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void inventoryImportBtn() {

        FileChooser openFile = new FileChooser();
        openFile.getExtensionFilters().add(new FileChooser.ExtensionFilter("Open Image File", "*png", "*jpg"));

        File file = openFile.showOpenDialog(main_form.getScene().getWindow());

        if (file != null) {

            data.path = file.getAbsolutePath();
            image = new Image(file.toURI().toString(), 135, 157, false, true);

            inventory_imageView.setImage(image);
        }
    }
    public void inventoryClearBtn() {

        inventory_productId.setText("");
        inventory_productName.setText("");
        inventory_type.getSelectionModel().clearSelection();
        inventory_stock.setText("");
        inventory_price.setText("");
        inventory_status.getSelectionModel().clearSelection();
        data.path = "";
        data.id = 0;
        inventory_imageView.setImage(null);

    }

    public void inventoryUpdateBtn() {

        if (inventory_productId.getText().isEmpty()
                || inventory_productName.getText().isEmpty()
                || inventory_type.getSelectionModel().getSelectedItem() == null
                || inventory_stock.getText().isEmpty()
                || inventory_price.getText().isEmpty()
                || inventory_status.getSelectionModel().getSelectedItem() == null
                || data.path == null || data.id == 0) {

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();

        } else {

            String path = data.path;
            path = path.replace("\\", "\\\\");

            String updateData = "UPDATE product SET "
                    + "pro_id = '" + inventory_productId.getText() + "', pro_name = '"
                    + inventory_productName.getText() + "', type = '"
                    + inventory_type.getSelectionModel().getSelectedItem() + "', stock = '"
                    + inventory_stock.getText() + "', price = '"
                    + inventory_price.getText() + "', status = '"
                    + inventory_status.getSelectionModel().getSelectedItem() + "', image = '"
                    + path + "', date = '"
                    + data.date + "' WHERE id = " + data.id;

            connect = DbConnection.connectDB();

            try {

                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to UPDATE Product ID: " + inventory_productId.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    prepare = connect.prepareStatement(updateData);
                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Updated!");
                    alert.showAndWait();

                    // TO UPDATE YOUR TABLE VIEW
                    inventoryshowData();
                    // TO CLEAR YOUR FIELDS
                    inventoryClearBtn();
                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Cancelled.");
                    alert.showAndWait();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void inventorySelectData() {

        productData prodData = inventory_tableview.getSelectionModel().getSelectedItem();
        int num = inventory_tableview.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        inventory_productId.setText(prodData.getProductId());
        inventory_productName.setText(prodData.getProductName());
        inventory_stock.setText(String.valueOf(prodData.getStock()));
        inventory_price.setText(String.valueOf(prodData.getPrice()));

        data.path = prodData.getImage();

        String path = "File:" + prodData.getImage();
        data.date = String.valueOf(prodData.getDate());
        data.id = prodData.getId();

        image = new Image(path, 135, 157, false, true);
        inventory_imageView.setImage(image);
    }
    public void inventoryDeleteBtn() {
        if (data.id == 0) {

            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all blank fields");
            alert.showAndWait();

        } else {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to DELETE Product ID: " + inventory_productId.getText() + "?");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {
                String deleteData = "DELETE FROM product WHERE id = " + data.id;
                try {
                    prepare = connect.prepareStatement(deleteData);
                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("successfully Deleted!");
                    alert.showAndWait();

                    // TO UPDATE YOUR TABLE VIEW
                    inventoryshowData();
                    // TO CLEAR YOUR FIELDS
                    inventoryClearBtn();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Cancelled");
                alert.showAndWait();
            }
        }
    }

    private int cID;
    public void customerID() {

        String sql = "SELECT MAX(customer_id) FROM customer";
        connect = DbConnection.connectDB();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                cID = result.getInt("MAX(customer_id)");
            }

            String checkCID = "SELECT MAX(customer_id) FROM receipt";
            prepare = connect.prepareStatement(checkCID);
            result = prepare.executeQuery();
            int checkID = 0;
            if (result.next()) {
                checkID = result.getInt("MAX(customer_id)");
            }

            if (cID == 0) {
                cID += 1;
            } else if (cID == checkID) {
                cID += 1;
            }

            data.cID = cID;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void menuShowOrderData() {
        this.menuOrderListData = this.menuGetOrder();
        this.menu_co_productName.setCellValueFactory(new PropertyValueFactory("productName"));
        this.menu_co_quantity.setCellValueFactory(new PropertyValueFactory("quantity"));
        this.menu_co_price.setCellValueFactory(new PropertyValueFactory("price"));
        this.menu_tableView.setItems(this.menuOrderListData);
    }
    public void menuSelectOrder() {
        productData prod = (productData)this.menu_tableView.getSelectionModel().getSelectedItem();
        int num = this.menu_tableView.getSelectionModel().getSelectedIndex();
        if (num - 1 >= -1) {
            this.getid = prod.getId();
        }
    }
    private double totalP;

    public void menuDisplayTotal() {
        menuGetTotal();
        menu_total.setText("Rs " + totalP);
    }
    public ObservableList<productData> menuGetOrder() {
        customerID();
        ObservableList<productData> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM customer WHERE customer_id = " + this.cID;
        connect = DbConnection.connectDB();

        try {
            prepare = this.connect.prepareStatement(sql);
            result = this.prepare.executeQuery();

            while(this.result.next()) {
                productData prod = new productData(this.result.getInt("customer_id"),
                        result.getString("prod_id"),
                        result.getString("prod_name"),
                        result.getString("type"),
                        result.getInt("quantity"),
                        result.getDouble("price"),
                        result.getString("image"),
                        result.getDate("date"));
                listData.add(prod);
            }

        } catch (Exception e) {

          }

        return listData;
    }
    public void printBill(String total) throws JRException {
        ObservableList<BillData> obList = FXCollections.observableArrayList();

        for (productData dto: menuOrderListData) {
            obList.add(new BillData(
                    dto.getProductName(),
                    dto.getQuantity(),
                    dto.getPrice()

            ));
        }

        for (BillData data: obList) {
            System.out.println(data.getProductName());
            System.out.println(data.getQuantity());
            System.out.println(data.getPrice());
        }

        JRBeanCollectionDataSource billData = new JRBeanCollectionDataSource(obList);

        Map<String, Object> map = new HashMap<>();
        map.put("collection",billData);
        map.put("total",total);

        InputStream resourceAsStream =  getClass().getResourceAsStream("/Report/billCafe1.jrxml");
        JasperDesign load = JRXmlLoader.load(resourceAsStream);
        JasperReport jasperReport= JasperCompileManager.compileReport(load);

        JasperPrint jasperPrint = JasperFillManager.fillReport(
                jasperReport,
                map,
                new JREmptyDataSource()
        );

        JasperViewer.viewReport(jasperPrint,false);

    }
    public void menuRestart(){

        totalP=0;
        change=0;
        amount=0;
        menu_amount.setText("");
        menu_change.setText("");
        menu_total.setText("");

    }
    public void menuGetTotal() {
        customerID();
        String total = "SELECT SUM(price) FROM customer WHERE customer_id = " + this.cID;
        this.connect = DbConnection.connectDB();

        try {
            prepare = this.connect.prepareStatement(total);
            result = this.prepare.executeQuery();
            if (result.next()) {
                totalP = result.getDouble("SUM(price)");
            }
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }
    private double amount;
    private double change;

   public void menuAmount(){
      menuGetTotal();
       if (menu_amount.getText().isEmpty()|| totalP ==0){
          alert=new Alert(Alert.AlertType.ERROR);
          alert.setTitle("Error Message");
          alert.setHeaderText(null);
          alert.setContentText("Imvalid: 3");
          alert.showAndWait();
       }else {
            amount = Double.parseDouble(menu_amount.getText());
            change = 0;
           if (amount<totalP){
               menu_amount.setText("");
           }else {
               change=(amount-totalP);
               menu_change.setText("Rs "+change);
           }
       }

   }
    public void menuPayBtn() throws JRException {

        printBill(menu_total.getText());

        if (this.totalP == 0.0D) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Please choose your order first!");
            alert.showAndWait();
        } else {
            menuGetTotal();
            String insertPay = "INSERT INTO receipt (customer_id, total, date, em_username) VALUES(?,?,?,?)";
            connect = DbConnection.connectDB();

            try {
                if (amount == 0.0D) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Messaged");
                    alert.setHeaderText((String)null);
                    alert.setContentText("Something wrong :3");
                    alert.showAndWait();
                } else {
                    this.alert = new Alert(Alert.AlertType.CONFIRMATION);
                    this.alert.setTitle("Confirmation Message");
                    this.alert.setHeaderText((String)null);
                    this.alert.setContentText("Are you sure?");
                    Optional<ButtonType> option = this.alert.showAndWait();
                    if (((ButtonType)option.get()).equals(ButtonType.OK)) {
                        this.customerID();
                        this.menuGetTotal();
                        this.prepare = this.connect.prepareStatement(insertPay);
                        this.prepare.setString(1, String.valueOf(this.cID));
                        this.prepare.setString(2, String.valueOf(this.totalP));
                        Date date = new Date();
                        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                        this.prepare.setString(3, String.valueOf(sqlDate));
                        this.prepare.setString(4, data.username);
                        this.prepare.executeUpdate();
                        this.alert = new Alert(Alert.AlertType.INFORMATION);
                        this.alert.setTitle("Infomation Message");
                        this.alert.setHeaderText((String)null);
                        this.alert.setContentText("Successful.");
                        this.alert.showAndWait();
                        menuShowOrderData();
                        //menuRestart();
                        clearFelids();

                    } else {
                        this.alert = new Alert(Alert.AlertType.WARNING);
                        this.alert.setTitle("Infomation Message");
                        this.alert.setHeaderText((String)null);
                        this.alert.setContentText("Cancelled.");
                        this.alert.showAndWait();
                    }
                }
            } catch (Exception var5) {
                var5.printStackTrace();
            }
        }

    }

    private void clearFelids() {
       menuOrderListData.clear();
        menu_total.setText("");
        menu_amount.setText("");
        menu_change.setText("");
    }

    public void menuReceiptBtn() {



    }
    public void menuRemoveBtn() {
        if (this.getid == 0) {
            this.alert = new Alert(Alert.AlertType.ERROR);
            this.alert.setTitle("Error Message");
            this.alert.setHeaderText((String)null);
            this.alert.setContentText("Please select the order you want to remove");
            this.alert.showAndWait();
        } else {
            String deleteData = "DELETE FROM customer WHERE  customer_id = " + this.getid;
            this.connect = DbConnection.connectDB();

            try {
                this.alert = new Alert(Alert.AlertType.CONFIRMATION);
                this.alert.setTitle("Confirmation Message");
                this.alert.setHeaderText((String)null);
                this.alert.setContentText("Are you sure you want to delete this order?");
                Optional<ButtonType> option = this.alert.showAndWait();
                if (((ButtonType)option.get()).equals(ButtonType.OK)) {
                    this.prepare = this.connect.prepareStatement(deleteData);
                    this.prepare.executeUpdate();
                }

                menuShowOrderData();
                menuAmount();
            } catch (Exception var3) {
                var3.printStackTrace();
            }
        }

    }
    public ObservableList<customersData> customersDataList() {
        ObservableList<customersData> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM receipt";
        this.connect = DbConnection.connectDB();

        try {
            this.prepare = this.connect.prepareStatement(sql);
            this.result = this.prepare.executeQuery();

            while(this.result.next()) {
                customersData cData = new customersData(this.result.getInt("id"), this.result.getInt("customer_id"), this.result.getDouble("total"), this.result.getDate("date"), this.result.getString("em_username"));
                listData.add(cData);
            }
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        return listData;
    }

    public void customersShowData() {
        this.customersListData = this.customersDataList();
        this.customers_col_customerID.setCellValueFactory(new PropertyValueFactory("customerID"));
        this.customers_col_total.setCellValueFactory(new PropertyValueFactory("total"));
        this.customers_col_date.setCellValueFactory(new PropertyValueFactory("date"));
        this.customers_col_cashier.setCellValueFactory(new PropertyValueFactory("emUsername"));
        this.customers_tableView.setItems(this.customersListData);
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
           displayUsername();

        dashboardDisplayNC();
        dashboardDisplayTI();
        dashboardTotalI();
        dashboardNSP();
        dashboardIncomeChart();
        dashboardCustomerChart();;


            inventoryType();
            inventoryStatusList();
            inventoryshowData();

            menuDisplayCard();
            menuDisplayTotal();
            menuGetOrder();
            menuShowOrderData();

            customersShowData();

    }
}
