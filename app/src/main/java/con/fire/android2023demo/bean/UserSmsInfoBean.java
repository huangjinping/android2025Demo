package con.fire.android2023demo.bean;


import java.io.Serializable;

/* loaded from: classes.dex */
public class UserSmsInfoBean implements Serializable {
    private String messageContent;
    private String messageDate;
    private String phone;
    private String type;
    private int userId;

    public String getMessageContent() {
        return this.messageContent;
    }

    public String getMessageDate() {
        return this.messageDate;
    }

    public String getPhone() {
        return this.phone;
    }

    public String getType() {
        return this.type;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setMessageContent(String str) {
        this.messageContent = str;
    }

    public void setMessageDate(String str) {
        this.messageDate = str;
    }

    public void setPhone(String str) {
        this.phone = str;
    }

    public void setType(String str) {
        this.type = str;
    }

    public void setUserId(int i9) {
        this.userId = i9;
    }
}