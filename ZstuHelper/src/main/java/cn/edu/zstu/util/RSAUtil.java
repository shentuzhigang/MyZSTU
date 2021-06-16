package cn.edu.zstu.util;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;


public class RSAUtil {
	private static Map<Integer, String> keyMap = new HashMap<Integer, String>();  //���ڷ�װ��������Ĺ�Կ��˽Կ
	/** 
	 * ���������Կ�� 
	 * @throws NoSuchAlgorithmException 
	 */  
	public static void genKeyPair() throws NoSuchAlgorithmException {  
		// KeyPairGenerator���������ɹ�Կ��˽Կ�ԣ�����RSA�㷨���ɶ���  
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");  
		// ��ʼ����Կ������������Կ��СΪ96-1024λ  
		keyPairGen.initialize(1024,new SecureRandom());  
		// ����һ����Կ�ԣ�������keyPair��  
		KeyPair keyPair = keyPairGen.generateKeyPair();  
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();   // �õ�˽Կ  
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  // �õ���Կ  
		String publicKeyString = new String(Base64.getEncoder().encode(publicKey.getEncoded()));  
		// �õ�˽Կ�ַ���  
		String privateKeyString = new String(Base64.getEncoder().encode((privateKey.getEncoded())));  
		// ����Կ��˽Կ���浽Map
		keyMap.put(0,publicKeyString);  //0��ʾ��Կ
		keyMap.put(1,privateKeyString);  //1��ʾ˽Կ
	}  
	/** 
	 * RSA��Կ���� 
	 *  
	 * @param str 
	 *            �����ַ���
	 * @param publicKey 
	 *            ��Կ 
	 * @return ���� 
	 * @throws Exception 
	 *             ���ܹ����е��쳣��Ϣ 
	 */  
	public static String encryptByX509EncodedKeySpec( String str, String publicKey ) throws Exception{
		//base64����Ĺ�Կ
		byte[] decoded = Base64.getDecoder().decode(publicKey.getBytes("UTF-8"));
		RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
		//RSA����
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, pubKey);
		String outStr = Base64.getEncoder().encodeToString(cipher.doFinal(str.getBytes("UTF-8")));
		
		return outStr;
	} 
	
	/** 
	 * RSA��Կ���� 
	 *  
	 * @param str 
	 *            �����ַ���
	 * @param modulus 
	 *            ģ�� 
	 * @param publicExponent
	 *            ����ָ��        
	 * @return ���� 
	 * @throws Exception 
	 *             ���ܹ����е��쳣��Ϣ 
	 */  
	public static String encryptByRSAPublicKeySpec( String str, String modulus, String publicExponent ) throws Exception{
		//base64����Ĺ�Կ
		RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA")
					.generatePublic(new RSAPublicKeySpec(
							new BigInteger(1,Base64.getDecoder().decode(modulus.getBytes("UTF-8"))),
							new BigInteger(1,Base64.getDecoder().decode(publicExponent.getBytes("UTF-8")))
					));
		//RSA����
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, pubKey);
		String outStr = Base64.getEncoder().encodeToString(cipher.doFinal(str.getBytes("UTF-8")));
		
		return outStr;
	} 
	
	/** 
	 * RSA˽Կ����
	 *  
	 * @param str 
	 *            �����ַ���
	 * @param privateKey 
	 *            ˽Կ 
	 * @return ����
	 * @throws Exception 
	 *             ���ܹ����е��쳣��Ϣ 
	 */  
	public static String decryptByPKCS8EncodedKeySpec(String str, String privateKey) throws Exception{
		//64λ������ܺ���ַ���
		byte[] inputByte = Base64.getDecoder().decode(str.getBytes("UTF-8"));
		//base64�����˽Կ
		byte[] decoded = Base64.getDecoder().decode(privateKey.getBytes("UTF-8"));  
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));  
		//RSA����
        
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, priKey);
		String outStr = new String(cipher.doFinal(inputByte));
		return outStr;
	}
}
