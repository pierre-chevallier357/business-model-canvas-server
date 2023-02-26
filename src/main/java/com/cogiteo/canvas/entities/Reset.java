package com.cogiteo.canvas.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Reset")
public class Reset {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "GUIDReset")
    private Long GUIDReset;

    @Column(name = "mail", nullable = false, unique = true, length = 75)
    private String mail;

    @Column(name = "code", nullable = false, length = 100)
    private String code;

    @Column(name = "tentative", nullable = false)
    private int tentative;

    @Column(name = "expiration", nullable = false)
    private LocalDateTime expiration;

    public Reset() {
    }

    public Reset(String mail, String code) {
        this.mail = mail;
        this.code = code;
        this.tentative = 3;
        this.expiration = LocalDateTime.now().plusMinutes(15);
    }

    public Long getGUIDReset() {
        return GUIDReset;
    }

    public void setGUIDReset(Long gUIDReset) {
        GUIDReset = gUIDReset;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getTentative() {
        return tentative;
    }

    public void setTentative(int tentative) {
        this.tentative = tentative;
    }

    public LocalDateTime getExpiration() {
        return expiration;
    }

    public void setExpiration(LocalDateTime expiration) {
        this.expiration = expiration;
    }

}
