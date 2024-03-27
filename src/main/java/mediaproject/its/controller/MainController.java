package mediaproject.its.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

@Controller
@ResponseBody
public class MainController {

    @GetMapping("/main")
    public String mainPage(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
        GrantedAuthority auth = iter.next();

        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        String userRole = auth.getAuthority();

        return "main controller" + userName + userRole;
    }

}
