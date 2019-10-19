package com.ydys.qmb.bean;

public class StructureInfo {
    private String[] word_name;//名字信息
    private String[] pian_pang;//部首信息
    private String[] jie_gou;//结构信息
    private String[] gou_zao;//构造信息
    private String[] bi_hua;//笔画信息
    private String[] bi_shun;//笔顺信息

    private String zi_xing_desc;//字形搭配分析(上半部分)
    private String zi_xing_title;//字形搭配分析(下半部分)
    private String bihua_desc;//笔画书写分析(上半部分)
    private String bihua_title;//笔画书写分析(下半部分)
    private String total_desc;//总评

    public String[] getWord_name() {
        return word_name;
    }

    public void setWord_name(String[] word_name) {
        this.word_name = word_name;
    }

    public String[] getPian_pang() {
        return pian_pang;
    }

    public void setPian_pang(String[] pian_pang) {
        this.pian_pang = pian_pang;
    }

    public String[] getJie_gou() {
        return jie_gou;
    }

    public void setJie_gou(String[] jie_gou) {
        this.jie_gou = jie_gou;
    }

    public String[] getGou_zao() {
        return gou_zao;
    }

    public void setGou_zao(String[] gou_zao) {
        this.gou_zao = gou_zao;
    }

    public String[] getBi_hua() {
        return bi_hua;
    }

    public void setBi_hua(String[] bi_hua) {
        this.bi_hua = bi_hua;
    }

    public String[] getBi_shun() {
        return bi_shun;
    }

    public void setBi_shun(String[] bi_shun) {
        this.bi_shun = bi_shun;
    }

    public String getZi_xing_desc() {
        return zi_xing_desc;
    }

    public void setZi_xing_desc(String zi_xing_desc) {
        this.zi_xing_desc = zi_xing_desc;
    }

    public String getZi_xing_title() {
        return zi_xing_title;
    }

    public void setZi_xing_title(String zi_xing_title) {
        this.zi_xing_title = zi_xing_title;
    }

    public String getBihua_desc() {
        return bihua_desc;
    }

    public void setBihua_desc(String bihua_desc) {
        this.bihua_desc = bihua_desc;
    }

    public String getBihua_title() {
        return bihua_title;
    }

    public void setBihua_title(String bihua_title) {
        this.bihua_title = bihua_title;
    }

    public String getTotal_desc() {
        return total_desc;
    }

    public void setTotal_desc(String total_desc) {
        this.total_desc = total_desc;
    }
}
