package es.dtr.job.interview.inditex.ms.infrastructure.configuration.security;

import es.dtr.job.interview.inditex.ms.domain.entity.UserEntity;
import es.dtr.job.interview.inditex.ms.domain.repository.UserDomainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    // Dependencies
    private final UserDomainRepository userDomainRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        // Load user information
        final UserEntity user = userDomainRepository.findByUsername(username);

        // Generate UserDetails
        final List<SimpleGrantedAuthority> roles = Collections
                .singletonList(new SimpleGrantedAuthority(user.getRole().name()));
        return new UserDetailsCustom(user.getId(), user.getUsername(), user.getPassword(), roles);
    }

}
