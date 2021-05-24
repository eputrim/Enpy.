package viercimi.enpy;

public class MyCart {

    public String key;
    String color, price, product_name, quantity, size, url_photo_product, total;

    public MyCart() {
    }

    public MyCart(String key, String color, String price, String product_name, String quantity, String size, String url_photo_product, String total) {
        this.key = key;
        this.color = color;
        this.price = price;
        this.product_name = product_name;
        this.quantity = quantity;
        this.size = size;
        this.url_photo_product = url_photo_product;
        this.total = total;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getUrl_photo_product() {
        return url_photo_product;
    }

    public void setUrl_photo_product(String url_photo_product) {
        this.url_photo_product = url_photo_product;
    }
}
