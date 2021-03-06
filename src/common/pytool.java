package common;
/**
 * MonkeyRunner 通过python脚本调用工具
 * 静态方法 takeshot 截取Android手机图像
 *
 */
public class pytool {
	private static final String TAKESHOT = "takeshot.py ";
	/*
	 * 获取 Android屏幕截图
	 */
	public static void takeshot(String filename) throws Exception{
		String pypath = new pytool().getPypath();
		try{
			JavaShellUtil.execute( pypath + TAKESHOT + filename);
		}catch(Exception e){
			throw e;
		}
	}
	/*
	 * 获取 python 文件所在相对路径
	 */
	private String getPypath(){
		String path = this.getClass().getClassLoader().getResource(".").getPath();
		String pypath = path.split("bin/")[0]+"pysrc/";
		System.out.println(pypath);
		return pypath;
	}
	/*
	 * 获取 tmp 文件所在相对路径
	 */
	private String getTmppath(){
		String path = this.getClass().getClassLoader().getResource(".").getPath();
		String pypath = path.split("bin/")[0]+"tmpPic/";
		System.out.println(pypath);
		return pypath;
	}
	
	public static void main(String args[]) throws Exception{
		String targetpath = new pytool().getTmppath();
		//takeshot("./test.png");
		//pytool p = new pytool();
		//p.getPypath();
	}
}
