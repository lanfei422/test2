package com.smart.monkey;

import java.io.File;
import java.util.ArrayList;

import com.saliency.SaliencyResultExt;
import com.saliency.SaliencyUtils;

public class Analysis {
	private static ArrayList<String> filelist = new ArrayList<String>();
	
	static void getFiles(String filePath){
		  File root = new File(filePath);
		    File[] files = root.listFiles();
		    for(File file:files){     
		      filelist.add(file.getAbsolutePath());
		    }
		 }
	public static void imageSalientDetect(String inputFile,String outputFile,
			String algorithm,int numOfNodes,String method){
		new SaliencyUtils(inputFile,outputFile,numOfNodes,algorithm,method);
	}	
	public static SaliencyResultExt imageSalientDetect(String inputFile,
			String algorithm,int numOfNodes,String method){
		return new SaliencyUtils().SaliencyIdle(inputFile,numOfNodes,algorithm,method);
	}
	public static SaliencyResultExt imageSalientDetectMerge(String inputFile,
			String algorithm,int numOfNodes,String method){
		return new SaliencyUtils().SaliencyIdleMerge(inputFile,numOfNodes,algorithm,method);
	}
	public static SaliencyResultExt imageSalientDetectRandom(String inputFile,
			int numOfNodes,String method){
		return new SaliencyUtils().SaliencyIdleRandom(inputFile,numOfNodes,method);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Analysis.getFiles("/home/yanlf/Desktop/ClashOfClan");
//		File curFile=new File(filelist.get(0));
//		SaliencyResultExt su=Analysis.imageSalientDetectRandom(filelist.get(0), 10, "kmeans");
//		su.writeResult("/home/yanlf/Desktop/testIdlePicMerge.png");
		
//		System.out.println("LC kmeans");
		for(int i=0;i<filelist.size();i++){
			System.out.println("handle file :"+filelist.get(i));
			File curFile=new File(filelist.get(i));
			SaliencyResultExt su=Analysis.imageSalientDetectRandom(filelist.get(i), 10, "kmeans");
			su.writeResult("/home/yanlf/Desktop/pic_out_all/ClashOfClan/"+curFile.getName());
		}
//		System.out.println("LC random");
//		for(int i=0;i<filelist.size();i++){
//			System.out.println("handle file :"+filelist.get(i));
//			File curFile=new File(filelist.get(i));
//			Analysis.imageSalientDetect(filelist.get(i), "/home/yanlf/Desktop/SmartMonkeyOutput/LC_Random/"+curFile.getName()+"_random", "lc", 20, "random");
//		}
//		System.out.println("SR kmeans");
//		for(int i=0;i<filelist.size();i++){
//			System.out.println("handle file :"+filelist.get(i));
//			File curFile=new File(filelist.get(i));
//			Analysis.imageSalientDetect(filelist.get(i), "/home/yanlf/Desktop/SmartMonkeyOutput/SR_Kmeans/"+curFile.getName()+"_kmeans", "sr", 20, "kmeans");
//		}
//		System.out.println("SR random");
//		for(int i=0;i<filelist.size();i++){
//			System.out.println("handle file :"+filelist.get(i));
//			File curFile=new File(filelist.get(i));
//			Analysis.imageSalientDetect(filelist.get(i), "/home/yanlf/Desktop/SmartMonkeyOutput/SR_Random/"+curFile.getName()+"_random", "sr", 20, "random");
//		}
	}

}
