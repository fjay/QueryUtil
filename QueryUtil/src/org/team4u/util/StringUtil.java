package org.team4u.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * <h3>字符串处理类</h3> <h4>Description</h4>
 * 
 * <h4>Special Notes</h4>
 * 
 * 
 * @ver 0.5
 * @author Jay Wu 2009年11月3日
 */
public class StringUtil extends StringUtils {

	/**
	 * 替换s到e的字符串
	 * 
	 * @param s
	 *            开始字符
	 * @param e
	 *            结束字符
	 * @param content
	 *            要截取的字符
	 * @param value
	 *            替换内容
	 * @return
	 */
	public static String replaceInValue(String s, String e, String content, String value) {
		if (content == null) {
			return null;
		}
		int i = 0;
		int k = 0;
		if ((i = (content.indexOf(s, i))) >= 0 && (k = (content.indexOf(e, k))) >= 0) {
			StringBuffer sbf = new StringBuffer();
			int s_length = s.length();
			int e_length = e.length();
			sbf.append(content.substring(0, i)).append(value);
			int n = i + s_length;
			int m = k + e_length;
			for (; ((i = (content.indexOf(s, n))) >= 0 && (k = (content.indexOf(e, m))) >= 0);) {
				sbf.append(content.substring(m, i)).append(value);
				n = i + s_length;
				m = k + e_length;
			}
			sbf.append(content.substring(m, content.length()));
			return sbf.toString();
		}
		return content;
	}

	/**
	 * 截取字符串前面一顶长度的字符
	 * 
	 * @param length
	 *            要截取长度
	 * @param content
	 *            内容
	 * @return
	 */
	public static String cutString(int length, String content) {
		if (content.length() > length) {
			return content.substring(0, length);
		}
		return content;
	}

	/**
	 * 截取字符串前面一顶长度的字符,同时增加"..."结尾 commons
	 * com.loyoyo.common.utils.StringUtil.java
	 * 
	 * @param length
	 * @param content
	 * @return String
	 * @author: bobo 2008-8-22 下午04:12:12
	 */
	public static String cutSubString(int length, String content) {
		if (content.length() > length) {
			return content.substring(0, length) + "...";
		}
		return content;
	}

