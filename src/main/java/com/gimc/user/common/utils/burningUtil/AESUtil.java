package com.gimc.user.common.utils.burningUtil;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.gimc.user.common.utils.Md5Util;
import com.gimc.user.modules.b_constant.CommonParam;
 
public class AESUtil {
 
	/**
	 * 密钥算法
	 */
	private static final String ALGORITHM = "AES";
	/**
	 * 加解密算法/工作模式/填充方式
	 */
	private static final String ALGORITHM_MODE_PADDING = "AES/ECB/PKCS5Padding";
	/**
	 * 生成key
	 */
	private static SecretKeySpec key = new SecretKeySpec(Md5Util.encodeByMd5(CommonParam.WX_SECRET).toLowerCase().getBytes(), ALGORITHM);
 
	/**
	 * AES加密
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static String encryptData(String data) throws Exception {
		// 创建密码器
		Cipher cipher = Cipher.getInstance(ALGORITHM_MODE_PADDING);
		// 初始化
		cipher.init(Cipher.ENCRYPT_MODE, key);
		return Base64Util.encode(cipher.doFinal(data.getBytes()));
	}
 
	/**
	 * AES解密
	 * 
	 * @param base64Data
	 * @return
	 * @throws Exception
	 */
	public static String decryptData(String base64Data) throws Exception {
		Cipher cipher = Cipher.getInstance(ALGORITHM_MODE_PADDING);
		cipher.init(Cipher.DECRYPT_MODE, key);
		return new String(cipher.doFinal(Base64Util.decode(base64Data)));
	}
 
	public static void main(String[] args) throws Exception {
		String A = "y3V34Dt1+KQYvGfjtcdTBk4h4ZusiNQ4ULBvaKk7uBbn9DPQSFJYSH/vSB35as5kRS8lOmNfzEwScsWmUfYjqsAVpaWcChiIwoysiSO5rT/LxwJ75nzJuUjjaoENMz9ZkFDiqbaXpxGmUxsEHEb62qMlbxWN0/8sS5nDyA2uIXerxzFC9sJh0pkqI0afgg7QLXaj4hxggW0aiJy1+9H0VDNV9AEoyShdIuYJzLXgNWSGZZzP634+Z4jMonZahDGSGOBsgilFkVltKXoXBqExk+zw6AnY8eyMgqVc5TmtLELJF+SPhyQJ+o268crS/cKfjRdRCBlcvaKDM+hCSX44GzWooukOaiHAxEzKnzs6Gpwse/QdnhR7nKT8oEUT17u+PE4sgTojrWyr2PYP1zxtedNh0SfMi/m/qvtWo3YrvQ20pL5asdNB439/N6NT4FxFz5dj9GA1ynUVJbyo5KdRU4oclTzaCNidOYFOmRUOyRFdWgxqU87etHzoFHmYfS3RKY+lcQYVxVZXFrJtdPpqI+RikQCeNTzQK9xukCPH/wL1NGgwix7jxOGjekOnyxGtwmnf2g8AYELM6hWa9oQ1XPfkO1nIT8aK7wi9X1I0OFFdoXvc0ipBM4qqRXDsdfjoA7kqiMAmrrIC7orUUT7frTy1IS5tlkYqhsLkUmw2TWTESSCO0ShyQDBHLVTHRFEuYspw3rngU9I8/SFQMNcPJLCKnJhrMTlzpXf5maG7tQNVkEYHZv0pIrfaBH8csIFl7PZbUl89Gp9UOSuYVFD+EtGEYd9Sv9GN8cP9V4xoKUCANAw67J1/edtq5M7SJytZYAtNYgiqK//FQnxrqA6WtGxVF5m/E6vIrISXk0bWMSO9HkUmoTVY3HcMKLD/rRLYNmQc9I5Xj6nCX5zROl5oNrx1N3ZY29xwd5kG7IdijrLsfejOPM3ZmPUpGVS1p/x4HdhH+WgIXrv5SLZSiRX+Bq6vCyRkUbU/Pimlzg1o6EW1SvfIYK2uOB1uqjPkAgJE0DcgGKAsUzmhoxNdWT7zgXXrt3CNPzMGwwBz6Oomxgix7U304PmJptedYI+dRUVX";
		String B = AESUtil.decryptData(A);
		System.out.println(B);
	    
	}
 
}