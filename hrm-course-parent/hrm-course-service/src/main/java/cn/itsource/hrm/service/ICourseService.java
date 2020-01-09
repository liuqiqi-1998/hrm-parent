package cn.itsource.hrm.service;

import cn.itsource.basic.util.PageList;
import cn.itsource.hrm.document.CourseDocument;
import cn.itsource.hrm.domain.Course;
import cn.itsource.hrm.query.CourseQuery;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liuqiqi
 * @since 2019-12-30
 */
public interface ICourseService extends IService<Course> {

    PageList<Course> pageByQuery(CourseQuery query);

    void online(List<Long> ids);
    void offline(List<Long> ids);

    PageList<CourseDocument> pageOnline(CourseQuery query);
}
