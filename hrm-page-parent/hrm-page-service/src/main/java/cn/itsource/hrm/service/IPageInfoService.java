package cn.itsource.hrm.service;

import cn.itsource.basic.util.PageList;
import cn.itsource.hrm.domain.PageInfo;
import cn.itsource.hrm.query.PageInfoQuery;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liuqiqi
 * @since 2020-01-02
 */
public interface IPageInfoService extends IService<PageInfo> {

    PageList<PageInfo> pageByQuery(PageInfoQuery query);
}
