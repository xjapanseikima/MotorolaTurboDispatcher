package priv.chunyu.moto.DeviceOperationCommands;

import java.io.IOException;
import java.util.logging.Logger;

import priv.chunyu.moto.DeviceDiscoveryCommands.ESN;
import priv.chunyu.moto.XCMP.XCMPsocket;

public class Stun extends XCMPsocket{
	public Stun() throws IOException, InterruptedException {
		super();
	}
	static byte id[] = { (byte) 0x00, (byte) 0xFA };
	static String strClassName = Stun.class.getName();
	static Logger logger = Logger.getLogger(strClassName);
	public static void request(int RadioID) throws IOException, InterruptedException {
		synchronized (lock) {
			checkflag();
			byte data[] = { (byte) 0x00, (byte) 0x17, (byte) 0X00, (byte) 0x0B, (byte) 0X01, (byte) flag,
					(byte) RadioAddress[0], (byte) RadioAddress[1], (byte) MasterAddress[0], (byte) MasterAddress[1],
					(byte) id[0], (byte) id[1], (byte) 0X00, (byte) 0X0B,
					(byte) 0X04, (byte) 0X1C,
					(byte) 0X01, //01 關閉 02 打開 
					(byte) 0X01,
					(byte) 0x01,(byte) 0x03,(byte) 0x00,(byte) 0x00,(byte) RadioID,(byte) 0x00,(byte) 0x00 };
			output.write(data);
			logger.info("Sending Stun Request");
			lock.wait(500);
		}
		
	}
	
}
