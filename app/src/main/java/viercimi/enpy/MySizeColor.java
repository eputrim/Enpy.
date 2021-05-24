package viercimi.enpy;

public class MySizeColor {
    public String key;
    String chart;

    public MySizeColor() {
    }

    public MySizeColor(String key, String chart) {
        this.key = key;
        this.chart = chart;
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
