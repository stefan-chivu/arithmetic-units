package com.alg;

import java.io.IOException;

public class BoothModified extends ArithmeticUnit{
    int OVR;
    Register A;
    Register Q;
    int R;
    Register M;
    Register _M;
    int CNT_MAX;
    Register CNT;

    public BoothModified(int operandBits, int opA,int opB){
        super(opA,opB);
        this.CNT_MAX = operandBits;
        OVR = 0;
        this.A = new Register(operandBits);
        this.Q = new Register(operandBits+1);
        this.M = new Register(operandBits);
        this._M = new Register(operandBits);
        this.CNT = new Register((int)(Math.log(operandBits)/Math.log(2)));
        R = 0;
        try {
            Q.setBits(opA);
            M.setBits(opB);
            _M.setBits((-1)*opB);
        }catch(Exception e){
            System.out.println(e);
        }
    }

    private void printHeader(){
        System.out.println("| CNT | OVR |     A\t   | Q[8] |    Q\t | R | \t\tM\t|");
    }

    private void printSeparator(){
        String res = "";
        for(int i = 0; i<50; i++){
            res+="-";
        }
        System.out.println(res);
    }

    private void printLine(){
        System.out.println("| "+CNT.getBits()+ " |  "+ OVR + "  | " + A.getBits() +" |   "+ Q.getBits().substring(0,1) +"  | "+Q.getBits().substring(1,Q.bitNumber) + " | "+ R + " | "+ M.getBits() + " | " );
        printSeparator();
    }

    @Override
    public void calculateResult() {
        String OP = "";
        printHeader();
        printSeparator();
        printLine();
        for(int i = 0 ; i < CNT_MAX; i++){
            try {
                CNT.setBits(i);
                OP = Q.bits[1] + "" + Q.bits[0] + "" + R;
                switch (OP){
                    case "000":
                    case "100":
                        R = 0;
                        A.rightArithmeticalChainShift(1,Q);
                        break;
                    case "001":
                    case "010":
                        R = 0;
                        OVR = A.addRegisterCheckOVR(M);
                        A.rightArithmeticalChainShift(1,Q);
                        A.bits[7] = (((OVR==0)&&(A.bits[7]==1)) || ((OVR==1)&&(A.bits[7]==0))) ? 1:0;
                        break;
                    case "011":
                    case "111":
                        R = 1;
                        A.rightArithmeticalChainShift(1,Q);
                        break;
                    case "101":
                    case "110":
                        R = 1;
                        //A.addRegister(_M);
                        OVR = A.addRegisterCheckOVR(_M);
                        A.rightArithmeticalChainShift(1,Q);
                        A.bits[7] = (((OVR==0)&&(A.bits[7]==1)) || ((OVR==1)&&(A.bits[7]==0))) ? 1:0;
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

        this.result = P.getValue()>>1;
        System.out.println("\nP = " + P.getBits() + "\b");
    }

    public void printResult(){
        System.out.println(opA + " * " + opB + " = " + result);
    }
}
