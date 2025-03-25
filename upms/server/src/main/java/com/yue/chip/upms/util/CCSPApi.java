package com.yue.chip.upms.util;

import com.ccsp.sdk.SDFFactory;
import com.ccsp.sdk.bean.GlobalData;
import com.ccsp.sdk.bean.SM2refSignature;
import com.ccsp.sdk.bean.SessionKeyContext;
import com.ccsp.sdk.crypto.CryptoException;
import com.ccsp.sdk.crypto.HsmClient;
import com.ccsp.sdk.crypto.impl.CCSPClient;
import com.ccsp.util.Bytes;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author jiacheng.liao on 2025/01/06
 */
public class CCSPApi {

    static volatile CCSPClient ccspClient;
    static HsmClient crypto = CCSPClient.getHsmClient();
    public static final String ip = "19.53.6.190";
    public static final String appName = "zhyj";
    public static final String password = "zhyj-123";
    public static final String SM4KeyId = "zhyjSM4_Random_DEK";
    public static final String SM2KeyId = "zhyjSM2_Standard";

//    public void client() {
//        CCSPClient ccspClient = null;
//        try {
//            ccspClient = SDFFactory.getInstance(ip, 20000, 10, 30, 20);
//            ccspClient.CCSP_LoginbyAppNameAndPwd(appName, password);
//        } catch (CryptoException e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * SM4对称加密
     * @param inputData
     * @return
     * @throws CryptoException
     * @throws IOException
     */
    public static byte[] SM4encrypt(String inputData) throws CryptoException, IOException {
        if (ccspClient == null) {
            try {
                ccspClient = SDFFactory.getInstance(ip, 20000, 10, 30, 20);
                ccspClient.CCSP_LoginbyAppNameAndPwd(appName, password);
            } catch (CryptoException e) {
                e.printStackTrace();
            }
        }
        SessionKeyContext skc = new SessionKeyContext();
        skc.setKeyID(SM4KeyId.getBytes());
        skc.setAlgId(GlobalData.SGD_SM4_ECB);
        byte[] inData = null;
        try {
            inData = inputData.getBytes("UTF-8");
        } catch (Exception e){
            e.printStackTrace();
        }

        byte[] encryptData = crypto.CCSP_EncryptWithPadding(skc, GlobalData.SGD_SM4_ECB, null, inData);
        return encryptData;
    }

    /**
     * SM4对称解密
     * @param inputData
     * @return
     * @throws CryptoException
     */
    public static byte[] SM4decrypt(byte[] inputData) throws CryptoException {
        if (ccspClient == null) {
            try {
                ccspClient = SDFFactory.getInstance(ip, 20000, 10, 30, 20);
                ccspClient.CCSP_LoginbyAppNameAndPwd(appName, password);
            } catch (CryptoException e) {
                e.printStackTrace();
            }
        }
        SessionKeyContext skc = new SessionKeyContext();
        skc.setKeyID(SM4KeyId.getBytes());
        skc.setAlgId(GlobalData.SGD_SM4_ECB);

        byte[] decryptData = crypto.CCSP_DecryptWithPadding(skc, GlobalData.SGD_SM4_ECB, null, inputData);

        return decryptData;
    }

    /**
     * hMac加密
     * @param inputData
     * @return
     * @throws CryptoException
     */
    public static byte[] hMac(String inputData) throws CryptoException {
        if (ccspClient == null) {
            try {
                ccspClient = SDFFactory.getInstance(ip, 20000, 10, 30, 20);
                ccspClient.CCSP_LoginbyAppNameAndPwd(appName, password);
            } catch (CryptoException e) {
                e.printStackTrace();
            }
        }
        SessionKeyContext skc = new SessionKeyContext();
        skc.setKeyID(SM4KeyId.getBytes());

        byte[] inData = null;
        try {
            inData = inputData.getBytes("UTF-8");
        } catch (Exception e){
            e.printStackTrace();
        }

        byte[] hMac = crypto.CCSP_HMAC(skc, GlobalData.SGD_SHA256, inData);
        return hMac;
    }

