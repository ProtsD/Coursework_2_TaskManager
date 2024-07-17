package utils;

import exception.WrongInputException;

public class Validation{
    public static String vali♦dateString(String str) throws WrongInputException {
        if (str == null || str.isEmpty() || str.isBlank()){
            throw new WrongInputException();
        }
        return str;
    }
}
