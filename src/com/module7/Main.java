
package com.module7;

public class Main {

    public static void main(String[] args) {
      // Module 11, problem 1
       DSA dsa = new DSA(7879, 101, 170, 4567);
       double[] dsaSignature = dsa.sign(75, 52, 49);
       System.out.println("(" + dsaSignature[0] + ", " + dsaSignature[1] + ")");
       boolean signatureVerified = dsa.verify(52, dsaSignature[0], dsaSignature[1]);
       System.out.println("Signature Verified: " + signatureVerified);

        // Module 11, problem 2
        ECDSA ellipticCurve = new ECDSA(127, 1, 26, new double[]{2, 6});
        ellipticCurve.setPrivateKey(54);
        ellipticCurve.setQ();
        // 2a
        ellipticCurve.loadPublicKey();
        System.out.println("Public Key: " + "(" + ellipticCurve.publicKey[0] + ", " + ellipticCurve.publicKey[1] + ")");


        //2b
        double[] ecdsaSignature = ellipticCurve.sign(75, 10);
        //message x if SHA3-224(x) = 10, when k = 75.
        System.out.println("Signature: (" + ecdsaSignature[0] + ", " + ecdsaSignature[1] + ")");
        //2c
        System.out.println(ellipticCurve.verify(ecdsaSignature, 10));
    }
}
