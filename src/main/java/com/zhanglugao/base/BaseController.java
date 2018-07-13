package com.zhanglugao.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BaseController extends RestHttpTemplate{
    private static final Log log = LogFactory.getLog(BaseController.class);

    protected void writeJson(HttpServletResponse response, Object object) {
        try {
            String json = JSON.toJSONString(object,
                    SerializerFeature.WriteDateUseDateFormat);

            // log.info("转换后的JSON字符串：" + json);
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(json);
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    protected void writeString(HttpServletResponse response, String object) {
        try {
            // log.info("转换后的JSON字符串：" + json);
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(object);
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 获取分页参数
     *
     * @param request
     * @return
     */
    public PageParameter getPageParameter(HttpServletRequest request) {
        PageParameter pageParameter = new PageParameter();
        if(StringUtils.isNotBlank(request.getParameter("page_size"))){
            pageParameter.setPageSize(Integer.parseInt(request.getParameter("page_size")));
        }else{
            pageParameter.setPageSize(10);
        }
        if(StringUtils.isNotBlank(request.getParameter("current_page"))){
            pageParameter.setCurrentPage(Integer.parseInt(request.getParameter("current_page")));
        }else{
            pageParameter.setCurrentPage(1);
        }
        if(StringUtils.isNotBlank(request.getParameter("order_field"))){
            pageParameter.setOrderField(request.getParameter("order_field"));
        }
        if(StringUtils.isNotBlank(request.getParameter("order_type"))){
            pageParameter.setOrderType(request.getParameter("order_type"));
        }
        return pageParameter;
    }

    public HttpEntity<MethodResultInfo> writeData(MethodResultInfo result){
        return new ResponseEntity<>(result,result.getHttpStatus());
    }

}
