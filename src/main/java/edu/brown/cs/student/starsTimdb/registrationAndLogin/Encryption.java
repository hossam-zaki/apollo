package edu.brown.cs.student.starsTimdb.registrationAndLogin;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;

import com.google.crypto.tink.Aead;
import com.google.crypto.tink.CleartextKeysetHandle;
import com.google.crypto.tink.JsonKeysetReader;
import com.google.crypto.tink.JsonKeysetWriter;
import com.google.crypto.tink.KeysetHandle;
import com.google.crypto.tink.aead.AeadConfig;
import com.google.crypto.tink.aead.AeadKeyTemplates;

public final class Encryption {
  private static KeysetHandle keysetHandle;
  private static Aead aead;
  private static File keyset;

  public Encryption() {
    try {
      AeadConfig.register();
    } catch (GeneralSecurityException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    keyset = new File("test.cfg");
  }

  /**
   * Loads a KeysetHandle from {@code keyset} or generate a new one if it doesn't
   * exist.
   */
  private static KeysetHandle getKeysetHandle(File keyset)
      throws GeneralSecurityException, IOException {
    if (keyset.exists()) {
      System.out.println("HEREEEEEEEEEEEEEE");
      // Read the cleartext keyset from disk.
      // WARNING: reading cleartext keysets is a bad practice. Tink supports
      // reading/writing
      // encrypted keysets, see
      // https://github.com/google/tink/blob/master/docs/JAVA-HOWTO.md#loading-existing-keysets.
      return CleartextKeysetHandle.read(JsonKeysetReader.withFile(keyset));
    }
    KeysetHandle handle = KeysetHandle.generateNew(AeadKeyTemplates.AES128_GCM);
    CleartextKeysetHandle.write(handle, JsonKeysetWriter.withFile(keyset));
    return handle;
  }

  public static byte[] encrypt(String string) throws Exception {

    // 1. Obtain a keyset handle.
    KeysetHandle handle = getKeysetHandle(keyset);
    // 2. Get a primitive.
    Aead aead = handle.getPrimitive(Aead.class);
    // 3. Do crypto. It's that simple!
    byte[] plaintext = string.getBytes();
    byte[] ciphertext = aead.encrypt(plaintext, new byte[0] /* additionalData */);
    System.out.println(ciphertext.toString());
    return ciphertext;
  }

  public static String decrypt(byte[] string) throws Exception {
    KeysetHandle handle = getKeysetHandle(keyset);

    Aead aead = handle.getPrimitive(Aead.class);
    byte[] ciphertext = string;
    String plaintext = new String (aead.decrypt(ciphertext, new byte[0] /* additionalData */));
    System.out.println(plaintext);
    return plaintext;
  }

}
