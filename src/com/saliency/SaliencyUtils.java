package com.saliency;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;

import com.idle.Location;
import com.idle.LocationRandomFactory;

public class SaliencyUtils {
	private static SaliencyResult result;

	public SaliencyUtils(String sourcePath, int k_num, String method) {
		SaliencyContext context = new SaliencyContext(new LcAlgorithm());
		ImageObj imgobj = new ImageObj();
		imgobj.setSource(sourcePath);
		imgobj.setK_num(k_num);
		;
		result = context.SaliencyContextInterface(imgobj, method);
	}

	public SaliencyUtils(String sourcePath, String resultPath, int k_num, String method) {
		SaliencyContext context = new SaliencyContext(new LcAlgorithm());
		ImageObj imgobj = new ImageObj();
		imgobj.setSource(sourcePath);
		imgobj.setK_num(k_num);
		;
		result = context.SaliencyContextInterface(imgobj, method);
		result.writeResult(resultPath);
		result.printResult();
	}

	public SaliencyUtils(String sourcePath, String resultPath, int k_num, String algorithm, String method) {
		SaliencyContext context = null;
		if (algorithm.equals("lc"))
			context = new SaliencyContext(new LcAlgorithm());
		else if (algorithm.equals("sr"))
			context = new SaliencyContext(new SrAlgorithm());
		ImageObj imgobj = new ImageObj();
		imgobj.setSource(sourcePath);
		imgobj.setK_num(k_num);

		result = context.SaliencyContextInterface(imgobj, method);
		result.writeResult(resultPath);
		result.printResult();
	}

	public SaliencyUtils() {
	};

	public SaliencyResultExt SaliencyIdle(String sourcePath, int k_num, String algorithm, String method) {
		SaliencyContext context = null;
		if (algorithm.equals("lc"))
			context = new SaliencyContext(new LcAlgorithm());
		else if (algorithm.equals("sr"))
			context = new SaliencyContext(new SrAlgorithm());
		ImageObj imgobj = new ImageObj();
		imgobj.setSource(sourcePath);
		imgobj.setK_num(k_num);

		Mat img = Highgui.imread(imgobj.getSourcePath(), Highgui.CV_LOAD_IMAGE_GRAYSCALE);

		List<Location> locList = new ArrayList<>();
		LocationRandomFactory lrf = new LocationRandomFactory(img.rows(), img.cols());
		Thread randomThread = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (!Thread.currentThread().isInterrupted()) {
					Location tmp = lrf.newInstance();
					locList.add(new Location(tmp.getX(), tmp.getY()));
					try {
						Thread.sleep(10L);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						Thread.currentThread().interrupt();
					}
				}
			}
		});
		randomThread.start();
		SaliencyResult resultInner = context.SaliencyContextInterface(imgobj, method);
		randomThread.interrupt();
		SaliencyResultExt resultEx = new SaliencyResultExt();
		resultEx.sourceimg = resultInner.sourceimg;
		resultEx.result = resultInner.result;
		resultEx.saliencymap = resultInner.saliencymap;
		resultEx.k_num = resultInner.k_num;
		resultEx.randomResult = getRandomResult(locList);

		return resultEx;
	}

	public SaliencyResultExt SaliencyIdleRandom(String sourcePath, int k_num, String method) {
		SaliencyContext context = null;
		String algorithm = "lc";
		Random random = new Random(System.currentTimeMillis());
		int choice = random.nextInt();
		if (Math.abs(choice) % 2 == 0) {
			algorithm = "sr";
		}
		System.out.println(algorithm);
		if (algorithm.equals("lc"))
			context = new SaliencyContext(new LcAlgorithm());
		else if (algorithm.equals("sr"))
			context = new SaliencyContext(new SrAlgorithm());
		ImageObj imgobj = new ImageObj();
		imgobj.setSource(sourcePath);
		imgobj.setK_num(k_num);

		Mat img = Highgui.imread(imgobj.getSourcePath(), Highgui.CV_LOAD_IMAGE_GRAYSCALE);

		List<Location> locList = new ArrayList<>();
		LocationRandomFactory lrf = new LocationRandomFactory(img.rows(), img.cols());
		Thread randomThread = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (!Thread.currentThread().isInterrupted()) {
					Location tmp = lrf.newInstance();
					locList.add(new Location(tmp.getX(), tmp.getY()));
					try {
						Thread.sleep(10L);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						Thread.currentThread().interrupt();
					}
				}
			}
		});
		randomThread.start();
		SaliencyResult resultInner = context.SaliencyContextInterface(imgobj, method);
		randomThread.interrupt();
		SaliencyResultExt resultEx = new SaliencyResultExt();
		resultEx.sourceimg = resultInner.sourceimg;
		resultEx.result = resultInner.result;
		resultEx.saliencymap = resultInner.saliencymap;
		resultEx.k_num = resultInner.k_num;
		resultEx.randomResult = getRandomResult(locList);

		return resultEx;
	}

	public SaliencyResultExt SaliencyIdleMerge(String sourcePath, int k_num, String algorithm, String method) {
		SaliencyContext contextLC = new SaliencyContext(new LcAlgorithm());
		SaliencyContext contextSR = new SaliencyContext(new SrAlgorithm());
		ImageObj imgobj = new ImageObj();
		imgobj.setSource(sourcePath);
		imgobj.setK_num(k_num);

		Mat img = Highgui.imread(imgobj.getSourcePath(), Highgui.CV_LOAD_IMAGE_GRAYSCALE);

		List<Location> locList = new ArrayList<>();
		LocationRandomFactory lrf = new LocationRandomFactory(img.rows(), img.cols());
		Thread randomThread = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (!Thread.currentThread().isInterrupted()) {
					Location tmp = lrf.newInstance();
					locList.add(new Location(tmp.getX(), tmp.getY()));
					try {
						Thread.sleep(10L);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						Thread.currentThread().interrupt();
					}
				}
			}
		});
		randomThread.start();
		SaliencyResult resultInnerLC = contextLC.SaliencyContextInterface(imgobj, method);
		randomThread.interrupt();
		SaliencyResult resultInnerSR = contextSR.SaliencyContextInterface(imgobj, method);
		SaliencyResultExt resultEx = new SaliencyResultExt();
		resultEx.sourceimg = resultInnerLC.sourceimg;
		resultEx.result = resultInnerLC.result;
		Random random = new Random(System.currentTimeMillis());
		for (int i = 0; i < k_num / 2; i++) {
			int tmpIndex = Math.abs(random.nextInt(1000)) % resultEx.result.length / 2 * 2;
			resultEx.result[tmpIndex] = resultInnerSR.result[tmpIndex];
			resultEx.result[tmpIndex + 1] = resultInnerSR.result[tmpIndex + 1];
		}
		resultEx.saliencymap = resultInnerLC.saliencymap;
		resultEx.k_num = resultInnerLC.k_num;
		resultEx.randomResult = getRandomResult(locList);

		return resultEx;
	}

	private static int[] getRandomResult(List<Location> locList) {
		// TODO Auto-generated method stub
		int result[] = new int[locList.size() * 2];
		for (int i = 0; i < locList.size(); i++) {
			result[i * 2] = locList.get(i).getX();
			result[i * 2 + 1] = locList.get(i).getY();
		}
		return result;
	}

	public SaliencyResult getSaliencyResult() {
		return result;
	}
}
