package cn.itsource.hrm.web.controller;

import cn.itsource.basic.util.AjaxResult;
import cn.itsource.hrm.domain.CourseResource;
import cn.itsource.hrm.service.ICourseResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/courseResources")
public class CourseResourcesController {

    @Autowired
    public ICourseResourceService courseResourceService;

    /**
    * 保存和修改公用的
    * @param courseResource  传递的实体
    * @return Ajaxresult转换结果
    */
    @RequestMapping(value="/save",method= RequestMethod.POST)
    public AjaxResult save(@RequestBody CourseResource courseResource){
        try {
            courseResourceService.save(courseResource);
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
            courseResourceService.removeById(id);
            return AjaxResult.me();
        } catch (Exception e) {
        e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！"+e.getMessage());
        }
    }



   /* @PostMapping("/addPic")
    public AjaxResult addPic(Long courseId){
        try {
            courseService.addPic(courseId);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("失败！");
        }
    }*/


}
