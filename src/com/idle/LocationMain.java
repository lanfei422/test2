package com.idle;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.android.chimpchat.adb.AdbChimpDevice;
import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class LocationMain {
	private static Logger logger = LogManager.getLogger(LocationMain.class);
	public static final int RINGBUFFER_SIZE=256;
	
	private static Disruptor<Location> disruptor;
	private static final ExecutorService Service=Executors.newCachedThreadPool();
	
	public static RingBuffer<Location> ringBuffer;
	public static LocationRandomPublisher lrp;
	
	public static void init(int x,int y,AdbChimpDevice device){
		disruptor=new Disruptor<Location>(
				new LocationFactory(),
				RINGBUFFER_SIZE,
				Service, 
				ProducerType.MULTI,
				new BusySpinWaitStrategy()
				);
		
		handleEventsWith(new EventHandler[]{new LocationConsumer(device)});
		ringBuffer=disruptor.start();
		
		addRandomProducer(x,y);
	}
	
	public static void addRandomProducer(int x,int y){
		LocationRandomPublisher lrp=new LocationRandomPublisher(ringBuffer,x,y);
		Service.submit(lrp);
		LocationMain.lrp=lrp;
	}
	
	public static void addSaliencyProducer(LocationProducer lp){
		Service.submit(lp);
	}
	
	public static void shutdown(){
		disruptor.shutdown();
		Service.shutdown();
		lrp.stop();
	}
	
	public static void handleEventsWith(EventHandler[] eventsHandler){
		disruptor.handleEventsWith(eventsHandler);
	}
	
	public static void addConsumer(LocationConsumer lc){
		disruptor.handleEventsWith(lc);
	}
	public static void main(String[] args){
		LocationMain.init(800, 480, null);
		int[] result=new int[]{55,55,55,55,55,55,55,55,55,55,55};
		try {
			Thread.sleep(2000);
			LocationProducer lp=new LocationProducer(LocationMain.ringBuffer,result,LocationMain.lrp);
			LocationMain.addSaliencyProducer(lp);
			Thread.sleep(2000);
			LocationMain.addRandomProducer(800, 400);
			Thread.sleep(2000);
			LocationMain.shutdown();}
		catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("finished.");
	}
}
