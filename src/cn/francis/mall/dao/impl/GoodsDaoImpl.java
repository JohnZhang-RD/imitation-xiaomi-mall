package cn.francis.mall.dao.impl;

import cn.francis.mall.dao.GoodsDao;
import cn.francis.mall.domain.Cart;
import cn.francis.mall.domain.Goods;
import cn.francis.mall.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * Name: GoodsDaoImpl
 * Package: cn.francis.mall.dao.impl
 * date: 2024/09/05 - 16:22
 * Description:
 *
 * @author Junhui Zhang
 * @version 1.0
 */

public class GoodsDaoImpl implements GoodsDao {
    private QueryRunner queryRunner = new QueryRunner(DataSourceUtils.getDataSource());

    @Override
    public long getCount(String where, List<Object> params) {
        String sql = " SELECT COUNT(*) FROM tb_goods " + where;
        try {
            return queryRunner.query(sql, new ScalarHandler<>(), params.toArray());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Goods> selectByPage(int pageNumDefault, int pageSizeDefault, String where, List<Object> params) {
        String sql = " SELECT * FROM tb_goods " + where + " LIMIT ?, ? ";
        params.add((pageNumDefault - 1) * pageSizeDefault);
        params.add(pageSizeDefault);
        try {
            return queryRunner.query(sql, new BeanListHandler<>(Goods.class), params.toArray());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Goods getGoods(int id) {
        String sql = " SELECT * FROM tb_goods WHERE id = ? ";
        try {
            return queryRunner.query(sql, new BeanHandler<>(Goods.class), id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Goods> listGoods() {
        String sql = " SELECT * FROM tb_goods ";
        try {
            return queryRunner.query(sql, new BeanListHandler<>(Goods.class));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insertGoods(Goods goods) {
        String sql = " INSERT INTO tb_goods (name, pubdate, picture, price, star, intro, typeid) VALUES (?, ?, ?, ?, ?, ?, ?) ";
        Object[] params = {goods.getName(), goods.getPubdate(), goods.getPicture(), goods.getPrice(), goods.getStar(), goods.getIntro(), goods.getTypeid()};
        try {
            queryRunner.update(sql, params);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteGoods(Integer id) {
        String sql = " DELETE FROM tb_goods WHERE id = ? ";
        try {
            queryRunner.update(sql, id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateGoods(Goods goods) {
        String sql = " UPDATE tb_goods SET name = ?, price = ?, star = ?, intro = ?, typeid = ? WHERE id = ? ";
        Object[] params = {goods.getName(), goods.getPrice(), goods.getStar(), goods.getIntro(), goods.getTypeid(), goods.getId()};
        try {
            queryRunner.update(sql, params);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
