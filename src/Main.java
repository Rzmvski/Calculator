import java.util.List;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        System.out.println("Calculator v2.0");
        System.out.println("Input:");
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();
        System.out.println(Main.calc(input));
    }
    public static String calc(String input) {
        try {
            String[] numbers = input.split(" ");
            if (numbers.length > 3) {
                throw new Exception("Формат математической операции не удовлетворяет заданию - только два операнда и один оператор, не больше");
            }
            if (numbers.length == 1) {
                throw new Exception("Строка не является математической операцией");
            }
            if ((input.contains("I") || input.contains("V") || input.contains("X")) &&
                    (input.contains("0") || input.contains("1") || input.contains("2") || input.contains("3") || input.contains("4") || input.contains("5") ||
                            input.contains("6") || input.contains("7") || input.contains("8") || input.contains("9"))) {
                throw new Exception("Используются одновременно разные системы счисления");
            }
            boolean romanSwitch = false;
            if (isRoman(numbers[0], numbers[2])) {
                numbers[0] = String.valueOf(getRomanToArab(numbers[0]));
                numbers[2] = String.valueOf(getRomanToArab(numbers[2]));
                romanSwitch = true;
            }
            if (input.contains(".") || input.contains(",")) {
                throw new Exception("Только целые числа, чувак");
            }
            int number1 = Integer.parseInt(numbers[0]);
            String operator = numbers[1];
            int number2 = Integer.parseInt(numbers[2]);
            if (number1 > 10 || number2 > 10) {
                throw new Exception("Калькулятор работает только с числами не больше 10, c'est la vie");
            }
            if (operator.equals("-") && isRoman(numbers[0], numbers[2])) {
                throw new Exception("К сожалению в римской системе нет отрицательных чисел");
            }
            int result;
            switch (operator) {
                case ("+") -> result = number1 + number2;
                case ("-") -> result = number1 - number2;
                case ("*") -> result = number1 * number2;
                case ("/") -> result = number1 / number2;
                default -> throw new Exception("Упс, кто-то забыл ввести оператор");
            }
            if (romanSwitch) {
                if (result < 0) {
                    throw new Exception("К сожалению в римской системе нет отрицательных чисел");
                }
                return "Output:\n" + getArabToRoman(String.valueOf(result));
            }
            return "Output:\n" + result;
        }
        catch (Exception ex) {
            return ex.getMessage();
        }
    }
    public static boolean isRoman(String number1, String number2) {
        return number1.contains("I") || number2.contains("I") || number1.contains("V") || number2.contains("V") ||
                number1.contains("X") || number2.contains("X");
    }
    public static int getRomanToArab(String input) {
        String romanNumeral = input.toUpperCase();
        int result = 0;
        List<RomanNumeral> romanNumerals = RomanNumeral.getReverseSortedValues();
        int i = 0;
        while ((romanNumeral.length() > 0) && (i < romanNumerals.size())) {
            RomanNumeral symbol = romanNumerals.get(i);
            if (romanNumeral.startsWith(symbol.name())) {
                result += symbol.getValue();
                romanNumeral = romanNumeral.substring(symbol.name().length());
            } else {
                i++;
            }
        }
        return result;
    }
    public static String getArabToRoman(String number) {
        int arabNumber = Integer.parseInt(number);
        List<RomanNumeral> romanNumerals = RomanNumeral.getReverseSortedValues();
        int i = 0;
        StringBuilder sb = new StringBuilder();
        while ((arabNumber > 0) && (i < romanNumerals.size())) {
            RomanNumeral currentSymbol = romanNumerals.get(i);
            if (currentSymbol.getValue() <= arabNumber) {
                sb.append(currentSymbol.name());
                arabNumber -= currentSymbol.getValue();
            } else {
                i++;
            }
        }
        return sb.toString();
    }
}
