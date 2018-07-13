package com.zhanglugao.dao;
import com.zhanglugao.base.IBaseDao;
import com.zhanglugao.base.PageParameter;
import com.zhanglugao.vo.BlogArticleInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BlogArticleDao extends IBaseDao<BlogArticleInfo> {
    /**-CustomBegin*****/
    List<BlogArticleInfo> findBySomeParamForPage(@Param("obj") PageParameter page);
    /**-CustomEnd*******/
}
