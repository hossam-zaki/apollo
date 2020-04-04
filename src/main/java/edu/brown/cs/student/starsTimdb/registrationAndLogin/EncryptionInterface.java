package edu.brown.cs.student.starsTimdb.registrationAndLogin;

public interface EncryptionInterface {

  String encrypt(String string, String context);

  String decrypt(String string, String context);
}
