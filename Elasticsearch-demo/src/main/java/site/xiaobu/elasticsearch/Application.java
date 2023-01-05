package site.xiaobu.elasticsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import site.xiaobu.elasticsearch.config.ElasticRestClientConfig;

@SpringBootApplication
//@Import(ElasticRestClientConfig.class)
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
