package priv.chunyu.moto.SignalingControlCommand;

import java.io.IOException;
import java.util.logging.Logger;

import priv.chunyu.moto.XCMP.XCMPsocket;

public class EmergencyControl extends XCMPsocket{

	public EmergencyControl() throws IOException, InterruptedException {
		super();
	}
	public static byte id[] = { (byte) 0x00, (byte) 0x0D };
	static String strClassName = CallControl.class.getName();
	static Logger logger = Logger.getLogger(strClassName);
	
	public static void request() throws IOException, InterruptedException {
		synchronized (lock) {
			checkflag();
			byte data[] = { (byte) 0x00, (byte) 0x17, (byte) 0x00, (byte) 0x0B, (byte) 0x01, (byte) flag,
					(byte) RadioAddress[0], (byte) RadioAddress[1], (byte) MasterAddress[0], (byte) MasterAddress[1],
					(byte) id[0], (byte) id[1], (byte) 0x00, (byte) 0x0B, (byte) 0x04, (byte) 0x13, (byte) 0x04,(byte) 0x10, (byte) 0x01,(byte) 0x03,(byte) 0x00,(byte) 0x00,(byte) 0x26,(byte) 0x00,(byte) 0x00};
			output.write(data);
			logger.info("Sending Call Request");
			lock.wait(500);;
		}
	}
}
