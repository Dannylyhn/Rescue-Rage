/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rescuerage.common.data.entityparts;

import rescuerage.common.data.Entity;
import rescuerage.common.data.GameData;
import rescuerage.common.data.GameData;
import static rescuerage.common.data.GameKeys.A;
import static rescuerage.common.data.GameKeys.D;
import static rescuerage.common.data.GameKeys.W;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author ander
 */
public class EnemyMovingPart implements EntityPart {

    private float dx, dy;
    //private float deceleration, acceleration;
    private float maxSpeed;
    //private float rotationSpeed;
    private boolean left, right, up, down, upLeft, upRight, downLeft, downRight;
    
    private int playerTile = 0;
    private int playerX = 0;
    private int playerY = 0;
    private int roomNR = 0;
    //private int tile = 0;
    private int tileX = 0;
    private int tileY = 0;
    public boolean newTile = false;
    private boolean enter = false;
    private int tileSize = 24;
    private Map<String, Entity> roomMap = new ConcurrentHashMap<>();
    
    private boolean changed = false;
    public ArrayList<String> path = new ArrayList();
    
    public void setPlayerTile(int tileNR, int currentRoom, int playerX, int playerY){
        if(currentRoom == roomNR){
            enter = true;
            if(playerTile != tileNR){
                changed = true;
            }
            playerTile = tileNR;
            this.playerX = playerX;
            this.playerY = playerY;
        }
    }

    public EnemyMovingPart(float maxSpeed, int roomNR, Map<String, Entity> room) {
       
        this.maxSpeed = maxSpeed;
        this.roomNR = roomNR;
        this.roomMap = room;
       initMap();
    }

    public float getDx() {
        return dx;
    }

    public float getDy() {
        return dy;
    }

    /*public void setDeceleration(float deceleration) {
        this.deceleration = deceleration;
    }*/

    /*public void setAcceleration(float acceleration) {
        this.acceleration = acceleration;
    }*/

