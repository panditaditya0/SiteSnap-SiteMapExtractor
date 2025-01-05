package com.fastrender.SiteMapExtractor.Services;

public interface MessageBrokerService {
    <T> void createQueue(String queueName);
    <T> void sendMessage(String topic, T message);
}
