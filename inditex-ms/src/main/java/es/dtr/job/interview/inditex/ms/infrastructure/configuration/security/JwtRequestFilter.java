package es.dtr.job.interview.inditex.ms.infrastructure.configuration.security;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

	// Constant
	private static final String HEADER_AUTH_PREFIX = "Bearer ";

	// Dependencies
	private final AuthenticationManagerCustom authenticationManager;

	@Override
	protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
			final FilterChain filterChain) throws ServletException, IOException {

		log.info("Validating JWT [({}) {} - {}].", request.getRequestURI(), request.getRemoteAddr(),
				request.getRemoteHost());

		// Loads the user information
		final HttpServletRequest httpServletRequest = (HttpServletRequest) request;

		// Checks if JWT has been received
		final String jwt = getBearerJwtToken(httpServletRequest);

		if (StringUtils.isNotBlank(jwt)) {
			final UserDetails userDetails = authenticationManager.validate(jwt);

			// Authenticates the user
			final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
					userDetails, null, userDetails.getAuthorities());
			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}

		// Allows the access to the application
		filterChain.doFilter(request, response);
	}

	private String getBearerJwtToken(final HttpServletRequest request) {
		String result = null;

		// Reads authorization / bearer header
		final String headerAuth = request.getHeader(HttpHeaders.AUTHORIZATION);

		if (StringUtils.isNotBlank(headerAuth) && headerAuth.startsWith(HEADER_AUTH_PREFIX)) {
			result = headerAuth.substring(HEADER_AUTH_PREFIX.length(), headerAuth.length());
		}

		return result;
	}
}
