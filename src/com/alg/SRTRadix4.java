package com.alg;

import java.io.IOException;

public class SRTRadix4 extends ArithmeticUnit {
    Register P;
    Register A;
    Register _A;
    Register B;
    Register M;
    Register M2;
    Register _M;
    Register _M2;
    Register CNT;

    String b;
    int bInt;

    int cntMax;

    public SRTRadix4(int operandBits, int opA, int opB){
        this.opA = opA;
        this.opB = opB;

        P = new Register(operandBits+1);
        A = new Register(operandBits);
        _A = new Register(operandBits);
        B = new Register(operandBits);
        M = new Register(operandBits+1);
        M2 = new Register(operandBits+1);
        _M = new Register(operandBits+1);
        _M2 = new Register(operandBits+1);

        CNT = new Register(2);
        cntMax = 4;

        try {
            if(opA < 0 || opB<=0){
                throw new IOException("Only positive integers are allowed for SRT-2 divider");
            }
            A.setBits(opA);
            B.setBits(opB);
            M.setBits(opB);
            M2.setBits(2*opB);
            _M.setBits((-1)*(opB));
            _M2.setBits((-2)*(opB));
        }catch (IOException e){
            System.out.println(e);
        }
    }

    private void shiftLeftChainPAB(int bitNo){
        M.leftShift(bitNo);
        M2.leftShift(bitNo);
        _M.leftShift(bitNo);
        _M2.leftShift(bitNo);

        b = M.getBits().substring(1,5);

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
        System.out.print("|    |           | "+ _A.getBits() +" |          |");
    }

    public void solveForBValue(){
        bInt = 0;
        StringBuilder sb = new StringBuilder();
        sb.append(b);
        sb.reverse();
        String aux = sb.toString();
        for(int i = 0; i<4; i++){
            bInt += (int) Math.pow(2,i) * Integer.parseInt(aux.substring(i,i+1));
        }
        System.out.println("b: " + b + " = " + bInt);
    }

