package com.cursospring.vendas.dto;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenDTO {
    private  String login;
    private String token;

}
