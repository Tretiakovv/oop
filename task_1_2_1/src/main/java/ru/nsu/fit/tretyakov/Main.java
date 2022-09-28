package ru.nsu.fit.tretyakov;

class Parent{
    Parent(){
        System.out.print("P");
    }
    public void doIt(){
        System.out.print("A");
    }
}

class Child extends Parent{
    Child(){
        super();
        System.out.println("C");
    }

    public void doIt() {
        System.out.println("B");
    }
}

public class Main {
    public static void main(String[] args) {
        Parent parent = new Child();
        parent.doIt();
    }
}