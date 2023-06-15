package com.pro.app.component;

import java.util.HashMap;
import java.util.Map;

public class MemoryLeak {

    private final Map<Integer, MemoryLeak.Memory> memorableList = new HashMap<>();

    public void run() {

        Integer i = 0;
        while (true) {
            memorableList.put(i, new MemoryLeak.Memory(i));
            i++;
        }
    }

    class Memory {
        Integer index ;
        Memory(final Integer index) {
            this.index = index;
        }
    }
}
