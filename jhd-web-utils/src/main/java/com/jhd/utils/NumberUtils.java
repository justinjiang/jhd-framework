package com.jhd.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 数字相关辅助类
 * 
 * @author justin
 * @version $Date: 2010-04-28 &
 */
public class NumberUtils {

	/**
	 * 获得默认的Integer值，默认0
	 *
	 * @param value
	 * @return
	 */
	public static Integer defaultValue(Integer value) {
		return defaultValue(value, 0);
	}

	/**
	 * 获得默认的Integer值
	 *
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static Integer defaultValue(Integer value, Integer defaultValue) {
		if (value == null)
			return defaultValue;
		else
			return value;
	}

	/**
	 * 获得默认的Long值，默认0
	 * 
	 * @param value
	 * @return
	 */
	public static Long defaultValue(Long value) {
		return defaultValue(value, 0L);
	}
	public static Long defaultValue(Long value, Long defaultValue) {
		if (value == null)
			return defaultValue;
		else
			return value;
	}

	/**
	 * 获得默认的Double值，默认0.0
	 * 
	 * @param value
	 * @return
	 */
	public static Double defaultValue(Double value) {
		return defaultValue(value, 0.0);
	}
	/**
	 * 获得默认的Double值
	 *
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static Double defaultValue(Double value, Double defaultValue) {
		if (value == null)
			return defaultValue;
		else
			return value;
	}

	/**
	 * 获取金钱的精度
	 * 
	 * @param value
	 * @return
	 */
	public static double getScaleValue4Money(double value) {
		return getScaleValue(value, 2);
	}

	/**
	 * 获取四舍五入的精度double
	 * 
	 * @param value
	 * @param sacle
	 * @return
	 */
	public static double getScaleValue(double value, int sacle) {
		return getScaleValue(value, sacle, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * 获取精度的double
	 * 
	 * @param value
	 * @param sacle
	 * @param round
	 * @return
	 */
	public static double getScaleValue(double value, int sacle, int round) {
		BigDecimal db = new BigDecimal(value);
		db = db.setScale(sacle, round);
		return db.doubleValue();
	}

	/**
	 * 两个数相减的金钱值
	 *
	 * @param value1
	 * @param value2
	 * @return
	 */
	public static double sub4Money(Double value1, Double value2) {
		return getScaleValue4Money(defaultValue(value1).doubleValue()
				- defaultValue(value2).doubleValue());
	}
	public static String formatNumber(Double value,String format){
		DecimalFormat myformat = new  DecimalFormat(format); 
		return myformat.format(value);
	}

	/**
	 * 两个数相加的金钱值
	 *
	 * @param value1
	 * @param value2
	 * @return
	 */
	public static double add4Money(Double value1, Double value2) {
		return getScaleValue4Money(defaultValue(value1).doubleValue()
				+ defaultValue(value2).doubleValue());
	}

	/**
	 * 两个数相乘的金钱值
	 * 
	 * @param value1
	 * @param value2
	 * @return
	 */
	public static double multi4Money(Double value1, Double value2) {
		return getScaleValue4Money(defaultValue(value1).doubleValue()
				* defaultValue(value2).doubleValue());
	}

	/**
	 * 两个数相除的金钱值
	 * 
	 * @param value1
	 * @param value2
	 * @return
	 */
	public static double divide4Money(Double value1, Double value2) {
		return getScaleValue4Money(defaultValue(value1).doubleValue()
				/ defaultValue(value2).doubleValue());
	}
	
}
