package com.example.moviecrud;



import com.example.moviecrud.business.PeliculaMgr;
import com.example.moviecrud.ui.InfoPelicula;
import com.example.moviecrud.ui.Inicio;
import com.example.moviecrud.ui.Principal;
import com.example.moviecrud.ui.movie.MovieController;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.text.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.stream.Stream;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.effect.Reflection;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import javax.swing.*;

@SpringBootApplication
public class MovieCrudApplication extends Application {

    @Autowired
    PeliculaMgr peliculaMgr;


    private static ConfigurableApplicationContext context;

    private Parent root;

    @Override
    public void init() throws Exception {
        MovieCrudApplication.context = SpringApplication.run(MovieCrudApplication.class);
    }

    private static final double WIDTH = 700, HEIGHT = 400;
   // private Timeline animation;

    public Parent createContent()throws Exception {
        // load images
        int z = peliculaMgr.getAllPeliculas().size();
        Image[] images = new Image[z];

        for (int i = 0; i < z ; i++) {

            byte [] img = peliculaMgr.getAllPeliculas().get(i).getMovieImage();
            ByteArrayInputStream bis = new ByteArrayInputStream(img);
            BufferedImage bImage = ImageIO.read(bis);
            Image image = SwingFXUtils.toFXImage(bImage, null);
            images[i] = image;
        }


        // create display shelf
        DisplayShelf displayShelf = new DisplayShelf(images);
        displayShelf.setPrefSize(WIDTH, HEIGHT);
        for (int i = 0; i <z ; i++) {
            displayShelf.getItems()[i].setOnMouseClicked(abrirPaginaPelicula());
            displayShelf.getItems()[i].setId(String.valueOf(i));
        }
        return displayShelf;
    }

    EventHandler abrirPaginaPelicula() throws Exception {
        EventHandler handler = new EventHandler() {
            @Override
            public void handle(Event event) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setControllerFactory(MovieCrudApplication.getContext()::getBean);

                Parent root = null;
                try {
                    root = fxmlLoader.load(MovieController.class.getResourceAsStream("InfoPelicula.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                PerspectiveImage pp = (PerspectiveImage) event.getSource();
                int clave = Integer.valueOf(pp.getId());
                Stage stage = new Stage();
                Scene sc = new Scene(root);

                Text dur = (Text) sc.lookup("#Duracion");
                Text gen = (Text) sc.lookup("#Genero");
                Text act = (Text) sc.lookup("#Actores");
                Text tit = (Text) sc.lookup("#titulo");

                tit.setText(peliculaMgr.getAllPeliculas().get(clave).getTitulo());
                dur.setText(peliculaMgr.getAllPeliculas().get(clave).getDuracion());
                gen.setText(peliculaMgr.getAllPeliculas().get(clave).getGenero());
                act.setText(peliculaMgr.getAllPeliculas().get(clave).getActores());

                stage.setScene(sc);
                stage.show();
            }
        };
        return handler;
    }



    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(MovieCrudApplication.getContext()::getBean);

        root = fxmlLoader.load(Inicio.class.getResourceAsStream("Inicio.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();



//        primaryStage.setResizable(true);
//        primaryStage.setScene(new Scene(createContent()));
//        primaryStage.show();


    }
    static {

        System.setProperty("java.awt.headless", "false");
    }


    @Override
    public void stop() {
        MovieCrudApplication.getContext().close();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static ConfigurableApplicationContext getContext() {
        return context;
    }


}

