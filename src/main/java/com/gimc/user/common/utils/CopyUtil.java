package com.gimc.user.common.utils;

import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

public class CopyUtil {

	public static void copyPropToStr(Object tag, Object src, String[] noprops) {
		noprops = noprops == null ? new String[] { "class" } : noprops;
		PropertyDescriptor[] tagPds = PropertyUtils.getPropertyDescriptors(tag);
		PropertyDescriptor[] srcPds = PropertyUtils.getPropertyDescriptors(src);
		String[] srcPdsName = new String[srcPds.length];
		for (int i = 0; i < srcPds.length; i++) {
			srcPdsName[i] = srcPds[i].getName();
		}
		// 迭代目标对象
		for (PropertyDescriptor tagPd : tagPds) {
			if (ArrayUtils.indexOf(noprops, tagPd.getName()) >= 0) {
				continue;
			}
			if (!(ArrayUtils.indexOf(srcPdsName, tagPd.getName()) >= 0)) {
				continue;
			}
			try {
				if (tagPd.getWriteMethod() == null) // 无setter方法的属性无需拷
					continue;
				else if (PropertyUtils.getProperty(src, tagPd.getName()) == null) // src的属性值为空时不用拷
					continue;
			} catch (Exception e) {
				continue;
			}
			// 拷属性
			try {
				Object sourceVObj = PropertyUtils.getProperty(src,
						tagPd.getName());
				PropertyUtils
						.setProperty(tag, tagPd.getName(), sourceVObj + "");
			} catch (Exception e) {
				throw new RuntimeException("拷贝属性" + tagPd.getName() + "不成功！", e);
			}
		}
	}

	/**
	 * imageDataList 拷贝共有属性到目录对象 tag 拷贝至目标对象 src 拷贝的源对象 noprops 目标对象中无需拷贝的属性
	 **/
	public static void copyBeanProToStr(Object tag, Object src, String[] noprops) {
		noprops = noprops == null ? new String[] { "class" } : noprops;
		PropertyDescriptor[] tagPds = PropertyUtils.getPropertyDescriptors(tag);
		PropertyDescriptor[] srcPds = PropertyUtils.getPropertyDescriptors(src);
		String[] srcPdsName = new String[srcPds.length];
		for (int i = 0; i < srcPds.length; i++) {
			srcPdsName[i] = srcPds[i].getName();
		}
		// 迭代目标对象
		for (PropertyDescriptor tagPd : tagPds) {
			if (ArrayUtils.indexOf(noprops, tagPd.getName()) >= 0) {
				continue;
			}
			if (!(ArrayUtils.indexOf(srcPdsName, tagPd.getName()) >= 0)) {
				continue;
			}
			try {
				if (tagPd.getWriteMethod() == null) // 无setter方法的属性无需拷
					continue;
				else if (PropertyUtils.getProperty(src, tagPd.getName()) == null) // src的属性值为空时不用拷
					continue;
			} catch (Exception e) {
				continue;
			}
			// 拷属性
			try {
				Object sourceV = PropertyUtils
						.getProperty(src, tagPd.getName());
				String name = sourceV.getClass().getName();
				String targetV = sourceV + "";
				PropertyUtils.setProperty(tag, tagPd.getName(), targetV);
			} catch (Exception e) {
				throw new RuntimeException("拷贝属性" + tagPd.getName() + "不成功！", e);
			}
		}
	}

	/** 把可序列化对象深复制把要复制的对象所引用的对象都复制了一遍(关键：执行序列化和反序列化来进行深度拷贝) */

	public static <T extends Serializable> List<T> deepCopy(List<T> src) {
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		ObjectOutputStream out;
		List<T> dest = null;
		try {
			out = new ObjectOutputStream(byteOut);
			out.writeObject(src);
			ByteArrayInputStream byteIn = new ByteArrayInputStream(
					byteOut.toByteArray());
			ObjectInputStream in = new ObjectInputStream(byteIn);
			@SuppressWarnings("unchecked")
			List<T> destTag = (List<T>) in.readObject();
			dest = destTag;
		} catch (IOException e) {
			throw new RuntimeException("拷贝属性不成功！", e);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("拷贝属性不成功！", e);
		}
		return dest;
	}

	/**
	 * 提供类型转换功能的拷贝，即发现两个JavaBean的同名属性为不同类型时，在支持的数据类型范围内进行转换
	 */
	public static void copyProperties(Object dest, Object orig) {
		if (orig == null) {
			return;
		}
		try {
			PropertyUtils.copyProperties(dest, orig);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("拷贝属性不成功！", e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException("拷贝属性不成功！", e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException("拷贝属性不成功！", e);
		}
	}

	public static void copyStrToBeanPro(Object tag, Object src, String[] noprops) {
	    noprops = noprops == null ? new String[] { "class" } : noprops;
		PropertyDescriptor[] tagPds = PropertyUtils.getPropertyDescriptors(tag);
		PropertyDescriptor[] srcPds = PropertyUtils.getPropertyDescriptors(src);
		String[] srcPdsName = new String[srcPds.length];
		for (int i = 0; i < srcPds.length; i++) {
			srcPdsName[i] = srcPds[i].getName();
		}
		// 迭代目标对象
		for (PropertyDescriptor tagPd : tagPds) {
		    if (ArrayUtils.indexOf(noprops, tagPd.getName()) >= 0) {
                continue;
            }
			try {
				if (tagPd.getWriteMethod() == null) {// 无setter方法的属性无需拷
					continue;
				} else if (PropertyUtils.getProperty(src, tagPd.getName()) == null) {// src的属性值为空时不用拷
					continue;
				}
			} catch (Exception e) {
				continue;
			}
			// 拷属性
			try {
				String proType = tagPd.getPropertyType().getName();
				String sourceV = PropertyUtils
						.getProperty(src, tagPd.getName()) + "";
				Object targetV = null;
				if(StringUtils.isEmpty(sourceV)){
				   continue;
				}
				if ("java.util.Date".equals(proType)) {
					// sourceV = DateUtil.formatDate(sourceV);
				} else if ("java.lang.String".equals(proType)) {
					targetV = sourceV;
				} else if ("java.lang.Short".equals(proType)) {
					targetV = Short.parseShort(sourceV);
				} else if ("java.lang.Integer".equals(proType)) {
					targetV = Integer.parseInt(sourceV);
				} else if ("java.lang.Float".equals(proType)) {
					targetV = Float.parseFloat(sourceV);
				} else if ("java.lang.Double".equals(proType)) {
					targetV = Double.parseDouble(sourceV);
				} else if ("java.lang.Boolean".equals(proType)) {
					targetV = Boolean.parseBoolean(sourceV);
				} else if ("java.lang.Long".equals(proType)) {
					targetV = Long.parseLong(sourceV);
				} else {// byte[]
					targetV = sourceV.getBytes("UTF-8");
				}
				PropertyUtils.setProperty(tag, tagPd.getName(), targetV);
			} catch (Exception e) {
				throw new RuntimeException("拷贝属性" + tagPd.getName() + "不成功！", e);
			}
		}
	}

}
