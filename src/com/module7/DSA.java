package com.module7;

public class DSA {
    double p;
    double q;
    double alpha;
    double beta;

    public DSA(double p, double q, double alpha, double beta) {
      this.p = p;
      this.q = q;
      this.alpha = alpha;
      this.beta = beta;
    }

    public double[] sign(double privateKey, double hashedX, double k) {
      if(k < 1 || k >= this.q) {
        return new double[]{};
      }
      double gamma = (RSATools.squareAndMultiply(this.alpha, (int) k, this.p)) % this.q;
      double firstDelta = hashedX+(privateKey*gamma);
      double secondDelta = RSATools.multiplicateInverse(this.q, k);
      double delta = (firstDelta * secondDelta) % this.q;
      return new double[]{gamma, delta};
    }

    public boolean verify(double hashedX, double gamma, double delta) {
      double deltaInverse = RSATools.multiplicateInverse(this.q, delta);
      double e1 = (hashedX * deltaInverse) % this.q;
      double e2 = (gamma * deltaInverse) % this.q;
      double alphaToE1 = RSATools.squareAndMultiply(this.alpha, (int) e1, this.p);
      double betaToE2 = RSATools.squareAndMultiply(this.beta, (int) e2, this.p);;
      double potentialGamma = ((alphaToE1*betaToE2) % this.p) % this.q;
      return potentialGamma == gamma;
    }
}
