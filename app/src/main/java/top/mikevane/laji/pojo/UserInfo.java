package top.mikevane.laji.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author MikeV
 */
public class UserInfo implements Parcelable {
    /**
     * 主键
     */
    private Integer id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 用户email
     */
    private String email;
    /**
     * 用户电话
     */
    private String phone;
    /**
     * 用户头像
     */
    private String userPhoto;
    /**
     * 用户性别
     */
    private String sex;


    public UserInfo(Integer id, String username, String password, String email, String address, String phone, String userPhoto, String sex) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.userPhoto = userPhoto;
        this.sex = sex;
    }

    public UserInfo() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    protected UserInfo(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        username = in.readString();
        email = in.readString();
        phone = in.readString();
        userPhoto = in.readString();
        sex = in.readString();
    }

    public static final Creator<UserInfo> CREATOR = new Creator<UserInfo>() {
        @Override
        public UserInfo createFromParcel(Parcel in) {
            return new UserInfo(in);
        }

        @Override
        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(id);
        }
        parcel.writeString(username);
        parcel.writeString(email);
        parcel.writeString(phone);
        parcel.writeString(userPhoto);
        parcel.writeString(sex);
    }
}
