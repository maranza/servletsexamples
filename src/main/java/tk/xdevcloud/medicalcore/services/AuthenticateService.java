package tk.xdevcloud.medicalcore.services;

import javax.persistence.EntityManager;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.google.gson.JsonObject;

import tk.xdevcloud.medicalcore.models.User;
import tk.xdevcloud.medicalcore.utils.SecurityUtil;

public class AuthenticateService extends DBService {

	public AuthenticateService(EntityManager entityManager) {

		super(entityManager);
	}

	/**
	 * verify if username and password is valid
	 * 
	 * @param username
	 * @param rawPassword
	 * @return True if password valid
	 */
	public boolean verify(String username, String rawPassword) {

		try {
			User user = entityManager.createQuery("SELECT u FROM User u WHERE username = :username", User.class)
					.setParameter("username", username).getSingleResult();
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			return encoder.matches(rawPassword, user.getPassword());

		} catch (Throwable e) {

			return false;

		}
	}

}
