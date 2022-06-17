package practice_4_crypto_alg;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Random;

public class Main {

	// ������� ���������� ��������
	public static String VigenerCipher (String message, String key) {
		// ������ ������ ������� ��������
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ 0123456789";		
		// ���������� ������ ����� ������ � ���������
		int alphabetLength = alphabet.length();
		// ���������� ������ �������������� ������������� ���������
		String ciphertext = "";
		// ������ ������� � ��������
		int messageIndex = 0;
		// ����� ��������� ����� 
		int keyLength = key.length();
		// ���������� ������ ������ ��������������� �������������� �������
		int tempIndex = 0;
		// ������ ������ ������� �������� �����, ��������������� �������� ��������
		int keyShifts [] = new int [keyLength];
		
		// ����, ������� ���������� � ������ ������� �������� ��������, ��������������� �������� ��������� �����
		for (int i = 0; i < key.length(); i++) {
			keyShifts[i] = alphabet.toLowerCase().indexOf(key.toLowerCase().charAt(i));
		}
		
		// ����, ������� ������� ������� ��������� ���������
		for (int j = 0; j < message.length(); j++) {
			// ���������� ������ ������ ������� ������� ��������, ������� ������������� ������� ��������� ��������� 
			messageIndex = alphabet.toLowerCase().indexOf(message.toLowerCase().charAt(j));
			// ���������� ������ ������ ������� ��������������� �������������� �������
			tempIndex = (messageIndex + keyShifts[j % keyLength]) % alphabetLength;
			// ���������� ������ ������������ ������������� ���������
			ciphertext = ciphertext + alphabet.toLowerCase().charAt(tempIndex);
		}			
		return ciphertext;
	}
	
	// ������� ������������ ���������, ����������� � ���������� ����������� ��������
	public static String VigenerCipherDecode (String ciphertext, String key) {
		// ������ ������ ������� ��������
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ 0123456789";		
		// ���������� ������ ����� ������ � ���������
		int alphabetLength = alphabet.length();
		// ���������� ������ �������������� �������������� ���������
		String message = "";
		// ���������� ������ ������ ������� ��������� �����������
		int cipherIndex = 0;
		// ���������� ����� ��������� �����
		int keyLength = key.length();
		// ���������� ������ ������ ��������������� ��������������� �������
		int tempIndex = 0;
		// ������ ������ ������� �������� �����, ��������������� �������� ��������
		int keyShifts [] = new int [keyLength];
		
		// ����, ������� ���� ������� ��������, ������� ������������� �������� ��������� �����������
		for (int i = 0; i < key.length(); i++) {
			keyShifts[i] = alphabet.toLowerCase().indexOf(key.toLowerCase().charAt(i));
		}
		// ���� ������� �������������� �������� ����������
		for (int j = 0; j < ciphertext.length(); j++) {
			// ���������� ������ ������ ������� ������� ��������, ������� ������������� ������� ��������� ����������� 
			cipherIndex = alphabet.toLowerCase().indexOf(ciphertext.toLowerCase().charAt(j));
			// ���������� ������ ������ ��������������� �������
			tempIndex = (cipherIndex - keyShifts[j % keyLength]  + alphabetLength) % alphabetLength;
			// ���������� ������ �������������� �������������� ���������
			message = message + alphabet.toLowerCase().charAt(tempIndex);
		}	
		
		return message;
	
	}
	
	// ������� ��������� �������� �� ����� ��������������
	public static boolean isCoprimeNumber(int a, int b) {
		
		// ���� ���� ����� �������� ����� � � b 
		for (int i = 2; i <= Math.min(a, b); i++) {
			// ���� ����� ����� ����� ��������, ������� ���������� ����
			if ((a % i == 0) && (b % i == 0)) {
				return false;
			}
		}
		// ������� ���������� ������
		return true;	
	}
	
	// ������� ��������� �������� ���������� RSA
	public static BigDecimal [] RSAChiper(String message, int e, int n) {
		
		// ������ ������ ����� �������� ��������� ���������
		BigDecimal [] a  = new BigDecimal [message.length()];
		// ������ ������ ����� ������� �������
		byte [] bytes = message.getBytes();
		
		// �������������� ��������� ��������� � ������ ������
		for (int i = 0; i < a.length; i++) {
			if (bytes[i] < 0) {
				 a[i] = new BigDecimal(Integer.toString(bytes[i] ^ -1<<8));
			} else {
				 a[i] = new BigDecimal(Integer.toString(bytes[i]));
			}
		 }
		
		 // ����������� ��������� ���������
		BigDecimal [] b  = new BigDecimal [a.length];
		 BigDecimal temp = new BigDecimal(0);
		 for (int j = 0; j < b.length; j++) {
			 temp = a[j].pow(e);
			 b[j] = temp.remainder(new BigDecimal(n));
		 }
		 
		 return b;		
	} 
	
	// ������� ������������ �������������� ��������� ���������� RSA
	public static BigDecimal [] RSADecodeChiper(BigDecimal [] RSAChiperArray, int d, int n) {
		
		 BigDecimal [] a1 = new BigDecimal [RSAChiperArray.length];
		 BigDecimal temp1 = new BigDecimal(0);
		 for (int t = 0; t < a1.length; t++) {
			 temp1 = RSAChiperArray[t].pow(d);
			 a1[t] = temp1.remainder(new BigDecimal(n));
		 }	
		return a1;		
	} 
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
		
		System.out.println("ciphertext: " + VigenerCipher ("mess", "world"));
		System.out.println("message: " + VigenerCipherDecode ("7s82", "world"));
		String message = "CRYPTO";
		// ��������� ������� ����� p � q
		int p = new BigInteger(8, 1000, new Random()).intValue();
		int q = new BigInteger(8, 1000, new Random()).intValue();
		// ���������� ����� n
		int n = p * q; 
		// ���������� �������� ������� ������
		int eulerValue = (p - 1) * (q - 1);
		Random rnd = new Random();
		// ���������� ��������� �����
		int e;
		
		do {
			e = rnd.nextInt(eulerValue);
			
		}while(!((e > 1) && (isCoprimeNumber(e, eulerValue))));
		
		// ���������� ��������� �����
		int k = 0;
		do {
			k++;
		}while(((eulerValue * k + 1) % e != 0) || ((eulerValue * k + 1) / e) == e);
		
		int d = (eulerValue * k + 1) / e;
		System.out.println("p = " + p + " q = " + q + " n = " + n + " eulerValue = " + eulerValue + " e = " + e + " d = " + d + " k = " + k);
		
		BigDecimal [] RSAEncodeArray = RSAChiper(message, e, n);
		
		// ������������� ���������
		System.out.println("RSAEncode: ");
		for (int j = 0; j < RSAEncodeArray.length; j++) {
			System.out.print(String.format("%8s",RSAEncodeArray[j]));
		}
		
		BigDecimal [] RSADecodeArray = RSADecodeChiper(RSAEncodeArray, d, n);
		
		// �������������� ���������
		System.out.println();
		System.out.println("RSADecode: ");
		for (int j = 0; j < RSADecodeArray.length; j++) {
			System.out.print(String.format("%8s",RSADecodeArray[j]));
		}
		
		
	}

}
