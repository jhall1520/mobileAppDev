package com.company;

import java.util.*;

/*
 InClass01
 Group14_InClass01
 Joel Hall
 Jimmy Kropp
*/
public class Main {

    public static void main(String[] args) {
	// write your code here
//        fizzBuzz();
//        System.out.println(isEven(6));
//        Integer[] numbers = new Integer[4];
//        numbers[0] = 6;
//        numbers[1] = 7;
//        numbers[2] = -10;
//        numbers[3] = 1;
//        System.out.println(getMinimum(numbers));
        ArrayList<User> users = getParsedUsers(Data.users);
//        for (int i = 0; i < users.size(); i++) {
//            System.out.println(users.toString());
//        }
        //printUsers_OMN(users);
//        printUsersSortedByAge(users);
        //printUsersOldest10(users);
        //printUserStateStats(users);
//        Set<String> overlap = getWordOverlap(Data.words_1, Data.words_1);
//        System.out.println(overlap.toString());
//        System.out.println(overlap.size());
//        System.out.println(Data.words_1.length);
        ArrayList<User> otherUsers = getParsedUsers(Data.otherUsers);
        //System.out.println(getUserOverlap(users, otherUsers).toString());
    }

    //Question 1
    public static void fizzBuzz(){
        for (int i = 1; i <= 20; i++) {
            if (i % 3 == 0 && i % 5 == 0) {
                System.out.println("FizzBuzz");
            } else if (i % 3 == 0) {
                System.out.println("Fizz");
            } else if (i % 5 == 0) {
                System.out.println("Buzz");
            }
        }
    }

    //Question 2
    public static boolean isEven(Integer num){
        if (num % 2 == 0)
            return true;
        else
            return false;
    }

    //Question 3
    public static Integer getMinimum(Integer[] numbers){

        if (numbers == null || numbers.length == 0)
            return null;

        int minimum = numbers[0];
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] < minimum) {
                minimum = numbers[i];
            }
        }
        return minimum;
    }


    //Question 4
    public static ArrayList<User> getParsedUsers(String[] strings){
        ArrayList<User> users = new ArrayList<>();
        for (int i = 0; i < strings.length; i++) {
            User user = new User(strings[i]);
            users.add(user);
        }
        return users;
    }

    //Question 5
    public static void printUsers_OMN(ArrayList<User> users){
        if (users != null && users.size() > 0) {
            for (int i = 0; i < users.size(); i++) {
                String firstname = users.get(i).firstname;
                if (firstname.charAt(0) =='O' || firstname.charAt(0) =='M' || firstname.charAt(0) =='N')
                    System.out.println(firstname);
            }
        }
    }


    //Question 6
    public static void printUsersSortedByAge(ArrayList<User> users){
        Collections.sort(users, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return Integer.parseInt(o1.age) - Integer.parseInt(o2.age);
            }
        });

        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            System.out.println(user.firstname + " " + user.lastname + ": " + user.age);
        }
    }

    //Question 7
    public static void printUsersOldest10(ArrayList<User> users){
        Collections.sort(users, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return Integer.parseInt(o1.age) - Integer.parseInt(o2.age);
            }
        });

        for (int i = users.size() - 10; i < users.size(); i++) {
            User user = users.get(i);
            System.out.println(user.firstname + " " + user.lastname + ": " + user.age);
        }
    }

    //Question 8
    public static void printUserStateStats(ArrayList<User> users){
        Map<String, Integer> states = new HashMap<>();
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            if (states.isEmpty() || !states.containsKey(user.state)) {
                states.put(user.state, 1);
            } else {
                int num = states.get(user.state);
                num++;
                states.replace(user.state, num);
            }
        }
        Set<String> stateNames = states.keySet();
        for (String curState : stateNames) {
            System.out.println(curState + ": " + states.get(curState));
        }
    }

    //Question 9
    public static Set<String> getWordOverlap(String[] listA, String[] listB){
        Set<String> listAWords = new HashSet<>();
        listAWords.addAll(Arrays.asList(listA));

        Set<String> listBWords = new HashSet<>();
        listBWords.addAll(Arrays.asList(listB));

        Set<String> overLapWords = new HashSet<>();

        for (String curWord : listAWords) {
            if (listBWords.contains(curWord)) {
                overLapWords.add(curWord);
            }
        }
        return overLapWords;
    }

    //Question 10
    public static ArrayList<User> getUserOverlap(ArrayList<User> usersA, ArrayList<User> usersB){
        ArrayList<User> overlapUsers = new ArrayList<>();
        for (int i = 0; i < usersA.size(); i++) {
            for (int j = 0; j < usersB.size(); j++) {
                if (usersA.get(i).equals(usersB.get(j)))
                    overlapUsers.add(usersA.get(i));
            }
        }
        return overlapUsers;
    }



}
