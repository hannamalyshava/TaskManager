package com.example.taskmanager.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity
public class Task implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int uid;
    @ColumnInfo(name = "text")
    private String text;
    @ColumnInfo(name = "category")
    private String category;
    @ColumnInfo(name = "timestamp")
    private long timestamp;
    @ColumnInfo(name = "date")
    private String date;
    @ColumnInfo(name = "isCompleted")
    private boolean isCompleted;


    public Task(){}

    protected Task(Parcel in) {
        uid = in.readInt();
        text = in.readString();
        category = in.readString();
        timestamp = in.readLong();
        date = in.readString();
        isCompleted = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(uid);
        dest.writeString(text);
        dest.writeString(category);
        dest.writeLong(timestamp);
        dest.writeString(date);
        dest.writeByte((byte) (isCompleted ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return uid == task.uid && timestamp == task.timestamp && isCompleted == task.isCompleted && Objects.equals(text, task.text) && Objects.equals(category, task.category) && Objects.equals(date, task.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid, text, category, timestamp, date, isCompleted);
    }

    public Task(int uid, String text, String category, long timestamp, String date, boolean isCompleted) {
        this.uid = uid;
        this.text = text;
        this.category = category;
        this.timestamp=timestamp;
        this.date = date;
        this.isCompleted = isCompleted;
    }

    public int getUid() {
        return uid;
    }

    public long getTimestamp() {
        return timestamp;
    }
    public boolean getIsCompleted() {
        return isCompleted;
    }
    public String getCategory() {
        return category;
    }

    public String getDate() {
        return date;
    }

    public String getText() {
        return text;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

}

