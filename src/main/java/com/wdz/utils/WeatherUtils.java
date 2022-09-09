package com.wdz.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wdz.entity.Weather;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: Morning
 * @description:
 * @author: 吴大中
 * @create: 2022-08-29 11:00
 **/
public class WeatherUtils {
	public static final String AK = "tMzbYE7QNM4QAlmalfkWwNhG1dg5gOz1";
	public static final String DATA_TYPE = "all";
	public static final String DISTRICT_ID = "130203";
	public static final String WEATHER_URL = "https://api.map.baidu.com/weather/v1/";
	
	public static void main(String[] args) {
		System.out.println(getWeather(DISTRICT_ID));
	}
	
	public static Weather getWeather(String districtId) {
		RestTemplate restTemplate = new RestTemplate();
		Map<String, String> map = new HashMap<>();
		map.put("district_id", districtId);
		map.put("data_type", DATA_TYPE);
		map.put("ak", AK);
		String res = restTemplate.getForObject(
				WEATHER_URL + "?district_id=" + districtId + "&data_type={data_type}&ak={ak}", String.class, map);
		JSONObject json = JSON.parseObject(res);
		assert json != null;
		JSONArray forecasts = json.getJSONObject("result").getJSONArray("forecasts");
		List<Weather> weathers = forecasts.toJavaList(Weather.class);
		JSONObject now = json.getJSONObject("result").getJSONObject("now");
		JSONObject tomorrow = (JSONObject) json.getJSONObject("result").getJSONArray("forecasts").get(1);
		Weather weather = weathers.get(0);
		weather.setText_now(now.getString("text"));
		weather.setTemp(now.getString("temp"));
		weather.setWind_class(now.getString("wind_class"));
		weather.setWind_dir(now.getString("wind_dir"));
		weather.setText_now1(tomorrow.getString("text_day"));
		weather.setHigh1(tomorrow.getString("high"));
		weather.setLow1(tomorrow.getString("low"));
		weather.setWind_class1(tomorrow.getString("wc_day"));
		weather.setWind_dir1(tomorrow.getString("wd_day"));
		return weather;
	}
}
