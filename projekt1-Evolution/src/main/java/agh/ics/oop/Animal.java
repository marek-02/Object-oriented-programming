package agh.ics.oop;

public class Animal {
    private Vector2d position;
    private int energy;
    private int direction;
    private int[] gens;
    private int genIndex;
    private int kids;
    private int age;
    private int deathDay;

    public Animal(Vector2d p, int e, int d, int[] gens, int gI) {
        this.position = p;
        this.energy = e;
        this.direction = d;
        this.gens = gens;
        this.genIndex = gI;
        this.kids = 0;
        this.age = 0;
        this.deathDay = -1;
    }
}
