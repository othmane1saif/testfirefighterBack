package com.example.test.technique;

import com.example.test.technique.model.Leave;
import com.example.test.technique.model.Role;
import com.example.test.technique.model.User;
import com.example.test.technique.repository.RoleRepository;
import com.example.test.technique.service.LeaveService;
import com.example.test.technique.service.UserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.transaction.Transactional;
import java.time.LocalDate;

@SpringBootApplication
public class TestTechniqueApplication implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private LeaveService leaveService;

    public static void main(String[] args) {
        SpringApplication.run(TestTechniqueApplication.class, args);
    }

    @Override
    @Transactional
    public void run(String... args) {

        roleRepository.save(Role.builder().name("ADMIN").description("ADMIN ROLE DESCRIPTION").build());
        roleRepository.save(Role.builder().name("USER").description("USER ROLE DESCRIPTION").build());

        userService.saveUser(User.builder().username("fanch").password("fanch").email("Fanch@user.eu.fr").name("Fanch").team("Core qualité").build());
        userService.saveUser(User.builder().username("romain").password("romain").email("Romain@user.eu.fr").name("Romain").team("Core qualité").build());
        userService.saveUser(User.builder().username("saad").password("saad").email("saad@user.eu.fr").name("Kevin").team("Android").build());
        userService.saveUser(User.builder().username("renaud").password("renaud").email("renaud@user.eu.fr").name("Renaud").team("Core qualité").build());
        userService.saveUser(User.builder().username("hugo").password("hugo").email("hugo@user.eu.fr").name("Hugo").team("Core qualité").build());

        userService.saveUser(User.builder().username("admin").password("admin").email("maxime@admin.eu.fr").name("Maxime").team("Core Qualité").build());

        Leave leave = Leave.builder().id(null).
                dateDepart(LocalDate.now().minusDays(3)).
                dateFin(LocalDate.now().plusDays(3)).user((User) userService.findOnById(1L)).build();

        Leave leave2 = Leave.builder().id(null).
                dateDepart(LocalDate.now().minusDays(5)).
                dateFin(LocalDate.now().minusDays(2)).user((User) userService.findOnById(2L)).build();

        leaveService.addLeave(leave);
        leaveService.addLeave(leave2);

    }
}
