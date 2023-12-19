package es.dtr.job.interview.inditex.ms.infrastructure.configuration.security;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import es.dtr.job.interview.commons.hexagonal.domain.entity.type.Roles;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SecurityUtils {

	public static UUID getLoggedUserId() {
		UUID result = null;

		// Loads authentication information
		final UserDetailsCustom authenticatedUser = getLoggedUser();

		// Get user information from authentication
		if (Objects.nonNull(authenticatedUser)) {
			result = authenticatedUser.getId();
		}

		return result;
	}

	public static Roles getLoggedUserRole() {
		Roles result = null;

		// Loads authentication information
		final UserDetailsCustom authenticatedUser = getLoggedUser();

		// Get user information from authentication
		if (Objects.nonNull(authenticatedUser)) {
			final Optional<Roles> mayKnownRole = authenticatedUser.getAuthorities().stream().findFirst().map(a -> Roles.valueOf(a.getAuthority()));

			if (mayKnownRole.isEmpty()) {
				throw new SessionException("Unknown role. This should not happen.", HttpStatus.INTERNAL_SERVER_ERROR);
			} else {
				result = mayKnownRole.get();
			}
		}

		return result;
	}

	public static UserDetailsCustom getLoggedUser() {
		UserDetailsCustom result = null;

		// Loads authentication information
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		// Get user information from authentication
		if (authentication.getPrincipal() instanceof UserDetailsCustom) {
			result = (UserDetailsCustom) authentication.getPrincipal();
		} else {
            throw new SessionException("Checking logged user without session. This should not happen.", HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return result;
	}

}
