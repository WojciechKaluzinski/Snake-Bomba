package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Random;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;



public class Gameplay extends JPanel implements KeyListener, ActionListener {


    private int[] snakeXLength = new int[750];
    private int[] snakeYLength = new int[750];

    private boolean left = false;
    private boolean right = false;
    private boolean up = false;
    private boolean down = false;
    private boolean czarnaDziura = false;
    private ImageIcon rightbomba, leftbomba, upbomba, downbomba, startbomba;
    private ImageIcon kosmita;
    private Random random = new Random();
    private Random randomEnemy = new Random();
    private int lengthofsnake = 3;
    private Timer timer;
    private ImageIcon bodyhotdog;

    private int [] enemyXpos = {0,50,100,150,200,250,300,350,400,450,500,550,600,650,
    700,750,800};
    private int [] enemyYpos = {100,150,200,250,300,350,400,450,500,550};

    private ImageIcon[] kosmici = {new ImageIcon("C'Qurwozaur_1.png"),new ImageIcon("C'Qurwozaur_2.png"),
            new ImageIcon("chujew_1.png"),new ImageIcon("kurwinoks_4.png"),new ImageIcon("dodupyzaur.png"),
            new ImageIcon("mkbewe.png"),new ImageIcon("chujew_2.png"),new ImageIcon("skurwol_1.png"),new ImageIcon("pedator.png"),
            new ImageIcon("kurwinoks_6.png"),new ImageIcon("kurwinoks_1.png"),new ImageIcon("kurwinoks_5.png"),new ImageIcon("sułtan_kosmitów.png"),
            new ImageIcon("skurwol_2.png"),new ImageIcon("kurwinoks_3.png")};
    //private ImageIcon[] kosmici = {new ImageIcon("kurwinoks_4.png"),new ImageIcon("mkbewe.png"),new ImageIcon("kurwinoks_5.png")};
    //private File [] voices  = {new File("kurwinoks_4.wav"),new File("diabeł.wav"),new File("kurwinoks_5.wav")};

    private int xPos = random.nextInt(17);
    private int yPos = random.nextInt(10);
    private int enemy = random.nextInt(15);

    private ImageIcon titleImage,background1,background2, gameOver;
    private int delay = 150;
    private int moves = 0;
    private int score = 0;
    Clip clip;

    public Gameplay(){

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
        new Thread(() -> {
            try {
                File f = new File("song_3.wav");
                AudioInputStream ais = AudioSystem.getAudioInputStream(f);
                clip = AudioSystem.getClip();
                clip.open(ais);
            } catch (Exception e) {
                e.printStackTrace();
            }
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }).start();
    }

