package com.zhanglugao.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;

/**
 * 序列化工具类
 * @project: guopei5-common
 * @comments: 序列化工具类
 * @JDK Version:  JDK1.7
 * @author: liuwenbo
 * @create date: 2015年7月1日下午1:06:16
 * @version: V1.0
 */
public class SerializeUtil {
	private static final Log log = LogFactory.getLog(SerializeUtil.class);
	/**
	 * 序列化
	 * @param object
	 * @return 字节数组
	 */
	public static byte[] serialize(Object object) {
		ObjectOutputStream oos = null;
		ByteArrayOutputStream baos = null;
		try {
			// 序列化
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			byte[] bytes = baos.toByteArray();
			return bytes;
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return null;
		} finally {
			try {
				if(oos != null) {
					oos.close();
				}
			} catch (IOException e) {
				log.error(e.getMessage(),e);
			}
			try {
				if(baos != null) {
					baos.close();
				}
			} catch (IOException e) {
				log.error(e.getMessage(),e);
			}
		}
	}
	
	/**
	 * 反序列化
	 * @param bytes
	 * @return 对象
	 */
	public static Object unserialize(byte[] bytes) {
		ByteArrayInputStream bais = null;
		ObjectInputStream ois = null;
		try {
			// 反序列化
			bais = new ByteArrayInputStream(bytes);
			ois = new ObjectInputStream(bais);
			return ois.readObject();
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			return null;
		} finally {
			try {
				if(bais != null) {
					bais.close();
				}
			} catch (IOException e) {
				log.error(e.getMessage(),e);
			}
			try {
				if(ois != null) {
					ois.close();
				}
			} catch (IOException e) {
				log.error(e.getMessage(),e);
			}
		}
	}
	
}
