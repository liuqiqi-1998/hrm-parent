package cn.itsource.hrm.repository;

import cn.itsource.hrm.document.CourseDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface CourseRepository extends ElasticsearchRepository<CourseDocument,Long> {

}
