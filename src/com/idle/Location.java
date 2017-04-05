package com.idle;

import java.util.Random;

public class Location {
	private int x;
	private int y;
	
	public Location(){}
	public Location(int x,int y){
		this.x=x;
		this.y=y;
	}
	public Location(int xMax,int yMax,int z){
		Random r=new Random(System.currentTimeMillis());
		this.x=r.nextInt(xMax);
		this.y=r.nextInt(yMax);
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
}
