package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Scanner;

public class FlappyBird extends JFrame implements ActionListener {

    private final int WINDOW_WIDTH = 600;
    private final int WINDOW_HEIGHT = 600;
    private String player = "bird.png";
    private int size = 50;
    private int speed = 2;
    private boolean start = false;

    private Bird bird;
    private ArrayList<Pipe> pipes = new ArrayList<>();
    private ArrayList<Pipe> pipesRemove = new ArrayList<>();
    private Timer timer;
    private Timer pipeTimer;
    private int score;
    private boolean start2 = false;
    private int pipeSpeed = 2000;
    private Boolean custom = false;
    Color color = Color.BLACK;

    private GamePanel gamePanel;

    public FlappyBird() {
        System.out.println("Do you want to play the default or customizable game?");
        System.out.println("Write custom for customizable, any other key for default");
        Scanner scan = new Scanner(System.in);
        String c = scan.nextLine();
        if(c.equals("custom")){
            custom = true;
            System.out.println("Choose your character: bird, dog, cat");
            c=scan.nextLine();
            if(c.equals("cat"))
                player = "cat2.png";
            else if(c.equals("dog"))
                player = "dog.png";
            System.out.println("Choose the size of your character: small, medium, big");
            c=scan.nextLine();
            if(c.equals("small"))
                size = 30;
            else if(c.equals("big"))
                size = 70;
            System.out.println("Choose the difficulty of your game: easy, medium, hard");
            c=scan.nextLine();
            if(c.equals("medium")){
                speed = 3;
                pipeSpeed = 1550;
            }

            else if(c.equals("hard")){
                speed = 4;
                pipeSpeed = 1050;
            }
            System.out.println("Choose the color of the background(RGB):");
            System.out.println("Red:");
            int red = scan.nextInt();
            System.out.println("Green:");
            int green = scan.nextInt();
            System.out.println("Blue:");
            int blue = scan.nextInt();
            color = new Color(red, green, blue);


        }
        setTitle("Flappy Bird");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        bird = new Bird(WINDOW_WIDTH / 5, WINDOW_HEIGHT / 2,size,player);
        pipes.add(new Pipe(WINDOW_WIDTH, WINDOW_HEIGHT,speed));
        score = 0;

        addKeyListener(new BirdKeyListener());

        timer = new Timer(10, this);
        pipeTimer = new Timer(pipeSpeed, new CreatePipesListener());
        timer.start();


        gamePanel = new GamePanel();
        add(gamePanel);
        setVisible(true);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (start){
            if(!start2){
                pipeTimer.start();
                start2 = true;
            }
            bird.update();
            for (Pipe pipe : pipes) {
                pipe.update();

                if (bird.isColliding(pipe) || bird.isOut()) {
                    gameOver();
                }

                if (pipe.isPassed(bird)) {
                    score++;
                    if(score > 5 && !custom){
                        if(score > 10){
                            speed = 4;
                            pipeSpeed = 1050;
                            color = new Color(139,0,0);
                        }
                        else{
                            speed = 3;
                            pipeSpeed = 1550;
                            color = new Color(0,0,139);

                        }
                    }

                    pipesRemove.add(pipe);

                }
            }
        }
        pipes.removeAll(pipesRemove);
        pipesRemove.clear();
        gamePanel.repaint();
    }

    private void gameOver() {
        timer.stop();
        pipeTimer.stop();
        JOptionPane.showMessageDialog(this, "Game Over! Your score is: " + score);
        dispose();
    }

    private class BirdKeyListener extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                start = true;
                bird.jump();
            }
        }
    }

    private class GamePanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            g.setColor(color);
            g.fillRect(0, 0, getWidth(), getHeight());
            for (Pipe pipe : pipes){
                pipe.draw(g);
            }

            bird.draw(g);

            g.setColor(Color.PINK);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString("Score: " + score, 50, 50);
        }
    }
    private void createPipes() {
        pipes.add(new Pipe(WINDOW_WIDTH, WINDOW_HEIGHT, speed));
    }

    private class CreatePipesListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            createPipes();
        }
    }

    public static void main(String[] args) {
        new FlappyBird();
    }

}
