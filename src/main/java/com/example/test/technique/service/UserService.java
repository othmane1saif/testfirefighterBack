package com.example.test.technique.service;

import com.example.test.technique.model.Role;
import com.example.test.technique.model.User;
import com.example.test.technique.repository.RoleRepository;
import com.example.test.technique.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service(value= "userService")
public class UserService implements UserDetailsService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bcryptEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bcryptEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bcryptEncoder = bcryptEncoder;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority(user));
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        });
        return authorities;
    }

    public User saveUser(User user) {
        user.setPassword(bcryptEncoder.encode(user.getPassword()));
        Set roleSet = new HashSet();
        roleSet.add(user.getEmail().toLowerCase(Locale.ROOT).contains("admin") ?
                roleRepository.findByName("ADMIN")
                : roleRepository.findByName("USER"));
        user.setRoles(roleSet);
        return userRepository.save(user);
    }

    public List<User> getUsers(String team) {
        return userRepository.findAllByTeam(team);
    }

    public User findOne(String userName) {
        return userRepository.findByUsername(userName);
    }

    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }

    public Object findOnById(Long id) {
        return userRepository.findById(id).orElse(new User());
    }
}
