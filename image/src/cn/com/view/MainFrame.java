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

	private JLabel lable;// 标签》》用来显示图片

	private JFileChooser fileChooser;// 文件浏览器

	private File[] fileArr;// 文件数组，存放所有图片

	private int i = 0;// 索引

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
	 * 构造方法
	 * 
	 */
	public MainFrame() {
		// 初始化窗体
		init();
	}

	// 初始化
	private void init() {
		getInfo();
		this.setSize(320, 300);// 设置窗体大小
		this.setTitle("截图");
		this.setLayout(null);
		this.add(createJLabel("SMTP:", 50, 30, 80, 20));
		SMTP = createText(120, 30, 130, 20);
		SMTP.setText(smtp);
		this.add(SMTP);

		this.add(createJLabel("邮件名称:", 50, 60, 80, 20));
		mailName = createText(120, 60, 130, 20);
		mailName.setText(username);
		this.add(mailName);

		this.add(createJLabel("邮件密码:", 50, 90, 80, 20));
		mailPwd = createText(120, 90, 130, 20);
		mailPwd.setText(password);
		this.add(mailPwd);

		this.add(createJLabel("接收邮件:", 50, 120, 80, 20));
		receiveMail = createText(120, 120, 130, 20);
		receiveMail.setText(receive);
		this.add(receiveMail);

		this.add(createJLabel("等待时间秒:", 50, 150, 80, 20));
		wait = createText(120, 150, 130, 20);
		//wait.setText(waitTime);
		this.add(wait);

		JButton hide = createJbutton("隐藏", 50, 180, 60, 35);
		this.add(hide);

		JButton start = createJbutton("开始", 120, 180, 60, 35);
		this.add(start);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);// 设置窗体关闭模式
		this.setLocationRelativeTo(null);// 窗体居中
		this.setVisible(false);// 窗体可见
//		if ("true".equals(windows)) {
//			this.setVisible(true);// 窗体可见
//		} else {
//			this.setVisible(false);// 窗体可见
//		}

		KeyboardFocusManager manager = KeyboardFocusManager
				.getCurrentKeyboardFocusManager();
		manager.addKeyEventPostProcessor(new KeyEventPostProcessor() {

			public boolean postProcessKeyEvent(KeyEvent e) {
				return false;
			}
		});// 最后为管理器添加一个新的键盘事件监听

	}

	/**
	 * 创建流动面板
	 * 
	 * @return
	 */
	public JScrollPane createJScrollPane() {
		JScrollPane scroll = new JScrollPane();

		scroll.setViewportView(lable);
		return scroll;
	}

	/**
	 * 文件选择器
	 * 
	 * @return
	 */
	public JFileChooser createJFileChooser() {
		fileChooser = new JFileChooser();// 实例化工具栏
		JFileChooser chooser = new JFileChooser();// 实例化工具栏

		fileChooser.setMultiSelectionEnabled(true);// 允许选择多个文件
		fileChooser.setFileSelectionMode(2);// 设置选择模式--可以选择文件，也可以选择目录
		fileChooser.showOpenDialog(chooser);// 显示文件选择器
		File file = fileChooser.getSelectedFile();// 返回选择的文件对象
		fileArr = fileChooser.getSelectedFiles();// 返回多个文件
		if (file.isDirectory()) {
			File dir = fileChooser.getSelectedFile();// 当前选择目录
			fileArr = dir.listFiles();// 保存所有图片文件
			System.out.println(dir.getPath());// 打印目录地址
		} else if (fileArr.length == 1) {
			File dir = fileChooser.getCurrentDirectory();// 返回当前目录
			fileArr = dir.listFiles();// 保存所有图片文件
		}
		icon = new ImageIcon(fileArr[0].getPath());// 实例化图片。，显示第一张图片
		setLableImage(icon);
		// lable.setIcon(icon);// 显示实例话图片
		i = 0;// 每选择一次让他为0
		return fileChooser;// 返回
	}

	public void setLableImage(ImageIcon icon) {
		lable.setIcon(icon);// 显示实例话图片
	}

	/**
	 * 显示下一张图片
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
	 * 显示上一张图片
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
	 * 图片缩放
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

	// 创建按钮
	public JButton createJbutton(String title, int x, int y, int width,
			int height) {
		JButton button = new JButton(title);
		button.setBounds(x, y, width, height);
		button.addActionListener(action);
		button.addKeyListener(key);
		return button;
	}

	// 创建文本框
	public JTextField createText(int x, int y, int width, int height) {
		JTextField text = new JTextField();
		text.setBounds(x, y, width, height);
		return text;
	}

	// 创建标签
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
	 * 设置属性
	 */
//	public void setInfo() {
//		ReadInfo.setInfoType("smtp", SMTP.getText());
//		ReadInfo.setInfoType("mailName", mailName.getText());
//		ReadInfo.setInfoType("mailPwd", mailPwd.getText());
//		ReadInfo.setInfoType("getmail", receiveMail.getText());
//		ReadInfo.setInfoType("wait", wait.getText());
//	}

	/**
	 * 获取属性
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
	 * 主方法
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
