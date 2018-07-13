package com.zhanglugao.base;

import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public class MethodResultInfo {
    //返回的状态值 0代表执行成功 其他值代表出错
    private int status=0;
    //status不为0时 error_desc返回详细报错提示
    private String error_desc;
    //httpStatus 默认ok  配合restful是可更改status
    private HttpStatus httpStatus=HttpStatus.OK;
    //额外存储信息的map
    private Map<String,Object> dataMap=new HashMap<>();

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError_desc() {
        return error_desc;
    }

    public void setError_desc(String error_desc) {
        this.error_desc = error_desc;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public Map<String, Object> getDataMap() {
        return dataMap;
    }

    public void setDataMap(Map<String, Object> dataMap) {
        this.dataMap = dataMap;
    }
}
