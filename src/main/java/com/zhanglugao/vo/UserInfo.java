
package com.zhanglugao.vo;
import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
@SuppressWarnings("serial")
public class UserInfo implements Serializable
{
	
	private Integer id;   
	private String name;   
	private String nickName;   
	private String picture;   
	private String mobile;   
	private String email;   
	private String password;   
	private String realName;   
    
    public UserInfo(){}
   
    
    /**
     * 用户名
     */ 	
	public Integer getId(){
        return id;
    }
    
     /**
     * 用户名
     */ 	
    public void setId(Integer id){
        this.id=id;
    }
    
    /**
     * 
     */ 	
	public String getName(){
        return name;
    }
    
     /**
     * 
     */ 	
    public void setName(String name){
        this.name=name;
    }
    
    /**
     * 昵称
     */ 	
	public String getNickName(){
        return nickName;
    }
    
     /**
     * 昵称
     */ 	
    public void setNickName(String nickName){
        this.nickName=nickName;
    }
    
    /**
     * 图片url
     */ 	
	public String getPicture(){
        return picture;
    }
    
     /**
     * 图片url
     */ 	
    public void setPicture(String picture){
        this.picture=picture;
    }
    
    /**
     * 手机号
     */ 	
	public String getMobile(){
        return mobile;
    }
    
     /**
     * 手机号
     */ 	
    public void setMobile(String mobile){
        this.mobile=mobile;
    }
    
    /**
     * 邮箱
     */ 	
	public String getEmail(){
        return email;
    }
    
     /**
     * 邮箱
     */ 	
    public void setEmail(String email){
        this.email=email;
    }
    
    /**
     * 密码
     */ 	
	public String getPassword(){
        return password;
    }
    
     /**
     * 密码
     */ 	
    public void setPassword(String password){
        this.password=password;
    }
    
    /**
     * 真实姓名
     */ 	
	public String getRealName(){
        return realName;
    }
    
     /**
     * 真实姓名
     */ 	
    public void setRealName(String realName){
        this.realName=realName;
    }
    
    public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
           .append("Id",getId())
           .append("Name",getName())
           .append("NickName",getNickName())
           .append("Picture",getPicture())
           .append("Mobile",getMobile())
           .append("Email",getEmail())
           .append("Password",getPassword())
           .append("RealName",getRealName())

			.toString();
            
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof UserInfo == false) return false;
		if(this == obj) return true;
		UserInfo other = (UserInfo)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
    
    /**-CustomBegin*****//**-CustomEnd*******/
}
