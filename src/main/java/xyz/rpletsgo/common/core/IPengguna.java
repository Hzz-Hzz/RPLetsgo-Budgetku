package xyz.rpletsgo.common.core;

import java.time.LocalDateTime;

public interface IPengguna {
    public String getUsername();
    public String getPassword();
    public String getName();
    public String getEmail();
    public LocalDateTime getLastLoginDate();
    
    public void setPassword(String value);
    public void setName(String value);
    public void setEmail(String value);
}
