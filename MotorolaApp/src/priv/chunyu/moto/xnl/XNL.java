package priv.chunyu.moto.xnl;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class XNL extends Thread {
	protected static Socket master;
	protected static DataOutputStream output;
	protected static DataInputStream input;

	protected static void setConnection() throws IOException {
		checkConnection();
		master = new Socket("192.168.10.1", 8002);// Pc set as master ,and setup connection;// Pc
		output = new DataOutputStream(master.getOutputStream());
		input = new DataInputStream(master.getInputStream());// data input
	}

	private static void checkConnection() {
		// master.connect(new InetSocketAddress("192.168.10.1", 8002), 1000);

	}
}
