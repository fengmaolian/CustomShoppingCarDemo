package com.example.datasave;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Table;

/**
 * Created by fml on 2015/12/17 0017.
 */
@Table(name = "goods")
public class GoodsBean extends GoodsBase{
    @Column(column = "menupos")
    private int menupos;
    @Column(column = "goodsid")
    private int goodsid;
    @Column(column = "goodsnum")
    private String goodsnum;
    @Column(column = "goodsprice")
    private String goodsprice;

    public int getMenupos() {
        return menupos;
    }

    public void setMenupos(int menupos) {
        this.menupos = menupos;
    }

    public int getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(int goodsid) {
        this.goodsid = goodsid;
    }

    public String getGoodsnum() {
        return goodsnum;
    }

    public void setGoodsnum(String goodsnum) {
        this.goodsnum = goodsnum;
    }

    public String getGoodsprice() {
        return goodsprice;
    }

    public void setGoodsprice(String goodsprice) {
        this.goodsprice = goodsprice;
    }

    @Override
    public String toString() {
        return "GoodsBean{" +
                "menupos='" + menupos + '\'' +
                ", goodsid='" + goodsid + '\'' +
                ", goodsnum='" + goodsnum + '\'' +
                ", goodsprice='" + goodsprice + '\'' +
                '}';
    }
}
