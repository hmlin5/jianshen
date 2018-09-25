package com.gimc.user.common.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.springframework.util.FileCopyUtils;

import com.gimc.user.common.config.Global;


/**
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author ShineU
 * @date 2015年11月19日 下午4:21:51
 * @version 1.0
 */

public class FileUtil {
	protected static Logger log = Logger.getLogger(FileUtil.class);

	public static void storeFile(final String basepath, final String fileName,
			String fileSuffix, final byte[] fileis) {
		FileOutputStream fos = null;
		try {
			final File file = new File(basepath + "/" + fileName
					+ fileSuffix);
			fos = new FileOutputStream(file);
			fos.write(fileis);
			fos.flush();
		} catch (Exception e) {
			log.error("保存文件异常！", e);
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		log.info("存储文件【" + (fileName + fileSuffix) + "】完毕！");
	}

	public static String getRadomFileName() {
		String timeStr = DateUtil.replaceTimeChar(DateUtil.formateDate2Str(
				new Date(), DateUtil.yyyyMMddHHmmss));
		timeStr = timeStr + NumberUtil.nextInt(1000, 9999);
		return timeStr;
	}

	// 获取年月日文件夹
	public static String getMediaPath() {
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DAY_OF_MONTH);
		String mm = month < 10 ? "0" + month : month + "";
		String dd = day < 10 ? "0" + day : day + "";
		return year + "/" + mm + "/" + dd;
	}

	public static FileBean savePic(byte[] photo) throws IOException {
		String savePath = Global.getConfig("userfiles.basedir") + getMediaPath();
		String destfileName = makeFileKey() + ".jpg";
		//判断是否存在文件夹
		File file = new File(savePath);
		//判断文件夹是否存在,如果不存在则创建文件夹
		if (!file.exists()) {
		   file.mkdirs();
		}	
		String realFullPath = savePath + "/" + destfileName;
		File fileUpload = new File(realFullPath);
		FileCopyUtils.copy(photo, fileUpload);
		String httpPath = Global.getConfig("RESOURECE_URL") + getMediaPath()
				+ "/" + destfileName;
		FileBean bean = new FileBean();
		bean.setName(getMediaPath() + "/" + destfileName);
		bean.setHttpURL(httpPath);
		bean.setSuffix(".jpg");
		return bean;
	}
	
	public static String makeFileKey(){
		Random random = new Random();
		int max = 999999;
		int min = 100000;
		Integer s = random.nextInt(max) % (max - min + 1) + min;
		int c = s.toString().length();
		int rc = 6 - c;
		String pre = "";
		for (int i = 0; i < rc; i++) {
			pre = pre + "0";
		}
		String fileKey = DateUtil
				.formateDate2Str(new Date(), DateUtil.yyyyMMdd) + pre + s;
		return fileKey;
	}
	
	public static void main(String[] args) {
		File homedir = new File("c:/ad/asd/sd");
		if (!homedir.exists()) {
			try {
				homedir.mkdirs();
			} catch (Exception ex) {
			}
		}
	}

}
