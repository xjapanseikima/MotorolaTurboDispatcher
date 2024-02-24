package priv.chunyu.Controller;

import java.io.File;
import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class MapViewController {
	@FXML
	private WebView webView;
	private static WebEngine engine;

	@FXML

	private void initialize() {
		engine = webView.getEngine();
		engine.setJavaScriptEnabled(true);
		File f = new File("map/map.html");
		engine.load(f.toURI().toString());
	}

	public static void add(String id, String latlng) {
		String lat = latlng.split(",")[0];
		String lng = latlng.split(",")[1];
		engine.executeScript("addmarker("+id +"," + lat + "," + lng+ ")");
	}
}
