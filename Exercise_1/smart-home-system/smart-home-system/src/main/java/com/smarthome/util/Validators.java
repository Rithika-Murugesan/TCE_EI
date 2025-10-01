
package com.smarthome.util;
public class Validators {
    public static boolean isValidTime(String hhmm) {
        return hhmm.matches("^\\d{1,2}:\\d{2}$");
    }
}
