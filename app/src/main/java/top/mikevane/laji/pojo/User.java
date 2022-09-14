package top.mikevane.laji.pojo;

import android.os.Parcel;
import android.os.Parcelable;






public class User  implements Parcelable{
    private Integer id;
    private String username;
    private String password;
    private String email;
    private String userInfoId;
    public User() {
    }

    public User(Integer id, String username, String password, String email, String userInfoId) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.userInfoId = userInfoId;

    }

    protected User(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        username = in.readString();
        password = in.readString();
        email = in.readString();
        userInfoId=in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

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

    public String getUserInfoId() {
        return userInfoId;
    }

    public void setUserInfoId(String userInfoId) {
        this.userInfoId = userInfoId;
    }

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
        parcel.writeString(userInfoId);

    }
}
