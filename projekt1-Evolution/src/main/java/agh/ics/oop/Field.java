package agh.ics.oop;

import java.util.ArrayList;

public class Field {
    private boolean hasPlant;
    private boolean isAttractive;
    private int deads;
    private int indexDeads;
    private Field prevDeads;
    private Field nextDeads;
    private ArrayList<Integer> animals;

    public Field() {
        indexDeads = 0;
        isAttractive = false;
        hasPlant = false;
        deads = 0;
        prevDeads = nextDeads = null;
        animals = new ArrayList<>();
    }

    public void animalDied() {
        deads++;
    }

    public void noLongerAttractive() {
        isAttractive = false;
    }

    public void makeItAttractive() {
        isAttractive = true;
    }

    public boolean isAttractive() {
        return isAttractive;
    }

    public int getIndexDeads() {
        return indexDeads;
    }

    public void setIndexDeads(int indexDeads) {
        this.indexDeads = indexDeads;
    }

    public Field getNextDeads() {
        return nextDeads;
    }

    public Field getPrevDeads() {
        return prevDeads;
    }

    public void setNextDeads(Field nextDeads) {
        this.nextDeads = nextDeads;
    }

    public void setPrevDeads(Field prevDeads) {
        this.prevDeads = prevDeads;
    }

    public void addAnimal(int index) {
        animals.add(index);
    }

    public void addPlant() {
        hasPlant = true;
    }

    public boolean hasPlant() {
        return hasPlant;
    }

    public int howManyDeads() {
        return deads;
    }

    public void removeAnimal(Integer index) {
        animals.remove(index);
    }

    public int numberOfAnimals() {
        return animals.size();
    }

    public ArrayList<Integer> getAnimals() {
        return animals;
    }

    public void setAnimals(ArrayList<Integer> animals) {
        this.animals = animals;
    }

    public void deletePlant() {
        hasPlant = false;
    }
}
