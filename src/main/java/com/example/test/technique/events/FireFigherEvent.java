package com.example.test.technique.events;

import com.example.test.technique.model.FireFigher;
import com.example.test.technique.model.User;
import com.example.test.technique.service.FireFigherService;
import com.example.test.technique.service.LeaveService;
import com.example.test.technique.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;



@Slf4j
@Service
public class FireFigherEvent {

    private FireFigherService fireFigherService;
    private LeaveService leaveService;
    private UserService userService;

    public FireFigherEvent(FireFigherService fireFigherService, LeaveService leaveService, UserService userService) {
        this.fireFigherService = fireFigherService;
        this.leaveService = leaveService;
        this.userService = userService;
    }

    @Scheduled(cron = "@daily")
    public void defineFireFigher () {
        List<User> users = userService.getUsers("Core qualité");
        Optional<FireFigher> fireFigher = fireFigherService.getFireFighter();
        if (!fireFigher.isPresent()) {
            fireFigherService.addTheNewFireFigher(FireFigher.builder().name(users.get(0).getName())
                    .id(users.get(0).getId()).build());
        } else {
            User activeFireFigherUser = userService.findOne(fireFigher.get().getName());
            User nextFireFigher = findNextFireFighter(users, activeFireFigherUser);
            fireFigherService.deleteById(fireFigher.get().getId());
            fireFigherService.addTheNewFireFigher(FireFigher.builder().name(nextFireFigher.getName())
                    .id(nextFireFigher.getId()).build());
        }
    }

    private User findNextFireFighter(List<User> users, User lastFireFighter) {
        int i = IntStream.range(0, users.size())
                .filter(j -> lastFireFighter.getId().equals(users.get(j).getId()))
                .findFirst().orElse(0);
        if (users.size()-1 == i) {
            return users.get(0);
        } else return users.get(i+1);
    }
}
