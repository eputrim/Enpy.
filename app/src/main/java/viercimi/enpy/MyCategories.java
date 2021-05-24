package viercimi.enpy;

public class MyCategories {
    public String key;
    String category_name;

    public MyCategories() {
    }

    public MyCategories(String key, String category_name) {
        this.key = key;
        this.category_name = category_name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }
}
