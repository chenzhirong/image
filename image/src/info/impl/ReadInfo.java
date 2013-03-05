package info.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class ReadInfo {
	public static Properties p;
	private static ReadInfo readinfo;
	private static OutputStream infoOut;
	private static InputStream input;

	/**
	 * @param args
	 */
	public static Properties getProperties() {
		if (p == null) {
			p = new Properties();
		}
		return p;
	}

	// public static void main(String[] args) {
	// System.out.println(ReadInfo.readInfoType("mailName"));
	// ReadInfo.setInfoType("mailName", "mailName");
	// }

	/**
	 * 获取属性值
	 * 
	 * @param value
	 * @return
	 */
	public static String readInfoType(String mailName) {
		String typeValue = "";
		getProperties();
		try {
			if (input == null) {
				input = new BufferedInputStream(new FileInputStream(
						"info.manage"));
			}
			p.load(input);
			typeValue = p.getProperty(mailName);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		return typeValue;
	}

	/**
	 * 设置属性值
	 * 
	 * @param value
	 * @return
	 */
	public static void setInfoType(String key, String value) {
		getProperties();
		try {
			if (infoOut == null) {
				infoOut = new BufferedOutputStream(new FileOutputStream(
						"info.manage"));
			}
			p.setProperty(key, value);
			p.store(infoOut, "jonee");
			//infoOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
