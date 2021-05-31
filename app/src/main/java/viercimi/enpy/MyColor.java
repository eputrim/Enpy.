package viercimi.enpy;

import java.io.Serializable;

public class MyColor implements Serializable {
    public String key;
    String chart;
    private boolean isChecked = false;

    public MyColor() {
    }

    public MyColor(String key, String chart) {
        this.key = key;
        this.chart = chart;
    }

    public boolean isChecked(){
        return isChecked;
    }

    public void setChecked(boolean checked){
        isChecked = checked;
    }

    public String getChart() {
        return chart;
    }

    public void setChart(String chart) {
        this.chart = chart;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
