package practice_4_crypto_alg;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Random;

public class Main {

	// функция шифрования Вижинера
	public static String VigenerCipher (String message, String key) {
		// строка хранит символы алфавита
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ 0123456789";		
		// переменная хранит длину строки с алфавитом
		int alphabetLength = alphabet.length();
		// переменная хранит результирующее зашифрованное сообщение
		String ciphertext = "";
		// индекс символа в алфавите
		int messageIndex = 0;
		// длина исходного ключа 
		int keyLength = key.length();
		// переменная хранит индекс результирующего зашифрованного символа
		int tempIndex = 0;
		// массив хранит индексы символов ключа, соответствующих символам алфавита
		int keyShifts [] = new int [keyLength];
		
		// цикл, который записывает в массив индексы символов алфавита, соответствующих символам исходного ключа
		for (int i = 0; i < key.length(); i++) {
			keyShifts[i] = alphabet.toLowerCase().indexOf(key.toLowerCase().charAt(i));
		}
		
		// цикл, который шифрует символы исходного сообщения
		for (int j = 0; j < message.length(); j++) {
			// переменная хранит индекс каждого символа алфавита, который соответствует символу исходного сообщения 
			messageIndex = alphabet.toLowerCase().indexOf(message.toLowerCase().charAt(j));
			// переменная хранит индекс каждого результирующего зашифрованного символа
			tempIndex = (messageIndex + keyShifts[j % keyLength]) % alphabetLength;
			// переменная хранит результующее зашифрованное сообщение
			ciphertext = ciphertext + alphabet.toLowerCase().charAt(tempIndex);
		}			
		return ciphertext;
	}
	
	// функция дешифрования сообщения, полученного в результате шифрованием Вижинера
	public static String VigenerCipherDecode (String ciphertext, String key) {
		// строка хранит символы алфавита
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ 0123456789";		
		// переменная хранит длину строки с алфавитом
		int alphabetLength = alphabet.length();
		// переменная хранит результирующее расшифрованное сообщение
		String message = "";
		// переменная хранит индекс символа исходного шифротекста
		int cipherIndex = 0;
		// переменная длину исходного ключа
		int keyLength = key.length();
		// переменная хранит индекс результирующего расшифрованного символа
		int tempIndex = 0;
		// массив хранит индексы символов ключа, соответствующих символам алфавита
		int keyShifts [] = new int [keyLength];
		
		// цикл, который ищет символы алфавита, которые соответствуют символам исходного шифротекста
		for (int i = 0; i < key.length(); i++) {
			keyShifts[i] = alphabet.toLowerCase().indexOf(key.toLowerCase().charAt(i));
		}
		// цикл который расшифровывает исходный шифротекст
		for (int j = 0; j < ciphertext.length(); j++) {
			// переменная хранит индекс каждого символа алфавита, который соответствует символу исходного шифротекста 
			cipherIndex = alphabet.toLowerCase().indexOf(ciphertext.toLowerCase().charAt(j));
			// переменная хранит индекс расшифрованного символа
			tempIndex = (cipherIndex - keyShifts[j % keyLength]  + alphabetLength) % alphabetLength;
			// переменная хранит результирующее расшифрованное сообщение
			message = message + alphabet.toLowerCase().charAt(tempIndex);
		}	
		
		return message;
	
	}
	
	// функция проверяет являются ли числа взаимопростыми
	public static boolean isCoprimeNumber(int a, int b) {
		
		// цикл ищет общие делители чисел а и b 
		for (int i = 2; i <= Math.min(a, b); i++) {
			// если числа имеют общие делители, функция возвращает ложь
			if ((a % i == 0) && (b % i == 0)) {
				return false;
			}
		}
		// функция возвращает истину
		return true;	
	}
	
	// функция реализует алгоритм шифрования RSA
	public static BigDecimal [] RSAChiper(String message, int e, int n) {
		
		// массив хранит байты символов исходного сообщения
		BigDecimal [] a  = new BigDecimal [message.length()];
		// массив хранит байты каждого символа
		byte [] bytes = message.getBytes();
		
		// преобразование исходного сообщения в массив байтов
		for (int i = 0; i < a.length; i++) {
			if (bytes[i] < 0) {
				 a[i] = new BigDecimal(Integer.toString(bytes[i] ^ -1<<8));
			} else {
				 a[i] = new BigDecimal(Integer.toString(bytes[i]));
			}
		 }
		
		 // шифорование исходного сообщения
		BigDecimal [] b  = new BigDecimal [a.length];
		 BigDecimal temp = new BigDecimal(0);
		 for (int j = 0; j < b.length; j++) {
			 temp = a[j].pow(e);
			 b[j] = temp.remainder(new BigDecimal(n));
		 }
		 
		 return b;		
	} 
	
	// функция дешифрования зашифрованного сообщения алгоритмом RSA
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
		// случайные простые числа p и q
		int p = new BigInteger(8, 1000, new Random()).intValue();
		int q = new BigInteger(8, 1000, new Random()).intValue();
		// нахождение числа n
		int n = p * q; 
		// нахождение значения функции Эйлера
		int eulerValue = (p - 1) * (q - 1);
		Random rnd = new Random();
		// нахождение открытого ключа
		int e;
		
		do {
			e = rnd.nextInt(eulerValue);
			
		}while(!((e > 1) && (isCoprimeNumber(e, eulerValue))));
		
		// нахождение закрытого ключа
		int k = 0;
		do {
			k++;
		}while(((eulerValue * k + 1) % e != 0) || ((eulerValue * k + 1) / e) == e);
		
		int d = (eulerValue * k + 1) / e;
		System.out.println("p = " + p + " q = " + q + " n = " + n + " eulerValue = " + eulerValue + " e = " + e + " d = " + d + " k = " + k);
		
		BigDecimal [] RSAEncodeArray = RSAChiper(message, e, n);
		
		// зашифрованное сообщение
		System.out.println("RSAEncode: ");
		for (int j = 0; j < RSAEncodeArray.length; j++) {
			System.out.print(String.format("%8s",RSAEncodeArray[j]));
		}
		
		BigDecimal [] RSADecodeArray = RSADecodeChiper(RSAEncodeArray, d, n);
		
		// расшифрованное сообщение
		System.out.println();
		System.out.println("RSADecode: ");
		for (int j = 0; j < RSADecodeArray.length; j++) {
			System.out.print(String.format("%8s",RSADecodeArray[j]));
		}
		
		
	}

}
