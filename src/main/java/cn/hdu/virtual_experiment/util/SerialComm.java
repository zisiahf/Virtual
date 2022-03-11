package cn.hdu.virtual_experiment.util;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static java.lang.Thread.sleep;

@Slf4j
public class SerialComm {
	
	private InputStream in;
	
	private SerialPort serialPort;
	
	private StringBuilder stringBuilder = new StringBuilder();

	private StringBuilder resData  = new StringBuilder();



	public StringBuilder getData() {
		return resData;
	}

	public StringBuilder getStringBuilder() {
		return stringBuilder;
	}

	//列出所有串口
	public static Set<String> listPorts()
	{
		Set<String> ports =new HashSet<String>();
		java.util.Enumeration<CommPortIdentifier> portEnum = CommPortIdentifier.getPortIdentifiers();
		while ( portEnum.hasMoreElements() )
		{
			CommPortIdentifier portIdentifier = portEnum.nextElement();
			if(portIdentifier.getName().indexOf("ttyUSB") != -1){
				ports.add(portIdentifier.getName().split("/")[2]);
			}
		}
		return ports;
	}

	//打开串口
	public void connect(String portName,int baudRate) throws Exception {

		CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
		
		if (portIdentifier.isCurrentlyOwned()) {
			
			System.out.println("Error: Port is currently in use");
			
		} else {

			CommPort commPort = portIdentifier.open(this.getClass().getName(), 2000);

			if (commPort instanceof SerialPort) {
				serialPort = (SerialPort) commPort;
				serialPort.setSerialPortParams(baudRate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
						SerialPort.PARITY_NONE);
				in = serialPort.getInputStream();
			} else {
				System.out.println("Error: Only serial ports are handled by this example.");
			}
		}
	}

	//关闭串口
	public void closePort() {
		if (serialPort != null) {
			serialPort.close();
			serialPort = null;
		}
	}
	//读串口id
	public void getID(int length,boolean flag){
		try{
			readSerial(false);
			if(stringBuilder != null && stringBuilder.length() >= length){
				return;
			}
			getID(length,flag);

		}catch (Exception e){
			e.printStackTrace();
		}
	}
	public void getSerialData(int length){
		int start = 0;
		int end = 0;
		readSerial(true);
		if(stringBuilder == null || stringBuilder.length() < 60){
			return;
		}
		for(int i = 2;i < stringBuilder.length();i++){
			if(stringBuilder.charAt(i) == '7' && stringBuilder.charAt(i + 1) == 'e'){
				if(i - start < 40){//40
					stringBuilder.delete(start, i);
					return;
				}
				end = i+2;
				break;
			}
		}
		String d  =  stringBuilder.toString().trim().substring(start, end)+ "\r\n";
		stringBuilder.delete(start, end+1);
		resData.append(d);
	}

	//flag 表示是否使用替换
	public void readSerial(boolean flag) {
		byte[] buffer = new byte[40];
		int len = -1;
		String v = null;
		
		try {
			if((len = in.read(buffer)) == -1){
				return;
			}
			v = byte2hex(new String(buffer, 0, len, "ISO-8859-1").getBytes("ISO-8859-1"),flag);
			if(v != null && !v.equals("")){
				stringBuilder.append(v);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String byte2hex(byte[] buffer,boolean flag) {
		String h = "";

		for (int i = 0; i < buffer.length; i++) {
			// System.out.println(buffer.length);
			String temp = Integer.toHexString(buffer[i] & 0xFF);
			if (flag && temp.equals("7d") && i+1 < buffer.length) {
				i= i+1;
				if (Integer.toHexString(buffer[i] & 0xFF).equals("5d")) {
					//temp = "7d";
					temp = "yy";
				} else if (Integer.toHexString(buffer[i] & 0xFF).equals("5e")) {
					//temp = "7e";
					temp = "xx";
				}
			}
			if (temp.length() == 1) {
				temp = "0" + temp;
			}
			h = h + " " + temp;
		}
		return h;
	}

	public static void main(String[] args) {
//		try {
//			SerialComm serialComm1 = new SerialComm();
//			//SerialComm serialComm2 = new SerialComm();
//			serialComm1.connect("/dev/ttyUSB0", 115200);
//			//serialComm2.connect("/dev/ttyUSB2", 115200);
//			for(int i = 0;i < 10;i++){
//				serialComm1.getSerialData(45);
//				sleep(1000);
//
//				System.out.println("serialComm1数据为"+serialComm1.getData());
//				if(serialComm1.getData().length() > 0){
//					String a = serialComm1.getData().substring(serialComm1.getData().length()-16,serialComm1.getData().length()-14);
//					System.out.println("a"+a);
//					String b = serialComm1.getData().substring(serialComm1.getData().length()-13,serialComm1.getData().length()-11);
//					System.out.println("b"+b);
//					double value = (int) ((Integer.valueOf("b",16) | (Integer.valueOf("a",16) << 8)));
//					value = value * 0.01 - 40.1;
//					System.out.println("value"+value);
//				}
//
//				//System.out.println("长度："+serialComm1.getData().length());
//				//System.out.println("serialComm2数据为"+serialComm2.getData());
//			}
//			serialComm1.closePort();
//			//serialComm2.closePort();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

	}
}