package Mandelbrot;

public class Complex {
    private final double real;
    private final double imag;

    public Complex(double r, double i) {
        real = r;
        imag = i;
    }

    public String toString() {
        if (real == 0) {
            return imag + "i";
        } else if (imag < 0) {
            return real + "-" + (-imag) + "i";
        } else if (imag > 0) {
            return real + "+" + imag + "i";
        }
        return real + "";
    }

    public double abs() {
        return Math.sqrt(Math.pow(real, 2) + Math.pow(imag, 2));
    }

    public Complex plus(Complex b) {
        Complex a = this;
        return new Complex(a.real + b.real, a.imag + b.imag);
    }

    public Complex plus(double b) {
        return new Complex(real + b, imag);
    }

    public Complex minus(Complex b) {
        Complex a = this;
        return new Complex(a.real - b.real, a.imag - b.imag);
    }

    public Complex minus(double b) {
        return new Complex(real - b, imag);
    }

    public Complex multiply(Complex b) {
        Complex a = this;
        double r = a.real * b.real - a.imag * b.imag;
        double i = a.real * b.imag + a.imag * b.real;
        return new Complex(r, i);
    }

    public Complex conjugate() {
        return new Complex(real, -imag);
    }

    public Complex receprocal() {
        double scale = Math.pow(real, 2) + Math.pow(imag, 2);
        return new Complex(real / scale, -imag / scale);
    }

    public Complex divide(Complex b) {
        Complex a = this;
        return a.multiply(b.receprocal());
    }

    public Complex exp(Complex b) {
        return new Complex(Math.exp(real) * Math.cos(imag), Math.exp(real) * Math.sin(imag));
    }

    public Complex exp(int b) {
        Complex a = this;
        for(int i =0; i<b-1;i++){
            a = a.multiply(a);
        }
        return a;
    }

    public double angle() {
        return Math.atan2(real, imag);
    }

    public boolean equals(Object x) {
        if (x == null) {
            return false;
        } else if (this.getClass() != x.getClass()) {
            return false;
        }
        Complex other = (Complex) x;
        return real == other.real && imag == other.imag;
    }

    public double getReal() {
        return real;
    }

    public double getImag() {
        return imag;
    }

    //to test
    public static void main(String[] args){
        Complex a = new Complex(1,1);
        Complex b = new Complex(Math.sqrt(2), Math.sqrt(2));

        System.out.println("a = "+a.toString());
        System.out.println("b = "+b.toString());
        System.out.println(a.exp(2).toString());

    }
}
