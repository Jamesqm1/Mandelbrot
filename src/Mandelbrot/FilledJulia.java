package Mandelbrot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class FilledJulia {
    private double xRange;
    private double yRange;
    private double maxReal;
    private double minReal;
    private double maxImag;
    private double minImag;
    private int imageW;
    private int imageH;
    private int maxIterations;
    private Complex c;

    public FilledJulia() {
        maxReal = 2.0;
        minReal = -2.0;
        maxImag = 2.0;
        minImag = -2.0;
        imageW = 1000;
        imageH = 1000;
        maxIterations = 500;
        c = new Complex(0,0);
        xRange = maxReal - minReal;
        yRange = maxImag - minImag;
    }

    public FilledJulia(double mxr, double mnr, double mxi, double mni, int iW, int iH, int maxI, double cr, double ci) {
        maxReal = mxr;
        minReal = mnr;
        maxImag = mxi;
        minImag = mni;
        imageW = iW;
        imageH = iH;
        maxIterations = maxI;
        c = new Complex(cr, ci);
        xRange = maxReal - minReal;
        yRange = maxImag - minImag;
    }

    public FilledJulia(String InputFileName) {
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
        double cr = in.nextDouble();
        double ci = in.nextDouble();
        c = new Complex(cr,ci);
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

    public int findJuliaNumber(Complex z) {
        int i = 0;
        while (i <= maxIterations && z.abs() <= 2) {
            // iterate z^2 + c with fixed c
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

                    int n = findJuliaNumber(z);
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
        run("output_image_j.ppm");
    }

    public static void main(String[] args){
        FilledJulia test = new FilledJulia("inputJulia.txt");
        test.run();
    }
}
