package com.tevfikkoseli.wf.users.configuration;

import de.flapdoodle.embed.mongo.distribution.IFeatureAwareVersion;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.mongo.distribution.Versions;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class EmbeddedMongoTestConfig {
    @Bean
    public IFeatureAwareVersion embeddedMongoVersion() {
        return Versions.withFeatures(Version.V6_0_5);
    }
}
