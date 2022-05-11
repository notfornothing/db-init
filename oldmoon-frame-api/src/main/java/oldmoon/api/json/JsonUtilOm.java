package oldmoon.api.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;

import java.util.List;
import java.util.Map;

/**
 * Json格式数据操作工具类
 */
public class JsonUtilOm {


	/**
	 * @Description map转json
	 * @Author oldmoon.top
	 * @Date 2020-8-15 14:52:46
	 */
	public static String mapToJson(Map<String, Object> map) {
		String jsonString = JSON.toJSONString(map);
		return jsonString;
	}
	public static String mapListToJson(List<Map> list) {
		String jsonString = JSON.toJSONString(list);
		return jsonString;
	}
	public static String listObjectToJson(List<Object> list) {
		String jsonString = JSON.toJSONString(list);
		return jsonString;
	}
	public static String arrayToJson(Object[] array) {
		String jsonString = JSON.toJSONString(array);
		return jsonString;
	}


	/**
	 * 
	 * @description 将List<T> 封装为JSONArray
	 */
	public static <T> JSONArray listObjectToJsonObject(List<T> list) {
		ValueFilter filter = new ValueFilter() {
			@Override
			public Object process(Object obj, String s, Object v) {
				if (v == null)
					return "";
				return v;
			}
		};
		String jsonText = JSON.toJSONString(list, filter, SerializerFeature.DisableCircularReferenceDetect);
		JSONArray jsonarr = JSONArray.parseArray(jsonText);
		return jsonarr;
	}

	/**
	 * 
	 * @description 将List<T> 封装为JSONArray（原始数据）
	 */
	public static <T> JSONArray listOriginalObjcktToJsonObject(List<T> list) {
		ValueFilter filter = new ValueFilter() {
			@Override
			public Object process(Object obj, String s, Object v) {
				return v;
			}
		};
		String jsonText = JSON.toJSONString(list, filter, SerializerFeature.DisableCircularReferenceDetect);
		JSONArray jsonarr = JSONArray.parseArray(jsonText);
		return jsonarr;
	}

	/**
	 * @description 将json字符串解析为com.alibaba.fastjson.JSONObject
	 */

	public static JSONObject getJsonObject(String jsonstr) {
		JSONObject json = JSONObject.parseObject(jsonstr);
		return json;
	}

	/**
	 * @description 将json字符串解析为com.alibaba.fastjson.JSON
	 */

	public static JSON getjson(String jsonstr) {
		JSON json = JSON.parseObject(jsonstr);
		return json;
	}

	/**
	 * @description 将json字符串解析为com.alibaba.fastjson.JSONArray
	 */

	public static JSONArray getJsonArr(String jsonstr) {
		JSONArray json = JSONArray.parseArray(jsonstr);
		return json;
	};

	/**
	 * @Description 普通Java类转换为json格式字符串
	 */
	public static String objectToJsonString(Object obj) {
		String string = JSONObject.toJSONString(obj, SerializerFeature.WriteMapNullValue);
		return string;

	}

	/**
	 * @Description 普通Java类封装为JSONObject
	 */
	public static JSONObject ObjectToJsonObjectNotNull(Object obj) {
		String string = JSON.toJSONString(obj, SerializerFeature.WriteMapNullValue,
				SerializerFeature.DisableCircularReferenceDetect);
		return JSONObject.parseObject(string);

	}

	/**
	 * @Description: json字符串转map
	 */
	public static Map<String, Object> jsonStringToMapObject(String json) {
		Map<String, Object> map = JSON.parseObject(json, Map.class);
		return map;
	}
	public static List<Map<String, Object>> jsonStringToListMap(String json) {
		List<Map<String, Object>> map = JSON.parseObject(json, List.class);
		return map;
	}

	/**
	 * @Description JSON转JSONObject
	 */
	public static JSONObject jsonToJsonObject(JSON json) {
		return (JSONObject) json;
	}

	/**
	 * @Description JSON转JSONArray
	 */
	public static JSONArray jsonToJsonArray(JSON json) {
		return (JSONArray) json;
	}

	/**
	 * @description 把json字符串转换成普通字符串列表
	 */
	public static List<String> getStringList(String jsonData) {
		return JSON.parseArray(jsonData, String.class);
	}

	/**
	 * @description 把json字符串转换成指定的java对象
	 */
	public static <T> T getSingleBean(String jsonData, Class<T> clazz) {
		return JSON.parseObject(jsonData, clazz);
	}

	/**
	 * @description 把json字符串转换成指定的java对象列表
	 */
	public static <T> List<T> getBeanList(String jsonData, Class<T> clazz) {
		return JSON.parseArray(jsonData, clazz);
	}

	/**
	 * @description 把json字符串转换成较为复杂的java对象列表List<Map<String, Object>>
	 */
	public static List<Map<String, Object>> getBeanMapList(String jsonData) {
		return JSON.parseObject(jsonData, new TypeReference<List<Map<String, Object>>>() {
		});
	}
	
}
