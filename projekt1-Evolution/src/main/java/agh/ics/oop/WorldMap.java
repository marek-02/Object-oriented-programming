package agh.ics.oop;

import java.util.*;

public class WorldMap {
    private int width;
    private int height;
    private int day;
    private int energyFromPlant;
    private int dayPlants;
    private int fullEnergy;
    private int birthEnergy;
    private int minMutation;
    private int maxMutation;
    private int genomLength;
    private Field[][] fields;
    private Field head0;
    private Field head20;
    private ArrayList<Animal> allAnimals;
    private int allAnimalsIndex;
    private int numberOfPlants;
    private int numberOfAttractiveFields;
    private int freeAttractiveFields;
    private int freeNormalFields;
    private TypeOfPlanet typeOfPlanet;
    private TypeOfPlants typeOfPlants;
    private TypeOfMutation typeOfMutation;
    private TypeOfMovement typeOfMovement;

    public WorldMap(int width, int height, int energyFromPlant, int startPlants, int dayPlants, int startAnimals, int startEnergy, int fullEnergy,
                    int birthEnergy, int minMutation, int maxMutation,
                    int genomLength, TypeOfPlants typeOfPlants, TypeOfMutation typeOfMutation, TypeOfMovement typeOfMovement, TypeOfPlanet typeOfPlanet) {
        this.width = width;             //szerokość od 0 do width-1
        this.height = height;           //wysokość od 0 do height-1
        this.day = 0;
        this.energyFromPlant = energyFromPlant;
        this.dayPlants = dayPlants;
        this.fullEnergy = fullEnergy;
        this.birthEnergy = birthEnergy;
        this.minMutation = minMutation;
        this.maxMutation = maxMutation;
        this.genomLength = genomLength;
        this.fields = new Field[height][width];
        this.allAnimals = new ArrayList<>();
        this.allAnimalsIndex = 0;
        this.numberOfPlants = 0;
        if(typeOfPlants == TypeOfPlants.EQATOR) {
            int a = 2 * height / 5;
            int b = 3 * height / 5;
            this.numberOfAttractiveFields = (b - a) * width;
            this.freeAttractiveFields = this.numberOfAttractiveFields;
            this.freeNormalFields = width * height -  this.freeAttractiveFields;
        } else {
            setDeadsLinkedList();
            this.numberOfAttractiveFields = width * height / 5;
            this.freeAttractiveFields = this.numberOfAttractiveFields;
            this.freeNormalFields = width * height -  this.freeAttractiveFields;
            head0 = fields[0][0];
            head20 = fields[numberOfAttractiveFields / width][numberOfAttractiveFields % width];
        }
        this.typeOfPlanet = typeOfPlanet;
        this.typeOfPlants = typeOfPlants;
        this.typeOfMutation = typeOfMutation;
        this.typeOfMovement = typeOfMovement;
        this.growingPlants(startPlants);
        this.placeStartAnimals(startAnimals, startEnergy, genomLength);
    }

    private void setDeadsLinkedList() {
        int prevX = width - 1;
        int prevY = height - 1;
        int nextX = 0;
        int nextY = 0;
        for(int y = 0; y < height; y++)
            for(int x = 0; x < width; x++) {
                fields[y][x].setPrevDeads(fields[prevY][prevX]);
                prevX = x;
                prevY = y;
                nextX++;
                if(nextX == width) {
                    nextX = 0;
                    nextY++;
                }
                fields[y][x].setNextDeads(fields[nextY][nextX]);
                int newIndex = y * width + x;
                fields[y][x].setIndexDeads(newIndex);
                if(newIndex < numberOfAttractiveFields) fields[y][x].makeItAttractive();
            }
        fields[0][0].setPrevDeads(null);
        fields[height - 1][width - 1].setNextDeads(null);
    }
    private int findFieldForEquator(int start, int end, int fieldIndex) {
        for(int y = start; y < end; y++){
            for(int x = 0; x < width; x++) {
                if(!fields[y][x].hasPlant()) {
                    if(fieldIndex == 0) {
                        fields[y][x].addPlant();
                        return - 1;
                    } else fieldIndex--;
                }
            }
        }
        return fieldIndex;
    }

    private void findFieldForCorpses(Field start, int fieldIndex) {
        Field f = start;
        while(fieldIndex >= 0) {
            if(!f.hasPlant()) {
                if(fieldIndex == 0) {
                    f.addPlant();
                    return;
                } else fieldIndex--;
            }
            f = f.getNextDeads();
        }
    }

