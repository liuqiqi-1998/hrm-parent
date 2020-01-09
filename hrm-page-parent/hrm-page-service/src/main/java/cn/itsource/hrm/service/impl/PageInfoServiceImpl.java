package cn.itsource.hrm.service.impl;

import cn.itsource.basic.util.PageList;
import cn.itsource.hrm.domain.PageConfig;
import cn.itsource.hrm.domain.PageInfo;
import cn.itsource.hrm.domain.Site;
import cn.itsource.hrm.mapper.PageConfigMapper;
import cn.itsource.hrm.mapper.PageInfoMapper;
import cn.itsource.hrm.query.PageInfoQuery;
import cn.itsource.hrm.service.IPageInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liuqiqi
 * @since 2020-01-02
 */
@Service
public class PageInfoServiceImpl extends ServiceImpl<PageInfoMapper, PageInfo> implements IPageInfoService {

    @Autowired
    private PageConfigMapper pageConfigMapper;
    @Override
    public PageList<PageInfo> pageByQuery(PageInfoQuery query) {
        IPage<PageInfo> mypage = baseMapper.mypage(new Page(query.getPage(), query.getRows()), query);
        return new PageList<>(mypage.getTotal(),mypage.getRecords());
    }

    //重写一下保存的方法，保存config配置的初始值
    @Override
    @Transactional
    public boolean save(PageInfo entity) {
        super.save(entity);
        PageConfig pageConfig = new PageConfig();
        pageConfig.setTemplateUrl(entity.getTemplateUrl());
        pageConfig.setPhysicalPath(entity.getPhysicalPath());
        pageConfig.setDfsType(0L);
        pageConfig.setPageId(entity.getId());
        pageConfigMapper.insert(pageConfig);
        return true;
    }
}
