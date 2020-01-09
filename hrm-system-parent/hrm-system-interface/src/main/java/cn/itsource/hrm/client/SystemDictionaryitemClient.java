package cn.itsource.hrm.client;

import cn.itsource.basic.util.AjaxResult;
import cn.itsource.basic.util.PageList;
import cn.itsource.hrm.client.impl.SystemDictonaryitemCientImpl;
import cn.itsource.hrm.domain.Systemdictionaryitem;
import cn.itsource.hrm.query.SystemdictionaryitemQuery;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(path = "/systemdictionaryitem",value = "SYSTEM-SERVICE",fallback = SystemDictonaryitemCientImpl.class)
public interface SystemDictionaryitemClient {

    @RequestMapping(value="/save",method= RequestMethod.POST)
    public AjaxResult save(@RequestBody Systemdictionaryitem systemdictionaryitem);

    @RequestMapping(value="/{id}",method=RequestMethod.DELETE)
    public AjaxResult delete(@PathVariable("id") Long id);

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Systemdictionaryitem get(@PathVariable("id")Long id);

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<Systemdictionaryitem> list();

    @RequestMapping(value = "/page",method = RequestMethod.POST)
    public PageList<Systemdictionaryitem> page(@RequestBody SystemdictionaryitemQuery query);
}
