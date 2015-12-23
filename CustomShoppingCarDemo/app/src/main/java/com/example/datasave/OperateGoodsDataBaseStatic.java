package com.example.datasave;
import android.content.Context;
import android.util.Log;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by fml on 2015/12/17 0017.
 */
public class OperateGoodsDataBaseStatic{
    /**
     *添加和删除商品数量
     */
    public static int saveGoodsNumber(Context context, int menupos, int goodsid, String goodsnum , String goodsprice) {
        DbUtils utils = DbUtils.create(context);
        GoodsBean goodsBean = null;
        goodsBean =new GoodsBean();
        goodsBean.setMenupos(menupos);
        goodsBean.setGoodsid(goodsid);
        goodsBean.setGoodsnum(goodsnum);
        goodsBean.setGoodsprice(goodsprice);
        try {
            GoodsBean bean = utils.findFirst(Selector.from(GoodsBean.class).where("menupos" , "=" , menupos).and("goodsid", "=", goodsid));
            //如果有这条数据，数量直接加1；否则就插入表里面
            if(bean == null){
                Log.e("TAG", "还没有该商品");
                utils.save(goodsBean);
                Log.e("TAG" , "该商品已经存储");
                return getSecondGoodsNumber(context , menupos , goodsid);
            }else{
                Log.e("TAG" , "已经有该商品");
                //返回添加商品之后的商品总数
                return updateNum(context, menupos, goodsid, goodsnum);
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        Log.e("TAG" , "添加商品失败");
        utils.close();
        return 0;
    }
    /**修改数量，直接传入数量**/
    public static int updateNum(Context context , int menupos , int goodsid , String goodsnum){
        DbUtils	utils = DbUtils.create(context);
        try {
            GoodsBean bean = utils.findFirst(Selector.from(GoodsBean.class).where("menupos", "=", menupos).and("goodsid", "=", goodsid));
            bean.setGoodsnum(goodsnum);
            utils.update(bean);
            Log.e("TAG", "该商品数量改变为：" + getSecondGoodsNumber(context, menupos, goodsid));
            return getSecondGoodsNumber(context , menupos , goodsid);
        } catch (DbException e) {
            e.printStackTrace();
        }
        utils.close();
        return 0;
    }
    /**
     *根据下标得到 第二级对应购物的数量
     */
    public static int getSecondGoodsNumber(Context context , int menupos , int goodsid) {
        DbUtils	utils = DbUtils.create(context);
        if(utils == null){
            Log.e("TAG" , "还没有该数据库");
            return 0;
        }
        try {
            GoodsBean bean = utils.findFirst(Selector.from(GoodsBean.class).where("menupos", "=", menupos).and("goodsid", "=", goodsid));
            if(bean == null){
                Log.e("TAG" , "还没有该存储商品");
                return 0;
            }else{
                Log.e("TAG" , "获取商品数量成功:" + Integer.parseInt(bean.getGoodsnum()));
                return Integer.parseInt(bean.getGoodsnum());
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        utils.close();
        Log.e("TAG", "获取商品数量失败");
        return 0;
    }
    /**
     * 根据第一级的下标 得到第二级的所有购物数量
     */
    public static int getSecondGoodsNumberAll(Context context, int menupos) {
        DbUtils	utils = DbUtils.create(context);
        int mSecondGoodsNum = 0;
        ArrayList<GoodsBean> mGoodsBeanList = null;
        mGoodsBeanList = getSecondGoodsTypeList(context);
        if(mGoodsBeanList == null){
            Log.e("TAG" , "获取商品类型总数失败");
            return 0;
        }
        for(int i = 0 ; i < mGoodsBeanList.size() ; i++){
            if(mGoodsBeanList.get(i).getMenupos() == menupos){
                mSecondGoodsNum += Integer.parseInt(mGoodsBeanList.get(i).getGoodsnum());
            }
        }
        Log.e("TAG", "根据第一级的下标 得到第二级的所有购物数量成功：" + mSecondGoodsNum);
        utils.close();
        return mSecondGoodsNum;
    }
    /**
     *根据第一级的下标 得到第二级商品所有商品类型集合
     */
    public static ArrayList<GoodsBean> getSecondGoodsTypeList(Context context){
        DbUtils	utils = DbUtils.create(context);
        ArrayList<GoodsBean> list = null;
        try {
            list = (ArrayList<GoodsBean>) DbUtils.create(context).findAll(GoodsBean.class);
            if(list == null){
                Log.e("TAG" , "该二级商品还没有存储数据");
                return null;
            }else{
                Log.e("TAG" , "根据第一级的下标 得到第二级商品类型总数成功：" + list.size());
                return list;
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        Log.e("TAG" , "根据第一级的下标 得到第二级商品类型总数失败");
        return null;
    }

    /**
     *据第一级的下标 得到第二级的所有购物的价格
     */
    public static int getSecondGoodsPriceAll(Context context, int menupos) {
        DbUtils	utils = DbUtils.create(context);
        int mSecondGoodsPrice = 0;
        ArrayList<GoodsBean> mGoodsBeanList = null;
        mGoodsBeanList = getSecondGoodsTypeList(context);
        if(mGoodsBeanList == null){
            Log.e("TAG" , "获取商品类型总数失败");
            return 0;
        }
        for(int i = 0 ; i < mGoodsBeanList.size(); i++){
            if(mGoodsBeanList.get(i).getMenupos() == menupos){
                mSecondGoodsPrice += Integer.parseInt(mGoodsBeanList.get(i).getGoodsnum()) * Integer.parseInt(mGoodsBeanList.get(i).getGoodsprice());
            }
        }
        Log.e("TAG" , "根据第一级的下标 得到第二级的所有购物的价格成功：" + mSecondGoodsPrice);
        utils.close();
        Log.e("TAG" , "根据第一级的下标 得到第二级的所有购物的价格失败");
        return mSecondGoodsPrice;
    }
    /**
     *删除所有的购物数据
     */
    public static void deleteAll(Context context) {
        DbUtils	utils = DbUtils.create(context);
        try {
            List<GoodsBean> records = utils.findAll(GoodsBean.class);
            utils.deleteAll(records);
        } catch (DbException e) {
            e.printStackTrace();
        }
        utils.close();
    }
}
