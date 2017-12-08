
import java.util.List;

public class Point {
    public int id; // 点的序列号
    public List<Double> location;// 点的坐标
    public int flag; // 0表示未处理，1表示处理了

    Point(int id, List<Double> location, int flag) {
        this.id = id;
        this.location = location;
        this.flag = flag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Double> getLocation() {
        return location;
    }

    public void setLocation(List<Double> location) {
        this.location = location;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "Point [id=" + id + ", location=" + location + ", flag="
                + flag + "]";
    }
}