package com.wdz.utils;

import com.wdz.entity.Weather;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;

import java.util.Map;

/**
 * @Description
 * @Author 吴大中
 * @Date 2022/8/29 13:38
 */
public class PushUtils {
	
	public static void main(String[] args) {
		push();
	}
	
	/**
	 * 微信公众号 id
	 */
	private static final String APP_ID = "wxbd8e0a9d657ffb00";
	/**
	 * 微信公众号 app secret
	 */
	private static final String SECRET = "670fec312697fdc839773e3184a54437";
	
	public static void push() {
		//1，配置
		WxMpInMemoryConfigStorage wxStorage = new WxMpInMemoryConfigStorage();
		wxStorage.setAppId(APP_ID);
		wxStorage.setSecret(SECRET);
		WxMpService wxMpService = new WxMpServiceImpl();
		wxMpService.setWxMpConfigStorage(wxStorage);
		//2,推送消息
		WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
		                                                         //.toUser("oQq-K5o9IemNPxR1kNM13vUef_QM")
		                                                         .toUser("oQq-K5lKG3q9x-CGa5HkBIUCxccc")
		                                                         .templateId(
				                                                         "xAQk_7c72xESAvmxWCINTL_s281jwSdiESNmMO3gaXw")
		                                                         .build();
		//3,如果是正式版发送模版消息，这里需要配置你的信息
		//todo 获取当前位置信息
		Weather weather = WeatherUtils.getWeather(WeatherUtils.DISTRICT_ID);
		Map<String, String> map = SentenceUtils.getEnsentence();
		// 日期加星期
		templateMessage.addData(new WxMpTemplateData("riqi", weather.getDate() + "  " + weather.getWeek(), "#00BFFF"));
		// 今天
		// 白天天气
		templateMessage.addData(new WxMpTemplateData("tianqi", weather.getText_now(), "#00FFFF"));
		// 温度
		templateMessage.addData(new WxMpTemplateData("high", weather.getHigh() + "", "#FF6347"));
		templateMessage.addData(new WxMpTemplateData("low", weather.getLow() + "", "#173177"));
		templateMessage.addData(new WxMpTemplateData("temp", weather.getTemp() + "", "#EE212D"));
		// 风向风级
		templateMessage.addData(new WxMpTemplateData("winddir", weather.getWind_dir() + "", "#B95EA3"));
		templateMessage.addData(new WxMpTemplateData("windclass", weather.getWind_class() + "", "#42B857"));
		// 倒计时
		templateMessage.addData(new WxMpTemplateData("lianai", DateUtils.getLianAi() + "", "#FF1493"));
		templateMessage.addData(new WxMpTemplateData("shengri1", DateUtils.getZXJBirthday() + "", "#FFA500"));
		templateMessage.addData(new WxMpTemplateData("shengri2", DateUtils.getWDZBirthday() + "", "#FFA500"));
		// 彩虹p
		templateMessage.addData(new WxMpTemplateData("caihongpi", SentenceUtils.getCaiHongPi(), "#FF69B4"));
		// 每日一句
		templateMessage.addData(new WxMpTemplateData("en", map.get("en") + "", "#C71585"));
		templateMessage.addData(new WxMpTemplateData("zh", map.get("zh") + "", "#C71585"));
		String beizhu = "保安say早安宝❤";
		if (DateUtils.getLianAi() % 365 == 0) {
			beizhu = "今天是恋爱" + (DateUtils.getLianAi() / 365) + "周年纪念日！";
		}
		if (DateUtils.getZXJBirthday() == 0) {
			beizhu = "生日快乐🎉";
		}
		if (DateUtils.getWDZBirthday() == 0) {
			beizhu = "今天是谁生日呀";
		}
		templateMessage.addData(new WxMpTemplateData("beizhu", beizhu, "#FF0000"));
		
		try {
			System.out.println(templateMessage.toJson());
			System.out.println(wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage));
		} catch (Exception e) {
			System.out.println("推送失败：" + e.getMessage());
			e.printStackTrace();
		}
	}
}
