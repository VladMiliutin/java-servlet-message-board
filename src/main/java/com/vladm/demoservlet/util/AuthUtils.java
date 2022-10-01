package com.vladm.demoservlet.util;

import com.vladm.demoservlet.model.UserPrincipal;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Base64;

import static com.vladm.demoservlet.model.RequestParams.ID;

public class AuthUtils {

    private final static Base64.Decoder DECODER = Base64.getDecoder();

    public static UserPrincipal getUserInfoFromReq(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        String id = null;
        if(request.getHeader(ID) != null){
            id = request.getHeader(ID);
        }

        if(authHeader != null && authHeader.substring(0, 5).equalsIgnoreCase(HttpServletRequest.BASIC_AUTH)) {
            String usernameAndPassword = new String(DECODER.decode(authHeader.substring(6)));
            String username = usernameAndPassword.split(":")[0];
            String password = usernameAndPassword.split(":")[1];

            return new UserPrincipal(id, username, password);
        }

        return null;
    }
}
