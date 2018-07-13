package com.zhanglugao.service;

import com.zhanglugao.base.IBaseService;
import com.zhanglugao.base.PageBean;
import com.zhanglugao.base.PageParameter;
import com.zhanglugao.dao.BlogArticleDao;
import com.zhanglugao.swagger.constant.Result;
import com.zhanglugao.vo.BlogArticleInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class ArticleService extends IBaseService<BlogArticleInfo>{

    @Autowired
    private BlogArticleDao articleDao;

    @PostConstruct
    private void setIDao(){
        this.setRepository(articleDao);
    }

    public Result<PageBean<BlogArticleInfo>> findBySomeParamForPage(PageParameter page) {
        Result<PageBean<BlogArticleInfo>> result=new Result<>();
        List<BlogArticleInfo> list=articleDao.findBySomeParamForPage(page);
        PageBean<BlogArticleInfo> pageBean=new PageBean<>();
        pageBean.setRows(list);
        pageBean.setTotal((long) page.getTotalCount());
        result.setData(pageBean);
        return result;
    }
}
