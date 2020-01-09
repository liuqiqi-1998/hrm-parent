package cn.itsource.hrm.mapper;

import cn.itsource.hrm.domain.Site;
import cn.itsource.hrm.query.SiteQuery;
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
public interface SiteMapper extends BaseMapper<Site> {

    IPage<Site> mypage(Page page, SiteQuery query);
}
