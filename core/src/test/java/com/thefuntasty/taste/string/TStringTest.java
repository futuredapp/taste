package com.thefuntasty.taste.string;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class TStringTest {

	@Test
	public void capitalize_withEmptyString_returnsEmptyString() {
		assertThat(TString.capitalize("")).isEqualTo("");
	}

	@Test
	public void capitalize_withSingleCharacterString_returnsSingleCharacter() {
		assertThat(TString.capitalize("b")).isEqualTo("B");
	}

	@Test
	public void capitalize_withLongerString_returnsLongerString() {
		assertThat(TString.capitalize("aaabbb")).isEqualTo("Aaabbb");
	}

	@Test
	public void capitalize_withCzechString_returnsCzechString() {
		assertThat(TString.capitalize("ščřžýáí")).isEqualTo("Ščřžýáí");
	}

	@Test
	public void isEmpty_withEmptyString_returnsTrue() {
		assertThat(TString.isEmpty("")).isTrue();
	}

	@Test
	public void isEmpty_withNullString_returnsTrue() {
		assertThat(TString.isEmpty(null)).isTrue();
	}

	@Test
	public void isEmpty_withNonEmptyString_returnsFalse() {
		assertThat(TString.isEmpty(" ")).isFalse();
	}

	@Test
	public void join_withListOfStrings_returnsJoinedString() {
		List<String> input = Arrays.asList("a", "b", "c");
		assertThat(TString.join(",", input)).isEqualTo("a,b,c");
	}

	@Test
	public void join_withListOfIntegers_returnsJoinedString() {
		List<Integer> input = Arrays.asList(1, 2, 3);
		assertThat(TString.join(",", input)).isEqualTo("1,2,3");
	}
}
