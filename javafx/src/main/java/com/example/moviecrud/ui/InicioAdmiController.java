package com.example.moviecrud.ui;

import com.example.moviecrud.MovieCrudApplication;
import com.example.moviecrud.business.CineMgr;
import com.example.moviecrud.business.UsuarioMgr;
import com.example.moviecrud.business.entities.Cine;
import com.example.moviecrud.business.entities.Usuario;
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
    private String us;

    @FXML
    void cargaInicio(ActionEvent event) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(MovieCrudApplication.getContext()::getBean);

        Parent root = fxmlLoader.load(Inicio.class.getResourceAsStream("Inicio.fxml"));
        Scene inicioScene = new Scene(root, 800, 550);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(inicioScene);
        window.show();
    }

    @FXML
    public void adminEmpresa(ActionEvent event) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(MovieCrudApplication.getContext()::getBean);

        Parent root = fxmlLoader.load(Principal.class.getResourceAsStream("Cartelera.fxml"));
        Scene inicioScene = new Scene(root, 800, 550);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(inicioScene);
        window.show();
    }

    @FXML
    public void iniciarSesion(ActionEvent event) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(MovieCrudApplication.getContext()::getBean);

        Parent root = fxmlLoader.load(Inicio.class.getResourceAsStream("iniciarSesion.fxml"));
        Scene inicioScene = new Scene(root, 800, 550);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(inicioScene);
        window.show();
    }


    @FXML
    public void ingresar(ActionEvent event) throws IOException {
        String username = adminID.getText();
        System.out.println(username);
        String contra = password.getText();
        List<Usuario> users = usuarioMgr.getAllUsuarios();
        System.out.println(users.size());
        List<Cine> cines = cineMgr.getAllCine();

        boolean cineEncontrado = false;

        int indiceCine = 0;

        try {
            Usuario usuario = (Usuario) usuarioMgr.getUsuarioByUsername(username);
            System.out.println(usuario.getAdminPrivileges());
            if (usuario.getAdminPrivileges().equals("cine")) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setControllerFactory(MovieCrudApplication.getContext()::getBean);

                us = usuario.getUsername();

                Parent root = fxmlLoader.load(CarteleraFuncionesDelCine.class.getResourceAsStream("CarteleraFuncionesDelCine.fxml"));
                Scene inicioScene = new Scene(root, 600, 500);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(inicioScene);
                window.show();


            } else if (usuario.getAdminPrivileges().equals("admin")){
                System.out.println(usuario.getClass());
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setControllerFactory(MovieCrudApplication.getContext()::getBean);

                Parent root = fxmlLoader.load(Principal.class.getResourceAsStream("Cartelera.fxml"));
                Scene inicioScene = new Scene(root, 600, 500);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(inicioScene);
                window.show();

            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING,  "Algun dato ingresado no es correcto o no esta asociado a nigun cine o administrador del sistema");

            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, e.getMessage() + "Algun dato ingresado no es correeeeeecto o no esta asociado a nigun usuario del sistema");
            alert.showAndWait();

        }

    }

    public String getUs() {
        return us;
    }

    public void setUs(String us) {
        this.us = us;
    }
}
