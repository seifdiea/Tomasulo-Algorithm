package com.example.tomasulosalgorithmgui;

import com.example.tomasulo.Simulator.TomasuloSimulator;
import com.example.tomasulo.RegisterFile.RegisterFile;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import com.example.tomasulo.Buffers.LoadBuffer;
import com.example.tomasulo.Buffers.LoadBufferSlot;
import com.example.tomasulo.Buffers.StoreBuffer;
import com.example.tomasulo.Buffers.StoreBufferSlot;

import com.example.tomasulo.Memory.CacheMemory;
import com.example.tomasulo.InstructionUnit.InstructionUnit;
import com.example.tomasulo.InstructionUnit.InstructionUnitSlot;




public class HelloController {
    // Adder Reservation Station Table
    @FXML private TableView<ReservationStationRow> adderRsTable;
    @FXML private TableColumn<ReservationStationRow, String> adderCyclesColumn;
    @FXML private TableColumn<ReservationStationRow, String> adderTagColumn;
    @FXML private TableColumn<ReservationStationRow, String> adderBusyColumn;
    @FXML private TableColumn<ReservationStationRow, String> adderOperationColumn;
    @FXML private TableColumn<ReservationStationRow, String> adderVjColumn;
    @FXML private TableColumn<ReservationStationRow, String> adderVkColumn;
    @FXML private TableColumn<ReservationStationRow, String> adderQjColumn;
    @FXML private TableColumn<ReservationStationRow, String> adderQkColumn;
    @FXML private TableColumn<ReservationStationRow, String> adderAddressColumn;

    // Multiplier Reservation Station Table
    @FXML private TableView<ReservationStationRow> multRsTable;
    @FXML private TableColumn<ReservationStationRow, String> multCyclesColumn;
    @FXML private TableColumn<ReservationStationRow, String> multTagColumn;
    @FXML private TableColumn<ReservationStationRow, String> multBusyColumn;
    @FXML private TableColumn<ReservationStationRow, String> multOperationColumn;
    @FXML private TableColumn<ReservationStationRow, String> multVjColumn;
    @FXML private TableColumn<ReservationStationRow, String> multVkColumn;
    @FXML private TableColumn<ReservationStationRow, String> multQjColumn;
    @FXML private TableColumn<ReservationStationRow, String> multQkColumn;
    @FXML private TableColumn<ReservationStationRow, String> multAddressColumn;
    @FXML private Label clockCycleLabel;

    @FXML private TableView<RegisterRow> registerFileTable;
    @FXML private TableColumn<RegisterRow, String> tagColumn;
    @FXML private TableColumn<RegisterRow, String> qiColumn;
    @FXML private TableColumn<RegisterRow, String> valueColumn;

    @FXML private TableView<LoadBufferRow> loadBufferTable;
    @FXML private TableColumn<LoadBufferRow, String> loadTagColumn;
    @FXML private TableColumn<LoadBufferRow, String> loadBusyColumn;
    @FXML private TableColumn<LoadBufferRow, String> loadAddressColumn;
    @FXML private TableColumn<LoadBufferRow, String> loadCyclesColumn;

    @FXML private TableView<StoreBufferRow> storeBufferTable;
    @FXML private TableColumn<StoreBufferRow, String> storeTagColumn;
    @FXML private TableColumn<StoreBufferRow, String> storeBusyColumn;
    @FXML private TableColumn<StoreBufferRow, String> storeAddressColumn;
    @FXML private TableColumn<StoreBufferRow, String> storeVColumn;
    @FXML private TableColumn<StoreBufferRow, String> storeQColumn;
    @FXML private TableColumn<StoreBufferRow, String> storeCyclesColumn;

    @FXML private TableView<CacheMemoryRow> cacheMemoryTable;
    @FXML private TableColumn<CacheMemoryRow, String> cacheAddressColumn;
    @FXML private TableColumn<CacheMemoryRow, String> cacheValueColumn;

    @FXML private TableView<InstructionRow> instructionTable;
    @FXML private TableColumn<InstructionRow, String> instructionColumn;
    @FXML private TableColumn<InstructionRow, String> jColumn;
    @FXML private TableColumn<InstructionRow, String> kColumn;
    @FXML private TableColumn<InstructionRow, String> issueColumn;
    @FXML private TableColumn<InstructionRow, String> executionCompleteColumn;
    @FXML private TableColumn<InstructionRow, String> writeResultColumn;



    @FXML private javafx.scene.control.Button nextStepButton;

    private StringProperty currentClockCycleProperty = new SimpleStringProperty("0");
    private TomasuloSimulator simulator;

