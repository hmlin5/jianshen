package com.gimc.user.common.msg;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.gimc.user.common.config.Global;
/*
 * 利用HttpClient进行post请求的工具类
 */
public class HttpClientUtil {
	

	private static final String APP_KEY = "api";
	
	private static final String POST_URL = "http://sms-api.luosimao.com/v1/send.json";
	private static final String charset = "utf-8";  
	
	 /** 
     * 模拟请求 
     *  
     * @param url       资源地址 
     * @param map   参数列表 
     * @param encoding  编码 
     * @return 
     * @throws ParseException 
     * @throws IOException 
     */  
    public static String doPost(String url, Map<String,String> map,String encoding) throws ParseException, IOException{  
        String body = "";  
  
        //创建httpclient对象  
        CloseableHttpClient client = HttpClients.createDefault();  
        //创建post方式请求对象  
        HttpPost httpPost = new HttpPost(url);  
          
        //装填参数  
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();  
        if(map!=null){  
            for (Entry<String, String> entry : map.entrySet()) {  
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));  
            }  
        }  
        //设置参数到请求对象中  
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, encoding));  
        httpPost.addHeader("Authorization", getHeader());
        
        System.out.println("请求地址："+url);  
        System.out.println("请求参数："+nvps.toString());  
          
        //设置header信息  
        //指定报文头【Content-type】、【User-Agent】  
        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");  
        httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");  
          
        //执行请求操作，并拿到结果（同步阻塞）  
        CloseableHttpResponse response = client.execute(httpPost);  
        //获取结果实体  
        HttpEntity entity = response.getEntity();  
        if (entity != null) {  
            //按指定编码转换结果实体为String类型  
            body = EntityUtils.toString(entity, encoding);  
        }  
        EntityUtils.consume(entity);  
        //释放链接  
        response.close();  
        return body;  
    }  


	
	public static String SendMsg(String mobile , String message){
		try {
			String httpOrgCreateTest = POST_URL;  
			Map<String,String> createMap = new HashMap<String,String>();   
			createMap.put("mobile", mobile);  
			createMap.put("message", message);  
			String httpOrgCreateTestRtn = HttpClientUtil.doPost(httpOrgCreateTest,createMap,charset); 
			System.out.println("result:"+httpOrgCreateTestRtn);
			return httpOrgCreateTestRtn;
		
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
        
    }
	
	/**
	   * 构造Basic Auth认证头信息
	   * 
	   * @return
	   */
	private static String getHeader() {
	    String auth = APP_KEY + ":" + Global.getMsgKey();
	    byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("UTF-8")));
	    String authHeader = "Basic " + new String(encodedAuth);
	    return authHeader;
	}
	
	
  	/**
     * 
     * 用于产生随机密码
     * @Title: Rands  
     * @param lengthNum 随机密码长度 默认为8
     * @return
     *
     */
	 public static String getRandIntPassword(int lengthNum) {
		       if(lengthNum == 0){
		    	 lengthNum = 8;
		       }
		        Random rd = new Random(); // 创建随机对象
		        String n = "";            //保存随机数
		        int rdGet; // 取得随机数
		        do {

		            rdGet = Math.abs(rd.nextInt()) % 10 + 48; // 产生48到57的随机数(0-9的键位值)
		           
	 		        char num1 = (char) rdGet;            //int转换char
	 		        String dd = Character.toString(num1);
	 		        n += dd;
		        } while (n.length() < lengthNum);// 设定长度，此处假定长度小于8
		     return n;
	 }
}