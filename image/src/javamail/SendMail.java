package javamail;

import info.impl.ReadInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * 
 * @author javalzbin
 * 
 */
public class SendMail {
	private String smtp = ""; // �ʼ�������������
	private String protocol = ""; // �ʼ�����Э��
	private String username = ""; // ��¼�û���
	private String password = ""; // ��¼����
	private String from = ""; // �����˵�ַ
	private String to = ""; // �ռ��˵�ַ
	private String subject = ""; // �ʼ�����
	private String body = ""; // �ʼ�����

	// һ���й����map������Ƕ��ͼƬ
	Map<String, String> map;
	// ��Ÿ���
	List<String> list;

//	public static void main(String[] args) {
//		try {
//			SendMail.sendZipMail("2013��02��17��21.40.54.zip");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

	public SendMail(Map<String, String> map, List<String> filelist,
			Map<String, String> image) {
		this.smtp = map.get("smtp");
		this.protocol = map.get("protocol");
		this.username = map.get("username");
		this.password = map.get("password");
		this.from = map.get("from");
		this.to = map.get("to");
		this.subject = map.get("subject");
		this.body = map.get("body");
		this.list = filelist;
		this.map = image;
	}

	public static void sendZipMail(String zipPath) throws Exception {
		String smtp = ReadInfo.readInfoType("smtp");
		String username = ReadInfo.readInfoType("mailName");
		String password = ReadInfo.readInfoType("mailPwd");
		String receiveMail = ReadInfo.readInfoType("getmail");
		String windows = ReadInfo.readInfoType("windows");
		String wait = ReadInfo.readInfoType("wait");
		String content ="<a href=http://www.baidu.com>�������ٶ�</a><br/>"
				+ "<img src=\"cid:a00000001\"><br/><br/>";
		// "<img src=\"cid:a00000002\">";

		Map<String, String> map = new HashMap<String, String>();

		// �ʼ�������������(smtp��������ַ)
		// �磺qq��smtp��������ַ��smtp.qq.com
		map.put("smtp", smtp);
		// �ʼ�����Э�飺��smtp
		map.put("protocol", "smtp");
		// ��¼������û���
		map.put("username", username);
		// ��¼���������
		map.put("password", password);
		// �����˵��ʺ�
		map.put("from", username);
		// �����˵��ʺţ������","�Ÿ���
		map.put("to", receiveMail);
		// �ʼ�����
		map.put("subject", "���������");
		// �ʼ�����
		map.put("body", content);

		// ��Ƕ�˶�����ͼƬ�����û�У���newһ������ֵ��Map
		Map<String, String> image = new HashMap<String, String>();
		image.put("a00000001", "img/a4048775_s.jpg");
		// image.put("a00000002", "F:/0343-15302r.jpg");

		// �����ľ���·��,����������newһ������ֵ��List
		List<String> list = new ArrayList<String>();
		list.add(zipPath);

		// ����ʵ��
		SendMail sm = new SendMail(map, list, image);
		// ִ�з���
		sm.send();
	}

	public void send() throws Exception {
		Properties pros = new Properties();
		pros.setProperty("mail.transport.protocol", this.protocol);
		pros.setProperty("mail.host", this.smtp);
		pros.put("mail.smtp.auth", "true");
		MySendMailAuthenticator ma = new MySendMailAuthenticator(this.username,
				this.password);
		Session session = Session.getInstance(pros, ma);
		session.setDebug(false);

		MimeMessage msg = createMessage(session);

		Transport ts = session.getTransport();
		ts.connect();
		ts.sendMessage(msg, msg.getRecipients(Message.RecipientType.TO));
		ts.close();
	}

	public MimeMessage createMessage(Session session) throws Exception {

		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(this.from));
		message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(to));
		message.setSubject(this.subject);

		MimeMultipart allMultipart = new MimeMultipart();

		// ���������ʼ����ĺ͸����ĸ���MimeBodyPart����
		MimeBodyPart contentpart = createContent(this.body);
		allMultipart.addBodyPart(contentpart);

		// ������������ʼ����ĺ͸�����MimeMultipart����
		for (int i = 0; i < list.size(); i++) {
			allMultipart.addBodyPart(createAttachment(list.get(i)));
		}

		// ���������ʼ�����Ϊ������ϳ���MimeMultipart����
		message.setContent(allMultipart);
		message.saveChanges();
		return message;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public MimeBodyPart createContent(String body) throws Exception {
		// �����������Mime��Ϣ��MimeMultipart���󣬽���MimeMultipart���󱣴浽MimeBodyPart����
		MimeBodyPart contentPart = new MimeBodyPart();
		MimeMultipart contentMultipart = new MimeMultipart("related");

		// �������ڱ���HTML���ĵ�MimeBodyPart���󣬲��������浽MimeMultipart��
		MimeBodyPart htmlbodypart = new MimeBodyPart();
		htmlbodypart.setContent(this.body, "text/html;charset=UTF-8");
		contentMultipart.addBodyPart(htmlbodypart);

		if (map != null && map.size() > 0) {
			Set<Entry<String, String>> set = map.entrySet();
			for (Iterator iterator = set.iterator(); iterator.hasNext();) {
				Entry<String, String> entry = (Entry<String, String>) iterator
						.next();

				// �������ڱ���ͼƬ��MimeBodyPart���󣬲��������浽MimeMultipart��
				MimeBodyPart gifBodyPart = new MimeBodyPart();
				FileDataSource fds = new FileDataSource(entry.getValue());// ͼƬ���ڵ�Ŀ¼�ľ���·��

				gifBodyPart.setDataHandler(new DataHandler(fds));
				gifBodyPart.setContentID(entry.getKey()); // cid��ֵ
				contentMultipart.addBodyPart(gifBodyPart);
			}
		}

		// ��MimeMultipart���󱣴浽MimeBodyPart����
		contentPart.setContent(contentMultipart);
		return contentPart;
	}

	public MimeBodyPart createAttachment(String filename) throws Exception {
		// �������渽����MimeBodyPart���󣬲����븽�����ݺ���Ӧ����Ϣ
		MimeBodyPart attachPart = new MimeBodyPart();
		FileDataSource fsd = new FileDataSource(filename);
		attachPart.setDataHandler(new DataHandler(fsd));
		attachPart.setFileName(fsd.getName());
		return attachPart;
	}

}

// ���ʼ��������ύ��֤��Ϣ
class MySendMailAuthenticator extends Authenticator {
	String username = "";
	String password = "";

	public MySendMailAuthenticator(String user, String pass) {
		this.username = user;
		this.password = pass;
	}

	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(username, password);
	}

}