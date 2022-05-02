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
    private int tile = 0;
    public boolean newTile = false;
    private boolean enter = false;
    
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

    public EnemyMovingPart(float maxSpeed, int roomNR) {
       
        this.maxSpeed = maxSpeed;
        this.roomNR = roomNR;
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

    @Override
    public void process(GameData gameData, Entity entity) {
        if(enter){
            PositionPart positionPart = entity.getPart(PositionPart.class);
            float x = positionPart.getX();
            float y = positionPart.getY();
            if(changed){
                System.out.println("In new tile: " + playerTile);
                changed = false;
                //ArrayList<String> path = new ArrayList();
                Node astar = aStar((int)x, (int)y, (int)playerX, (int)playerY);
                path.clear();
                for(Node n : astar.path()){
                    if(n!=null){
                        path.add(n.action);
                    }
                }
            }
            float radians = positionPart.getRadians();
            float dt = gameData.getDelta();
            /*tile = (int)x*16;
            tile = tile * ((int)y/16);*/
            int tempTile = ((int)x/16) * ((int)y/16);
            if(tile != tempTile){
                newTile = true;
                tile = tempTile;
                System.out.println(tile);
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
            positionPart.setRadians(radians);
        }
    }
    
    private Node aStar(int startX, int startY, int goalX, int goalY){
        ArrayList<Node> fringe = new ArrayList();
        Node initNode = new Node(startX, startY, goalX, goalY);
        fringe.add(initNode);
        while(!fringe.isEmpty()){
            //System.out.println("while 225");
            System.out.print("List: ");
            for(Node n : fringe){
                System.out.print((n.heuristic()) + ", ");
            }
            System.out.println("");
            Node node = fringe.get(0);
            fringe.remove(0);
            int temp = node.heuristic();
            System.out.println("heuristic + path: " + temp );
            
            //if(node.heuristic()<16){
            if(node.heuristic()<16){
                //return node.path();
                return node;
            }
            ArrayList<Node> children = expand(node);
            for(Node n : children){
                //System.out.println("for 234");
                //fringe.add(n);
                fringe.add(0, n);
                //System.out.println(n.heuristic());
            }
            //System.out.println(fringe);
            for(int i = 0; i < fringe.size(); i++){
                //System.out.println("for 238");
                for(int j = 0; j < fringe.size(); j++){
                    //System.out.println("for 240");
                    if(j+1<fringe.size()){
                        /*
                        int path1 = 0;
                        for(Node n : fringe.get(j).path()){
                            if(n!=null){
                                //path1 = path1 + n.depth;
                                path1 = path1 + 16;
                            }
                        }
                        
                        int path2 = 0;
                        for(Node n : fringe.get(j+1).path()){
                            if(n!=null){
                                //path2 = path2 + n.depth;
                                path2 = path2 + 16;
                            }
                        }
                        */
                        if(fringe.get(j).heuristic()+fringe.get(j).depth > fringe.get(j+1).heuristic()+fringe.get(j+1).depth){
                        //if(fringe.get(j).heuristic() > fringe.get(j+1).heuristic()){
                            /*
                            Node temp1 = fringe.get(i);
                            Node temp2 = fringe.get(j+1);
                            
                            fringe.set(i, temp2);
                            fringe.set(j+1, temp1);
                            */
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
        Node(int x, int y, int goalX, int goalY){
            this.x = x;
            this.y = y;
            this.depth = 0;
            this.parentNode = null;
            this.goalX = goalX;
            this.goalY = goalY;
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
                    nx = 16;
                    //this.x = x + 16;
                case 1:
                    ny = 16;
                    this.y = y + 16;
                    break;
                case 8:
                    nx = -16;
                case 2:
                    ny = -16;
                    this.y = y - 16;
                    break;
                case 6:
                    ny = 16;
                case 3:
                    nx = -16;
                    this.x = x - 16;
                    break;
                case 7:
                    ny = -16;
                case 4:
                    nx = 16;
                    this.x = x + 16;
                    break;
                default:
                    break;
            }
            //this.x = x + nx;
            //this.y = y + ny;
        }
    }
    private ArrayList<Node> expand(Node node){
        ArrayList<Node> successors = new ArrayList();
        ArrayList<Integer> children = successor_fn(node.getX(), node.getY());
        //
        for(int child : children){
            Node n = new Node(node.getX(), node.getY(), node.goalX, node.goalY);
            n.actuators(child);
            n.action = moves.get(child);
            System.out.println("move: " + moves.get(child));
            n.parentNode = node;
            n.depth = node.depth + 1;
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
                    nx = 16;
                case 1:
                    ny = 16;
                    break;
                case 8:
                    nx = -16;
                case 2:
                    ny = -16;
                    break;
                case 6:
                    ny = 16;
                case 3:
                    nx = -16;
                    break;
                case 7:
                    ny = -16;
                case 4:
                    nx = 16;
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
