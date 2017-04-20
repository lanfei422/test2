package com.idle;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;

public class LocationRandomPublisher implements Runnable {
	private static EventFactory<Location> factory;

	public static RingBuffer<Location> rb;

	private boolean flag=false;
	
	public LocationRandomPublisher(RingBuffer<Location> rb,int x,int y){
		this.rb=rb;

		factory=new LocationRandomFactory(x,y);
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(!flag){
			addLocation();
			try {
				Thread.sleep(2000L);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				flag=true;
			}
		}
	}
	
	public static EventFactory<Location> getFactory() {
		return factory;
	}
	public static void setFactory(EventFactory<Location> factory) {
		LocationRandomPublisher.factory = factory;
	}
	
	private void addLocation(){
		if(hasCapacity()){
			System.out.println("disruptor's capacity belows to 10%");
		}else{
			long seq=rb.next();
			Location loc=rb.get(seq);

			Location tmpLoc=factory.newInstance();
			loc.setX(tmpLoc.getX());
			loc.setY(tmpLoc.getY());
			
			rb.publish(seq);
		}
	}
	
	public  void stop(){
		this.flag=true;
	}
	
	private static boolean hasCapacity(){
		return (rb.remainingCapacity()<LocationMain.RINGBUFFER_SIZE*0.1);
	}
}
