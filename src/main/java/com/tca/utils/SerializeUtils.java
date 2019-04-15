package com.tca.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 序列化工具类
 * @author zhoua
 *
 */
public class SerializeUtils {
	
	private static Logger logger = LoggerFactory.getLogger(SerializeUtils.class);
	
	
	
	
	/**
	 * 将对象转换为json字符串
	 * @param value
	 * @param isTrim(是否去掉回车换行)
	 * @return
	 * @throws CommonException
	 */
	public static String objectSerializeToJson(Object value, boolean isTrim) throws Exception {
		try{
			ObjectMapper mapper = new ObjectMapper();
			String jsonStr = mapper.writeValueAsString(value);
			//去回车换行
			if(isTrim){
				   Pattern pattern = Pattern.compile("\r|\n");
				   Matcher matcher = pattern.matcher(jsonStr);
				   jsonStr = matcher.replaceAll("");
			}
			return jsonStr;
		}catch(Exception ex){
			logger.error("将对象序列化成Json字符异常:", ex);
			throw new Exception("将对象序列化成Json字符异常");
		}
	}
	

	/**
	 * 将对象转换为json字符串(默认不去回车换行)
	 * @param value
	 * @return
	 * @throws Exception 
	 * @throws CommonException
	 */
	public static String objectSerializeToJson(Object value) throws Exception  {
		return objectSerializeToJson(value, false);
	}
	
	
	/**
	 * 将json字符串转成对象
	 * @param jsonStr
	 * @param clazz
	 * @param ignoreUnknown(是否忽略未知字段)
	 * @return
	 * @throws Exception
	 */
	public static <T> T jsonSerializeToObject(String jsonStr, Class<T> clazz, boolean ignoreUnknown) throws Exception {
		try{
			ObjectMapper mapper = new ObjectMapper();
			if(ignoreUnknown){
				mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			}
			T obj = mapper.readValue(jsonStr, clazz);
			if(obj == null){
				throw new Exception("Json字符串反序列化生成了空对象");
			}
			return obj;
		}catch(Exception ex){
			logger.error("将json反序列化为对象异常:", ex);
			throw new Exception("将json反序列化为对象:" + clazz.getName() + "异常");
		}
	}
	
	
	/**
	 * 将json字符串转换为对象(默认遇到未知字段报错)
	 * @param jsonStr
	 * @param clazz
	 * @return
	 * @throws CommonException
	 */
	public static <T> T jsonSerializeToObject(String jsonStr, Class<T> clazz) throws Exception {
		return jsonSerializeToObject(jsonStr,clazz,false);
	}
	
	
	/**
	 * 将对象转成Map
	 * @param beanInfo
	 * @return
	 * @throws Exception
	 */
	public static <T> Map<String, Object> beanToMap(T beanInfo) throws Exception {
		try{
			String jsonStr = objectSerializeToJson(beanInfo);
			@SuppressWarnings("unchecked")
			Map<String, Object> beanMap = jsonSerializeToObject(jsonStr, HashMap.class);
			return beanMap;
		}catch(Exception ex){
			logger.error("将实体Bean转换为Map对象异常:", ex);
			throw new Exception(
					"将实体Bean:" + beanInfo.getClass().getName() + "转换为Map对象异常");
		}
	}
	
	/**
	 * 将Map转成对象
	 * @param map
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public static <T> T mapToBean(Map<String, Object> map, Class<T> clazz) throws Exception {
		try{
			T beanInfo = clazz.newInstance();	
			BeanUtils.populate(beanInfo, map);
			return beanInfo;
		}catch(Exception ex){
			logger.error("将Map转换为实体Bean异常:", ex);
			throw new Exception("将Map转换为实体Bean:" + clazz.getName() + "异常");
		}

	}
	
	/**
	 * 将json字符串转成List
	 * @param jsonStr
	 * @param collectionClazz
	 * @param clazz
	 * @param ignoreUnknown(是否忽略未知字段)
	 * @return
	 * @throws Exception
	 */
	public static <T,V> List<T> jsonSerializeToList(String jsonStr,Class<V> collectionClazz, Class<T> clazz, boolean ignoreUnknown) throws Exception {
		try{
			ObjectMapper mapper = new ObjectMapper();
			if(ignoreUnknown){
				mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			}
			JavaType javaType = mapper.getTypeFactory().constructParametricType(collectionClazz, clazz);
			@SuppressWarnings("unchecked")
			List<T> lst =  (List<T>)mapper.readValue(jsonStr, javaType);
			return lst;
		}catch(Exception ex){
			throw new Exception("将json反序列化为List:" + clazz.getName() + "异常");
		}
	}
	
