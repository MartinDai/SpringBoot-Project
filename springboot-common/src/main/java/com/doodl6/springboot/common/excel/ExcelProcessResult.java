package com.doodl6.springboot.common.excel;

public class ExcelProcessResult {

    private volatile boolean isDone;

    private int rows;

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public void processDone() {
        synchronized (this) {
            isDone = true;
            this.notifyAll();
        }
    }

    public void waitForDone() {
        if (isDone) {
            return;
        }

        synchronized (this) {
            while (!isDone) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
