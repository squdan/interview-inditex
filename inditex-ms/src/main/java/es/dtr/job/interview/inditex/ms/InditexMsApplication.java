package es.dtr.job.interview.inditex.ms;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.TimeZone;

@SpringBootApplication
public class InditexMsApplication {

    public static void main(String[] args) {
        // Configure default timezone
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

        // Configuring spring app launch with headless false o avoid HeadlessException
        // using Swing
        SpringApplicationBuilder builder = new SpringApplicationBuilder(InditexMsApplication.class);
        builder.headless(false);
        builder.run(args);
    }
}
