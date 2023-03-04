package ru.otus.architect.commands.transactions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.architect.commands.Command;

import java.util.function.Consumer;
import java.util.function.Supplier;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionalImplTest {

    @Mock
    private Command backup;
    @Mock
    private Command rollback;
    @Mock
    private Command command;

    private Transactional transactional;

    @BeforeEach
    void setUp() {
        transactional = new TransactionalImpl(command, backup, rollback);
    }

    @Test
    void backup() {
        doNothing().when(backup).execute();

        transactional.backup();

        verify(backup, times(1)).execute();
    }

    @Test
    void rollback() {
        doNothing().when(rollback).execute();

        transactional.rollback();

        verify(rollback, times(1)).execute();
    }

    @Test
    void execute() {
        transactional.execute();

        verify(command, times(1)).execute();
    }
}