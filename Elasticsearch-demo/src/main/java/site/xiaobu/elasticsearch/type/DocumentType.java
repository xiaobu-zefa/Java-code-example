package site.xiaobu.elasticsearch.type;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Document(indexName = "test.index", createIndex = true)
public class DocumentType implements Serializable {

    @Id
    @Field(type = FieldType.Long)
    private String id;

    @Field(type = FieldType.Keyword)
    private String name;

    @Field(type = FieldType.Text, searchAnalyzer = "ik_smart", analyzer = "ik_max_word")
    private String remark;

    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second, pattern = "yyyy-MM-dd HH:mm:ss:SSS")
    private LocalDateTime createTime;

    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second, pattern = "yyyy-MM-dd HH:mm:ss:SSS")
    private LocalDateTime modifyTime;

    @Field(type = FieldType.Boolean)
    private Boolean isPrivate;

    @Field(type = FieldType.Boolean)
    private Boolean isImportant;
}
