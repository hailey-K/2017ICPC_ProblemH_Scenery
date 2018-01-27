/*
Date : Jan 27th 2018
Name : Hailey Kim
 */
package problemh_scenery;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author RIM
 */
public class ProblemH_Scenery {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        int n; // the number of desired photographs
        int t; // time you spend to take each photograph
        List < Time > time = new ArrayList < Time > (); // photos that contains start and end 
        String[] splited = new String[2];

        splited = getUserInput("Please enter the number of desired photographs and the time you spend to take each photograph and put space in between.\n Please use this format eg.2 10. ");
        n = Integer.parseInt(splited[0]);
        t = Integer.parseInt(splited[1]);

        for (int i = 0; i < n; i++) {
            Time tempTime = new Time();
            splited = getUserInput("Please enter the earliest time that you may begin working on that phorograph, and b is the time by which the photograph must be completed.");
            tempTime.start = Integer.parseInt(splited[0]);
            tempTime.end = Integer.parseInt(splited[1]);
            time.add(tempTime);
        }

        time = sortByStart(time); // Sort by time's start order

        time = checkIfPossible(time, t); // Check if it is possible to take all n photographs

        if (time == null || time.isEmpty()) {
            System.out.println("Yes");
        } else {
            System.out.println("NO");
        }
    }

    public static List < Time > checkIfPossible(List < Time > time, int t) {
        int currentTime = 0; //track current time
        while (time != null && findMaxEndTime(time) >= currentTime) {
            if (time.size() == 1 && time.get(0).end - time.get(0).start <= t) {
                currentTime += time.get(0).start - currentTime + t;
                time.remove(time.get(0));
            } else if (time.size() == 1) {
                break;
            } else {
                if (time.get(1).start - time.get(0).start >= t) {
                    if (currentTime < time.get(0).start) {
                        currentTime += time.get(0).start - currentTime + t;
                    } else {
                        currentTime += t;
                    }
                    time.remove(time.get(0));
                } else if (time.get(1).end - time.get(1).start >= t) {
                    if (currentTime < time.get(1).start) {
                        currentTime += time.get(1).start - currentTime + t;
                    } else {
                        currentTime += t;
                    }
                    time.remove(time.get(0));
                }
            }
        }
        return time;
    }

    public static String[] getUserInput(String instruction) {
        boolean userInputFalse = true;
        String[] splited = new String[2];

        while (userInputFalse) {
            System.out.println(instruction);
            Scanner reader = new Scanner(System.in);
            String userInput = reader.nextLine();
            if (userInput.contains(" ")) {
                splited = userInput.split("\\s+");
            } else {
                System.out.println("Please use right format.");
            }

            if (!splited[0].matches("^[0-9]*$") || !splited[1].matches("^[0-9]*$")) {
                System.out.println("Please enter number only.");
            } else {
                userInputFalse = false;
            }
        }
        return splited;
    }

    public static List < Time > sortByStart(List < Time > oldTime) {
        List < Time > newTime = new ArrayList < Time > ();
        while (oldTime != null && !oldTime.isEmpty()) {
            int start = oldTime.get(0).start;
            int index = 0;
            for (int i = 0; i < oldTime.size(); i++) {
                if (start >= oldTime.get(i).start) {
                    start = oldTime.get(i).start;
                    index = i;

                }
            }
            newTime.add(oldTime.get(index));
            oldTime.remove(oldTime.get(index));

        }

        return newTime;
    }

    public static int findMaxEndTime(List < Time > time) {
        int max = 0;
        for (int i = 0; i < time.size(); i++) {
            if (max <= time.get(i).end) {
                max = time.get(i).end;
            }
        }
        return max;
    }
}