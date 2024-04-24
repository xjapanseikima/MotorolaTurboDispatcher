package priv.chunyu.moto.ARS;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Logger;

import application.Main;
import javafx.scene.control.TextArea;
import priv.chunyu.moto.DataProcesss.DataProcess;

public class ARS implements Runnable {
	byte ack[] = { (byte) 0x00, (byte) 0x02, (byte) 0xBF, (byte) 0x01 };
	static byte DeReg[] = { (byte) 0x00, (byte) 0x01, (byte) 0x31 };
	// byte buf[] = {(byte)0x00,(byte)0x02,(byte)0xBF,(byte)0x01};
	static String strClassName = ARS.class.getName();
	static Logger logger = Logger.getLogger(strClassName);
	byte[] receiveData = new byte[15];// assign buffer for the receive data
	InetAddress PC;
	DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length, PC, 4005);
	static DatagramPacket sendPacket;
	static DatagramSocket ARSsocket;
    TextArea mytextArea;
    public static String RadioID;
	@Override
	public void run() {
	
		try {
			ARSsocket = new DatagramSocket(4005);
			InetAddress Radio;// send
			PC = InetAddress.getByName("192.168.10.2");// receive	
			while (true) {
				ARSsocket.receive(receivePacket);
				logger.info("ARS receiving");
				// check message type
				if (receiveData[2] == (byte) 0xF0 && receiveData[3] == (byte) 0x20 || receiveData[3] == (byte) 0x40) {
					StringBuilder HexicmalData = DataProcess.ReadingData(receiveData);
					System.out.println(HexicmalData);
//					RadioID = DataProcess.hexToAscii(10, receiveData[4] + 12, HexicmalData.toString());
//					logger.info("Radio ID " + RadioID + " get connected");
					Radio = receivePacket.getAddress();
					Main.controllerhandle.userConnect(Radio.toString());
					sendPacket = new DatagramPacket(ack, ack.length, Radio, 4005);
					logger.info("ARS ack Sending");
					ARSsocket.send(sendPacket);
				}
				if (receiveData[1] == (byte) 0x01 && receiveData[2] == (byte) 0x31) {
					Radio = receivePacket.getAddress();
					logger.info(Radio + " get disconnected");
					Main.controllerhandle.userDisconnect(Radio.toString());
				}
			}
		} catch (SocketException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void DeReg() throws IOException {
		InetAddress AllRadio = InetAddress.getByName("255.0.0.1");
		sendPacket = new DatagramPacket(DeReg, DeReg.length, AllRadio, 4005);
		logger.info("DegRegistrate to all Radio");
		ARSsocket.send(sendPacket);
	}
}
