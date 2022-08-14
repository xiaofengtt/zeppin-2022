package com.bbl.thread;

import lombok.Data;

@Data
public class Runner implements Runnable {

    private String threadname;

    public Runner(String threadname) {
        super();
        this.threadname = threadname;
    }
    @Override
    public void run() {

    }
}
