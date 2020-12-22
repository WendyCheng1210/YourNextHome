package org.yanwen.consumer.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;
import org.yanwen.core.service.OrderService;

@Service
public class ListenerService {
    @Autowired
    private OrderService orderService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @JmsListener(destination = "MyNextHomeQueue")
    public void processMessage(String jsonMessage) {

    }
}
