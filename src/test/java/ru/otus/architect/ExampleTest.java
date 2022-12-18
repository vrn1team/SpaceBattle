package ru.otus.architect;

import com.sun.tools.javac.Main;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExampleTest {

    @Test
    void main() {
        var cut = new Example();
        assertNotNull(cut);
    }
}