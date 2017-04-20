package com.smart.monkey;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;

import com.android.chimpchat.adb.AdbBackend;
import com.android.chimpchat.adb.AdbChimpDevice;
import com.android.chimpchat.core.IChimpImage;
import com.idle.LocationMain;
import com.idle.LocationProducer;
import com.saliency.ImageObj;
import com.saliency.SaliencyUtils;
import com.templatematch.MatchInterface;
import com.templatematch.MatchResult;
/**
 * smart monkey �����
 * smart monkey
 *
 */



public class runSmartmonkey {
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
	/*
	 * ���Է���
	 */
	public void run(String deviceId,int maxstep,int knum,int maxtime){
		//run("test","test");
		System.out.println("Start Smart Monkey!");
	}
	
	public static void runIdle(String basePath,String deviceId,int maxstep){
		System.out.println("start!");
		if (adb==null){ 
			adb = new AdbBackend(); 
		    device = (AdbChimpDevice) adb.waitForConnection(8000,deviceId);
		}
		
		device.takeSnapshot().writeToFile(basePath+"test"+System.currentTimeMillis(),"png");
		Mat img= Highgui.imread(basePath+"tmp.png", Highgui.CV_LOAD_IMAGE_GRAYSCALE);
		
		LocationMain.init(img.width(), img.height(),device);
		
		boolean flag=true;
		int count=0;
		while(flag){
			String srcfile = "SRC_"+System.currentTimeMillis();
			device.takeSnapshot().writeToFile(basePath+srcfile,"png");
			
			SaliencyUtils su=new SaliencyUtils(basePath+srcfile+".png",basePath+srcfile+"_target.png",20,"sr","kmeans");
			int[] result=su.getSaliencyResult().getResult();
			LocationProducer lp=new LocationProducer(LocationMain.ringBuffer,result,LocationMain.lrp);
			LocationMain.addSaliencyProducer(lp);
			
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			LocationMain.addRandomProducer(img.width(), img.height());
			count++;
			if(count==maxstep)
				flag=false;
		}
		LocationMain.shutdown();
		adb.shutdown();
		System.out.println("Finished!");
	}
	
	public static void run(String basePath,String deviceId) {
		String settings = basePath +"settings";
		System.out.println("start!");
		if (adb==null){ 
			adb = new AdbBackend(); 
		    device = (AdbChimpDevice) adb.waitForConnection(8000,deviceId);
		} 
		File file = new File(settings);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
				/*
				 * ��ȡ�ű���Ϣ
				 */
				String operate = line.split("\t")[0]; 
				String targetfile = line.split("\t")[1];
				String srcfile = "SRC_"+targetfile;
				int startx = Integer.parseInt(line.split("\t")[2]);
				int starty = Integer.parseInt(line.split("\t")[3]);
				int endx = Integer.parseInt(line.split("\t")[4]);
				int endy = Integer.parseInt(line.split("\t")[5]);
				int scalex = Integer.parseInt(line.split("\t")[6]);
				int scaley = Integer.parseInt(line.split("\t")[7]);
				/*
				 * ��ȡԭʼͼ��
				 */
				device.takeSnapshot().writeToFile(basePath+srcfile,"png");
				/*
				 * ִ��ͼ��ƥ���㷨
				 */
				MatchInterface tool = new MatchInterface
						(basePath+srcfile,basePath+targetfile,startx,starty,endx,endy,scalex,scaley);
				MatchResult result = tool.getMatchResult();
				System.out.println(result.startx+result.width/2);
				System.out.println(result.starty+result.height/2);
				if(operate.equals("click")){
					/*
					 * ִ������
					 */
					device.touch(result.startx+result.width/2,result.starty+result.height/2,com.android.chimpchat.core.TouchPressType.DOWN_AND_UP);
					/*
					 * �ȴ�Ƭ�̣���������
					 */
					try{
					    Thread thread = Thread.currentThread();
					    thread.sleep(2000);
					}catch (InterruptedException e) {
					    e.printStackTrace();
					}
				}
				else if(operate.equals("drag")){
					device.drag(result.startx,result.starty,
							result.startx+result.width,result.starty+result.height,2,3);
					try{
					    Thread thread = Thread.currentThread();
					    thread.sleep(2000);
					}catch (InterruptedException e) {
					    e.printStackTrace();
					}
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		adb.shutdown();
		System.out.println("Finished!");
	}
}
