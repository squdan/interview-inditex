package es.dtr.job.interview.inditex.ms.adapter.in;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.dtr.job.interview.inditex.ms.configuration.security.AuthenticationManagerCustom;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

public abstract class ControllerBaseTest {

    // Dependencies
    @Getter
    @Autowired
    protected ObjectMapper objectMapper;

    // Mock REST
    @Getter
    @Autowired
    protected MockMvc mockMvc;

    // Mocks
    @MockBean
    protected AuthenticationManagerCustom authenticationManagerCustom;
}
