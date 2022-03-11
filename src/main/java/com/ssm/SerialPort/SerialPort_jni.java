package com.ssm.SerialPort;

public class SerialPort_jni
{
	public native static String download(String a,String b,String c);
	public native static int getSerialPortList(String[] portlist);
	public native static int readSerialDate(byte[] date,int fd);
	public native static int openSerialPort(String port);
	public native static int closeSerialPort(int fd);

	static{
		
		System.loadLibrary("serialC");
	}
}
