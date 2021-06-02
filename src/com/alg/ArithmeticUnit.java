package com.alg;

public abstract class ArithmeticUnit {
    protected int opA;
    protected int opB;
    protected int result;
    protected String type;

    public ArithmeticUnit(){

    }

    public ArithmeticUnit(int opA, int opB){
        this.opA = opA;
        this.opB = opB;
        this.type = this.getClass().getSimpleName();
    }

    public void setOpA(int opA){
        this.opA = opA;
    }

    public void setOpB(int opB){
        this.opB = opB;
    }

    public abstract void calculateResult();

    public abstract void printResult();
}
