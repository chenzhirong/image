package cn.com.action;

import info.impl.ReadInfo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Properties;

import javax.swing.ImageIcon;

import cn.com.impl.GuiCamera;
import cn.com.view.MainFrame;

/**
 * ��ť�¼�
 * 
 * @author Administrator
 * 
 */
public class BoolBarAction implements ActionListener {
	private MainFrame frame;

	public BoolBarAction(MainFrame frame) {
		this.frame = frame;
	}

	/**
	 * ������ťִ����Ӧ�ķ���
	 */
	public void actionPerformed(ActionEvent e) {
		String title = e.getActionCommand();
		if ("����".equals(title)) {
			ReadInfo.setInfoType("windows", "false");
	    	frame.setVisible(false);
	    	try {
				frame.start();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		} else if ("��ʼ".equals(title)) {
			try {
				frame.start();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		} else if ("��һ��".equals(title)) {
			frame.next();
		} else if ("�Ŵ�".equals(title)) {
			frame.zoom(false);
		} else if ("��С".equals(title)) {
			frame.zoom(true);
		} else if ("��Ļ����".equals(title)) {
			GuiCamera camera = GuiCamera.createCamera();
			File f = camera.snapShot();
			ImageIcon icon = new ImageIcon(f.getPath());
			frame.setLableImage(icon);
		}

	}

}
