package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.MenuDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.Menu;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService{

    @Autowired
    MenuDao menuDao;
    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Page<Menu> page =  menuDao.findByCondition(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(),page);
    }

    @Override
    public void add( Menu menu) {
        menuDao.add(menu);
    }

    @Override
    public void edit(Menu menu) {
        menuDao.edit(menu);
    }

    @Override
    public Menu findById(Integer id) {
        return menuDao.findById(id);
    }

    @Override
    public void delById(Integer id) {
        long count = menuDao.findCountById(id);
        if(count>0){
            throw  new RuntimeException("菜单项被角色表关联，不能删除！！！");
        }
        menuDao.delById(id);
    }

    @Override
    public List<Menu> findAllMenu() {
        return menuDao.findAllMenu();
    }


    public List<Menu> findMenuByUsername(String username) {
        //获取父菜单集合
        List<Menu> menuParentList = menuDao.findMenuByUsername(username);

        //获取父菜单下的子菜单
        for (Menu menuParent : menuParentList) {
            List<Menu> menuChildrenList = menuDao.findMenuByParentId(username,menuParent.getId());
            menuParent.setChildren(menuChildrenList);
        }
        return menuParentList;
    }
}
