package cn.itsource.hrm.client.impl;

import cn.itsource.basic.util.AjaxResult;
import cn.itsource.basic.util.PageList;
import cn.itsource.hrm.client.SystemDictionaryitemClient;
import cn.itsource.hrm.domain.Systemdictionaryitem;
import cn.itsource.hrm.query.SystemdictionaryitemQuery;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SystemDictonaryitemCientImpl implements SystemDictionaryitemClient {
    @Override
    public AjaxResult save(Systemdictionaryitem systemdictionaryitem) {
        return null;
    }

    @Override
    public AjaxResult delete(Long id) {
        return null;
    }

    @Override
    public Systemdictionaryitem get(Long id) {
        return null;
    }

    @Override
    public List<Systemdictionaryitem> list() {
        return null;
    }

    @Override
    public PageList<Systemdictionaryitem> page(SystemdictionaryitemQuery query) {
        return null;
    }
}