	/**
	 * 将json字符串转成List(默认不忽略未知字段)
	 * @param jsonStr
	 * @param collectionClazz
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public static <T,V> List<T> jsonSerializeToList(String jsonStr,Class<V> collectionClazz, Class<T> clazz) throws Exception {
		try{
			ObjectMapper mapper = new ObjectMapper();
			JavaType javaType = mapper.getTypeFactory().constructParametricType(collectionClazz, clazz);   
			@SuppressWarnings("unchecked")
			List<T> lst =  (List<T>)mapper.readValue(jsonStr, javaType);
			return lst;
		}catch(Exception ex){
			throw new Exception("将json反序列化为List:" + clazz.getName() + "异常");
		}
	}
	
	
	/**
	 * 将一个Bean实体转成另一个Bean
	 * @param beanInfo
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public static <T, V> V beanConvert(T beanInfo, Class<V> clazz) throws Exception {
		Map<String, Object> beanMap = beanToMap(beanInfo);
		V newBeanInfo = mapToBean(beanMap, clazz);
		return newBeanInfo;
	}
	
}
	
	
	
	
	
	
	
	

/*	
	
	
	
	
	*//**
	 * 
	 * @param jsonStr 格式如: "[{\"configId\":\"1\",\"percent\":\"11\"},{\"configId\":\"1\"},{\"configId\":\"1\"}]"
	 * @param clazz
	 * @return List<Bean>格式
	 * @throws CommonException
	 *//*
	


	*//**
	 * 将一个bean数据复制到另一个bean
	 * @param destBean
	 * @param oriBean
	 * @throws CommonException
	 *//*
	public <T,V> void copyProperties(T destBean,V oriBean) throws CommonException{
		try {
			BeanUtils.copyProperties(destBean, oriBean);
		} catch(Exception ex){
			throw new CommonException("将oriBean"+oriBean.getClass().getName()+"复制到destBean:" + destBean.getClass().getName() + "异常");
		}
	}
	
	*//**
	 * 将字符串序列化为一个实体Bean(只支持String)
	 * @param clazz
	 * @param itemString
	 * @param seprator
	 * @param totalSection
	 * @return
	 * @throws CommonException
	 *//*
	public <T> T stringSerializeToBeanBySeprator(Class<T> clazz, String itemString, 
			String pattern, int totalSection) throws CommonException{
		try {
			String[] items = itemString.split(pattern, -1);
			if(items.length != totalSection){
				throw new CommonException("要求的总字段数为:"
						+ totalSection + ",分隔的总字段数为:" + items.length + "两者不相等");
			}
			//获取声明的域变量
			T beanInfo = clazz.newInstance();
			Field[] fields = beanInfo.getClass().getDeclaredFields();
			for(Field field : fields){
				String fieldName = field.getName();
				BeanItemBySeprator beanItem = field.getAnnotation(
						BeanItemBySeprator.class);
				if(beanItem != null){
					String desc = beanItem.desc();
					int position = beanItem.position();
					int maxLength = beanItem.maxLength();
					boolean isAllowEmpty = beanItem.isAllowEmpty();
					String checkType = beanItem.checkType();
					String restriction = beanItem.restriction();
					if(position >= totalSection){
						throw new CommonException("字段:" + desc + "定义的位移:" 
								+ (position + 1) + "超过总字段数:" + totalSection);					
					}
					String value = items[position];
					//最大长度小于等于0则认为不校验最大长度
					if((maxLength > 0) && (value.length() > maxLength)){
						throw new CommonException("字段:" + desc + "的总长度:" 
								+ value.length() + "超过定义的最大长度:" + maxLength);						
					}
					//是否允许为空判断
					if(!isAllowEmpty){
						if(validateUtils.isEmpty(value)){
							throw new CommonException("字段:" + desc + "不能为空");	
						}
						//类型校验
						if(validateUtils.isNotEmpty(checkType)){
							if(checkType.equalsIgnoreCase("amount")){
								try{
									new BigDecimal(value);
								}catch(Exception ex){
									throw new CommonException(
										"字段:" + desc + "值：" + value + "非数字类型");
								}
							}
						}
						//限制校验
						if(validateUtils.isNotEmpty(restriction)){
							StringTokenizer tokenizer = new StringTokenizer(restriction, "\\|");
							boolean isMatch = false;
							while(tokenizer.hasMoreTokens()){
								String token = tokenizer.nextToken();
								if(token.equalsIgnoreCase(value)){
									isMatch = true;
									break;
								}
							}
							if(!isMatch){
								throw new CommonException(
										"字段:" + desc + "值：" + value + "不在枚举列表之中");							
							}
						}
					}
					//获取调用方法进行调用赋值
					String setMethodName = "set" + fieldName.substring(0, 1).toUpperCase()
							+ fieldName.substring(1);
					Method method = null;
					try{
						method = beanInfo.getClass().getDeclaredMethod(setMethodName, String.class);
					}catch(Exception ex){
						throw new CommonException("实体Bean:" + beanInfo.getClass().getName()
								+ "不存在方法:" + setMethodName + "(String.class)");
					}
					try{
						method.invoke(beanInfo, value);
					}catch(Exception ex){
						throw new CommonException("实体Bean:" + beanInfo.getClass().getName()
								+ "调用方法:" + setMethodName + "(String.class)失败,"
								+ "传入的调用值为:" + value);
					}
				}
			}
			return beanInfo;
		} catch(Exception ex){
			exceptionUtils.throwCommonException(ex, 
					"将字符串:" + itemString + "序列化为实体Bean发生异常");
			return null;
		}
	}
	
	*//**
	 * 将实体BeanBySeprator转换为字符串(只支持String)
	 * @param beanInfo
	 * @return
	 * @throws CommonException
	 *//*
	public <T> String beanBySepratorSerializeToString(T beanInfo, String seprator, int totalSection) throws CommonException {
		try {
			String content = "";
			List<Field> fieldList = new ArrayList<Field>();
			Field[] fields = beanInfo.getClass().getDeclaredFields();
			for(Field field : fields){
				BeanItemBySeprator beanItem = field.getAnnotation(
						BeanItemBySeprator.class);
				if(beanItem != null){
					fieldList.add(field);
				}
			}
			if(fieldList.size() != totalSection){
				throw new CommonException("要求的总字段数为:"
						+ totalSection + ",合并的总字段数为:" + fieldList.size() + "两者不相等");
			}
			//将列表转换为数据
			Field[] fieldArray = fieldList.toArray(new Field[fieldList.size()]);
			Arrays.sort(fieldArray, new BeanItemComparator());
			//获取属性值
			for(Field field : fieldArray){
				String fieldName = field.getName();
				String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase()
						+ fieldName.substring(1);
				Method method = null;
				try{
					method = beanInfo.getClass().getDeclaredMethod(getMethodName);
				}catch(Exception ex){
					throw new CommonException("实体Bean:" + beanInfo.getClass().getName()
							+ "不存在方法:" + getMethodName + "()");
				}
				Object object = null;
				try{
					object = method.invoke(beanInfo);
				}catch(Exception ex){
					throw new CommonException("实体Bean:" + beanInfo.getClass().getName()
							+ "调用方法:" + getMethodName + "()失败");
				}
				if (object == null) {
					content += seprator;
				} else {
					content += object.toString() + seprator;
				}
			}
			if(content.length() > 0){
				content = content.substring(0, content.length() -1);
			}
			return content;
		} catch(Exception ex){
			exceptionUtils.throwCommonException(ex, 
					"将实体Bean:" + beanInfo.getClass().getName() + "转换为字符串发生异常");
			return null;
		}
	}
	
	*//**
	 * 按BeanItemComparator的位置排队
	 *
	 *//*
	private class BeanItemComparator implements Comparator<Field> {
		@Override
		 public int compare(Field fieldOne, Field fieldTwo) {
			BeanItemBySeprator beanItemOne = fieldOne.getAnnotation(
					BeanItemBySeprator.class);
			BeanItemBySeprator beanItemTwo = fieldTwo.getAnnotation(
					BeanItemBySeprator.class);
			 return beanItemOne.position() - beanItemTwo.position();  
		 }
	}
	
	
}
*/


/*public static void main(String[] args) {
	PageBean pageBean = new PageBean();
	pageBean.setPage(10);
	pageBean.setRows(5);
	try {
		String PersonStr = "{\"age\":60,\"username\":\"tca\",\"password\":\"hello\"}";
		Person person = SerializeUtils.jsonSerializeToObject(PersonStr, Person.class, true);
		System.out.println(person.getUsername());
		System.out.println(person.getPassword());
		Map map = new HashMap();
		map = SerializeUtils.beanToMap(person);
		System.out.println(map.get("age").toString() + map.get("username") +map.get("password") + map.size());
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}*/