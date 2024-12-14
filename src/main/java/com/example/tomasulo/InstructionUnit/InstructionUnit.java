package com.example.tomasulo.InstructionUnit;

public class InstructionUnit {

    private InstructionUnitSlot[] instructionUnitSlots;
    private int size;

    public InstructionUnit(int size) {
        this.size = size;
        instructionUnitSlots = new InstructionUnitSlot[size];
        for (int i = 0; i < size; i++) {
            instructionUnitSlots[i] = new InstructionUnitSlot(null, 0, 0, 0, 0, 0);
        }

    }

    public InstructionUnitSlot[] getInstructionUnitSlots() {
        return instructionUnitSlots;
    }


    public void addInstruction(InstructionUnitSlot slot) {
        for (int i = 0; i < size; i++) {
            if (instructionUnitSlots[i].getInstruction() == null) {
                instructionUnitSlots[i] = slot;
                break;
            }
        }
    }

    public InstructionUnitSlot getSlotByInstruction(Instruction instruction) {
        for (InstructionUnitSlot slot : instructionUnitSlots) {
            if (slot.getInstruction() == instruction) {
                return slot;
            }
        }
        return null;
    }

    public InstructionUnitSlot getSlotByIssueClock(int issueClock) {
        for (InstructionUnitSlot slot : instructionUnitSlots) {
            if (slot.getIssueClock() == issueClock) {
                return slot;
            }
        }
        return null;
    }
}
