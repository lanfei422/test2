package com.idle;

import com.lmax.disruptor.EventFactory;

public class LocationFactory implements EventFactory<Location> {

	@Override
	public Location newInstance() {
		// TODO Auto-generated method stub
		return new Location();
	}

}
