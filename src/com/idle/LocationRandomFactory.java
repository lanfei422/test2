package com.idle;

import com.lmax.disruptor.EventFactory;

public class LocationRandomFactory implements EventFactory<Location> {

	private int X;
	private int Y;
	
	public LocationRandomFactory(int x,int y){
		this.X=x;
		this.Y=y;
	}
	
	@Override
	public Location newInstance() {
		// TODO Auto-generated method stub
		return new Location(this.X,this.Y,0);
	}
	public int getX() {
		return X;
	}

	public void setX(int x) {
		X = x;
	}

	public int getY() {
		return Y;
	}

	public void setY(int y) {
		Y = y;
	}
}
