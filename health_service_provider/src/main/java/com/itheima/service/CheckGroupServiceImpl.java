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
    private CheckGroupDao checkGroupDao;


    @Override
    @Transactional
    public void add(Integer[] checkItemIds, CheckGroup checkGroup) {
        checkGroupDao.add(checkGroup);
        System.out.println(checkGroup);
        if (checkGroup.getId() != null && checkItemIds != null && checkItemIds.length > 0) {
            for (Integer checkItemId : checkItemIds) {
                System.out.println(checkItemId);
                checkGroupDao.set(checkGroup.getId(), checkItemId);
            }
        }
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        /*获取当前页码和页码尺寸*/
        /*
        * 需要的参数有
        *   1.总条数    可以底层查询出来也是已知
        *   2.当前页    已知
        *   3.页面尺寸  已知
        *   4.当前页数据 通过limit也可以查出来
        *   5.总页数*/

        /*
        无条件时：
                查询出来总的条数select count(0)......
        有条件时：
                查询出来的是满足条件的总的条数select count(0)......where......
        */

        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        /**
         * 1是获取总条数，
         * 2是查询满足查询条件的检查组的集合
         */
        /*
        1.执行方法时触发拦截器，参数保存在page中 里面包含(当前页和每页显示多少条)
        2.调用完startPage后，他会通过PageInterceptor对其后的第一个执行sql进行拦截
        3.在sql语句执行之前对sql语句进行拦截修改成我们需要的分页sql语句然后再去执行
         */


         /*
        无条件时：
                查询出来的是当前页的数据
        有条件时：
                查询出来的是根据条件查询出来的数据集合的当前页
        */
        Page<CheckGroup> page = checkGroupDao.findByCondition(queryPageBean.getQueryString());

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

    /**
     * 步骤：
     *  1. 更新检查组信息（update）
     *  2. 维护检查组和检查项的关系
     *      a. 先删除原来的关系
     *      b. 添加新的关系
     *
     * @param checkItemIds
     * @param checkGroup
     */
    @Override
    public void edit(Integer[] checkItemIds, CheckGroup checkGroup) {
        checkGroupDao.edit(checkGroup);

        /*先删除关系，再重新设置关系*/
        checkGroupDao.delRelation(checkGroup.getId());
        if(checkItemIds != null && checkItemIds.length > 0){
            for (Integer checkItemId : checkItemIds) {
                System.out.println(checkItemId);
                checkGroupDao.set(checkGroup.getId(), checkItemId);
            }
        }
    }

    //删除检查项功能
    @Override
    public void delById(Integer id) {
        long count = checkGroupDao.findCountById(id);
        if(count>0){
            throw new RuntimeException("检查组被套餐所关联，不能删除!!!");
        }
        checkGroupDao.delRelation(id);
        checkGroupDao.delById(id);
    }

    @Override
    public List<CheckGroup> findAll() {
        return checkGroupDao.findAll();
    }
}
