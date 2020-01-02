package net.smiguel.sample.app.sampleapp.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.smiguel.sample.app.sampleapp.model.Person;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

@RefreshScope
@Configuration
@ConfigurationProperties("app")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonProperties {
    private Person person;
}