    /**
     * SM2签名
     * @param data
     * @return
     * @throws CryptoException
     * @throws NoSuchAlgorithmException
     */
    public static String SM2encrypt(String data) throws CryptoException, NoSuchAlgorithmException {
        if (ccspClient == null) {
            try {
                ccspClient = SDFFactory.getInstance(ip, 20000, 10, 30, 20);
                ccspClient.CCSP_LoginbyAppNameAndPwd(appName, password);
            } catch (CryptoException e) {
                e.printStackTrace();
            }
        }
        byte[] inData = null;
        try {
            inData = data.getBytes(StandardCharsets.UTF_8);
        } catch (Exception e){
            e.printStackTrace();
        }
        byte[] inputData = processInputTo32Bytes(inData);

        SM2refSignature refSig = crypto.CCSP_InternalSign_ECC(SM2KeyId, inputData);

        String RBase64 = extractValue(refSig.toString(), "R:").trim();
        String SBase64 = extractValue(refSig.toString(), "S:").trim();
        return RBase64 + "_RS_" + SBase64;
    }

    /**
     * SM2验签
     * @param data
     * @param signData
     * @return
     * @throws CryptoException
     * @throws NoSuchAlgorithmException
     */
    public static Boolean SM2decrypt(String data, String signData) throws CryptoException, NoSuchAlgorithmException {
        if (ccspClient == null) {
            try {
                ccspClient = SDFFactory.getInstance(ip, 20000, 10, 30, 20);
                ccspClient.CCSP_LoginbyAppNameAndPwd(appName, password);
            } catch (CryptoException e) {
                e.printStackTrace();
            }
        }
        byte[] inData = null;
        try {
            inData = data.getBytes(StandardCharsets.UTF_8);
        } catch (Exception e){
            e.printStackTrace();
        }
        byte[] inputData = processInputTo32Bytes(inData);

        boolean result = false;
        SM2refSignature refSig = null;

        String RBase64 = signData.split("_RS_")[0];
        String SBase64 = signData.split("_RS_")[1];

//        byte[] br = processInputTo64Bytes(Bytes.hex2bytes(RBase64));
//        byte[] bs = processInputTo64Bytes(Bytes.hex2bytes(SBase64));

        refSig = new SM2refSignature(processInputTo64Bytes(Bytes.hex2bytes(RBase64)), processInputTo64Bytes(Bytes.hex2bytes(SBase64)));
        result = crypto.CCSP_InternalVerify_ECC(SM2KeyId, inputData, refSig);
        return result;
    }

    private static byte[] processInputTo32Bytes(byte[] input) {
        byte[] processedInput = new byte[32];

        // 如果输入长度大于 32 字节，截断
        if (input.length > 32) {
            System.arraycopy(input, 0, processedInput, 0, 32);
        } else {
            // 如果输入长度小于或等于 32 字节，填充
            System.arraycopy(input, 0, processedInput, 0, input.length);
            // 填充剩余部分为 0
            for (int i = input.length; i < 32; i++) {
                processedInput[i] = 0;
            }
        }

        return processedInput;
    }

    private static byte[] processInputTo64Bytes(byte[] input) {
        byte[] processedInput = new byte[64];

        // 如果输入长度大于 64 字节，截断
        if (input.length > 64) {
            System.arraycopy(input, 0, processedInput, 0, 64);
        } else {
            // 如果输入长度小于或等于 64 字节，填充
            // 计算需要填充的零的数量
            int paddingLength = 64 - input.length;

            // 填充前面的零
            for (int i = 0; i < paddingLength; i++) {
                processedInput[i] = 0;
            }

            // 将输入数据复制到 processedInput 的后面
            System.arraycopy(input, 0, processedInput, paddingLength, input.length);
        }

        return processedInput;
    }

    private static String extractValue(String result, String key) {
        // 查找 key 的位置
        int startIndex = result.indexOf(key);
        if (startIndex == -1) {
            return null;
        }

        // 截取从 key 开始到行结束的部分
        int endIndex = result.indexOf("\n", startIndex);
        String valueLine = (endIndex == -1) ? result.substring(startIndex) : result.substring(startIndex, endIndex);

        // 去掉 key 和前后的空格
        return valueLine.replace(key, "").trim();
    }

}
