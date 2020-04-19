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
      System.err.println("ERROR: Aead registering");
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
    // 3. Do crypto.
    byte[] plaintext = string.getBytes();
    byte[] ciphertext = aead.encrypt(plaintext, new byte[0] /* additionalData */);
    return ciphertext;
  }

  public static String decrypt(byte[] string) throws Exception {
    KeysetHandle handle = getKeysetHandle(keyset);
    Aead aead = handle.getPrimitive(Aead.class);
    byte[] ciphertext = string;
    String plaintext = new String(aead.decrypt(ciphertext, new byte[0] /* additionalData */));
    return plaintext;
  }

}
