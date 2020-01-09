package cn.itsource.hrm.mapper;

import cn.itsource.hrm.domain.PageInfo;
import cn.itsource.hrm.query.PageInfoQuery;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liuqiqi
 * @since 2020-01-02
 */
public interface PageInfoMapper extends BaseMapper<PageInfo> {

    IPage<PageInfo> mypage(Page page, PageInfoQuery query);
}
