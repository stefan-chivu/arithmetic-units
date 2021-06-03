package com.alg;

import java.io.IOException;

import static java.lang.Math.*;

public class Register {
    public int bitNumber;
    public int[] bits;
    private int value;

    public Register(int bitNumber){
        this.bitNumber = bitNumber;
        this.bits = new int[bitNumber];
        this.reset();
    }

    public void reset(){
        for(int i = 0 ; i<bitNumber; i++){
            this.bits[i] = 0;
        }
    }

    public void setBits(int a) throws IOException {
        this.value = a;
        int logA = a>=0 ? (int) (log(abs(a)) / log(2)) : (int) (log(abs(a)) / log(2)) + 1;
        if(a<0){
            if(logA>bitNumber-1){
                //throw new IOException("Value " + a + " exceeds the "+ bitNumber + " bits of the current register (1 sgn + " + (bitNumber-1) + " val)\nPlease use only " + (bitNumber-1) +  " bit operands");
            }
        }

        int aux = a;
        int maskNo = ((int)pow(2,bitNumber)) - 1;

        for(int i = bitNumber-1 ; i>=0; i--){
            bits[i] = (aux>>i & 1);
        }
    }

    public String getBits(){
        String res = "";
        //String res = "Value " + value + ":\n";
        for(int i = bitNumber-1 ; i>=0; i--){
            res+=bits[i];
        }
        return res;
    }

    public void rightArithmeticalShift(int bitNo) throws IOException {
        this.value = this.value>>bitNo;
        this.setBits(this.value);
    }

    public void rightLogicalShift(int bitNo){
        while(bitNo-- !=0){
            for(int i = 0 ; i<bitNumber-1; i++){
                bits[i] = bits[i+1];
            }
            bits[bitNumber-1] = 0;
            this.updateValue();
        }
    }

    public void rightArithmeticalChainShift(int bitNo, Register chain) throws IOException {
        if(chain != null){
            while(bitNo-- != 0){
                chain.rightArithmeticalShift(1);
                chain.bits[chain.bitNumber-1] = this.bits[0];
                chain.updateValue();
                this.rightArithmeticalShift(1);
            }
        }
    }

    public void leftShift(int bitNo){
        this.value = (this.value<<bitNo);
        this.value = this.value % (int) Math.pow(2,this.bitNumber);
        try {
            this.setBits(this.value);
        }catch (IOException e){
            System.out.println(e);
        }
        this.updateValue();
    }

    public void updateValue(){
        int sum=0;
        for(int i = 0; i<this.bitNumber; i++){
            if(this.bits[i]==1){
                sum+=(int)pow(2,i);
            }
        }
        this.value = sum;
    }

    public void addRegister(Register R){
        this.value = this.value+R.value;
        try{
            this.setBits(this.value);
        }catch (IOException e){
            System.out.println(e);
        }
    }

    public int addRegisterCheckOVR(Register R){
        int OVR = 0;
        if((this.value + R.value > ((int)Math.pow(2,bitNumber-1))) || (this.value + R.value<(-1)*((int)Math.pow(2,bitNumber-1)))){
            OVR = 1;
            this.value = (this.value + R.value) % (int)Math.pow(2,bitNumber);
            try {
                this.setBits(this.value);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return OVR;
        }
        this.addRegister(R);
        return 0;
    }

    public int getValue(){
        return this.value;
    }
}
