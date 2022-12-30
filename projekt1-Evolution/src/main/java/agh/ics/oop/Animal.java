package agh.ics.oop;

import java.util.Random;

public class Animal {
    public int xPosition;
    public int yPosition;
    private int energy;
    private int direction;
    private int[] gens;
    private int genIndex;
    private int kids;
    private int age;
    private int eatenPlants;
    private int deathDay;

    public Animal(int e, int[] gens, int x, int y) {
        this.energy = e;
        Random generator = new Random();
        this.direction = generator.nextInt(8);
        this.gens = gens;
        this.genIndex = generator.nextInt(gens.length);
        this.kids = 0;
        this.age = 0;
        this.eatenPlants = 0;
        this.deathDay = -1;
        this.xPosition = x;
        this.yPosition = y;
    }

    public int getDeathDay() {
        return deathDay;
    }

    public int getEnergy() {
        return energy;
    }

    public int getAge() {
        return age;
    }

    public int getKids() {
        return kids;
    }

    public void changeEnergy(int x) {
        energy += x;
    }
    public int turnAround(TypeOfMovement type) {
        direction = (direction + gens[genIndex]) % 8;
        if(type == TypeOfMovement.CRAZY && new Random().nextInt(10) < 2) genIndex = new Random().nextInt(gens.length);
        else genIndex = (genIndex + 1) % gens.length;
        return direction;
    }

    public int getGen(int index) {
        return gens[index];
    }

    public void rotation180Degree() {
        direction = (direction + 4) % 8;
    }

    public void nextDay() {
        changeEnergy(-1);
    }

    public void bornKid(int birthEnergy) {
        kids++;
        changeEnergy(-birthEnergy);
        changeEnergy(-birthEnergy);
    }

    public void die(int day) {
        deathDay = day;
    }
    public boolean isAlive() {
        return energy > 0;
    }
}
