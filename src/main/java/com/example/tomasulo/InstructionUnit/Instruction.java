package com.example.tomasulo.InstructionUnit;

public class Instruction {

    String operation;
    String destination;
    String source1;
    String source2;



    public Instruction(String operation, String destination, String source1, String source2) {
        this.operation = operation;
        this.destination = destination;
        this.source1 = source1;
        this.source2 = source2;
    }

    public String getOperation() {
        return operation;
    }

    public String getDestination() {
        return destination;
    }

    public String getSource1() {
        return source1;
    }

    public String getSource2() {
        return source2;
    }

    public String toString() {

        if(source2 == null) {
            return operation + " " + destination + ", " + source1;
        } else {
            return operation + " " + destination + ", " + source1 + ", " + source2;
        }
    }


    public boolean isAddOrSub() {
        return operation.equals("ADD.D") || operation.equals("ADD.S") || operation.equals("SUB.D") || operation.equals("SUB.S");
    }

    public boolean isMulOrDiv() {
        return operation.equals("MUL.D") || operation.equals("MUL.S") || operation.equals("DIV.D") || operation.equals("DIV.S");
    }

    public boolean isMul(){
        return operation.equals("MUL.D") || operation.equals("MUL.S");
    }

    public boolean isDiv(){
        return operation.equals("DIV.D") || operation.equals("DIV.S");
    }

    public boolean isLoad() {
        return operation.equals("L.D") || operation.equals("L.S") || operation.equals("LW") || operation.equals("LD");
    }

    public boolean isStore() {
        return operation.equals("S.D") || operation.equals("S.S") || operation.equals("SW") || operation.equals("SD");
    }

    public boolean isBranch() {
        return operation.equals("BEQ") || operation.equals("BNE");
    }

    public boolean isImmediate() {
        return operation.equals("DSUBI") || operation.equals("DADDI");
    }



    public String execute() {
        String result = "";
        switch (operation) {
            case "ADD.D":
            case "ADD.S":
            case "DADDI":
                result +=  Integer.parseInt(source1) + Integer.parseInt(source2); break;
            case "SUB.D":
            case "SUB.S":
            case "DSUBI":
                result +=  Integer.parseInt(source1) - Integer.parseInt(source2); break;
            case "MUL.D":
            case "MUL.S":
                result +=  Integer.parseInt(source1) * Integer.parseInt(source2); break;
            case "DIV.D":
            case "DIV.S":
                result +=  Integer.parseInt(source1) / Integer.parseInt(source2); break;

        }
        return result;
    }


}
