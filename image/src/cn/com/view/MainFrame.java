package cn.com.view;

import info.impl.ReadInfo;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.KeyEventPostProcessor;
import java.awt.KeyboardFocusManager;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import cn.com.action.BoolBarAction;
import cn.com.action.KeyEventAction;
import cn.com.action.MyMouseAction;
import cn.com.impl.GuiCamera;

public class MainFrame extends JFrame {

	private static MainFrame frame;

	private KeyEventAction key = new KeyEventAction(this);

	private BoolBarAction action = new BoolBarAction(this);

	private MyMouseAction mouse = new MyMouseAction(this);

	private JLabel lable;// ��ǩ����������ʾͼƬ

	private JFileChooser fileChooser;// �ļ������

	private File[] fileArr;// �ļ����飬�������ͼƬ

	private int i = 0;// ����

	private ImageIcon icon;

	private double scale = 1;

	private JTextPane text = new JTextPane();

	private String smtp;
	private String username;
	private String password;
	private String receive;
	private String windows;
	private long Time;

	private JTextField SMTP;
	private JTextField mailName;
	private JTextField mailPwd;
	private JTextField receiveMail;
	private JTextField wait;

	/**
	 * ���췽��
	 * 
	 */
	public MainFrame() {
		// ��ʼ������
		init();
	}

	// ��ʼ��
	private void init() {
		getInfo();
		this.setSize(320, 300);// ���ô����С
		this.setTitle("��ͼ");
		this.setLayout(null);
		this.add(createJLabel("SMTP:", 50, 30, 80, 20));
		SMTP = createText(120, 30, 130, 20);
		SMTP.setText(smtp);
		this.add(SMTP);

		this.add(createJLabel("�ʼ�����:", 50, 60, 80, 20));
		mailName = createText(120, 60, 130, 20);
		mailName.setText(username);
		this.add(mailName);

		this.add(createJLabel("�ʼ�����:", 50, 90, 80, 20));
		mailPwd = createText(120, 90, 130, 20);
		mailPwd.setText(password);
		this.add(mailPwd);

		this.add(createJLabel("�����ʼ�:", 50, 120, 80, 20));
		receiveMail = createText(120, 120, 130, 20);
		receiveMail.setText(receive);
		this.add(receiveMail);

		this.add(createJLabel("�ȴ�ʱ����:", 50, 150, 80, 20));
		wait = createText(120, 150, 130, 20);
		//wait.setText(waitTime);
		this.add(wait);

		JButton hide = createJbutton("����", 50, 180, 60, 35);
		this.add(hide);

		JButton start = createJbutton("��ʼ", 120, 180, 60, 35);
		this.add(start);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);// ���ô���ر�ģʽ
		this.setLocationRelativeTo(null);// �������
		this.setVisible(false);// ����ɼ�
//		if ("true".equals(windows)) {
//			this.setVisible(true);// ����ɼ�
//		} else {
//			this.setVisible(false);// ����ɼ�
//		}

