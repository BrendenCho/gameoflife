package gameoflife;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class mainlogic extends Thread {

    private nodequeue queue;
    private mainwindow mw;
    private node[][] mainArr;
    private int numRectangles = 20;
    private boolean go = false;
    private boolean breakout = false;

    public void run() {
        draw();
        addListeners();
        mainLoop();

    }

    public mainlogic(mainwindow mw) {
        this.mw = mw;
    }

    public void mainLoop() {

        while (breakout == false) {
            while (go == true) {
                if (mw.getClearBttn().isDisabled() == false) {
                    Platform.runLater(new Runnable() {
                        public void run() {
                            mw.getClearBttn().setDisable(true);
                            mw.getGoBttn().setText("Stop");
                        }

                    });
                }
                queue = new nodequeue();
                enqueueNodes();
                while (queue.isEmpty() == false) {
                    node n = queue.peek();
                    evaluateNode(n);
                    queue.dequeue();
                }
                resetAndColor();

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                }

            }
            if (mw.getClearBttn().isDisabled() == true) {
                Platform.runLater(new Runnable() {
                    public void run() {
                        mw.getClearBttn().setDisable(false);
                        mw.getGoBttn().setText("Go");
                    }

                });
            }
        }

    }

    public void resetAndColor() {

        for (int x = 0; x < numRectangles; x++) {
            for (int y = 0; y < numRectangles; y++) {
                if (mainArr[x][y].getCount() == 3) {
                    mainArr[x][y].setAlive(true);
                }

                if (mainArr[x][y].isConsumed() == true) {
                    mainArr[x][y].setAlive(false);
                }
                mainArr[x][y].setConsumed(false);
                mainArr[x][y].setCount(0);

                Color c;
                if (mainArr[x][y].isAlive() == true) {
                    c = Color.BLACK;
                } else {
                    c = Color.WHITE;
                }
                final int xx = x;
                final int yy = y;
                Platform.runLater(new Runnable() {
                    public void run() {
                        mainArr[xx][yy].getRect().setFill(c);
                    }

                });

            }
        }

    }

    public void evaluateNode(node n) {

        int x = n.getX() - 1;
        int y = n.getY() - 1;
        int c = 0;

        for (int a = x; a < (x + 3); a++) {
            for (int b = y; b < (y + 3); b++) {
                if ((a >= 0 && a < numRectangles) && (b >= 0 && b < numRectangles)
                        && !(a == n.getX() && b == n.getY())) {

                    if (mainArr[a][b].isAlive() == true) {
                        c++;
                    } else {
                        mainArr[a][b].setCount(mainArr[a][b].getCount() + 1);

                    }
                }

            }

        }

        if (c < 2 || c > 3) {
            n.setConsumed(true);
        }

    }

    public void enqueueNodes() {

        for (int x = 0; x < numRectangles; x++) {
            for (int y = 0; y < numRectangles; y++) {
                if (mainArr[x][y].isAlive() == true) {
                    queue.enqueue(mainArr[x][y]);
                }
            }

        }

    }

    public void draw() {
        mainArr = new node[numRectangles][numRectangles];
        int rectWidth = mw.getWidth() / numRectangles;
        int rectHeight = mw.getHeight() / numRectangles;
        int currX = 0;
        int currY = 0;
        for (int x = 0; x < numRectangles; x++) {
            currY = 0;
            for (int y = 0; y < numRectangles; y++) {

                mainArr[x][y] = new node(new Rectangle(currX, currY, rectWidth, rectHeight), false, 0);
                mainArr[x][y].setX(x);
                mainArr[x][y].setY(y);
                mainArr[x][y].getRect().setFill(Color.WHITE);
                mainArr[x][y].getRect().setStroke(Color.BLACK);

                final int xx = x;
                final int yy = y;
                Platform.runLater(new Runnable() {

                    public void run() {
                        mw.getPane().getChildren().add(mainArr[xx][yy].getRect());
                    }

                });

                currY += rectWidth;
            }
            currX += rectHeight;
        }

    }

    public void addListeners() {

        for (int x = 0; x < numRectangles; x++) {
            for (int y = 0; y < numRectangles; y++) {
                final int xx = x;
                final int yy = y;

                mainArr[x][y].getRect().setOnMouseClicked(new EventHandler<MouseEvent>() {

                    @Override
                    public void handle(MouseEvent arg0) {
                        if (mainArr[xx][yy].isConsumed() == false) {
                            flipNode(mainArr[xx][yy]);

                        }
                    }

                });

                EventHandler<MouseEvent> drag = new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent e) {
                        try {
                            PickResult r = e.getPickResult();
                            Rectangle rect = (Rectangle) r.getIntersectedNode();
                            node n = findFromRect(rect);
                            if (n.isConsumed() == false) {
                                flipNode(n);
                            }

                            n.setConsumed(true);
                        } catch (NullPointerException ee) {
                        } catch (ClassCastException eee) {
                        }
                    }
                };

                mainArr[x][y].getRect().setOnMouseDragged(drag);
                mainArr[x][y].getRect().setOnMouseReleased(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent e) {
                        for (int x = 0; x < numRectangles; x++) {
                            for (int y = 0; y < numRectangles; y++) {
                                mainArr[x][y].setConsumed(false);

                            }

                        }

                    }
                });

            }

        }

    }

    public void flipNode(node n) {

        if (n.isAlive() == false) {

            Platform.runLater(new Runnable() {

                public void run() {
                    n.getRect().setFill(Color.BLACK);

                }

            });
            n.setAlive(true);

        } 

    }

    public node findFromRect(Rectangle r) {
        boolean found = false;
        int x = 0;
        int y = 0;

        while (found == false) {

            if (r.equals(mainArr[x][y].getRect())) {
                return mainArr[x][y];
            } else {

                y++;

                if (y >= numRectangles) {
                    x++;
                    y = 0;
                }

            }

        }

        return null;

    }

    public void clear() {
        for (int x = 0; x < numRectangles; x++) {
            for (int y = 0; y < numRectangles; y++) {
                mainArr[x][y].setAlive(false);
                final int xx = x;
                final int yy = y;
                Platform.runLater(new Runnable() {

                    public void run() {
                        mainArr[xx][yy].getRect().setFill(Color.WHITE);

                    }

                });

            }

        }

    }

    public void setGo(boolean go) {
        this.go = go;
    }

    public boolean getGo() {
        return go;
    }

    public void setBreakout(boolean breakout) {
        this.breakout = breakout;
    }
}