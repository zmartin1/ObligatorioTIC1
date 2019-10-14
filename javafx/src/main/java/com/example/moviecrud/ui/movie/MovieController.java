package com.example.moviecrud.ui.movie;

import com.example.moviecrud.MovieCrudApplication;
import com.example.moviecrud.business.PeliculaMgr;
import com.example.moviecrud.business.entities.Pelicula;
import com.example.moviecrud.business.exceptions.InformacionPeliculaInvalida;
import com.example.moviecrud.business.exceptions.PeliculaNoExiste;
import com.example.moviecrud.business.exceptions.PeliculaYaExiste;
import com.example.moviecrud.ui.Principal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.management.LockInfo;
import java.net.URL;
import java.util.ResourceBundle;


@Component
public class MovieController implements Initializable {

    Principal principal;

    @Autowired
    public MovieController(Principal principal){
        this.principal=principal;
    }

    @Autowired
    private PeliculaMgr peliculaMgr;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnClose;

    @FXML
    private TextField titulo;

    @FXML
    private TextField actores;

    @FXML
    private ComboBox<String> genero;

    @FXML
    private TextField duracion;

    @FXML
    private TextArea descripcion;


    //eliminar
    @FXML
    private Button btnDelete;

    @FXML
    private TextField tituloAEliminar;

    //editar
    @FXML
    private Button btnEdit;

    @FXML
    private TextField tituloAEditar;

    @FXML
    private TextField tituloNew;

    @FXML
    private TextField actoresNew;

    @FXML
    private TextField duracionNew;

    @FXML
    private TextArea descripcionNew;



    //parte del editar
    private boolean editando = false;
    private Long idTemp;


    //Filtrado
    @FXML
    private ListView<String> listaBusqueda;

    private ObservableList<Pelicula> movieLista = FXCollections.observableArrayList();

    private ObservableList<String> movieListaString = FXCollections.observableArrayList();

    @FXML
    private TextField buscador;







