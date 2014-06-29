package net.tsuttsu305.tundere.screenshot;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by tsuttsu305 on 2014/06/28.
 */
public class FullScreenController implements Initializable {
    private Stage stage;
    double x, y;
    Rectangle rec = new Rectangle();

    @FXML private Pane pane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //GUIのパネルを半透明に設定(白+アルファ値)
        pane.setStyle("-fx-background-color: rgba(255,255,255,0.01);");

        //選択枠表示四角形描画用インスタンス登録
        pane.getChildren().add(rec);

        //D&D中の処理を設定
        pane.setOnMouseDragged(new EventHandler<MouseEvent>() {
            double xt, yt;

            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() != MouseButton.PRIMARY) return;

                xt = event.getX();
                yt = event.getY();

                if (xt > x){
                    rec.setWidth(xt - x);
                } else {
                    rec.setWidth(x - xt);
                    rec.setX(xt);
                }

                if (yt > y){
                    rec.setHeight(yt - y);
                } else {
                    rec.setHeight(y - yt);
                    rec.setY(yt);
                }


                System.out.println("D&D now: " + event.getX() + ", " + event.getY());
            }
        });

        //クリックの動作を設定
        pane.setOnMousePressed(event -> {
            if (event.getButton() != MouseButton.PRIMARY) return;

            x = event.getX();
            y = event.getY();
            rec.setStroke(Color.RED);
            rec.setFill(null);
            rec.setX(x);
            rec.setY(y);
            rec.setVisible(true);

            System.out.println("D&D Enter: " + event.getX() + ", " + event.getY());
        });

        //クリックが終わった時の動作を設定
        pane.setOnMouseReleased(event -> {
            if (event.getButton() != MouseButton.PRIMARY) return;

            double sx = rec.getX(), sy = rec.getY(), ex = rec.getWidth() + rec.getX(), ey = rec.getHeight() + rec.getY();
            System.out.println(sx + ", " + sy + " -> " + ex + ", " + ey);

            if (sx != ex && sy != ey) {
                try {
                    //画面を撮影するため、ウィンドウは非表示にする
                    stage.hide();

                    //デスクトップ撮影用処理
                    BufferedImage bufferedImage = Camera.createDesktopCap((int) sx, (int) sy, (int) ex - (int) sx, (int) ey - (int) sy);
                    File f = File.createTempFile("ScreenShot", ".png");
                    ImageIO.write(bufferedImage, "png", f);

                    //Fileをクリップボードにコピーするための処理
                    //Systemのクリップボードを取得
                    Clipboard cp = Clipboard.getSystemClipboard();
                    ClipboardContent cc = new ClipboardContent();
                    ArrayList<File> af = new ArrayList<>();
                    af.add(f);
                    //クリップボードContentにFileリストを登録
                    cc.putFiles(af);
                    //クリップボードに反映
                    cp.setContent(cc);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    //プログラムを終了する
                    stage.close();
                }
            }

            rec.setVisible(false);
            rec.setX(0);
            rec.setY(0);
            rec.setWidth(0);
            rec.setHeight(0);


            System.out.println("D&D Exit: " + event.getX() + ", " + event.getY());
        });
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
