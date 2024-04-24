package priv.chunyu.moto.ImmiediateLocationRequest;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.logging.Logger;

import priv.chunyu.moto.DataProcesss.DataProcess;
import priv.chunyu.moto.LocationRequestResponseProtocol.LRRPsocket;

public class ImmediateLocation extends LRRPsocket {

	public ImmediateLocation() throws IOException, InterruptedException {
		super();
	}

	static String strClassName = ImmediateLocation.class.getName();
	static Logger logger = Logger.getLogger(strClassName);
	static String latlng;

	public static String request(int RadioID) throws IOException, InterruptedException {
		synchronized (lock) {
			//latlng="NO LATLON";
			byte data[] = { (byte) 0x05, (byte) 0x08, (byte) 0x022, (byte) 0x04, (byte) 0x00, (byte) 0x00, (byte) 0x00,
					(byte) 0x01, (byte) 0x51, (byte) 0x62 };
			SU = InetAddress.getByName("12.0.0."+RadioID);
			DatagramPacket DpSend = new DatagramPacket(data, data.length, SU, 4001);
			LS_socket.send(DpSend);
			lock.wait(500);
		}
		return latlng;
	}

	public static void report(byte[] data) throws IOException, InterruptedException {
		synchronized (lock) {
		double Lat;
		double Lon;
		logger.info("ImmediateLocation Report");
		final long q = 4294967295L;// 0xfffffff
		Lat = DataProcess.ReadLatLongData(15, 18, data) * 180.0 / q;
		Lon = DataProcess.ReadLatLongData(19, 22, data) * 360.0 / q;
		latlng=Lat+","+Lon;
		lock.notify();
		}
	}
}