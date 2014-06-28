package net.tsuttsu305.tundere.screenshot;
/**
 * Created by tsuttsu305 on 2014/06/28.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class TundereScreenShot extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("FullScreen.fxml"));

        Parent parent = loader.load();

        FullScreenController controller = loader.getController();
        controller.setStage(primaryStage);

        Scene scene = new Scene(parent);
        scene.setFill(null);
        scene.setCursor(Cursor.CROSSHAIR);
        scene.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                primaryStage.close();
            }
        });

        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.setTitle("TundereScreenShot");
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.keyCombination("Esc"));
        primaryStage.setFullScreenExitHint("Escでキャプチャを中止");
        primaryStage.show();
    }
}
