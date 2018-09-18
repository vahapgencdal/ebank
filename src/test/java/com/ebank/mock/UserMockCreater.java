package com.ebank.mock;

import com.ebank.model.entity.User;
import com.ebank.util.UserTypeEnum;

/**
 * @author Vahap Gencdal
 * @email avahap19@gmail.com
 * @date 17.09.2018
 * @description TODO: Class Description
 */
public class UserMockCreater {
    public static User getTest() {
        User user = new User();
        user.setFullName("Vahap Gencdal");
        user.setEmail("avahap19@gmail.com");
        user.setStatus(true);
        return user;
    }

    public static User getIsBank(long addressId, long bankId) {
        User user = new User();
        user.setFullName("İs Bank");
        user.setEmail("isbank@isbank.com.tr");
        user.setStatus(Boolean.TRUE);
        user.setAddressId(addressId);
        user.setPhone("+9002122343456");
        user.setUserType(UserTypeEnum.CORP.toString());
        return user;
    }

    public static User getGaranti(long addressId, long bankId) {
        User user = new User();
        user.setFullName("Garanti");
        user.setEmail("garanti@garanti.com.tr");
        user.setStatus(Boolean.TRUE);
        user.setAddressId(addressId);
        user.setPhone("+9002122343456");
        user.setUserType(UserTypeEnum.CORP.toString());
        return user;
    }

    public static User getVahapUser(long addressId, long bankId) {
        User user = new User();
        user.setFullName("Vahap Gencdal");
        user.setEmail("avahap19@gmail.com");
        user.setStatus(Boolean.TRUE);
        user.setAddressId(addressId);
        user.setPhone("+906783712345");
        user.setUserType(UserTypeEnum.CORP.toString());
        return user;
    }

    public static User getFerhatUser(long addressId, long bankId) {
        User user = new User();
        user.setFullName("Ferhat Kakun");
        user.setEmail("ferhat@gmail.com");
        user.setStatus(Boolean.TRUE);
        user.setAddressId(addressId);
        user.setPhone("+905423712345");
        user.setUserType(UserTypeEnum.CORP.toString());
        return user;
    }

    public static User getUser(long addressId, String fullName, String email, String phone, String userType, long bankId) {
        User user = new User();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setStatus(Boolean.TRUE);
        user.setAddressId(addressId);
        user.setPhone(phone);
        user.setUserType(userType);
        user.setBankId(bankId);
        return user;
    }
}
