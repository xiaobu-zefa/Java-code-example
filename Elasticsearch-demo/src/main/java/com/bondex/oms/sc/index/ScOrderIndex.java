package com.bondex.oms.sc.index;

import com.bondex.oms.sc.esentity.ScOrderDocType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScOrderIndex extends ElasticsearchRepository<ScOrderDocType, Long> {
}
