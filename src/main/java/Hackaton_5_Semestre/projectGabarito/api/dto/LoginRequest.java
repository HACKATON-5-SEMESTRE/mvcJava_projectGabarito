package Hackaton_5_Semestre.projectGabarito.api.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String login;
    private String senha;
}