    @FXML
    public void initialize() {
        simulator = new TomasuloSimulator(12, 20,12, 3, 2,
                3, 2, 64, 4, 10, 4, 2,
                2, 2, 40, 1, 1, 2);

        clockCycleLabel.textProperty().bind(currentClockCycleProperty);
        setupAdderRsTable();
        setupMultRsTable();
        setupRegisterFileTable();
        setupLoadBufferTable();
        setupStoreBufferTable();
        setupCacheMemoryTable();
        setupInstructionTable();
        updateUI();

        nextStepButton.setOnAction(event -> {
            simulator.step();
            updateUI();
        });
    }

    private void setupLoadBufferTable() {
        loadTagColumn.setCellValueFactory(data -> data.getValue().tagProperty());
        loadBusyColumn.setCellValueFactory(data -> data.getValue().busyProperty());
        loadAddressColumn.setCellValueFactory(data -> data.getValue().addressProperty());
        loadCyclesColumn.setCellValueFactory(data -> data.getValue().cyclesProperty());
    }

    private void setupCacheMemoryTable() {
        cacheAddressColumn.setCellValueFactory(data -> data.getValue().addressProperty());
        cacheValueColumn.setCellValueFactory(data -> data.getValue().valueProperty());
    }

    private void setupStoreBufferTable() {
        storeTagColumn.setCellValueFactory(data -> data.getValue().tagProperty());
        storeBusyColumn.setCellValueFactory(data -> data.getValue().busyProperty());
        storeAddressColumn.setCellValueFactory(data -> data.getValue().addressProperty());
        storeVColumn.setCellValueFactory(data -> data.getValue().vProperty());
        storeQColumn.setCellValueFactory(data -> data.getValue().qProperty());
        storeCyclesColumn.setCellValueFactory(data -> data.getValue().cyclesProperty());
    }

    private void setupRegisterFileTable() {
        tagColumn.setCellValueFactory(data -> data.getValue().nameProperty());
        qiColumn.setCellValueFactory(data -> data.getValue().qiProperty());
        valueColumn.setCellValueFactory(data -> data.getValue().valueProperty());
    }

    private void updateRegisterFileTable() {
        ObservableList<RegisterRow> registerRows = FXCollections.observableArrayList();
        RegisterFile registerFile = simulator.getRegisterFile();

        // Populate floating-point registers
        registerFile.getFloatingRegisters().forEach((name, reg) -> {
            registerRows.add(new RegisterRow(
                    reg.getName(),
                    reg.getQi(),
                    reg.getValue()
            ));
        });

        // Populate integer registers
        registerFile.getIntegerRegisters().forEach((name, reg) -> {
            registerRows.add(new RegisterRow(
                    reg.getName(),
                    "-",  // Qi is not relevant for integer registers
                    reg.getValue()
            ));
        });

        registerFileTable.setItems(registerRows);
    }

    private void setupInstructionTable() {
        instructionColumn.setCellValueFactory(data -> data.getValue().instructionProperty());
        jColumn.setCellValueFactory(data -> data.getValue().jProperty());
        kColumn.setCellValueFactory(data -> data.getValue().kProperty());
        issueColumn.setCellValueFactory(data -> data.getValue().issueProperty());
        executionCompleteColumn.setCellValueFactory(data -> data.getValue().executionCompleteProperty());
        writeResultColumn.setCellValueFactory(data -> data.getValue().writeResultProperty());
    }


    private void setupAdderRsTable() {
        adderCyclesColumn.setCellValueFactory(data -> data.getValue().cyclesProperty());
        adderTagColumn.setCellValueFactory(data -> data.getValue().tagProperty());
        adderBusyColumn.setCellValueFactory(data -> data.getValue().busyProperty());
        adderOperationColumn.setCellValueFactory(data -> data.getValue().operationProperty());
        adderVjColumn.setCellValueFactory(data -> data.getValue().vjProperty());
        adderVkColumn.setCellValueFactory(data -> data.getValue().vkProperty());
        adderQjColumn.setCellValueFactory(data -> data.getValue().qjProperty());
        adderQkColumn.setCellValueFactory(data -> data.getValue().qkProperty());
        adderAddressColumn.setCellValueFactory(data -> data.getValue().addressProperty());
    }

