package com.module7;

public class RSACryptosystem {
    public double plaintextSummation;
    public int a;
    public int b;
    public double n;
    public RSACryptosystem(int a, int b, double n) {
        this.a = a;
        this.b = b;
        this.n = n;
    }

    public int encrypt(String plaintext) {
        int[] plaintextPositions = getPlaintextPositions(plaintext);
        this.plaintextSummation = calculateSummationOfPlaintextValues(plaintextPositions);
        return this.encryptSummation();
    }

    public int decrypt(int ciphervalue) {
      return (int) RSATools.squareAndMultiply(ciphervalue, this.a, this.n);
    }

    private int encryptSummation() {
        return (int) RSATools.squareAndMultiply(this.plaintextSummation, this.b, this.n);
    }

    private int[] getPlaintextPositions(String plaintext) {
        int[] plaintextPositions = new int[plaintext.length()];
        for(int i = 0; i < plaintext.length(); i++) {
            char pChar = plaintext.charAt(i);
            char lowerCaseChar = Character.toLowerCase(pChar);
            plaintextPositions[i] = (int) lowerCaseChar - 97;
        }
        return plaintextPositions;
    }

    private double calculateSummationOfPlaintextValues(int[] plaintextPositions) {
        int exponentValue = 0;
        double sum = 0;
        for (int i = plaintextPositions.length - 1; i >= 0; i--) {
            sum += ((double) plaintextPositions[i])*RSATools.squareAndMultiply(26, exponentValue, this.n);
            exponentValue++;
        }
        return sum;
    }
}
