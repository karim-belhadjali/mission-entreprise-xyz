package com.example.xyz.controller;

import com.example.xyz.entity.Role;
import com.example.xyz.entity.User;
import com.example.xyz.enums.RoleName;
import com.example.xyz.repositories.RoleRepository;
import com.example.xyz.security.jwt.request.SignUpForm;
import com.example.xyz.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserDetailsServiceImpl userService;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    RoleRepository roleRepository;

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