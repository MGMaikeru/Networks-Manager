package com.example.graphdemo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import java.lang.Math;

import model.*;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML
    private Canvas canvas;
    private GraphicsContext gc;
    @FXML
    private Button edgeBTN;
    @FXML
    private Button addBTN;
    @FXML
    private Button removeBTN;
    @FXML
    private TextField finalTF;

    @FXML
    private TextField initTF;
    @FXML
    private TextField nameTF;
    @FXML
    private TextField bandTF;
    @FXML
    private TextField keyTF;
    @FXML
    private TextField valueTF;

    private Integer counter = 0;
    private GraphForMatrix<Vertex1<Node, String>, String> graph;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        keyTF.setText(null);
        nameTF.setText(null);
        bandTF.setText(null);
        graph = new GraphForMatrix<>(false);
        gc = canvas.getGraphicsContext2D();
        canvas.setOnMousePressed(this::onMousePressed);
    }

    @FXML
    void addEdge(ActionEvent event) {
        String  init = initTF.getText();
        String fin = finalTF.getText();
            for (int i = 0; i < counter; i++){
                Vertex1<Node, String> v = graph.getVertices().get(graph.searchVertex(init)).getValue();
                Vertex1<Node, String> v2 = graph.getVertices().get(graph.searchVertex(fin)).getValue();
                if(v == null || v2 == null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("The Vertex does not exist in the notDirected Graph");
                    alert.showAndWait();
                }
                if(v != null && v2 != null) graph.addEdge(v.getKey(),v2.getKey());
                initTF.setText(null);
                finalTF.setText(null);
                gc.setStroke(Color.RED);
                gc.setLineWidth(2);
                gc.strokeLine(v.getX()+3, v.getY(), v2.getX()-3, v2.getY());
            }

    }

    @FXML
    void modify(ActionEvent event) {

    }

    @FXML
    void remove(ActionEvent event) {

    }

    public double calculateAttenuation(Vertex1<Node, String> initialNode, Vertex1<Node, String> destinationNode, double distance){
        double frequency = (initialNode.getValue().getBandWith() + destinationNode.getValue().getBandWith())/2;
        return 20*Math.log10(distance)+20*Math.log10(frequency)+20*Math.log10((4*Math.PI)/(3*Math.pow(10, 8)));
    }

    private void onMousePressed(MouseEvent e) {
        //System.out.println("X: " + e.getX() + " " + "Y: " + e.getY());
        int k = graph.searchVertex(keyTF.getText());
        if(k == -1){
            if(keyTF.getText() == null || bandTF.getText() == null || nameTF.getText() == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Please provide the full information.");
                alert.showAndWait();
            }
            else {
                String name = nameTF.getText();
                double band = Double.parseDouble(bandTF.getText());
                String key = keyTF.getText();
                Node n = new Node(keyTF.getText(), band, name);
                Vertex1 v = new Vertex1<>(n, keyTF.getText(), e.getX(), e.getY());
                graph.addVertex(key, v, e.getX(), e.getY());
                gc.fillOval(e.getX(), e.getY(), 20, 20);
                gc.fillText(keyTF.getText(), e.getX(), e.getY());
                counter++;
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("A vertex was added.");
                alert.showAndWait();
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("The Vertex is already in the graph");
            alert.showAndWait();
        }
    }
}
