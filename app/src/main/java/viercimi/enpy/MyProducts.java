package viercimi.enpy;

public class MyProducts {

    public String key;
    String product_name, price, url_photo_product, category_id;

    public MyProducts() {
    }

    public MyProducts(String key, String product_name, String price, String url_photo_product, String category_id) {
        this.key = key;
        this.product_name = product_name;
        this.price = price;
        this.url_photo_product = url_photo_product;
        this.category_id = category_id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUrl_photo_product() {
        return url_photo_product;
    }

    public void setUrl_photo_product(String url_photo_product) {
        this.url_photo_product = url_photo_product;
    }
}
