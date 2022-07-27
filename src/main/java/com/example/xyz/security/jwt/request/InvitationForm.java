package com.example.xyz.security.jwt.request;

import java.util.Set;

public class InvitationForm {
    private String favoris;

    private String participation;


    private Set<String> validation;



    public Set<String> getValidation() {
        return this.validation;
    }

    public void setValidation(Set<String> validation) {
        this.validation = validation;
    }

    public String getFavoris() {
        return favoris;
    }

    public void setFavoris(String favoris) {
        this.favoris = favoris;
    }

    public String getParticipation() {
        return participation;
    }

    public void setParticipation(String participation) {
        this.participation = participation;
    }




}
