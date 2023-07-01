package com.module7;

import java.util.ArrayList;

public class Shanks_Algorithm {
  public double run(double n, double alpha, double beta) {
    ArrayList<double[]> L1Pairs = new ArrayList<double[]>();
    double modulo = n + 1;
    double m = Math.ceil(Math.sqrt(n));
    double L1BaseValue = RSATools.squareAndMultiply(alpha, (int) m, modulo);
    for (double i = 0; i < m; i++) {
      double value = RSATools.squareAndMultiply(L1BaseValue, (int) i, modulo);
      L1Pairs.add(new double[]{i, value});
    }
    L1Pairs.sort(new Sorter());
    ArrayList<double[]> L2Pairs = new ArrayList<double[]>();
    for (double i = 0; i < m; i++) {
      double alphaToTheI = RSATools.squareAndMultiply(alpha, (int) i, modulo);
      double value = (beta*RSATools.multiplicateInverse(modulo, alphaToTheI) % modulo);
      L2Pairs.add(new double[]{i, value});
    }
    L2Pairs.sort(new Sorter());
    double L1Value = 0;
    double L2Value = 0;
    boolean valuesSet = false;
    for (int i = 0; i < L1Pairs.size(); i++) {
      double[] L1Item = L1Pairs.get(i);
      for (int j = 0; j < L2Pairs.size(); j++) {
        double[] L2Item = L2Pairs.get(j);
        if(L1Item[1] == L2Item[1]) {
          L1Value = L1Item[0];
          L2Value = L2Item[0];
          valuesSet = true;
          break;
        }
      }
      if(valuesSet) {
        break;
      }
    }
    return m*L1Value + L2Value;
  }
}
