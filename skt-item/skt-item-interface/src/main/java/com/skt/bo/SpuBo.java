package com.skt.bo;

import com.skt.pojo.Spu;
import com.skt.pojo.SpuDetail;
import com.skt.pojo.Sku;

import java.util.List;

public class SpuBo extends Spu {
    private String bname;//品牌名称
    private String cname;//品牌分类名称
    private List<Sku> skus;//sku列表
    private SpuDetail spuDetail;//商品详情

    public List<Sku> getSkus() {
        return skus;
    }

    public void setSkus(List<Sku> skus) {
        this.skus = skus;
    }

    public SpuDetail getSpuDetail() {
        return spuDetail;
    }

    public void setSpuDetail(SpuDetail spuDetail) {
        this.spuDetail = spuDetail;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }
}
