package com.wdz.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: Morning
 * @description:
 * @author: 吴大中
 * @create: 2022-08-29 13:36
 **/
public class SentenceUtils {
	public static String getCaiHongPi() {
		String httpUrl = "http://api.tianapi.com/caihongpi/index?key=e296b776a880ac07468d137643468544";
		BufferedReader reader;
		String result = null;
		StringBuilder sbf = new StringBuilder();
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
		JSONArray newslist = jsonObject.getJSONArray("newslist");
		return newslist.getJSONObject(0).getString("content");
	}
	
	public static Map<String,String> getEnsentence() {
		String httpUrl = "http://api.tianapi.com/ensentence/index?key=e296b776a880ac07468d137643468544";
		BufferedReader reader = null;
		String result = null;
		StringBuilder sbf = new StringBuilder();
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
		JSONArray newslist = jsonObject.getJSONArray("newslist");
		String en = newslist.getJSONObject(0).getString("en");
		String zh = newslist.getJSONObject(0).getString("zh");
		Map<String, String> map = new HashMap<>();
		map.put("zh",zh);
		map.put("en",en);
		return map;
	}
}
