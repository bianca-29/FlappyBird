package org.example;

import java.awt.*;

class Pipe {

    private final int WIDTH = 100;
    private final int HEIGHT = 600;
    private final int GAP_SIZE = 200;
    private int speed = 2;

    private int x;
    private int topY;
    private int bottomY;

    public Pipe(int x, int screenHeight,int speed) {
        this.x = x;
        int gapY = (int) (Math.random() * (screenHeight - GAP_SIZE - HEIGHT)) + HEIGHT / 2;
        topY = gapY - GAP_SIZE / 2;
        bottomY = gapY + GAP_SIZE / 2;
        this.speed=speed;
    }

    public void update() {
        x -= speed;
    }

    public Rectangle getTopBounds() {
        return new Rectangle(x, 0, WIDTH, topY);
    }

    public Rectangle getBottomBounds() {
        return new Rectangle(x, bottomY, WIDTH, getHeight() - bottomY);
    }


    public void draw(Graphics g) {
        g.setColor(new Color(138,43,226));
        g.fillRect(x, 0, WIDTH, topY);
        g.fillRect(x, bottomY, WIDTH, getHeight() - bottomY);
    }

    public boolean isPassed(Bird bird) {
        return bird.getX() > x + WIDTH;
    }
    private int getHeight() {
        return Toolkit.getDefaultToolkit().getScreenSize().height;
    }
}
