package com.example.moviecrud.ui;

import com.example.moviecrud.MovieCrudApplication;
import com.example.moviecrud.business.CineMgr;
import com.example.moviecrud.business.UsuarioMgr;
import com.example.moviecrud.business.entities.Cine;
import com.example.moviecrud.business.entities.Usuario;
import com.example.moviecrud.business.entities.UsuarioFinal;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.List;

@Controller
public class InicioAdmiController {

    @Autowired
    UsuarioMgr usuarioMgr;

    @Autowired
    CineMgr cineMgr;


    @FXML
    private AnchorPane root;

    @FXML
    private Button registrarse;

    @FXML
    private Button user;

    @FXML
    private TextField adminID;

    @FXML
    private TextField password;

    @FXML
    private Button enter;

    @FXML
    void cargaInicio (ActionEvent event) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(MovieCrudApplication.getContext()::getBean);

        Parent root = fxmlLoader.load(Inicio.class.getResourceAsStream("Inicio.fxml"));
        Scene inicioScene = new Scene(root, 600, 500);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(inicioScene);
        window.show();
    }

    @FXML
    public void adminEmpresa (ActionEvent event) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(MovieCrudApplication.getContext()::getBean);

        Parent root = fxmlLoader.load(Principal.class.getResourceAsStream("Cartelera.fxml"));
        Scene inicioScene = new Scene(root, 600,500);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(inicioScene);
        window.show();
    }

    @FXML
    public void iniciarSesion (ActionEvent event) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(MovieCrudApplication.getContext()::getBean);

        Parent root = fxmlLoader.load(Inicio.class.getResourceAsStream("iniciarSesion.fxml"));
        Scene inicioScene = new Scene(root, 600, 500);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(inicioScene);
        window.show();
    }


    @FXML
    public void ingresar (ActionEvent event) throws IOException {
        String username = adminID.getText();
        String contra = password.getText();

        List<Usuario> users = usuarioMgr.getAllUsuarios();
        List<Cine> cines = cineMgr.getAllCine();

        boolean cineEncontrado = false;

        int indiceCine = 0;

      //  System.out.println(cines.size()+ "!!!!!!!");

        for (int i = 0; i < cines.size() ; i++) {
            String nombre =   cines.get(i).getNombre();

            if (nombre.equals(username)) {
                System.out.println("SE ENCONTRO EL CINE");
                indiceCine = i;
                cineEncontrado = true;
            }
        }

        if (cineEncontrado){

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setControllerFactory(MovieCrudApplication.getContext()::getBean);

            Parent root = fxmlLoader.load(CarteleraFuncionesDelCine.class.getResourceAsStream("CarteleraFuncionesDelCine.fxml"));
            Scene inicioScene = new Scene(root, 600, 500);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(inicioScene);
            window.show();


        } else if (username.equals("root") || username.equals("admin")){
          //parte admin




            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setControllerFactory(MovieCrudApplication.getContext()::getBean);

            Parent root = fxmlLoader.load(Principal.class.getResourceAsStream("Cartelera.fxml"));
            Scene inicioScene = new Scene(root, 600, 500);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(inicioScene);
            window.show();


        } else {
            //parte cliente final
            UsuarioFinal usuarioFinal = (UsuarioFinal) usuarioMgr.getUsuarioByUsername(username);

            if (usuarioFinal != null) {


                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setControllerFactory(MovieCrudApplication.getContext()::getBean);

                Parent root = fxmlLoader.load(Inicio.class.getResourceAsStream("Inicio.fxml"));
                Scene inicioScene = new Scene(root, 600, 500);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(inicioScene);
                window.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Algun dato ingresado no es correcto o no esta asociado a nigun usuario del sistema");
                alert.showAndWait();
            }


        }
    }

}
