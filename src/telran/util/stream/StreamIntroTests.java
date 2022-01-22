package telran.util.stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StreamIntroTests {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void streamArraySourcesTest() {
		int array[] = {10,-8,17,13,10};
		int exp[] = {-8,10};
		int actual[] = Arrays.stream(array).filter(n -> n%2 == 0).distinct().sorted().toArray();
		assertArrayEquals(exp,actual);
		Arrays.stream(array).filter(n -> n%2 == 0).distinct().sorted().forEach(System.out::println);
	}
	@Test
	void streamRandomSourcesTest() {
		Random gen = new Random();
		gen.ints().limit(10).forEach(System.out::println);
		assertEquals(10, gen.ints().limit(10).toArray().length);
		gen.ints(10,10,25).forEach(n -> assertTrue(n >= 10 && n < 25));
	}
	@Test
	void streamCollectionSourcesTest() {
		List<Integer> list = Arrays.asList(10,-8,30);
		Integer[] ar = list.stream().toArray(Integer[]::new);
	}
	@Test
	void streamStringSourcesTest() {
		String str = "Hello";
		
		str.chars().forEach(n -> System.out.printf("%c;", n));
	}
	@Test
	void conversionFromIntToInteger() {
		List<Integer> expected = Arrays.asList(1,2,3);
		int array[] = {1,2,3};
		List<Integer> actual = Arrays.stream(array).boxed().collect(Collectors.toList());
		assertIterableEquals(expected,actual);
	}
	@Test
	void conversionFromIntegerToInt() {
		List<Integer> list = Arrays.asList(1,2,3);
		int sum = 6;
		assertEquals(sum, list.stream().mapToInt(n -> n).sum());
		assertArrayEquals(new int[] {1,2,3}, list.stream().mapToInt(n->n).toArray());
	}
	
	private Integer[] getLotoNumbers(int nNumbers, int min, int max) {
		Random gen = new Random();
		return gen.ints().filter(n -> n>min && n<max).distinct().limit(nNumbers).boxed().toArray(Integer[]::new);
	}
	@Test
	void lotoTest() {
		Integer[] lotoNumbers = getLotoNumbers(7,1,49);
		assertEquals(7, lotoNumbers.length);
		assertEquals(7, new HashSet<Integer>(Arrays.asList(lotoNumbers)).size());
		Arrays.stream(lotoNumbers).forEach(n -> assertTrue(n>=1 && n<=49));
	}
	
	private boolean isHalfSum(int[]ar) {
		
		int sum = Arrays.stream(ar).sum();
		if(sum%2==0) {
			int halfSum = sum/2;
			HashSet<Integer> hash = new HashSet<>();
			for(int i = 0; i < ar.length; i++) {
				int secondOperand = halfSum - ar[i];
				if(hash.contains(secondOperand)) {
					return true;
				}
				hash.add(ar[i]);
			}
		}
		return false;
	}
	@Test
	void isHalfSumTest() {
		int ar[] = {1,2, 10, -7};
		assertTrue(isHalfSum(ar));
		int ar1[] = {1, 2, 10, 7};
		assertFalse(isHalfSum(ar1));
		int ar2[] = {2};
		assertFalse(isHalfSum(ar2));
	}
}
