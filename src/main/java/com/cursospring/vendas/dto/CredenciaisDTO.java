package com.cursospring.vendas.dto;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Data
public class CredenciaisDTO {
    private String login;
    private String senha;
}
