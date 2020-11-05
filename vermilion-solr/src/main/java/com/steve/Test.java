package com.steve;

/**
 * @author stevexu
 * @since 2020/10/1
 */
public class Test {

	public static void main(String[] args) {
		System.out.println(getBaseDkScore(20, 5,100, 1000));
		System.out.println(getBaseDkScore(30, 8,500, 1000));
	}


	private static float getBaseDkScore(
		float clicks, float conversions, float exposes, float normImpression) {

		return (float) Math.sqrt((clicks + 25) * (conversions + 1))
			/ (normImpression + exposes) * 200;
	}
}
