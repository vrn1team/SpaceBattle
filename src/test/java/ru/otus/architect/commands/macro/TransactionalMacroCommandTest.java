package ru.otus.architect.commands.macro;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.architect.commands.Command;
import ru.otus.architect.commands.baseCommands.MoveCommand;
import ru.otus.architect.exceptions.CommandException;
import ru.otus.architect.game.objects.characteristic.Movable;
import ru.otus.architect.game.objects.dimension.Coordinates;
import ru.otus.architect.stubs.UndoableMovableStub;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class TransactionalMacroCommandTest {

    @Mock
    Command macroCommand;

    @Test
    @DisplayName("Команда завершена успешно, объект должен иметь новые координаты")
    void testTransactionalMacroCommandWithMovables() {
        // arrange
        var movableObj = new UndoableMovableStub(); // coordinates 0,0 by movable stub construction
        movableObj.setVelocity(new Coordinates(2, 3));
        var command1 = spy(new MoveCommand(movableObj));
        var command2 = spy(new MoveCommand(movableObj));
        Map<Undoable, Consumer<? super Undoable>> initialStates = new HashMap<>();
        initialStates.put(movableObj, m -> ((Movable) m).setPosition(((Movable) m).getPosition()));
        var macro = new TransactionalMacroCommand(List.of(command1, command2), initialStates);
        // act
        macro.execute();
        /// assert
        assertEquals(4, movableObj.getPosition().getX());
        assertEquals(6, movableObj.getPosition().getY());
        verify(command1, Mockito.times(1)).execute();
        verify(command2, Mockito.times(1)).execute();
    }

    @Test
    @DisplayName("Вторая команда в списке выбросила исключение, координаты объекта не изменились")
    void testTransactionalMacroCommandWithExceptionInOne() {
        // arrange
        var movableObj = new UndoableMovableStub(); // coordinates 0,0 by movable stub construction
        movableObj.setVelocity(new Coordinates(2, 3));
        var command1 = spy(new MoveCommand(movableObj));
        var command2 = spy(new MoveCommand(movableObj));

        doThrow(new RuntimeException("ERROR")).when(command2).execute();
        Map<Undoable, Consumer<? super Undoable>> initialStates = new HashMap<>();
        var initialCoordinates = movableObj.getPosition();
        initialStates.put(movableObj, m -> ((Movable) m).setPosition(initialCoordinates));
        var macro = new TransactionalMacroCommand(List.of(command1, command2), initialStates);
        // act
        assertThrows(CommandException.class, macro::execute);
        /// assert
        assertEquals(0, movableObj.getPosition().getX());
        assertEquals(0, movableObj.getPosition().getY());
        verify(command1, Mockito.times(1)).execute();
        verify(command2, Mockito.times(1)).execute();
    }
}