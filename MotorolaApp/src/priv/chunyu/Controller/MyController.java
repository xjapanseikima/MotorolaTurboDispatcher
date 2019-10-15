package priv.chunyu.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.Map;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import priv.chunyu.moto.DeviceDiscoveryCommands.ESN;
import priv.chunyu.moto.DeviceDiscoveryCommands.MN;
import priv.chunyu.moto.DeviceDiscoveryCommands.RSSI;
import priv.chunyu.moto.DeviceDiscoveryCommands.SN;
import priv.chunyu.moto.DeviceOperationCommands.RemoteMonitor;
import priv.chunyu.moto.DeviceOperationCommands.Stun;
import priv.chunyu.moto.DeviceOperationCommands.UnStun;
import priv.chunyu.moto.ImmiediateLocationRequest.ImmediateLocation;
import priv.chunyu.moto.SignalingControlCommand.CallControl;
import priv.chunyu.moto.SignalingControlCommand.EmergencyControl;
import priv.chunyu.moto.VERINFO.RFBand;

public class MyController implements Initializable {

	@FXML
	private Button ESN_Button = new Button();
	@FXML
	private Button SN_Button = new Button();
	@FXML
	private Button Stun_Button = new Button();
	@FXML
	private Button Call_Button = new Button();
	@FXML
	private Button UnStun_Button = new Button();
	@FXML
	private Button EMGEREQ_Button = new Button();
	@FXML
	private Button MN_Button = new Button();
	@FXML
	private Button RSSI_Button = new Button();
	@FXML
	private Button IMR_Button = new Button();
	@FXML
	private Button RemotMonitor_Button = new Button();

	@FXML
	private TextField id_TextField = new TextField();
	@FXML
	public TextArea Text = new TextArea();
	String latlng;
	ArrayList<String> UserList=new ArrayList<String>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	public void ESN(ActionEvent event) throws IOException, InterruptedException {
		System.out.println("ESN Clicked!");
		Text.appendText(ESN.request() + "\n");
	}

	public void MN(ActionEvent event) throws IOException, InterruptedException {
		System.out.println("MN Clicked!");
		Text.appendText(MN.request() + "\n");
	}

	public void SN(ActionEvent event) throws IOException, InterruptedException {
		System.out.println("SN Clicked!");
		Text.appendText(SN.request() + "\n");
	}

	public void Stun(ActionEvent event) throws IOException, InterruptedException {
		System.out.println("SHUTDWN Clicked!");
		int RadioID = Integer.parseInt(id_TextField.getText());
		Stun.request(RadioID);
	}

	public void CALL(ActionEvent event) throws IOException, InterruptedException {
		System.out.println("Call Reqesut Clicked!");
		int RadioID = Integer.parseInt(id_TextField.getText());
		CallControl.request(RadioID);
	}

	public void EMG(ActionEvent event) throws IOException, InterruptedException {
		System.out.println("EMG Reqesut Clicked!");
		EmergencyControl.request();
	}

	public void UnStun(ActionEvent event) throws IOException, InterruptedException {
		System.out.println("UnStun Reqesut Clicked!");
		int RadioID = Integer.parseInt(id_TextField.getText());
		UnStun.request(RadioID);
	}

	public void RemoteMonitor(ActionEvent event) throws IOException, InterruptedException {
		System.out.println("RemoteMonitor Reqesut Clicked!");
		int RadioID = Integer.parseInt(id_TextField.getText());
		RemoteMonitor.request(RadioID);
	}

	public void RSSI(ActionEvent event) throws IOException, InterruptedException {
		System.out.println("RSSI Reqesut Clicked!");
		Text.appendText("-" + RSSI.request() + "\n");
	}

	public void IMR(ActionEvent event) throws IOException, InterruptedException {
		System.out.println("IMR Reqesut Clicked!");
		int RadioID = Integer.parseInt(id_TextField.getText());
		latlng = ImmediateLocation.request(RadioID);
		Text.appendText("LatLong " + latlng + "\n");
	}

	public void Map_Button(ActionEvent event) throws Exception {
		new Map();
	}

	public void add_Marker(ActionEvent event) throws Exception {
		int RadioID = Integer.parseInt(id_TextField.getText());
		MapViewController.add(Integer.toString(RadioID), latlng);
	}

	public void update(String x) throws Exception {
		int RadioID = Integer.parseInt(id_TextField.getText());
		MapViewController.add(Integer.toString(RadioID), latlng);
	}

	public void userConnect(String radioID) {
		Text.appendText(radioID + " is online!! :) \n");
		UserList.add(radioID);
	}
	public void userDisconnect(String radioID) {
		Text.appendText(radioID + " is offline!! :( \n");
		UserList.remove(radioID);
	}
	public void getUserList() {
		for(String str:UserList)  
			Text.appendText("Now online: "+ str+"\n") ;
	     }  
	public void RF_Band () throws IOException, InterruptedException {
		System.out.println("PowerLevel Clicked!");
		Text.appendText(RFBand.request() + "\n");
	}
	}


