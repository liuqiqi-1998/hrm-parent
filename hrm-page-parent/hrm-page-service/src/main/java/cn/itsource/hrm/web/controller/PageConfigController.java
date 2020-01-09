package cn.itsource.hrm.web.controller;

import cn.itsource.hrm.service.IPageConfigService;
import cn.itsource.hrm.domain.PageConfig;
import cn.itsource.hrm.query.PageConfigQuery;
import cn.itsource.basic.util.AjaxResult;
import cn.itsource.basic.util.PageList;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pageConfig")
public class PageConfigController {
    @Autowired
    public IPageConfigService pageConfigService;

    /**
    * 保存和修改公用的
    * @param pageConfig  传递的实体
    * @return Ajaxresult转换结果
    */
    @RequestMapping(value="/save",method= RequestMethod.POST)
    public AjaxResult save(@RequestBody PageConfig pageConfig){
        try {
            if(pageConfig.getId()!=null){
                pageConfigService.updateById(pageConfig);
            }else{
                pageConfigService.save(pageConfig);
            }
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("保存对象失败！"+e.getMessage());
        }
    }

    /**
    * 删除对象信息
    * @param id
    * @return
    */
    @RequestMapping(value="/{id}",method=RequestMethod.DELETE)
    public AjaxResult delete(@PathVariable("id") Long id){
        try {
            pageConfigService.removeById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！"+e.getMessage());
        }
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public PageConfig get(@PathVariable("id")Long id)
    {
        return pageConfigService.getById(id);
    }


    /**
    * 查看所有信息
    * @return
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<PageConfig> list(){

        return pageConfigService.list(null);
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @RequestMapping(value = "/page",method = RequestMethod.POST)
    public PageList<PageConfig> page(@RequestBody PageConfigQuery query)
    {
        Page<PageConfig> page = pageConfigService.page(new Page<PageConfig>(query.getPage(), query.getRows()));
        return new PageList<>(page.getTotal(),page.getRecords());
    }
}