    public void paint (Graphics graphics){

        if(moves == 0){
            snakeXLength[2] = 50;
            snakeXLength[1] = 100;
            snakeXLength[0] = 150;

            snakeYLength[2] = 150;
            snakeYLength[1] = 150;
            snakeYLength[0] = 150;

        }

        //granice tytułu
        graphics.setColor(Color.white);
        graphics.drawRect(0,10,850,80);
        //tytuł
        titleImage = new ImageIcon("tytuł.png");
        titleImage.paintIcon(this, graphics, 1, 11);
        //granice ramki
        graphics.setColor(Color.white);
        graphics.drawRect(0,100,850,577);
        //tło dla gameplay
        graphics.setColor(Color.lightGray);
        graphics.fillRect(0,100,850,600);
        background1 = new ImageIcon("tło.png");
        background2 = new ImageIcon("tło_cd.png");
        if(!czarnaDziura) background1.paintIcon(this, graphics,0,100);
        else background2.paintIcon(this, graphics,0,100);
        startbomba = new ImageIcon("bomba_prawo.png");
        startbomba.paintIcon(this, graphics, snakeXLength[0], snakeYLength[0]);
        //rysowanie wyniku
        graphics.setColor(Color.white);
        graphics.setFont(new Font("arial", Font.PLAIN, 14));
        graphics.drawString("WYNIK: " + score, 700, 30);
        //rysowanie długości
        graphics.setColor(Color.white);
        graphics.setFont(new Font("arial", Font.PLAIN, 14));
        graphics.drawString("DŁUGOŚĆ: " + lengthofsnake, 700, 50);
        //info o czarnej dziurze
        graphics.setColor(Color.RED);
        graphics.setFont(new Font("arial", Font.PLAIN, 14));
        if(czarnaDziura)  graphics.drawString("CZARNA DZIURA", 700, 70);



        for (int a = 0; a < lengthofsnake; a++){

            if(a==0 && right && !czarnaDziura){
                rightbomba = new ImageIcon("bomba_prawo.png");
                rightbomba.paintIcon(this, graphics, snakeXLength[a], snakeYLength[a]);
            }
            if(a==0 && left && !czarnaDziura){
                leftbomba = new ImageIcon("bomba_lewo.png");
                leftbomba.paintIcon(this, graphics, snakeXLength[a], snakeYLength[a]);
            }
            if(a==0 && up && !czarnaDziura){
                upbomba = new ImageIcon("bomba_góra.png");
                upbomba.paintIcon(this, graphics, snakeXLength[a], snakeYLength[a]);
            }
            if(a==0 && down && !czarnaDziura){
                downbomba = new ImageIcon("bomba_dół.png");
                downbomba.paintIcon(this, graphics, snakeXLength[a], snakeYLength[a]);
            }
            if(a==0 && right && czarnaDziura){
                rightbomba = new ImageIcon("bomba_cd_prawo.png");
                rightbomba.paintIcon(this, graphics, snakeXLength[a], snakeYLength[a]);
            }
            if(a==0 && left && czarnaDziura){
                leftbomba = new ImageIcon("bomba_cd_lewo.png");
                leftbomba.paintIcon(this, graphics, snakeXLength[a], snakeYLength[a]);
            }
            if(a==0 && up && czarnaDziura){
                upbomba = new ImageIcon("bomba_cd_góra.png");
                upbomba.paintIcon(this, graphics, snakeXLength[a], snakeYLength[a]);
            }
            if(a==0 && down && czarnaDziura){
                downbomba = new ImageIcon("bomba_cd_dół.png");
                downbomba.paintIcon(this, graphics, snakeXLength[a], snakeYLength[a]);
            }
            if(a != 0 && !czarnaDziura){
                bodyhotdog = new ImageIcon("hot-dog.png");
                bodyhotdog.paintIcon(this, graphics, snakeXLength[a], snakeYLength[a]);
            }
            if(a != 0 && czarnaDziura){
                bodyhotdog = new ImageIcon("gówno.png");
                bodyhotdog.paintIcon(this, graphics, snakeXLength[a], snakeYLength[a]);
            }
        }


        if((enemyXpos[xPos] == snakeXLength[0]) && (enemyYpos[yPos] == snakeYLength[0])){
            lengthofsnake++;
            score++;

           /* if(lengthofsnake % 7 == 0) {
               try {
                    File f = new File("tępe_ch.wav");
                    AudioInputStream ais = AudioSystem.getAudioInputStream(f);
                    clip = AudioSystem.getClip();
                    clip.open(ais);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                clip.start();
            }*/
            xPos = random.nextInt(17);
            yPos = random.nextInt(10);
            enemy = randomEnemy.nextInt(15);
           /* try {
                File v = voices[enemy];
                AudioInputStream aisv = AudioSystem.getAudioInputStream(v);
                clip = AudioSystem.getClip();
                clip.open(aisv);
            } catch (Exception e) {
                e.printStackTrace();
            }
            clip.start();*/
        }

        kosmita = kosmici[enemy];
        kosmita.paintIcon(this, graphics, enemyXpos[xPos], enemyYpos[yPos]);

        for (int b = 1; b<lengthofsnake; b++) {
            if (snakeXLength[b] == enemyXpos[xPos] && snakeYLength[b] == enemyYpos[yPos]) {
                lengthofsnake = lengthofsnake - 2;
               // score--;
            }
        }

        for (int b = 1; b<lengthofsnake; b++){
            if((snakeXLength[b] == snakeXLength[0] && snakeYLength[b] == snakeYLength[0]) || lengthofsnake < 3){
                right = false;
                left = false;
                up = false;
                down = false;
                czarnaDziura =false;


                gameOver = new ImageIcon("game_over.png");
                gameOver.paintIcon(this, graphics, 136, 108);
                try {
                    File f = new File("game_over.wav");
                    AudioInputStream ais = AudioSystem.getAudioInputStream(f);
                    clip = AudioSystem.getClip();
                    clip.open(ais);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                clip.start();

                graphics.setColor(Color.white);
                graphics.setFont(new Font("arial", Font.BOLD, 20));
                graphics.drawString("RESTERT (spacja)", 320, 600);
            }
        }
        graphics.dispose();



    }


    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        if(right){
            for (int r = lengthofsnake-1; r>=0; r--) snakeYLength[r+1] = snakeYLength[r];
            for (int r = lengthofsnake; r>=0; r--){
                if(r==0) snakeXLength[r] = snakeXLength[r] + 50;
                else snakeXLength[r] = snakeXLength[r-1];
                if(snakeXLength[r] > 800){
                    snakeXLength[r] = 0;
                    czarnaDziura = !czarnaDziura;
                    //++fizyka czarnej dziury
                }
            }
            repaint();
        }
        if(left){
            for (int r = lengthofsnake-1; r>=0; r--) snakeYLength[r+1] = snakeYLength[r];
            for (int r = lengthofsnake; r>=0; r--){
                if(r==0) snakeXLength[r] = snakeXLength[r] - 50;
                else snakeXLength[r] = snakeXLength[r-1];
                if(snakeXLength[r] < 0){
                    snakeXLength[r] = 800;
                    czarnaDziura = !czarnaDziura;
                    //++fizyka czarnej dziury
                }
            }
            repaint();
        }
        if(up){
            for (int r = lengthofsnake-1; r>=0; r--) snakeXLength[r+1] = snakeXLength[r];
            for (int r = lengthofsnake; r>=0; r--){
                if(r==0) snakeYLength[r] = snakeYLength[r] - 50;
                else snakeYLength[r] = snakeYLength[r-1];
                if(snakeYLength[r] < 100){
                    snakeYLength[r] = 600;
                    czarnaDziura = !czarnaDziura;
                    //++fizyka czarnej dziury
                }
            }
            repaint();
        }
        if(down){
            for (int r = lengthofsnake-1; r>=0; r--) snakeXLength[r+1] = snakeXLength[r];
            for (int r = lengthofsnake; r>=0; r--){
                if(r==0) snakeYLength[r] = snakeYLength[r] + 50;
                else snakeYLength[r] = snakeYLength[r-1];
                if(snakeYLength[r] > 600){
                    snakeYLength[r] = 100;
                    czarnaDziura = !czarnaDziura;
                    //++fizyka czarnej dziury
                }
            }
            repaint();
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            moves = 0;
            score = 0;
            lengthofsnake = 3;
            repaint();
        }
        if(e.getKeyCode() == KeyEvent.VK_P){
            timer.stop();
        }
        if(e.getKeyCode() == KeyEvent.VK_S){
            timer.start();
        }

        if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            moves++;
            right = true;
            if(!left) right = true;
            else {
                right = false;
                left = true;
            }

            up = false;
            down = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT){
            moves++;
            left = true;
            if(!right) left= true;
            else {
                left = false;
                right= true;
            }

            up = false;
            down = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_UP){
            moves++;
            up = true;
            if(!down) up= true;
            else {
                up= false;
                down= true;
            }

            left = false;
            right = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_DOWN){
            moves++;
            down = true;
            if(!up) down= true;
            else {
                down= false;
                up= true;
            }

            left = false;
            right = false;
        }


    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

}