    private void chooseFieldForPlantForEquator() {
        Random generator = new Random();
        int tmp = generator.nextInt(10);
        if(tmp < 2) {
            if (freeNormalFields == 0) return;
            int fieldIndex = generator.nextInt(freeNormalFields);
            int a = 2 * height / 5;
            int b = 3 * height / 5;
            fieldIndex = findFieldForEquator(0, a + 1, fieldIndex);
            if(fieldIndex != -1) findFieldForEquator(b + 1, height, fieldIndex);
            freeNormalFields--;
            numberOfPlants++;
        } else {
            if (freeAttractiveFields == 0) return;
            int fieldIndex = generator.nextInt(freeAttractiveFields);
            int a = 2 * height / 5;
            int b = 3 * height / 5;
            findFieldForEquator(a + 1, b + 1, fieldIndex);
            freeAttractiveFields--;
            numberOfPlants++;
        }
    }

    private void chooseFieldForPlantForCorpses() {
        Random generator = new Random();
        int tmp = generator.nextInt(10);
        if(tmp < 2) {
            if (freeNormalFields == 0) return;
            int fieldIndex = generator.nextInt(freeNormalFields);
            findFieldForCorpses(head20, fieldIndex);
            freeNormalFields--;
            numberOfPlants++;
        } else {
            if (freeAttractiveFields == 0) return;
            int fieldIndex = generator.nextInt(freeAttractiveFields);
            findFieldForCorpses(head0, fieldIndex);
            freeAttractiveFields--;
            numberOfPlants++;
        }
    }

    private void growingPlants(int numberOfPlants) {
        for(int i = 0; i < numberOfPlants; i++) {
            if (typeOfPlants == TypeOfPlants.EQATOR) {
                chooseFieldForPlantForEquator();
            } else {
                chooseFieldForPlantForCorpses();
            }
        }
    }

    private void placeAnimal(int energy, int[] gens, int x, int y) {
        Animal a = new Animal(energy, gens, x, y);
        allAnimals.add(a);
        fields[y][x].addAnimal(allAnimalsIndex++);
    }

    private void placeStartAnimals(int startAnimals, int energy, int genomLength) {
        Random generator = new Random();
        for(int i = 0; i < startAnimals; i++) {
            int x = generator.nextInt(width);
            int y = generator.nextInt(height);
            int[] gens = new int[genomLength];
            for(int j = 0; j < genomLength; j++) {
                gens[j] = generator.nextInt(8);
            }
            placeAnimal(energy, gens, x, y);
        }
    }

    private void moveAnimals() {
        for(int i = 0; i < allAnimals.size(); i++) {
            Animal a = allAnimals.get(i);
            if(a.getDeathDay() == -1) {
                a.nextDay();
                fields[a.yPosition][a.xPosition].removeAnimal(i);
                if(a.isAlive())
                    moveAnimal(a, i, typeOfMovement);
                else {
                    animalDied(a);
                }
            }
        }
    }

    private void animalDied(Animal a) {
        int x = a.xPosition;
        int y = a.yPosition;
        a.die(day);
        fields[y][x].animalDied();

        if(typeOfPlants == TypeOfPlants.CORPSES) {
            changeDeadsLinkedList(fields[y][x]);
        }
    }

    private void changeDeadsLinkedList(Field f) {
        while(f.getNextDeads() != null && f.howManyDeads() > f.getNextDeads().howManyDeads())
            swapWithNext(f);
    }

    private void swapWithNext(Field f) {
        Field prev = f.getPrevDeads();
        Field next = f.getNextDeads();
        if(prev != null) prev.setNextDeads(next);
        next.setPrevDeads(prev);
        next.setNextDeads(f);
        f.setPrevDeads(next);
        f.setNextDeads(next.getNextDeads());
        if(next.getNextDeads() != null) next.getNextDeads().setPrevDeads(f);
        int tmp = next.getIndexDeads();
        next.setIndexDeads(f.getIndexDeads());
        f.setIndexDeads(tmp);
        if(f.isAttractive() && !next.isAttractive()) {
            f.noLongerAttractive();
            next.makeItAttractive();
            if(f.hasPlant() && !next.hasPlant()) {
                freeAttractiveFields++;
                freeNormalFields--;
            } else if (!f.hasPlant() && next.hasPlant()) {
                freeAttractiveFields--;
                freeNormalFields++;
            }
        }
    }
    private void moveAnimal(Animal a, int index, TypeOfMovement type) {
        int dir = a.turnAround(type);
        int changeY = 0;
        int changeX = 0;
        if(dir == 7 || dir == 0 || dir == 1) changeY = 1;
        else if(dir == 3 || dir == 4 || dir == 5) changeY = -1;
        if(dir == 1 || dir == 2 || dir == 3) changeX = 1;
        else if(dir == 5 || dir == 6 || dir == 7) changeX = -1;
        int newX = a.xPosition + changeX;
        int newY = a.yPosition + changeY;
        if(newX >= width || newX < 0 || newY >= height || newY < 0) {
            if(typeOfPlanet == TypeOfPlanet.PORTAL) {
                Random generator = new Random();
                newX = generator.nextInt(width);
                newY = generator.nextInt(height);
                a.changeEnergy(-birthEnergy);
            } else {
                if (newY >= height || newY < 0) {
                    newX = a.xPosition;
                    newY = a.yPosition;
                    a.rotation180Degree();
                }
                if(newX >= width || newX < 0) {
                    newX = (newX + width) % width;
                }
            }
        }
        a.xPosition = newX;
        a.yPosition = newY;
        fields[newY][newX].addAnimal(index);
    }

