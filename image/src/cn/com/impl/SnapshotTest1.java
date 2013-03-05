package cn.com.impl;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.filechooser.FileSystemView;

/**
 * ���ܣ�java��ͼ ���к󽫵�ǰ��Ļ��ȡ���������ʾ�� ��ק��꣬ѡ���Լ���Ҫ�Ĳ��֡� ��Esc������ͼƬ�����棬���˳�����
 * ˫��������������ͼƬ�����棬���˳����� ������Ͻǣ�û�пɼ��İ�ť�����˳����򣬲�����ͼƬ�� ����Ҽ����˳����򣬲�����ͼƬ��
 * 
 * @author ���²�
 */
public class SnapshotTest1 {

	private static SnapshotTest1 test1;

	public static SnapshotTest1 createSnapshotTest1() {
		if (test1 == null) {
			SnapshotTest1 test1 = new SnapshotTest1();
		}
		return test1;
	}

	public static void start() {

		// ȫ������
		RectD rd = new RectD();
//		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment()
//				.getDefaultScreenDevice();
//		gd.setFullScreenWindow(rd);
	}
}

class RectD extends JFrame {
	private static final long serialVersionUID = 1L;
	int orgx, orgy, endx, endy;// ��갴�º��ͷ�ʱx��y������
	Dimension d = Toolkit.getDefaultToolkit().getScreenSize();// �����Ļ��С
	BufferedImage image;// �洢������Ļ
	BufferedImage tempImage;// ����
	BufferedImage saveImage;// ����(��ȡ������)
	Graphics g;

	@Override
	public void paint(Graphics g) {
		RescaleOp ro = new RescaleOp(0.8f, 0,null);// ����һ��������ϣ�����������Ӻ�ƫ��������
		// RescaleOp
		tempImage = ro.filter(image, null);// ��Դ BufferedImage����image����������
		g.drawImage(tempImage, 0, 0, this);

	}

	public RectD() {
		snapshot();
		setVisible(true);// ���ô��ڿɼ�
		setSize(d);// ��󻯴���
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// Ĭ�Ϲرշ�ʽ
		this.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {// ��갴��������ϰ���ʱ���á�
				orgx = e.getX();
				orgy = e.getY();
			}
		});
		this.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {// ��갴��������ϰ��²��϶�ʱ���á�
				endx = e.getX();
				endy = e.getY();
				g = getGraphics();
				g.drawImage(tempImage, 0, 0, RectD.this);
				int x = Math.min(orgx, endx);
				int y = Math.min(orgy, endy);
				int width = Math.abs(endx - orgx) + 1;
				int height = Math.abs(endy - orgy) + 1;
				// ����1����ֹwidth��heightΪ0
				g.setColor(Color.BLUE);
				g.drawRect(x - 1, y - 1, width + 1, height + 1);
				// ��1����1����Ϊ�˷�ֹͼƬ�����ο򸲸ǵ�
				saveImage = image.getSubimage(x, y, width, height);
				g.drawImage(saveImage, x, y, RectD.this);
			}
		});
		this.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				// ��Esc��
				if (e.getKeyCode() == 27) {
					if (saveImage != null) {
						saveToFile();// ����ͼƬ
					}
					//System.exit(0);// �˳�
					setVisible(false);// ���ô��ڿɼ�
				}
			}
		});
		this.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {// ����(˫��)���ʱ����
				// TODO Auto-generated method stub
				// ˫��������
				if (e.getClickCount() == 2) {
					if (saveImage != null) {// �����ͼ���򱣴�ͼƬ������
						saveToFile();
					}
					//System.exit(0);// �˳�
					setVisible(false);// ���ô��ڿɼ�
				}
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {// �������ʱ����
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {// �뿪���ʱ����
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {// ������ϰ�����갴��ʱ����
				// TODO Auto-generated method stub
				// �������Ҽ����˳�����
				if (e.getModifiers() == MouseEvent.BUTTON3_MASK) {
					//System.exit(0);
					setVisible(false);// ���ô��ڿɼ�
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {// ��������ͷ���갴ťʱ����
				// TODO Auto-generated method stub

			}
		});
	}

	// ����ͼƬ�����棬ͼƬ���Ƹ�ʽΪyyyymmddHHmmss.jpg
	public void saveToFile() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyymmddHHmmss");
		String name = sdf.format(new Date());// ��ʽת�����ͼƬ��
		File path = FileSystemView.getFileSystemView().getHomeDirectory();// �������·��
		String format = "jpg";
		File f = new File(path + File.separator + name + "." + format);
		try {
			ImageIO.write(saveImage, format, f);// ����ͼƬ
		} catch (IOException e) {// �׳��쳣
			e.printStackTrace();
		}
	}

	/**
	 * ��׽����ͼƬ
	 */
	public void snapshot() {
		try {
			Robot robot = new Robot();// �ڻ�����Ļ����ϵ�й���һ�� Robot����
			Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
			image = robot.createScreenCapture(new Rectangle(0, 0, d.width,
					d.height));// ���������Ļ
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
}
