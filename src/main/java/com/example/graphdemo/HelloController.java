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
import java.lang.Math;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import model.*;

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
    private GraphForMatrix<Vertex1<Node, String>, String> dirGraph;
    private GraphForMatrix<Vertex1, String> graph;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dirGraph = new GraphForMatrix<>(true);
        graph = new GraphForMatrix<>(false);
        gc = canvas.getGraphicsContext2D();
        canvas.setOnMousePressed(this::onMousePressed);
    }

    @FXML
    void addEdge(ActionEvent event) {
        String  init = initiBTN.getText();
        String fin = finalBTN.getText();
        if (dirCB.isSelected()) {
            for (int i = 0; i < counter; i++){
                Vertex1<Node, String> v = dirGraph.getVertices().get(dirGraph.searchVertex(init)).getValue();
                Vertex1<Node, String> v2 = dirGraph.getVertices().get(dirGraph.searchVertex(fin)).getValue();
                if(v != null && v2 != null) dirGraph.addEdge(v.getKey(),v2.getKey());
                gc.setStroke(Color.DARKRED);
                gc.setLineWidth(1);
                //gc.strokeLine(v.getX()+5, v.getY(), v2.getX()-5, v2.getY());
            }
        } else {
            for (int i = 0; i < counter; i++){
                Vertex1<Node, String> v = dirGraph.getVertices().get(dirGraph.searchVertex(init)).getValue();
                Vertex1<Node, String> v2 = dirGraph.getVertices().get(dirGraph.searchVertex(fin)).getValue();
                if(v != null && v2 != null) graph.addEdge(v.getKey(),v2.getKey());
                gc.setStroke(Color.RED);
                gc.setLineWidth(2);
                //gc.strokeLine(v.getX()+5, v.getY(), v2.getX()-5, v2.getY());
            }
        }
    }

    public double calculateAttenuation(Vertex1<Node, String> initialNode, Vertex1<Node, String> destinationNode, double distance){
        double frequency = (initialNode.getValue().getBandWith() + destinationNode.getValue().getBandWith())/2;
        return 20*Math.log10(distance)+20*Math.log10(frequency)+20*Math.log10((4*Math.PI)/(3*Math.pow(10, 8)));
    }

    private void onMousePressed(MouseEvent e) {
        System.out.println("X: " + e.getX() + " " + "Y: " + e.getY());
        //Circle nodo1 = new Circle(e.getX(), e.getY(), 20);
        gc.fillOval(e.getX(), e.getY(),20,20);
        gc.fillText(Integer.toString(counter+1),e.getX(),e.getY());
        counter++;
        //graph.addVertex(counter,e.getX(),e.getY());
        //dirGraph.addVertex(counter, e.getX(), e.getY());
    }
}
