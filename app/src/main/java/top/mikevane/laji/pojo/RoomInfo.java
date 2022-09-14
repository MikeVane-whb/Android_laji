package top.mikevane.laji.pojo;

/**
 * 这是房间温度湿度亮度的实体类
 */
public class RoomInfo {
    private int temperature;
    private int humidity;
    private String light;
    private String roomId;

    public String getRoomId() {
        return roomId;
    }

    public RoomInfo(int temperature, int humidity, String light, String roomId) {
        this.temperature = temperature;
        this.humidity = humidity;
        this.light = light;
        this.roomId = roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public RoomInfo() {
    }


    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public String getLight() {
        return light;
    }

    public void setLight(String light) {
        this.light = light;
    }
}
