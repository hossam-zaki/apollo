import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import registrationandlogin.Encryption;

public class EncryptionTest {

  @Before
  public void setUp() throws Exception {
    Encryption.registerEncryption();
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
