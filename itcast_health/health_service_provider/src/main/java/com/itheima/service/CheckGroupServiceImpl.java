package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.CheckGroupDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    CheckGroupDao checkGroupDao;

    @Override
    @Transactional
    public void add(Integer[] checkitemIds, CheckGroup checkGroup) {
        //添加检查组的主键回显  映射文件中配置生效的
        checkGroupDao.add(checkGroup);
        //维护检查组和检查项的关系
        if(checkGroup.getId() != null && checkitemIds != null && checkitemIds.length > 0){
            for (Integer checkitemId : checkitemIds) {
                //checkGroup.getId() 就是检查组的自增id  checkitemId就是检查项的id
                checkGroupDao.set(checkGroup.getId(),checkitemId);
            }
        }
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        //开始分页
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        //条件查询
        Page<CheckGroup> page = checkGroupDao.findByCondition(queryPageBean.getQueryString());
        //创建返回值对象
        return new PageResult(page.getTotal(),page);
    }

    @Override
    public CheckGroup findById(Integer id) {
        return checkGroupDao.findById(id);
    }

    @Override
    public Integer[] findCheckItemIdsById(Integer id) {
        return checkGroupDao.findCheckItemIdsById(id);
    }

    @Override
    public void edit(Integer[] checkitemIds, CheckGroup checkGroup) {
        //更新检查组
        checkGroupDao.edit(checkGroup);
        //维护检查组和检查项的关系
        //先删除原来的关系
        checkGroupDao.delRelation(checkGroup.getId());
        //添加新的关系
        if(checkitemIds != null && checkitemIds.length > 0){
            for (Integer checkitemId : checkitemIds) {
                checkGroupDao.set(checkGroup.getId(),checkitemId);
            }
        }
    }

    @Override
    public void delById(Integer id) {
        //查询检查组是否被套餐组关联 如果关联 不能删除
        long count = checkGroupDao.findCountById(id);
        if(count > 0){
            throw new RuntimeException("该项被套餐组关联,不能删除!!!");
        }
        checkGroupDao.delRelation(id);

        checkGroupDao.delById(id);
    }

    @Override
    public List<CheckGroup> findAll() {
        return checkGroupDao.findAll();
    }
}
