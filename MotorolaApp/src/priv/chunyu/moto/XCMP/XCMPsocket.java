package priv.chunyu.moto.XCMP;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import priv.chunyu.moto.DataProcesss.DataProcess;
import priv.chunyu.moto.xnl.XNL;

public class XCMPsocket extends XCMP {
	public static Socket master;
	public static DataOutputStream output;
	public static DataInputStream input;
	public static byte[] MasterAddress;
	public static byte[] RadioAddress;
	public static final Object lock = new Object();
	public static byte  flag = 0x01; //message Flag 
	public static StringBuilder HexicmalData = null;

	public XCMPsocket() throws IOException, InterruptedException {
		System.out.println("XCMP Connection... start");
		master = XNL.master;
		output = XNL.output;
		input = XNL.input;
		MasterAddress = DataProcess.get_MasterAddress(); // Pc address
		RadioAddress = DataProcess.get_RadioAddress(); // radio address
		receive_XCMP_DEVICE_INIT_STATUS();// g
		send_XCMP_DEVICE_INIT_STATUS();// h
		receive_XCMP_DEVICE_INIT_STATUS_COMPLETE(); // i) ack
		receive_RRCTRLBRDCST();
		XCMPMsg xcmpMsg = new XCMPMsg();
		xcmpMsg.run();
	}

	private void send_DATA_RRCTRLBRDCST_ACK_COMPLETE(byte ID1, byte ID2) throws IOException {
		byte data[] = { (byte) 0x00, (byte) 0x0C, (byte) 0X00, (byte) 0x0C, (byte) 0X01, // Protocol ID
				(byte) 0x02, // FLag
				(byte) RadioAddress[0], (byte) RadioAddress[1], (byte) MasterAddress[0], (byte) MasterAddress[1]// id?
				, (byte) ID1, (byte) ID2, // Transaction ID
				(byte) 0X00, (byte) 0x00,// payloadLength
		};
		output.write(data);
		System.out.println("ACK for Complete");
		System.out.println("XCMP Establish Complete\n");
	}

	private void receive_RRCTRLBRDCST() throws IOException {
		/* Remote Radio Control Broadcast */
		byte data[] = new byte[25];
		input.read(data, 0, data.length);
		StringBuilder HexicmalData = DataProcess.ReadingData(data);
		System.out.println("Receive Remote Radio Control Broadcast");
		System.out.println(HexicmalData);
		DataProcess.MessageStructure(data);
		send_DATA_RRCTRLBRDCST_ACK_COMPLETE(data[10], data[11]);
	}

	private void send_DATA_MSG_ACK_COMPLETE(byte ID1, byte ID2) throws IOException {
		byte DATA_MSG_ACK_COMPLETE[] = { (byte) 0x00, (byte) 0x0C, (byte) 0x00, (byte) 0x0C, (byte) 0x01, (byte) 0x01,
				(byte) RadioAddress[0], (byte) RadioAddress[1], (byte) MasterAddress[0], (byte) MasterAddress[1],
				(byte) ID1, (byte) ID2, (byte) 0x00, (byte) 0x00 };
		output.write(DATA_MSG_ACK_COMPLETE);
		System.out.println("DATA_MSG_ACK_COMPLETE");
		System.out.println("XCMP initialization complete");
	}

	private void receive_DATA_MSG_ACK() throws IOException {
		byte data[] = new byte[14];
		input.read(data, 0, data.length);
		StringBuilder HexicmalData = DataProcess.ReadingData(data);
		System.out.println("Receive DATA_MSG_ACK");
		System.out.println(HexicmalData);

	}

	private void receive_XCMP_DEVICE_INIT_STATUS_COMPLETE() throws IOException {
		byte data[] = new byte[21];
		input.read(data, 0, data.length);
		StringBuilder HexicmalData = DataProcess.ReadingData(data);
		System.out.println("Receive XCMP_DEVICE_INIT_STATUS_COMPLETE");
		System.out.println(HexicmalData);
		DataProcess.MessageStructure(data);
		send_DATA_MSG_ACK_COMPLETE(data[10], data[11]);
	}

	private void send_XCMP_DEVICE_INIT_STATUS() throws IOException {
		byte[] XCMP_DEVICE_INIT_STATUS = { (byte) 0x00, (byte) 0x1B, (byte) 0x00, (byte) 0x0B, (byte) 0x01, (byte) 0x00,
				(byte) 0x00, (byte) 0x00, (byte) MasterAddress[0], (byte) MasterAddress[1], (byte) 0x00, (byte) 0x00,
				(byte) 0x00, (byte) 0x0F, (byte) 0xB4, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x01,
				(byte) 0x00, (byte) 0x0A, (byte) 0x00, (byte) 0x00, (byte) 0x04, (byte) 0x00, (byte) 0x00, (byte) 0x02,
				(byte) 0xFF };
		output.write(XCMP_DEVICE_INIT_STATUS);
		System.out.println("Send XCMP_DEVICE_INIT_STATUS");
		receive_DATA_MSG_ACK();
	}

	private void send_DATA_MSG_ACK(byte ID1, byte ID2) throws IOException {
		byte[] DATA_MSG_ACK = { (byte) 0x00, (byte) 0x0C, (byte) 0x00, (byte) 0x0C, (byte) 0x01, (byte) 0x00,
				(byte) RadioAddress[0], (byte) RadioAddress[1], (byte) MasterAddress[0], (byte) MasterAddress[1],
				(byte) ID1, (byte) ID2, (byte) 0x00, (byte) 0x00 };
		System.out.println("Send DATA_MSG_ACK");
		output.write(DATA_MSG_ACK);
	}

	private void receive_XCMP_DEVICE_INIT_STATUS() throws IOException {
		byte data[] = new byte[41];
		input.read(data, 0, data.length);
		StringBuilder HexicmalData = DataProcess.ReadingData(data);
		System.out.println("Receive XCMP_DEVICE_INIT_STATUS");
		System.out.println(HexicmalData);
		send_DATA_MSG_ACK(data[10], data[11]);// gsend
	}

	public static void checkflag() {
		if (flag == (byte) 0x07) {
			flag = (byte) 0x00;
		} else {
			flag = (byte) (flag + 0x01);
		}
	}// if flag >0x07 then reset it to 0, check every XCMP message
}
