import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import registrationAndLogin.Encryption;

public class EncryptionTest {

	@Before
	public void setUp() throws Exception {
		new Encryption();
	}

	@Test
	public void testEncryptionAndDecryption() throws Exception {
		String test = "hello";
		byte[] testEncrypted = Encryption.encrypt(test);
		assertFalse(test.contains(testEncrypted.toString()));
		String decrypted = Encryption.decrypt(testEncrypted);
		assertTrue(test.contains(decrypted));
	}

}
