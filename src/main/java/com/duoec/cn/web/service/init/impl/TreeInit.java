package com.duoec.cn.web.service.init.impl;

import com.duoec.cn.core.dto.TreeNode;
import com.duoec.cn.web.dao.CnEntityDao;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.Map;

/**
 * TreeNode 加载器的基类
 * Created by ycoe on 16/11/28.
 */
public abstract class TreeInit<T extends TreeNode> extends BaseUpdatedInit {
    private static final Logger logger = LoggerFactory.getLogger(TreeInit.class);

    private Map<Long, T> treeIdMap;

    private List<T> roots;

    protected abstract CnEntityDao<T> getDao();

    protected void setTreeNode(T node) {
    }

    protected void initData() {
    }

    protected void onLoaded() {
    }

    @Override
    protected int getInterval() {
        return 10;
    }

    @Override
    protected void loadData(long time) {
        Bson query;
        if (time > 0) {
            query = Filters.gt("updateTime", time);
        } else {
            //初始化
            treeIdMap = Maps.newHashMap();
            roots = Lists.newArrayList();
            initData();

            query = Filters.ne("status", -1);
        }

        //排序需要先删除后添加!
        List<T> nodes = getDao().findEntities(query, Sorts.ascending("sort"), null, null);
        if (time == 0) {
            //组建Tree
            buildTreeAndGetRoot(nodes);
            onLoaded();
        } else {
            if (!nodes.isEmpty()) {
                //重新加载吧，简单一点
                logger.info("重新构建树：{}", this.getClass().getName());
                loadData(0);
            }
        }
    }

    @Override
    protected boolean isReady() {
        return getDao() != null;
    }

    /**
     * 获取所有根节点
     *
     * @return
     */
    public List<T> getRoots() {
        return roots;
    }

    /**
     * 获取所有根节点Cloned
     *
     * @return
     */
    public List<T> getCloneRoots() {
        List<T> clonedRoots = Lists.newArrayList();
        if (roots.isEmpty()) {
            return Lists.newArrayList();
        }
        Class<T> clazz = (Class<T>) roots.get(0).getClass();
        roots.forEach(root -> {
            T newInstance = BeanUtils.instantiate(clazz);
            BeanUtils.copyProperties(root, newInstance);
            clonedRoots.add(newInstance);
        });
        return clonedRoots;
    }

    /**
     * 通过ID获取某个节点
     *
     * @param id
     * @return
     */
    public T getById(long id) {
        return treeIdMap.get(id);
    }

    private void buildTreeAndGetRoot(List<T> list) {
        for (T node : list) {
            node.setChildren(null);
            treeIdMap.put(node.getId(), node);
            setTreeNode(node);
        }

        for (T node : list) {
            Long parentId = node.getParentId();
            if (parentId == null || parentId == 0) {
                //根
                if (!roots.contains(node)) {
                    roots.add(node);
                }
            } else {
                //支
                if (treeIdMap.containsKey(parentId)) {
                    //找到父节点
                    treeIdMap.get(parentId).add(node);
                } else {
                    //找不到父节点,丢弃
                }
            }
        }
    }

    /**
     * 尝试将当前TreeNode的父级设置为parentId，是否合法
     * @param self
     * @param parentId
     * @return
     */
    public String parentAvailableStatus(TreeNode self, Long parentId) {
        //修改时，需要检查父级是否合法
        if (parentId.longValue() == self.getId()) {
            return "父级不能为自己";
        }
        T parent = getById(parentId);
        //检查父级是否存在
        if (parent == null) {
            return "父级不存在";
        }

        //父级不能是自己的下级
        if (checkChildren(parentId, self)) {
            return "父级不能是自己的子分类";
        }
        return null;
    }

    private boolean checkChildren(Long categoryId, TreeNode self) {
        if (self.getChildren() != null && !self.getChildren().isEmpty()) {
            for (TreeNode child : self.getChildren()) {
                if (child.getId() == categoryId) {
                    return true;
                }
                return checkChildren(categoryId, child);
            }
        }
        return false;
    }
}
