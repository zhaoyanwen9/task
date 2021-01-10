package com.nz.test.bean;

import java.util.function.Consumer;

public class EventListenerBean {

    private void initialize() {
        System.out.println("#### 初始化监听: EventListenerBean initializing");
        Consumer<String> eventConsumer = s -> System.out.println("#### event received in EventListenerBean");
        EventManager.getInstance().addListener(eventConsumer);
    }
}