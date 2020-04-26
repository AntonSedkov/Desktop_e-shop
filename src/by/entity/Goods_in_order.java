package by.entity;

public class Goods_in_order {
    private int orders_id;
    private int goods_id;
    private int count;

    public Goods_in_order(int orders_id, int goods_id, int count) {
        this.orders_id = orders_id;
        this.goods_id = goods_id;
        this.count = count;
    }

    public Goods_in_order(int orders_id) {
        this.orders_id = orders_id;
    }

    public int getOrders_id() {
        return orders_id;
    }

    public void setOrders_id(int orders_id) {
        this.orders_id = orders_id;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
