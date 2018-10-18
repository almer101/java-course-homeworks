package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Program u kojem korisnik unosi cijele brojeve u rasponu od 1 do 20
 * te program ispisuje faktorijel unesenog broja. Korisnik unosi 
 * brojeve sve dok ne unese riječ "kraj".
 * 
 * @author 0036498427
 * @version 1.0
 */
public class Factorial {

	
	/**
	 * Funkcija koja se poziva pri pokretanju programa.
	 * Argumenti opisani u nastavku.
	 * @param args argumenti naredbenog retka
	 */
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		sc.useDelimiter("\\n");
		final int GORNJA_GRANICA = 20;
		final int DONJA_GRANICA = 1;
		
		while (true) {
			System.out.print("Unesite broj > ");
			String input = sc.next();
			if (input.toLowerCase().equals("kraj")) {
				break;
			}
			int number;
			try {
				number = Integer.parseInt(input);
			} catch (NumberFormatException e) {
				System.out.println("'" + input + "' nije cijeli broj.");
				continue;
			}
			if(number > GORNJA_GRANICA || number < DONJA_GRANICA ) {
				System.out.println("'" + input + "' nije broj u dozvoljenom rasponu.");
				continue;
			}
			else {
				System.out.println(number + "! = " + calculateFactorial(number));
			}
		}
		
		System.out.println("Doviđenja.");
		sc.close();
	}
	
	/**
	 * Metoda vraca long vrijednost faktorijela
	 * broja zadanog parametrom {@code n}. Argument {@code n} 
	 * opisan je u nastavku.
	 * @param n broj ciji faktorijel treba izracunati
	 * @return faktorijel broja {@code n}
	 */
	public static long calculateFactorial (int n) throws IllegalArgumentException{
		if(n < 0) {
			throw new IllegalArgumentException("Argument treba biti veci ili jednak 0!");
		}
		else if(n > 20) {
			throw new IllegalArgumentException("Faktorijel argumenta je prevelika! Najveci broj koji "
												+ "long moze prikazati je 9,223,372,036,854,775,807");
		}
		long factorial = 1;
		
		for(int i = n; i > 1; i--) {
			factorial *= i;
		}
		
		return factorial;
	}
}
