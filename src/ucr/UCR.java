/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ucr;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 *
 * @author Harsh Kumar Singh
 */
public class UCR extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
	Parent root = FXMLLoader.load(getClass().getResource("loginScreen.fxml"));
	Scene scene = new Scene(root, 300, 470);
	/*   primaryStage.addEventHandler(KeyEvent.KEY_PRESSED,
                event -> {if(event.getCode().equals(KeyCode.ENTER));
        });*/
	primaryStage.setTitle("μCR");
	primaryStage.setScene(scene);
	primaryStage.getIcons().add(new Image("ucrIcon.jpg"));
	Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
	primaryStage.setX((primScreenBounds.getWidth() - 300) / 2);
	primaryStage.setY((primScreenBounds.getHeight() - 520) / 2);

	primaryStage.show();
    }

    public static void main(String[] args) {
	launch(args);
    }
}
