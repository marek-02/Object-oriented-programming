package agh.ics.oop;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class GrassField extends AbstractWorldMap {
    private int amountOfGrass;
    private List<Grass> grass;
    private Vector2d upperRightCorner;
    private Vector2d lowerLeftCorner;

    public GrassField(int amountOfGrass) {
        this.amountOfGrass = 0;
        grass = new ArrayList<>();
        animals = new ArrayList<>();
        this.upperRightCorner = new Vector2d(Integer.MIN_VALUE, Integer.MIN_VALUE);
        this.lowerLeftCorner = new Vector2d(Integer.MAX_VALUE, Integer.MAX_VALUE);

        int max = (int) Math.sqrt(amountOfGrass * 10);
        Random generator = new Random();

        while (this.amountOfGrass < amountOfGrass) {
            int x = generator.nextInt(max);
            int y = generator.nextInt(max);
            Vector2d v = new Vector2d(x, y);

            boolean canPlace = true;
            for (Grass g : grass)
                if (v.equals(g.getPosition())) {
                    canPlace = false;
                    break;
                }

            if (canPlace) {
                grass.add(new Grass(v));
                this.amountOfGrass++;
                upperRightCorner = v.upperRight(upperRightCorner);
                lowerLeftCorner = v.lowerLeft(lowerLeftCorner);
            }
        }
    }

    public void deleteGrass(Vector2d position) {
        for(int i = 0; i < grass.size(); i++)
            if(grass.get(i).getPosition().equals(position)) grass.remove(i);
    }

    public void addNewGrass() {
        int max = (int) Math.sqrt(amountOfGrass * 10);
        Random generator = new Random();
        boolean canPlace = false;
        while (!canPlace) {
            canPlace = true;
            int x = generator.nextInt(max);
            int y = generator.nextInt(max);
            Vector2d v = new Vector2d(x, y);

            for (Grass g : grass)
                if (v.equals(g.getPosition())) {
                    canPlace = false;
                    break;
                }
            if (canPlace)
                for (Animal a : animals)
                    if (v.equals(a.getPosition())) {
                        canPlace = false;
                        break;
                    }

            if (canPlace) {
                grass.add(new Grass(v));
                upperRightCorner = v.upperRight(upperRightCorner);
                lowerLeftCorner = v.lowerLeft(lowerLeftCorner);
            }
        }


    }
    @Override
    public boolean canMoveTo(Vector2d position) {
        for (Animal a : animals)
            if (a.getPosition().equals(position)) return false;
        upperRightCorner = position.upperRight(upperRightCorner);
        lowerLeftCorner = position.lowerLeft(lowerLeftCorner);
        return true;
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        for (Animal a : animals)
            if (a.getPosition().equals(position)) return true;
        for (Grass g : grass)
            if (g.getPosition().equals(position)) return true;
        upperRightCorner = position.upperRight(upperRightCorner);
        lowerLeftCorner = position.lowerLeft(lowerLeftCorner);
        return false;
    }

    @Override
    public Object objectAt(Vector2d position) {
        for (Animal a : animals)
            if (a.getPosition().equals(position)) return a;
        for (Grass g : grass)
            if (g.getPosition().equals(position)) return g;
        return null;
    }

    public Vector2d getUpperRightCorner() { return upperRightCorner; }
    public Vector2d getLowerLeftCorner() { return lowerLeftCorner; }
}