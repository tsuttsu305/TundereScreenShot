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
        //FXMLのロード先を指定してロード用インスタンスを作成
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("FullScreen.fxml"));

        //FXMLロード
        Parent parent = loader.load();

        //コントローラのインスタンスを取得しStageをsetする
        FullScreenController controller = loader.getController();
        controller.setStage(primaryStage);

        //Scene作成
        Scene scene = new Scene(parent);
        //塗りつぶしなし
        scene.setFill(null);
        //マウスカーソルは十字に
        scene.setCursor(Cursor.CROSSHAIR);
        //Escキーが押されたらプログラムを終了するように設定
        scene.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                primaryStage.close();
            }
        });

        //ウィンドウ枠を透明に設定
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setScene(scene);
        primaryStage.setTitle("TundereScreenShot");
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.keyCombination("Esc"));
        primaryStage.setFullScreenExitHint("Escでキャプチャを中止");
        //ウィンドウを表示
        primaryStage.show();
    }
}
