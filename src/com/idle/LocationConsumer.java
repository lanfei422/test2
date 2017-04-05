package com.idle;

import com.android.chimpchat.adb.AdbChimpDevice;
import com.lmax.disruptor.EventHandler;

public class LocationConsumer implements EventHandler<Location> {
	private  AdbChimpDevice device;

	public LocationConsumer(AdbChimpDevice device){
		this.device=device;
	}
	@Override
	public void onEvent(Location location, long sequence, boolean endOfBatch) throws Exception {
		// TODO Auto-generated method stub
		device.touch(location.getX(), location.getY(),com.android.chimpchat.core.TouchPressType.DOWN_AND_UP);
	}
}
