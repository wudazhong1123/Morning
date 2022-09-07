package com.wdz.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * @Description
 * @Author 吴大中
 * @Date 2022/8/29 10:55
 */
public class DateUtils {
	public static final String BEGIN_DATE = "2020-10-04";
	public static final String ZXJ_BIRTHDAY = "10-5";
	public static final String WDZ_BIRTHDAY = "10-16";
	/**
	 * java环境中文传值时，需特别注意字符编码问题
	 */
	public static final String HTTP_URL = "http://api.tianapi.com/lunar/index?key=e296b776a880ac07468d137643468544";
	public static final String SOLAR = "0";
	/**
	 * 按农历查询该值为1且日期不能有前导零
	 */
	public static final String LUNAR = "1";
	
	/**
	 * @Description 距离纪念日还有
	 * @Author 吴大中
	 * @Date 2022/8/29 14:36
	 */
	public static int getLianAi() {
		return calculationLianAi(BEGIN_DATE);
	}
	
	/**
	 * @Description 距离赵学静生日还有
	 * @Author 吴大中
	 * @Date 2022/8/29 14:36
	 */
	public static long getZXJBirthday() {
		return calculationBirthday(ZXJ_BIRTHDAY);
	}
	
	/**
	 * @Description 距离吴大中生日还有
	 * @Author 吴大中
	 * @Date 2022/8/29 14:36
	 */
	public static long getWDZBirthday() {
		return calculationBirthday(WDZ_BIRTHDAY);
	}
	
	/**
	 * @param httpUrl 请求接口
	 * @param date    日期
	 * @param type    按农历查询该值为1且日期不能有前导零
	 * @return 返回结果
	 */
	public static LocalDate date(String httpUrl, String date, String type) {
		BufferedReader reader = null;
		String result = null;
		StringBuilder sbf = new StringBuilder();
		httpUrl = httpUrl + "&date=" + date + "&type=" + type;
		
		try {
			URL url = new URL(httpUrl);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setRequestMethod("GET");
			InputStream is = connection.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
			String strRead = null;
			while ((strRead = reader.readLine()) != null) {
				sbf.append(strRead);
				sbf.append("\r\n");
			}
			reader.close();
			result = sbf.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject jsonObject = JSON.parseObject(result);
		assert jsonObject != null;
		String birthday = jsonObject.getJSONArray("newslist").getJSONObject(0).getString("gregoriandate");
		return LocalDate.parse(birthday);
	}
	
	/**
	 * @Description 纪念日
	 * @Author 吴大中
	 * @Date 2022/8/29 14:29
	 */
	public static int calculationLianAi(String date) {
		DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		int day = 0;
		try {
			long time = System.currentTimeMillis() - simpleDateFormat.parse(date).getTime();
			day = (int) (time / 86400000L);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return day;
	}
	
	/**
	 * @Description 生日
	 * @Author 吴大中
	 * @Date 2022/8/29 14:29
	 */
	public static long calculationBirthday(String date) {
		LocalDate now = LocalDate.now();
		int year = now.getYear();
		LocalDate time = date(HTTP_URL, year + "-" + date, LUNAR);
		if (now.isAfter(time)) {
			time = date(HTTP_URL, (year + 1) + "-" + date, LUNAR);
		}
		return ChronoUnit.DAYS.between(now, time);
	}
}
