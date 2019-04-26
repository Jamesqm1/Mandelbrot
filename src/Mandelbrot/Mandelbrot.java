package Mandelbrot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Mandelbrot {
    private double xRange;
    private double yRange;
    private double maxReal;
    private double minReal;
    private double maxImag;
    private double minImag;
    private int imageW;
    private int imageH;
    private int maxIterations;

    public Mandelbrot() {
        maxReal = 2.0;
        minReal = -2.0;
        maxImag = 2.0;
        minImag = -2.0;
        imageW = 1000;
        imageH = 1000;
        maxIterations = 500;
        xRange = maxReal - minReal;
        yRange = maxImag - minImag;
    }

    public Mandelbrot(double mxr, double mnr, double mxi, double mni, int iW, int iH, int maxI) {
        maxReal = mxr;
        minReal = mnr;
        maxImag = mxi;
        minImag = mni;
        imageW = iW;
        imageH = iH;
        maxIterations = maxI;
        xRange = maxReal - minReal;
        yRange = maxImag - minImag;
    }

    public Mandelbrot(String InputFileName) {
        File inputFile = new File(InputFileName);
        Scanner in = null;
        try {
            in = new Scanner(inputFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        maxReal = in.nextDouble();
        minReal = in.nextDouble();
        maxImag = in.nextDouble();
        minImag = in.nextDouble();
        imageW = in.nextInt();
        imageH = in.nextInt();
        maxIterations = in.nextInt();
        xRange = maxReal - minReal;
        yRange = maxImag - minImag;
        in.close();
        System.out.println("File Successfully loaded!");

    }

    public double mapToReal(int x) {
        return x * (xRange / imageW) + minReal;
    }

    public double mapToImag(int y) {
        return y * (yRange / imageH) + minImag;
    }

    public int findMandelbrotNumber(Complex c) {
        int i = 0;
        Complex z = new Complex(0,0);
        while (i <= maxIterations && z.abs() <= 2) {
            // iterate z^2 + c
            z = z.exp(2).plus(c);
            i++;
        }
        return i;
    }

    public void run(String outputFileName) {
        final long startTime = System.currentTimeMillis();
        File outputFile = new File(outputFileName);
        PrintWriter out = null;
        try {
            out = new PrintWriter(outputFile);
            out.println("P3");
            out.println(imageW + " " + imageH);
            out.println(256);

            for (int y = 0; y < imageH; y++) {
                for (int x = 0; x < imageW; x++) {

                    Complex z = new Complex(mapToReal(x), mapToImag(y));

                    int n = findMandelbrotNumber(z);
                    int r = (n % 256);
                    int g = (n % 256);
                    int b = (n % 256);

                    out.print(r + " " + g + " " + b + " ");
                }
                out.println();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            out.flush();
            out.close();
        }
        final long endTime = System.currentTimeMillis();
        System.out.println("Finished running in "+(endTime-startTime)/1000+"s");

    }

    public void run() {
        run("output_image_m.ppm");
    }

    public static void main(String[] args) {
        Mandelbrot test = new Mandelbrot("inputMandelbrot.txt");
        test.run("mandelbrot_image.ppm");
    }
}
