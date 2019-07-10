package high.muliti;

public class OrderEvent {
    private long value;

    private String name;

    private String id;

    public void setValue(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
