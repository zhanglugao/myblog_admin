package com.zhanglugao.api.controller;

import com.zhanglugao.base.BaseController;
import com.zhanglugao.base.PageBean;
import com.zhanglugao.base.PageParameter;
import com.zhanglugao.service.ArticleService;
import com.zhanglugao.swagger.constant.Result;
import com.zhanglugao.vo.ArticleReplyInfo;
import com.zhanglugao.vo.BlogArticleInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/articles")
@RestController
public class ArticleController extends BaseController{


    @Autowired
    private ArticleService articleService;

    /***
     * 获取article列表
     * @param category          类别
     * @param label             标签
     * @param createUser        创建人
     * @return
     */
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public Result<PageBean<BlogArticleInfo>> list(HttpServletRequest request,@RequestParam(required = false) String category, @RequestParam(required = false)String label, @RequestParam(required = false)String createUser){
        PageParameter page=this.getPageParameter(request);
        Map<String,Object> select=new HashMap<>();
        if(StringUtils.isNotBlank(category)){
            select.put("category",category);
        }
        if(StringUtils.isNotBlank(label)){
            select.put("label",label);
        }
        if(StringUtils.isNotBlank(createUser)){
            select.put("createUser",createUser);
        }
        page.setSelect(select);
        return articleService.findBySomeParamForPage(page);
    }

    /***
     * 获取详情
     * @param id        article的id
     * @return
     */
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Result<BlogArticleInfo> detail(@PathVariable("id") String id){
        BlogArticleInfo info= articleService.findById(id);
        if(info==null){
            return new Result<>("article不存在");
        }
        Result<BlogArticleInfo> result=new Result<>();
        result.setData(info);
        return result;
    }
}
