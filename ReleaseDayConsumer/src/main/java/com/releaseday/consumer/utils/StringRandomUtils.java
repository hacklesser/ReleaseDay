package com.releaseday.consumer.utils;

import java.util.Random;

public class StringRandomUtils {

	/**
	 * 随机生成盐值（由数字、大小写字母组成的十位随机数）
	 * 
	 * @return
	 */
	public static String generateRandomSalt() {
		
		String val = "";
		Random random = new Random();

		// 生成十位随机数
		for (int i = 0; i < 10; i++) {
			String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
			// 输出字母还是数字
			if ("char".equalsIgnoreCase(charOrNum)) {
				// 输出是大写字母还是小写字母
				int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
				val += (char) (random.nextInt(26) + temp);
			} else if ("num".equalsIgnoreCase(charOrNum)) {
				val += String.valueOf(random.nextInt(10));
			}
		}
		return val;
	}
	
	/**
	 * 随机生成验证码随机值（由数字、大小写字母组成的八位随机数）
	 * 
	 * @return
	 */
	public static String generateRandomValidateCode() {
		
		String val = "";
		Random random = new Random();

		// 生成八位随机数
		for (int i = 0; i < 8; i++) {
			String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
			// 输出字母还是数字
			if ("char".equalsIgnoreCase(charOrNum)) {
				// 输出是大写字母还是小写字母
				int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
				val += (char) (random.nextInt(26) + temp);
			} else if ("num".equalsIgnoreCase(charOrNum)) {
				val += String.valueOf(random.nextInt(10));
			}
		}
		return val;
	}
	
}
