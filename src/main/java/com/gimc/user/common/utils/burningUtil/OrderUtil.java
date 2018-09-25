package com.gimc.user.common.utils.burningUtil;

import java.text.SimpleDateFormat;  
import java.util.Date;
import java.util.Random;  
  
/** 
 * @ClassName: MakeOrderNum 
 * @CreateTime 2015年9月13日 下午4:51:02 
 * @author : mayi 
 * @Description: 订单号生成工具，生成非重复订单号，理论上限1毫秒1000个，可扩展 
 * 
 */  
public class OrderUtil {  
    /** 
     * 锁对象，可以为任意对象 
     */  
    private static Object lockObj = "lockerOrder";  
    /** 
     * 订单号生成计数器 
     */  
    private static long orderNumCount = 2019L;  
    /** 
     * 每毫秒生成订单号数量最大值 
     */  
    private int maxPerMSECSize=10000;  
    /** 
     * 生成非重复订单号，理论上限1毫秒1000个，可扩展 
     * @param tname 测试用 
     */  
    public String makeOrderNum(String tname) {  
        try {  
            // 最终生成的订单号  
            String finOrderNum = "";  
            synchronized (lockObj) {  
                // 取系统当前时间作为订单号变量前半部分，精确到毫秒  
                long nowLong = Long.parseLong(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));  
                // 计数器到最大值归零，可扩展更大，目前1毫秒处理峰值(10000-2019)个
                if (orderNumCount >= maxPerMSECSize) {  
                    orderNumCount = 2019L;  
                }  
                //组装订单号  
                String countStr=maxPerMSECSize +orderNumCount+"";  
                
                Random r = new Random();
        		String nextInt = r.nextInt(9000)+1000+"";
                
                finOrderNum=nowLong+countStr.substring(1)+nextInt;  
                orderNumCount++;  
                System.out.println(finOrderNum + "--" + Thread.currentThread().getName() + "::" + tname );  
                
                return finOrderNum;
                // Thread.sleep(1000);  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
            return null;
        }  
    }  
  
    public static void main(String[] args) {  
        // 测试多线程调用订单号生成工具  
        try {  
            for (int i = 0; i < 200; i++) {  
                Thread t1 = new Thread(new Runnable() {  
                    public void run() {  
                        OrderUtil makeOrder = new OrderUtil();  
                        makeOrder.makeOrderNum("a");  
                    }  
                }, "at" + i);  
                t1.start();  
  
                Thread t2 = new Thread(new Runnable() {  
                    public void run() {  
                        OrderUtil makeOrder = new OrderUtil();  
                        makeOrder.makeOrderNum("b");  
                    }  
                }, "bt" + i);  
                t2.start();  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
  
    }  
  
}  
