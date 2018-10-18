package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Program koji u strukturu podataka binarno stablo stavlja
 * brojeve, ali samo ako taj broj već ne postoji u stablu.
 * 
 * @author 0036498427
 *
 */
public class UniqueNumbers {

	/**
	 * Pomocna struktura (klasa) koja predstavlja
	 * jedan cvor u stablu, a sastoji se od vrijednosti
	 * {@code value} te referenci na lijevi cvor {@code left}
	 * i na desni cvor {@code right}.
	 * @author 0036498427
	 *
	 */
	static class TreeNode {
		TreeNode right;
		TreeNode left;
		int value;
	}
	
	/**
	 * Metoda koja se poziva pri pokretanju programa.
	 * Argumenti objasnjeni u nastvku.
	 * @param args argumenti naredbenog retka
	 */
	public static void main(String[] args) {
		
		TreeNode glava = null;
		Scanner sc = new Scanner(System.in);
		sc.useDelimiter("\\n");
		
		while(true) {
			System.out.print("Unesite broj > ");
			String input = sc.next();
			if(input.toLowerCase().equals("kraj")) {
				break;
			}
			
			int value;
			try {
				value = Integer.parseInt(input);
			} catch (NumberFormatException e) {
				System.out.format("'%s' nije cijeli broj.\n", input);
				continue;
			}	
			
			if(containsValue(glava, value)) {
				System.out.println("Broj već postoji. Preskačem.");
				continue;
			}
			else {
				glava = addNode(glava, value);
				if(containsValue(glava, value)) {
					System.out.println("Dodano.");
					continue;
				}
			}
		}
		sc.close();
		System.out.print("Ispis od najmanjeg: ");
		ispisStabla(glava, false);
		System.out.print("\nIspis od najveceg: ");
		ispisStabla(glava, true);
		System.out.println("");
	}

	/**
	 * Metoda koja dodaje cvor vrijednosti {@code value}
	 * u binarno stablo kojem je glava {@code glava}.
	 * U lijevom podstablu se nalaze elementi manje
	 * vrijednosti od vrijednosti glave, a u desnom podstablu
	 * elementi vece vrijednosti od vrijednosti glave.
	 * @param glava korijenski cvor
	 * @param value vrijednost koju dodajemo u stablo
	 * @return vrijednost glave nakon dodavanja u stablo
	 */
	public static TreeNode addNode(TreeNode glava, int value) {
		if(glava == null) {
			TreeNode novi = new TreeNode();
			novi.value = value;
			novi.left = null;
			novi.right = null;
			glava = novi;
			return glava;
		}
		if(value > glava.value) {
			glava.right = addNode(glava.right, value);
		}
		else if (value < glava.value) {
			glava.left = addNode(glava.left, value);
		}
		
		return glava;
	}
	
	/**
	 * Metoda koja vraca broj elemenata u stablu (velicinu stabla)
	 * za zadan korijenski cvor stabla {@code glava}. 
	 * @param glava korijenski cvor
	 * @return broj elemenata u stablu
	 */
	public static int treeSize(TreeNode glava) {
		if(glava == null) {
			return 0;
		}
		int left = treeSize(glava.left);
		int right = treeSize(glava.right);
		
		return left + 1 + right;
	}
	
	/**
	 * Metoda koja vraca vrijednosti {@code true} ako binarno stablo
	 * kojem je korijen {@code glava} sadrzi element vrijednosti {@code value},
	 * a {@code false} inace.
	 * @param glava korijenski cvor binarnog stabla
	 * @param value vrijednost koju trazimo postoji li u stablu
	 * @return {@code true} ako vrijednost {@code value} jest sadrzana u stablu,
	 * 			a {@code false} inace.
	 */
	public static boolean containsValue(TreeNode glava, int value) {
		if(glava == null) {
			return false;
		}
		if(value == glava.value) {
			return true;
		}
		else if(value > glava.value) {
			return containsValue(glava.right, value);
		}
		else {
			return containsValue(glava.left, value);
		}
	}
	
	/**
	 * Ispisuje binarno stablo čiji je korijenski čvor {@code glava} 
	 * ovisno o argumentu {@code reverse}, 
	 * odnosno ako je {@code reverse} jednak {@code false} onda će
	 * se stablo ispisati uzlaznim poretkom (od najmanjeg prema najvecem), 
	 * ako je {@code reverse} jednak {@code true} onda će se ispisati silazno 
	 * (od najveceg prema najmanjem)
	 * @param glava korijenski cvor stabla
	 * @param reverse parametar o kojem ovisi nacin ispisa stabla;
	 * 					false - od najmanjeg prema najvecem;
	 * 					true - od najveceg prema najmanjem
	 */
	public static void ispisStabla(TreeNode glava, boolean reverse) {
		if(glava == null) {
			return;
		}
		if(!reverse) {
			//od najmanjeg prema najvecem
			ispisStabla(glava.left, reverse);
			System.out.print(glava.value + " ");
			ispisStabla(glava.right, reverse);
		}
		else {
			//od najveceg prema najmanjem
			ispisStabla(glava.right, reverse);
			System.out.print(glava.value + " ");
			ispisStabla(glava.left, reverse);
		}
	}
}
