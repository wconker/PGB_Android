package com.android.pgb.Bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wu on 2017/4/13.
 */

public class NewInfo implements Parcelable {

    /**
     * content :
     * fbsj : 2017.03.28
     * id : 10
     * readtimes : 15
     * title : 中国资产评估协会会长会议在京召开
     */

    private String content;
    private String fbsj;
    private String id;
    private int readtimes;
    private String title;

    public void setContent(String content) {
        this.content = content;
    }

    public void setFbsj(String fbsj) {
        this.fbsj = fbsj;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setReadtimes(int readtimes) {
        this.readtimes = readtimes;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public String getFbsj() {
        return fbsj;
    }

    public String getId() {
        return id;
    }

    public int getReadtimes() {
        return readtimes;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.content);
        dest.writeString(this.fbsj);
        dest.writeString(this.id);
        dest.writeInt(this.readtimes);
        dest.writeString(this.title);
    }

    public NewInfo() {
    }

    protected NewInfo(Parcel in) {
        this.content = in.readString();
        this.fbsj = in.readString();
        this.id = in.readString();
        this.readtimes = in.readInt();
        this.title = in.readString();
    }

    public static final Parcelable.Creator<NewInfo> CREATOR = new Parcelable.Creator<NewInfo>() {
        public NewInfo createFromParcel(Parcel source) {
            return new NewInfo(source);
        }

        public NewInfo[] newArray(int size) {
            return new NewInfo[size];
        }
    };
}
