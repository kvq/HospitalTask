package me.kvq.HospitalTask.security;

import lombok.RequiredArgsConstructor;
import me.kvq.HospitalTask.dao.SecurityUserDao;
import me.kvq.HospitalTask.exception.NotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service("securityService")
public class SecurityUserService implements UserDetailsService {
    private final SecurityUserDao dao;

    public SecurityUser getSecurityUser(User principal) {
        return dao.findByUsername(principal.getUsername())
                .orElseThrow(() -> new NotFoundException("Security user not found"));
    }

    public boolean ownsAccount(User principal, long id) {
        SecurityUser securityUser = getSecurityUser(principal);
        if (securityUser.getId() == id) {
            return true;
        }
        return false;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SecurityUser securityUser = dao.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new User(securityUser.getUsername(),
                securityUser.getPassword(),
                roleToAuthorities(securityUser.getRole()));
    }

    private Collection<? extends GrantedAuthority> roleToAuthorities(Role role) {
        return Stream.of(role.getPermissions())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

}
