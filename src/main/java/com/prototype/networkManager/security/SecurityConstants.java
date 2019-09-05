package com.prototype.networkManager.security;

final class SecurityConstants {

    static final String AUTH_LOGIN_URL = "/api/authenticate";
    static final String JWT_SECRET = "Yp3s6v9y$B&E)H@McQfThWmZq4t7w!z%C*F-JaNdRgUkXn2r5u8x/A?D(G+KbPeS";

    static final String TOKEN_HEADER = "Authorization";
    static final String TOKEN_PREFIX = "";
    static final String TOKEN_TYPE = "JWT";
    static final String TOKEN_ISSUER = "secure-api";
    static final String TOKEN_AUDIENCE = "secure-app";

    private SecurityConstants() {
        throw new IllegalStateException("Cannot create instance of static util class");
    }
}
