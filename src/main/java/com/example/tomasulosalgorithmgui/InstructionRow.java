package com.example.tomasulosalgorithmgui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class InstructionRow {
    private final StringProperty instruction;
    private final StringProperty j;
    private final StringProperty k;
    private final StringProperty issue;
    private final StringProperty executionComplete;
    private final StringProperty writeResult;

    public InstructionRow(String instruction, String j, String k, String issue, String executionComplete, String writeResult) {
        this.instruction = new SimpleStringProperty(instruction);
        this.j = new SimpleStringProperty(j);
        this.k = new SimpleStringProperty(k);
        this.issue = new SimpleStringProperty(issue);
        this.executionComplete = new SimpleStringProperty(executionComplete);
        this.writeResult = new SimpleStringProperty(writeResult);
    }

    public StringProperty instructionProperty() {
        return instruction;
    }

    public StringProperty jProperty() {
        return j;
    }

    public StringProperty kProperty() {
        return k;
    }

    public StringProperty issueProperty() {
        return issue;
    }

    public StringProperty executionCompleteProperty() {
        return executionComplete;
    }

    public StringProperty writeResultProperty() {
        return writeResult;
    }
}
