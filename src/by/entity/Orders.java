package by.entity;

public class Orders {
    private int orders_id;
    private int users_id;
    private String payment;
    private int delete_status;
    private int total_cost;

    public Orders(int orders_id, int users_id, String payment, int delete_status, int total_cost) {
        this.orders_id = orders_id;
        this.users_id = users_id;
        this.payment = payment;
        this.delete_status = delete_status;
        this.total_cost = total_cost;
    }

    public Orders(int users_id, String payment, int delete_status, int total_cost) {
        this.users_id = users_id;
        this.payment = payment;
        this.delete_status = delete_status;
        this.total_cost = total_cost;
    }

    public Orders(int orders_id) {
        this.orders_id = orders_id;
        this.users_id = 0;
        this.payment = "processing";
        this.delete_status = 0;
    }

    public Orders(int orders_id, String payment) {
        this.orders_id = orders_id;
        this.payment = payment;
    }

    public int getOrders_id() {
        return orders_id;
    }

    public void setOrders_id(int orders_id) {
        this.orders_id = orders_id;
    }

    public int getUsers_id() {
        return users_id;
    }

    public void setUsers_id(int users_id) {
        this.users_id = users_id;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public int getDelete_status() {
        return delete_status;
    }

    public void setDelete_status(int delete_status) {
        this.delete_status = delete_status;
    }

    public int getTotal_cost() {
        return total_cost;
    }

    public void setTotal_cost(int total_cost) {
        this.total_cost = total_cost;
    }
}