		KeyboardFocusManager manager = KeyboardFocusManager
				.getCurrentKeyboardFocusManager();
		manager.addKeyEventPostProcessor(new KeyEventPostProcessor() {

			public boolean postProcessKeyEvent(KeyEvent e) {
				return false;
			}
		});// ���Ϊ���������һ���µļ����¼�����

	}

	/**
	 * �����������
	 * 
	 * @return
	 */
	public JScrollPane createJScrollPane() {
		JScrollPane scroll = new JScrollPane();

		scroll.setViewportView(lable);
		return scroll;
	}

	/**
	 * �ļ�ѡ����
	 * 
	 * @return
	 */
	public JFileChooser createJFileChooser() {
		fileChooser = new JFileChooser();// ʵ����������
		JFileChooser chooser = new JFileChooser();// ʵ����������

		fileChooser.setMultiSelectionEnabled(true);// ����ѡ�����ļ�
		fileChooser.setFileSelectionMode(2);// ����ѡ��ģʽ--����ѡ���ļ���Ҳ����ѡ��Ŀ¼
		fileChooser.showOpenDialog(chooser);// ��ʾ�ļ�ѡ����
		File file = fileChooser.getSelectedFile();// ����ѡ����ļ�����
		fileArr = fileChooser.getSelectedFiles();// ���ض���ļ�
		if (file.isDirectory()) {
			File dir = fileChooser.getSelectedFile();// ��ǰѡ��Ŀ¼
			fileArr = dir.listFiles();// ��������ͼƬ�ļ�
			System.out.println(dir.getPath());// ��ӡĿ¼��ַ
		} else if (fileArr.length == 1) {
			File dir = fileChooser.getCurrentDirectory();// ���ص�ǰĿ¼
			fileArr = dir.listFiles();// ��������ͼƬ�ļ�
		}
		icon = new ImageIcon(fileArr[0].getPath());// ʵ����ͼƬ������ʾ��һ��ͼƬ
		setLableImage(icon);
		// lable.setIcon(icon);// ��ʾʵ����ͼƬ
		i = 0;// ÿѡ��һ������Ϊ0
		return fileChooser;// ����
	}

	public void setLableImage(ImageIcon icon) {
		lable.setIcon(icon);// ��ʾʵ����ͼƬ
	}

	/**
	 * ��ʾ��һ��ͼƬ
	 * 
	 */
	public void next() {

		if (fileArr != null) {
			i++;
			if (i == fileArr.length) {
				i = 0;
			}
			icon = new ImageIcon(fileArr[i].getPath());
			lable.setIcon(icon);
		}

	}

	/**
	 * ��ʾ��һ��ͼƬ
	 * 
	 */
	public void up() {
		if (fileArr != null) {
			i--;
			if (i < 0) {
				i = fileArr.length - 1;
			}
			icon = new ImageIcon(fileArr[i].getPath());
			lable.setIcon(icon);

		}

	}

	/**
	 * ͼƬ����
	 * 
	 * @param flg
	 */
	public void zoom(boolean flg) {
		if (scale > 0.2 && flg == true) {
			scale = scale - 0.1;
			System.out.println(scale);
		} else if (flg == false) {
			scale = scale + 0.1;
			System.out.println("false");
		}
		int width = (int) (icon.getIconWidth() * scale);
		ImageIcon newIcon = new ImageIcon(icon.getImage().getScaledInstance(
				width, -1, Image.SCALE_DEFAULT));
		lable.setIcon(newIcon);

	}

	// ������ť
	public JButton createJbutton(String title, int x, int y, int width,
			int height) {
		JButton button = new JButton(title);
		button.setBounds(x, y, width, height);
		button.addActionListener(action);
		button.addKeyListener(key);
		return button;
	}

	// �����ı���
	public JTextField createText(int x, int y, int width, int height) {
		JTextField text = new JTextField();
		text.setBounds(x, y, width, height);
		return text;
	}

	// ������ǩ
	public JLabel createJLabel(String title, int x, int y, int width, int height) {
		JLabel label = new JLabel(title);
		label.setBounds(x, y, width, height);
		return label;
	}

	public static MainFrame getframe() {
		if (frame == null) {
			frame = new MainFrame();
		}
		return frame;
	}

	public void start() throws InterruptedException {
		//setInfo();
		while (true) {
			GuiCamera camera = GuiCamera.createCamera();
			File f = camera.snapShot();
			ImageIcon icon = new ImageIcon(f.getPath());
			Thread.sleep(Time);
		}
	}

	/**
	 * ��������
	 */
//	public void setInfo() {
//		ReadInfo.setInfoType("smtp", SMTP.getText());
//		ReadInfo.setInfoType("mailName", mailName.getText());
//		ReadInfo.setInfoType("mailPwd", mailPwd.getText());
//		ReadInfo.setInfoType("getmail", receiveMail.getText());
//		ReadInfo.setInfoType("wait", wait.getText());
//	}

	/**
	 * ��ȡ����
	 */
	public void getInfo() {
		smtp = ReadInfo.readInfoType("smtp");
		username = ReadInfo.readInfoType("mailName");
		password = ReadInfo.readInfoType("mailPwd");
		receive = ReadInfo.readInfoType("getmail");
		windows = ReadInfo.readInfoType("windows");
		String waitTime = ReadInfo.readInfoType("wait");
		 Time = Long.parseLong(waitTime);
	}

	/**
	 * ������
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			// UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			MainFrame.getframe().start();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
