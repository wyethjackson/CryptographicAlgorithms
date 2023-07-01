package com.module7;

public class RSABreaker {
    public int n;
    public int b;
    private int a;
    public RSABreaker(int n, int b) {
       this.n = n;
       this.b = b;
    }

    public int[] factorN() {
        int decrementIndex = (int)Math.floor(this.n/2);
        for (int incrementIndex = 2; incrementIndex < this.n; incrementIndex++) {
            if(isPrime(incrementIndex) && this.n % incrementIndex == 0) {
              int division = (int) (this.n/incrementIndex);
              if(isPrime((int) division)) {
                  return new int[]{incrementIndex, division};
              }
            }
        }
        return new int[]{};
    }

    public void setA(int a) {
        this.a = a;
    }

    public String decrypt(int cipherValue) {
      int plaintextIntValue = (int) RSATools.squareAndMultiply(cipherValue, this.a, this.n);
      return RSATools.convertIntToText(plaintextIntValue);
    }

    private int findIsPrime(int x, int indexChange) {
        if(x <= 0 || x >= this.n) {
            return 0;
        }
        if(this.isPrime(x)) {
            return x;
        }
        x = x+indexChange;
        return this.findIsPrime(x, indexChange);
    }

    private boolean isPrime(int primeCandidate) {
      if(primeCandidate <= 1) {
        return false;
      }
      for(int i = 2; i < primeCandidate/2; i++) {
          if ((primeCandidate % i) == 0) {
              return false;
          }
      }
      return true;
    }
}
