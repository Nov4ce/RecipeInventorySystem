package Assignment;
import java.util.Scanner;

class Operation {
	
	void Fibonaccinumber(int num) {
		int var1 = 0, var2 = 1, var3 = 0, i = 0, N = num;
		do {
			System.out.print(var1 + ", ");				
	        var3 = var1 + var2;
	        var1 = var2;
	        var2 = var3;
	        i++;
	    }while(i < N);
	}
	
	void Fibonacciword(int num) {
		int var1 = 0, var2 = 1, var3 = 0, i = 0, N = num;
		do {
			System.out.print(ConvertNumber(var1) + ", ");				
	        var3 = var1 + var2;
	        var1 = var2;
	        var2 = var3;
	        i++;
	    }while(i < N);
	}
	
	static String ones[] = {" One", " Two", " Three", " Four", " Five", " Six", " Seven", " Eight", " Nine"};
	static String tens[] = {" Ten", " Twenty", " Thirty", " Forty", " Fifty", " Sixty", " Seventy", " Eighty", " Ninety"};
	static String tenTo20[] = {" Ten", " Eleven", " Twelve", " Thirteen", " Fourteen", " Fifteen", " Sixteen", " Seventeen", " Eighteen", " Nineteen"};
	static String identifier[] = {" Hundred", " Thousand", " Million"};
	
	public static String ConvertNumber(long number) {
		String result = "";
		if (number == 0) 
			System.out.print("Zero");
		for (long x = String.valueOf(number).length() - 1; x >= 0; x--) {
			long div =  (long) Math.pow(10, x);
			int value = (int) Math.floor(number/div);
			
			if (value > 0) {
				if (div == 10000000 || div == 10000 || div == 10 ) {
					if (value == 1) {
						number = number % div;
						div = (long)Math.pow(10, x - 1);
						value = (int) Math.floor(number / div);
						x--;
						result = result + tenTo20[value] + "";
						
					} else {
						result = result + tens[value - 1] + "";
					}
				} else {
					result = result + ones[value - 1] + "";
				}
				if (div == 100000000 || div == 100000 || div == 100) {
					result = result + identifier[0] + "";
				}
			}
			if (div == 1000000) {
				result = result + identifier[2] + "";
			}
			
			if (div == 1000) {
				result = result + identifier[1] + "";
			}
			number = number % div;
		}	
		
		return result;
    }
	
}

class Numbers extends Operation{
	void Fibonaccinumber() {
		System.out.println("Fibonacci Series: ");
	}
}

class NumberToWords extends Operation {
	void Fibonacciword() {
		System.out.println("Fibonacci Series: "); 
	}
}

public class FlexFibbo {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(System.in);
		
		int choice, num;
		do {
			System.out.println("\nPrinting Fibonacci Series from 0 to N");
			System.out.print("Enter how many series of Fibbonacci you want: ");
			num = scan.nextInt(); 
			System.out.print("Woud you want the output in \n" + "1. Numbers?\n" + "2. Words?\n"+ "3. Exit\n"+ "Enter Your Choice: ");
			choice = scan.nextInt();
			
			switch(choice) {
				case 1:
					Numbers a = new Numbers();
					a.Fibonaccinumber(num);
					
					break;
				case 2:
					NumberToWords b = new NumberToWords();
					b.Fibonacciword(num);
					break;
					
				case 3:
					System.exit(0);
					
				default:
					System.err.println("Error! You must input a choice");
			}
		
		}while(choice < 3);
		
	}

}
