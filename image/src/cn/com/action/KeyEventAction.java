package cn.com.action;

import info.impl.ReadInfo;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Properties;

import javax.swing.ImageIcon;

import cn.com.impl.GuiCamera;
import cn.com.impl.SnapshotTest1;
import cn.com.view.MainFrame;

public class KeyEventAction extends  KeyAdapter {
	private MainFrame frame;

	public KeyEventAction(MainFrame frame) {
		this.frame = frame;
	}

	// 按下某个键时调用此方法。
	public void keyPressed(KeyEvent e) {
		if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_Q) {
			GuiCamera camera = GuiCamera.createCamera();
			File f = camera.snapShot();
			ImageIcon icon = new ImageIcon(f.getPath());
			frame.setLableImage(icon);
		}else if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_C) {
			SnapshotTest1.createSnapshotTest1().start();
		}else if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_K) {
			//隐藏
			frame.setVisible(false);
			ReadInfo.setInfoType("windows", "false");
			try {
				frame.start();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}else if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_M) {
			//现身
			frame.setVisible(true);
			ReadInfo.setInfoType("windows", "true");
		}
	}
}
