package tk.xdevcloud.medicalcore.utils;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Date;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

public class SecurityUtil {

	public static String aesEncrypt(String data) {

		Cipher cipher;
		String encryptedString = null;

		try {
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			// generate secret key
			KeyGenerator keyGen = KeyGenerator.getInstance("AES");
			keyGen.init(1024);
			// set secure random
			SecureRandom random = new SecureRandom();
			byte randomBytes[] = new byte[20];
			random.nextBytes(randomBytes);
			SecretKey secretKey = keyGen.generateKey();
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, random);
			cipher.update(data.getBytes());
			encryptedString = new String(cipher.doFinal());

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();

		} catch (NoSuchPaddingException e) {
			e.printStackTrace();

		} catch (InvalidKeyException e) {

			e.printStackTrace();

		} catch (IllegalBlockSizeException e) {

			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return encryptedString;
	}

	public static boolean validateToken(String token) {
		Long time = System.currentTimeMillis();
		try {
			Algorithm algorithm = Algorithm.HMAC256("secret");
			JWTVerifier verify = JWT.require(algorithm).withIssuer("xdevcloud.tk").build();
			DecodedJWT jwt = verify.verify(token);

			if (jwt.getExpiresAt().before(new Date(time))) {
				return false;
			}

			return true;
		} catch (JWTVerificationException e) {
			e.printStackTrace();
			System.out.println(e);
			return false;
		}
	}

	public static String generateToken(String username) {

		try {
			Algorithm algorithm = Algorithm.HMAC256("secret");
			Long time = System.currentTimeMillis();

			String jwtToken = JWT.create().withIssuer("xdevcloud.tk").withIssuedAt(new Date(time))
					.withExpiresAt(new Date(time + 5000)).withClaim("username", username).sign(algorithm);
			return jwtToken;
		} catch (JWTCreationException e) {
			e.printStackTrace();
			System.out.println(e);
			return null;
		}
	}

}
