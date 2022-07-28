package com.example.xyz.controller;

import com.example.xyz.entity.Role;
import com.example.xyz.entity.User;
import com.example.xyz.enums.RoleName;
//import com.example.xyz.messagerie.service.EmailService;
import com.example.xyz.repositories.RoleRepository;
import com.example.xyz.repositories.UserRepository;
import com.example.xyz.security.JwtTokenUtil;
import com.example.xyz.security.jwt.request.LoginForm;
import com.example.xyz.security.jwt.request.SignUpForm;
import com.example.xyz.security.jwt.response.JwtResponse;
import com.example.xyz.security.jwt.response.ResponseMessage;
import com.example.xyz.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserDetailsServiceImpl userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

//    @Autowired
//    public EmailService emailService;

    @PostMapping("/signin")
    public ResponseEntity<?> signInUser(@Valid @RequestBody LoginForm loginRequest) {
        User user;
        user = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow(() -> new RuntimeException(" User does not exist"));

        if (user.getActif()){
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtTokenUtil.generateJwtToken(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities()));
        }else {
            return new ResponseEntity<>(new ResponseMessage("User account is disabled"),
                    HttpStatus.FORBIDDEN);
        }

    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody SignUpForm signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity<>(new ResponseMessage("Username already exist!"),
                    HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByMail(signUpRequest.getMail())) {
            return new ResponseEntity<>(new ResponseMessage("Mail already exist!"),
                    HttpStatus.BAD_REQUEST);
        }

        User user = new User(
                signUpRequest.getFirstName(),
                signUpRequest.getLastName(),
                signUpRequest.getUsername(),
                signUpRequest.getMail(),
                encoder.encode(signUpRequest.getPassword()),
                signUpRequest.getBirthday(),
                signUpRequest.getMatricule(),
                signUpRequest.getActif(),
                signUpRequest.getProfilePicture(),
                signUpRequest.getStartingDate(),
                signUpRequest.getTypeContract()
        );

        Set<String> strRoles = signUpRequest.getRole();
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

                case "employe":
                    Role employee = roleRepository.findByName(RoleName.ROLE_EMPLOYEE)
                            .orElseThrow(() -> new RuntimeException(" User Role does not exist"));
                    roles.add(employee);

                    break;
                default:
                    Role userRole = roleRepository.findByName(RoleName.ROLE_EMPLOYEE)
                            .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not found."));
                    roles.add(userRole);
            }
        });

        user.setRoles(roles);
        userRepository.save(user);
//        emailService.sendSimpleEmail(user.getMail(),"New User Profile Creation","your account has been created please use this link to complete your profile http://localhost:8989/auth/"+user.getUsername());

        return new ResponseEntity<>(new ResponseMessage("User registered successfully!"), HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> GetUser() {
        List<User> listuser = null;
        listuser = userRepository.findAll();
        if (listuser.isEmpty())
            return ResponseEntity.noContent().build();

        return new ResponseEntity<>(listuser, HttpStatus.OK);
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<User>> enableAccountUser(@PathVariable String username) {
        User userupdated;
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException(" User account does not exist"));
        user.setActif(true);
        userRepository.save(user);
        return new ResponseEntity(new ResponseMessage("Account enabled you can sign in to your account now!"), HttpStatus.OK);
    }
}
