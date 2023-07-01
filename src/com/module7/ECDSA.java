package com.module7;

import java.util.ArrayList;

public class ECDSA {
    double p;
    double a;
    double b;
    double privateKey;
    double[] publicKey;
    double[] generatorPoint;
    double q;
    public ECDSA(double p, double a, double b, double[] generatorPoint) {
      this.p = p;
      this.a = a;
      this.b = b;
      this.generatorPoint = generatorPoint;
    }

    public void setQ() {
      ArrayList<double[]> points = getAllPointsOnCurve();
      this.q = points.size() + 1;
    }

    public ArrayList<double[]> getAllPointsOnCurve() {
        ArrayList<double[]> points = new ArrayList<>();

        for(double x = 0; x < this.p; x++) {
          double z = (Math.pow(x, 3) + this.a*x + this.b) % this.p;
          if(z == 0) {
              double[] point = new double[]{x, z};
              points.add(point);
          } else {
            double quadratic_residue_exponent = (this.p - 1)/2;
            double quadratic_residue = RSATools.squareAndMultiply(z,(int) quadratic_residue_exponent, this.p);
            if(quadratic_residue == 1 && this.p % 4 == 3) {
              double plusZ = RSATools.squareAndMultiply(z, (int) ((this.p + 1)/4), this.p);
              double minusZ = (-plusZ) + this.p;
              points.add(new double[]{x, plusZ});
              points.add(new double[]{x, minusZ});
            }
          }
        }
        return points;
    }

    public double[] addPoints(double x1, double y1, double x2, double y2) {
      double lambda = calculateLambda(x1, y1, x2, y2);
      double x3 = (Math.pow(lambda, 2) - x1 - x2) % this.p;
      double y3 = (lambda * (x1 - x3) - y1) % this.p;
      if(x3 < 0) {
          x3 = x3 + this.p;
      }

      if(y3 < 0) {
          y3 = y3 + this.p;
      }
      double[] points = new double[]{x3, y3};
      return points;
    }

    private double calculateLambda(double x1, double y1, double x2, double y2) {
      double value;
      if(x1 == x2 && y1 == y2) {
        double secondPart = ((2*y1) % this.p);
        value = (3*Math.pow(x1, 2) + this.a) * RSATools.multiplicateInverse(this.p, secondPart);
      } else {
        double secondPart = (x2 - x1) % this.p;
        if(secondPart < 0) {
            secondPart += this.p;
        }
        value = (y2 - y1) * RSATools.multiplicateInverse(this.p, secondPart);
      }
      value = value % this.p;
      if(value < 0) {
          value += this.p;
      }
      return value;
    }

    public void setPrivateKey(double privateKey) {
        this.privateKey = privateKey;
    }

    public double[] sign(double k, double hashedX) {
        double[] temporaryKey = this.calculateMultPoint(this.generatorPoint, k);
        double u = temporaryKey[0];
        double v = temporaryKey[1];
        double r = u % this.q;
        double kInverse = RSATools.multiplicateInverse(q, k);
        double s = ((hashedX + (this.privateKey*r))*kInverse) % q;
        return new double[]{r, s};
    }

    public boolean verify(double[] signature, double hashedX) {
        double r = signature[0];
        double s = signature[1];
        double w = RSATools.multiplicateInverse(this.q, s);
        double i = (w*hashedX) % this.q;
        double j = (w*r) % this.q;
        double[] generatorPointMult = this.calculateMultPoint(this.generatorPoint, i);
        double[] publicKeyMult = this.calculateMultPoint(this.publicKey, j);
        double[] point = this.addPoints(generatorPointMult[0], generatorPointMult[1], publicKeyMult[0], publicKeyMult[1]);
        double u = point[0];
        return (u % this.q) == r;
    }

    public void loadPublicKey() {
        this.publicKey = calculateMultPoint(this.generatorPoint, this.privateKey);
    }

    public double[] calculateMultPoint(double[] point, double mult) {
        double[] multPoint = point;
        for (int i = 1; i < mult; i++) {
            multPoint = addPoints(multPoint[0], multPoint[1], point[0], point[1]);
        }
        return multPoint;
    }
}

