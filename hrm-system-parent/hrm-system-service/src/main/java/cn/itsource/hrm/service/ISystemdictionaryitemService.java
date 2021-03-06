package cn.itsource.hrm.service;

import cn.itsource.hrm.domain.Systemdictionaryitem;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liuqiqi
 * @since 2019-12-29
 */
public interface ISystemdictionaryitemService extends IService<Systemdictionaryitem> {

    List<Systemdictionaryitem> listSn(String sn);
}
