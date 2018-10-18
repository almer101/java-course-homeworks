package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Program koji prima visinu i sirinu pravokutnika preko
 * naredbenog retka i ispisuje njegovu povrsinu i opseg.
 * U slucaju da korisnik ne zada visinu i sirinu preko
 * naredbenog retka, iste se ucitavaju s tipkovnice.
 * 
 * @author 0036498427
 * @version 1.0
 */
public class Rectangle {

	/**
	 * Metoda koja se poziva pri pokretanju programa.
	 * Argumenti su opisani u nastavku.
	 * @param args argumenti naredbenog retka
	 */
	public static void main(String[] args) {
		
		if(args.length != 0 && args.length != 2) {
			System.out.println("Niste unijeli dobar broj argumenata "
					+ "(ocekivani broj je 0 ili 2 argumenta). "
					+ "Program ce sada prekinuti s radom!"
			);
			System.exit(1);
		}
		
		else if(args.length == 2) {
			try {
				double width = Double.parseDouble(args[0]);
				double height = Double.parseDouble(args[1]);
				if(width <= 0 || height <= 0) {
					throw new NumberFormatException();
				}
				povrsinaOpseg(width, height);
			} catch (NumberFormatException e) {
				System.out.println("Neku od dimenzija pravokutnika niste dobro unijeli."
						+ " Molim unesite visinu i sirinu preko tipkovnice."
				);
				unosSirineIVisine();
			}
		}
		else {
			unosSirineIVisine();
		}
	}
	
	/**
	 * Metoda koja racuna i ispisuje povrsinu i opseg
	 * pravokutnika odredenog sirinom {@code width} i
	 * visinom {@code height}.
	 * 
	 * @param width sirina pravokutnika
	 * @param height visina pravokutnika
	 */
	public static void povrsinaOpseg(double width, double height) {
		
		System.out.format("Pravokutnik širine %.2f i visine %.2f ima površinu %.2f te opseg %.2f.\n", width, height, width*height, 2*(height+width));
	}
	
	/**
	 * Metoda koja cita sa tipkovnice dimenziju pravokutnika
	 * {@code dimension} i vraca ucitani rezultat, no u meduvremenu
	 * provjerava moze li se ucitano interpretirati kao broj te je 
	 * li uopce u dozvoljenom intervalu.
	 * U slucaju da je ucitana 0 za visinu ili sirinu program
	 * to nece dopustiti jer to onda nije pravokutnik.
	 * @param dimension dimenzija pravokutnika koju unosimo
	 * 					npr. "visinu"
	 * @return double ucitana vrijednost.
	 */
	public static double unosPodatka(Scanner sc, String dimension) {
		sc.useDelimiter("\\n");
		
		while(true) {
			System.out.print("Unesite " + dimension + " > ");
			String input = sc.next();
			
			double number;
			try {
				number = Double.parseDouble(input);
			} catch (NumberFormatException e) {
				System.out.println(input + " se ne moze protumaciti kao broj.");
				continue;
			}
			
			if(number < 0) {
				System.out.println("Unijeli ste negativnu vrijednost.");
				continue;
			}
			if(Math.abs(number - 0) < 1E-06) { 
				//usporedujemo broj s 0, a buduci da je number tipa double usporedujemo ga na ovaj nacin
				System.out.println("Ova dimenzija pravokutnika ne može "
						+ "biti 0 jer to onda nije pravokutnik."
				);
				continue;
			}
			
			return number;
		}
	}
	
	/**
	 * Metoda koja cita visinu i sirinu sa System.in
	 * te racuna i ispisuje iznose opsega i povrsine.
	 * 
	 */
	public static void unosSirineIVisine() {
		Scanner sc = new Scanner(System.in);
		double width = unosPodatka(sc, "sirinu");
		double height = unosPodatka(sc, "visinu");
		
		povrsinaOpseg(width, height);
		
		sc.close();
	}
}
