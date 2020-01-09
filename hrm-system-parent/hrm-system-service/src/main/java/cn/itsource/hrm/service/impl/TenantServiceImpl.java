package cn.itsource.hrm.service.impl;

import cn.itsource.hrm.domain.*;
import cn.itsource.hrm.mapper.*;
import cn.itsource.hrm.service.ITenantService;
import cn.itsource.hrm.web.dto.TenantDto;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liuqiqi
 * @since 2019-12-29
 */
@Service
public class TenantServiceImpl extends ServiceImpl<TenantMapper, Tenant> implements ITenantService {

    @Autowired
    private TenantMealMapper tenantMealMapper;
    @Autowired
    private MealPermissionMapper mealPermissionMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;
    @Autowired
    private EmployeeMapper employeeMapper;
    @Autowired
    private EmployeeRoleMapper EmployeeRoleMapper;
    //注册租户
    @Override
    @Transactional
    public void registeTenant(TenantDto tenantDto) {
        //保存到租户表，由于前端和实体类字段不匹配，所以定义了一个类TenantDto来接收租户表以及其他字段
        //保存到租户表，返回租户id
        Tenant tenant = tenantDto.getTenant();
        tenant.setState(0);
        tenant.setRegisterTime(System.currentTimeMillis());//获取系统当前时间
        baseMapper.insert(tenant); //保存到租户表
        //添加到租户套餐中间表
        TenantMeal tm = new TenantMeal();
        tm.setTenantId(tenant.getId());
        tm.setMealId(tenantDto.getMeal());
        //剩下的字段初始化
        tm.setState(0);
        tm.setExpireDate(System.currentTimeMillis()+7*24*60*60*1000);//设置过期时间为7天后过期
        tenantMealMapper.insert(tm);//插入到租户套餐中间表

        //根据套餐的id获取到该套餐的权限
        List<MealPermission> mealPermissions = mealPermissionMapper.selectList(new QueryWrapper<MealPermission>().eq("meal_id", tenantDto.getMeal()));

        //创建一个租户管理员
        Role role = new Role();
        role.setSn("TenantAdmin");
        role.setName(tenant.getCompanyName()+"租户管理员");
        role.setTenant(tenant.getId());
        roleMapper.insert(role);

        //角色权限中间表存入 角色id，权限id，
        List<RolePermission> list = new ArrayList<>();
        //循环遍历该套餐的所有权限
        for (MealPermission mealPermission : mealPermissions) {
            //角色权限中间表
            RolePermission rp = new RolePermission();
            rp.setRoleId(role.getId());
            rp.setPermissionId(mealPermission.getPermissionId());
            list.add(rp);
        }
        if(list!=null && list.size()>0){
            rolePermissionMapper.insertBatch(list);
        }

        //创建员工分配角色
        Employee employee = new Employee();
        employee.setUsername(tenantDto.getUsername());
        employee.setPassword(tenantDto.getPassword());
        employee.setState(0);
        employee.setInputTime(System.currentTimeMillis());
        employee.setTenantId(tenant.getId());
        employee.setType(3);
        employeeMapper.insert(employee);

        //员工角色中间表
        EmployeeRole er = new EmployeeRole();
        er.setEmployeeId(employee.getId());
        er.setRoleId(role.getId());
        EmployeeRoleMapper.insert(er);







    }
}
