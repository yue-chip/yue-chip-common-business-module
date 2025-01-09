package com.yue.chip.upms.util;

import com.ccsp.util.Bytes;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * @author jiacheng.liao on 2025/01/06
 */
public class CCSPUtil {

    /**
     * SM4加密
     * @param input
     * @return
     */
    public static String SM4encrypt(String input) {
        String encryptStr = "";
        if (StringUtils.hasText(input)) {
            try {
                byte[] encrypt = CCSPApi.SM4encrypt(input);
                if (Objects.nonNull(encrypt)) {
                    encryptStr = Bytes.bytesToHexString(encrypt);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return encryptStr;
    }

    /**
     * SM4解密
     * @param input
     * @return
     */
    public static String SM4decrypt(String input) {
        String decryptStr = "";
        if (StringUtils.hasText(input)) {
            try {
                byte[] bytes = Bytes.hexStringToBytes(input);
                byte[] decrypt = CCSPApi.SM4decrypt(bytes);
                if (Objects.nonNull(decrypt)) {
                    decryptStr = new String(decrypt);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return decryptStr;
    }

    /**
     * HMac加密
     * @param input
     * @return
     */
    public static String getHMac(String input) {
        String hmacStr = "";
        if (StringUtils.hasText(input)) {
            try {
                byte[] hmac = CCSPApi.hMac(input);
                if (Objects.nonNull(hmac)) {
                    hmacStr = Bytes.bytesToHexString(hmac);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return hmacStr;
    }

    /**
     * HMac验证
     * @param input
     * @param hmacStr
     * @return
     */
    public static Boolean checkoutHMac(String input, String hmacStr) {
        boolean result = false;
        if (!StringUtils.hasText(input) && !StringUtils.hasText(hmacStr)) {
            return true;
        }
        if (StringUtils.hasText(input) && StringUtils.hasText(hmacStr)) {
            String hMac = getHMac(input);
            if (hMac.equals(hmacStr)) {
                result = true;
            }
        }
        return result;
    }
}
