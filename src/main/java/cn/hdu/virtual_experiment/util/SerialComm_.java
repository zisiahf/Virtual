package cn.hdu.virtual_experiment.util;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

import static java.lang.Thread.sleep;

public class SerialComm_ {
	
	private InputStream in;
	
	private CommPort commPort;

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
			
			commPort = portIdentifier.open(this.getClass().getName(), 2000);

			if (commPort instanceof SerialPort) {
				
				serialPort = (SerialPort) commPort;

				serialPort.setSerialPortParams(baudRate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
						SerialPort.PARITY_NONE);
				
				if(in != null){
					in = null;
				}
				in = serialPort.getInputStream();
			} else {
				System.out.println("Error: Only serial ports are handled by this example.");
			}
		}
	}

	//关闭串口
	public void closePort() {
		try {
			//stringBuilder.delete(0, stringBuilder.length());
			//serialPort.notifyOnDataAvailable(false);
			//serialPort.removeEventListener();
			commPort.close();
			serialPort.close();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
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
		String res = null;
		int start = 0;
		int end = 0;
		readSerial(true);
		if(stringBuilder == null || stringBuilder.length() < length){
			return;
		}
		for(int i = 2;i < stringBuilder.length();i++){
			if(stringBuilder.charAt(i) == '7' && stringBuilder.charAt(i + 1) == 'e'){
				if(i - start < 40){//40
					stringBuilder.delete(start, i);
					return;
				}
				end = i+1;
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

	public String readSerial3() {
		byte[] buffer = new byte[15];
		String result = "";
		int len = -1;
		String v = "";
		try {
			sleep(2000);
			
			len = in.read(buffer);
			if(len < 15){
				return "";
			}
			double value = (int) ((buffer[11] & 0xFF) | ((buffer[10] & 0xFF) << 8));
			value = value * 0.01 - 40.1;
			result = Double.toString(value).substring(0, 5);

			v = byte2hex(new String(buffer, 0, 15, "ISO-8859-1").getBytes("ISO-8859-1"),true);
			System.out.print("result=" +result);
			System.out.print("v=" +v);
			
			System.out.println(v.length());
			System.out.println(result.length());
				
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return v + "-" + result;
	}


	public String readSerial4(int i, int j) {
		byte[] buffer = new byte[18];
		int len = -1;
		String v = "";
		try {
			len = in.read(buffer);
			while(len < 18){
				len += in.read(buffer,len,18-len);
				sleep(2000);
			}
			v = byte2hex(new String(buffer, 0, 18, "ISO-8859-1").getBytes("ISO-8859-1"),true);
			System.out.println(v);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return v;
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
		try {
			SerialComm_ serialComm1 = new SerialComm_();
			//SerialComm serialComm2 = new SerialComm();
			serialComm1.connect("/dev/ttyUSB1", 115200);
			//serialComm2.connect("/dev/ttyUSB2", 115200);
			for(int i = 0;i < 10;i++){
				//.getSerialData(45);
				serialComm1.getSerialData(45);
				sleep(1000);
				System.out.println("serialComm1数据为"+serialComm1.getData());
				//System.out.println("serialComm2数据为"+serialComm2.getData());
			}
			serialComm1.closePort();
			//serialComm2.closePort();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}