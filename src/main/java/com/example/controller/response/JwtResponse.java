package com.example.controller.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Getter
@RequiredArgsConstructor
public class JwtResponse implements Serializable {

    private static final long serialVersionUID = -3456129853690234589L;

    private final String jwtToken;

}
