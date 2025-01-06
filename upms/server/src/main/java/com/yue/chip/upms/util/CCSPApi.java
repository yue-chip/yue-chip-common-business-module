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
     * 杂凑运算 签名验签
     * @param data
     * @return
     * @throws CryptoException
     */
    public static byte[] SM2encrypt(String data) throws CryptoException {
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
            inData = data.getBytes("UTF-8");
        } catch (Exception e){
            e.printStackTrace();
        }

        // 杂凑运算
        Object hashContext = crypto.CCSP_HashInit(GlobalData.SGD_SHA256, null, null);
        System.out.println("HashInit OK");
        crypto.CCSP_HashUpdate(hashContext, inData);
        System.out.println("HashUpdate OK");
        byte[] hashresult = crypto.CCSP_HashFinal(hashContext,null);
        System.out.println("HashFinal OK");
        if(hashresult == null) {
            System.out.println("哈希失败!");
        } else {
            System.out.println("哈希成功.");
            for(int i = 0; i<hashresult.length; i++)
            {
                System.out.printf("%02x", hashresult[i]);
            }
        }

        boolean result = false;
        SM2refSignature refSig = null;
        System.out.println("私钥签名->公钥验签");
        System.out.println("----------------------------------");
        System.out.println("签名数据: "+Bytes.bytes2hex(hashresult));
        refSig = crypto.CCSP_InternalSign_ECC(SM2KeyId, hashresult);
        System.out.println("签名结果: ");
        System.out.println(refSig);
        result = crypto.CCSP_InternalVerify_ECC(SM2KeyId, hashresult, refSig);
        System.out.println("验签结果: "+result);
        return null;
    }

    public static byte[] SM2decrypt(byte[] inputData) throws CryptoException {
        return null;
    }

}
