package com.ydys.qmb.bean;

public class OrderInfo {
    private String id;//订单ID
    private String xing;//解锁的姓氏
    private String title;//订单名称
    private String orderno;//订单编号
    private String user_id;//用户ID
    private String pay_way;//支付方式(alipay:支付宝,wxpay:微信)
    private String uip;//IP
    private String amount;//支付价格(单位：元)
    private String agent;//来源渠道
    private String add_time;//下单时间
    private long update_time;//支付成功时间
    private String tk_time;//退款时间
    private int order_state;//订单状态(1:等待支付，2：支付成功，3：交易退款，99：订单过期)
    private String expire_date;//订单过期时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getXing() {
        return xing;
    }

    public void setXing(String xing) {
        this.xing = xing;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPay_way() {
        return pay_way;
    }

    public void setPay_way(String pay_way) {
        this.pay_way = pay_way;
    }

    public String getUip() {
        return uip;
    }

    public void setUip(String uip) {
        this.uip = uip;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public long getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(long update_time) {
        this.update_time = update_time;
    }

    public String getTk_time() {
        return tk_time;
    }

    public void setTk_time(String tk_time) {
        this.tk_time = tk_time;
    }

    public int getOrder_state() {
        return order_state;
    }

    public void setOrder_state(int order_state) {
        this.order_state = order_state;
    }

    public String getExpire_date() {
        return expire_date;
    }

    public void setExpire_date(String expire_date) {
        this.expire_date = expire_date;
    }
}
