package com.nz.test.bean;

public class EventPublisherBean {

    public void initialize() {
        System.out.println("#### 初始化发布: EventPublisherBean initializing");
        EventManager.getInstance().publish("#### event published from EventPublisherBean");
    }
}
