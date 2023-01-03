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
    private int startPlants;
    private int startAnimals;
    private int startEnergy;
    private TypeOfPlanet typeOfPlanet;
    private TypeOfPlants typeOfPlants;
    private TypeOfMutation typeOfMutation;
    private TypeOfMovement typeOfMovement;
    private Map<int[], Integer> numberOfGenotypes;
    private int numberOfAnimalsOnMap;

    public WorldMap(WorldMap map) {
        this.width = map.width;             //szerokość od 0 do width-1
        this.height = map.height;           //wysokość od 0 do height-1
        this.day = 0;
        this.energyFromPlant = map.energyFromPlant;
        this.dayPlants = map.dayPlants;
        this.fullEnergy = map.fullEnergy;
        this.birthEnergy = map.birthEnergy;
        this.minMutation = map.minMutation;
        this.maxMutation = map.maxMutation;
        this.genomLength = map.genomLength;
        this.fields = new Field[height][width];
        for(int y = 0; y < height; y++)
            for(int x = 0; x < width; x++) fields[y][x] = new Field();
        this.allAnimals = new ArrayList<>();
        this.allAnimalsIndex = 0;
        this.numberOfPlants = 0;
        this.numberOfAnimalsOnMap = 0;
        this.numberOfGenotypes = new HashMap<>();
        this.typeOfPlanet = map.typeOfPlanet;
        this.typeOfPlants = map.typeOfPlants;
        this.typeOfMutation = map.typeOfMutation;
        this.typeOfMovement = map.typeOfMovement;
        this.startAnimals = map.startAnimals;
        this.startPlants = map.startPlants;
        this.startEnergy = map.startEnergy;
        if(typeOfPlants == TypeOfPlants.EQATOR) {
            int a = 2 * height / 5;
            int b = 3 * height / 5;
            this.numberOfAttractiveFields = (b - a) * width;
            this.freeAttractiveFields = this.numberOfAttractiveFields;
            this.freeNormalFields = width * height -  this.freeAttractiveFields;
            setFieldsAttractiveForEquator(a, b);
        } else {
            this.numberOfAttractiveFields = width * height / 5;
            this.freeAttractiveFields = this.numberOfAttractiveFields;
            this.freeNormalFields = width * height -  this.freeAttractiveFields;
            head0 = fields[0][0];
            head20 = fields[numberOfAttractiveFields / width][numberOfAttractiveFields % width];
            setDeadsLinkedList();
        }
        this.growingPlants(startPlants);
        this.placeStartAnimals(startAnimals, startEnergy, genomLength);
    }

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
        for(int y = 0; y < height; y++)
            for(int x = 0; x < width; x++) fields[y][x] = new Field();
        this.allAnimals = new ArrayList<>();
        this.allAnimalsIndex = 0;
        this.numberOfPlants = 0;
        this.numberOfAnimalsOnMap = 0;
        this.numberOfGenotypes = new HashMap<>();
        this.typeOfPlanet = typeOfPlanet;
        this.typeOfPlants = typeOfPlants;
        this.typeOfMutation = typeOfMutation;
        this.typeOfMovement = typeOfMovement;
        this.startAnimals = startAnimals;
        this.startPlants = startPlants;
        this.startEnergy = startEnergy;
        if(typeOfPlants == TypeOfPlants.EQATOR) {
            int a = 2 * height / 5;
            int b = 3 * height / 5;
            this.numberOfAttractiveFields = (b - a) * width;
            this.freeAttractiveFields = this.numberOfAttractiveFields;
            this.freeNormalFields = width * height -  this.freeAttractiveFields;
            setFieldsAttractiveForEquator(a, b);
        } else {
            this.numberOfAttractiveFields = width * height / 5;
            this.freeAttractiveFields = this.numberOfAttractiveFields;
            this.freeNormalFields = width * height -  this.freeAttractiveFields;
            head0 = fields[0][0];
            head20 = fields[numberOfAttractiveFields / width][numberOfAttractiveFields % width];
            setDeadsLinkedList();
        }
        this.growingPlants(startPlants);
        this.placeStartAnimals(startAnimals, startEnergy, genomLength);
    }

    private void setFieldsAttractiveForEquator(int a, int b) {
        for(int y = a + 1; y <= b; y++)
            for(int x = 0; x < width; x++) fields[y][x].makeItAttractive();
    }

    private void setDeadsLinkedList() {
        int prevX = 0;
        int prevY = 0;
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
                    nextY = Math.min(nextY + 1, height - 1);
                }
                fields[y][x].setNextDeads(fields[nextY][nextX]);
                int newIndex = y * width + x;
                fields[y][x].setIndexDeads(newIndex);
                if(newIndex < numberOfAttractiveFields) fields[y][x].makeItAttractive();
                else if(newIndex == numberOfAttractiveFields) head20 = fields[y][x];
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
                        return -1;
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
        Animal a = new Animal(energy, gens, x, y, allAnimalsIndex);
        allAnimals.add(a);
        this.numberOfAnimalsOnMap++;
        fields[y][x].addAnimal(allAnimalsIndex++);
        if(numberOfGenotypes.containsKey(gens)) numberOfGenotypes.replace(gens, numberOfGenotypes.get(gens) + 1);
        else numberOfGenotypes.put(gens, 1);
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
        this.numberOfAnimalsOnMap--;
        if(typeOfPlants == TypeOfPlants.CORPSES) {
            changeDeadsLinkedList(fields[y][x]);
        }
    }

    private void changeDeadsLinkedList(Field f) {
        while(f.getNextDeads() != null && f.howManyDeads() > f.getNextDeads().howManyDeads()) {
            swapWithNext(f);
        }

    }

    private void swapWithNext(Field f) {
        int tmp = f.getNextDeads().getIndexDeads();
        if(f.getPrevDeads() != null) f.getPrevDeads().setNextDeads(f.getNextDeads());
        else head0 = f.getNextDeads();
        f.getNextDeads().setPrevDeads(f.getPrevDeads());
        f.setNextDeads(f.getNextDeads().getNextDeads());
        if(f.getNextDeads() != null) f.getNextDeads().setPrevDeads(f);
        f.getPrevDeads().getNextDeads().setNextDeads(f);
        f.setPrevDeads(f.getPrevDeads().getNextDeads());



        if(f.getIndexDeads() == numberOfAttractiveFields) head20 = f.getPrevDeads();
        f.getPrevDeads().setIndexDeads(f.getIndexDeads());
        f.setIndexDeads(tmp);
        if(tmp == numberOfAttractiveFields) {
            head20 = f;
            f.noLongerAttractive();
            f.getPrevDeads().makeItAttractive();
            if(f.hasPlant() && !f.getPrevDeads().hasPlant()) {
                freeAttractiveFields++;
                freeNormalFields--;
            } else if (!f.hasPlant() && f.getPrevDeads().hasPlant()) {
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

    private void deletePlant(int x, int y) {
        fields[y][x].deletePlant();
        if(typeOfPlants == TypeOfPlants.EQATOR) {
            int a = 2 * height / 5;
            int b = 3 * height / 5;
            if(y > a && a <= b) freeAttractiveFields++;
            else freeNormalFields++;
        } else {
            if(fields[y][x].getIndexDeads() < numberOfAttractiveFields) {
                freeAttractiveFields++;
            }
            else freeNormalFields++;
        }
        numberOfPlants--;
    }

    private void feedAnimals() {
        for(int y = 0; y < height; y++)
            for(int x = 0; x < width; x++) {
                if(fields[y][x].hasPlant()) {
                    ArrayList<Integer> a = fields[y][x].getAnimals();
                    int n = fields[y][x].numberOfAnimals();
                    if(n > 0) {
                        allAnimals.get(a.get(0)).eatPlant(energyFromPlant);
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
                    if(a2.getEnergy() > fullEnergy && a1.getEnergy() > fullEnergy) reproduceAnimal(x, y, a1, a2);
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
                    generator.nextInt(2) == 0 ? (newGens[index] - 1 + 8) % 8 : (newGens[index] + 1) % 8;
            newGens[index] = newGen;
        }
        placeAnimal(birthEnergy * 2, newGens, x, y);
        a1.bornKid(birthEnergy);
        a2.bornKid(birthEnergy);
    }

    class SortAnimals implements Comparator<Integer> {
        public int compare(Integer animalIndex1, Integer animalIndex2) {
            Random generator = new Random();
            int energyDiff = allAnimals.get(animalIndex2).getEnergy() - allAnimals.get(animalIndex1).getEnergy();
            int ageDiff = allAnimals.get(animalIndex2).getAge() - allAnimals.get(animalIndex1).getAge();
            int kidsDiff = allAnimals.get(animalIndex2).getKids() - allAnimals.get(animalIndex1).getKids();
            return energyDiff != 0 ? energyDiff : ageDiff != 0 ? ageDiff : kidsDiff != 0 ? kidsDiff : generator.nextInt(3) - 1;
        }
    }

    private void sortAnimals() {
        for(int y = 0; y < height; y++)
            for(int x = 0; x < width; x++) {
                int n = fields[y][x].numberOfAnimals();
                if(n > 2) {
                    ArrayList<Integer> a = fields[y][x].getAnimals();
                    Collections.sort(a, new SortAnimals());
                    fields[y][x].setAnimals(a);
                }
            }
    }
    public void day() {
        sortAnimals();
        day++;
        moveAnimals();
        feedAnimals();
        reproduceAnimals();
        growingPlants(dayPlants);
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Field getField(int x, int y) {
        return fields[y][x];
    }

    public ArrayList<Animal> getAnimalsFromField(int x, int y) {
        ArrayList<Integer> animalsIndexes = fields[y][x].getAnimals();
        ArrayList<Animal> animals = new ArrayList<>();
        for(Integer index : animalsIndexes) {
            animals.add(allAnimals.get(index));
        }
        return animals;
    }

    public Animal getRandomAnimalFromField(int x, int y) {
        Field f = fields[y][x];
        if(f.numberOfAnimals() == 0) return null;
        int choosenIndexOfAnimal = new Random().nextInt(f.numberOfAnimals());
        return allAnimals.get(f.getAnimals().get(choosenIndexOfAnimal));
    }

    public int getBirthEnergy() {
        return birthEnergy;
    }

    public int getFullEnergy() {
        return fullEnergy;
    }

    public int getNumberOfPlants() {
        return numberOfPlants;
    }

    public int getDay() {
        return day;
    }
    public int getNumberOfAnimalsOnTheMap() {
        return numberOfAnimalsOnMap;
    }
    public int getNumberOfFreeFields() {
        int free = 0;
        for(int y = 0; y < height; y++)
            for(int x = 0; x < width; x++)
                if(!fields[y][x].hasPlant() && fields[y][x].numberOfAnimals() == 0) free++;
        return free;
    }

    public String getMostPopularGenotypeAsString() {
        int[] best = getMostPopularGenotype();
        String s = "[";
        int i;
        for(i = 0; i < best.length - 1; i++) {
            s += best[i] + ", ";
        }
        s += best[i] +  "]";
        return s;
    }

    public int[] getMostPopularGenotype() {
        int max = -1;
        int[] best = {};
        for(Map.Entry<int[],Integer> entry : numberOfGenotypes.entrySet()) {
            if(entry.getValue() > max) best = entry.getKey();
        }
        return best;
    }

    public double averageEnergyForLiving() {
        double sum = 0.;
        int n = 0;
        for(Animal a : allAnimals)
            if(a.isAlive()) {
                n++;
                sum += a.getEnergy();
            }
        int places = 2;
        double scale = Math.pow(10, places);
        return Math.round(sum *  scale / n) / scale;
    }

    public double averageLifeTimeForDeads() {
        double sum = 0.;
        int n = 0;
        for(Animal a : allAnimals)
            if(a.getDeathDay() != -1) {
                n++;
                sum += a.getDeathDay();
            }
        int places = 2;
        double scale = Math.pow(10, places);
        return Math.round(sum *  scale / n) / scale;
    }
}

