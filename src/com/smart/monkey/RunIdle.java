package com.smart.monkey;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.android.chimpchat.adb.AdbBackend;
import com.android.chimpchat.adb.AdbChimpDevice;

import common.ListDevices;

public class RunIdle {
	private static Logger logger = LogManager.getLogger(runSmartmonkey.class);
	private static AdbChimpDevice device;
	private static AdbBackend adb;

	public static void main(String[] args) {
		if (args.length == 2) {
			String basePath = args[0];
			String maxStep = args[1];
			
			ListDevices device = new  ListDevices();
			String deviceId = device.getdeviceId();
			
			System.out.println("Run: " + basePath );
			System.out.println("DeviceId: " + deviceId);
			System.out.println("Run max steps:" + maxStep);

			run(basePath, deviceId,maxStep);
		} else {
			System.out.println("Need two arguments - basePath  - deviceCode");
		}

	}

	private static void run(String basePath, String deviceId, String maxStep) {
		// TODO Auto-generated method stub
		runSmartmonkey.runIdle(basePath, deviceId, Integer.valueOf(maxStep));
	}

}
