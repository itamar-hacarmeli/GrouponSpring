package com.jb.GrouponSpring.loginManager;

import lombok.*;

/**
 * ClientType class is a enum class that describing each of predefined client types
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum ClientType {
    ADMINISTRATOR(),
    COMPANY(),
    CUSTOMER();

    private String email;
    private String password;


}
