package com.saniya.news.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {


    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public void run(String... strings) throws Exception {
        System.out.println("Loading Data... ");

        roleRepository.save(new Role("USER"));

        Role userRole = roleRepository.findRoleByRoleName("USER");

        User newUser = new User();
        newUser.addRole(userRole);
        newUser.setUsername("newUser");
        newUser.setPassword("password");
        userRepository.save(newUser);


    }

}

