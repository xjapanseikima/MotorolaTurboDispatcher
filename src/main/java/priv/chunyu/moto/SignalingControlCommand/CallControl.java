package priv.chunyu.moto.SignalingControlCommand;


import java.io.IOException;
import java.util.logging.Logger;
import priv.chunyu.moto.DataProcesss.DataProcess;
import priv.chunyu.moto.XCMP.XCMPsocket;

/*
 * 1.Flag 要一樣 (data[5])  2.id要一樣(data[10]) 3.確保回正確的ack
 * 
*/
public class CallControl extends XCMPsocket {
	public CallControl() throws IOException, InterruptedException {
		super();
	}

	public static byte id[] = { (byte) 0x00, (byte) 0x0A };
	static String strClassName = CallControl.class.getName();
	static Logger logger = Logger.getLogger(strClassName);
	
	public static void request(int RadioID) throws IOException, InterruptedException {
		synchronized (lock) {
			checkflag();
			byte data[] = { (byte) 0x00, (byte) 0x17, (byte) 0x00, (byte) 0x0B, (byte) 0x01, (byte) flag,
					(byte) RadioAddress[0], (byte) RadioAddress[1], (byte) MasterAddress[0], (byte) MasterAddress[1],
					(byte) id[0], (byte) id[1], (byte) 0x00, (byte) 0x0B, (byte) 0x04, (byte) 0x1E, (byte) 0x01,(byte) 0x04, (byte) 0x01,(byte) 0x03,(byte) 0x00,(byte) 0x00,(byte) RadioID,(byte) 0x00,(byte) 0x00};
			output.write(data);
			logger.info("Sending Call Request");
			lock.wait(500);;
		}
	}

	public static void reply(byte[] data) throws IOException, InterruptedException {
		synchronized (lock) {
			HexicmalData = DataProcess.ReadingData(data);
			System.out.println("	" + HexicmalData);
			logger.info("CALL REPLY: ");
			lock.notify();
		}
	}
}