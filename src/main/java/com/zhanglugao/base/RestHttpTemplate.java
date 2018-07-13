package com.zhanglugao.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Component
public class RestHttpTemplate {

    @Autowired
    private RestTemplate restTemplate;

    public RestTemplate getRestTemplate(){
        if(restTemplate==null) {
            HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory();
            httpRequestFactory.setConnectionRequestTimeout(30000);
            httpRequestFactory.setConnectTimeout(30000);
            httpRequestFactory.setReadTimeout(30000);
            restTemplate= new RestTemplate(httpRequestFactory);
        }
        return restTemplate;
    }

    /***
     *
     * @param url			请求的url地址 不允许包含?以及?后的参数
     * @param responseType	请求返回的实体类型
     * @param map			请求参数集合 自动封装参数进url
     * @return
     */
    public <T> T sendGet(String url,Class<T> responseType,Map<String,Object> map){
        url = url + "?";
        url=handleParamMap(url,map);
        if(map==null){
            map=new HashMap<>(2);
        }
        T t=getRestTemplate().getForObject(url,responseType,map);
        return t;
    }

    /***
     *
     * @param url			请求的url地址 不允许包含?以及?后的参数
     * @param responseType	请求返回的实体类型
     * @param map			请求参数集合 自动封装参数进url
     * @return
     */
    public <T> T sendDelete(String url,Class<T> responseType,Map<String,Object> map){
        url+="?";
        if(map!=null) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if(entry.getValue()!=null) {
                    url += entry.getKey() + "=" + entry.getValue() + "&";
                }
            }
        }
        ResponseEntity<T> entity=getRestTemplate().exchange(url, HttpMethod.DELETE,null,responseType);
        T t=entity.getBody();
        return t;
    }

    /***
     *
     * @param url
     * @param responseType
     * @param map
     * @param <T>
     * @return
     */
    public <T> T sendPost(String url,Class<T> responseType,Map<String,Object> map){
        MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
        if(map!=null) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if(entry.getValue()!=null) {
                    postParameters.add(entry.getKey(), entry.getValue());
                }
            }
        }
        T t=getRestTemplate().postForObject(url,postParameters,responseType);
        return t;
    }

    private String handleParamMap(String url, Map<String, Object> map){
        if (map != null) {
            boolean first = true;
            Iterator var5 = ((Map)map).entrySet().iterator();
            while(var5.hasNext()) {
                Map.Entry<String, Object> entry = (Map.Entry)var5.next();
                if (entry.getValue() != null) {
                    if (first) {
                        url = url + (String)entry.getKey() + "={" + (String)entry.getKey() + "}";
                        first = false;
                    } else {
                        url = url + "&" + (String)entry.getKey() + "={" + (String)entry.getKey() + "}";
                    }
                }
            }
        }
        return url;
    }

    public <T> T sendGet(String url, ParameterizedTypeReference<T> parameterizedTypeReference, Map<String, Object> map) {
        url = url + "?";
        url=handleParamMap(url,map);
        if(map==null){
            map=new HashMap<>(2);
        }
        HttpHeaders headers =new HttpHeaders();
        HttpEntity<String> requestEntity = new HttpEntity<String>(null, headers);
        ResponseEntity<T> resp = getRestTemplate().exchange(url, HttpMethod.GET, requestEntity,
                parameterizedTypeReference,(Map)map);
        return resp.getBody();
    }

    public <T> T sendPut(String url, ParameterizedTypeReference<T> parameterizedTypeReference, Map<String, Object> map) {
        url = url + "?";
        url=handleParamMap(url,map);
        if(map==null){
            map=new HashMap<>(2);
        }
        HttpHeaders headers =new HttpHeaders();
        HttpEntity<String> requestEntity = new HttpEntity<String>(null, headers);
        ResponseEntity<T> resp = getRestTemplate().exchange(url, HttpMethod.PUT, requestEntity,
                parameterizedTypeReference,(Map)map);
        return resp.getBody();
    }

    public <T> T sendPost(String url, ParameterizedTypeReference<T> parameterizedTypeReference, Map<String, Object> map) {
        url = url + "?";
        url=handleParamMap(url,map);
        if(map==null){
            map=new HashMap<>(2);
        }
        HttpHeaders headers =new HttpHeaders();
        HttpEntity<String> requestEntity = new HttpEntity<String>(null, headers);
        ResponseEntity<T> resp = getRestTemplate().exchange(url, HttpMethod.POST, requestEntity,
                parameterizedTypeReference,(Map)map);
        return resp.getBody();
    }

    public <T> T sendDelete(String url, ParameterizedTypeReference<T> parameterizedTypeReference, Map<String, Object> map) {
        url = url + "?";
        url=handleParamMap(url,map);
        if(map==null){
            map=new HashMap<>(2);
        }
        HttpHeaders headers =new HttpHeaders();
        HttpEntity<String> requestEntity = new HttpEntity<String>(null, headers);
        ResponseEntity<T> resp = getRestTemplate().exchange(url, HttpMethod.DELETE, requestEntity,
                parameterizedTypeReference,(Map)map);
        return resp.getBody();
    }


    public <T> T sendPut(String url, Class<T> responseType, Map<String, Object> map) {
        MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap();
        if (map != null) {
            Iterator var5 = map.entrySet().iterator();

            while(var5.hasNext()) {
                Map.Entry<String, Object> entry = (Map.Entry)var5.next();
                if (entry.getValue() != null) {
                    postParameters.add(entry.getKey(), entry.getValue().toString());
                }
            }
        }

        HttpEntity entity = new HttpEntity(postParameters, postParameters);
        ResponseEntity<T> info = this.getRestTemplate().exchange(url, HttpMethod.PUT, entity, responseType, new Object[0]);
        return info.getBody();
    }
}
