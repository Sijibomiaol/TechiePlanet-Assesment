package com.techieplanet.assessment;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class TimeInWords {

    public static String timmeWords(int hours, int minutes) {
        if (hours < 1 || hours >12) {
            throw new IllegalArgumentException("Invalid hour:"   + hours + ". Hours must be between 1 and 12");
        }
        if (minutes < 0 || minutes > 59) {
            throw new IllegalArgumentException("Invalid minutes" + minutes + ". Minutes must be between 0 and 59");
        }
        if(minutes == 0){
            return timeInWords.get(hours) + " o' clock";
        }
        if (minutes == 15){
            return "quarter past " + timeInWords.get(hours);
        }

        if (minutes == 30){
            return "half past " + timeInWords.get(hours);
        }
        if (minutes == 45){
            int nextHour = (hours % 12) + 1;
            return "quarter to " + timeInWords.get(nextHour);
        }
        if (minutes <= 30){
            String minutesWord = timeInWords.get(minutes);
            String unit = (minutes ==1) ? "minute" : "minutes";
            return minutesWord + " " + unit + " past " + timeInWords.get(hours);
        }

        int minutesTo = 60 - minutes;
        int nextHour = (hours % 12) + 1;
        String minutesWord = timeInWords.get(minutesTo);
        String unit = (minutesTo == 1) ? "minute" : "minutes";
        return minutesWord + " " + unit + " to " + timeInWords.get(nextHour);


    }
    private static  final Map<Integer,String> timeInWords = new HashMap<Integer,String>();
    static {
        timeInWords.put(1,"one");
        timeInWords.put(2,"two");
        timeInWords.put(3,"three");
        timeInWords.put(4,"four");
        timeInWords.put(5,"five");
        timeInWords.put(6,"six");
        timeInWords.put(7,"seven");
        timeInWords.put(8,"eight");
        timeInWords.put(9,"nine");
        timeInWords.put(10,"ten");
        timeInWords.put(11,"eleven");
        timeInWords.put(12,"twelve");
        timeInWords.put(13,"thirteen");
        timeInWords.put(14,"fourteen");
        timeInWords.put(15,"fifteen");
        timeInWords.put(16,"sixteen");
        timeInWords.put(17,"seventeen");
        timeInWords.put(18,"eighteen");
        timeInWords.put(19,"nineteen");
        timeInWords.put(20,"twenty");
        timeInWords.put(21,"twenty one");
        timeInWords.put(22,"twenty two");
        timeInWords.put(23,"twenty three");
        timeInWords.put(24,"twenty four");
        timeInWords.put(25,"twenty five");
        timeInWords.put(26,"twenty six");
        timeInWords.put(27,"twenty seven");
        timeInWords.put(28,"twenty eight");
        timeInWords.put(29,"twenty nine");

    }


    public static void main(String[] args) {
        Scanner  scanner = new Scanner(System.in);
        try {
            int hours = scanner.nextInt();
            int minutes = scanner.nextInt();

            String result = timmeWords(hours, minutes);

            result = result.substring(0, 1).toUpperCase() + result.substring(1);
            System.out.println(result);
        }
        catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        }
        catch (InputMismatchException e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        }
        catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        }
        finally {
            scanner.close();
        }
    }
}