    @FXML
    void close(ActionEvent actionEvent) {
        Node source = (Node)  actionEvent.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    void addPelicula(ActionEvent event) {
            if (titulo.getText() == null || titulo.getText().equals("") || actores.getText() == null || actores.getText().equals("")
            || duracion.getText() == null || duracion.getText().equals("") || descripcion.getText() == null || descripcion.getText().equals("")){

            showAlert(
                    "Datos faltantes!",
                    "No se ingresaron los datos necesarios para completar el ingreso.");

            } else {
                try {

                String tituloAgregar = titulo.getText();
                String generoAgregar = genero.getValue();
                String actoresAgregar = actores.getText();
                String duracionAgregar = duracion.getText();
                String descripcionAgregar = descripcion.getText();


                try {
                    if(!editando) {
                        peliculaMgr.addPelicula(tituloAgregar, generoAgregar, actoresAgregar, duracionAgregar, descripcionAgregar);

                        showAlert("Pelicula agregada", "Se agrego con exito la Pelicula!");


                        close(event);
                        principal.actualizaCart();
                    } else {


                            Pelicula peliculaActualizada = new Pelicula(tituloAgregar,generoAgregar ,actoresAgregar,duracionAgregar ,descripcionAgregar);
                            peliculaMgr.update(idTemp,peliculaActualizada);

                            showAlert("Pelicula Editada", "Se edito con exito la Pelicula!");
                            close(event);
                            principal.actualizaCart();


                    }
                } catch (InformacionPeliculaInvalida informacionPeliculaInvalida) {
                    showAlert(
                            "Informacion invalida !",
                            "Se encontro un error en los datos ingresados.");
                } catch (PeliculaYaExiste peliculaYaExiste) {
                    showAlert(
                            "Documento ya registrado !",
                            "El documento indicado ya ha sido registrado en el sistema).");
                }

                } catch (NumberFormatException e) {

                    showAlert(
                        "Datos incorrectos !",
                        "El documento no tiene el formato esperado (numerico).");

                }
            }
        }



        @FXML
        void eliminarPelicula (ActionEvent event){
            if (tituloAEliminar.getText() == null || tituloAEliminar.equals("")){

                showAlert(
                        "Datos faltantes!",
                        "No se ingresaron los datos necesarios para completar el ingreso.");

            } else {
                try {

                    String tituloEliminar = tituloAEliminar.getText();


                    try {

                        peliculaMgr.eliminarPelicula(tituloEliminar);

                        showAlert("Pelicula eliminada", "Se elimino con exito la Pelicula!");
                        principal.actualizaCart();

                        close(event);
                    } catch (InformacionPeliculaInvalida informacionPeliculaInvalida) {
                        showAlert(
                                "Informacion invalida !",
                                "Se encontro un error en los datos ingresados.");
                    } catch (PeliculaNoExiste peliculaNoExiste) {
                        showAlert(
                                "Pelicula no registrada !",
                                "La pelicula no esta registrada en el sistema.");
                    }

                } catch (NumberFormatException e) {

                    showAlert(
                            "Datos incorrectos !",
                            "El documento no tiene el formato esperado (numerico).");

                }
            }

        }


    @FXML
    void editarPeliculaAction (ActionEvent event){
        if (tituloNew.getText() == null || tituloNew.getText().equals("") || actoresNew.getText() == null || actoresNew.getText().equals("")
                || duracionNew.getText() == null || duracionNew.getText().equals("") || descripcionNew.getText() == null || descripcionNew.getText().equals("")){

            showAlert(
                    "Datos faltantes!",
                    "No se ingresaron los datos necesarios para completar el ingreso.");

        } else {
            try {

                String tituloAEdi = tituloAEditar.getText();
                String tituloEditado = tituloNew.getText();
                String generoAgregar = genero.getValue();
                String actoresEditado = actoresNew.getText();
                String duracionEditado = duracionNew.getText();
                String descripcionEditado = descripcionNew.getText();


                try {

                    peliculaMgr.editarPelicula(tituloAEdi, tituloEditado,generoAgregar ,actoresEditado,duracionEditado,descripcionEditado);

                    showAlert("Pelicula agregada", "Se agrego con exito la Pelicula!");
                    principal.actualizaCart();

                    close(event);
                } catch (InformacionPeliculaInvalida informacionPeliculaInvalida) {
                    showAlert(
                            "Informacion invalida !",
                            "Se encontro un error en los datos ingresados.");
                } catch (PeliculaNoExiste peliculaNoExiste) {
                    showAlert(
                            "La pelicula no existe !",
                            "El documento indicado no ha sido registrado en el sistema).");
                }

            } catch (NumberFormatException e) {

                showAlert(
                        "Datos incorrectos !",
                        "El documento no tiene el formato esperado (numerico).");

            }
        }
    }

    @FXML
    void editarPeliculaFinalAction(ActionEvent event) throws Exception {
//
//
//        FXMLLoader fxmlLoader = new FXMLLoader();
//        fxmlLoader.setControllerFactory(MovieCrudApplication.getContext()::getBean);
//
//        Parent root = fxmlLoader.load(MovieController.class.getResourceAsStream("AddPelicula.fxml"));
//        Stage stage = new Stage();
//        stage.setScene(new Scene(root));
//        stage.show();
//
//
//
//        MovieController movieController = fxmlLoader.getController();
//        //movieController.loadMovieData();

    }



    private void clean (){
        titulo.setText(null);
        actores.setText(null);
        genero.setVisibleRowCount(1);
        duracion.setText(null);
        descripcion.setText(null);
    }
    private void showAlert(String title, String contextText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contextText);
        alert.showAndWait();
    }

    public void loadMovieData (Pelicula pelicula){
        try {
            titulo.setText(pelicula.getTitulo());
            actores.setText(pelicula.getActores());
            descripcion.setText(pelicula.getDescripcion());
            duracion.setText(pelicula.getDuracion());
        } catch (NumberFormatException e){
            e.printStackTrace();
        }
        idTemp = pelicula.getId();
        editando = true;
    }

    public void actualizarLista (){
        movieLista.clear();
        movieLista.addAll(peliculaMgr.getAllPeliculas());
        //listaBusqueda.setItems(movieLista);
        for (Pelicula pelicula: movieLista
             ) {
            movieListaString.add(pelicula.getTitulo());
        }

        listaBusqueda.setItems(movieListaString);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
       actualizarLista();
    }
}
