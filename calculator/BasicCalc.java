import java.util.Scanner;

public class BasicCalc {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the first number: ");
        int firstNumber = sc.nextInt();
        System.out.print("Enter the second number: ");
        int secondNumber = sc.nextInt();

        System.out.print("Enter the type of operation (+, -, *, /, %, ^): ");
        String operation = sc.next();

        int result = performOperation(firstNumber, secondNumber, operation);
        System.out.println("Your answer is: " + result);
    }

    public static int performOperation(int firstNumber, int secondNumber, String operation) {
        int result = switch (operation) {
            case "+" -> firstNumber + secondNumber;
            case "-" -> firstNumber - secondNumber;
            case "*" -> firstNumber * secondNumber;
            case "/" -> firstNumber / secondNumber;
            case "%" -> firstNumber % secondNumber;
            case "^" -> (int) Math.pow(firstNumber, secondNumber);
            default -> {
                System.out.println("Invalid operation");
                yield 0;
            }
        };

        return result;
    }
}