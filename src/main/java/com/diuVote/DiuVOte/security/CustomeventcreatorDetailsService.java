package com.diuVote.DiuVOte.security;

import com.diuVote.DiuVOte.entity.EVENTCREATOR;
import com.diuVote.DiuVOte.repository.EventCreatorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomeventcreatorDetailsService implements UserDetailsService {

    private final EventCreatorRepository eventCreatorRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        EVENTCREATOR eventCreator = eventCreatorRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Event creator not found"));

        String role = eventCreator.getRole();

        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);

        return new org.springframework.security.core.userdetails.User(
                eventCreator.getEmail(),
                eventCreator.getPasswordHash(),
                List.of(authority)
        );
    }
}