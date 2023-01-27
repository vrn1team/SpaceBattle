package ru.otus.architect.commands;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ChangeableCommandImplTest {
    private final static String TEST_MESSAGE_OK = "Ok";
    private final static String TEST_MESSAGE_FAILED = "FAILED";
    private final static List<String> LOG = new ArrayList<>();



    @Test
    @DisplayName("После изменения команды будут выполнены новые действия")
    void changeCommand() {
        ChangeableCommand command = new ChangeableCommandImpl(() -> LOG.add(TEST_MESSAGE_FAILED));

        command.changeCommand(() -> LOG.add(TEST_MESSAGE_OK));

        command.execute();

        assertEquals(1, LOG.size());

        assertEquals(TEST_MESSAGE_OK, LOG.get(0));
    }

    @Test
    @DisplayName("Команда выполняется")
    void execute() {
        ChangeableCommand command = new ChangeableCommandImpl(() -> LOG.add(TEST_MESSAGE_OK));

        command.execute();

        assertEquals(1, LOG.size());

        assertEquals(TEST_MESSAGE_OK, LOG.get(0));

    }
}