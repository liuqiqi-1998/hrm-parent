package cn.itsource.hrm.mapper;

import cn.itsource.hrm.domain.Course;
import cn.itsource.hrm.query.CourseQuery;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liuqiqi
 * @since 2019-12-30
 */
@Component
public interface CourseMapper extends BaseMapper<Course> {

    Long count(CourseQuery query);

    List<Course> selectByQuery(CourseQuery query);

    IPage<Course> mypage(Page page, @Param("query") CourseQuery query);

    void online(@Param("ids") List<Long> ids, @Param("currentTimeMillis")Long currentTimeMillis);

    void offline(@Param("ids")List<Long> ids, @Param("currentTimeMillis")long currentTimeMillis);
}
