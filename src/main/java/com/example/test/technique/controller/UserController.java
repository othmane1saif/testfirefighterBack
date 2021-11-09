package com.example.test.technique.controller;

import com.example.test.technique.configuration.TokenProvider;
import com.example.test.technique.model.Role;
import com.example.test.technique.model.User;
import com.example.test.technique.service.UserService;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/users")
public class UserController {

    private AuthenticationManager authenticationManager;
    private TokenProvider jwtTokenUtil;
    private UserService userService;

    public UserController(AuthenticationManager authenticationManager, TokenProvider jwtTokenUtil, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> generateToken(@RequestBody LoginUser loginUser) throws AuthenticationException {

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getUserName(),
                        loginUser.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenUtil.generateToken(authentication);
        User user = userService.findOne(loginUser.getUserName());
        UserResp userResp = UserResp.builder().id(user.getId()).token(token).username(loginUser.getUserName()).
                name(user.getName()).role(user.getRoles().iterator().next().getName()).build();
        return ResponseEntity.ok().body(userResp);
    }

    @GetMapping("/user")
    public ResponseEntity getUserById(@RequestParam Long id) {
        return ResponseEntity.ok().body(userService.findOnById(id));
    }

    @GetMapping("")
    public ResponseEntity getAllUser() {
        return ResponseEntity.ok().body(userService.getUsers());
    }
}

@Data
class LoginUser {
    private String userName;
    private String password;
}

@Data
@Builder
class UserResp {
    Long id;
    String username;
    String name;
    String email;
    String role;
    String token;
}
