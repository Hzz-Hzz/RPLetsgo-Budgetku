package xyz.rpletsgo.auth.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import xyz.rpletsgo.common.core.IPengguna;

import java.time.LocalDateTime;

@Entity
@Table
public class Pengguna implements IPengguna {
    @Getter
    @Id
    @Column(name = "username", updatable = false)
    private String username;
    
    @Getter
    @Setter
    @Column(name = "password")
    private String password;
    
    @Getter
    @Setter
    @Column(name = "email")
    private String email;
    
    @Getter
    @Column(name = "lastLoginDate")
    private LocalDateTime lastLoginDate;
    
    
    public Pengguna(String username, String password, String email){
        this.username = username;
        this.password = password;
        this.email = email;
    }
    
    protected Pengguna(){}
}