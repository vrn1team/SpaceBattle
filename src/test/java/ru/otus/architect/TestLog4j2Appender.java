package ru.otus.architect;

import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.PatternLayout;

import java.util.ArrayList;
import java.util.List;

@Plugin(name = "TestLog4j2Appender", category = "Core", elementType = "appender", printObject = true)
public class TestLog4j2Appender extends AbstractAppender {

    private static final long serialVersionUID = -4319748955513985321L;

    private final List<LogEvent> logs = new ArrayList();

    protected TestLog4j2Appender(String name, Filter filter,
                                 Layout layout, final boolean ignoreExceptions) {
        super(name, filter, layout, ignoreExceptions, null);
    }

    @Override
    public void append(LogEvent event) {
        logs.add(event);
    }

    public void clearMessages() {
        logs.clear();
    }

    public String getMessage(int index) {
        if (logs.isEmpty()) {
            return null;
        }
        return logs.get(index).getMessage().getFormattedMessage();
    }

    @PluginFactory
    public static TestLog4j2Appender createAppender(
            @PluginAttribute("name") String name,
            @PluginElement("Layout") Layout layout,
            @PluginElement("Filter") final Filter filter,
            @PluginAttribute("otherAttribute") String otherAttribute) {
        if (name == null) {
            LOGGER.error("No name provided for TestLog4j2Appender");
            return null;
        }
        if (layout == null) {
            layout = PatternLayout.createDefaultLayout();
        }
        return new TestLog4j2Appender(name, filter, layout, true);
    }
}
