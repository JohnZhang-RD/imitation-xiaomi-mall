package cn.francis.mall.dao.impl;

import cn.francis.mall.dao.GoodsTypeDao;
import cn.francis.mall.domain.GoodsType;
import cn.francis.mall.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * Name: GoodsTypeDaoImpl
 * Package: cn.francis.mall.dao.impl
 * date: 2024/09/05 - 11:46
 * Description:
 *
 * @author Junhui Zhang
 * @version 1.0
 */

public class GoodsTypeDaoImpl implements GoodsTypeDao {
    private QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());

     @Override
     public List<GoodsType> select(int i) {
         String sql = " SELECT * FROM tb_goods_type WHERE level = ? ";
         try {
             return queryRunner.query(sql,new BeanListHandler<>(GoodsType.class), i);
         } catch (Exception e) {
             throw new RuntimeException(e);
         }
     }

    @Override
    public GoodsType getGoodsType(Integer typeid) {
        try {
            String sql = " SELECT * FROM tb_goods_type WHERE id = ? ";
            return queryRunner.query(sql, new BeanHandler<>(GoodsType.class), typeid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<GoodsType> listGoodsType() {
        String sql = " SELECT * FROM tb_goods_type ";
        try {
            return queryRunner.query(sql, new BeanListHandler<>(GoodsType.class));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteGoodsType(int typeId) {
        String sql = " DELETE FROM tb_goods_type WHERE id = ? ";
        try {
            queryRunner.update(sql, typeId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateGoodsType(GoodsType goodsType) {
        String sql = " UPDATE tb_goods_type SET name = ?, level = ?, parent = ? WHERE id = ? ";
        Object[] params = {goodsType.getName(), goodsType.getLevel(), goodsType.getParent(), goodsType.getId()};
        try {
            queryRunner.update(sql, params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insertGoodsType(GoodsType goodsType) {
        String sql = " INSERT INTO tb_goods_type  (name, level , parent) value (?, ?, ?) ";
        Object[] params = {goodsType.getName(), goodsType.getLevel(), goodsType.getParent()};
        try {
            queryRunner.update(sql, params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<GoodsType> listGoodsType(String where, List<Object> params) {
        String sql = " SELECT * FROM tb_goods_type " + where;
        try {
            return queryRunner.query(sql, new BeanListHandler<>(GoodsType.class), params.toArray());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
