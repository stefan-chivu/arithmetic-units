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
        System.out.print("| CNT | \tA\t |\t\tQ\t|  Q[-1] | \t\tM\t|    OP");
    }

    private void printSeparator(){
        String res = "\n";
        for(int i = 0; i<50; i++){
            res+="-";
        }
        System.out.println(res);
    }

    private void printLine(){
        System.out.print("| "+CNT.getBits()+" | " + A.getBits() + " | "+ Q.getBits() + " |    "+ q_1 + "   | "+ M.getBits() + " | " );
    }

    private void printA(){
        System.out.print("|     | " + A.getBits() + " | "+ Q.getBits() +" |        |          |   now RSHIFT\n" );
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
                        printLine();
                        System.out.print("Q[0]Q[-1] = 01 : A = A+M");
                        System.out.print("\n|     |+"+M.getBits()+" |\t\t \t|        | \t\t \t|");
                        System.out.print("\n|     |__________|\t\t \t|        | \t\t \t|\n");
                        A.addRegister(M);
                        q_1 = Q.bits[0];
                        printA();
                        A.rightArithmeticalChainShift(1,Q);
                        printLine();
                        printSeparator();
                        break;
                    case "10":
                        printLine();
                        System.out.print("Q[0]Q[-1] = 10 : A = A-M");
                        System.out.print("\n|     |+"+_M.getBits()+" |\t\t \t|        | \t\t \t|");
                        System.out.print("\n|     |__________|\t\t \t|        | \t\t \t|\n");
                        A.addRegister(_M);
                        q_1 = Q.bits[0];
                        printA();
                        A.rightArithmeticalChainShift(1,Q);
                        printLine();
                        printSeparator();
                        break;
                    default:
                        printLine();
                        System.out.print("Q[0]Q[-1] = 00 || 11 - shift only\n");
                        q_1 = Q.bits[0];
                        A.rightArithmeticalChainShift(1,Q);
                        printLine();
                        printSeparator();
                        break;
                }
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
        if(opA*opB == result){
            System.out.println("CORRECT");
        }else{
            System.out.println("FALSE");
        }
    }
}
