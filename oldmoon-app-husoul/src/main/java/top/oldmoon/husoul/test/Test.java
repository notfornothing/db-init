package top.oldmoon.husoul.test;

import top.oldmoon.husoul.model.Soul;

public class Test {
    public static void main(String[] args) {
        Soul soul1 = new Soul();
        soul1.setId("");
        Soul soul2 = new Soul();
        soul2.setId("123");

        System.out.println(soul1.hashCode());
        System.out.println(soul2.hashCode());
        System.out.println(soul1.hashCode() == soul2.hashCode());
        System.out.println(soul1.equals(soul2));

    }
}
