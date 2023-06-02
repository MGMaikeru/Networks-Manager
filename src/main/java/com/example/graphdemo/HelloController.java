package com.example.graphdemo;

import exception.EmptyFieldException;
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
    private TextField KEY2TF;

    @FXML
    private TextField KEYTF;
    @FXML
    private Button bfsBTN;
    @FXML
    private Button dijkstraBTN;

    @FXML
    private Button primBTN;
    @FXML
    private Button edgeBTN;
    @FXML
    private Button addBTN;
    @FXML
    private Button removeBTN;
    @FXML
    private Button removeEBTN;
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
    @FXML
    private TextField nvalueTF;
    @FXML
    private TextField nbandTF;
    @FXML
    private TextField nNameTF;

    //for vertex quantity
    private Integer counter = 0;

    private GraphForMatrix<Vertex1<Device, String>, String> graph;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0,0,canvas.getWidth(), canvas.getHeight());
        keyTF.setText(null);
        nameTF.setText(null);
        bandTF.setText(null);
        graph = new GraphForMatrix<>(false);
        canvas.setOnMousePressed(this::onMousePressed);
    }
    @FXML
    void bfs(ActionEvent event) {
        String key = KEYTF.getText();
        if(key != null) {
            try {
                graph.bfs(key);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("BFS INFO HERE");
                alert.showAndWait();
            } catch (EmptyFieldException e) {
                throw new RuntimeException(e);
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Invalid key/s");
            alert.showAndWait();
        }
    }
    @FXML
    void dijkstra(ActionEvent event) {
        String key = KEYTF.getText();
        String key2 = KEY2TF.getText();
        if(key != null && key2 != null) {
            graph.dijkstra(key, key2);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("DIJKSTRA INFO HERE");
            alert.showAndWait();
        }
         else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Invalid key/s");
            alert.showAndWait();
        }
    }
    @FXML
    void prim(ActionEvent event) {
        String key = KEYTF.getText();
        if(key != null) {
            graph.prim(key);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("PRIM INFO HERE");
            alert.showAndWait();
        }
         else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Invalid key/s");
            alert.showAndWait();
        }
    }

    @FXML
    void addEdge(ActionEvent event) {
        String  init = initTF.getText();
        String fin = finalTF.getText();
            for (int i = 0; i < counter; i++){
                Vertex1<Device, String> v = graph.getVertices().get(graph.searchVertex(init)).getValue();
                Vertex1<Device, String> v2 = graph.getVertices().get(graph.searchVertex(fin)).getValue();
                if(v == null || v2 == null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("The Vertex does not exist in the notDirected Graph");
                    alert.showAndWait();
                    //romper ciclo
                    break;
                }
                if(v != null && v2 != null){
                    double distance = v2.getX() - v.getX();
                    distance = distance + (v2.getY() - v.getY());
                    if(distance < 0){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setContentText("The Vertexes order is invalid");
                        alert.showAndWait();
                        //romper ciclo
                        break;
                    }
                    else graph.addEdge(v.getKey(),v2.getKey(), calculateAttenuation(v, v2, distance));
                }
                initTF.setText(null);
                finalTF.setText(null);
                gc.setStroke(Color.RED);
                gc.setLineWidth(1);
                gc.strokeLine(v.getX()+10, v.getY()+10, v2.getX(), v2.getY()+10);
            }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setContentText("The Edge was created.");
        alert.showAndWait();

    }

    @FXML
    void modify(ActionEvent event) {
        int k = graph.searchVertex(valueTF.getText());
        if(k != -1){
            System.out.println(graph.getVertices().get(k).getValue().getValue().getIpAddress());
            Device d = new Device(nvalueTF.getText(), Double.parseDouble(nbandTF.getText()), nNameTF.getText());
            graph.getVertices().get(k).getValue().setValue(d);
            graph.getVertices().get(k).setKey(nvalueTF.getText());
            System.out.println(graph.getVertices().get(k).getValue().getValue().getIpAddress());
            gc.setFill(Color.WHITE);
            gc.fillRect(graph.getVertices().get(k).getX(), graph.getVertices().get(k).getY()-20, 20, 20);
            gc.setFill(Color.BLACK);
            gc.fillText(nvalueTF.getText(), graph.getVertices().get(k).getX()+5, graph.getVertices().get(k).getY()-5);
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Confirmation");
            alert.setContentText("Device successfully modified.");
            alert.showAndWait();
            nNameTF.setText(null);
            valueTF.setText(null);
            nvalueTF.setText(null);
            nbandTF.setText(null);
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Invalid ip");
            alert.showAndWait();
            nNameTF.setText(null);
            valueTF.setText(null);
            nvalueTF.setText(null);
            nbandTF.setText(null);
        }
    }

    @FXML
    void remove(ActionEvent event) {
        int k = graph.searchVertex(valueTF.getText());
        if(k != -1){
            System.out.println(graph.getVertices().get(k).getValue().getValue().getIpAddress());
            gc.setFill(Color.WHITE);
            gc.fillRect(graph.getVertices().get(k).getX(), graph.getVertices().get(k).getY()-20, 20, 20);
            gc.fillOval(graph.getVertices().get(k).getX(), graph.getVertices().get(k).getY(),23, 23);
            graph.getVertices().remove(k);
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Confirmation");
            alert.setContentText("Device successfully Removed.");
            alert.showAndWait();
            nNameTF.setText(null);
            valueTF.setText(null);
            nvalueTF.setText(null);
            nbandTF.setText(null);
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Invalid ip");
            alert.showAndWait();
            nNameTF.setText(null);
            valueTF.setText(null);
            nvalueTF.setText(null);
            nbandTF.setText(null);
        }
    }
    @FXML
    void removeEdge(ActionEvent event) {
        String init = initTF.getText();
        String fin = finalTF.getText();
        for (int i = 0; i < counter; i++){
            Vertex1<Device, String> v = graph.getVertices().get(graph.searchVertex(init)).getValue();
            Vertex1<Device, String> v2 = graph.getVertices().get(graph.searchVertex(fin)).getValue();
            if(v == null || v2 == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("The Vertex does not exist in the notDirected Graph");
                alert.showAndWait();
                //romper ciclo
                break;
            }
            if(v != null && v2 != null) graph.removeEdge(v.getKey(),v2.getKey());
            initTF.setText(null);
            finalTF.setText(null);
            gc.setFill(Color.WHITE);
            gc.fillRect(v.getX()+10, v.getY()+10, v2.getX()-10, v2.getY()+10);
            for (int j = 0; j < counter; j++){
                gc.setFill(Color.BLACK);
                double x = graph.getVertices().get(j).getX();
                double y = graph.getVertices().get(j).getY();
                gc.fillOval(x, y, 20, 20);
                gc.fillText(graph.getVertices().get(j).getKey(), graph.getVertices().get(j).getX()+5, graph.getVertices().get(j).getY()-5);
            }
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Alert");
        alert.setContentText("The Edge was removed.");
        alert.showAndWait();
    }

    public double calculateAttenuation(Vertex1<Device, String> initialNode, Vertex1<Device, String> destinationNode, double distance){
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
                Device n = new Device(keyTF.getText(), band, name);
                Vertex1 v = new Vertex1<>(n, keyTF.getText(), e.getX(), e.getY());
                graph.addVertex(key, v, e.getX(), e.getY());
                gc.setFill(Color.BLACK);
                gc.fillOval(e.getX(), e.getY(), 20, 20);
                gc.fillText(keyTF.getText(), e.getX()+5, e.getY()-5);
                counter++;
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("A vertex was added.");
                alert.showAndWait();
                nameTF.setText(null);
                keyTF.setText(null);
                bandTF.setText(null);

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
