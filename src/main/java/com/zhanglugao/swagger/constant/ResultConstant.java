package com.zhanglugao.swagger.constant;

/**
 * 包装对象.
 * 返回特殊的自定义的包装编码
 * 与异常描述.所有的自定义描述.都定义该常量下
 * @author zhangaho
 */
public interface ResultConstant {

   /**
    * 默认返回成功结果编码
    */
   String DEFAULT_SUCCESS_CODE="SUCCESS";
   /**
    * 默认返回成功结果描述
    */
   String DEFAULT_SUCCESS_MSG="成功";
   /**
    * 未知异常结果编码,该描述主要用于通用拦截器对处理流程中特殊情况的返回使用
    */
   String UNKNOWN_EXCEPTION_CODE="UNKNOWN_EXCEPTION";
   /**
    * 未知异常结果描述
    */
   String UNKNOWN_EXCEPTION_MSG="服务器繁忙...请稍后再试";

   Integer DEFAULT_SUCCESS_STATUS=0;
   Integer UNKNOWN_EXCEPTION_STATUS=1;
}
