package com.example.xyz.service;

import com.example.xyz.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserPrinciple implements UserDetails {
    private static final long serialVersionUID = 1L;

    private Long userId;

    private String username;

    private String mail;

    private String firstName;

    private String lastName;

    private Date birthday;

    private Long matricule;

    private boolean actif;

    private String profilePicture;

    private Date startingDate;

    private String typeContract;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public UserPrinciple(Long userId,
                         String firstName, String lastName,
                         String username, String mail, String password, Date birthday,
                         Long matricule, boolean actif, String profilePicture,
                         Date startingDate, String typeContract,
                         Collection<? extends GrantedAuthority> authorities) {

        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.mail = mail;
        this.password = password;
        this.birthday = birthday;
        this.matricule = matricule;
        this.actif = actif;
        this.profilePicture = profilePicture;
        this.startingDate = startingDate;
        this.typeContract = typeContract;
        this.authorities = authorities;
    }

    public static UserPrinciple build(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList());

        return new UserPrinciple(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getMail(),
                user.getPassword(),
                user.getBirthday(),
                user.getMatricule(),
                user.getActif(),
                user.getProfilePicture(),
                user.getStartingDate(),
                user.getTypeContract(),
                authorities);
    }

    public Long getId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMail() {
        return mail;
    }

    public Date getBirthday() {
        return birthday;
    }

    public Long getMatricule() {
        return matricule;
    }

    public boolean getActif() {
        return actif;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public Date getStartingDate() {
        return startingDate;
    }

    public String getTypeContract() {
        return typeContract;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        UserPrinciple user = (UserPrinciple) o;
        return Objects.equals(userId, user.userId);
    }
}