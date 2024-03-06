package org.example;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;



class Bird {

    private  int size;
    private static final int GRAVITY = 1;
    private static final int JUMP_VELOCITY = -12;
    private static final int WINDOW_HEIGHT = 600;
    private BufferedImage image;
    private String path ="src/main/resources/images/";

    private int x;
    private int y;
    private int velocity;

    public Bird(int x, int y, int size, String path) {
        this.x = x;
        this.y = y;
        this.size = size;
        velocity = 0;
        this.path+=path;
        loadImage();
    }
    private void loadImage() {
        try {
            image = ImageIO.read(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {
        y += velocity;
        velocity += GRAVITY;
    }

    public void jump() {
        velocity = JUMP_VELOCITY;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, size, size);
    }

    public void draw(Graphics g) {
        g.drawImage(image, x, y, size, size, null);
    }

    public boolean isColliding(Pipe pipe) {
        return getBounds().intersects(pipe.getTopBounds()) || getBounds().intersects(pipe.getBottomBounds());
    }
    public boolean isOut() {
        return y < 0 || y > WINDOW_HEIGHT - size;
    }

    public int getX() {
        return x;
    }

}