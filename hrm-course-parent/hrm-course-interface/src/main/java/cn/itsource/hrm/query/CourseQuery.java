package cn.itsource.hrm.query;



import cn.itsource.basic.query.BaseQuery;
import lombok.Data;


/**
 * <p>
 *  查询参数对象
 * </p>
 *
 * @author liuqiqi
 * @since 2019-12-30
 */
@Data
public class CourseQuery extends BaseQuery {

    private Integer status;

}