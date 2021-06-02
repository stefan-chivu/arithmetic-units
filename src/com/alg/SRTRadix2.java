package com.alg;

import java.io.IOException;

public class SRTRadix2 extends ArithmeticUnit{
    Register P;
    Register A;
    Register _A;
    Register B;
    Register M;
    Register _M;
    Register CNT;
    int cntMax;

    public SRTRadix2(int operandBits, int opA, int opB){
        this.opA = opA;
        this.opB = opB;

        P = new Register(operandBits+1);
        A = new Register(operandBits);
        _A = new Register(operandBits);
        B = new Register(operandBits);
        M = new Register(operandBits+1);
        _M = new Register(operandBits+1);
        int v = (int) (Math.log(operandBits) / Math.log(2));
        CNT = new Register(v);
        cntMax = operandBits;

        try {
            if(opA < 0 || opB<=0){
                throw new IOException("Only positive integers are allowed for SRT-2 divider");
            }
            A.setBits(opA);
            B.setBits(opB);
            M.setBits(opB);
            _M.setBits((-1)*(opB));
        }catch (IOException e){
            System.out.println(e);
        }
    }

    private void shiftLeftChainPAB(int bitNo){
        M.leftShift(bitNo);
        _M.leftShift(bitNo);
        while(bitNo-- != 0){
            P.leftShift(1);
            P.bits[0] = A.bits[A.bitNumber-1];
            P.updateValue();
            A.leftShift(1);
            A.bits[0] = B.bits[B.bitNumber-1];
            A.updateValue();
            B.leftShift(1);
        }
    }

    private void shift_step(){
        P.leftShift(1);
        P.bits[0] = A.bits[A.bitNumber-1];
        P.updateValue();
        A.leftShift(1);
        _A.leftShift(1);
        A.bits[0] = 0;
    }

    private void sub_step(){
        P.leftShift(1);
        P.bits[0] = A.bits[A.bitNumber-1];
        P.updateValue();
        A.leftShift(1);
        A.bits[0] = 1;
        A.updateValue();
        _A.leftShift(1);
        P.addRegister(_M);
    }

    private void add_step(){
        P.leftShift(1);
        P.bits[0] = A.bits[A.bitNumber-1];
        P.updateValue();
        A.leftShift(1);
        _A.leftShift(1);
        _A.bits[0] = 1;
        _A.updateValue();
        P.addRegister(M);
    }

    private void printHead(){
        System.out.println("| CNT |     P     |     A    |     B    |     OP     ");
        printSeparator();
    }

    private void printSeparator(){
        String res = "\n";
        for(int i = 0 ; i < 55; i++){
            res += "-";
        }
        System.out.println(res);
    }

    private void printLine(){
        System.out.println("| " + CNT.getBits() + " | " + P.getBits() + " | " + A.getBits() + " | " + B.getBits() + " |");
        System.out.print("|     |           | "+ _A.getBits() +" |          |");
    }

    @Override
    public void calculateResult() {
        printHead();
        printLine();

        //Initially we shift k-bits left
        //where k is the number of 0 bits at the beginning of B

        //find k
        int k=0;
        for(int i = B.bitNumber-1; i>=0; i--){
            if(B.bits[i] != 0){
                break;
            }
            k++;
        }

        //LSHIFT k bits
        System.out.print(" " +  k + " bits LSHIFT");
        printSeparator();
        shiftLeftChainPAB(k);
        printLine();
        printSeparator();

        for(int i = 0 ; i<cntMax ; i++){
            try {
                CNT.setBits(i);
                String OP = P.bits[P.bitNumber-1] + "" + P.bits[P.bitNumber-2] + "" + P.bits[P.bitNumber-3];
                int q_i = 0;
                switch (OP){
                    case "000":
                    case "111":
                        q_i = 0;
                        shift_step();
                        printLine();
                        System.out.print( " q_"+ i + " = "+ q_i + " ; => shift only, A[0] = 0");
                        printSeparator();
                        break;
                    case "001":
                    case "010":
                    case "011":
                        q_i = 1;
                        sub_step();
                        printLine();
                        System.out.print( " q_"+ i + " = "+ q_i + " ; => P = P-B , A[0] = 1");
                        printSeparator();
                        break;
                    case "100":
                    case "101":
                    case "110":
                        q_i = -1;
                        add_step();
                        printLine();
                        System.out.print( " q_"+ i + " = "+ q_i + " ; => P = P+B, _A[0] = 1");
                        printSeparator();
                        break;
                }

            }catch (IOException e){
                System.out.println(e);
            }
        }

        if(P.bits[P.bitNumber-1] == 1){
            System.out.println("P value is < 0, P = P+B , A = A-1");
            P.addRegister(M);
            _A.bits[0] = 1;
            _A.updateValue();
            printLine();
            printSeparator();
        }

        System.out.println(k + " bits RSHIFT P");
        P.rightLogicalShift(k);
        printLine();
        printSeparator();
        //calculate quotient value2 from A and _A
        result = A.getValue() - _A.getValue();
    }

    @Override
    public void printResult() {
        Register Q = new Register(A.bitNumber);
        try {
            Q.setBits(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Q = A - _A\nQ: " + Q.getBits() + " = " + Q.getValue() + " (quotient)");
        System.out.println("P: " + P.getBits() + " = " + P.getValue() + " (remainder)");
        System.out.println("\n" + opA + " / " + opB + " = " + result + " r " + P.getValue());
    }
}
