package agh.ics.oop;

import java.util.Objects;
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
    private int animalIndex;

    public Animal(int e, int[] gens, int x, int y, int index) {
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
        this.animalIndex = index;
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
    public void eatPlant(int x) {
        eatenPlants++;
        changeEnergy(x);
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

    public String getStringGens() {
        String s = "";
        int i;
        for(i = 0; i < gens.length - 1; i++){
            if(i == genIndex) s += gens[i] + ", <- current gen\n";
            else s += gens[i] + ", \n";
        }
        if(i == genIndex) s += gens[i] + " <- current gen";
        else s += gens[i];
        return s;
    }

    public int[] getGens() {
        return gens;
    }

    public int getEatenPlants() { return eatenPlants; }

    public void rotation180Degree() {
        direction = (direction + 4) % 8;
    }

    public void nextDay() {
        changeEnergy(-1);
        age++;
    }

    public void bornKid(int birthEnergy) {
        kids++;
        changeEnergy(-birthEnergy);
    }

    public void die(int day) {
        deathDay = day;
    }
    public boolean isAlive() {
        return energy > 0;
    }

    public boolean isWeak(int birthEnergy) {
        return energy <= birthEnergy;
    }

    public boolean isNormal(int birthEnergy, int fullEnergy) {
        return energy > birthEnergy && energy < fullEnergy;
    }

    public boolean isFed(int fullEnergy) {
        return energy >= fullEnergy && energy < 2 * fullEnergy;
    }

    public boolean isStrong(int fullEnergy) {
        return energy >= 2 * fullEnergy;
    }

    @Override
    public boolean equals(Object other) {
        if(other == this) {
            return true;
        }

        if(!(other instanceof Animal)) return false;

        Animal a = (Animal) other;
        return a.animalIndex == this.animalIndex;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this);
    }
}
