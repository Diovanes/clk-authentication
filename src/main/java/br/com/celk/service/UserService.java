package br.com.celk.service;

import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.stream.IntStream;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.wildfly.security.password.PasswordFactory;
import org.wildfly.security.password.WildFlyElytronPasswordProvider;
import org.wildfly.security.password.interfaces.BCryptPassword;
import org.wildfly.security.password.spec.EncryptablePasswordSpec;
import org.wildfly.security.password.spec.IteratedSaltedPasswordAlgorithmSpec;

import br.com.celk.domain.User;

@ApplicationScoped
public class UserService {
	
	public final static Provider ELYTRON_PROVIDER = new WildFlyElytronPasswordProvider();
	public final static int ITERATION_COUNT = 10;
	
	@Inject
	EntityManager entityManager;
	
	@Transactional
	public void createUsers(Integer qtd) {
		
		//Remove all users
		this.entityManager.createNativeQuery("delete from clk_user where login != 'admin' ").executeUpdate();
		
		IntStream.range(0, qtd)
		  .forEach(idx ->
		  	this.saveUser(new User("Usuario " + idx, "user" + idx, "user" + idx, "ROLE_USER"))
		  );

	}
	
	@Transactional
	public void createUserAdmin() {
		//Remove user admin
		this.entityManager.createNativeQuery("delete from clk_user where login = 'admin' ").executeUpdate();

		this.saveUser(new User("Admin", "admin", "admin", "ROLE_ADMIN"));
		
	}
	
	/**
	 * Create new user
	 * 
	 * @param name
	 * @param login
	 * @param pass
	 */
	private void saveUser(User user) {
		
		this.entityManager.persist(this.updatePass(user));
	}
	
	/**
	 * Create BCryp password
	 * 
	 * @param user
	 * @return
	 */
	private User updatePass(User user) {
		
    	PasswordFactory passwordFactory = null;
        try {
            passwordFactory = PasswordFactory.getInstance(BCryptPassword.ALGORITHM_BCRYPT, ELYTRON_PROVIDER);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        byte[] salt = new byte[BCryptPassword.BCRYPT_SALT_SIZE];
        SecureRandom random = new SecureRandom();
        random.nextBytes(salt);

        IteratedSaltedPasswordAlgorithmSpec iteratedAlgorithmSpec = new IteratedSaltedPasswordAlgorithmSpec(ITERATION_COUNT, salt);
        EncryptablePasswordSpec encryptableSpec = new EncryptablePasswordSpec(user.getPass().toCharArray(), iteratedAlgorithmSpec);

        BCryptPassword original = null;
        try {
            original = (BCryptPassword) passwordFactory.generatePassword(encryptableSpec);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

        byte[] hash = original.getHash();

        Base64.Encoder encoder = Base64.getEncoder();
        user.setPass_hash(encoder.encodeToString(hash));
        user.setSalt(encoder.encodeToString(salt));
        user.setIterations(ITERATION_COUNT);
        
        return user;
	}

}


