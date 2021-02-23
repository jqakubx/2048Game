package Simulation;

import GameComponents.Game2048;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Main extends Application {
    boolean victory = false;
    boolean lose = false;
    Game2048 game = new Game2048();
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("2048");
        VBox vBox = newVBox(game);
        Scene scene = new Scene(vBox, 400, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(!lose && !victory) {
                    switch (keyEvent.getCode()) {
                        case A:
                            vBox.getChildren().clear();
                            try {
                                game.map.moveCells(1);
                                vBox.getChildren().add(newVBox(game));
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            break;
                        case D:
                            vBox.getChildren().clear();
                            try {
                                game.map.moveCells(2);
                                vBox.getChildren().add(newVBox(game));
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            break;
                        case W:
                            vBox.getChildren().clear();
                            try {
                                game.map.moveRows(1);
                                vBox.getChildren().add(newVBox(game));
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            break;
                        case S:
                            vBox.getChildren().clear();
                            try {
                                game.map.moveRows(2);
                                vBox.getChildren().add(newVBox(game));
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            break;
                    }
                }
            }
        });
    }

    private Text newTextScore(Game2048 game) {
        String score = String.valueOf(game.getScore());
        Text text = new Text("Wynik: " + score);
        return text;
    }

    private VBox newVBox(Game2048 game) throws FileNotFoundException {
        victory = game.checkVictory();
        lose = game.checkLose();
        if(victory) {
            VBox textScore = new VBox();
            Text text = new Text("Gratulacje. \nTwój wynik to " + game.getScore());
            text.setFont(Font.font("Tahoma", 35));
            text.setFill(Color.RED);
            text.setStroke(Color.BLACK);
            text.setTextOrigin(VPos.BASELINE);
            textScore.setMinWidth(400);
            textScore.setMinHeight(500);
            textScore.setAlignment(Pos.CENTER);
            textScore.getChildren().add(text);
            return textScore;
        }
        else if(lose) {
            VBox textScore = new VBox();
            Text text = new Text("Niestety przegrałeś. \nTwój wynik to " + game.getScore());
            text.setFont(Font.font("Tahoma", 35));
            text.setFill(Color.RED);
            text.setStroke(Color.BLACK);
            text.setTextOrigin(VPos.BASELINE);
            textScore.setMinWidth(400);
            textScore.setMinHeight(500);
            textScore.setAlignment(Pos.CENTER);
            textScore.getChildren().add(text);
            return textScore;
        }
        else {
            int map[][] = game.map.getValues();
            VBox[] vbox = new VBox[4];

            ImageView imv;
            FileInputStream imageFileInput;
            Image image;
            for (int i = 0; i < 4; i++) {
                vbox[i] = new VBox();
                for (int j = 0; j < 4; j++) {
                    imageFileInput = new FileInputStream(chooseImage(map[i][j]));
                    image = new Image(imageFileInput);
                    imv = new ImageView();
                    imv.setImage(image);
                    vbox[i].getChildren().add(imv);
                }
            }
            HBox hbox = new HBox();
            HBox textScore = new HBox();
            VBox vboxRet = new VBox();
            Text text = newTextScore(game);
            text.setFont(Font.font("Tahoma", 30));
            text.setFill(Color.RED);
            text.setStroke(Color.BLACK);
            text.setTextOrigin(VPos.BASELINE);
            textScore.setMinWidth(400);
            textScore.setMinHeight(100);
            textScore.setAlignment(Pos.CENTER);
            textScore.getChildren().add(text);
            hbox.getChildren().add(vbox[0]);
            hbox.getChildren().add(vbox[1]);
            hbox.getChildren().add(vbox[2]);
            hbox.getChildren().add(vbox[3]);
            vboxRet.getChildren().add(textScore);
            vboxRet.getChildren().add(hbox);
            return vboxRet;
        }
    }

    private String chooseImage(int value) {
        if(value == 0) return "images/block0.png";
        if(value == 2) return "images/block2.png";
        if(value == 4) return "images/block4.png";
        if(value == 8) return "images/block8.png";
        if(value == 16) return "images/block16.png";
        if(value == 32) return "images/block32.png";
        if(value == 64) return "images/block64.png";
        if(value == 128) return "images/block128.png";
        if(value == 256) return "images/block256.png";
        if(value == 512) return "images/block512.png";
        if(value == 1024) return "images/block1024.png";
        if(value == 2048) return "images/block2048.png";
        return null;
    }
    public static void main(String[] args) {
        launch(args);
    }
}
