package com.example.xyz.service;

import com.example.xyz.entity.Role;
import com.example.xyz.entity.User;
import com.example.xyz.enums.RoleName;
import com.example.xyz.repositories.RoleRepository;
import com.example.xyz.repositories.UserRepository;
import com.example.xyz.security.jwt.request.SignUpForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User Not Found : " + username));

        return UserPrinciple.build(user);
    }

    @Transactional
    public void deleteByUsername(String username) throws ChangeSetPersister.NotFoundException {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            throw new ChangeSetPersister.NotFoundException();
        }
        userRepository.deleteByUsername(username);
    }

    @Transactional
    public User updateUser(SignUpForm userToUpdate) throws ChangeSetPersister.NotFoundException {
        Optional<User> databaseUser = userRepository.findByUsername(userToUpdate.getUsername());
        if (databaseUser.isEmpty()) {
            throw new ChangeSetPersister.NotFoundException();
        }
        User user = databaseUser.get();

        user.setActif(userToUpdate.getActif());
        user.setBirthday(userToUpdate.getBirthday());
        user.setLastName(userToUpdate.getLastName());
        user.setMail(userToUpdate.getMail());
        user.setMatricule(userToUpdate.getMatricule());
        user.setPassword(userToUpdate.getPassword());
        user.setTypeContract(userToUpdate.getTypeContract());
        user.setStartingDate(userToUpdate.getStartingDate());
        user.setProfilePicture(userToUpdate.getProfilePicture());

        Set<String> strRoles = userToUpdate.getRole();

        Set<Role> roles = new HashSet<>();

        strRoles.forEach(role -> {
            switch (role) {
                case "admin":
                    Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("User Role not found"));
                    roles.add(adminRole);

                    break;
                case "superadmin":
                    Role companyRole = roleRepository.findByName(RoleName.ROLE_SUPER_ADMIN)
                            .orElseThrow(() -> new RuntimeException("User Role not found"));
                    roles.add(companyRole);

                    break;

                default:
                    Role userRole = roleRepository.findByName(RoleName.ROLE_EMPLOYEE)
                            .orElseThrow(() -> new RuntimeException("User Role not found"));
                    roles.add(userRole);
            }
        });

        user.setRoles(roles);
        return userRepository.save(user);

    }
};
