package com.uqiansoft.gateway.constant;

/**
 * Http请求返回数据封装
 * @author xutao
 * @date 2018-12-07 17:30
 */
public class ResponseData {

    /**
     * code定义
     */
    public static final String AUTHORITY_NO = "0";                  // 没有访问权限
    public static final String AUTHORITY_YES = "1";                 // 有访问权限
    public static final String INVALID_TOKEN = "2";                 // 无效的单点登录token：未登录或者token已失效
    public static final String REQUEST_FAIL = "999";                // 请求失败


    private String code;

    private String msg;

    private String data;


    public ResponseData() {
    }

    public ResponseData(String code, String msg, String data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 构建默认的请求返回
     */
    public static ResponseData getDefault() {
        return new ResponseData(AUTHORITY_YES, "请求失败", "");
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
