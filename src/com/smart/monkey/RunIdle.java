package com.smart.monkey;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.android.chimpchat.adb.AdbBackend;
import com.android.chimpchat.adb.AdbChimpDevice;

public class RunIdle {
	private static Logger logger = LogManager.getLogger(runSmartmonkey.class);
	private static AdbChimpDevice device;
	private static AdbBackend adb;
	public static void main(String[] args) {
		if(args.length == 2){
			String basePath = args[0];
			String deviceId = args[1];
			System.out.println("Run: "+basePath+"/settings.");
			System.out.println("DeviceId: "+deviceId);
			run(basePath,deviceId);
		}else{
			System.out.println("Need two arguments - basePath  - deviceCode");
		}
		
	}
	private static void run(String basePath, String deviceId) {
		// TODO Auto-generated method stub
		
	}

}
