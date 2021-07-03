package com.superops.movie.security;

import com.superops.movie.security.services.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

public final class SecurityUtils {

    private SecurityUtils() {
    }

    public static UUID getUserId() {
        return getCustomUserDetails().getId();
    }

    public static String getUsername() {
        return getCustomUserDetails().getUsername();
    }

    private static UserDetailsImpl getCustomUserDetails() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        return (UserDetailsImpl) authentication.getPrincipal();
    }

}
