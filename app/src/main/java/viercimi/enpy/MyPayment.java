package viercimi.enpy;

import java.io.Serializable;

public class MyPayment implements Serializable {
    public String key;
    String url_photo_payment;
    private boolean isChecked = false;

    public MyPayment() {
    }

    public MyPayment(String key, String url_photo_payment) {
        this.key = key;
        this.url_photo_payment = url_photo_payment;
    }

    public boolean isChecked(){
        return isChecked;
    }

    public void setChecked(boolean checked){
        isChecked = checked;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUrl_photo_payment() {
        return url_photo_payment;
    }

    public void setUrl_photo_payment(String url_photo_payment) {
        this.url_photo_payment = url_photo_payment;
    }
}
