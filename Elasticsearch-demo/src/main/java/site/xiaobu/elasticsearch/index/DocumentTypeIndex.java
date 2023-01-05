package site.xiaobu.elasticsearch.index;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import site.xiaobu.elasticsearch.type.DocumentType;

@Repository
public interface DocumentTypeIndex extends ElasticsearchRepository<DocumentType, Long> {
}
