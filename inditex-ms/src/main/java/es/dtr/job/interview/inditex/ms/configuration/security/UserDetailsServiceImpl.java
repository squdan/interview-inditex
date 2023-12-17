package es.dtr.job.interview.inditex.ms.configuration.security;

import java.util.Collections;
import java.util.List;

import es.dtr.job.interview.inditex.ms.adapter.out.database.hibernate.UserRepository;
import es.dtr.job.interview.inditex.ms.domain.entity.UserEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

	// Dependencies
	private final UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		// Load user information
		final UserEntity user = userRepository.findByUsername(username)
				.orElseThrow(() -> new SessionException("El usuario no existe.", HttpStatus.NOT_FOUND));

		// Generate UserDetails
		final List<SimpleGrantedAuthority> roles = Collections
				.singletonList(new SimpleGrantedAuthority(user.getRole().name()));
		return new UserDetailsCustom(user.getId(), user.getUsername(), user.getPassword(), roles);
	}
}
