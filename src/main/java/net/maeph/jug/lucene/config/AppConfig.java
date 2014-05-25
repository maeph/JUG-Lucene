package net.maeph.jug.lucene.config;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.ConcurrentUpdateSolrServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Configuration
@ComponentScan("net.maeph.jug.lucene")
public class AppConfig {

    @Autowired
    private ResourceLoader resourceLoader;
    
    
    @Bean(name = "jsonSource")
    public Reader jsonSource() throws IOException {
        Resource resource = resourceLoader.getResource("/dataset.json");
        return Files.newBufferedReader(Paths.get(resource.getURI()));
    }
    
    @Bean
    public SolrServer solrServer() {
        return new ConcurrentUpdateSolrServer("http://localhost:8983/solr/jug", 100, 10);
    }
    
}
