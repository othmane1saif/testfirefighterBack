package com.example.test.technique.events;

import com.example.test.technique.model.FireFigher;
import com.example.test.technique.model.User;
import com.example.test.technique.service.FireFigherService;
import com.example.test.technique.service.LeaveService;
import com.example.test.technique.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Slf4j
public class FireFigherEvent {

    private FireFigherService fireFigherService;
    private LeaveService leaveService;
    private UserService userService;

    public FireFigherEvent(FireFigherService fireFigherService, LeaveService leaveService, UserService userService) {
        this.fireFigherService = fireFigherService;
        this.leaveService = leaveService;
        this.userService = userService;
    }

    @Scheduled(fixedRate = 50000)
    public void defineFireFigher () {
        List<User> users = userService.getUsers("core quali");


        Optional<Object> fireFigher = fireFigherService.getFireFigher();
        if (!users.isEmpty()) {
            if (fireFigher.isPresent()) {
                FireFigher fireFigher1 = (FireFigher) fireFigher.get();
                log.info("the new fireFighter: " + fireFigher1.toString());
                for (int i = 0; i < users.size(); i++) {
                    if (users.get(i).getId().equals(fireFigher1.getId())) {
                        int j = i + 1;
                        while (i != j) {
                            if (j == users.size()) {
                                j = 0;
                            }
                            if (addFireFighter(users, fireFigher1, j)) break;
                            j++;
                        }
                    }
                }
            } else {
                fireFigherService.addTheNewFireFigher(new FireFigher(users.get(4).getId(), users.get(4).getName()));
            }
        }
    }

    private boolean addFireFighter(List<User> users, FireFigher fireFigher1, int j) {
        if (!leaveService.getLeaveByUserId(users.get(j).getId()).isPresent()) {
            fireFigherService.deleteById(fireFigher1.getId());
            fireFigherService.addTheNewFireFigher(new FireFigher(users.get(j).getId(), users.get(j).getName()));
            return true;
        }
        return false;
    }
}
