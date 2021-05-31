package viercimi.enpy;

public class MyOrder {
    String date,  payment, total, key;

    public MyOrder() {
    }

    public MyOrder(String date, String payment, String total, String key) {
        this.date = date;
        this.payment = payment;
        this.total = total;
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
