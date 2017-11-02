package com.chee.util;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.log4j.Logger;

public final class StringUtils {
	public static Logger log = Logger.getLogger(StringUtils.class);

	public static String toString(Object obj) {
		return ReflectionToStringBuilder.toString(obj);
	}

	public static String nvl(String s) {
		return s == null ? "" : s.trim();
	}

	public static boolean isEmpty(String str) {
		return (str == null) || ("".equals(str));
	}

	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	public static String leftWildcardString(String str) {
		String reStr = nvl(str);
		if (isNotEmpty(reStr)) {
			reStr = "%" + reStr;
		}
		return reStr;
	}

	public static String rightWildcardString(String str) {
		String reStr = nvl(str);
		if (isNotEmpty(reStr)) {
			reStr = reStr + "%";
		}
		return reStr;
	}

	public static String allWildcardString(String str) {
		String reStr = nvl(str);
		if (isNotEmpty(reStr)) {
			reStr = "%" + reStr + "%";
		}
		return reStr;
	}

	public static Double toDouble(String s) {
		String reStr = nvl(s);
		Double reDouble = null;
		if (isNotEmpty(reStr)) {
			reDouble = Double.valueOf(Double.parseDouble(reStr));
		}
		return reDouble;
	}

	public static Integer toInteger(String str) {
		String reStr = nvl(str);
		Integer reInteger = null;
		if (isNotEmpty(reStr)) {
			reInteger = Integer.valueOf(Integer.parseInt(reStr));
		}
		return reInteger;
	}

	public static Long toLong(String str) {
		String reStr = nvl(str);
		Long reLong = null;
		if (isNotEmpty(reStr)) {
			reLong = Long.valueOf(Long.parseLong(reStr));
		}
		return reLong;
	}

	public static long str2Long(String str) {
		String reStr = nvl(str);
		long reLong = 0L;
		if (isNotEmpty(reStr)) {
			reLong = Long.valueOf(reStr).longValue();
		}
		return reLong;
	}

	public static Float toFloat(String str) {
		String reStr = nvl(str);
		Float reFloat = null;
		if (isNotEmpty(reStr)) {
			reFloat = Float.valueOf(Float.parseFloat(reStr));
		}
		return reFloat;
	}

	public static String double2String(Double d) {
		String str = "";
		if (d != null) {
			str = String.valueOf(d);
		}
		return str;
	}

	public static String integer2String(Integer i) {
		String str = "";
		if (i != null) {
			str = String.valueOf(i);
		}
		return str;
	}

	public static String long2String(Long i) {
		String str = "";
		if (i != null) {
			str = String.valueOf(i);
		}
		return str;
	}

	public static String float2String(Float i) {
		String str = "";
		if (i != null) {
			str = String.valueOf(i);
		}
		return str;
	}

	public static String createRadomString(int size) {
		if (size <= 0) {
			return "";
		}
		if (size > 9) {
			size = 9;
		}
		int[] maxNum = { 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000, 1000000000 };
		String[] zeroStr = { "", "0", "00", "000", "0000", "00000", "000000", "0000000", "00000000", "000000000" };
		String randomStr = String.valueOf(new Random().nextInt(maxNum[(size - 1)]));
		int extraZeroNum = size - randomStr.length();
		extraZeroNum = extraZeroNum < 0 ? 0 : extraZeroNum;
		return new StringBuilder().append(zeroStr[extraZeroNum]).append(randomStr).toString();
	}

	public static String getDevTypeFromDevCode(String devCode) {
		if ((devCode != null) && (devCode.trim().length() >= 12)) {
			return devCode.substring(6, 8);
		}
		return "";
	}

	public static String[] getOprtArray(String description) {
		if ((description != null) && (description.trim().length() > 0)) {
			return description.split("\\.");
		}
		return null;
	}

	public static boolean checkIsNum(String value) {
		boolean flag = false;
		if (isNotEmpty(value)) {
			flag = value.matches("[0-9]*");
		}
		return flag;
	}

	public static boolean checkSpecialChar(String value) {
		boolean flag = false;
		if (isNotEmpty(value)) {
			flag = value.matches("[a-zA-Z0-9一-龥]+");
		}
		return flag;
	}

	public static String getReplaceStr(String str, String org0, String org1) {
		if ((str != null) && (org0 != null) && (org1 != null)) {
			str = str.replace(org0, org1);
		}
		return str;
	}

	public static String delAllBlankSpace(String str) {
		if (str != null) {
			str = str.replace(" ", "");
		}
		return str;
	}

	public static String rep(String val) {
		if (org.apache.commons.lang.StringUtils.isBlank(val)) {
			return "";
		}
		return val.replaceAll("\"", "&quot").replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\\s", "&nbsp;");
	}

	public static boolean isMobile(String sjhm) {
		boolean boo = false;
		try {
			if (isEmpty(sjhm)) {
				return false;
			}
			Pattern ptn = Pattern.compile("^[1][3,4,5,6,7,8,9][0-9]{9}$");
			Matcher mcr = ptn.matcher(sjhm);
			boo = mcr.matches();
		} catch (Exception e) {
			log.error("It has error when check sjhm");
		}
		return boo;
	}

	public static String join(Object[] array, String prefix, String separator) {
		if (array == null) {
			return null;
		}
		return join(array, prefix, separator, 0, array.length);
	}

	public static String join(Object[] array, String prefix, String separator, int startIndex, int endIndex) {
		if (array == null) {
			return null;
		}
		if (prefix == null) {
			prefix = "";
		}
		if (separator == null) {
			separator = "";
		}
		int bufSize = endIndex - startIndex;
		if (bufSize <= 0) {
			return "";
		}
		bufSize *= ((array[startIndex] == null ? 16 : array[startIndex].toString().length()) + separator.length());

		StringBuffer buf = new StringBuffer(bufSize);
		for (int i = startIndex; i < endIndex; i++) {
			if (i > startIndex) {
				buf.append(separator);
			}
			if (array[i] != null) {
				buf.append(prefix + array[i]);
			}
		}
		return buf.toString();
	}

	public static boolean areNotBlank(String... strings) {
		if ((strings == null) || (strings.length == 0)) {
			return false;
		}
		String[] arrayOfString = strings;
		int j = strings.length;
		for (int i = 0; i < j; i++) {
			String string = arrayOfString[i];
			if ((string == null) || ("".equals(string.trim()))) {
				return false;
			}
		}
		return true;
	}

	public static boolean isNotBlank(String string) {
		return (string != null) && (!string.trim().equals(""));
	}

	public static boolean isBlank(String string) {
		return (string == null) || (string.trim().equals(""));
	}

	public static boolean isNumeric(String str) {
		if (str == null) {
			return false;
		}
		int i = str.length();
		do {
			int chr = str.charAt(i);
			if ((chr < 48) || (chr > 57)) {
				return false;
			}
			i--;
		} while (i >= 0);
		return true;
	}

	public static String escapeHtml(String text) {
		if (isBlank(text)) {
			return text;
		}
		return text.replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;").replace("'", "&#x27;").replace("/", "&#x2F;");
	}
}
