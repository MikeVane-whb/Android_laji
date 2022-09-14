package top.mikevane.laji.pojo;

import android.os.Parcel;
import android.os.Parcelable;

public class UserInfo implements Parcelable {
    private Integer id;
    private String username;
    private String password;
    private String email;
    private String address;
    private String phone;
    private String userPhoto;
    private String sex;


    public UserInfo(Integer id, String username, String password, String email, String address, String phone, String userPhoto, String sex) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.address = address;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
        password = in.readString();
        email = in.readString();
        address = in.readString();
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
        parcel.writeString(password);
        parcel.writeString(email);
        parcel.writeString(address);
        parcel.writeString(phone);
        parcel.writeString(userPhoto);
        parcel.writeString(sex);
    }
}
