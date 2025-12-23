package com.saas.auth.payload.response;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String email;
    private List<String> roles;
    private Long tenantId;

    public JwtResponse(String accessToken, Long id, String username, String email, Long tenantId, List<String> roles) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.tenantId = tenantId;
        this.roles = roles;
    }
}