    public int getOP() {
        int pValue = convertPfirst6bits(P.getBits().substring(0, 6));

        try {
            switch (bInt) {
                case 8:
                    switch (pValue) {
                        case -12:
                        case -11:
                        case -10:
                        case -9:
                        case -8:
                        case -7:
                            return -2;
                        case -6:
                        case -5:
                        case -4:
                        case -3:
                            return -1;
                        case -2:
                        case -1:
                        case 0:
                        case 1:
                            return 0;
                        case 2:
                        case 3:
                        case 4:
                        case 5:
                            return 1;
                        case 6:
                        case 7:
                        case 8:
                        case 9:
                        case 10:
                        case 11:
                            return 2;
                    }
                    break;
                case 9:
                    switch (pValue) {
                        case -14:
                        case -13:
                        case -12:
                        case -11:
                        case -10:
                        case -9:
                        case -8:
                            return -2;
                        case -7:
                        case -6:
                        case -5:
                        case -4:
                            return -1;
                        // -3 aici era duplicat si pt -1 si 0
                        case -3:
                        case -2:
                        case -1:
                        case 0:
                        case 1:
                        case 2:
                            return 0;
                        case 3:
                        case 4:
                        case 5:
                        case 6:
                            return 1;
                        case 7:
                        case 8:
                        case 9:
                        case 10:
                        case 11:
                        case 12:
                        case 13:
                            return 2;
                    }
                    break;
                case 10:
                    switch (pValue) {
                        case -15:
                        case -14:
                        case -13:
                        case -12:
                        case -11:
                        case -10:
                        case -9:
                            return -2;
                        case -8:
                        case -7:
                        case -6:
                        case -5:
                        case -4:
                            return -1;
                        // -3 aici era duplicat
                        case -3:
                        case -2:
                        case -1:
                        case 0:
                        case 1:
                        case 2:
                            return 0;
                        case 3:
                        case 4:
                        case 5:
                        case 6:
                        case 7:
                            return 1;
                        case 8:
                        case 9:
                        case 10:
                        case 11:
                        case 12:
                        case 13:
                        case 14:
                            return 2;
                    }
                    break;
                case 11:
                    switch (pValue) {
                        case -16:
                        case -15:
                        case -14:
                        case -13:
                        case -12:
                        case -11:
                        case -10:
                            return -2;
                        case -9:
                        case -8:
                        case -7:
                        case -6:
                        case -5:
                        case -4:
                            return -1;
                        case -3:
                        case -2:
                        case -1:
                        case 0:
                        case 1:
                        case 2:
                            return 0;
                        case 3:
                        case 4:
                        case 5:
                        case 6:
                        case 7:
                        case 8:
                            return 1;
                        case 9:
                        case 10:
                        case 11:
                        case 12:
                        case 13:
                        case 14:
                        case 15:
                            return 2;
                    }
                    break;
                case 12:
                    switch (pValue) {
                        case -18:
                        case -17:
                        case -16:
                        case -15:
                        case -14:
                        case -13:
                        case -12:
                        case -11:
                            return -2;
                        case -10:
                        case -9:
                        case -8:
                        case -7:
                        case -6:
                        case -5:
                            return -1;
                        case -4:
                        case -3:
                        case -2:
                        case -1:
                        case 0:
                        case 1:
                        case 2:
                        case 3:
                            return 0;
                        case 4:
                        case 5:
                        case 6:
                        case 7:
                        case 8:
                        case 9:
                            return 1;
                        case 10:
                        case 11:
                        case 12:
                        case 13:
                        case 14:
                        case 15:
                        case 16:
                        case 17:
                            return 2;
                    }
                    break;
                case 13:
                    switch (pValue) {
                        case -19:
                        case -18:
                        case -17:
                        case -16:
                        case -15:
                        case -14:
                        case -13:
                        case -12:
                        case -11:
                            return -2;
                        case -10:
                        case -9:
                        case -8:
                        case -7:
                        case -6:
                        case -5:
                            return -1;
                        case -4:
                        case -3:
                        case -2:
                        case -1:
                        case 0:
                        case 1:
                        case 2:
                        case 3:
                            return 0;
                        case 4:
                        case 5:
                        case 6:
                        case 7:
                        case 8:
                        case 9:
                            return 1;
                        case 10:
                        case 11:
                        case 12:
                        case 13:
                        case 14:
                        case 15:
                        case 16:
                        case 17:
                        case 18:
                            return 2;
                    }
                    break;
                case 14:
                    switch (pValue) {
                        case -20:
                        case -19:
                        case -18:
                        case -17:
                        case -16:
                        case -15:
                        case -14:
                        case -13:
                        case -12:
                            return -2;
                        case -11:
                        case -10:
                        case -9:
                        case -8:
                        case -7:
                        case -6:
                        case -5:
                            return -1;
                        case -4:
                        case -3:
                        case -2:
                        case -1:
                        case 0:
                        case 1:
                        case 2:
                        case 3:
                            return 0;
                        case 4:
                        case 5:
                        case 6:
                        case 7:
                        case 8:
                        case 9:
                        case 10:
                            return 1;
                        case 11:
                        case 12:
                        case 13:
                        case 14:
                        case 15:
                        case 16:
                        case 17:
                        case 18:
                        case 19:
                            return 2;
                    }
                    break;
                case 15:
                    switch (pValue) {
                        case -22:
                        case -21:
                        case -20:
                        case -19:
                        case -18:
                        case -17:
                        case -16:
                        case -15:
                        case -14:
                        case -13:
                            return -2;
                        case -12:
                        case -11:
                        case -10:
                        case -9:
                        case -8:
                        case -7:
                        case -6:
                            return -1;
                        case -5:
                        case -4:
                        case -3:
                        case -2:
                        case -1:
                        case 0:
                        case 1:
                        case 2:
                        case 3:
                        case 4:
                            return 0;
                        case 5:
                        case 6:
                        case 7:
                        case 8:
                        case 9:
                        case 10:
                        case 11:
                            return 1;
                        case 12:
                        case 13:
                        case 14:
                        case 15:
                        case 16:
                        case 17:
                        case 18:
                        case 19:
                        case 20:
                        case 21:
                            return 2;
                    }
                    break;
            }
            throw new Exception("Ceva s-a bulit");
        }catch (Exception e){
            System.out.println(e);
        }
        return 3;
    }

    private void shiftOnly(){
        P.leftShift(2);
        P.bits[1] = A.bits[A.bitNumber-1];
        P.bits[0] = A.bits[A.bitNumber-2];
        P.updateValue();

        A.leftShift(2);
        _A.leftShift(2);
        System.out.print("\n");
    }

    private void addM1(){
        P.leftShift(2);
        P.bits[1] = A.bits[A.bitNumber-1];
        P.bits[0] = A.bits[A.bitNumber-2];
        P.updateValue();
        A.leftShift(2);
        _A.leftShift(2);
        _A.bits[1] = 0;
        _A.bits[0] = 1;
        _A.updateValue();

        System.out.print("\n");
        printLine();
        System.out.print("\n|    |+"+ M.getBits() +" |          |          |\n");
        System.out.print("|    |___________|          |          |\n");

        P.addRegister(M);


    }

