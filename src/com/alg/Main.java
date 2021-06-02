package com.alg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void Booth(int x, int y){
        //(nr biti, Q, M)
        BoothMultiplier bm = new BoothMultiplier(8,x,y);
        bm.calculateResult();
        bm.printResult();
    }

    public static void BoothModified(int x, int y){
        BoothModified bmod = new BoothModified(8,x,y);
        bmod.calculateResult();
        bmod.printResult();
    }

    public static void BoothRadix4(int x, int y){
        BoothRadix4 br4 = new BoothRadix4(8,x,y);
        br4.calculateResult();
        br4.printResult();
    }

    public static void SRT2(int x, int y){
        SRTRadix2 srt2 = new SRTRadix2(8, x, y);
        srt2.calculateResult();
        srt2.printResult();
    }

    public static void printMenu(){
        System.out.println("\n\nChoose operation:");
        System.out.println("\t1.Booth");
        System.out.println("\t2.Booth Modified");
        System.out.println("\t3.Booth Radix-4");
        System.out.println("\t4.SRT Radix-2");
        System.out.print("\tOPT:");
    }

    public static void test(){

    }

    public static void main(String[] args) {
        int x,y;
        boolean exit = false;
        while(!exit){
            printMenu();
            try{
                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                String optStr = br.readLine();
                int opt = Integer.parseInt(optStr);
                switch (opt){
                    case 1:
                        System.out.print("\nx = ");
                        optStr = br.readLine();
                        opt = Integer.parseInt(optStr);
                        x = opt;
                        System.out.print("y = ");
                        optStr = br.readLine();
                        opt = Integer.parseInt(optStr);
                        y = opt;
                        Booth(x,y);
                        break;
                    case 2:
                        System.out.print("\nx = ");
                        optStr = br.readLine();
                        opt = Integer.parseInt(optStr);
                        x = opt;
                        System.out.print("y = ");
                        optStr = br.readLine();
                        opt = Integer.parseInt(optStr);
                        y = opt;
                        BoothModified(x,y);
                        break;
                    case 3:
                        System.out.print("\nx = ");
                        optStr = br.readLine();
                        opt = Integer.parseInt(optStr);
                        x = opt;
                        System.out.print("y = ");
                        optStr = br.readLine();
                        opt = Integer.parseInt(optStr);
                        y = opt;
                        BoothRadix4(x,y);
                        break;
                    case 4:
                        System.out.print("\nx = ");
                        optStr = br.readLine();
                        opt = Integer.parseInt(optStr);
                        x = opt;
                        System.out.print("y = ");
                        optStr = br.readLine();
                        opt = Integer.parseInt(optStr);
                        y = opt;
                        SRT2(x,y);
                        break;
                    default:
                        test();
                        exit = true;
                        break;
                }
            }catch (IOException e){
                System.out.println(e);
            }
        }
    }
}
