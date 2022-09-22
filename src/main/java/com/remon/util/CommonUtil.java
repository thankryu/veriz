package com.remon.util;

import java.io.IOException;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonUtil {

	private static final Logger log = LoggerFactory.getLogger(CommonUtil.class);

	// DB 접속
	static Connection con = null;

	/**
	 * 리퀘스트 정보를 맵에 담아 리턴
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static HashMap<String, String> request2map(HttpServletRequest request) throws Exception {
		HashMap<String, String> resultMap = new HashMap<String, String>();

		@SuppressWarnings("unchecked")
		Enumeration<String> paramName = request.getParameterNames();
		while (paramName.hasMoreElements()) {
			String param = paramName.nextElement();
			if (request.getParameterValues(param) != null) {
				resultMap.put(param, request.getParameterValues(param)[0]);
				for (int i = 0; i < request.getParameterValues(param).length; i++) {
					resultMap.put(param + i, request.getParameterValues(param)[i]);
				}
			} else {
				resultMap.put(param, request.getParameter(param));
			}
			resultMap.put(param, request.getParameter(param));
		}
		return resultMap;
	}

	/**
	 * 
	 * 리퀘스트 정보를 맵에 담아 리턴 (※주의사항 :: Object타입 맵이므로 데이터 꺼낼 시 형변환 필수)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static HashMap<String, Object> request2mapObject(HttpServletRequest request) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		@SuppressWarnings("unchecked")
		Enumeration<String> paramName = request.getParameterNames();
		while (paramName.hasMoreElements()) {
			String param = paramName.nextElement();
			//			log.debug("param:" + param + ",value:" + request.getParameter(param));
			if (request.getParameterValues(param) != null) {
				resultMap.put(param, request.getParameterValues(param)[0]);
				resultMap.put(param + "_length", request.getParameterValues(param).length); // length 추가
				for (int i = 0; i < request.getParameterValues(param).length; i++) {
					resultMap.put(param + i, request.getParameterValues(param)[i]);
				}
			} else {
				resultMap.put(param, request.getParameter(param));
			}
		}
		return resultMap;
	}

	public static HashMap<String, Object> request2mapObject2(HttpServletRequest request) throws Exception {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();

		@SuppressWarnings("unchecked")
		Enumeration<String> paramName = request.getParameterNames();
		while (paramName.hasMoreElements()) {
			String param = paramName.nextElement();
			if (request.getParameterValues(param) != null) {
				resultMap.put(param, request.getParameterValues(param)[0]);
				for (int i = 0; i < request.getParameterValues(param).length; i++) {
					resultMap.put(param, request.getParameterValues(param)[i]);
				}
			} else {
				if (!"".equals(request.getParameter(param))) {
					resultMap.put(param, request.getParameter(param));
				} else {
					resultMap.put(param, null);
				}
			}
		}
		return resultMap;
	}

	/**
	 * 전달받은 맵 파라미터를 Json Object 타입으로 response 처리한다.
	 * 
	 * @param map
	 * @param response
	 * @throws Exception
	 */
	public static void response2jsonMap(Map<String, Object> map, HttpServletResponse response) throws Exception {

		JSONObject jo = new JSONObject(map);

		response.setCharacterEncoding("UTF-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("application/json");
		response.getWriter().print(jo);
		response.getWriter().flush();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void ResponseData(HttpServletResponse response, String resultCode, String resultMessage, List<HashMap<String, Object>> list) throws IOException {
		HashMap map = new HashMap();
		map.put("result_code", resultCode);
		map.put("result_msg", resultMessage);
		if (list != null)
			map.put("result_count", list.size());
		else
			map.put("result_count", 0);
		map.put("result_data", list);

		JSONObject jo = new JSONObject(map);

		response.setCharacterEncoding("UTF-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Cache-Control", "no-cache");
		response.resetBuffer();
		response.setContentType("application/json");
		response.getWriter().print(jo);
		response.getWriter().flush();
	}

	public static void ResponseArrayListData(HttpServletResponse response, String resultCode, String resultMessage, ArrayList<HashMap<String, Object>> list) throws IOException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("result_code", resultCode);
		map.put("result_msg", resultMessage);
		if (list != null)
			map.put("result_count", list.size());
		else
			map.put("result_count", 0);
		map.put("result_data", list);

		JSONObject jo = new JSONObject(map);

		response.setCharacterEncoding("UTF-8");
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Cache-Control", "no-cache");
		response.resetBuffer();
		response.setContentType("application/json");
		response.getWriter().print(jo);
		response.getWriter().flush();
	}

	/**
	 * 전달 값의 null 여부 및 공백여부를 판단하여 지정된 기본값을 반환한다.
	 * 
	 * @param value
	 * @param defaultValue
	 * @return
	 * @throws Exception
	 */
	public static String nvl(Object value, String defaultValue) throws Exception {
		if (value == null || "".equals(value))
			return defaultValue;
		else
			return (String) value;
	}

	/**
	 * 전달 값의 null 여부 및 공백여부를 판단하여 해당할 경우 빈 문자열을 반환한다.
	 * 
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public static String nvl(Object value) throws Exception {
		return nvl(value, "");
	}

	/**
	 * 전달 값의 null 여부 및 공백여부를 판단하여 Integer 타입으로 반환한다. 전달 값이 숫자가 아니라면 NumberFormatException 발생 주의.
	 * 
	 * @param value
	 * @param defaultInt
	 * @return
	 * @throws Exception
	 */
	public static Integer nvl(Object value, int defaultInt) throws Exception {
		if (value == null || "".equals(String.valueOf(value)))
			return defaultInt;
		else
			return Integer.valueOf(String.valueOf(value));
	}
	
	public static Double nvl(Object value, Double defaultInt) throws Exception {
		if (value == null || "".equals(String.valueOf(value)))
			return defaultInt;
		else
			return Double.valueOf(String.valueOf(value));
	}

	public static String lpad(String value, int length, String padChar) throws Exception {
		StringBuilder sbAddChar = new StringBuilder();

		for (int i = value.length(); i < length; i++) {
			sbAddChar.append(padChar);
		}

		sbAddChar.append(value);

		return sbAddChar.toString();
	}

	public static String rpad(String value, int length, String padChar) throws Exception {
		StringBuilder sbAddChar = new StringBuilder();

		sbAddChar.append(value);

		for (int i = value.length(); i < length; i++) {
			sbAddChar.append(padChar);
		}

		return sbAddChar.toString();
	}

	public static String getProp(String key) throws Exception {

		return key;

	}

	/**
	 * value를 pattern 으로 변환한다. 1234567, #,### -> 1,234,567 getFormatNumber
	 */
	public static String getFormatNumber(String value, String pattern) {
		StringBuffer buffer = new StringBuffer();
		DecimalFormat df = new DecimalFormat(pattern);
		try {
			buffer.append(df.format(Long.parseLong(value)));
		} catch (NumberFormatException e) {
			buffer.append(df.format(Double.parseDouble(value)));
		}
		return buffer.toString();
	}

	/**
	 * 주어진 숫자를 파일 사이즈 형식으로 리턴. String
	 */
	public static String getFileSize(long size, int length) {
		int s = 0;
		String suffix = "";
		int round = 0;

		int lengthCalc = (int) Math.pow(10, length);

		while (size / 1024 > 0) {
			round = Math.round((((float) (size % 1024) / 1024) * lengthCalc));
			size = size / 1024;
			s++;
		}
		switch (s) {
		case 0:
			suffix = "B";
			break;
		case 1:
			suffix = "KB";
			break;
		case 2:
			suffix = "MB";
			break;
		case 3:
			suffix = "GB";
			break;
		case 4:
			suffix = "TB";
			break;
		default:
			suffix = "B";
			break;
		}
		String str = String.valueOf(size);
		if (round > 0)
			str += "." + String.valueOf(round);
		str += suffix;
		return str;
	}

	public static String getFileSize(long size) {
		return getFileSize(size, 1);
	}

	/**
	 * 주어진 문자열에서 & < > " ' 의 문자를 unicode로 변환하여 리턴. String
	 */
	public static String escapeXml(String buffer) {
		char specialCharactersRepresentation[][];
		specialCharactersRepresentation = new char[63][];
		specialCharactersRepresentation[38] = "&amp;".toCharArray();
		specialCharactersRepresentation[60] = "&lt;".toCharArray();
		specialCharactersRepresentation[62] = "&gt;".toCharArray();
		specialCharactersRepresentation[34] = "&#034;".toCharArray();
		specialCharactersRepresentation[39] = "&#039;".toCharArray();

		int start = 0;
		int length = buffer.length();
		char arrayBuffer[] = buffer.toCharArray();
		StringBuffer escapedBuffer = null;
		for (int i = 0; i < length; i++) {
			char c = arrayBuffer[i];
			if (c > '>')
				continue;
			char escaped[] = specialCharactersRepresentation[c];
			if (escaped == null)
				continue;
			if (start == 0)
				escapedBuffer = new StringBuffer(length + 5);
			if (start < i)
				escapedBuffer.append(arrayBuffer, start, i - start);
			start = i + 1;
			escapedBuffer.append(escaped);
		}

		if (start == 0)
			return buffer;
		if (start < length)
			escapedBuffer.append(arrayBuffer, start, length - start);
		return escapedBuffer.toString();
	}

	/**
	 * 주어진 문자열을 모두 unicode로 변환하여 리턴. String
	 */
	public static String toUnicode(String str) {
		int length = str.length();
		char arrayBuffer[] = str.toCharArray();
		StringBuffer escapedBuffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int c = (int) arrayBuffer[i];
			escapedBuffer.append(("&#" + c).toCharArray());
		}
		return escapedBuffer.toString();
	}

	/**
	 * 문자열에서 주어진 길이만큼만 반환 String
	 */
	public static String getEllipsis(String s, int len) {
		if (s == null)
			return s;
		if (s.length() < len) {
			s = s.replaceAll("&nbsp;", " ");
			s = s.replaceAll("&rdquo;", "\"");
			s = s.replaceAll("&ldquo;", "\"");
			s = s.replaceAll("&quot;", "\'");
			return s;
		}
		return s.substring(0, len);
	}

	/**
	 * 문자열의 $ -> //$으로 변환 String
	 */
	public static String dollarToBackslashDollar(String str) {
		char arrayBuffer[] = str.toCharArray();
		StringBuffer escapedBuffer = new StringBuffer();
		for (int i = 0; i < arrayBuffer.length; i++) {
			if (arrayBuffer[i] == '$') {
				escapedBuffer.append("\\$");
			} else {
				escapedBuffer.append(arrayBuffer[i]);
			}
		}
		return escapedBuffer.toString();
	}

	/**
	 * html문자열의 html태그를 모두 제거하여 리턴 String
	 */
	public static String stripTags(String s) {
		Pattern ignorecase = Pattern.compile("<\\w+(\\s(\"[^\"]*\"|'[^']*'|[^>])+)?>|<\\/\\w+>", Pattern.CASE_INSENSITIVE);
		Matcher matcher = ignorecase.matcher(s);
		return matcher.replaceAll("");
	}

	/**
	 * html 문자열의 script를 제거하여 리턴 String
	 */
	public static String stripScripts(String s) {
		Pattern ignorecase = Pattern.compile("<script[^>]*>([\\S\\s]*?)<\\/script>", Pattern.CASE_INSENSITIVE);
		Matcher matcher = ignorecase.matcher(s);
		return matcher.replaceAll("");
	}

	/**
	 * html 문자열의 style을 제거하여 리턴 String
	 */
	public static String stripStyles(String s) {
		Pattern ignorecase = Pattern.compile("<style[^>]*>([\\S\\s]*?)<\\/style>", Pattern.CASE_INSENSITIVE);
		Matcher matcher = ignorecase.matcher(s);
		return matcher.replaceAll("");
	}

	/**
	 * 문자열 바꾸기 String
	 */
	public static String replaceTemplate(String s, String[] template) {
		for (int i = 0; i < template.length; i++) {
			s = s.replaceAll("\\{" + (i + 1) + "\\}", template[i]);
		}
		return s;
	}

	/**
	 * html 관련 모든 태그 정보를 제거한다. <style> 제거 <script> 제거 <tag> 제거. String
	 */
	public static String stripHtml(String s) {
		return stripTags(stripScripts(stripStyles(s)));
	}

	/**
	 * 확장자를 리턴한다. String
	 */
	public static String getExtension(String s) {
		return s.lastIndexOf(".") > -1 ? s.substring(s.lastIndexOf(".") + 1) : "";
	}

	/**
	 * 날짜타입을 문자형으로 바꿔 리턴 String
	 */
	public static String dateToString(java.util.Date d, String format) {
		String ch = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			ch = sdf.format(d);
		} catch (Exception dfdf) {
		}
		return ch;
	}

	public static String makeIdentifier() {
		String identifier = "Identifier-";
		String arraystr[] = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z" };
		for (int i = 0; i < 36; i++) {
			if (i == 8 || i == 13 || i == 18 || i == 23) {
				identifier = identifier + "-";
			} else {

				// identifier = identifier + arraystr[(ra.nextInt(arraystr.length))];
				identifier = identifier + arraystr[((int) (Math.random() * 100) % arraystr.length)];
			}
		}
		return identifier;
	}

	/**
	 * String 문자열을 받아 천단위 구분 기호를 처리한다. String
	 */
	public static String makeComma(String str) {

		StringBuffer sb = new StringBuffer();

		int str_size = str.length(); // 받은 문자열의 길이
		int bgn_size = str_size % 3; // 콤마를 찍기전 자리수

		if (bgn_size != 0)
			sb.append(str.substring(0, bgn_size));
		if ((str_size % 3 != 0) && str_size > 3)
			sb.append(",");

		for (int i = 0; i < (str_size - bgn_size) / 3; i++) {

			sb.append(str.substring(bgn_size + i * 3, bgn_size + i * 3 + 3));
			if (i < (str_size - bgn_size) / 3 - 1)
				sb.append(",");
		}

		return sb.toString();
	}

	/**
	 * range보다 작은 수 들 중의 random값을 리턴 int
	 */
	public static int random(int range) {
		int len = 1;
		int r = range;
		while (r > 10) {
			len++;
			r = r / 10;
		}
		return ((int) (Math.random() * (Math.pow(10, len)))) % range;
	}

	/**
	 * 문자열이 Null 이거나 ""이면 true boolean
	 */
	public static boolean isEmpty(String s) {
		return s == null || s.equals("") ? true : false;
	}

	/**
	 * 문자열이 null이 아니고 ""도 아니면 true boolean
	 */
	public static boolean isNotEmpty(String s) {
		return !isEmpty(s);
	}

	/**
	 * 주어진 문자열을 length 길이만큼 자른 후 "..."를 concat하여 리턴한다. String
	 */
	public static String subStrForOmit(String str, int length) {
		if (str == null) {
			str = "";
		} else {
			if (str.length() > length) {
				str = str.substring(0, length) + "...";
			}
		}
		return str;
	}

	/**
	 * 주어진 문자열이 숫자인지 확인 boolean
	 */
	public static boolean isNumber(String str) {
		char c;

		if (str.equals(""))
			return false;

		for (int i = 0; i < str.length(); i++) {
			c = str.charAt(i);
			if (c < 48 || c > 57) {
				return false;
			}
		}
		return true;
	}

	/*
	 * 파라미터로 전달받은 JSONObject를 HashMap<String, Object> 타입으로 변환
	 */
	public static HashMap<String, Object> jo2HashMap(JSONObject jo) {
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		Iterator<?> iter = jo.keys();
		while (iter.hasNext()) {
			try {
				String joKey = String.valueOf(iter.next());
				String joValule = jo.getString(joKey);
				paramMap.put(joKey, joValule);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return paramMap;
	}

	/*
	 * 파라미터로 전달받은 JSONObject를 HashMap<String, Object> 타입으로 변환
	 */
	public static HashMap<String, Object> addMapEle(HashMap<String, Object> paramMap, HashMap<String, Object> sessionMap) {
		Set<String> set = sessionMap.keySet();
		Iterator<?> iter = set.iterator();
		while (iter.hasNext()) {
			String mapKey = String.valueOf(iter.next());
			String mapValule = String.valueOf(sessionMap.get(mapKey));
			paramMap.put(mapKey, mapValule);
		}
		return paramMap;
	}

	/**
	 * make pagenation String
	 * 
	 * @param totalCount  전체목록건수
	 * @param pageNumber  현재 페이지번호
	 * @param pageCount   화면당 목록건수
	 * @param pageIconCnt 화면당 페이지 아이콘 개수
	 * @return
	 */
	public static String makePagenation(int totalCount, int pageNumber, int pageCount, int pageIconCnt, String pageScript) {

		StringBuffer pagenationStr = new StringBuffer();
		int totalPages = 0;
		if (totalCount % pageCount == 0) {
			totalPages = totalCount / pageCount;
		} else {
			totalPages = totalCount / pageCount + 1;
		}
		int pageStart = 0;
		int pageEnd = 0;

		if (pageNumber % pageIconCnt > 0) {
			pageStart = pageNumber / pageIconCnt * pageIconCnt + 1;
		} else {
			pageStart = pageNumber - pageIconCnt + 1;
		}
		pageEnd = pageStart - 1 + pageIconCnt;

		if (pageNumber > 1) {
			pagenationStr.append("<a href=\"#\" onclick=\"" + pageScript + "(" + (pageNumber - 1) + ")\"><i class=\"fas fa-chevron-left\"></i></a> ");
		} else {
			pagenationStr.append("<a href=\"#\" onclick=\"return false;\"><i class=\"fas fa-chevron-left\"></i></a> ");
		}

		for (int i = pageStart; i <= pageEnd && i <= totalPages; i++) {
			pagenationStr.append("<a href=\"#\" onclick=\"" + pageScript + "(" + i + ")\" ");
			if (i == pageNumber) {
				pagenationStr.append(" class=\"active\"");
			}
			pagenationStr.append(">" + i + "</a> ");
		}

		if (pageNumber < totalPages) {
			pagenationStr.append("<a href=\"#\" onclick=\"" + pageScript + "(" + (pageNumber + 1) + ")\"><i class=\"fas fa-chevron-right\"></i></a>");
		} else {
			pagenationStr.append("<a href=\"#\" onclick=\"return false;\"><i class=\"fas fa-chevron-right\"></i></a>");
		}

		return pagenationStr.toString();
	}

	@SuppressWarnings("unchecked")
	public static void processSessionDataParameter(HttpServletRequest request, HashMap<String, Object> paramMap) {
		HttpSession session = request.getSession();
		HashMap<String, Object> loginMap = (HashMap<String, Object>) session.getAttribute("loginMAP");
		String loginId = (String) session.getAttribute("loginID");
		paramMap.put("loginID", loginId);
		paramMap.put("REG_ID", loginId);
		paramMap.put("USER_SEQ", Integer.toString((Integer) loginMap.get("USER_SEQ")));
		paramMap.put("ENTP_SEQ", Integer.toString((Integer) loginMap.get("ENTP_SEQ")));
		paramMap.put("BRN_SEQ", session.getAttribute("brnSeq"));
		paramMap.put("LABEL_GROUP", (String) session.getAttribute("LABEL_GROUP"));
	}

	/**
	 * 핸드폰번호를 핸드폰 포맷으로 변경 request : 00000000000 response : 000-0000-0000
	 */
	public static String convertPhoneFormat(String number) {

		// 핸드폰 번호 패턴에 맞는지 확인
		String regEx = "(\\d{3})(\\d{3,4})(\\d{4})";
		// 패턴이 맞지 않을경우 null 반환
		if (!Pattern.matches(regEx, number)) {
			return "";
		}
		// 패턴이 맞을 경우 "-"를 추가하여 반환
		return number.replaceAll(regEx, "$1-$2-$3");
	}

	/**
	 * 핸드폰번호를 핸드폰 포맷으로 변경 request : 00000000000 response : 000-0000-0000 or original
	 */
	public static String convertPhoneFormat2(String number) {

		// 핸드폰 번호 패턴에 맞는지 확인
		String regEx = "(\\d{3})(\\d{3,4})(\\d{4})";
		// 패턴이 맞지 않을 경우 전화번호 그대로 반환
		if (!Pattern.matches(regEx, number)) {
			return number;
		}
		// 패턴이 맞을 경우 "-"를 추가하여 반환
		return number.replaceAll(regEx, "$1-$2-$3");
	}

	// '-' 문자 제거
	public static String removeHyphen(Object value) {
		String returnValue = (String) value;
		returnValue = returnValue.replace("-", "");
		return returnValue;
	}

	// IP 체크
	public static String getIp(HttpServletRequest request) {
		HttpSession session = request.getSession();

		// 클라이언트 아이피 체크
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
			log.info(">Proxy-Client-IP : " + ip);
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
			log.info(">WL-Proxy-Client-IP : " + ip);
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
			log.info(">HTTP_CLIENT_IP : " + ip);
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
			log.info(">HTTP_X_FORWARDED_FOR : " + ip);
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}

		session.setAttribute("CLIENT_IP", ip);

		return ip;
	}

	/**
	 * 좌표간 거리계산처리
	 * 
	 * @param dStartPointLon
	 * @param dStartPointLat
	 * @param dEndPointLon
	 * @param dEndPointLat
	 * @return km단위 거리 리턴.
	 * @throws Exception
	 */
	public static double getDistance(double dStartPointLon, double dStartPointLat, double dEndPointLon, double dEndPointLat) throws Exception {

		double theta = dStartPointLon - dEndPointLon;
		double dist = Math.sin(deg2rad(dStartPointLat)) * Math.sin(deg2rad(dEndPointLat)) + Math.cos(deg2rad(dStartPointLat)) * Math.cos(deg2rad(dEndPointLat)) * Math.cos(deg2rad(theta));

		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		dist = dist * 1.609344;
		return (dist);

	}

	// This function converts decimal degrees to radians
	private static double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	// This function converts radians to decimal degrees
	private static double rad2deg(double rad) {
		return (rad * 180 / Math.PI);
	}

	/**
	 * make pagenation String
	 * 
	 * @param totalCount  전체목록건수
	 * @param pageNumber  현재 페이지번호
	 * @param pageCount   화면당 목록건수
	 * @param pageIconCnt 화면당 페이지 아이콘 개수
	 * @return ::첫페이지, 끝페이지 추가
	 */
	public static String makePagenationMax(int totalCount, int pageNumber, int pageCount, int pageIconCnt, String pageScript) {

		StringBuffer pagenationStr = new StringBuffer();
		int totalPages = 0;
		if (totalCount % pageCount == 0) {
			totalPages = totalCount / pageCount;
		} else {
			totalPages = totalCount / pageCount + 1;
		}
		int pageStart = 0;
		int pageEnd = 0;

		if (pageNumber % pageIconCnt > 0) {
			pageStart = pageNumber / pageIconCnt * pageIconCnt + 1;
		} else {
			pageStart = pageNumber - pageIconCnt + 1;
		}

		pageEnd = pageStart - 1 + pageIconCnt;

		// 첫 페이지로 이동
		if (pageNumber > 1 && pageNumber > pageIconCnt) {
			pagenationStr.append("<a href=\"#\" onclick=\"" + pageScript + "(1)\"><i class=\"fas fa-angle-double-left\"></i></a> ");
		}

		if (pageNumber > 1) {
			pagenationStr.append("<a href=\"#\" onclick=\"" + pageScript + "(" + (pageNumber - 1) + ")\"><i class=\"fas fa-chevron-left\"></i></a> ");
		} else {
			pagenationStr.append("<a href=\"#\" onclick=\"return false;\"><i class=\"fas fa-chevron-left\"></i></a> ");
		}

		for (int i = pageStart; i <= pageEnd && i <= totalPages; i++) {
			pagenationStr.append("<a href=\"#\" onclick=\"" + pageScript + "(" + i + ")\" ");
			if (i == pageNumber) {
				pagenationStr.append(" class=\"active\"");
			}
			pagenationStr.append(">" + i + "</a> ");
		}

		if (pageNumber < totalPages) {
			pagenationStr.append("<a href=\"#\" onclick=\"" + pageScript + "(" + (pageNumber + 1) + ")\"><i class=\"fas fa-chevron-right\"></i></a> ");
		} else {
			pagenationStr.append("<a href=\"#\" onclick=\"return false;\"><i class=\"fas fa-chevron-right\"></i></a> ");
		}
		if ((pageNumber - 1) / pageIconCnt != totalPages / pageIconCnt && (totalPages + 1) > pageIconCnt) {
			pagenationStr.append("<a href=\"#\" onclick=\"" + pageScript + "(" + totalPages + ")\"><i class=\"fas fa-angle-double-right\"></i></a> ");
		}

		return pagenationStr.toString();
	}


	/**
	 * yyyyMM 형태로 넘기면
	 * yyyy-MM 형태로 return
	 * 
	 */
	public static String cngYYYY_MM(String target) {
		String cngType = target.substring(0, 4) + "-" + target.substring(4, 6);
		return cngType;
	}
}
