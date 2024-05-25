package mediaproject.its.for_test.util;

import java.util.Random;

public class MakeRandomValues {

    public static int getRandomNumber(int min, int max) {
        return (int) (Math.random() * (max - min + 1)) + min;
    }

    public static String getRandomString() {
        StringBuilder sb = new StringBuilder();
        Random rd = new Random();

        for(int i=0;i<20;i++){
            sb.append((char)(rd.nextInt(26)+65));
        }

        return sb.toString();
    }

    public static String getRandomTechStackType() {
        String[] techStackTypes = {"REACT", "TYPESCRIPT", "JAVASCRIPT", "VUE", "NEXTJS", "NODEJS","JAVA","SPRING","KOTLIN","NESTJS","SWIFT","FLUTTER","FIGMA"};
        return techStackTypes[getRandomNumber(0, techStackTypes.length - 1)];
    }

    public static String getRandomHiringType() {
        String[] hiringTypes = {"PROJECT", "STUDY"};
        return hiringTypes[getRandomNumber(0, hiringTypes.length - 1)];
    }

    public static String getRandomPositionType() {
        String[] positionTypes = {"FRONTEND", "BACKEND","PM","DESIGNER"};
        return positionTypes[getRandomNumber(0, positionTypes.length - 1)];
    }

    public static String getRandomProcessType() {
        String[] processTypes = {"ONLINE", "OFFLINE","BOTH"};
        return processTypes[getRandomNumber(0, processTypes.length - 1)];
    }

    public static String getRandomRecruitingType() {
        String[] recruitingTypes = {"ON", "OFF"};
        return recruitingTypes[getRandomNumber(0, recruitingTypes.length - 1)];
    }
}