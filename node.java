package gameoflife;

import javafx.scene.shape.Rectangle;

public class node {

private Rectangle rect;
private boolean alive;
private int count;     
private boolean consumed = false;
private int x;
private int y;
public node(Rectangle rect,boolean alive,int count){
this.rect = rect;
this.alive = alive;
this.count = count;
}

public void setRect(Rectangle rect){
this.rect = rect;
}

public Rectangle getRect(){
return rect;    
}

public void setAlive(boolean alive){
this.alive = alive;
}

public boolean isAlive(){
return alive;    
}

public void setCount(int count){
this.count = count;    
}

public int getCount(){
return count;    
}

public boolean isConsumed(){
return consumed;    
}

public void setConsumed(boolean consumed){
this.consumed = consumed;    
}

public void setX(int x){
this.x = x;
}

public int getX(){
return x;    
}

public void setY(int y){
this.y = y;    
}

public int getY(){
return y;    
}
}