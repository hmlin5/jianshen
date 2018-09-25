package wxpay;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import com.gimc.user.modules.b_constant.CommonParam;

public class MyConfig extends WXPayConfig {

	 private byte[] certData;
	
	public MyConfig() throws Exception {
        String certPath = CommonParam.WX_CERT_PATH;
        File file = new File(certPath);
        InputStream certStream = new FileInputStream(file);
        this.certData = new byte[(int) file.length()];
        certStream.read(this.certData);
        certStream.close();
    }
	
	
	@Override
	String getAppID() {
		return CommonParam.WX_APPID;
	}

	@Override
	String getMchID() {
		return CommonParam.WX_MCH_ID;
	}

	@Override
	String getKey() {
		return CommonParam.WX_SECRET;
	}

	@Override
	InputStream getCertStream() {
		ByteArrayInputStream certBis = new ByteArrayInputStream(this.certData);
        return certBis;
	}

	@Override
	IWXPayDomain getWXPayDomain() {
		return null;
	}

	
	public int getHttpConnectTimeoutMs() {
        return 10000;
    }

    public int getHttpReadTimeoutMs() {
        return 10000;
    }
	
    
	
}
