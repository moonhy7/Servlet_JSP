package lesson01.exam00;

import java.util.Scanner;

public class CalculatorConsole {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		
		System.out.print("������ �Է��ϼ��� : ");
		int num0 = sc.nextInt();
		System.out.print("������ �Է��ϼ��� : ");
		int num1 = sc.nextInt();
		System.out.print("�����ڸ� �Է��ϼ��� : ");
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
		
		System.out.println("��� ���� " + result + "�Դϴ�.");
		sc.close();
		
	}

}
