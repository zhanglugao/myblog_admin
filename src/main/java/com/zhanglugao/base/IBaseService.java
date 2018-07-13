package com.zhanglugao.base;

import java.io.Serializable;
import java.util.List;

public abstract  class IBaseService<M extends Serializable> {
    
    protected final  String redisSelectFlag="select";
    protected final  String redisUpdateFlag="update";
    protected final  String redisDeleteFlag="delete";
    
    private IBaseDao<M> idao;
    public void setRepository(IBaseDao<M> repository) {
        this.idao = repository;  
    }  
    

  /**
  * 查询表中 符合入参的集�?
  * @param findInfo 入参,查询条件
  * @return List<DictionaryInfo> 出参,符合条件的结果集
  */
 public List<M> findByParam(M findInfo) {
     return idao.findByParam(findInfo);
 }
   
   /**
  * 查询Dictionary表中 符合入参的集�?
  * @param findInfo 入参,查询条件
  * @return List<DictionaryInfo> 出参,符合条件的结果集
  */
 public List<M> findByParamForPage(PageParameter findInfo) {
     return idao.findByParamForPage(findInfo);
 }
 
 /**
  * 新增对象
  * @param info 对象
  * @return 对象id
  */
 public Integer add(M info) {
     return idao.insert(info);
 }   
 
 /**
  * 更新对象
  * @param info 对象(主键必须非空), �?��做数据校�?
  * @return 影响条数
  */
 public Integer update(M info,Integer versionId) {
     int result= idao.update(info,versionId);
       if(result!=0){
           handleRedis(info,redisUpdateFlag);
       }
       return result;
 }   
 /**
  * 根据主键删除
  * @param id 对象(主键), 删除前记得做数据校验
  * @return versionId 版本id
  */
 public Integer delete(Serializable id,Integer versionId) {
     int result= idao.deleteById(id,versionId);
       if(result!=0){
           handleRedis(id,redisDeleteFlag);
       }
       return result;
 }
 
 /**
  * 根据联合主键删除
  * @param  ids map(主键), 删除前记得做数据校验
  * @return 对象id
  */
 public Integer delete(java.util.Map<String, String> ids) {
     return idao.deleteById(ids);
 }
   
 /**
  * 根据id查询对象
  * @param id 对象主键
  * @return 对象本身
  */
 public M findById(Serializable id) {
     M result= null;
       Object redisResult=handleRedis(id,redisSelectFlag);
       if(redisResult==null){
           result=idao.findById(id);
       }
       else{
           result=(M)redisResult;
       }
       return result;
 }
 
 /**
  * 根据联合主键查询对象
  * @param ids map对象主键
  * @return 对象本身
  */
 public M findById(java.util.Map<String, String> ids) {
     return idao.findById(ids);
 }
 
 public Integer sealFlag (Serializable id,Integer versionId){
     int result= idao.updateSealFlag(id,versionId);
     if(result!=0){
         handleRedis(id,"delete");
     }
     return result;
 }
  
  /**
  * 根据联合主键封存
  * @param ids 对象(主键)
  * @return 对象id
  */
 public Integer sealFlag(java.util.Map<String, String> ids){
     return idao.updateSealFlag(ids);
 }
 
 /**
  * 
 * @Title: 处理redis的方法，子类需要重写方法
 * @Description: TODO(这里用一句话描述这个方法的作�? 
 * @param @param obj ：vo 或者id
 * @param @param flag：select update insert delete
 * @param @return  参数说明 
 * @return Object    返回类型 
 * @throws
  */
 protected Object handleRedis(Object obj,String flag){
     return null;
 
 }
    
}
