package com.example.demo.api;

import com.example.demo.application.service.rabbitmq.sender.SenderService;
import com.example.demo.domain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * @author 王景阳
 * @date 2022/10/20 19:01
 */
@RestController
@RequestMapping("/rabbitmq")
public class RabbitmqApi {

    @Autowired
    private SenderService sender;

    @GetMapping("/sender/{exchange}/{name}/{des}")
    public boolean sender(@PathVariable("exchange") String exchange, @RequestParam(required = false) String key, @PathVariable("name") String name, @PathVariable("des") String des) {
        User user = new User(name, des, LocalDateTime.now());
        System.out.println(user);
        return sender.send(exchange, key, user);
    }
}
