package com.wdz.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: wechatpush
 * @description:
 * @author: 吴大中
 * @create: 2022-08-29 10:18
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Lunar {
	private	String gregoriandate;
	private String lunardate;
	private String lunar_festival;
	private String festival;
	private String fitness;
	private String taboo;
	private String shenwei;
	private String taishen;
	private String chongsha;
	private String suisha;
	private String wuxingjiazi;
	private String wuxingnayear;
	private String wuxingnamonth;
	private String xingsu;
	private String pengzu;
	private String jianshen;
	private String tiangandizhiyear;
	private String tiangandizhimonth;
	private String tiangandizhiday;
	private String lmonthname;
	private String shengxiao;
	private String lubarmonth;
	private String lunarday;
	private String jieqi;
}
