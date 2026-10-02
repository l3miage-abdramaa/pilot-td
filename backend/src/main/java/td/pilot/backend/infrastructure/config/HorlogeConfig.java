package td.pilot.backend.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.Clock;
import java.time.ZoneId;


@Configuration
public class HorlogeConfig {
    @Bean
    public Clock horloge() {
        return Clock.system(ZoneId.of("Africa/Ndjamena"));
    }
}