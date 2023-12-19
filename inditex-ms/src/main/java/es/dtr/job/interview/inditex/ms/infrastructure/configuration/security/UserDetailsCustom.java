package es.dtr.job.interview.inditex.ms.infrastructure.configuration.security;

import java.io.Serial;
import java.util.Collection;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;

@Getter
public class UserDetailsCustom extends User {

	@Serial
	private static final long serialVersionUID = -6037179476306682497L;

	private final UUID id;

	public UserDetailsCustom(final UUID id, final String username, final String password,
			final Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		this.id = id;
	}
}