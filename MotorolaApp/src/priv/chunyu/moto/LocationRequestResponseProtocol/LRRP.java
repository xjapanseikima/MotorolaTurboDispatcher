package priv.chunyu.moto.LocationRequestResponseProtocol;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class LRRP extends Thread {
	protected static DatagramSocket LS_socket; // Location Service
	protected static InetAddress LS_address;
	protected static InetAddress SU;// Subscriber Uniit

	public LRRP() throws SocketException, UnknownHostException {
		LS_socket = new DatagramSocket(4001);
		LS_address = InetAddress.getByName("192.168.10.2");
	}
}
