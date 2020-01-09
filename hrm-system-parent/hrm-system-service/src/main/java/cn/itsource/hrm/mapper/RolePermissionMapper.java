package cn.itsource.hrm.mapper;

import cn.itsource.hrm.domain.RolePermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liuqiqi
 * @since 2019-12-29
 */
@Component
public interface RolePermissionMapper extends BaseMapper<RolePermission> {


    void insertBatch(List<RolePermission> list);
}
