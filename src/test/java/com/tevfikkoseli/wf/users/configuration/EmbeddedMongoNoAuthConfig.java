package com.tevfikkoseli.wf.users.configuration;


import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import de.flapdoodle.embed.mongo.commands.MongodArguments;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;

import de.flapdoodle.embed.mongo.packageresolver.Command;
import de.flapdoodle.embed.mongo.transitions.Mongod;
import de.flapdoodle.embed.mongo.transitions.RunningMongodProcess;
import de.flapdoodle.reverse.Transition;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class EmbeddedMongoNoAuthConfig {

    private RunningMongodProcess mongod;

    @Bean(destroyMethod = "close")
    public MongoClient reactiveMongoClient() throws Exception {

        MongodArguments args = MongodArguments.defaults()
                .withAuth(false) // disable authentication
                .withUseNoJournal(false);
        // ✅ Yeni yapılandırma (immutable config)
        mongod = Mongod.builder()
                .build()
                .start(Version.Main.V7_0).current();

        int port = mongod.getServerAddress().getPort();
        System.out.println("🚀 Embedded Mongo started on port: " + port);

        // ✅ create reactive client
        return MongoClients.create("mongodb://localhost:" + port);
    }
}
