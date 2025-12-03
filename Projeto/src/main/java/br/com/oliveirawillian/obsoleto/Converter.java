//package br.com.oliveirawillian.obsoleto;
//
//import br.com.oliveirawillian.exception.UnsupportedMathOperationException;
//
//public class Converter {
//    public static Double convertToDouble(String strNumber) throws IllegalArgumentException {
//        if (strNumber == null || strNumber.isEmpty()) throw new UnsupportedMathOperationException("please set a numecic value!");
//        String number = strNumber.replace(",", ".");
//        return Double.parseDouble(number);
//    }
//
//    public static boolean isNumeric(String strNumber) {
//        if (strNumber == null || strNumber.isEmpty()) return false;
//        String number = strNumber.replace(",", ".");
//        return number.matches("[-+]?[0-9]*\\.?[0-9]+");
//    }
//}
