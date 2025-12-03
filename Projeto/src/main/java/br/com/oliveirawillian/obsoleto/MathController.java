//package br.com.oliveirawillian.obsoleto;
//
//import br.com.oliveirawillian.exception.UnsupportedMathOperationException;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/math")
//public class MathController
//{
//    SimpleMath simpleMath = new SimpleMath();
//    @RequestMapping("/sum/{numberOne}/{numberTwo}")
//    public Double sum(@PathVariable String numberOne, @PathVariable String numberTwo) throws IllegalArgumentException {
//        if(!Converter.isNumeric(numberOne) || !Converter.isNumeric(numberTwo)) throw new  UnsupportedMathOperationException("please set a numecic value!");
//        return simpleMath.sum(Converter.convertToDouble(numberOne) , Converter.convertToDouble(numberTwo));
//    }
//    @RequestMapping("/sub/{numberOne}/{numberTwo}")
//    public Double sub(@PathVariable String numberOne, @PathVariable String numberTwo) throws IllegalArgumentException {
//        if(!Converter.isNumeric(numberOne) || !Converter.isNumeric(numberTwo)) throw new  UnsupportedMathOperationException("please set a numecic value!");
//        return simpleMath.sub(Converter.convertToDouble(numberOne) , Converter.convertToDouble(numberTwo));
//
//    }
//    @RequestMapping("/mult/{numberOne}/{numberTwo}")
//    public Double mult(@PathVariable String numberOne, @PathVariable String numberTwo) throws IllegalArgumentException {
//        if(!Converter.isNumeric(numberOne) || !Converter.isNumeric(numberTwo)) throw new  UnsupportedMathOperationException("please set a numecic value!");
//        return simpleMath.mult(Converter.convertToDouble(numberOne) , Converter.convertToDouble(numberTwo));
//
//    }
//    @RequestMapping("/div/{numberOne}/{numberTwo}")
//    public Double div(@PathVariable String numberOne, @PathVariable String numberTwo) throws IllegalArgumentException {
//        if(!Converter.isNumeric(numberOne) || !Converter.isNumeric(numberTwo)) throw new  UnsupportedMathOperationException("please set a numecic value!");
//        return simpleMath.div(Converter.convertToDouble(numberOne) , Converter.convertToDouble(numberTwo));
//
//    }
//    @RequestMapping("/med/{numberOne}/{numberTwo}")
//    public Double med(@PathVariable String numberOne, @PathVariable String numberTwo) throws IllegalArgumentException {
//        if(!Converter.isNumeric(numberOne) || !Converter.isNumeric(numberTwo)) throw new  UnsupportedMathOperationException("please set a numecic value!");
//        return simpleMath.med(Converter.convertToDouble(numberOne) , Converter.convertToDouble(numberTwo));
//
//    }
//    @RequestMapping("/raiz/{numberOne}/{numberTwo}")
//    public Double raiz(@PathVariable String numberOne) throws IllegalArgumentException {
//        if(!Converter.isNumeric(numberOne)) throw new  UnsupportedMathOperationException("please set a numecic value!");
//        return simpleMath.raiz(Converter.convertToDouble(numberOne));
//    }
//
//
//
//
//}
