package cn.itsource.hrm.service;

import cn.itsource.basic.util.PageList;
import cn.itsource.hrm.domain.Site;
import cn.itsource.hrm.query.SiteQuery;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liuqiqi
 * @since 2020-01-02
 */
public interface ISiteService extends IService<Site> {

    PageList<Site> pageByQuery(SiteQuery query);
}
