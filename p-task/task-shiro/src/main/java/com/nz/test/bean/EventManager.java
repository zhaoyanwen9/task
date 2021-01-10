package com.nz.test.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class EventManager {

    private final List<Consumer<String>> listeners = new ArrayList<>();

    private EventManager() {
    }

    private static class SingletonHolder {
        private static final EventManager INSTANCE = new EventManager();
    }

    public static EventManager getInstance() {
        System.out.println("---------------获取实例getInstance: "+ SingletonHolder.INSTANCE);
        return SingletonHolder.INSTANCE;
    }

    public void publish(final String message) {
        System.out.println("---------------发布publish: " + message);
        listeners.forEach(l -> l.accept(message));
    }

    public void addListener(Consumer<String> eventConsumer) {
        System.out.println("---------------增加监听addListener:" + eventConsumer);
        listeners.add(eventConsumer);
    }
}
