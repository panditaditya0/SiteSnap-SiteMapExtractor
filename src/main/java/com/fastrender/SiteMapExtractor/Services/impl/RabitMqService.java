package com.fastrender.SiteMapExtractor.Services.impl;

import com.fastrender.SiteMapExtractor.Services.MessageBrokerService;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabitMqService implements MessageBrokerService {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RabbitAdmin rabbitAdmin;

    @Override
    public <T> void createQueue(String queueName) {
        Queue queue = new Queue(queueName, true);
        rabbitAdmin.declareQueue(queue);
    }

    @Override
    public <T> void sendMessage(String queueName, T message) {
        rabbitTemplate.convertAndSend(queueName, message);
    }
}
