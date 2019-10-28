package com.example.moviecrud.ui;

import com.example.moviecrud.MovieCrudApplication;
import com.example.moviecrud.business.LocalMgr;
import com.example.moviecrud.business.SalaManager;
import com.example.moviecrud.business.entities.Cine;
import com.example.moviecrud.business.entities.Locales;
import com.example.moviecrud.business.entities.Sala;
import com.example.moviecrud.ui.movie.SalaController;
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
import jdk.vm.ci.meta.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;

@Controller
public class CarteleraLocales implements Initializable {

    @Autowired
    LocalMgr localMgr;

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
    private TableView<Locales> tabla;

    @FXML
    private TableColumn<Locales, String> locales;

    @FXML
    private TableColumn<Locales, Cine> cines;

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

    private ObservableList<Locales> localList = FXCollections.observableArrayList();

    @FXML
    void agregarLocalAction(ActionEvent event) throws Exception {
//        FXMLLoader fxmlLoader = new FXMLLoader();
//        fxmlLoader.setControllerFactory(MovieCrudApplication.getContext()::getBean);
//
//        Parent root = fxmlLoader.load(SalaController.class.getResourceAsStream("AddSala.fxml"));
//        Stage stage = new Stage();
//        stage.setScene(new Scene(root));
//        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        locales.setCellValueFactory(new PropertyValueFactory<>("locales"));
        cines.setCellValueFactory(new PropertyValueFactory<>("cines"));


        actualizaCartSala();



        buscador.textProperty().addListener((((observable, oldValue, newValue) -> {
            FilteredList<Locales> filteredList = new FilteredList<>(localList, s -> true);
            filteredList.setPredicate((Predicate<? super Locales>) (Locales local) ->{
                if (newValue.isEmpty() || newValue==null){
                    return true;
                } else if (local.getName().contains(newValue)){
                    return true;
                } else if (local.getCine().getNombre().contains(newValue)){
                    return true;
                }
                return false;
            });

            SortedList sortedList = new SortedList(filteredList);
            sortedList.comparatorProperty().bind(tabla.comparatorProperty());
            tabla.setItems(sortedList);

        })));
        FilteredList<Locales> filteredList = new FilteredList<>(localList, s -> true);
        SortedList sortedList = new SortedList(filteredList);
        sortedList.comparatorProperty().bind(tabla.comparatorProperty());
        tabla.setItems(sortedList);
    }

    public void actualizaCartSala(){
        localList.clear();
        localList.addAll(localMgr.getAllSalas());
        tabla.setItems(localList);
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


    //EDICION DE LOCALES


    @FXML
    private TextField nombrelocal;

    @FXML
    private TextField cinelocal;

    @FXML
    private Button btnaddlocal;




}