	/**
	 * 解码
	 * 
	 * @param src
	 * @return
	 * @author Jay.Wu
	 */
	public static String unescape(String src) {
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length());
		int lastPos = 0, pos = 0;
		char ch;
		while (lastPos < src.length()) {
			pos = src.indexOf("%", lastPos);
			if (pos == lastPos) {
				if (src.charAt(pos + 1) == 'u') {
					ch = (char) Integer.parseInt(src.substring(pos + 2, pos + 6), 16);
					tmp.append(ch);
					lastPos = pos + 6;
				} else {
					ch = (char) Integer.parseInt(src.substring(pos + 1, pos + 3), 16);
					tmp.append(ch);
					lastPos = pos + 3;
				}
			} else {
				if (pos == -1) {
					tmp.append(src.substring(lastPos));
					lastPos = src.length();
				} else {
					tmp.append(src.substring(lastPos, pos));
					lastPos = pos;
				}
			}
		}
		return tmp.toString();
	}

	/**
	 * 让普通的string支持类似EL标签的用法-简单版本 <br>
	 * <code>$contents</code>
	 * 
	 * @param paraMap
	 * @param destStr
	 * @return
	 * @author Jay.Wu
	 */
	public static String replaceKeysSimple(Map<?, ?> paraMap, String destStr) {
		if (paraMap == null) {
			return destStr;
		}

		for (Iterator<?> iter = paraMap.keySet().iterator(); iter.hasNext();) {
			String key = (String) iter.next();
			String value = paraMap.get(key) == null ? "" : String.valueOf(paraMap.get(key));

			destStr = destStr.replace(key, value);
		}

		// log.debug(destStr);
		return destStr;
	}

	/**
	 * 加强版字符串判空：支持Object和字符串为null的对象
	 * 
	 * @param target
	 * @return
	 */
	public static boolean isNotBlankPlus(Object target) {
		if (target == null) {
			return false;
		}

		String targetStr = (String) target;

		if (isBlank(targetStr)) {
			return false;
		}

		return !targetStr.equals("null");
	}

	/**
	 * 转义函数
	 * 
	 * @param rules
	 * @param target
	 * @author Jay Wu
	 * @return
	 */
	public static String transferRegex(String rules, String target) {
		if (StringUtil.isBlank(rules) || StringUtil.isBlank(target)) {
			return target;
		}

		String[] objs = rules.split(",");
		for (int i = 0; i < objs.length; i++) {
			String rule = objs[i].trim();
			target = target.replace(rule, "\\" + rule);
		}

		return target;
	}

	/**
	 * 将文本转换成无正则表达式规则
	 * 
	 * @param rules
	 * @param target
	 * @author Jay Wu
	 * @return
	 */
	public static String transfer2NoRegex(String target) {
		String[] rules = new String[] { "(", ")", "$", "^", ".", "*", "+", "?", "{", "}", "|", "[", "]" };

		return transferRegex(join(rules, ","), target);
	}

	/**
	 * 替换第一次出现的字符串
	 * 
	 * @param strSource
	 * @param strFrom
	 * @param strTo
	 * @author Jay Wu
	 * @return
	 */
	public static String replaceFirst(String strSource, String strFrom, String strTo) {
		// 如果要替换的子串为空，则直接返回源串
		if (strFrom == null || strFrom.equals("")) {
			return strSource;
		}

		String strDest = "";
		// 要替换的子串长度
		int intFromLen = strFrom.length();
		int intPos = strSource.indexOf(strFrom);

		// 获取匹配字符串的左边子串
		strDest = strDest + strSource.substring(0, intPos);
		// 加上替换后的子串
		strDest = strDest + strTo;
		// 获取匹配字符串的右边子串
		strSource = strSource.substring(intPos + intFromLen);

		// 左边+右边
		return strDest + strSource;
	}

	/**
	 * 计算出target在strSource中出现的次数
	 * 
	 * @param strSource
	 * @param target
	 * @author Jay.Wu
	 * @return
	 */
	public static int countString(String strSource, String target) {
		int result = 0;

		if (isBlank(strSource)) {
			return result;
		}

		while (strSource.indexOf(target) != -1) {
			strSource = replaceFirst(strSource, target, "");
			result++;
		}

		return result;
	}

	/**
	 * 返回第times次target在strSource出现的次数
	 * 
	 * @param strSource
	 * @param target
	 * @param times
	 * @author Jay.Wu
	 * @return
	 */
	public static int indexOf(String strSource, String target, int times) {
		int result = -1;

		if (isBlank(strSource)) {
			return result;
		}

		result = 0;
		int temp = 0;
		int count = 1;
		while ((temp = strSource.indexOf(target, temp) + 1) != -1) {
			if (times < count) {
				break;
			}

			if (result <= temp) {
				result = temp;
			} else {
				break;
			}

			count++;
		}

		return result - 1;
	}

	/**
	 * 返回第times次target在strSource出现的次数
	 * 
	 * @param strSource
	 * @param target
	 * @param times
	 * @author Jay.Wu
	 * @return
	 */
	public static int lastIndexOf(String strSource, String target, int times) {
		int result = -1;

		if (isBlank(strSource)) {
			return result;
		}

		int temp = strSource.length() - 1;
		int count = 1;
		result = strSource.length();

		while ((temp = strSource.lastIndexOf(target, temp)) != -1) {
			if (times < count) {
				break;
			}

			if (result >= temp) {
				result = temp;
			} else {
				break;
			}

			temp--;

			count++;
		}

		if (result == strSource.length()) {
			return -1;
		}

		return result;
	}

	/**
	 * 功能：验证字符串长度是否符合要求，一个汉字等于三个字符
	 * 
	 * @param strParameter
	 *            要验证的字符串
	 * @param limitLength
	 *            验证的长度
	 * @return 符合长度ture 超出范围false
	 */
	public static boolean validateStrByLength(String strParameter, int limitLength) {
		int temp_int = 0;
		byte[] b = strParameter.getBytes();

		for (int i = 0; i < b.length; i++) {
			if (b[i] >= 0) {
				temp_int = temp_int + 1;
			} else {
				temp_int = temp_int + 3;
				i++;
			}
		}

		if (temp_int > limitLength) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 判断是否大小写字母
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isLetter(String s) {
		for (int i = 0; i < s.length(); i++) {
			if (!((s.charAt(i) > 'a' && s.charAt(i) < 'z') || (s.charAt(i) > 'A' && s.charAt(i) < 'Z'))) {
				return false;
			}
		}

		return true;
	}

	public static boolean isBlank(Object value) {
		if (value == null)
			return true;

		if (value instanceof String) {
			if (StringUtil.isBlank((String) value))
				return true;
		}

		return false;
	}

	public static boolean isNotBlank(Object value) {
		return !isBlank(value);
	}

	public static String replace(String regex, String orgi, int pos) {
		if (isBlank(orgi) || isBlank(regex)) {
			return orgi;
		}

		Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(orgi);

		while (m.find()) {
			String outStr = m.group(pos);
			orgi = orgi.replace(m.group(0), outStr);
		}

		return orgi;
	}

	/**
	 * 根据正则表达式快速查找指定位置的字符串
	 * 
	 * @param regex
	 * @param orgi
	 * @param pos
	 * @return
	 */
	public static String find(String regex, String orgi, int pos) {
		if (isBlank(orgi) || isBlank(regex)) {
			return null;
		}

		Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(orgi.replaceAll("\r|\n", ""));

		while (m.find()) {
			String outStr = m.group(pos);
			return outStr;
		}

		return null;
	}

	public static Map<String, Object> String2Map(String mapStr) {
		Map<String, Object> map = new HashMap<String, Object>();
		mapStr = mapStr.substring(1, mapStr.length() - 1);
		String[] mapElements = mapStr.split(",");

		for (int i = 0; i < mapElements.length; i++) {
			String[] obj = mapElements[i].split("=");
			if (obj.length == 1) {
				continue;
			}

			String key = obj[0];
			String value = obj[1];
			map.put(key, value);
		}

		return map;
	}

	public static void sql2code() {
		Scanner objScanner = new Scanner(System.in);
		System.out.println("请输入StringBuffer变量的名称：");
		String strBuffer = objScanner.next();
		System.out.println("请输入SQL语句(最后一行需加回车符)：");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String s;
		int index = 0;
		try {
			while ((s = br.readLine()).length() != 0) {
				if (index == 0) {
					System.out.println("\n\n您要的代码如下：\n");
				}
				System.out.println(strBuffer + ".append(\" " + s + " \");");
				index++;
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

	}

	/**
	 * 模糊查找列表
	 * 
	 * @param list
	 *            目标列表
	 * @param str
	 *            欲查找的关键字
	 * @param flags
	 *            Pattern的常数类型
	 * @return 关键字在目标列表所处位置的列表
	 */
	public static ArrayList<Integer> fuzzySearch(ArrayList<String> list, String str, int flags) {
		String temp;
		String regex = str;
		Pattern p;
		ArrayList<Integer> index = new ArrayList<Integer>();
		if (flags == -1)
			p = Pattern.compile(regex);
		else
			p = Pattern.compile(regex, flags);
		Matcher m;

		for (int i = 0; i < list.size(); i++) {
			temp = list.get(i);
			m = p.matcher(temp);
			while (m.find())
				index.add(i);
		}
		return index;
	}

	/**
	 * 模糊搜索字符串
	 * 
	 * @param sourceStr
	 *            目标字符串
	 * @param destinationStr
	 *            欲查找的关键字
	 * @param flags
	 *            Pattern的常数类型
	 * @return 关键字在目标字符串所处位置的列表
	 */
	public static ArrayList<Integer> fuzzySearch(String sourceStr, String destinationStr, int flags) {
		String regex = destinationStr;
		Pattern p;
		ArrayList<Integer> index = new ArrayList<Integer>();
		if (flags == -1)
			p = Pattern.compile(regex);
		else
			p = Pattern.compile(regex, flags);
		Matcher m = p.matcher(sourceStr);
		while (m.find()) {
			index.add(m.start());
		}
		return index;
	}

	/**
	 * 编码转换
	 * 
	 * @param source
	 *            目标字符串
	 * @param srcEncode
	 *            目标字符串编码（原编码）
	 * @param destEncode
	 *            目的字符串编码
	 * @return 转换后的字符串
	 */
	public static String ConverterStringCode(String source, String srcEncode, String destEncode) {
		if (source != null) {
			try {
				return new String(source.getBytes(srcEncode), destEncode);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return "";
	}

	/**
	 * 去除HTML标记
	 * 
	 * @param htmlstring
	 *            包括HTML的源码
	 * @return 已经去除后的文字
	 */
	public static String noHtml(String htmlstring) {
		if (isBlank(htmlstring)) {
			return htmlstring;
		}

		// 删除脚本
		Pattern.compile("(?s)<\\s*script[^>]*?>.*?<\\s*/script\\s*>", Pattern.CASE_INSENSITIVE)
				.matcher(htmlstring).replaceAll("");
		// 删除HTML
		htmlstring = Pattern.compile("<\\s*(br|p).*?>", Pattern.CASE_INSENSITIVE).matcher(htmlstring)
				.replaceAll("[br]");
		htmlstring = Pattern.compile("(?s)<(.[^>]*?)>", Pattern.CASE_INSENSITIVE).matcher(htmlstring)
				.replaceAll("");
		htmlstring = Pattern.compile("([\\r\\n])[\\s]+", Pattern.CASE_INSENSITIVE).matcher(htmlstring)
				.replaceAll("");
		htmlstring = Pattern.compile("-->", Pattern.CASE_INSENSITIVE).matcher(htmlstring).replaceAll("");
		htmlstring = Pattern.compile("<!--.*", Pattern.CASE_INSENSITIVE).matcher(htmlstring).replaceAll("");

		htmlstring = Pattern.compile("&(quot|#34);", Pattern.CASE_INSENSITIVE).matcher(htmlstring)
				.replaceAll("\"");
		htmlstring = Pattern.compile("&(amp|#38);", Pattern.CASE_INSENSITIVE).matcher(htmlstring)
				.replaceAll("&");
		htmlstring = Pattern.compile("&(lt|#60);", Pattern.CASE_INSENSITIVE).matcher(htmlstring)
				.replaceAll("<");
		htmlstring = Pattern.compile("&(gt|#62);", Pattern.CASE_INSENSITIVE).matcher(htmlstring)
				.replaceAll(">");
		htmlstring = Pattern.compile("&(nbsp|#160);", Pattern.CASE_INSENSITIVE).matcher(htmlstring)
				.replaceAll("   ");
		htmlstring = Pattern.compile("&(iexcl|#161);", Pattern.CASE_INSENSITIVE).matcher(htmlstring)
				.replaceAll("\\xa1");
		htmlstring = Pattern.compile("&(cent|#162);", Pattern.CASE_INSENSITIVE).matcher(htmlstring)
				.replaceAll("\\xa2");
		htmlstring = Pattern.compile("&(pound|#163);", Pattern.CASE_INSENSITIVE).matcher(htmlstring)
				.replaceAll("\\xa3");
		htmlstring = Pattern.compile("&(copy|#169);", Pattern.CASE_INSENSITIVE).matcher(htmlstring)
				.replaceAll("\\xa9");
		htmlstring = Pattern.compile("&#(\\d+);", Pattern.CASE_INSENSITIVE).matcher(htmlstring)
				.replaceAll("");
		htmlstring = htmlstring.replace("[br]", "\r\n");

		return htmlstring;
	}

	/**
	 * 由普通文本转换成HTML源代码
	 * 
	 * @param txt
	 *            文本内容
	 * @return 转换后的源代码
	 */
	public static String toHtml(String txt) {
		if (isEmpty(txt)) {
			return txt;
		}

		txt = txt.replace(">", "&gt;");
		txt = txt.replace("<", "&lt;");
		txt = txt.replace("   ", "&nbsp;");
		txt = txt.replace("%20", "&nbsp;");
		txt = txt.replace("\r\n", "<br/>");
		return txt;
	}

	/**
	 * 判断value是否在targets之内
	 * 
	 * @param targets
	 * @param value
	 * @param ignoreCase
	 * @return
	 */
	public static boolean in(String[] targets, String value, boolean ignoreCase) {
		if (targets == null) {
			return false;
		}

		for (String target : targets) {
			if (ignoreCase) {
				if (value.equalsIgnoreCase(target)) {
					return true;
				}
			} else {
				if (value.equals(target)) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * 判断value是否在targets的contains之内
	 * 
	 * @param targets
	 * @param value
	 * @param ignoreCase
	 * @return
	 */
	public static boolean contains(Object[] targets, String value) {
		if (targets == null) {
			return false;
		}

		for (Object target : targets) {
			if (target != null) {
				if (value.contains(target.toString())) {
					return true;
				}
			}
		}

		return false;
	}

	public static String showSpendTime(Long millisecond) {
		Long second = millisecond / 1000;
		Long minute = second / 60;
		Long secAfterMin = second % 60;
		String result = "Running time " + millisecond + " ms ( " + minute + " m " + secAfterMin + " s )";
		return result;
	}

	/**
	 * 快速替换 <code>
	 * oRules:1#2#3
	 * dRules:A#B#C
	 * orgi:1xx2xx3xx
	 * 其中每个rule都支持正则表达式
	 * 处理后将变成：AxxBxxCxx
	 * </code>
	 * 
	 * @param oRules
	 * @param dRules
	 * @param orgi
	 * @return
	 */
	public static String replace(String oRules, String dRules, String orgi) {
		if (isBlank(orgi) || isBlank(oRules) || isBlank(dRules)) {
			return orgi;
		}

		String[] r = oRules.split("#");
		String[] d = dRules.split("#");

		for (int i = 0; i < r.length; i++) {
			orgi = orgi.replaceAll(r[i], d[i]);
		}

		return orgi;
	}

	public static String toTxt(String content) {
		if (StringUtil.isNotBlank(content)) {
			content = StringUtil.trim(StringUtil.noHtml(content));

			if (StringUtil.isNotBlank(content)) {
				content = content.replaceAll("^[\n\r\t]+$", "");
			}
		}

		return content;
	}

	public static String encode(String content) {
		StringBuffer b = new StringBuffer("");

		if (StringUtil.isNotBlank(content)) {
			char[] c = content.toCharArray();
			for (char d : c) {
				if (d < 256) {
					b.append(d);
				} else {
					String r = Integer.toHexString((int) d).toUpperCase();
					b.append("\\u" + r);
				}
			}
		}

		return b.toString();
	}

	public static String map2String(Map<?, ?> map) {
		StringBuffer result = new StringBuffer("");

		if (isNotBlank(map)) {
			for (Object key : map.keySet()) {
				result.append(key + ":" + map.get(key) + ";");
			}
		}

		return result.toString();
	}
}
