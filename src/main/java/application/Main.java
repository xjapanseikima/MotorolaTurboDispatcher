package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import priv.chunyu.Controller.MyController;
import priv.chunyu.moto.ARS.ARS;
import priv.chunyu.moto.LocationRequestResponseProtocol.LRRPmsg;
import priv.chunyu.moto.xnl.XNLsocket;

import java.io.IOException;

public class Main extends Application {
	public static MyController controllerhandle;
	@Override
	public void start(Stage primaryStage) {
		try {
			// Read file fxml and draw interface.
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/Mainpage.fxml"));
			Parent root = loader.load();
			controllerhandle=(MyController)loader.getController();
			primaryStage.setTitle("Motorola");
			primaryStage.setScene(new Scene(root));
			primaryStage.show();
			Start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void stop() throws IOException {
		ARS.DeReg();
	}

	private void Start() throws IOException, InterruptedException {
		Thread Xnl_Connection = new Thread(new XNLsocket());
		Xnl_Connection.start();
		Thread Location_Connection = new Thread(new LRRPmsg());
		Location_Connection.start();
		Thread ARS_connection = new Thread(new ARS());
		ARS_connection.start();
	}
	public static void main(String[] args) throws IOException, InterruptedException {
		launch(args);
	}
}
