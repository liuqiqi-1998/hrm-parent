package cn.itsource.hrm.file;

import cn.itsource.basic.util.AjaxResult;
import cn.itsource.hrm.util.FastDfsApiOpr;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Api(tags = "文件上传的接口api")
@RestController
@RequestMapping("/file")
public class FileController {

    @ApiOperation(value = "文件上传",notes ="fastdfs的文件上传" )
    @PostMapping("/upload")
    public AjaxResult upload(MultipartFile file){
        try {
            String file_id = fileupload(file);
            return AjaxResult.me().setResultObj(file_id);
        } catch (IOException e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("上传失败"+e.getMessage());
        }
    }
    /*
    组名：group1
    路径: M00/00/01/rBAFiV4KDU2AL4GyAADaubWUPbU208.jpg
    * */

    @ApiOperation(value = "文件删除",notes ="fastdfs的文件删除" )
    @GetMapping("/delete")
    public AjaxResult delete(String file_id){
        //   /group1/M00/00/01/rBAFiV4KDU2AL4GyAADaubWUPbU208.jpg
        //去掉第一个斜杠
        try {
            file_id = file_id.substring(1);
            int index = file_id.indexOf("/");
            String groupName = file_id.substring(0,index);
            String fileName = file_id.substring(index+1);
            FastDfsApiOpr.delete(groupName,fileName);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("删除失败！"+e.getMessage());
        }
    }

    @RequestMapping(value = "/download",method = RequestMethod.GET,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void download(@RequestParam("fileId")String file_id, HttpServletResponse response){
        //获取完整文件名
        file_id = file_id.substring(1);
        int index = file_id.indexOf("/");
        String groupName = file_id.substring(0,index);
        String fileName = file_id.substring(index+1);
        try {
            byte[] bytes = FastDfsApiOpr.download(groupName, fileName);
            OutputStream outputStream = response.getOutputStream();
            outputStream.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //feign的上传
    @RequestMapping(
            value = "/feignUpload",
            method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public AjaxResult feignUpload(@RequestPart(value = "file")MultipartFile file){
        try {
            String file_id = fileupload(file);
            return AjaxResult.me().setResultObj(file_id);
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("上传失败"+e.getMessage());
        }
    }

    public String fileupload(MultipartFile file) throws IOException{
        String fileName = file.getName();
        String originalFilename = file.getOriginalFilename();
        //文件后缀名
        int index = originalFilename.lastIndexOf(".");
        String extName = originalFilename.substring(index + 1);
        return FastDfsApiOpr.upload(file.getBytes(), extName);


    }



}