    public void setMaxSpeed(float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public void setSpeed(float speed) {
        //this.acceleration = speed;
        this.maxSpeed = speed;
    }

    /*public void setRotationSpeed(float rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }*/

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setUp(boolean up) {
        this.up = up;
    }
    
    public void setDown(boolean down){
        this.down = down;
    }
    //upLeft, upRight, downLeft, downRight
    public void setUpLeft(boolean upLeft){
        this.upLeft = upLeft;
    }
    public void setUpRight(boolean upRight){
        this.upRight = upRight;
    }
    public void setDownLeft(boolean downLeft){
        this.downLeft = downLeft;
    }
    public void setDownRight(boolean downRight){
        this.downRight = downRight;
    }
    private int counter = 0;
    @Override
    public void process(GameData gameData, Entity entity) {
        if(enter){
            if(counter>0){
                counter = counter -1;
                return;
            }
            PositionPart positionPart = entity.getPart(PositionPart.class);
            float x = positionPart.getX();
            float y = positionPart.getY();
            float size = entity.getSizeX();
            if(changed){
                //System.out.println("In new tile: " + playerTile);
                changed = false;
                //ArrayList<String> path = new ArrayList();
                Node astar = aStar((int)x, (int)y, (int)playerX, (int)playerY, (int)size);
                path.clear();
                for(Node n : astar.path()){
                    if(n!=null){
                        path.add(n.action);
                    }
                }
                Collections.reverse(path);
                path.remove(null);
                //System.out.println("Path: " + path);
            }
            float radians = positionPart.getRadians();
            float dt = gameData.getDelta();
            /*tile = (int)x*tileSize;
            tile = tile * ((int)y/tileSize);*/
            //int tempTile = ((int)x/tileSize) * ((int)y/tileSize);
            int tempTileX = ((int)x/tileSize);
            int tempTileY = ((int)y/tileSize);
            if(tileX != tempTileX){
                newTile = true;
                tileX = tempTileX;
                //System.out.println(tile);
                // update new movement
            }
            if(tileY != tempTileY){
                newTile = true;
                tileY = tempTileY;
                //System.out.println(tile);
                // update new movement
            }

            // turning
            if (left) {
                //dx = -maxSpeed;
                x -= maxSpeed*dt;
            }

            if (right) {
                //dx = maxSpeed;
                x += maxSpeed*dt;
            }

            // accelerating            
            if (up) {
                //dy =  maxSpeed;
                y += maxSpeed*dt;
            }

            if (down){
                //dy = -maxSpeed;
                y -= maxSpeed*dt;
            }
            if (upLeft){
                //dy =  maxSpeed;
                y += maxSpeed*dt;
                //dx = -maxSpeed;
                x -= maxSpeed*dt;
            }
            if (upRight){
                //dy =  maxSpeed;
                y += maxSpeed*dt;
                //dx = maxSpeed;
                x += maxSpeed*dt;
            }
            if (downLeft){
                //dy = -maxSpeed;
                y -= maxSpeed*dt;
                //dx = -maxSpeed;
                x -= maxSpeed*dt;
            }
            if (downRight){
                //dy = -maxSpeed;
                y -= maxSpeed*dt;
                //dx = maxSpeed;
                x += maxSpeed*dt;
            }
            /*
            // set position
            if(x!=0){
                x += dx * dt;
                if (x > gameData.getDisplayWidth()) {
                    x = 0;
                } else if (x < 0) {
                    x = gameData.getDisplayWidth();
                }

                y += dy * dt;
                if (y > gameData.getDisplayHeight()) {
                    y = 0;
                } else if (y < 0) {
                    y = gameData.getDisplayHeight();
                }

                positionPart.setX(x);
                positionPart.setY(y);
            }
            */
            positionPart.setX(x);
            positionPart.setY(y);
            positionPart.setRadians(radians);
        }
    }
    
    private Node aStar(int startX, int startY, int goalX, int goalY, int size){
        ArrayList<Node> fringe = new ArrayList();
        Node initNode = new Node(startX, startY, goalX, goalY, size);
        fringe.add(initNode);
        while(!fringe.isEmpty()){
            Node node = fringe.get(0);
            fringe.remove(0);
            int temp = node.heuristic();
            if(node.heuristic()<=tileSize){
                return node;
            }
            if(fringe.size() > 100){
                return node;
            }
            ArrayList<Node> children = expand(node);
            for(Node n : children){
                fringe.add(0, n);
            }
            for(int i = 0; i < fringe.size(); i++){
                for(int j = 0; j < fringe.size(); j++){
                    if(j+1<fringe.size()){
                        if(fringe.get(j).heuristic()*5+fringe.get(j).depth > fringe.get(j+1).heuristic()*5+fringe.get(j+1).depth){
                            Collections.swap(fringe, j, j+1);
                        }
                    }
                }
            }
        }
        return null;
    }
    private class Node{
        int x;
        int y;
        int goalX;
        int goalY;
        Node parentNode;
        int depth = 0;
        String action;
        float[] shapeX = new float[4];
        float[] shapeY = new float[4];
        float sizeX;
        float sizeY;
        Node(int x, int y, int goalX, int goalY, int size){
            this.x = x;
            this.y = y;
            this.depth = 0;
            this.parentNode = null;
            this.goalX = goalX;
            this.goalY = goalY;
            this.sizeX = 24+12;
            this.sizeY = 24+12;
            setShape();
        }
        private void setShape() {
        
            float[] shapex = new float[4];
            float[] shapey = new float[4];
            //PositionPart positionPart = entity.getPart(PositionPart.class);
            //float x = positionPart.getX();
            //float y = positionPart.getY();
            //float radius = entity.getRadius();
            //float sizeX = this.sizeX;
            //float sizeY = this.sizeY;

            shapex[0] = x + sizeX;
            shapey[0] = y + sizeY;

            shapex[1] = x + sizeX;
            shapey[1] = y - sizeY;

            shapex[2] = x - sizeX;
            shapey[2] = y - sizeY;

            shapex[3] = x - sizeX;
            shapey[3] = y + sizeY;

            this.shapeX = shapex;
            this.shapeY = shapey;

        }
        public ArrayList<Node> path(){
            Node current = this;
            ArrayList path = new ArrayList();
            path.add(current);
            if(current == null){
                System.out.println("\n\n\n\n\nhmmmm\n\n\n\n\n");
            }
            
            while(current != null){
                current = current.parentNode;
                path.add(current);
            }
            return path;
        }
        public int heuristic(){
            int retX;
            if(x>goalX){
                retX = x-goalX;
            }
            else{
                retX = goalX-x;
            }
            
            int retY;
            if(y>goalY){
                retY = y-goalY;
            }
            else{
                retY = goalY-y;
            }
            
            if(retX>retY){
                return retX;
            }
            else{
                return retY;
            }
        }
        public int getX(){
            return this.x;
        }
        public int getY(){
            return this.y;
        }
        public void actuators(int action){
            int nx = 0;
            int ny = 0;
            switch (action) { 
                case 5:
                    nx = tileSize;
                    //this.x = x + tileSize;
                case 1:
                    ny = tileSize;
                    this.y = y + tileSize;
                    break;
                case 8:
                    nx = -tileSize;
                case 2:
                    ny = -tileSize;
                    this.y = y - tileSize;
                    break;
                case 6:
                    ny = tileSize;
                case 3:
                    nx = -tileSize;
                    this.x = x - tileSize;
                    break;
                case 7:
                    ny = -tileSize;
                case 4:
                    nx = tileSize;
                    this.x = x + tileSize;
                    break;
                default:
                    break;
            }
            
            setShape();
            for(Entity e : roomMap.values()){
                if(e.getClass().getSimpleName().equals("Map")){
                    TilePart tp = e.getPart(TilePart.class);
                    if(tp.type.equals("wall")){
                        boolean ret = isCollision(e);
                        if(ret){
                            this.x = x + 10000;
                            this.y = y + 10000;
                            this.depth = depth + 1000;
                            break;
                        }
                    }
                }
            }
            //setShape();
            //this.x = x + nx;
            //this.y = y + ny;
        }
        private boolean isCollision(Entity entity) {
            float[] sx = shapeX;
            float[] sy = shapeY;
            for(int i = 0; i < sx.length; i++){
                if(entity.contains(sx[i], sy[i])){
                    return true;
                }
            }
            return false;
        }
    }
    private ArrayList<Node> expand(Node node){
        ArrayList<Node> successors = new ArrayList();
        ArrayList<Integer> children = successor_fn(node.getX(), node.getY());
        //
        for(int child : children){
            Node n = new Node(node.getX(), node.getY(), node.goalX, node.goalY, (int)node.sizeX);
            n.actuators(child);
            n.action = moves.get(child);
            //System.out.println("move: " + moves.get(child));
            n.parentNode = node;
            n.depth = node.depth + tileSize;
            successors.add(n);
        }
        return successors;
    }
    /*private Map<String, Integer> moves = new HashMap<String, Integer>();
    private void initMap(){
        moves.put("up",1);
        moves.put("down",2);
        moves.put("left",3);
        moves.put("right",4);
        moves.put("upright",5);
        moves.put("upleft",6);
        moves.put("downright",7);
        moves.put("downleft",8);
    }*/
    private Map<Integer, String> moves = new HashMap<Integer, String>();
    private void initMap(){
        moves.put(1, "up");
        moves.put(2, "down");
        moves.put(3, "left");
        moves.put(4, "right");
        moves.put(5, "upright");
        moves.put(6, "upleft");
        moves.put(7, "downright");
        moves.put(8, "downleft");
    }
    //private ArrayList<ArrayList<Integer>> successor_fn(int x, int y){
    private ArrayList<Integer> successor_fn(int x, int y){
        //ArrayList<ArrayList<Integer>> stateList = new ArrayList();
        ArrayList<Integer> stateList = new ArrayList();
        //int currentState = state;
        
        //for(String rule : moves.values()){
        //for(int i = 1; i < 9; i++){
        for(int i = 1; i < 5; i++){
            /*
            int nx = 0;
            int ny = 0;
            switch (rule) { 
                case 5:
                    nx = tileSize;
                case 1:
                    ny = tileSize;
                    break;
                case 8:
                    nx = -tileSize;
                case 2:
                    ny = -tileSize;
                    break;
                case 6:
                    ny = tileSize;
                case 3:
                    nx = -tileSize;
                    break;
                case 7:
                    ny = -tileSize;
                case 4:
                    nx = tileSize;
                    break;
                default:
                    break;
            }
            */
            //stateList.add(new ArrayList(Arrays.asList(rule,x+nx,y+ny)));
            stateList.add(i);
        }
        return stateList;
    }
}
