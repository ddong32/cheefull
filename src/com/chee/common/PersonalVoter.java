package com.chee.common;

import com.chee.entity.Role;
import com.chee.entity.User;
import com.chee.service.UserService;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationManager;
import org.springframework.security.ConfigAttribute;
import org.springframework.security.ConfigAttributeDefinition;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.intercept.web.FilterInvocation;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.security.ui.AuthenticationDetailsSource;
import org.springframework.security.ui.WebAuthenticationDetailsSource;
import org.springframework.security.vote.AccessDecisionVoter;

public class PersonalVoter implements AccessDecisionVoter {
    @Resource
    private UserService userService;
    private AuthenticationManager authenticationManager;
    private AuthenticationDetailsSource authenticationDetailsSource = new WebAuthenticationDetailsSource();

    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    public boolean supports(Class clazz) {
        return true;
    }

    public int vote(Authentication authentication, Object object, ConfigAttributeDefinition config) {
        int result = 0;
        FilterInvocation filterInvocation = (FilterInvocation) object;
        HttpServletRequest request = filterInvocation.getHttpRequest();
        String username = request.getHeader("username") == null ? request.getParameter("username") : request.getHeader("username");
        String password = request.getHeader("password") == null ? request.getParameter("password") : request.getHeader("password");
        if ((StringUtils.isNotEmpty(username)) && (StringUtils.isNotEmpty(password))) {
            String passwordMd5 = DigestUtils.md5Hex(password);
            List<?> userList = this.userService.getUserList(username, passwordMd5);
            if (userList.size() > 0) {
                request.setAttribute("username", username);
                request.setAttribute("password", password);
                UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
                authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
                Authentication authenticate = getAuthenticationManager().authenticate(authRequest);
                if ((authenticate != null) && (authenticate.isAuthenticated())) {
                    SecurityContextHolder.getContext().setAuthentication(authenticate);
                    result = 1;
                }
            }
        }
        return result;
    }

    public GrantedAuthority[] getGrantedAuthorities(User user) {
        Set<GrantedAuthorityImpl> grantedAuthorities = new HashSet();
        for (Role role : user.getRoleSet()) {
            grantedAuthorities.add(new GrantedAuthorityImpl(role.getValue()));
        }
        return (GrantedAuthority[]) grantedAuthorities.toArray(new GrantedAuthority[grantedAuthorities.size()]);
    }

    public AuthenticationManager getAuthenticationManager() {
        return this.authenticationManager;
    }

    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationDetailsSource getAuthenticationDetailsSource() {
        return this.authenticationDetailsSource;
    }

    public void setAuthenticationDetailsSource(AuthenticationDetailsSource authenticationDetailsSource) {
        this.authenticationDetailsSource = authenticationDetailsSource;
    }
}
