package com.tca.utils;

import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author zhoua
 *
 */
public class ValidateUtils {
	
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(ValidateUtils.class);
	
	/**
	 * 判断某对象是否为空
	 * @param beanInfo
	 * @return
	 */
	public static <T> boolean isEmpty(T beanInfo) {
		boolean isEmptyBean = (beanInfo == null);
		if(!isEmptyBean){
			if(beanInfo instanceof Collection){//集合类判断
				Collection<?> collection = (Collection<?>)beanInfo;
				isEmptyBean = (collection.size() == 0);
			}else if(beanInfo instanceof Map){//MAP类判断
				Map<?, ?> map = (Map<?, ?>)beanInfo;
				isEmptyBean = (map.size() == 0);
			}else if(beanInfo instanceof String){//字符串类判断
				String inputString = (String)beanInfo;
				isEmptyBean = StringUtils.isBlank(inputString);
			}
		}
		return isEmptyBean;
	}
	
		
	/**
	 * 判断某对象是否不为空
	 * @param beanInfo
	 * @return
	 */
	public static <T> boolean isNotEmpty(T beanInfo) {
		return !isEmpty(beanInfo);
	}
	

	/**
	 * 验证字符串是否数字（含小数）
	 * @param str
	 * @throws
	 */
	public boolean isNumber(String str) {
		/* 
		 * regex 
		 * [1-9]\d*\.\d*|0\.\d*[1-9]\d*|[1-9]\d*|0
		 *
		 * 大于1的浮点数 | 小于1的浮点数 | 整数 | 0
		 */
		Pattern pattern = Pattern.compile("[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|[1-9]\\d*|0");
		Matcher isNum = pattern.matcher(str);
		
	    if( !isNum.matches() ){
	        return false; 
	    } 
	    return true; 
	}
	
	/**
	 * 测试字符串是否正整数
	 * @param str
	 * 
	 * regex 
	 * ^[0-9]*[1-9][0-9]*$
	 * 大于0的整数
	 * @throws
	 */
	public static boolean isPositiveInteger(String str) throws Exception {
		if(isEmpty(str)){
			throw new Exception("传入的字符串不能为空");
		}
		Pattern pattern = Pattern.compile("^[0-9]*[1-9][0-9]*$");
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}
}