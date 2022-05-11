package oldmoon.api.http;

import oldmoon.api.enums.HttpCode;

/**
 * @description http返回对象处理工具
 */
public class ResultUtil {
	
	public static ResultObject getResponseObject(Integer code, String resultMsg, Object value) {
		ResultObject response = new ResultObject();
		response.setResultCode(code);
		response.setResultMsg(resultMsg);
		response.setResultInfo(value);
		return response;
	}
	public static ResultObject success(String resultMsg, Object value) {
		ResultObject response = new ResultObject();
		response.setResultCode(HttpCode.SUCCESS.getCode());
		response.setResultMsg(resultMsg);
		response.setResultInfo(value);
		return response;
	}
	public static ResultObject success(Object value, String token) {
		ResultObject response = new ResultObject();
		response.setResultCode(HttpCode.SUCCESS.getCode());
		response.setResultMsg("成功");
		response.setResultInfo(value);
		response.setToken(token);
		return response;
	}
	public static ResultObject success(Object value) {
		ResultObject response = new ResultObject();
		response.setResultCode(HttpCode.SUCCESS.getCode());
		response.setResultMsg("成功");
		response.setResultInfo(value);
		return response;
	}
	public static ResultObject success() {
		ResultObject response = new ResultObject();
		response.setResultCode(HttpCode.SUCCESS.getCode());
		response.setResultMsg("成功");
		response.setResultInfo(null);
		return response;
	}
	public static ResultObject error(String resultMsg, Object value) {
		ResultObject response = new ResultObject();
		response.setResultCode(HttpCode.ERROR.getCode());
		response.setResultMsg(resultMsg);
		response.setResultInfo(value);
		return response;
	}

}
