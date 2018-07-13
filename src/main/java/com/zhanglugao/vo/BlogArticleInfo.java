
package com.zhanglugao.vo;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
@SuppressWarnings("serial")
public class BlogArticleInfo implements Serializable
{
	
	private String id;   
	private String createUser;   
	private java.util.Date gmtCreate;   
	private java.util.Date gmtModified;   
	private String content;   
	private String title;   
	private String labels;   
	private Integer viewTimes;   
	private Integer replyTimes;   
	private Integer upvoteTimes;   
	private Integer steponTimes;   
	private String categories;   
    
    public BlogArticleInfo(){}
   
    
    /**
     * 
     */ 	
	public String getId(){
        return id;
    }
    
     /**
     * 
     */ 	
    public void setId(String id){
        this.id=id;
    }
    
    /**
     * 创建人
     */ 	
	public String getCreateUser(){
        return createUser;
    }
    
     /**
     * 创建人
     */ 	
    public void setCreateUser(String createUser){
        this.createUser=createUser;
    }
    
    /**
     * 创建时间
     */ 	
	public java.util.Date getGmtCreate(){
        return gmtCreate;
    }
    
     /**
     * 创建时间
     */ 	
    public void setGmtCreate(java.util.Date gmtCreate){
        this.gmtCreate=gmtCreate;
    }
    
    /**
     * 最后修改时间
     */ 	
	public java.util.Date getGmtModified(){
        return gmtModified;
    }
    
     /**
     * 最后修改时间
     */ 	
    public void setGmtModified(java.util.Date gmtModified){
        this.gmtModified=gmtModified;
    }
    
    /**
     * 内容
     */ 	
	public String getContent(){
        return content;
    }
    
     /**
     * 内容
     */ 	
    public void setContent(String content){
        this.content=content;
    }
    
    /**
     * 标题
     */ 	
	public String getTitle(){
        return title;
    }
    
     /**
     * 标题
     */ 	
    public void setTitle(String title){
        this.title=title;
    }
    
    /**
     * 标签
     */ 	
	public String getLabels(){
        return labels;
    }
    
     /**
     * 标签
     */ 	
    public void setLabels(String labels){
        this.labels=labels;
    }
    
    /**
     * 查看次数
     */ 	
	public Integer getViewTimes(){
        return viewTimes;
    }
    
     /**
     * 查看次数
     */ 	
    public void setViewTimes(Integer viewTimes){
        this.viewTimes=viewTimes;
    }
    
    /**
     * 回复次数
     */ 	
	public Integer getReplyTimes(){
        return replyTimes;
    }
    
     /**
     * 回复次数
     */ 	
    public void setReplyTimes(Integer replyTimes){
        this.replyTimes=replyTimes;
    }
    
    /**
     * 点赞次数
     */ 	
	public Integer getUpvoteTimes(){
        return upvoteTimes;
    }
    
     /**
     * 点赞次数
     */ 	
    public void setUpvoteTimes(Integer upvoteTimes){
        this.upvoteTimes=upvoteTimes;
    }
    
    /**
     * 点踩次数
     */ 	
	public Integer getSteponTimes(){
        return steponTimes;
    }
    
     /**
     * 点踩次数
     */ 	
    public void setSteponTimes(Integer steponTimes){
        this.steponTimes=steponTimes;
    }
    
    /**
     * 类别
     */ 	
	public String getCategories(){
        return categories;
    }
    
     /**
     * 类别
     */ 	
    public void setCategories(String categories){
        this.categories=categories;
    }
    
    public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
           .append("Id",getId())
           .append("CreateUser",getCreateUser())
           .append("GmtCreate",getGmtCreate())
           .append("GmtModified",getGmtModified())
           .append("Content",getContent())
           .append("Title",getTitle())
           .append("Labels",getLabels())
           .append("ViewTimes",getViewTimes())
           .append("ReplyTimes",getReplyTimes())
           .append("UpvoteTimes",getUpvoteTimes())
           .append("SteponTimes",getSteponTimes())
           .append("Categories",getCategories())

			.toString();
            
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof BlogArticleInfo == false) return false;
		if(this == obj) return true;
		BlogArticleInfo other = (BlogArticleInfo)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
    
    /**-CustomBegin*****//**-CustomEnd*******/
}
