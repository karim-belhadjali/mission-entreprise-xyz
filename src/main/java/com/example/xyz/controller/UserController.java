package com.example.xyz.controller;

import com.example.xyz.entity.Role;
import com.example.xyz.entity.User;
import com.example.xyz.enums.RoleName;
import com.example.xyz.messagerie.service.EmailService;
import com.example.xyz.repositories.RoleRepository;
import com.example.xyz.repositories.UserRepository;
import com.example.xyz.security.jwt.request.SignUpForm;
import com.example.xyz.security.jwt.response.ResponseMessage;
import com.example.xyz.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserDetailsServiceImpl userService;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    public EmailService emailService;

    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteUser(@PathVariable String username) {
        try {
            userService.deleteByUsername(username);
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return new ResponseEntity<>("failed to delete user", HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> GetUser() {
        List<User> listuser = null;
        listuser = userRepository.findAll();
        if (listuser.isEmpty())
            return ResponseEntity.noContent().build();

        return new ResponseEntity<>(listuser, HttpStatus.OK);
    }

    @GetMapping("/stats")
    public ResponseEntity<?> GetUsersStats() {
        List<User> listuser = null;
        listuser = userRepository.findAll();
        AtomicInteger numberOfActiveUsers = new AtomicInteger();
        AtomicInteger numberOfDisactivatedUsers = new AtomicInteger();
        AtomicInteger numberOfsuspendedUsers = new AtomicInteger();

        if (listuser.isEmpty())
            return ResponseEntity.noContent().build();
        listuser.forEach(user -> {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime lastLogin = user.getLastLogin();
            if (lastLogin != null) {
                Duration duration = Duration.between(now, lastLogin);
                long diff = Math.abs(duration.toDays());
                if (diff<30 &&user.getActif()) {
                    numberOfActiveUsers.getAndIncrement();
                } else {
                    numberOfsuspendedUsers.getAndIncrement();
                }
            } else {
                numberOfDisactivatedUsers.getAndIncrement();
            }
        });

        return new ResponseEntity<>(new ResponseMessage("the total number of users is: " + listuser.size() + " the number of active user last month is: " + numberOfActiveUsers + " the number suspended users is: " + numberOfsuspendedUsers + " and the number of inactive user is: " + numberOfDisactivatedUsers), HttpStatus.OK);
    }

    @Scheduled(fixedDelay = 30000)
    public void getUsersToBlock() throws InterruptedException {
        System.out.println("scheduled job for finding user who haven't logged in more than 2 hours");
        List<User> listuser = null;
        listuser = userRepository.findAll();
        if (listuser.isEmpty()) return;
        listuser.forEach(user -> {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime lastLogin = user.getLastLogin();
            if (lastLogin != null) {
                Duration duration = Duration.between(now, lastLogin);
                long diff = Math.abs(duration.toHours());
                if (diff > 2 && user.getActif()) {
                    user.setActif(false);
                    userRepository.save(user);
                    //emailService.sendSimpleEmail(user.getMail(),"User account is suspended","your account has been suspended because of lack of inactivity. please use this link to complete your profile http://localhost:8989/auth/"+user.getUsername());
                    System.out.println("found account to suspend of username: " + user.getUsername());
                }
            }
        });
    }


    @PutMapping
    public ResponseEntity<?> updateUser(@Valid @RequestBody SignUpForm userToUpdate) {
        User user;
        User userupdated;

        try {
            user = new User(
                    userToUpdate.getFirstName(),
                    userToUpdate.getLastName(),
                    userToUpdate.getUsername(),
                    userToUpdate.getMail(),
                    encoder.encode(userToUpdate.getPassword()),
                    userToUpdate.getBirthday(),
                    userToUpdate.getMatricule(),
                    userToUpdate.getActif(),
                    userToUpdate.getProfilePicture(),
                    userToUpdate.getStartingDate(),
                    userToUpdate.getTypeContract()
            );

            Set<String> strRoles = userToUpdate.getRole();
            Set<Role> roles = new HashSet<>();

            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException(" User Role does not exist"));
                        roles.add(adminRole);

                        break;
                    case "superadmin":
                        Role companyRole = roleRepository.findByName(RoleName.ROLE_SUPER_ADMIN)
                                .orElseThrow(() -> new RuntimeException(" User Role does not exist"));
                        roles.add(companyRole);

                        break;

                    default:
                        Role userRole = roleRepository.findByName(RoleName.ROLE_EMPLOYEE)
                                .orElseThrow(() -> new RuntimeException(" User Role does not exist"));
                        roles.add(userRole);
                }
            });
            user.setRoles(roles);
            user.setActif(true);

            userupdated = userService.updateUser(userToUpdate);
        } catch (ChangeSetPersister.NotFoundException e) {
            e.printStackTrace();
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}