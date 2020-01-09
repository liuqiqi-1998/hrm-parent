package cn.itsource.hrm.service.impl;

import cn.itsource.basic.util.PageList;
import cn.itsource.hrm.domain.Site;
import cn.itsource.hrm.mapper.SiteMapper;
import cn.itsource.hrm.query.SiteQuery;
import cn.itsource.hrm.service.ISiteService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liuqiqi
 * @since 2020-01-02
 */
@Service
public class SiteServiceImpl extends ServiceImpl<SiteMapper, Site> implements ISiteService {

    @Override
    public PageList<Site> pageByQuery(SiteQuery query) {
        IPage<Site> mypage = baseMapper.mypage(new Page(query.getPage(), query.getRows()), query);
        return new PageList<>(mypage.getTotal(),mypage.getRecords());
    }
}
