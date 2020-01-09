package cn.itsource.hrm.web.controller;

import cn.itsource.hrm.service.IPageInfoService;
import cn.itsource.hrm.domain.PageInfo;
import cn.itsource.hrm.query.PageInfoQuery;
import cn.itsource.basic.util.AjaxResult;
import cn.itsource.basic.util.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pageInfo")
public class PageInfoController {
    @Autowired
    public IPageInfoService pageInfoService;

    /**
    * 保存和修改公用的
    * @param pageInfo  传递的实体
    * @return Ajaxresult转换结果
    */
    @RequestMapping(value="/save",method= RequestMethod.POST)
    public AjaxResult save(@RequestBody PageInfo pageInfo){
        try {
            if(pageInfo.getId()!=null){
                pageInfoService.updateById(pageInfo);
            }else{
                pageInfo.setCreateTime(System.currentTimeMillis());
                pageInfoService.save(pageInfo);
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
            pageInfoService.removeById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！"+e.getMessage());
        }
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public PageInfo get(@PathVariable("id")Long id)
    {
        return pageInfoService.getById(id);
    }


    /**
    * 查看所有信息
    * @return
    */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<PageInfo> list(){

        return pageInfoService.list(null);
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @RequestMapping(value = "/page",method = RequestMethod.POST)
    public PageList<PageInfo> page(@RequestBody PageInfoQuery query)
    {
        //Page<PageInfo> page = pageInfoService.page(new Page<PageInfo>(query.getPage(), query.getRows()));
        return pageInfoService.pageByQuery(query);
    }
}
