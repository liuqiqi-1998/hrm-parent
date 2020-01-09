package cn.itsource.hrm.service;

import cn.itsource.hrm.domain.CourseType;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程目录 服务类
 * </p>
 *
 * @author liuqiqi
 * @since 2019-12-30
 */
public interface ICourseTypeService extends IService<CourseType> {

    List<CourseType> loadTreeData();

    void staticCoursePage(Long pageId);

}
