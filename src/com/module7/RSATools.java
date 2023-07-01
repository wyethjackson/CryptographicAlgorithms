package com.module7;

import java.util.ArrayList;

public class RSATools {
    public static double squareAndMultiply(double x, int exponent, double moduloN) {
      double z = 1;
      ArrayList<Integer> exponentBits = (countBitsFromInt(exponent));
      for (int i = exponentBits.size() - 1; i >= 0; i--) {
        z = Math.pow(z, 2) % moduloN;
        if(exponentBits.get(i) == 1) {
            z = (z * x) % moduloN;
        }
      }
      return z;
    }

    private static ArrayList<Integer> countBitsFromInt(int x) {
        int bitDigits = x;
        ArrayList<Integer> bits = new ArrayList<Integer>();
        while(bitDigits > 0) {
            if(bitDigits % 2 == 1) {
              bits.add(1);
            } else {
              bits.add(0);
            }
            bitDigits /= 2;
        }
        return bits;
    }

    private static double externalFactoringFunc(double x) {
        return Math.pow(x, 2) + 1;
    }

    public static double pollardRhoFactoring(double n, double initialX) {
      double x = initialX;
      double xPrime = initialX % n;
      double p = gcd((x - xPrime), n);
      while(p == 1) {
          x = externalFactoringFunc(x) % n;
          xPrime = externalFactoringFunc(xPrime) % n;
          xPrime = externalFactoringFunc(xPrime) % n;
          p = gcd((x - xPrime),n);
      }

      if(p == n) {
        return 0;
      }

      return (int) p;
    }



        public static double gcd(double a, double b) {
            ArrayList<Double> nums = new ArrayList<Double>();
            nums.add(a);
            nums.add(b);
            ArrayList<Double> q = new ArrayList<Double>();
        int m = 1;
        while(nums.get(m) != 0) {
            q.add(Math.floor(nums.get(m - 1)/nums.get(m)));
            nums.add(nums.get(m-1) - q.get(m - 1)*nums.get(m));
            m = m + 1;
        }
        m = m - 1;
        return Math.abs(nums.get(m));
    }

    public static double multiplicateInverse(double startModulo, double startTarget) {
        double modulo = startModulo;
        double target = startTarget;
        double t0 = 0;
        double t = 1;
        double q = Math.floor(modulo/target);
        int r = (int) (modulo - (q*target));
        while(r >= 1) {
            double temp = (t0 - (q*t)) % startModulo;
            if(temp < 0) {
                temp += startModulo;
            }
            t0 = t;
            t = temp;
            modulo = target;
            target = r;
            q = Math.floor(modulo/target);
            r = (int) (modulo - (q*target));
        }

        if(target != 1) {
            return 0;
        }
        return t;
    }

    public static char[] getPlaintextAlphabetPositions() {
        char[] plaintextPositions = new char[26];
        for(int i = 0; i < 26; i++) {
            plaintextPositions[i] = (char) (i + 97);
        }
        return plaintextPositions;
    }

    public static String convertIntToText(int plaintextIntValue) {
        char[] plaintextAlphabetPositions = getPlaintextAlphabetPositions();
        int sum = 0;
            for (int left = 0; left < 26; left++) {
                for (int middle = 0; middle < 26; middle++) {
                    for(int right = 0; right < 26; right++) {
                        sum = (int) (left*Math.pow(26, 2) + middle*26 + right);
                        if(sum == plaintextIntValue) {
                            return Character.toString(plaintextAlphabetPositions[left]) + Character.toString(plaintextAlphabetPositions[middle]) + Character.toString(plaintextAlphabetPositions[right]);
                        }
                    }
                }
            }
        return "";
    }

    public static void randomSquaresAlgorithm() {
        ArrayList<Double> products = new ArrayList<>();
        double productToCompare;
        int[] factorBase = new int[]{2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31};
        ArrayList<ArrayList<Integer>> productsOfProducts = new ArrayList<>();
            for (int i = 0; i <= factorBase.length; i++) {
                for (int j = i + 1; j < factorBase.length; j++) {
                   productToCompare = factorBase[i] * factorBase[j] % 256961;
                   products.add(productToCompare);
                   ArrayList<Integer> temp1 = new ArrayList<>();
                   temp1.add(factorBase[i]);
                   temp1.add(factorBase[j]);
                                                      productsOfProducts.add(temp1);
                    for (int m = j + 1; m < factorBase.length; m++) {
                       productToCompare = factorBase[i] * factorBase[j] * factorBase[m] % 256961;
                       products.add(productToCompare);
                       ArrayList<Integer> temp2 = new ArrayList<>();
                       temp2.add(factorBase[i]);
                       temp2.add(factorBase[j]);
                       temp2.add(factorBase[m]);
                                                      productsOfProducts.add(temp2);
                        for(int y = m + 1; y < factorBase.length; y++) {
                           productToCompare = factorBase[i] * factorBase[j] * factorBase[m] * factorBase[y] % 256961;
                           products.add(productToCompare);
                           ArrayList<Integer> temp3 = new ArrayList<>();
                           temp3.add(factorBase[i]);
                           temp3.add(factorBase[j]);
                           temp3.add(factorBase[m]);
                           temp3.add(factorBase[y]);
                           productsOfProducts.add(temp3);
                          for(int k = y + 1; k < factorBase.length; k++) {
                           productToCompare = factorBase[i] * factorBase[j] * factorBase[m] * factorBase[y] * factorBase[k] % 256961;
                           products.add(productToCompare);
                           ArrayList<Integer> temp4 = new ArrayList<>();
                           temp4.add(factorBase[i]);
                           temp4.add(factorBase[j]);
                           temp4.add(factorBase[m]);
                           temp4.add(factorBase[y]);
                           temp4.add(factorBase[k]);
                           productsOfProducts.add(temp4);
                          }
                        }
                    }
                }
            }

            ArrayList<ArrayList<Integer>> actualProducts = new ArrayList<>();

            for (int i = 500; i < 10000; i++) {
                double squareMod = Math.pow(i, 2) % 256961;
                for (int j = 0; j < products.size(); j++) {
                    if(squareMod == products.get(j)) {
                        System.out.println("SQUARE MOD=" + squareMod + "; z=" + i);
                        System.out.println("Base Factor Nums: ");
                        for (int k = 0; k < productsOfProducts.get(j).size(); k++) {
                            actualProducts.add(productsOfProducts.get(j));
                        }
                    }
                }
            }

    }
}
