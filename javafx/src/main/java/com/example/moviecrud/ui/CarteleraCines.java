package com.example.moviecrud.ui;

import com.example.moviecrud.MovieCrudApplication;
import com.example.moviecrud.business.CineMgr;
import com.example.moviecrud.business.entities.Cine;
import com.example.moviecrud.business.entities.Locales;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;

@Controller
public class CarteleraCines implements Initializable {

    @Autowired
    CineMgr cineMgr;

    @FXML
    private AnchorPane root;

    @FXML
    private MenuItem mItemAgregarSala;

    @FXML
    private MenuItem mItemEliminarSala;

    @FXML
    private MenuItem mItemEditarSala;

    @FXML
    private TextField buscador;

    @FXML
    private TableView<Cine> tabla;

    @FXML
    private TableColumn<Cine, String> cine;

    @FXML
    private Button btnpeliculas;

    @FXML
    private Button btnsalas;

    @FXML
    private Button btnfunciones;

    @FXML
    private Button btnlocales;

    @FXML
    private Button btncines;

    private ObservableList<Cine> cineList = FXCollections.observableArrayList();

    @FXML
    void agregarSalaAction(ActionEvent event) {

    }

    @FXML
    public void cargaCartPeliculas (ActionEvent event) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(MovieCrudApplication.getContext()::getBean);

        Parent root = fxmlLoader.load(Principal.class.getResourceAsStream("Cartelera.fxml"));
        Scene inicioScene = new Scene(root, 600,500);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(inicioScene);
        window.show();
    }

    @FXML
    public void cargaCartSalas (ActionEvent event) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(MovieCrudApplication.getContext()::getBean);

        Parent root = fxmlLoader.load(CarteleraSalas.class.getResourceAsStream("CarteleraSalas.fxml"));
        Scene inicioScene = new Scene(root, 600,500);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(inicioScene);
        window.show();
    }

//    @FXML
//    public void cargaCartFunciones (ActionEvent event) throws Exception{
//        FXMLLoader fxmlLoader = new FXMLLoader();
//        fxmlLoader.setControllerFactory(MovieCrudApplication.getContext()::getBean);
//
//        Parent root = fxmlLoader.load(Principal.class.getResourceAsStream("Cartelera.fxml"));
//        Scene inicioScene = new Scene(root, 600,500);
//        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        window.setScene(inicioScene);
//        window.show();
//    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cine.setCellValueFactory(new PropertyValueFactory<>("cine"));



        actualizaCartSala();



        buscador.textProperty().addListener((((observable, oldValue, newValue) -> {
            FilteredList<Cine> filteredList = new FilteredList<>(cineList, s -> true);
            filteredList.setPredicate((Predicate<? super Cine>) (Cine cine) ->{
                if (newValue.isEmpty() || newValue==null){
                    return true;
                } else if (cine.getNombre().contains(newValue)){
                    return true;
                }
                return false;
            });

            SortedList sortedList = new SortedList(filteredList);
            sortedList.comparatorProperty().bind(tabla.comparatorProperty());
            tabla.setItems(sortedList);

        })));
        FilteredList<Cine> filteredList = new FilteredList<>(cineList, s -> true);
        SortedList sortedList = new SortedList(filteredList);
        sortedList.comparatorProperty().bind(tabla.comparatorProperty());
        tabla.setItems(sortedList);
    }

    public void actualizaCartSala(){
        cineList.clear();
        cineList.addAll(cineMgr.getAllSalas());
        tabla.setItems(cineList);
    }
}