    private void addM2(){
        P.leftShift(2);
        P.bits[1] = A.bits[A.bitNumber-1];
        P.bits[0] = A.bits[A.bitNumber-2];
        P.updateValue();
        A.leftShift(2);
        _A.leftShift(2);
        _A.bits[1] = 1;
        _A.bits[0] = 0;
        _A.updateValue();

        System.out.print("\n");
        printLine();
        System.out.print("\n");
        System.out.print("|    |+"+ M2.getBits() +" |          |          |\n");
        System.out.print("|    |___________|          |          |\n");

        P.addRegister(M2);
    }

    private void add_M1(){
        P.leftShift(2);
        P.bits[1] = A.bits[A.bitNumber-1];
        P.bits[0] = A.bits[A.bitNumber-2];
        P.updateValue();
        A.leftShift(2);
        _A.leftShift(2);
        A.bits[1] = 0;
        A.bits[0] = 1;
        A.updateValue();

        System.out.print("\n");
        printLine();
        System.out.print("\n");
        System.out.print("|    |+"+ _M.getBits() +" |          |          |\n");
        System.out.print("|    |___________|          |          |\n");

        P.addRegister(_M);
    }

    private void add_M2(){
        P.leftShift(2);
        P.bits[1] = A.bits[A.bitNumber-1];
        P.bits[0] = A.bits[A.bitNumber-2];
        P.updateValue();
        A.leftShift(2);
        _A.leftShift(2);
        A.bits[1] = 1;
        A.bits[0] = 0;
        A.updateValue();

        System.out.print("\n");
        printLine();
        System.out.print("\n");
        System.out.print("|    |+"+ _M2.getBits() +" |          |          |\n");
        System.out.print("|    |___________|          |          |\n");

        P.addRegister(_M2);
    }

    @Override
    public void calculateResult() {
        int OP;

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
        solveForBValue();

        for(int i = 0; i<cntMax; i++){
            try{
            CNT.setBits(i);
            OP = getOP();
            switch (OP){
                case 2:
                    printLine();
                    System.out.print(" q_"+i+" = "+ OP + " => shift then P = P-2B");
                    add_M2();
                    printLine();
                    printSeparator();
                    break;
                case 1:
                    printLine();
                    System.out.print(" q_"+i+" = "+ OP + " => shift then P = P-B");
                    add_M1();
                    printLine();
                    printSeparator();
                    break;
                case 0:
                    printLine();
                    System.out.print(" q_"+i+" = "+ OP + " => shift only");
                    shiftOnly();
                    printLine();
                    printSeparator();
                    break;
                case -1:
                    printLine();
                    System.out.print(" q_"+i+" = "+ OP + " => shift then P = P+B");
                    addM1();
                    printLine();
                    printSeparator();
                    break;
                case -2:
                    printLine();
                    System.out.print(" q_"+i+" = "+ OP + " => shift then P = P+2B");
                    addM2();
                    printLine();
                    printSeparator();
                    break;
            }
            if(OP == 3){
                throw new Exception("Check switches  :(   ");
            }
            }catch (Exception e){
                System.out.println(e);
            }
        }

        if(P.bits[P.bitNumber-1] == 1){
            System.out.println("CORRECTION STEP:    P value is < 0, P = P+B , A = A-1");
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

    public int convertPfirst6bits(String bits){
        int result = 0;
        boolean isNegative = false;
        int[] bitArray = new int[6];
        Register aux = new Register(6);

        for(int i = 0 ; i<6; i++){
            bitArray[i] = Integer.parseInt(bits.substring(5-i,6-i));
            aux.bits[i] = bitArray[i];
        }

        if(bitArray[5] == 1){
            isNegative = true;
            //try {
            //    aux.setBits(aux.getValue()-1);
            //} catch (IOException e) {
            //    e.printStackTrace();
            //}
            for(int i = 0; i<6; i++){
                if(aux.bits[i] == 0){
                    aux.bits[i] = 1;
                }else{
                    aux.bits[i] = 0;
                }
            }
            aux.updateValue();
            return (aux.getValue()+1)*(-1);
        }

        for(int i = 0; i<6 ; i++){
            result += ((int) Math.pow(2,i)) * bitArray[i];
        }

        return result;
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
