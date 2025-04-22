package org.example.decorator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void test() {
        Main main = new Main();
        main.sendMessage("ginro");
    }

}