package Hackaton_5_Semestre.projectGabarito.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String mensagem;
    private String token;
}
