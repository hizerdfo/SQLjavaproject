package FinalProject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Member {
    String userName;
    String userAddress;
    String phoneNumber;

    int memberId;
    Date registrationDate;
    Date birthDate;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }


    public void registrationDate(String registrationDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            this.registrationDate = formatter.parse(registrationDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void birthDate(String birthDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            this.birthDate = dateFormat.parse(birthDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

}