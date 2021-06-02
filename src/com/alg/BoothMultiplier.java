package com.alg;

import java.io.IOException;

public class BoothMultiplier extends ArithmeticUnit{
    Register A;
    Register Q;
    int q_1;
    Register M;
    Register _M;
    int CNT_MAX;
    Register CNT;

    public BoothMultiplier(int operandBits, int opA,int opB){
        super(opA,opB);
        this.CNT_MAX = operandBits;
        this.A = new Register(operandBits);
        this.Q = new Register(operandBits);
        this.M = new Register(operandBits);
        this._M = new Register(operandBits);
        this.CNT = new Register((int)(Math.log(operandBits)/Math.log(2)));
        q_1 = 0;
        try {
            Q.setBits(opA);
            M.setBits(opB);
            _M.setBits((-1)*opB);
        }catch(Exception e){
            System.out.println(e);
        }
    }

    private void printHeader(){
        System.out.println("| CNT | \tA\t |\t\tQ\t|  Q[-1] | \t\tM\t|");
    }

    private void printSeparator(){
        String res = "";
        for(int i = 0; i<50; i++){
            res+="-";
        }
        System.out.println(res);
    }

    private void printLine(){
        System.out.println("| "+CNT.getBits()+" | " + A.getBits() + " | "+ Q.getBits() + " |    "+ q_1 + "   | "+ M.getBits() + " | " );
        printSeparator();
    }

    @Override
    public void calculateResult() {
        String OP = "";

        printHeader();
        printSeparator();
        //printLine();
        for(int i = 0 ; i < CNT_MAX; i++){
            try {
                CNT.setBits(i);
                OP = Q.bits[0] + "" + q_1;
                switch (OP){
                    case "01":
                        A.addRegister(M);
                        q_1 = Q.bits[0];
                        A.rightArithmeticalChainShift(1,Q);
                        break;
                    case "10":
                        A.addRegister(_M);
                        q_1 = Q.bits[0];
                        A.rightArithmeticalChainShift(1,Q);
                        break;
                    default:
                        q_1 = Q.bits[0];
                        A.rightArithmeticalChainShift(1,Q);
                        break;
                }
                printLine();
            }catch(Exception e){
                System.out.println(e);
            }
        }

        Register P = new Register(2*A.bitNumber);
        try{
            int aValue = A.getValue();
            aValue = aValue<<Q.bitNumber;
            P.setBits(Q.getValue()+aValue);
        }catch (IOException e){
            System.out.println(e);
        }

        this.result = P.getValue();
        System.out.println("\nP = " + P.getBits());
    }

    public void printResult(){
        System.out.println(opA + " * " + opB + " = " + result);
    }
}
