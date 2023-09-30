package com.tm.app.utils;

import java.util.Random;

public class RandomUtils {
	
	private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	// Set the desired length of the IDs
	private static final int ID_LENGTH = 3;

	// Create a random number generator
	private static final Random random = new Random();

	// Generate a random tracking ID
	public static String generateTrackingId() {
		return generateRandomId(ID_LENGTH);
	}

	// Generate a random order ID
	public static String generateOrderId() {
		return generateRandomId(ID_LENGTH);
	}

	// Helper method to generate a random ID of the specified length
	private static String generateRandomId(int length) {
		StringBuilder id = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			int randomIndex = random.nextInt(CHARACTERS.length());

			char randomChar = CHARACTERS.charAt(randomIndex);
			id.append(randomChar);
		}
		return id.toString()+System.currentTimeMillis();
	}

	
	public static String generatePassword() {
		int length=16;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+";
        StringBuilder password = new StringBuilder(length);
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            password.append(characters.charAt(index));
        }
        return password.toString();
    }
	
}
