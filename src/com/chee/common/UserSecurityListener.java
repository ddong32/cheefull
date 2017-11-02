package com.chee.common;

import com.chee.entity.User;
import com.chee.service.UserService;
import java.util.Date;
import javax.annotation.Resource;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.Authentication;
import org.springframework.security.event.authentication.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.event.authentication.AuthenticationSuccessEvent;
import org.springframework.security.ui.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class UserSecurityListener implements ApplicationListener {
    @Resource
    private UserService userService;
    String sessionIp = null;

    public void onApplicationEvent(ApplicationEvent event) {
        if ((event instanceof AuthenticationSuccessEvent)) {
            AuthenticationSuccessEvent authEvent = (AuthenticationSuccessEvent) event;
            Authentication authentication = (Authentication) authEvent.getSource();
            String loginIp = ((WebAuthenticationDetails) authentication.getDetails()).getRemoteAddress();
            User user = (User) authentication.getPrincipal();
            user.setLoginIp(loginIp);
            user.setLoginDate(new Date());
            user.setLoginFailureCount(Integer.valueOf(0));
            this.userService.update(user);
        }
        if ((event instanceof AuthenticationFailureBadCredentialsEvent)) {
            AuthenticationFailureBadCredentialsEvent authEvent = (AuthenticationFailureBadCredentialsEvent) event;
            Authentication authentication = (Authentication) authEvent.getSource();
            String loginUsername = authentication.getName();
            User user = (User) this.userService.get("username", loginUsername);
            if (user != null) {
                int loginFailureCount = user.getLoginFailureCount().intValue() + 1;
                if (loginFailureCount >= 5) {
                    user.setIsAccountLocked(Boolean.valueOf(true));
                    user.setLockedDate(new Date());
                }
                user.setLoginFailureCount(Integer.valueOf(loginFailureCount));
                this.userService.update(user);
            }
        }
    }
}
