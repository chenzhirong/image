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
		//System.out.println(" ��갴��������ϵ��������²��ͷţ�ʱ���á�");
		
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		//System.out.println("  �����뵽�����ʱ���á�");
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		//System.out.println("����뿪���ʱ���á�");
		
	}


	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		//System.out.println(" ��갴��������ϰ���ʱ���á�");
		
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println("��갴ť��������ͷ�ʱ���á�");
		
	}
}
