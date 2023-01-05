package site.xiaobu.elasticsearch.config;

import cn.hutool.core.date.DateUtil;
import lombok.NonNull;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchCustomConversions;
import org.springframework.data.elasticsearch.core.convert.MappingElasticsearchConverter;
import org.springframework.data.elasticsearch.core.mapping.SimpleElasticsearchMappingContext;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.http.HttpHeaders;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Date;

@Configuration
@EnableElasticsearchRepositories(basePackages = {"com.**", "site.**"})
public class ElasticRestClientConfig extends AbstractElasticsearchConfiguration {

    @Value("${spring.elasticsearch.rest.uris}")
    private String url;
    @Value("${spring.elasticsearch.rest.username}")
    private String username;
    @Value("${spring.elasticsearch.rest.password}")
    private String password;

    @PostConstruct
    public void init() {
        System.out.println("配置初始化......");
    }

    @Override
    public RestHighLevelClient elasticsearchClient() {
        url = url.replace("http://", "");
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(username, password);
        final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(url)
                .withDefaultHeaders(headers)
                .build();
        return RestClients.create(clientConfiguration).rest();
    }

    /**
     * 指定日期转换器，解决日期转换错误问题
     */
    @Override
    @Bean
    public ElasticsearchCustomConversions elasticsearchCustomConversions() {
        return new ElasticsearchCustomConversions(Arrays.asList(StringToDateConverter.INSTANT, DateToStringConverter.INSTANT));
    }

    @Bean
    public ElasticsearchConverter elasticsearchConverter(SimpleElasticsearchMappingContext mappingContext, ElasticsearchCustomConversions elasticsearchCustomConversions) {
        MappingElasticsearchConverter mappingElasticsearchConverter = new MappingElasticsearchConverter(mappingContext);
        mappingElasticsearchConverter.setConversions(elasticsearchCustomConversions);
        return mappingElasticsearchConverter;
    }

    /**
     * 字符串转换日期
     */
    @ReadingConverter
    private enum StringToDateConverter implements Converter<String, java.util.Date> {
        /**
         * 转换器实例
         */
        INSTANT;

        @Override
        public Date convert(@NonNull String source) {
            return DateUtil.parseDateTime(source);
        }
    }

    /**
     * 字符串转换日期
     */
    @WritingConverter
    private enum DateToStringConverter implements Converter<Date, String> {
        /**
         * 转换器实例
         */
        INSTANT;

        @Override
        public String convert(@NonNull Date source) {
            return DateUtil.formatDateTime(source);
        }
    }
}
