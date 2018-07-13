package com.zhanglugao.swagger.constant;


import org.springframework.http.HttpStatus;

/**
 * 统一结果集对象,使用拦截器统一规范返回结果.
 * web端所有的请求对象,都是在这个包装对象.
 * 所有的服务层对外传输数据皆采用该对象进行包装.
 *
 * 因为前端开发时接口返回格式需要兼容
 * 2018-04-11 update by zhanglugao
 *
 * @author zhanghao
 */
public class Result<T>{
    /**
     * 返回http状态码
     */
    private HttpStatus httpStatus;
    /**
     * 返回消息编码
     */
    private Integer status;
    /**
     * 返回消息描述
     */
    private String message;
    /**
     * 返回数据对象
     */
    private T data;

    /**
     * 返回消息编码
     */
    private String code;

    public Result() {
        super();
        this.httpStatus = HttpStatus.OK;
        this.code=ResultConstant.DEFAULT_SUCCESS_CODE;
        this.status = ResultConstant.DEFAULT_SUCCESS_STATUS;
        this.message = ResultConstant.DEFAULT_SUCCESS_MSG;
    }

    public Result(String message){
        super();
        this.httpStatus = HttpStatus.EXPECTATION_FAILED;
        this.code=ResultConstant.UNKNOWN_EXCEPTION_CODE;
        this.status = ResultConstant.UNKNOWN_EXCEPTION_STATUS;
        this.message = message;
    }

    public Result(HttpStatus httpStatus,String code,Integer status, String message, T data) {
        super();
        this.status = status;
        this.code=code;
        this.message = message;
        this.data = data;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
