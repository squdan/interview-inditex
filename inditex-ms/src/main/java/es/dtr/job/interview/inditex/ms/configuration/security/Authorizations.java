package es.dtr.job.interview.inditex.ms.configuration.security;

public interface Authorizations {

    interface Authorities {
        String ADMIN = "ADMIN";
        String USER = "USER";
    }

    String ADMIN = "hasRole('ADMIN')";
    String USER = "hasRole('USER')";
}
