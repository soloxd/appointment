package com.zzszxyy.appointment.util;

import java.util.Random;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

/**
 *  生成各种ID
 */
public class IdUtil {
	public static final long DIFF_TIMESTAMP = 1000000000000L;

	/**
	 * 创建id号,依据时间戳
	 * @return
	 */
	public static String getId() {
		try {
			long longtime = System.currentTimeMillis() + getRandomLongValue();
			return IntUtil.c10to32(longtime - DIFF_TIMESTAMP);
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 生成UUID
	 * @return
	 */
	public static String generateUUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}

	/**
	 * 字符串生成0-1之间的小数
	 * @param str
	 * @return
	 */
	public static double genDecimal(String str) {
		Integer hash = Math.abs(str.hashCode());
		String tmp = "0." + StringUtils.reverse(hash.toString());
		return Double.parseDouble(tmp);
	}

	/**
	 * 得到随机长整数
	 * @return
	 */
	public static long getRandomLongValue() {
		Random r = new Random();
		return r.nextLong() + r.nextInt();
	}
}
