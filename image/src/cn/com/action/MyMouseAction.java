package cn.com.action;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import cn.com.view.MainFrame;

public class MyMouseAction  implements MouseListener{
	private MainFrame frame;
	
	public MyMouseAction(MainFrame frame) {
		this.frame=frame;
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		//System.out.println(" 鼠标按键在组件上单击（按下并释放）时调用。");
		
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		//System.out.println("  鼠标进入到组件上时调用。");
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		//System.out.println("鼠标离开组件时调用。");
		
	}


	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		//System.out.println(" 鼠标按键在组件上按下时调用。");
		
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("鼠标按钮在组件上释放时调用。");
		
	}
}
