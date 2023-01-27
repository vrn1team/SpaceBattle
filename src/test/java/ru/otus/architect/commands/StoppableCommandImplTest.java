package ru.otus.architect.commands;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StoppableCommandImplTest {
    private final static String TEST_MESSAGE_OK = "Ok";
    private final static String TEST_MESSAGE_FAILED = "FAILED";
    private final static List<String> LOG = new ArrayList<>();

    @Test
    @DisplayName("Команда выполняется")
    void execute() {
        StoppableCommand command = new StoppableCommandImpl(() -> LOG.add(TEST_MESSAGE_OK), () -> LOG.add(TEST_MESSAGE_FAILED));

        command.execute();

        assertEquals(1, LOG.size());

        assertEquals(TEST_MESSAGE_OK, LOG.get(0));
    }

    @Test
    @DisplayName("После stop выполняется terminationCommand")
    void stop() {
        StoppableCommand command = new StoppableCommandImpl(() -> LOG.add(TEST_MESSAGE_FAILED), () -> LOG.add(TEST_MESSAGE_OK));

        command.stop();
        command.execute();

        assertEquals(1, LOG.size());

        assertEquals(TEST_MESSAGE_OK, LOG.get(0));
    }
}