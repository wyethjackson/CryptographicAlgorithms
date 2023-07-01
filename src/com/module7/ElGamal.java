package com.module7;

public class ElGamal {
    double p;
    double alpha;
    double a;
    double beta;
    public ElGamal(double p, double alpha, double a, double beta) {
        this.p = p;
        this.alpha = alpha;
        this.a = a;
        this.beta = beta;
    }

    public String decrypt(double y1, double y2) {
        double y1ToAModP = RSATools.squareAndMultiply(y1, (int) this.a, this.p);
        double inverse = RSATools.multiplicateInverse(this.p, y1ToAModP);
        double plaintextNum = (inverse*y2) % this.p;
        return RSATools.convertIntToText((int) plaintextNum);
    }
}
