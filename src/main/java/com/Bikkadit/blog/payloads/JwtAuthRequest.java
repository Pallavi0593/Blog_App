package com.Bikkadit.blog.payloads;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class JwtAuthRequest {
@NotNull
private String userName;
@NotNull
private String password;
}
