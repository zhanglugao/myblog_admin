
package com.zhanglugao.vo;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
@SuppressWarnings("serial")
public class ArticleReplyInfo implements Serializable
{
	
	private Integer id;   
	private String articleId;   
	private Integer sort;   
	private String replyId;   
	private String createUser;   
	private java.util.Date gmtCreate;   
	private Integer viewTimes;   
	private Integer replyTimes;   
	private Integer upvoteTimes;   
	private Integer steponTimes;   
	private String content;   
    
    public ArticleReplyInfo(){}
   
    
    /**
     * 
     */ 	
	public Integer getId(){
        return id;
    }
    
     /**
     * 
     */ 	
    public void setId(Integer id){
        this.id=id;
    }
    
    /**
     * 
     */ 	
	public String getArticleId(){
        return articleId;
    }
    
     /**
     * 
     */ 	
    public void setArticleId(String articleId){
        this.articleId=articleId;
    }
    
    /**
     * 
     */ 	
	public Integer getSort(){
        return sort;
    }
    
     /**
     * 
     */ 	
    public void setSort(Integer sort){
        this.sort=sort;
    }
    
    /**
     * 
     */ 	
	public String getReplyId(){
        return replyId;
    }
    
     /**
     * 
     */ 	
    public void setReplyId(String replyId){
        this.replyId=replyId;
    }
    
    /**
     * 
     */ 	
	public String getCreateUser(){
        return createUser;
    }
    
     /**
     * 
     */ 	
    public void setCreateUser(String createUser){
        this.createUser=createUser;
    }
    
    /**
     * 
     */ 	
	public java.util.Date getGmtCreate(){
        return gmtCreate;
    }
    
     /**
     * 
     */ 	
    public void setGmtCreate(java.util.Date gmtCreate){
        this.gmtCreate=gmtCreate;
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
     * 回复内容
     */ 	
	public String getContent(){
        return content;
    }
    
     /**
     * 回复内容
     */ 	
    public void setContent(String content){
        this.content=content;
    }
    
    public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
           .append("Id",getId())
           .append("ArticleId",getArticleId())
           .append("Sort",getSort())
           .append("ReplyId",getReplyId())
           .append("CreateUser",getCreateUser())
           .append("GmtCreate",getGmtCreate())
           .append("ViewTimes",getViewTimes())
           .append("ReplyTimes",getReplyTimes())
           .append("UpvoteTimes",getUpvoteTimes())
           .append("SteponTimes",getSteponTimes())
           .append("Content",getContent())

			.toString();
            
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof ArticleReplyInfo == false) return false;
		if(this == obj) return true;
		ArticleReplyInfo other = (ArticleReplyInfo)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
    
    /**-CustomBegin*****//**-CustomEnd*******/
}
