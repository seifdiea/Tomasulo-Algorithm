package com.example.tomasulo.Simulator;

import com.example.tomasulo.Buffers.LoadBuffer;
import com.example.tomasulo.Buffers.LoadBufferSlot;
import com.example.tomasulo.Buffers.StoreBuffer;
import com.example.tomasulo.Buffers.StoreBufferSlot;
import com.example.tomasulo.ExecutionQueue.ExecutionResult;
import com.example.tomasulo.InstructionUnit.Instruction;
import com.example.tomasulo.InstructionUnit.InstructionMemory;
import com.example.tomasulo.InstructionUnit.InstructionUnit;
import com.example.tomasulo.InstructionUnit.InstructionUnitSlot;
import com.example.tomasulo.Memory.CacheMemory;
import com.example.tomasulo.Memory.Memory;
import com.example.tomasulo.RegisterFile.Register;
import com.example.tomasulo.RegisterFile.RegisterFile;
import com.example.tomasulo.ReservationStations.*;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class TomasuloSimulator {

    public InstructionMemory instructionMemory;
    private RegisterFile registerFile;
    private AdderReservationStation adderRS;
    private MultiplierReservationStation multiplierRS;
    private LoadBuffer loadBuffer;
    private StoreBuffer storeBuffer;
    private int blockSize;
    private CacheMemory cacheMemory;
    private static boolean isFirstCacheAccess = true;
    private Memory memory;
    private ImmediateReservationStation immediateReservationStation;
    public InstructionUnit instructionUnit;

    public int FPMultiplierLatency;
    public int FPLoadLatency;
    public int FPStoreLatency;
    public int FPAdderLatency;
    public int FPSubtractorLatency;
    public int FPDividerLatency;

    public static int currentClockCycle = 0;

    public int IntAdderLatency;
    public int IntSubtractorLatency;


    public int branchLatency;
    public int immediateLatency = 1;
    
    


    public static Queue<ExecutionResult> results = new LinkedList<>();

    public TomasuloSimulator(int InstructionMemorySize,int InstructionUnitSize ,int RegisterFileSize, int AdderRSSize,
                             int MultiplierRSSize, int LoadBufferSize, int StoreBufferSize,
                             int cacheSize, int blockSize, int FPMultiplierLatency, int FPLoadLatency, int FPStoreLatency
                             , int FPAdderLatency, int FPSubtractorLatency, int FPDividerLatency,
                             int IntAdderLatency, int IntSubtractorLatency, int branchLatency) {

        memory = new Memory(cacheSize);
        memory.readMemoryFromFile("src/main/java/com/example/tomasulo/memory.txt");

        instructionMemory = new InstructionMemory(InstructionMemorySize);
        instructionMemory.readInstructionsFromFile("src/main/java/com/example/tomasulo/instructions.txt");

        registerFile = new RegisterFile(RegisterFileSize);

        adderRS = new AdderReservationStation(AdderRSSize);
        multiplierRS = new MultiplierReservationStation(MultiplierRSSize);

        loadBuffer = new LoadBuffer(LoadBufferSize);
        storeBuffer = new StoreBuffer(StoreBufferSize);

        this.blockSize = blockSize;
        this.cacheMemory = new CacheMemory(memory, blockSize, cacheSize);

        instructionUnit = new InstructionUnit(InstructionUnitSize);

        immediateReservationStation = new ImmediateReservationStation();


        this.FPMultiplierLatency = FPMultiplierLatency;
        this.FPLoadLatency = FPLoadLatency;
        this.FPStoreLatency = FPStoreLatency;
        this.FPAdderLatency = FPAdderLatency;
        this.FPSubtractorLatency = FPSubtractorLatency;
        this.FPDividerLatency = FPDividerLatency;
        this.IntAdderLatency = IntAdderLatency;
        this.IntSubtractorLatency = IntSubtractorLatency;
        this.branchLatency = branchLatency;

    }

    public void issueLoadInstruction(int latency){

        Instruction currentInstruction = instructionMemory.getCurrentInstruction();

        LoadBufferSlot slot = loadBuffer.getFreeSlot();
        if(slot!=null){
            slot.setBusy(true);
            slot.setCycle(latency);
            if(currentInstruction.getSource1().matches("^[0-9].*"))
                slot.setAddress(currentInstruction.getSource1());
            else
                slot.setAddress(registerFile.getIntegerRegister(currentInstruction.getSource1()).getValue());
            slot.setInstruction(currentInstruction);
            slot.setExecuting(false);

            if(cacheMemory.isLoaded()){
                slot.setCycle(1);
            }
            else{
                slot.setCycle(latency);
            }

            registerFile.setRegisterQi(currentInstruction.getDestination(), slot.getTag());

            if(cacheMemory.isLoaded()){
                addToInstructionUnit(currentInstruction, 1);
            }
            else{
                addToInstructionUnit(currentInstruction, latency);
            }

            instructionMemory.incrementCurrentAddress();
        }

    }

    public void issueStoreInstruction(int latency){

        Instruction currentInstruction = instructionMemory.getCurrentInstruction();

        StoreBufferSlot slot = storeBuffer.getFreeSlot();
        if(slot!=null){
            slot.setBusy(true);
            slot.setCycle(latency);
            if(currentInstruction.getSource1().matches("^[0-9].*"))
                slot.setAddress(currentInstruction.getSource1());
            else
                slot.setAddress(registerFile.getIntegerRegister(currentInstruction.getSource1()).getValue());
            slot.setInstruction(currentInstruction);
            slot.setExecuting(false);

            if(cacheMemory.isLoaded()){
                slot.setCycle(1);
            }
            else{
                slot.setCycle(latency);
            }

            Register r1 = registerFile.getFloatingRegister(currentInstruction.getDestination());
            if(r1.getQi().equals("0")){
                slot.setV(r1.getValue());
            }
            else{
                slot.setQ(r1.getQi());
            }

            if(cacheMemory.isLoaded()){
                addToInstructionUnit(currentInstruction, 1);
            }
            else{
                addToInstructionUnit(currentInstruction, latency);
            }


            instructionMemory.incrementCurrentAddress();
        }

    }

    public void issueAddOrSubInstruction(int latency){
        Instruction currentInstruction = instructionMemory.getCurrentInstruction();
        AdderRsSlot slot = adderRS.getFreeSlot();
        if(slot != null) {
            slot.setInstruction(currentInstruction);
            slot.setBusy(true);
            slot.setCycle(latency);
            slot.setOperation(currentInstruction.getOperation());
            slot.setIssueClockCycle(currentClockCycle);

            Register r1 = registerFile.getFloatingRegister(currentInstruction.getSource1());
            Register r2 = registerFile.getFloatingRegister(currentInstruction.getSource2());
            registerFile.setRegisterQi(currentInstruction.getDestination(), slot.getTag());

            if(r1.getQi().equals("0")){
                slot.setVj(r1.getValue());
            }
            else{
                slot.setQj(r1.getQi());
            }

            if(r2.getQi().equals("0")){
                slot.setVk(r2.getValue());
            }
            else{
                slot.setQk(r2.getQi());
            }
            addToInstructionUnit(currentInstruction, FPAdderLatency);

            instructionMemory.incrementCurrentAddress();
        }
    }

    public void issueMulDivInstruction(int latency){
        Instruction currentInstruction = instructionMemory.getCurrentInstruction();
        MultiplierRsSlot slot = multiplierRS.getFreeSlot();
        if(slot != null) {
            slot.setInstruction(currentInstruction);
            slot.setBusy(true);
            slot.setCycle(latency);
            slot.setOperation(currentInstruction.getOperation());
            slot.setIssueClockCycle(currentClockCycle);
            Register r1 = registerFile.getFloatingRegister(currentInstruction.getSource1());
            Register r2 = registerFile.getFloatingRegister(currentInstruction.getSource2());
            registerFile.setRegisterQi(currentInstruction.getDestination(), slot.getTag());

            if(r1.getQi().equals("0")){
                slot.setVj(r1.getValue());
            }
            else{
                slot.setQj(r1.getQi());
            }

            if(r2.getQi().equals("0")){
                slot.setVk(r2.getValue());
            }
            else{
                slot.setQk(r2.getQi());
            }
            addToInstructionUnit(currentInstruction, latency);

            instructionMemory.incrementCurrentAddress();
        }
    }

    public void addToInstructionUnit(Instruction instruction, int Latency){
        InstructionUnitSlot slot = new InstructionUnitSlot(instruction, Latency, currentClockCycle, 0, 0, 0);
        instructionUnit.addInstruction(slot);
    }


    public void issueCurrentInstruction(){

        Instruction currentInstruction = instructionMemory.getCurrentInstruction();
        System.out.println("CURRENT INSTRUCTION: " + currentInstruction);

        if(currentInstruction == null) return;

        if(currentInstruction.isAddOrSub()){ // assuming add and sub have the same latency
            issueAddOrSubInstruction(FPAdderLatency);

        }

        if(currentInstruction.isMul()){
            issueMulDivInstruction(FPMultiplierLatency);

        }

        if (currentInstruction.isDiv()){
            issueMulDivInstruction(FPDividerLatency);

        }

        if(currentInstruction.isLoad()){
            issueLoadInstruction(FPLoadLatency);

        }

        if(currentInstruction.isStore()){
            issueStoreInstruction(FPStoreLatency);
        }

        if(currentInstruction.isBranch() || currentInstruction.isImmediate()) {
            executeNonTomasuloInstruction();
        }
    }

    public void executeNonTomasuloInstruction(){
        switch (instructionMemory.getCurrentInstruction().getOperation()){
            case "BEQ":
                if(registerFile.getIntegerRegister(instructionMemory.getCurrentInstruction().getDestination()).getValue()
                        .equals(registerFile.getIntegerRegister(instructionMemory.getCurrentInstruction().getSource1()).getValue()))
                {
                    instructionMemory.jumpTo(Integer.parseInt(instructionMemory.getCurrentInstruction().getSource2()));
                    System.out.println("JUMPING TO: " + instructionMemory.getCurrentInstruction());

                }
                break;
            case "BNE":
                if(!(registerFile.getIntegerRegister(instructionMemory.getCurrentInstruction().getDestination()).getValue()
                        .equals(registerFile.getIntegerRegister(instructionMemory.getCurrentInstruction().getSource1()).getValue())))
                {
                    instructionMemory.jumpTo(Integer.parseInt(instructionMemory.getCurrentInstruction().getSource2()));
                    System.out.println("JUMPING TO: " + instructionMemory.getCurrentInstruction());
                }
                break;
            case "DADDI":
                registerFile.setIntegerRegisterValue(instructionMemory.getCurrentInstruction().getDestination(),
                        String.valueOf((Integer.parseInt(registerFile.getIntegerRegister(instructionMemory.getCurrentInstruction().getSource1()).getValue())
                                + Integer.parseInt(instructionMemory.getCurrentInstruction().getSource2()))));
                instructionMemory.incrementCurrentAddress();
                break;
            case "DSUBI":
                registerFile.setIntegerRegisterValue(instructionMemory.getCurrentInstruction().getDestination(),
                        String.valueOf((Integer.parseInt(registerFile.getIntegerRegister(instructionMemory.getCurrentInstruction().getSource1()).getValue())
                                - Integer.parseInt(instructionMemory.getCurrentInstruction().getSource2()))));
                instructionMemory.incrementCurrentAddress();
                break;

        }

    }

    public void executeLoadOrStoreInstruction(){
        System.out.println(loadBuffer.isExecuting());
        System.out.println(storeBuffer.isExecuting());
        if(loadBuffer.isExecuting() || storeBuffer.isExecuting()){
            if(loadBuffer.isExecuting()){
                LoadBufferSlot slot = loadBuffer.getExecutingSlot();
                if(slot.getCycles() == 0){
                    switch(slot.getInstruction().getOperation()){
                        case "LW": case "LD":
                            registerFile.setIntegerRegisterValue(slot.getInstruction().getDestination(), ""+ memory.getAddress(Integer.parseInt(slot.getInstruction().getSource1())));
                            InstructionUnitSlot s1 = instructionUnit.getSlotByInstruction(slot.getInstruction());
                            s1.setFinishExecutionClock(currentClockCycle);
                            s1.setWriteResultClock(currentClockCycle+1);
                            isFirstCacheAccess = false;
                            break;
                        case "L.D": case "L.S":

                           // ExecutionResult r = new ExecutionResult(""+ memory.getAddress(Integer.parseInt(slot.getInstruction().getSource1())), currentClockCycle, slot.getTag());
                            ExecutionResult r = new ExecutionResult(""+ memory.getAddress(Integer.parseInt(slot.getAddress())) , currentClockCycle, slot.getTag());
                            InstructionUnitSlot s2 = instructionUnit.getSlotByInstruction(slot.getInstruction());
                            s2.setFinishExecutionClock(currentClockCycle);
                            s2.setWriteResultClock(currentClockCycle+1);
                            System.out.println(r);
                            results.add(r);
                            isFirstCacheAccess = false;
                            break;
                    }

                }
            }
            else{
                StoreBufferSlot slot = storeBuffer.getExecutingSlot();
                System.out.println(slot);
                if(slot.getCycles() == 0){
                    switch(slot.getInstruction().getOperation()){
                        case "SW": case "SD":
                            memory.setAddress(Integer.parseInt(slot.getAddress()), Float.parseFloat(slot.getV()));
                            InstructionUnitSlot s1 = instructionUnit.getSlotByInstruction(slot.getInstruction());
                            s1.setFinishExecutionClock(currentClockCycle);
                            s1.setWriteResultClock(currentClockCycle+1);
                            cacheMemory.setValueByAddress(Integer.parseInt(slot.getAddress()), Float.parseFloat(slot.getV()));
                            isFirstCacheAccess = false;
                            break;
                        case "S.D": case "S.W":
                            ExecutionResult r = new ExecutionResult(""+ memory.getAddress(Integer.parseInt(slot.getAddress())), currentClockCycle, slot.getTag());
                            memory.setAddress(Integer.parseInt(slot.getAddress()), Float.parseFloat(slot.getV()));
                            InstructionUnitSlot s2 = instructionUnit.getSlotByInstruction(slot.getInstruction());
                            s2.setFinishExecutionClock(currentClockCycle);
                            s2.setWriteResultClock(currentClockCycle+1);
                            cacheMemory.setValueByAddress(Integer.parseInt(slot.getAddress()), Float.parseFloat(slot.getV()));
                            results.add(r);
                            isFirstCacheAccess = false;
                            break;
                    }
                }
            }
        }
        else{
            LoadBufferSlot slot = loadBuffer.getEarliestIssueTime();
            StoreBufferSlot slot2 = storeBuffer.getEarliestIssueTime();

            System.out.println("LOAD BUFFERRRRRRRRRRRRRRRRRRRRRR: " + slot);
            System.out.println("STORE BUFFERRRRRRRRRRRRRRRRRRRRRR: " + slot2);

            if(slot != null && slot2 != null && slot.isBusy() && slot2.isBusy()){
                if(slot.getIssueClockCycle()< slot2.getIssueClockCycle()){
                    slot.setExecuting(true);
                }
                else{
                    slot2.setExecuting(true);
                }
                if(isFirstCacheAccess){
                    cacheMemory.loadValuesFromMemory();
                    isFirstCacheAccess = false;
                }
            }
            else if(slot != null && slot.isBusy()){
                slot.setExecuting(true);
                if(isFirstCacheAccess){
                    cacheMemory.loadValuesFromMemory();
                    isFirstCacheAccess = false;
                }
            }
            else if(slot2 != null && slot2.isBusy()){
                slot2.setExecuting(true);
                if(isFirstCacheAccess){
                    cacheMemory.loadValuesFromMemory();
                    isFirstCacheAccess = false;
                }
            }

        }

    }

    public void executeCurrentInstruction(){
        PriorityQueue<ExecutionResult> resultsCombined = new PriorityQueue<>();
        executeLoadOrStoreInstruction();
        PriorityQueue<ExecutionResult> adderRSResults = adderRS.execute();
        PriorityQueue<ExecutionResult> multRSResults = multiplierRS.execute();

        if(adderRSResults != null){
            resultsCombined.addAll(adderRSResults);
        }
        if(multRSResults != null){
            resultsCombined.addAll(multRSResults);
        }
        //add the finishing time to the instruction unit
        for(ExecutionResult er : resultsCombined){
            InstructionUnitSlot slot = instructionUnit.getSlotByIssueClock(er.getIssuedClockCycles());
            slot.setFinishExecutionClock(currentClockCycle);
            slot.setWriteResultClock(currentClockCycle+1);
        }

        if(!resultsCombined.isEmpty()){
            results.addAll(resultsCombined);
        }


    }

    public void broadcast(){
        ExecutionResult er;
        if(!results.isEmpty()){
            er = results.poll();
            System.out.println("BROADCASTING: " + er.getTag() + " " + er.getResult());
            String tag = er.getTag();
            String result = er.getResult();
            adderRS.broadcast(tag, result);
            multiplierRS.broadcast(tag, result);
            storeBuffer.broadcast(tag, result);
            registerFile.broadcast(tag, result);
        }
    }

    public void decrementCycles(){
        adderRS.decrementCycles();
        multiplierRS.decrementCycles();
        loadBuffer.decrementCycles();
        storeBuffer.decrementCycles();
        immediateReservationStation.decrementCycles();
    }

    public void step(){
        //broadcast
        broadcast();
        executeCurrentInstruction();
        //decrementing after execution starts
        decrementCycles();

        issueCurrentInstruction();


        currentClockCycle++;
    }

    public AdderReservationStation getAdderRS() {
        return adderRS;
    }

    public MultiplierReservationStation getMultiplierRS() {
        return multiplierRS;
    }


    public static void main(String[] args) {



        // Define sizes and latencies
        int instructionMemorySize = 10;
        int registerFileSize = 12;
        int adderRSSize = 3;
        int multiplierRSSize = 2;
        int loadBufferSize = 3;
        int storeBufferSize = 2;
        int cacheSize = 64;
        int blockSize = 4;
        int fpMultiplierLatency = 10;
        int fpLoadLatency = 4;
        int fpStoreLatency = 2;
        int fpAdderLatency = 2;
        int fpSubtractorLatency = 2;
        int fpDividerLatency = 40;
        int intAdderLatency = 1;
        int intSubtractorLatency = 1;
        int branchLatency = 2;
        int InstructionUnitSize = 20;

        // Create TomasuloSimulator instance
        TomasuloSimulator simulator = new TomasuloSimulator(
                instructionMemorySize, InstructionUnitSize,registerFileSize, adderRSSize, multiplierRSSize,
                loadBufferSize, storeBufferSize, cacheSize, blockSize,
                fpMultiplierLatency, fpLoadLatency, fpStoreLatency, fpAdderLatency,
                fpSubtractorLatency, fpDividerLatency, intAdderLatency,
                intSubtractorLatency, branchLatency
        );

        //initialize register file with random values
        for(int i = 0; i < registerFileSize; i++){
            simulator.registerFile.setFloatingRegisterValue("F"+i, ""+(int)(Math.random()*100));
        }

        for(int i = 0; i < 20; i++){
            simulator.step();
            System.out.println(simulator.instructionMemory.toString());

            System.out.println(simulator.registerFile.toString());
            System.out.println(simulator.storeBuffer.toString());
            System.out.println(simulator.loadBuffer.toString());
            System.out.println(simulator.adderRS.toString());
            System.out.println(simulator.multiplierRS.toString());
            System.out.println(simulator.immediateReservationStation.toString());
            System.out.println("-------------------------------------------------");
            System.out.println(results);

            System.out.println("-------------------------------------------------");

        }



    }


    public RegisterFile getRegisterFile() {
        return registerFile;
    }

    public LoadBuffer getLoadBuffer() {
        return loadBuffer;
    }

    public StoreBuffer getStoreBuffer(){
        return storeBuffer;
    }

    public CacheMemory getCacheMemory() {
        return cacheMemory;
    }

    public InstructionUnit getInstructionUnit() {
        return instructionUnit;
    }

    public int getCurrentClockCycle() {
        return currentClockCycle;
    }
}
