package com.zhanglugao.swagger.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.zhanglugao.swagger.constant.Result;
import com.zhanglugao.swagger.constant.ResultConstant;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 通用拦截器
 * 针对所有的.
 * warning:此处的拦截器只是针对一个目前我们服务restful格式的请求返回结果.
 * 不会跳转任何视图层的定向
 * @author zhangaho
 */
@Aspect
@Component
@Order(2)
public class CommonInterceptor {
	
	private static final Logger logger=LoggerFactory.getLogger(CommonInterceptor.class);
	@Pointcut(value="execution(* com.zhanglugao..*Controller.*(..))")
	public void pointCut(){}  //切入到应用层
	
	@Around(value="pointCut()")
	public Object proceed(ProceedingJoinPoint pjp) {
		//请求对象
		RequestAttributes ras = RequestContextHolder.getRequestAttributes();
		ServletRequestAttributes servletRequestAttributes=(ServletRequestAttributes)ras;
		HttpServletRequest request = servletRequestAttributes.getRequest();
		//获取请求地址
		String requestURL =  request.getRequestURL().toString();
		//入参对象
		Object[] paramsObject=pjp.getArgs();
		HttpServletResponse httpServletResponse=null;
		String params="[";
		for (Object object : paramsObject) {
			if(object instanceof HttpServletResponse ) {
				//获取当前响应对象
				httpServletResponse =(HttpServletResponse) object;
			}
			if(!(object instanceof BeanPropertyBindingResult||object instanceof HttpServletRequest||object instanceof HttpServletResponse)){
				params += JSON.toJSONString(object);
			}
		}
		params+="]";
		Object proceed =null;
		try {
			proceed= pjp.proceed();
			return proceed;
		} catch (Throwable  e) {
			if(logger.isErrorEnabled()) {
                logger.error("统一异常拦截>>" + "请求地址:" + requestURL + ";" + "参数:" + params, e);
            }
			if(httpServletResponse!=null) {
				try {
					String json = JSON.toJSONString(new Result<Object>(HttpStatus.OK, ResultConstant.UNKNOWN_EXCEPTION_CODE,ResultConstant.UNKNOWN_EXCEPTION_STATUS,ResultConstant.UNKNOWN_EXCEPTION_MSG,null), SerializerFeature.WriteDateUseDateFormat);
					httpServletResponse.setContentType("text/html;charset=utf-8");
					httpServletResponse.getWriter().write(json);
					httpServletResponse.getWriter().flush();
					httpServletResponse.getWriter().close();
				} catch (Exception e2) {
					if(logger.isErrorEnabled()) {
						logger.error("无法返回数据", e);
					}
				}
			}else {
				Result<Object> result =new Result<Object>();
				result.setCode(ResultConstant.UNKNOWN_EXCEPTION_CODE);
				result.setStatus(ResultConstant.UNKNOWN_EXCEPTION_STATUS);
				result.setMessage(ResultConstant.UNKNOWN_EXCEPTION_MSG);
				result.setHttpStatus(HttpStatus.EXPECTATION_FAILED);
				return result;
			}
		}
		return  proceed;
	}

}
