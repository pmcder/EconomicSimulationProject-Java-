/**MET CS 622
 * Assignment 4
 * class RunAgain
 * by Iryna Chervachidze
 * June 8, 2020
 */

package guiPackage;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class RunAgain extends Application {

	@Override
	public void start(Stage primaryStage) {
		Button btn1 = new Button("Run again");
		Button btn2 = new Button("Quit");
		btn1.setMinWidth(80);
		btn2.setMinWidth(80);
		
		//Add event handler for the "Run Again" button
		btn1.setOnMouseClicked(e -> {
			
			//Add another stage with a message and OK button
			String lastMessage = "You chose to run this simulation for another 30 days";
			Text lastText = new Text(50, 50, lastMessage);
			lastText.setFont(Font.font("Courier", FontWeight.BOLD, 17));
			Button OKbtn = new Button("OK");
			OKbtn.setMinWidth(80);
			
			HBox hbox = new HBox(30);
			hbox.getChildren().add(OKbtn);
			hbox.setAlignment(Pos.CENTER);
			
			SplitPane splitPane = new SplitPane();
			splitPane.getItems().add(lastText);
			splitPane.getItems().add(hbox);
			splitPane.setOrientation(Orientation.VERTICAL);
			
						
			Scene lastScene = new Scene(splitPane, 500, 300);
			Stage lastStage = new Stage();
			lastStage.setScene(lastScene);
			lastStage.show();
			
			//Add an event handler to the OK button
			OKbtn.setOnMouseClicked(b -> {
				lastStage.close();
				Platform.exit();
				//System.exit(0);
			});
			
		});
		
		//Add event handler for the "Quit" button
		btn2.setOnMouseClicked(e -> {
			
			//Add another stage with a message and OK button
			String lastMessage = "Quitting simulation...";
			Text lastText = new Text(50, 50, lastMessage);
			lastText.setFont(Font.font("Courier", FontWeight.BOLD, 17));
			Button OKbtn = new Button("OK");
			OKbtn.setMinWidth(80);
			
			HBox hbox = new HBox(30);
			hbox.getChildren().add(OKbtn);
			hbox.setAlignment(Pos.CENTER);
			
			SplitPane splitPane = new SplitPane();
			splitPane.getItems().add(lastText);
			splitPane.getItems().add(hbox);
			splitPane.setOrientation(Orientation.VERTICAL);
			
			//Add an event handler to the OK button	
			OKbtn.setOnMouseClicked(b -> {
				Platform.exit();
				System.exit(0);
			});
			
			
			Scene lastScene = new Scene(splitPane, 500, 300);
			Stage lastStage = new Stage();
			lastStage.setScene(lastScene);
			lastStage.show();

		});


		
		String message = "Would you like to run simulation for another 30 days?";
		Text text = new Text(60, 60, message);
		text.setFont(Font.font("Courier", FontWeight.BOLD, 15));
		
		HBox hbox = new HBox(30);
		hbox.getChildren().addAll(btn1, btn2);
		hbox.setAlignment(Pos.CENTER);
		
		SplitPane splitPane = new SplitPane();
		splitPane.getItems().add(text);
		splitPane.getItems().add(hbox);
		splitPane.setOrientation(Orientation.VERTICAL);
		
		Scene scene = new Scene(splitPane, 500, 300);
		primaryStage.setScene(scene);
		primaryStage.setOnCloseRequest(e -> Platform.exit());
		primaryStage.show();
	}
		
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
