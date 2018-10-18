package hr.fer.zemris.java.hw01;

import org.junit.Assert;
import org.junit.Test;

/**
 * Testovi za metode koje se koriste u 
 * programu UniquNumbers
 * @author 0036498427
 *
 */
public class UniqueNumbersTest {

	@Test
	public void addNodeTestZaRazliciteBrojeve() {
		UniqueNumbers.TreeNode glava = null;
		
		glava = UniqueNumbers.addNode(glava, 15);
		glava = UniqueNumbers.addNode(glava, 45);
		glava = UniqueNumbers.addNode(glava, 2);
		glava = UniqueNumbers.addNode(glava, 60);
		glava = UniqueNumbers.addNode(glava, 90);
		glava = UniqueNumbers.addNode(glava, 10);
		glava = UniqueNumbers.addNode(glava, -5);
		
		Assert.assertEquals(-5, glava.left.left.value);
		Assert.assertEquals(10, glava.left.right.value);
		Assert.assertEquals(45, glava.right.value);
		Assert.assertEquals(60, glava.right.right.value);
		Assert.assertEquals(90, glava.right.right.right.value);
	}
	
	@Test
	public void treeSizeTestZaIsteElemente() {
		UniqueNumbers.TreeNode glava = null;
		
		for(int i = 0; i < 5; i++) {
			glava = UniqueNumbers.addNode(glava, 4);
		}
		//u slucaju da damo isti element vise puta broj elemenata mora biti 1
		// jer se u ovakvom stablu ne moze nalaziti vise istih brojeva
		Assert.assertEquals(1, UniqueNumbers.treeSize(glava));
		Assert.assertEquals(0, UniqueNumbers.treeSize(null));
	}
	
	@Test
	public void treeSizeTestZaRazliciteElemente() {
		UniqueNumbers.TreeNode glava = null;
		
		for(int i = 0; i< 50; i++) {
			glava = UniqueNumbers.addNode(glava, i);
		}
		Assert.assertEquals(50, UniqueNumbers.treeSize(glava));
		
		//nakon sto dodamo te iste elemente broj bi trebao ostati isti jer
		//svi ti brojevi vec postoje u stablu!
		for(int i = 0; i< 50; i++) {
			glava = UniqueNumbers.addNode(glava, i);
		}
		Assert.assertEquals(50, UniqueNumbers.treeSize(glava));
	}
	
	@Test
	public void containsValueTest() {
		//provjeravamo postoji li element koji smo dodali u stablo, provjeravamo hoce
		//li ispisati 0 u slucaju ako metodi damo prazno stablo
		UniqueNumbers.TreeNode glava = null;
		
		Assert.assertEquals(false, UniqueNumbers.containsValue(glava, 5));
		glava  = UniqueNumbers.addNode(glava, 25);
		glava = UniqueNumbers.addNode(glava, -45);
		Assert.assertEquals(true, UniqueNumbers.containsValue(glava, 25));
		Assert.assertEquals(false, UniqueNumbers.containsValue(glava, -44));
		Assert.assertEquals(true, UniqueNumbers.containsValue(glava, -45));
	}
}