    private void setupMultRsTable() {
        multCyclesColumn.setCellValueFactory(data -> data.getValue().cyclesProperty());
        multTagColumn.setCellValueFactory(data -> data.getValue().tagProperty());
        multBusyColumn.setCellValueFactory(data -> data.getValue().busyProperty());
        multOperationColumn.setCellValueFactory(data -> data.getValue().operationProperty());
        multVjColumn.setCellValueFactory(data -> data.getValue().vjProperty());
        multVkColumn.setCellValueFactory(data -> data.getValue().vkProperty());
        multQjColumn.setCellValueFactory(data -> data.getValue().qjProperty());
        multQkColumn.setCellValueFactory(data -> data.getValue().qkProperty());
        multAddressColumn.setCellValueFactory(data -> data.getValue().addressProperty());
    }

    private void updateLoadBufferTable() {
        ObservableList<LoadBufferRow> loadBufferRows = FXCollections.observableArrayList();
        LoadBuffer loadBuffer = simulator.getLoadBuffer();

        for (LoadBufferSlot slot : loadBuffer.getSlots()) {
            loadBufferRows.add(new LoadBufferRow(
                    slot.getTag(),
                    String.valueOf(slot.isBusy()),
                    slot.getAddress(),
                    String.valueOf(slot.getCycles())
            ));
        }

        loadBufferTable.setItems(loadBufferRows);
    }

    private void updateStoreBufferTable() {
        ObservableList<StoreBufferRow> storeBufferRows = FXCollections.observableArrayList();
        StoreBuffer storeBuffer = simulator.getStoreBuffer();

        for (StoreBufferSlot slot : storeBuffer.getSlots()) {
            storeBufferRows.add(new StoreBufferRow(
                    slot.getTag(),
                    String.valueOf(slot.isBusy()),
                    slot.getAddress(),
                    slot.getV(),
                    slot.getQ(),
                    String.valueOf(slot.getCycles())
            ));
        }

        storeBufferTable.setItems(storeBufferRows);
    }

    private void updateCacheMemoryTable() {
        ObservableList<CacheMemoryRow> cacheRows = FXCollections.observableArrayList();
        CacheMemory cacheMemory = simulator.getCacheMemory();

        float[] cacheData = cacheMemory.getCache();
        for (int i = 0; i < cacheMemory.getCacheSize(); i++) {
            cacheRows.add(new CacheMemoryRow(
                    String.valueOf(i),           // Address
                    String.valueOf(cacheData[i]) // Value
            ));
        }

        cacheMemoryTable.setItems(cacheRows);
    }

    private void updateInstructionTable() {
        ObservableList<InstructionRow> instructionRows = FXCollections.observableArrayList();
        InstructionUnit instructionUnit = simulator.getInstructionUnit();

        for (InstructionUnitSlot slot : instructionUnit.getInstructionUnitSlots()) {
            if (slot.getInstruction() != null) {
                instructionRows.add(new InstructionRow(
                        slot.getInstruction().toString(),              // Instruction
                        slot.getInstruction().getSource1(),           // J
                        slot.getInstruction().getSource2(),           // K
                        String.valueOf(slot.getIssueClock()),         // Issue
                        String.valueOf(slot.getFinishExecutionClock()), // Execution Complete
                        String.valueOf(slot.getWriteClockCycle())     // Write Result
                ));
            }
        }

        instructionTable.setItems(instructionRows);
    }

private void updateUI() {
    currentClockCycleProperty.set("Clock Cycle: " + simulator.getCurrentClockCycle());
    updateRegisterFileTable();
    updateLoadBufferTable();
    updateStoreBufferTable();
    updateCacheMemoryTable();
    updateInstructionTable();
    currentClockCycleProperty.set("Clock Cycle: " + simulator.getCurrentClockCycle());
    // Populate Adder Reservation Station Table
    ObservableList<ReservationStationRow> adderRows = FXCollections.observableArrayList();
    simulator.getAdderRS().getSlots().forEach(slot -> {
        adderRows.add(new ReservationStationRow(
                String.valueOf(slot.getCyclesLeft()),
                slot.getTag(),
                String.valueOf(slot.isBusy()),
                slot.getOperation(),
                slot.getVj(),
                slot.getVk(),
                slot.getQj(),
                slot.getQk(),
                "0"

        ));
    });
    adderRsTable.setItems(adderRows);

    // Populate Multiplier Reservation Station Table
    ObservableList<ReservationStationRow> multRows = FXCollections.observableArrayList();
    simulator.getMultiplierRS().getSlots().forEach(slot -> {
        multRows.add(new ReservationStationRow(
                String.valueOf(slot.getCyclesLeft()),
                slot.getTag(),
                String.valueOf(slot.isBusy()),
                slot.getOperation(),
                slot.getVj(),
                slot.getVk(),
                slot.getQj(),
                slot.getQk(),
                "0"
        ));
    });
    multRsTable.setItems(multRows);
}
}
