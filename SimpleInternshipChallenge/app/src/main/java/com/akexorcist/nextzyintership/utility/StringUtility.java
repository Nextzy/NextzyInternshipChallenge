package com.akexorcist.nextzyintership.utility;

/**
 * Created by Akexorcist on 5/7/2017 AD.
 */

@SuppressWarnings("ConstantConditions")
public class StringUtility {
    private static StringUtility utility;

    public static StringUtility getInstance() {
        if (utility == null) {
            utility = new StringUtility();
        }
        return utility;
    }

    public String getRealUsername(String email) {
        if (email == null) {
            String username = email.replaceAll("@nextzy.com", "").replaceAll("@gmail.com", "");
            username = Character.toUpperCase(username.charAt(0)) + username.substring(1);
            return username;
        } else {
            throw new NullPointerException("Invalid email");
        }
    }
}
