package gameoflife;

import javafx.application.Application;
import javafx.stage.Stage;

public class gameoflife extends Application{

public static void main(String[] args){
launch(args);
}

@Override
public void start(Stage primaryStage) throws Exception {
mainwindow mw = new mainwindow();
mainlogic ml = new mainlogic(mw);
mw.setML(ml);
ml.start();
}








    
}