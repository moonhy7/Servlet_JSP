package lesson01.exam00;

import java.util.Scanner;

public class CalculatorConsole {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		
		System.out.print("정수를 입력하세요 : ");
		int num0 = sc.nextInt();
		System.out.print("정수를 입력하세요 : ");
		int num1 = sc.nextInt();
		System.out.print("연산자를 입력하세요 : ");
		String op = sc.next();
		
		int result = 0;
		switch(op) {
		case "+" : 
			result = num0 + num1;
			break;
		case "-" : 
			result = num0 - num1;
			break;
		case "*" : 
			result = num0 * num1;
			break;
		case "/" : 
			result = num0 / num1;
			break;
		}
		
		System.out.println("결과 값은 " + result + "입니다.");
		sc.close();
		
	}

}
