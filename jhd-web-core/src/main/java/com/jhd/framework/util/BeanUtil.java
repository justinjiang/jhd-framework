package com.jhd.framework.util;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.util.ClassUtils;

import java.beans.PropertyDescriptor;
import java.util.Random;

public class BeanUtil extends PropertyUtils {

	public static void copyProperties(Object dest, Object src) {
		copyProperties(dest, src, true);
	}

	public static void copyProperties(Object dest, Object src, boolean skipNull) {
		// Validate existence of the specified beans
		if (dest == null) {
			throw new IllegalArgumentException("No destination bean specified");
		}

		if (src == null) {
			throw new IllegalArgumentException("No origin bean specified");
		}

		PropertyDescriptor[] origDescriptors = PropertyUtils
				.getPropertyDescriptors(src);

		for (int i = 0; i < origDescriptors.length; i++) {
			String name = origDescriptors[i].getName();

			if ("class".equals(name)) {
				continue; // No point in trying to set an object's class
			}

			if (PropertyUtils.isReadable(src, name)
					&& PropertyUtils.isWriteable(dest, name)) {

				Object value = null;
				try {
					value = PropertyUtils.getSimpleProperty(src, name);
				} catch (Exception e) {
					throw new RuntimeException();
				}

				if (skipNull) {
					if (value == null) {
						continue;
					}
				} else if (value == null) {
					value = null;
				}

				try {
					// copyProperties(dest, name, value);
					setProperty(dest, name, value);
				} catch (Exception e) {
					throw new RuntimeException();
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static String extractLShortName(Class clz) {
		String shortName = ClassUtils.getShortName(clz);
		return Character.toLowerCase(shortName.charAt(0))
				+ shortName.substring(1);
	}

	public static String getNotNullStr(String value) {

		return (value != null) ? value : "";
	}

	public static String getNotNullStr(Object obj) {

		return (obj != null) ? obj.toString() : "";
	}

	public static String getRandomString() {
		Random random = new Random();
		int randomName = random.nextInt(99999999);

		return "" + randomName;
	}

	public static String getRandomString(int len) {
		int seed = 10 ^ len - 1;

		Random random = new Random();
		int randomName = random.nextInt(seed);

		return "" + randomName;
	}

	// public static String genRandomPassword() {
	// Random r = new Random();
	// String newPw = "";
	// int i;
	// for (i = 6; i > 0; i--) {
	// newPw += AlphaHexadecimalUtil.ALPHA_SEQ[r.nextInt(34)];
	// }
	//
	// return newPw;
	//	}
}
