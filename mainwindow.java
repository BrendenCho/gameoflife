package gameoflife;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class mainwindow {
    private VBox root = new VBox();
    private Pane pane = new Pane();
    private Scene scene = new Scene(root, 1000, 1000);
    private Stage stage = new Stage();
    private HBox box = new HBox();
    private Button clearBttn = new Button("Clear");
    private Button goBttn = new Button("Go");
    private final int WINDOW_HEIGHT = 1000;
    private final int WINDOW_WIDTH = 1000;
    private mainlogic ml;

    public mainwindow() {
        root.setAlignment(Pos.TOP_CENTER);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(5, 5, 5, 5));

        box.getChildren().add(clearBttn);
        box.getChildren().add(goBttn);
        box.setSpacing(5);

        root.getChildren().add(box);
        root.getChildren().add(pane);
        stage.setTitle("Brenden Cho - Game of Life");
        stage.setScene(scene);
        stage.setWidth(WINDOW_WIDTH + 10);
        stage.setHeight(WINDOW_HEIGHT + 70);
        stage.setResizable(false);
       
        stage.setOnCloseRequest(e -> {
            ml.setBreakout(true);
        });
       
        stage.show();
        
    }

    public void setClearBttn(){
        clearBttn.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                ml.clear();
            }
        });

    }

    public void setGoBttn(){
        goBttn.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e){
                ml.setGo(!ml.getGo());    
            }
        });   
    }


    public Pane getPane() {
        return pane;
    }

    public int getHeight() {
        return WINDOW_HEIGHT;
    }

    public int getWidth() {
        return WINDOW_WIDTH;
    }

    public Scene getScene(){
        return scene;
    }

    public void setML(mainlogic ml){
        this.ml = ml;
        setClearBttn();
        setGoBttn();
    }

    public Button getClearBttn(){
        return clearBttn;
    }

    public Button getGoBttn(){
        return goBttn;
    }

}