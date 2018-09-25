package com.gimc.user.common.utils.https;

import java.util.ArrayList;  
import java.util.HashMap;
import java.util.Iterator;  
import java.util.List;  
import java.util.Map;  
import java.util.Map.Entry;  
import org.apache.http.HttpEntity;  
import org.apache.http.HttpResponse;  
import org.apache.http.NameValuePair;  
import org.apache.http.client.HttpClient;  
import org.apache.http.client.entity.UrlEncodedFormEntity;  
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;  
import org.apache.http.message.BasicNameValuePair;  
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;  
import org.apache.log4j.Logger;

/* 
 * 利用HttpClient进行post请求的工具类 
 */  
public class HttpClientUtil {  
	
	static Logger logger = Logger.getLogger(HttpClientUtil.class);
	
    public static String doPost(String url,Map<String,String> map,String charset, Integer timeOutMins){  
        HttpClient httpClient = null;  
        HttpPost httpPost = null;  
        String result = null;  
        try{  
            httpClient = new SSLClient();  
            httpPost = new HttpPost(url);  
            //设置参数  
            List<NameValuePair> list = new ArrayList<NameValuePair>();  
            Iterator iterator = map.entrySet().iterator();  
            while(iterator.hasNext()){  
                Entry<String,String> elem = (Entry<String, String>) iterator.next();  
                list.add(new BasicNameValuePair(elem.getKey(),elem.getValue()));  
            }  
            if(list.size() > 0){  
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,charset);  
                httpPost.setEntity(entity);  
            }  
            //设置超时
            if(timeOutMins!=null){
            	httpClient.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT, timeOutMins);
            	httpClient.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, timeOutMins); 
            }
            
            HttpResponse response = httpClient.execute(httpPost);  
            if(response != null){  
                HttpEntity resEntity = response.getEntity();  
                if(resEntity != null){  
                    result = EntityUtils.toString(resEntity,charset);  
                }  
            }  
        }catch(Exception ex){  
        	logger.info("发送 "+url+"?"+map+" 请求出现异常！"+ex);
            ex.printStackTrace();  
        }  
        return result;  
    }  
    
    public static String doGet(String url,Map<String,String> map,String charset, Integer timeOutMins){  
    	if(map == null){
    		map = new HashMap();
    	}
    	
        HttpClient httpClient = null;  
        HttpGet httpGet = null;  
        String result = null;  
        try{  
            httpClient = new SSLClient();  
            httpGet = new HttpGet(url);  
            //设置参数  
            List<NameValuePair> list = new ArrayList<NameValuePair>();  
            Iterator iterator = map.entrySet().iterator();  
            while(iterator.hasNext()){  
                Entry<String,String> elem = (Entry<String, String>) iterator.next();  
                list.add(new BasicNameValuePair(elem.getKey(),elem.getValue()));  
            }  
            if(list.size() > 0){  
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,charset);  
                //httpGet.setEntity(entity);  
            }  
            //设置超时
            if(timeOutMins!=null){
            	httpClient.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT, timeOutMins);
            	httpClient.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, timeOutMins); 
            }
            
            HttpResponse response = httpClient.execute(httpGet);  
            if(response != null){  
                HttpEntity resEntity = response.getEntity();  
                if(resEntity != null){  
                    result = EntityUtils.toString(resEntity,charset);  
                }  
            }  
        }catch(Exception ex){  
        	logger.info("发送 "+url+"?"+map+" 请求出现异常！"+ex);
            ex.printStackTrace();  
        }  
        return result;  
    }  
    
    
    
      
    public static void test(){  
//        String httpOrgCreateTest = "https://datacenter.jin10.com/get_market_history?jsonpCallback=jQuery111109118271472425017_1508381751599&time=1508381763502&ttype=5&etime=1508381763502&key=f7c67fad6319a551c284b2d474bd1b07&cutCount=2&sym=XAGUSD&isLocalWeek=1&_=1508381763502";  
        
    	String httpOrgCreateTest = "https://sshiendgnn.jin10.com:8083/socket.io/?EIO=3&transport=polling&t=LypYr-o";
        
        Map<String,String> createMap = new HashMap<String,String>();  
//        createMap.put("authuser","*****");  
//        String httpOrgCreateTestRtn = doPost(httpOrgCreateTest,createMap,charset);  
        String httpOrgCreateTestRtn = doGet(httpOrgCreateTest,null,"utf-8", 500);
        
        System.out.println("result:"+httpOrgCreateTestRtn);  
    }  
      
    public static void main(String[] args){  
       test();  
    }  
    
}  
	
	
