package com.idle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.android.chimpchat.adb.AdbChimpDevice;
import com.lmax.disruptor.EventHandler;

public class LocationConsumer implements EventHandler<Location> {
	private  AdbChimpDevice device;
	private static Logger logger = LogManager.getLogger(LocationMain.class);  

	public LocationConsumer(AdbChimpDevice device){
		this.device=device;
	}
	@Override
	public void onEvent(Location location, long sequence, boolean endOfBatch) throws Exception {
		// TODO Auto-generated method stub
		if(device!=null){
			logger.debug("click point is :"+location.getX()+"-|-"+location.getY());
			device.touch(location.getX(), location.getY(),com.android.chimpchat.core.TouchPressType.DOWN_AND_UP);
			}
		else
			System.out.println("click screen: x-"+location.getX()+" y-"+location.getY());
	}
}
