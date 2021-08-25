package com.jb.GrouponSpring.User;

import com.jb.GrouponSpring.loginManager.ClientType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetails {
    private String email;
    private String password;
    private ClientType clientType;
    private int userId;

    public UserDetails(String email, int userId, ClientType clientType) {
        this.email = email;
        this.clientType = clientType;
        this.userId = userId;
    }
    public UserDetails(String email,String password) {
        this.email = email;
        this.password=password;
    }

}