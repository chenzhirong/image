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
 * 按钮事件
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
	 * 单击按钮执行相应的方法
	 */
	public void actionPerformed(ActionEvent e) {
		String title = e.getActionCommand();
		if ("隐藏".equals(title)) {
			ReadInfo.setInfoType("windows", "false");
	    	frame.setVisible(false);
	    	try {
				frame.start();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		} else if ("开始".equals(title)) {
			try {
				frame.start();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		} else if ("下一张".equals(title)) {
			frame.next();
		} else if ("放大".equals(title)) {
			frame.zoom(false);
		} else if ("放小".equals(title)) {
			frame.zoom(true);
		} else if ("屏幕拍照".equals(title)) {
			GuiCamera camera = GuiCamera.createCamera();
			File f = camera.snapShot();
			ImageIcon icon = new ImageIcon(f.getPath());
			frame.setLableImage(icon);
		}

	}

}
