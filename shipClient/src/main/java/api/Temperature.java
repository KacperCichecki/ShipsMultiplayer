package api;

public class Temperature {

    private Metrics metrics;
    private Imperial imperial;

    public Metrics getMetrics() {
        return metrics;
    }

    public void setMetrics(Metrics metrics) {
        this.metrics = metrics;
    }

    public Imperial getImperial() {
        return imperial;
    }

    public void setImperial(Imperial imperial) {
        this.imperial = imperial;
    }

    @Override
    public String toString() {
        return "Temperature{" +
                "metrics=" + metrics +
                ", imperial=" + imperial +
                '}';
    }
}
