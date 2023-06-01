package com.example.graphdemo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import model.GraphList;
import model.Node1;
import model.Vertex;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML
    private Canvas canvas;
    GraphicsContext gc;
    @FXML
    private Button edgeBTN;
    @FXML
    private CheckBox dirCB;
    @FXML
    private TextField finalBTN;

    @FXML
    private TextField initiBTN;

    private int counter = 0;
    private GraphList dirGraph;
    private GraphList graph;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dirGraph = new GraphList(true);
        graph = new GraphList(false);
        gc = canvas.getGraphicsContext2D();
        canvas.setOnMousePressed(this::onMousePressed);
    }

    @FXML
    void addEdge(ActionEvent event) {
        int init = Integer.parseInt(initiBTN.getText());
        int fin = Integer.parseInt(finalBTN.getText());
        if (dirCB.isSelected()) {
            for (int i = 0; i < counter; i++){
                Vertex v = dirGraph.searchVertex(init);
                Vertex v2 = dirGraph.searchVertex(fin);
                if(v != null && v2 != null) dirGraph.addEdge(v.getValue(),v2.getValue());
                gc.setStroke(Color.DARKRED);
                gc.setLineWidth(1);
                gc.strokeLine(v.getX()+5, v.getY(), v2.getX()-5, v2.getY());
            }
        } else {
            for (int i = 0; i < counter; i++){
                Vertex v = graph.searchVertex(init);
                Vertex v2 = graph.searchVertex(fin);
                if(v != null && v2 != null) graph.addEdge(v.getValue(),v2.getValue());
                gc.setStroke(Color.RED);
                gc.setLineWidth(2);
                gc.strokeLine(v.getX()+5, v.getY(), v2.getX()-5, v2.getY());
            }
        }
    }


    private void onMousePressed(MouseEvent e) {
        System.out.println("X: " + e.getX() + " " + "Y: " + e.getY());
        //Circle nodo1 = new Circle(e.getX(), e.getY(), 20);
        gc.fillOval(e.getX(), e.getY(),20,20);
        gc.fillText(Integer.toString(counter+1),e.getX(),e.getY());
        counter++;
        graph.addVertex(counter,e.getX(),e.getY());
        dirGraph.addVertex(counter, e.getX(), e.getY());
    }
}
