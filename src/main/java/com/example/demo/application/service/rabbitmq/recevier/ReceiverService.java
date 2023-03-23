package com.example.demo.application.service.rabbitmq.recevier;

import com.example.demo.domain.entity.User;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

/**
 * 创建消息消费者Receiver
 *
 * @author 王景阳
 * @date 2022/10/19 21:05
 */
@Service
public class ReceiverService {

    /**
     * 只能完全匹配
     */
    @RabbitListener(bindings = {@QueueBinding(
            value = @Queue(value = "queue1"),
            key = "exchange1",
            exchange = @Exchange(name = "exchange1"))})
    public void process1(@Payload User user, @Header(name = "headMsg") String headMsg, @Header(name = "headContent") String headContent) {
        System.out.println("exchange1 DIRECT Receiver1 body: " + user);
        System.out.println("exchange1 DIRECT Receiver1 head headMsg : " + headMsg);
        System.out.println("exchange1 DIRECT Receiver1 head headContent : " + headContent);
    }

    /**
     * 只能完全匹配
     */
    @RabbitListener(bindings = {@QueueBinding(
            value = @Queue(value = "queue2"),
            key = "exchange1",
            exchange = @Exchange(name = "exchange1"))})
    public void process2(@Payload User user, @Header(name = "headMsg") String headMsg, @Header(name = "headContent") String headContent) {
        System.out.println("exchange1 DIRECT Receiver2 body: " + user);
        System.out.println("exchange1 DIRECT Receiver2 head headMsg : " + headMsg);
        System.out.println("exchange1 DIRECT Receiver2 head headContent : " + headContent);
    }

    /**
     * 只要属于“exchange2”交换机，就会转发
     */
    @RabbitListener(bindings = {@QueueBinding(
            value = @Queue(value = "queue1"),
            exchange = @Exchange(name = "exchange2", type = ExchangeTypes.FANOUT))})
    public void process3(@Payload User user, @Header(name = "headMsg") String headMsg, @Header(name = "headContent") String headContent) {
        System.out.println("exchange2 FANOUT Receiver1 body: " + user);
        System.out.println("exchange2 FANOUT Receiver1 head headMsg : " + headMsg);
        System.out.println("exchange2 FANOUT Receiver1 head headContent : " + headContent);
    }

    /**
     * 只要属于“exchange2”交换机，就会转发
     */
    @RabbitListener(bindings = {@QueueBinding(
            value = @Queue(value = "queue2"),
            exchange = @Exchange(name = "exchange2", type = ExchangeTypes.FANOUT))})
    public void process4(@Payload User user, @Header(name = "headMsg") String headMsg, @Header(name = "headContent") String headContent) {
        System.out.println("exchange2 FANOUT Receiver2 body: " + user);
        System.out.println("exchange2 FANOUT Receiver2 head headMsg : " + headMsg);
        System.out.println("exchange2 FANOUT Receiver2 head headContent : " + headContent);
    }

    /**
     * routing key为一个句点号“. ”分隔的字符串（我们将被句点号“. ”分隔开的每一段独立的字符串称为一个单词），如“stock.usd.nyse”、“nyse.vmw”、“quick.orange.rabbit”
     * binding key与routing key一样也是句点号“. ”分隔的字符串
     * binding key中可以存在两种特殊字符“*”与“#”，用于做模糊匹配，其中“*”用于匹配一个单词，“#”用于匹配多个单词（可以是零个）
     *
     * 当前队列可以匹配的key有：exchange3,exchange3.s,exchange3.s.ds,exchange3.df.sf.gg
     */
    @RabbitListener(bindings = {@QueueBinding(
            value = @Queue(value = "queue1"),
            key = "exchange3.#",
            exchange = @Exchange(name = "exchange3", type = ExchangeTypes.TOPIC))})
    public void process5(@Payload User user, @Header(name = "headMsg") String headMsg, @Header(name = "headContent") String headContent) {
        System.out.println("exchange3 TOPIC Receiver1 body: " + user);
        System.out.println("exchange3 TOPIC Receiver1 head headMsg : " + headMsg);
        System.out.println("exchange3 TOPIC Receiver1 head headContent : " + headContent);
    }

    /**
     * 当前队列可以匹配的key有：exchange3.s,exchange3.sdf
     */
    @RabbitListener(bindings = {@QueueBinding(
            value = @Queue(value = "queue2"),
            key = "exchange3.*",
            exchange = @Exchange(name = "exchange3", type = ExchangeTypes.TOPIC))})
    public void process6(@Payload User user, @Header(name = "headMsg") String headMsg, @Header(name = "headContent") String headContent) {
        System.out.println("exchange3 TOPIC Receiver2 body: " + user);
        System.out.println("exchange3 TOPIC Receiver2 head headMsg : " + headMsg);
        System.out.println("exchange3 TOPIC Receiver2 head headContent : " + headContent);
    }
}
