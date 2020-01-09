package cn.itsource.hrm.client.impl;

import cn.itsource.basic.util.AjaxResult;
import cn.itsource.hrm.client.StaticPageClient;
import org.springframework.stereotype.Component;

@Component
public class StaticPageImpl implements StaticPageClient {

    @Override
    public AjaxResult staticPages(String dataKey, Long pageId) {
        return AjaxResult.me().setSuccess(false).setMessage("失败了，托底数据！");
    }
}
