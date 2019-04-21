package sample;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {
    private Desktop desktop = Desktop.getDesktop();
    @Override
    public void start(Stage primaryStage) throws Exception{
        final FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF", "*.pdf"),
                                                 new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                                                 new FileChooser.ExtensionFilter("PNG", "*.png"));
        Label label1 = new Label("Введите значение: ");
        Label label2 = new Label("День недели: ");
        TextField firstField = new TextField();
        TextField secondField = new TextField();
        Button OpenFile = new Button("Открыть файл");
        Button AddImage = new Button("Добавить изображение");
        GridPane root = new GridPane();
        root.setPadding(new Insets(10,30,10,10));
        ColumnConstraints column1 = new ColumnConstraints(150,150,Double.MAX_VALUE);
        column1.setHgrow(Priority.ALWAYS);
        ColumnConstraints column2 = new ColumnConstraints(150,150,Double.MAX_VALUE);
        column2.setHgrow(Priority.ALWAYS);
        root.getColumnConstraints().add(column2);
        ColumnConstraints column3 = new ColumnConstraints(120,120,Double.MAX_VALUE);
        column3.setHgrow(Priority.ALWAYS);

        root.getColumnConstraints().add(column3);
        root.getRowConstraints().add(new RowConstraints(40,40,Double.MAX_VALUE,Priority.ALWAYS, VPos.CENTER,false));
        root.getRowConstraints().add(new RowConstraints(40,40,Double.MAX_VALUE,Priority.ALWAYS,VPos.CENTER,false));
        root.getRowConstraints().add(new RowConstraints(40,40,Double.MAX_VALUE,Priority.ALWAYS,VPos.CENTER,false));

        root.add(label1, 0, 0);
        root.add(label2,0 , 2);
        root.add(OpenFile, 0,1);
        root.add(AddImage, 0,2);

        root.add(firstField, 1, 0);
        root.add(secondField, 1, 2);

        Scene scene = new Scene(root, 350, 160);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Курсовая работа");
        primaryStage.show();

        OpenFile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                File file = fileChooser.showOpenDialog(primaryStage);
                if (file != null) {
                    Brochure.Split(file.getAbsolutePath());
                    label2.setText("Файл открыт");
                }
            }
        });

        AddImage.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                File file = fileChooser.showOpenDialog(primaryStage);
                if (file != null) {
                    Brochure.AddImage(file.getAbsolutePath());
                }
            }
        });
    }

    //private void openFile(File file) {
        //try {
            //desktop.open(file);
        //} catch (IOException ex) {
            //Logger.getLogger(
                    //Main.class.getName()).log(
                    //Level.SEVERE, null, ex
            //);
        //}
    //}

    public static void main(String[] args) {
        launch(args);
    }
}
