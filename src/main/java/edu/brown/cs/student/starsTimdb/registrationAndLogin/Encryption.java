package edu.brown.cs.student.starsTimdb.registrationAndLogin;

import com.google.crypto.tink.CleartextKeysetHandle;
import com.google.crypto.tink.KeysetHandle;
import com.google.crypto.tink.aead.AeadKeyTemplates;
import com.google.crypto.tink.JsonKeysetWriter;
import java.io.File;

public class Encryption implements EncryptionInterface{

	@Override
	public String encrypt(String string) {
		AeadConfig.register();

		KeysetHandle keysetHandle = KeysetHandle.generateNew(AeadKeyTemplates.AES256_GCM);
		Aead aead = AeadFactory.getPrimitive(keysetHandle);		
		return null;
	}

	@Override
	public String decrypt(String string) {
		// TODO Auto-generated method stub
		return null;
	}

}
