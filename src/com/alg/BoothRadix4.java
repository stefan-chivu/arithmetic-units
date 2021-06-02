package com.alg;

import java.io.IOException;

public class BoothRadix4 extends ArithmeticUnit{
    Register A;
    Register Q;
    int q_1;
    Register M;
    Register M2;
    Register _M;
    Register _M2;
    int CNT_MAX;
    Register CNT;

    public BoothRadix4(int operandBits, int opA,int opB){
        super(opA,opB);
        this.CNT_MAX = 4;
        this.A = new Register(operandBits+1);
        this.Q = new Register(operandBits);
        this.M = new Register(operandBits);
        this._M = new Register(operandBits);
        this.M2 = new Register(operandBits);
        this._M2 = new Register(operandBits);
        this.CNT = new Register(2);
        q_1 = 0;
        try {
            Q.setBits(opA);
            M.setBits(opB);
            M2.setBits(2*opB % (int)Math.pow(2,M2.bitNumber));
            _M.setBits((-1)*opB);
            _M2.setBits((-2)*opB % (int)Math.pow(2,M2.bitNumber));
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

        //System.out.println("| \tM\t | \t2M\t |\t\t-2M\t| \t\t-M\t|");
        //System.out.println("| "+M.getBits()+" | " + M2.getBits() + " | "+ _M2.getBits() + " |    "+ _M.getBits() + " | " );

        printHeader();
        printSeparator();
        //printLine();
        for(int i = 0 ; i < CNT_MAX; i++){
            try {
                CNT.setBits(i);
                OP = Q.bits[1] + "" + Q.bits[0] + "" + q_1;
                switch (OP){
                    case "001":
                    case "010":
                        A.addRegister(M);
                        q_1 = Q.bits[1];
                        A.rightArithmeticalChainShift(2,Q);
                        break;
                    case "011":
                        A.addRegister(M2);
                        q_1 = Q.bits[1];
                        A.rightArithmeticalChainShift(2,Q);
                        break;
                    case "100":
                        A.addRegister(_M2);
                        q_1 = Q.bits[1];
                        A.rightArithmeticalChainShift(2,Q);
                        break;
                    case "101":
                    case "110":
                        A.addRegister(_M);
                        q_1 = Q.bits[1];
                        A.rightArithmeticalChainShift(2,Q);
                        break;
                    default:
                        q_1 = Q.bits[1];
                        A.rightArithmeticalChainShift(2,Q);
                        break;
                }
                printLine();
            }catch(Exception e){
                System.out.println(e);
            }
        }

        Register P = new Register(2*Q.bitNumber);
        try{
            int aValue = A.getValue() % ((int) Math.pow(2,A.bitNumber-1));
            aValue = aValue<<Q.bitNumber;
            int qValue = Q.getValue();
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
