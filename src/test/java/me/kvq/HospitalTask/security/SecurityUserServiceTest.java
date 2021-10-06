package me.kvq.HospitalTask.security;

import me.kvq.HospitalTask.dao.SecurityUserDao;
import me.kvq.HospitalTask.exception.NotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

import static me.kvq.HospitalTask.testData.TestDataGenerator.validSecurityUser;
import static me.kvq.HospitalTask.testData.TestDataGenerator.validUserDetails;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class SecurityUserServiceTest {
    @MockBean
    SecurityUserDao dao;
    @Autowired
    SecurityUserService service;

    @Test
    @DisplayName("Gets SecurityUser from User")
    void getValidSecurityUserTest() {
        User user = validUserDetails();
        SecurityUser expected = validSecurityUser();
        when(dao.findByUsername(user.getUsername())).thenReturn(Optional.of(expected));
        SecurityUser actual = service.getSecurityUser(user);
        assertEquals(expected, actual);
        verify(dao, times(1)).findByUsername(user.getUsername());
    }

    @Test
    @DisplayName("Gets SecurityUser that does not exists, exception expected")
    void getNonExistentSecurityUserTest() {
        User user = validUserDetails();
        when(dao.findByUsername(user.getUsername())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> {
            service.getSecurityUser(user);
        });
        verify(dao, times(1)).findByUsername(user.getUsername());
    }

    @Test
    @DisplayName("Expects true if id same as user's")
    void validUserOwnsAccountTest() {
        User user = validUserDetails();
        SecurityUser securityUser = validSecurityUser();
        when(dao.findByUsername(user.getUsername())).thenReturn(Optional.of(securityUser));
        assertTrue(service.ownsAccount(user, securityUser.getId()));
        verify(dao, times(1)).findByUsername(user.getUsername());
    }

    @Test
    @DisplayName("Checks result if user not found, expects exception")
    void invalidUserOwnsAccountTest() {
        User user = validUserDetails();
        SecurityUser securityUser = validSecurityUser();
        when(dao.findById(user.getUsername())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> {
            service.ownsAccount(user, securityUser.getId());
        });
        verify(dao, times(1)).findByUsername(user.getUsername());
    }

    @Test
    @DisplayName("Gets user by its username, expects valid UserDetails")
    void loadUserByUsernameTest() {
        SecurityUser expected = validSecurityUser();
        when(dao.findByUsername(expected.getUsername())).thenReturn(Optional.of(expected));
        UserDetails actual = service.loadUserByUsername(expected.getUsername());
        assertEquals(expected.getUsername(), actual.getUsername());
        assertEquals(expected.getPassword(), actual.getPassword());
        compareAuthorities(expected.getRole().getPermissions(), actual.getAuthorities());
        verify(dao, times(1)).findByUsername(expected.getUsername());
    }

    @Test
    @DisplayName("Gets non-existent user by username, expects exception")
    void loadUserByInvalidUsernameTest() {
        SecurityUser user = validSecurityUser();
        when(dao.findByUsername(user.getUsername())).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> {
            service.loadUserByUsername(user.getUsername());
        });
        verify(dao, times(1)).findByUsername(user.getUsername());
    }

    private void compareAuthorities(String[] expected, Collection<? extends GrantedAuthority> actual) {
        HashSet<String> expectedSet = new HashSet<>(Arrays.asList(expected));
        HashSet<String> actualSet = new HashSet<>();
        for (GrantedAuthority authority : actual) {
            actualSet.add(authority.getAuthority());
        }
        assertTrue(expectedSet.equals(actualSet));
    }

}
