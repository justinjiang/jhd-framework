package com.jhd.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.lang.Character.UnicodeBlock;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

	/**
	 * trim
	 * 
	 * @param string
	 * @return
	 */
	public static String trim(String string) {
		if (string == null)
			return "";
		else
			return string.trim();
	}

	/**
	 * isEmpty
	 * 
	 * @param string
	 * @return boolean
	 */
	public static boolean isEmpty(String string) {
		if (string == null || "".equals(string.trim()) || "null".equals(string.trim()))
			return true;
		else
			return false;
	}

	/**
	 * isNotEmpty
	 * 
	 * @param string
	 * @return boolean
	 */
	public static boolean isNotEmpty(String string) {
		return !isEmpty(string);
	}

	/**
	 * 设置默认值（为NULL时用空值代替）
	 *
	 * @param value
	 * @return
	 */
	public static String defaultString(String value) {
		return value == null || value.trim().equals("null") ? "" : value;
	}

	/**
	 * string2Integer
	 *
	 * @param string
	 * @return Integer
	 */
	public static Integer toInteger(String string) {
		String str = trim(string);
		if ("".equals(str))
			str = "0";
		if (str == null)
			str = "0";
		return new Integer(str);
	}
	/**
	 * string2Int
	 *
	 * @param string
	 * @return int
	 */
	public static int toInt(String string) {
		String str = trim(string);
		if ("".equals(str))
			str = "0";
		return Integer.parseInt(str);
	}

	/**
	 * 转换为LONG
	 * @param string
	 * @return
     */
	public static long toLong(String string) {
		String str = trim(string);
		if ("".equals(str))
			str = "0";
		return Long.parseLong(str);
	}

	/**
	 * string2Double
	 *
	 * @param string
	 * @return double
	 */
	public static double toDouble(String string) {
		String str = trim(string);
		if ("".equals(str))
			str = "0.0";
		return Double.parseDouble(str);
	}
	/**
	 * integerValue
	 *
	 * @param value
	 * @return String
	 */
	public static String fromInteger(Integer value) {
		String result = "0";
		if (value != null)
			result = String.valueOf(value.intValue());
		return result;
	}
	/**
	 * double2String
	 * 
	 * @param value
	 * @return
	 */
	public static String fromDouble(Double value) {
		String result = "0.0";
		if (value != null)
			result = String.valueOf(value.doubleValue());
		return result;
	}


	public static String urlEncode(String str)
			throws UnsupportedEncodingException {
		return URLEncoder.encode(str, "UTF-8");
	}

	public static String urlDecode(String str)
			throws UnsupportedEncodingException {
		return URLDecoder.decode(str, "UTF-8");
	}

	/**
	 * Encode a string using Base64 encoding. Used when storing passwords as
	 * cookies.
	 * 
	 * This is weak encoding in that anyone can use the decodeString routine to
	 * reverse the encoding.
	 * 
	 * @param str
	 * @return String
	 */
	public static String encodeString(String str) {
		BASE64Encoder encoder = new BASE64Encoder();
		return new String(encoder.encodeBuffer(str.getBytes())).trim();
	}

	/**
	 * Decode a string using Base64 encoding.
	 * 
	 * @param str
	 * @return String
	 */
	public static String decodeString(String str) {
		BASE64Decoder dec = new BASE64Decoder();
		try {
			return new String(dec.decodeBuffer(str));
		} catch (Exception io) {
			return "";
		}
	}

	public static String formatTrim(String str) {
		String re = "";
		StringBuffer sb = new StringBuffer("");
		if (str == null)
			return "";
		try {
			String[] s = str.split(";");
			for (int i = 0; i < s.length; i++) {
				if (!"".equals(s[i])) {
					sb.append(s[i]);
					sb.append(";");
				}
			}
			re = sb.toString();
			if (re.endsWith(";"))
				re = re.substring(0, re.length() - 1);
			return re;
		} catch (Exception io) {
			return "";
		}
	}

	/**
	 * 删除字符串中的子字符串
	 * 
	 * @param str
	 * @param substr
	 * @return
	 */
	public static String delete(String str, String substr) {
		return str.replaceAll(substr, "");
	}

	/**
	 * 删除字符串中的子字符串，并且原来的分隔符不变
	 * 
	 * @param str
	 * @param substr
	 * @param separator
	 * @return
	 */
	public static String delete(String str, String substr, String separator) {
		String[] sList = str.trim().split(separator);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < sList.length; i++) {
			if (sList[i] != null && sList[i].length() > 0
					&& !substr.equals(sList[i]))
				sb.append(sList[i].trim()).append(separator);
		}
		if (sb.length() > 0)
			sb.delete(sb.length() - separator.length(), sb.length());
		return sb.toString();
	}

	/**
	 * 转化为2位小数点的金额格式
	 * 
	 * @param price
	 * @return
	 */
	public static String turnPriceFormat(String price) {
		if (price == null || "".equals(price)) {
			return "0.00";
		} else {
			int k = price.indexOf(".");
			if (k >= 0) {
				String s = price + "00";
				return s.substring(0, k + 3);
			} else {
				return price + ".00";
			}
		}
	}

	/**
	 * 实现小数的精度变换
	 * 
	 * @param value
	 * @param scale
	 * @return
	 */
	public static double formatDigit(double value, int scale) {
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(scale, BigDecimal.ROUND_HALF_UP);
		double d = bd.doubleValue();
		bd = null;
		return d;
	}

	/**
	 * 小数转换成指定精度的小数（即保留几位小数）
	 * 
	 * @param value
	 * @param scale
	 * @return
	 */
	public static String digitToString(double value, int scale) {
		return String.valueOf(formatDigit(value, scale));
	}

	public static String delZeroString(String s) {
		if (s == null || "".equals(s))
			return s;
		if (s.lastIndexOf(".00") > 0
				&& s.lastIndexOf(".00") == (s.length() - 3)) {
			s = s.substring(0, s.length() - 3);
		}
		if (s.lastIndexOf(".0") > 0 && s.lastIndexOf(".0") == (s.length() - 2)) {
			s = s.substring(0, s.length() - 2);
		}
		return s;
	}

	/**
	 * 格式化数字，在数字之前加指定数量的0值
	 * 
	 * @param value
	 * @param len
	 * @return
	 */
	public static String formatPreDigit(int value, int len) {
		if (value < 0)
			value = 0;
		String res = "";
		for (int i = 0; i < len - String.valueOf(value).length(); i++) {
			res += "0";
		}
		return res + value;
	}

	/**
	 * 去掉传入值小数点之后无用的0
	 * 
	 * @param comRate
	 * @return
	 */
	public static String formatDot(double comRate) {
		int iCom = (int) (comRate * 100);
		if (iCom % 100 > 0) {
			return comRate + "";
		} else {
			return (int) comRate + "";
		}
	}

	public static String formatDot(String sRate) {
		double comRate = toDouble(sRate);
		return formatDot(comRate);
	}

	/*
	 * 从jsp传过来的中文参数需要在servlet中调用此方法将其转换为中文。
	 */
	public static String isoToGB2312(String param) {
		String result = "";
		try {
			if (param != null && !param.equals(""))
				result = new String(param.getBytes("ISO-8859-1"), "gb2312");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String gb2312ToUtf8(String param) {
		String result = "";
		try {
			if (param != null && !param.equals(""))
				result = new String(param.getBytes("gb2312"), "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String Utf8ToGb2312(String param) {
		String result = "";
		try {
			if (param != null && !param.equals(""))
				result = new String(param.getBytes("utf-8"), "gb2312");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 取得字符串靠左的部分
	 * 
	 * @param str
	 * @param size
	 * @return
	 */
	public static String left(String str, int size) {
		if (str == null)
			return null;
		if (str.length() < size)
			size = str.length();
		return str.substring(0, size);
	}

	/**
	 * 取得字符串靠右的部分
	 * 
	 * @param str
	 * @param size
	 * @return
	 */
	public static String right(String str, int size) {
		if (str == null)
			return null;
		if (str.length() < size)
			size = str.length();
		return str.substring(str.length() - size, str.length());
	}

	// 将 s 进行 BASE64 编码
	public static String getBASE64(String s) {
		if (s == null)
			return null;
		return (new BASE64Encoder()).encode(s.getBytes());
	}

	public static String getFromBASE64(String s) {
		if (s == null)
			return "";
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			byte[] b = decoder.decodeBuffer(s);
			return new String(b, "gb2312");
		} catch (Exception e) {
			return "";
		}
	}

	//中文符转unicode
	public static String chineseToUnicode(String str) {
		char[] myBuffer = str.toCharArray();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			UnicodeBlock ub = UnicodeBlock.of(myBuffer[i]);
			if (ub == UnicodeBlock.BASIC_LATIN) {
				sb.append(myBuffer[i]);
			}
			else if (ub == UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
				int j = (int) myBuffer[i] - 65248;
				sb.append((char) j);
			}
			else {
				short s = (short) myBuffer[i];
				String hexS = Integer.toHexString(s);
				String unicode = "\\u" + hexS;
				sb.append(unicode.toLowerCase());
			}
		}
		return sb.toString();

    }
	
	/**
     * unicode转中文
     * @param  dataStr
     * @return 中文
     */
     public static String UnicodeTochinese(String dataStr) {
         int index = 0;
         StringBuffer buffer = new StringBuffer();
   
         while(index<dataStr.length()) {
                if(!"\\u".equals(dataStr.substring(index,index+2))){
                 buffer.append(dataStr.charAt(index));
                 index++;
                 continue;
          }
         String charStr = "";
         charStr = dataStr.substring(index+2,index+6);
         char letter = (char) Integer.parseInt(charStr, 16 );
          buffer.append(letter);
                index+=6;
         }
         return buffer.toString();
     }

	/** 产生一个随机的字符串 */
	public static String RandomString(int length) {
		String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random random = new Random();
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int num = random.nextInt(62);
			buf.append(str.charAt(num));
		}
		return buf.toString();
	}

	/** 产生一个随机的字符串 */
	public static String RandomNumString(int length) {
		String str = "123456789";
		Random random = new Random();
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int num = random.nextInt(9);
			buf.append(str.charAt(num));
		}
		return buf.toString();
	}

	/**
	 * 生成一串Token
	 * @return
	 */
	public static String GenerateToken() {
		UUID uuid = UUID.randomUUID();		
		return uuid.toString().replaceAll("-", "");
	}
	
	public static boolean isMobile(String mobile) {
		if(mobile==null || "".equals(mobile)){
			return false;
		}
		Pattern p = Pattern.compile("^([1])\\d{10}$");
		Matcher m = p.matcher(mobile);
		return m.matches();
	}

	public static String fromDigit(Double value) {
		DecimalFormat df = new DecimalFormat("0.#");
		String xs = df.format(value==null ? 0.0 : value);
		return xs;
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
	}
}
