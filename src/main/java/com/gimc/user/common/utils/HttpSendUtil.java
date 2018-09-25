package com.gimc.user.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

import org.apache.log4j.Logger;






public class HttpSendUtil{
	
	static Logger logger = Logger.getLogger(HttpSendUtil.class);
	
	
	/**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendHttp(String url, String param, Integer timeOutMillis) {
    	Date date = new Date();
    	
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            
            if(timeOutMillis!=null){
            	conn.setConnectTimeout(timeOutMillis);
            	conn.setReadTimeout(timeOutMillis);
            }
//            conn.setConnectTimeout(SysConfigData.NET_TIME_OUT);
//            conn.setReadTimeout(SysConfigData.NET_TIME_OUT);
            
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        }catch(ConnectException e){
        	 logger.info("发送 "+url+"?"+param+" 链接超时！");
        }catch(SocketTimeoutException e){
        	 logger.info("发送 "+url+"?"+param+" 请求超时！");
        }catch (Exception e) {
            logger.info("发送 "+url+"?"+param+" 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }    

    
    public static void main(String[] args) throws InterruptedException {
    	
//    	while(1==1){
    		
//    	String a = sendHttp("http://127.0.0.1:8070/bycj/api/data/getDataCatalogList.json", "parentCatalogId=-2000&accessToken=18E9E5A809C8D5437DEC6080E4169C46", null);
//    	String a = sendHttp("http://127.0.0.1:8070/bycj/api/data/addUserDataCatalog.json", "catalogId=101&accessToken=18E9E5A809C8D5437DEC6080E4169C46", null);
//    	String a = sendHttp("http://127.0.0.1:8070/bycj/api/data/delUserDataCatalog.json", "catalogId=101&accessToken=18E9E5A809C8D5437DEC6080E4169C46", null);
//    	String a = sendHttp("http://127.0.0.1:8070/bycj/api/data/sortUserDataCatalog.json", "catalogIdStr=51,100&accessToken=18E9E5A809C8D5437DEC6080E4169C46", null);
    	
//    	String a = sendHttp("http://cs.youxiangw.com/bycj/api/data/getDataCatalogList.json", "parentCatalogId=7&sign=1", null);
//    	String a = sendHttp("http://127.0.0.1:8070/bycj/api/data/getData.json", "catalogCode=GBPUSD", null);
//    	String a = sendHttp("http://cs.youxiangw.com/bycj/api/data/getKData.json", "catalogCode=399006.SZ&interval=5&rows=5", null);
    	String a = sendHttp("http://cs.youxiangw.com/bycj/api/data/getData.json", "catalogCode=GBPUSD", null);
//    	String a = sendHttp("http://cs.youxiangw.com/bycj/api/data/getKData.json", "catalogCode=CONC&interval=5&rows=5", null);
    	
//		String a = sendHttp("http://api.q.fx678.com/quotes.php?exchName=WGJS&symbol=XAG&st=0."+(new Date().getTime()), null);
//		
    	
//    	String a = sendHttp("http://127.0.0.1:8070/bycj/api/me/addUserStore.json", "articleId=1029&type=0&accessToken=1A782D1D8397B6BA915AECA7A98AFC56", null);
//    	String a = sendHttp("http://127.0.0.1:8070/bycj/api/me/getUserStoreList.json", "accessToken=2A782D1D8397B6BA915AECA7A98AFC56", null);
//    	String a = sendHttp("http://127.0.0.1:8070/bycj/api/me/getUserIsStore.json", "articleId=1029&type=0&accessToken=C60952AFA879250E678D2114B942E769", null);
    	
//    	String a = sendHttp("http://127.0.0.1:8070/bycj/api/user//updateUser.json", "accessToken=1DE401549408D6E650C0DE10393A56CF&nickName=%E4%BD%A0%E6%98%AF", null);
    	
//    	String a = sendHttp("http://127.0.0.1:8070/bycj/api/msg/getMsgList.json", "sign=1", null);
    	
    	System.out.println(a);
    	System.out.println("---------------");
    	Thread.sleep(500);
//    	}
	}
    
	
	
}
