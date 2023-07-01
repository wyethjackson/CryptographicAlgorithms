package com.module7;

import java.util.ArrayList;

public class EllipticCurve {
    double primeMaxNum;
    double a;
    double b;
    double privateKey;
    double[] publicKey;
    public EllipticCurve(double primeMaxNum, double a, double b) {
      this.primeMaxNum = primeMaxNum;
      this.a = a;
      this.b = b;
    }

    public ArrayList<double[]> getAllPointsOnCurve() {
        ArrayList<double[]> points = new ArrayList<>();

        for(double x = 0; x < this.primeMaxNum; x++) {
          double z = (Math.pow(x, 3) + this.a*x + this.b) % this.primeMaxNum;
          if(z == 0) {
              double[] point = new double[]{x, z};
              points.add(point);
          } else {
            double quadratic_residue_exponent = (this.primeMaxNum - 1)/2;
            double quadratic_residue = RSATools.squareAndMultiply(z,(int) quadratic_residue_exponent, this.primeMaxNum);
            if(quadratic_residue == 1 && this.primeMaxNum % 4 == 3) {
              double plusZ = RSATools.squareAndMultiply(z, (int) ((this.primeMaxNum + 1)/4), this.primeMaxNum);
              double minusZ = (-plusZ) + this.primeMaxNum;
              points.add(new double[]{x, plusZ});
              points.add(new double[]{x, minusZ});
            }
          }
        }
        return points;
    }

    public double[] addPoints(double x1, double y1, double x2, double y2) {
      double lambda = calculateLambda(x1, y1, x2, y2);
      double x3 = (Math.pow(lambda, 2) - x1 - x2) % this.primeMaxNum;
      double y3 = (lambda * (x1 - x3) - y1) % this.primeMaxNum;
      if(x3 < 0) {
          x3 = x3 + this.primeMaxNum;
      }

      if(y3 < 0) {
          y3 = y3 + this.primeMaxNum;
      }
      double[] points = new double[]{x3, y3};
      return points;
    }

    private double calculateLambda(double x1, double y1, double x2, double y2) {
      double value;
      if(x1 == x2 && y1 == y2) {
        double secondPart = ((2*y1) % this.primeMaxNum);
        value = (3*Math.pow(x1, 2) + this.a) * RSATools.multiplicateInverse(this.primeMaxNum, secondPart);
      } else {
        double secondPart = (x2 - x1) % this.primeMaxNum;
        if(secondPart < 0) {
            secondPart += this.primeMaxNum;
        }
        value = (y2 - y1) * RSATools.multiplicateInverse(this.primeMaxNum, secondPart);
      }
      value = value % this.primeMaxNum;
      if(value < 0) {
          value += this.primeMaxNum;
      }
      return value;
    }

    public void setPrivateKey(double privateKey) {
        this.privateKey = privateKey;
    }

    public void setPublicKey(double[] publicKey) {
        this.publicKey = publicKey;
    }

    public double[] computeQ() {
        return calculateMultPoint(this.publicKey, this.privateKey);
    }

    public double[] point_decompress(double x, double i) {
        double z = (Math.pow(x, 3) + this.a*x + this.b) % this.primeMaxNum;
        double y;
        double quadratic_residue_exponent = (this.primeMaxNum - 1)/2;
        double quadratic_residue = RSATools.squareAndMultiply(z,(int) quadratic_residue_exponent, this.primeMaxNum);
        if(quadratic_residue != 1 || this.primeMaxNum % 4 != 3) {
          y = Math.sqrt(z) % this.primeMaxNum;
        } else {
          y = Math.pow(z, ((this.primeMaxNum + 1)/4)) % this.primeMaxNum;
        }

        if ((y % 2) == (i % 2)) {
           return  new double[]{x, y};
        }
        return  new double[]{x, (this.primeMaxNum - y)};
    }

    public double decrypt(double[] y1, double y2) {
        double[] point = this.point_decompress(y1[0], y1[1]);
        double[] point0 = this.calculateMultPoint(point, this.privateKey);
        double x0 = point0[0];
        double x0Inverse = RSATools.multiplicateInverse(this.primeMaxNum, x0);
        return (x0Inverse*y2) % this.primeMaxNum;
    }

    public double[] calculateMultPoint(double[] point, double mult) {
        double[] multPoint = point;
        for (int i = 1; i < mult; i++) {
            multPoint = addPoints(multPoint[0], multPoint[1], point[0], point[1]);
        }
        return multPoint;
    }
}
