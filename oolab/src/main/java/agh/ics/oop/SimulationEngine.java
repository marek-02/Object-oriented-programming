package agh.ics.oop;

import java.util.ArrayList;
import java.util.List;

public class SimulationEngine implements IEngine{
    private final MoveDirection[] moves;
    private IWorldMap map;
    private List<Animal> animals; //it has to have own array because the IWorldMap interface doesn't require a getter for the Animal list

    public SimulationEngine(MoveDirection[] moves, IWorldMap map, Vector2d[] vectors){
        this.moves = moves;
        this.map = map;
        this.animals = new ArrayList<>();
        for(Vector2d v : vectors){
            Animal a = new Animal(map, v);
            if(!map.canMoveTo(v)) animals.add(a);
            map.place(a);
        }
    }

    public void run(){
        int animalsLength = animals.size();
        for(int i = 0; i < moves.length; i++){
            animals.get(i % animalsLength).move(moves[i]);
        }
    }
}
