package com.saliency;

import org.opencv.core.Core;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;

public class SaliencyResultExt extends SaliencyResult {
	protected int[] randomResult;

	@Override
	public void writeResult(String Pathname) {
		// TODO Auto-generated method stub
		for(int i = 0;i < randomResult.length;i=i+2){
			if(i==2*k_num)
				break;
			Point pt = new Point(this.randomResult[i+1],this.randomResult[i]);
			Scalar other = new Scalar(255,255,0);
			Core.circle(sourceimg, pt, 6, other,10);
			Core.putText(sourceimg, String.valueOf(i/2+1), pt, Core.FONT_HERSHEY_SIMPLEX, 0.5, new Scalar(0,0,0));
		}
		for(int i = 0;i < 2 * k_num;i=i+2){
			Point pt = new Point(this.result[i+1],this.result[i]);
			Scalar green = new Scalar(0,255,0);
			Core.circle(sourceimg, pt, 6, green,10);
			Core.putText(sourceimg, String.valueOf(i/2+1), pt, Core.FONT_HERSHEY_SIMPLEX, 0.5, new Scalar(0,0,0));
		}
		Highgui.imwrite(Pathname, sourceimg);
	}

}
