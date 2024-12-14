package com.example.tomasulo.Memory;

public class CacheMemory {

    private Memory memory;
    private float[] cache;
    private int blockSize;
    private int cacheSize;
    private boolean isLoaded;

    public CacheMemory(Memory memory, int blockSize, int cacheSize) {
        this.memory = memory;
        this.blockSize = blockSize;
        this.cacheSize = memory.getSize();
        this.isLoaded = false;
        this.cache = new float[cacheSize];
    }


    public int getBlockSize() {
        return blockSize;
    }

    public int getCacheSize() {
        return cacheSize;
    }

    public boolean isLoaded() {
        return isLoaded;
    }

    public float[] getCache() {
        return cache;
    }
    public void setLoaded(boolean loaded) {
        isLoaded = loaded;
    }

    public void loadValuesFromMemory(){

        for (int i = 0; i < cacheSize; i++) {
            cache[i] = memory.getAddress(i);
        }
        setLoaded(true);

    }

    public String getValueByAddress(int address){
        return "" + cache[address];
    }

    public void setValueByAddress(int address, float value){
        cache[address] = value;
        memory.setAddress(address, value);
    }




}
