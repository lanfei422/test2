package com.idle;

import java.util.ArrayList;
import java.util.List;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.RingBuffer;

public class LocationProducer implements Runnable{
	static RingBuffer<Location> rb;
	private List<Location> inputLocations;
	private LocationRandomPublisher lrp;
	private static EventFactory<Location> factory=new LocationFactory();
	
	public LocationProducer(RingBuffer<Location> rb, int[] result,LocationRandomPublisher lrp) {
		this.rb=rb;
		inputLocations=change2List(result);
		this.lrp=lrp;
	}

	private static List<Location> change2List(int[] result){
		List<Location> rtList=new ArrayList<>(result.length);
		for(int i=0;i+1<result.length;i+=2){
			rtList.add(new Location(result[i],result[i+1]));
		}
		return rtList;
	}
	
	private void addLocation(){
		if(hasCapacity()){
			System.out.println("disruptor's capacity belows to 10%");
		}else{
			lrp.stop();
			for(Location loc:inputLocations){
				long seq=rb.next();
				Location rb_loc=rb.get(seq);
				rb_loc.setX(loc.getX());
				rb_loc.setY(loc.getY());
				
				rb.publish(seq);
			}
		}
	}
	
	private static boolean hasCapacity(){
		return (rb.remainingCapacity()<LocationMain.RINGBUFFER_SIZE*0.1);
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		addLocation();
	}
}
