package com.melo.restful;

public enum RetCode {

    // 成功
    SUCCESS(200, "成功"),
    // 失败
    FAIL(400, "失败"),
    // 未认证（签名错误）
    UNAUTHORIZED(401, "未认证（签名错误）"),
    // 接口不存在
    NOT_FOUND(404, "接口不存在"),
    // 接口不存在
    TOKEN_ERROR(410, "TOKEN验证错误"),
    //从redsi获取token失败
    REDIS_TOKEN_ERROR(411, "TOKEN失效"),
    // 服务器内部错误
    INTERNAL_SERVER_ERROR(500, "系统错误，请联系管理员"),
    // 参数转换错误
    TRANSCODING_FAIL(409, "参数转换错误"),
    // 参数不完整
    PARAM_FAIL(501, "参数不完整"),

    // 请选择需要操作的信息
    SELECT_IS_NULL(666, "请选择需要操作的信息"),


    DATA_OPERIDS_IS_NULL(9998, "请选择要操作的记录"),

    // 不被允许操作
    OPERATION_NOT_ALLOWED(667, "不被允许操作"),

    DATA_NOT_EXIST(9999, "查询数据不存在！");

    /**
     * 错误代码
     */
    private int code;
    /**
     * 错误消息
     */
    private String msg;

    RetCode(int code, String msg) {
        this.setCode(code);
        this.setMsg(msg);
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
