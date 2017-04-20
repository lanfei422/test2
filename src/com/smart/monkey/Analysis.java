package com.smart.monkey;

import java.io.File;
import java.util.ArrayList;

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
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Analysis.getFiles("/home/yanlf/Desktop/SmartInput");
		System.out.println("LC kmeans");
		for(int i=0;i<filelist.size();i++){
			System.out.println("handle file :"+filelist.get(i));
			File curFile=new File(filelist.get(i));
			Analysis.imageSalientDetect(filelist.get(i), "/home/yanlf/Desktop/photoAnalysis/40/Random/"+curFile.getName()+"_kmeans", "lc", 40, "random");
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
