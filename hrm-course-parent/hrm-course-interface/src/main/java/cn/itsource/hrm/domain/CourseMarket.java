package cn.itsource.hrm.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author liuqiqi
 * @since 2019-12-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_course_market")
public class CourseMarket extends Model<CourseMarket> {

    private static final long serialVersionUID=1L;

    /**
     * 课程id
     */
    private Long id;

    /**
     * 收费规则，对应数据字典
     */
    private Long charge;

    /**
     * 有效性，对应数据字典
     */
    private Long valid;

    /**
     * 杩囨湡鏃堕棿
     */
    private Long expires;

    /**
     * 咨询qq
     */
    private String qq;

    /**
     * 价格
     */
    private Float price;

    /**
     * 原价
     */
    private Float priceOld;

    private Long courseId;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
