package ru.otus.architect.exceptionHandling;

import static java.util.Objects.nonNull;

import java.util.HashMap;
import java.util.Map;
import ru.otus.architect.command.Command;
import ru.otus.architect.exceptionHandling.handlers.CommandExceptionHandler;

/**
 * Хендлер, который реализует решение исключений сначала происходит поиск хэнделра по конкретной команде и конкретному
 * исключению
 * Если ничего не найдено - идет поиск хэндлера для всех исключений по данной команде
 * Далее идет поиск хэндлера, который обрабатывает исключение вне зависимости от типа команды
 */
public class ExceptionHandlerServiceImpl implements ExceptionHandlerService {

    private Map<Class<? extends Command>, Map<Class<? extends Exception>, CommandExceptionHandler>> handlerRepository;

    public ExceptionHandlerServiceImpl() {
        this.handlerRepository = new HashMap<>();
        handlerRepository.put(RuleForPreciseException.class, new HashMap<>());
    }


    @Override
    public void handleException(Command command, Exception exception) {
        CommandExceptionHandler handlerCommand = getHandlerCommand(command, exception);
        handlerCommand.handleException(command, exception);
    }

    private CommandExceptionHandler getHandlerCommand(Command command, Exception exception) {
        Class<? extends Command> commandClass = command.getClass();
        Class<? extends Exception> exceptionClass = exception.getClass();

        //Проверяем наличие правил обработки по конкретной команде
        if (handlerRepository.containsKey(commandClass)) {
            Map<Class<? extends Exception>, CommandExceptionHandler> handlerMap = handlerRepository.get(commandClass);
            // Если по нужному классу эксепшена есть хэндлер - берем его
            if (handlerMap.containsKey(exceptionClass)) {
                return handlerMap.get(exceptionClass);
                //Иначе смотрим на наличие хендлера для всех эксепшенов в случае данной команды
            } else if (handlerMap.containsKey(NoOpException.class)) {
                return handlerMap.get(NoOpException.class);
            }
            //Если в прошлых шагах ничего не нашли - смотрим наличие общих правил обработки данного исключения
        }
        if (handlerRepository.get(RuleForPreciseException.class).containsKey(exceptionClass)) {
            return (handlerRepository.get(RuleForPreciseException.class).get(exceptionClass));
        }
        //если и общих правил нет - наши полномочия все
        throw new NoCorrectHandlerException("Can't find handler for exception " + exceptionClass.getName());
    }

    @Override
    public void registerExceptionHandlingRule(
            Command command, Exception exception, CommandExceptionHandler exceptionHandler) {
        registerExceptionHandlingRule(command.getClass(), exception.getClass(), exceptionHandler);
    }

    @Override
    public void registerExceptionHandlingRule(Command command, CommandExceptionHandler exceptionHandler) {
        registerExceptionHandlingRule(command.getClass(), NoOpException.class, exceptionHandler);
    }

    @Override
    public void registerExceptionHandlingRule(Exception exception, CommandExceptionHandler exceptionHandler) {
        registerExceptionHandlingRule(RuleForPreciseException.class, exception.getClass(), exceptionHandler);
    }

    private void registerExceptionHandlingRule(
            Class<? extends Command> commandClass, Class<? extends Exception> exceptionClass,
            CommandExceptionHandler exceptionHandler) {

        Map<Class<? extends Exception>, CommandExceptionHandler> exceptionExceptionHandlerHashMap = handlerRepository
                .get(commandClass);
        if (nonNull(exceptionExceptionHandlerHashMap)) {
            exceptionExceptionHandlerHashMap.put(exceptionClass, exceptionHandler);
        } else {
            exceptionExceptionHandlerHashMap = new HashMap<>();
            exceptionExceptionHandlerHashMap.put(exceptionClass, exceptionHandler);
            handlerRepository.put(commandClass, exceptionExceptionHandlerHashMap);
        }
    }

    /**
     * Служебный класс, нужен для хранения правил обработки конкретных исключений, без привязки к командам
     */
    private class RuleForPreciseException implements Command {

        @Override
        public void execute() {
        }
    }

    /**
     * Служебный класс, нужен для хранения правил обработки конкретных команд, без привязки к исключениям
     */
    private class NoOpException extends Exception {
    }
}