    class SortAnimals implements Comparator<Integer> {
        public int compare(Integer animalIndex1, Integer animalIndex2) {
            Random generator = new Random();
            int energyDiff = allAnimals.get(animalIndex1).getEnergy() - allAnimals.get(animalIndex2).getEnergy();
            int ageDiff = allAnimals.get(animalIndex1).getAge() - allAnimals.get(animalIndex2).getAge();
            int kidsDiff = allAnimals.get(animalIndex1).getKids() - allAnimals.get(animalIndex2).getKids();
            return energyDiff != 0 ? energyDiff : ageDiff != 0 ? ageDiff : kidsDiff != 0 ? kidsDiff : generator.nextInt(3) - 1;
        }
    }

    private void deletePlant(int x, int y) {
        fields[y][x].deletePlant();
        if(typeOfPlants == TypeOfPlants.EQATOR) {
            int a = 2 * height / 5;
            int b = 3 * height / 5;
            if(y > a && a <= b) freeAttractiveFields++;
            else freeNormalFields++;
        } else {
            if(fields[y][x].getIndexDeads() < numberOfAttractiveFields) freeAttractiveFields--;
            else freeNormalFields--;
        }
    }

    private void feedAnimals() {
        for(int y = 0; y < height; y++)
            for(int x = 0; x < width; x++) {
                if(fields[y][x].hasPlant()) {
                    ArrayList<Integer> a = fields[y][x].getAnimals();
                    int n = fields[y][x].numberOfAnimals();
                    if(n > 2) {
                        Collections.sort(a, new SortAnimals());
                        fields[y][x].setAnimals(a);
                    }
                    if(n > 0) {
                        allAnimals.get(a.get(0)).changeEnergy(energyFromPlant);
                        deletePlant(x, y);
                    }
                }
            }
    }

    private void reproduceAnimals() {
        for(int y = 0; y < height; y++)
            for(int x = 0; x < width; x++) {
                int n = fields[y][x].numberOfAnimals();
                ArrayList<Integer> a = fields[y][x].getAnimals();
                for(int i = 0; i < n - 1; i += 2) {
                    Animal a1 = allAnimals.get(a.get(i));
                    Animal a2 = allAnimals.get(a.get(i + 1));
                    if(a2.getEnergy() > fullEnergy) reproduceAnimal(x, y, a1, a2);
                }
            }
    }

    private void reproduceAnimal(int x, int y, Animal a1, Animal a2) {
        Random generator = new Random();
        int e1 = a1.getEnergy();
        int e2 = a2.getEnergy();
        int biggerEnergy = Math.max(e1, e2);
        int smallerEnergy = Math.min(e1, e2);
        Animal animalWithBiggerEnergy = e1 > e2 ? a1 : a2;
        Animal animalWithSmallerEnergy = e1 > e2 ? a2 : a1;
        int side = generator.nextInt(2);
        int changeIndex;
        if(side == 0) changeIndex = biggerEnergy * genomLength / (e1 + e2);
        else changeIndex = smallerEnergy * genomLength / (e1 + e2);
        int[] newGens = new int[genomLength];
        for(int i = 0; i < changeIndex; i++) {
            if(side == 0) newGens[i] = animalWithBiggerEnergy.getGen(i);
            else newGens[i] = animalWithSmallerEnergy.getGen(i);
        }
        for(int i = changeIndex; i < genomLength; i++) {
            if(side == 0) newGens[i] = animalWithSmallerEnergy.getGen(i);
            else newGens[i] = animalWithBiggerEnergy.getGen(i);
        }
        for(int i = minMutation; i <= maxMutation; i++) {
            int index = generator.nextInt(genomLength);
            int newGen = typeOfMutation == TypeOfMutation.RANDOM ? generator.nextInt(8) :
                    generator.nextInt(2) == 0 ? newGens[index] - 1 : newGens[index] + 1;
            newGens[index] = newGen;
        }
        placeAnimal(birthEnergy * 2, newGens, x, y);
        a1.bornKid(birthEnergy);
        a2.bornKid(birthEnergy);
    }

    public void day() {
        day++;
        moveAnimals();
        feedAnimals();
        reproduceAnimals();
        growingPlants(dayPlants);
    }
}
