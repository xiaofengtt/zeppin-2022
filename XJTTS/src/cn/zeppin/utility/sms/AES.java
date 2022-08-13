package cn.zeppin.utility.sms;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class AES {

    private static final String KEY_ALGORITHM = "AES";
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";//Ĭ�ϵļ����㷨
    
	private final static String templateId = "1419581928322953217";//ģ��ID
	private final static String signId = "1419581851869179906";//ǩ��ID
	private final static String userName = "otherapi";//�û���
	private final static String sendType = "san";//���͹���
	private final static String apikey = "smsj59dkgksmssed";//��Կ
	private final static String privatekey = "d24cqB7SMSmn9D5c";//˽Կ
	


    /**
     * AES ���ܲ���
     *
     * @param content ����������
     * @param key     ��������
     * @return ����Base64ת���ļ�������
     */
    public static String encrypt(String content, String key) {
        try {

            SecretKey secretKey = new SecretKeySpec(key.getBytes(), KEY_ALGORITHM);
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);// ����������
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);// ��ʼ��Ϊ����ģʽ��������
            byte[] result = cipher.doFinal(byteContent);// ����
            return new BASE64Encoder().encode(result);//ͨ��Base64ת�뷵��
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    /**
     * AES ���ܲ���
     *
     * @param content
     * @param key
     * @return
     */
    public static String decrypt(String content, String key) {

        try {
            //ʵ����
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);

            SecretKey secretKey = new SecretKeySpec(key.getBytes(), KEY_ALGORITHM);
            //ʹ����Կ��ʼ��������Ϊ����ģʽ
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            //ִ�в���
            byte[] result = cipher.doFinal(new BASE64Decoder().decodeBuffer(content));

            return new String(result, "utf-8");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }


    public static void main(String[] args) throws Exception {

        long a = System.currentTimeMillis();


        String plainText = "smsj59dkgksmssed"+a; //����
        String key = "d24cqB7SMSmn9D5c";//΢����

        System.out.println("plainText:" + plainText);


        String encryptionText = AES.encrypt(plainText, key);
        System.out.println("���ܺ������(encryptionText):" + encryptionText);

        System.out.println("����(decrypt):" + AES.decrypt(encryptionText, key));

    }
}