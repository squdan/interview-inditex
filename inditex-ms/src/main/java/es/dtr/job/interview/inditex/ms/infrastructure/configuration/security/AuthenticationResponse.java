package es.dtr.job.interview.inditex.ms.infrastructure.configuration.security;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class AuthenticationResponse {

    String username;
    String jwt;
}
