import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import controlP5.*; 
import processing.serial.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Remote_Clip extends PApplet {



Serial myPort;
ControlP5 cp5;
String hc05 = "/dev/tty.MICCLIPRECIEVER-DevB";
String [] portName; 
PImage mic;
int angle = 90;
float counter = 0.0f;
int maroon = 0xff6F054D;
int active =  0xff920766;
int sliderValue;
boolean connected = false;

PFont f;
ControlFont cf1;
ControlFont cf2;



public void setup() {
  //PImage icon = loadImage("BatteryLogo7.png");

  PImage[] upImg = {loadImage("data/up_arrow.png"), loadImage("data/up_arrow_over.png"), loadImage("data/up_arrow_active.png")};
  PImage[] downImg = {loadImage("data/down_arrow.png"), loadImage("data/down_arrow_over.png"), loadImage("data/down_arrow_active.png")};
  mic = loadImage("data/mic2.png");

  f = createFont("helvetica neue", 30, true);
  cf1 = new ControlFont(createFont("helvetica neue", 20, true));
  cf2 = new ControlFont(createFont("helvetica neue", 10, true));


  textFont(f, 40);

  cp5 = new ControlP5(this);
  portName = Serial.list();
  println(portName);
  //fullScreen();
  
  background(0);


  cp5.addButton("up")
    .setPosition(100, 500)
    .updateDisplayMode(CENTER)
    .setSize(100, 100)
    //.setImages(upImg);
    .setImages(upImg);

  cp5.addButton("down")
    .setSize(100, 100)
    .setPosition(500, 500)
    .updateDisplayMode(CENTER)
    .setImages(downImg);
  ;

  cp5.addButton("Reset")
    .setSize(200, 100)
    .setPosition(250, 500)
    .updateDisplayMode(CENTER)
    .setColorBackground(maroon)
    .setColorForeground(active)
    .setFont(cf1)
    ;

  cp5.addButton("Apply")
    .setSize(50, 25)
    .setPosition(325, 660)
    .updateDisplayMode(CENTER)
    .setColorBackground(maroon)
    .setColorForeground(active)
    .setFont(cf2)
    ;

  cp5.addSlider("Angle")
    .updateDisplayMode(CENTER)
    .setColorBackground(maroon)
    .setSize(200, 25)
    .setMax(160)
    .setMin(30)
    .setLabel("")
    .setPosition(250, 625);
}

public void draw() {
  background(0);

  textAlign(CENTER, CENTER);
  textFont(f, 20);
  text("Status: "+(connected?"connected":"not connected"), 350, 100);
  if (connected==false) {
    try {
      myPort = new Serial(this, hc05, 9600);
      println("connected");
      connected=true;
      myPort.write(253);
      angle = 90;
    }
    catch(Exception e) { 
      connected=false;
      //print(e);
      textAlign(CENTER, CENTER);
      textFont(f, 20);
      //background(0);
    }
  } else {

    pushMatrix();
    translate(width/2, height/2-100);
    rotate(degToRad(angle));
    translate(-mic.width/2, -mic.height/2);
    image(mic, 0, 0);
    popMatrix();
  }
}


public void up(int theValue) {
  if (connected) {


    println("up");
    myPort.write(254);
    if (!(angle-10<0)) {

      angle-=10;
    }
    println(angle);
  }
}

public void down(int theValue) {
  if (connected) {

    println("down");
    myPort.write(255);
    if (!(angle+10>160)) {

      angle+=10;
    }

    println(angle);
  }
}

public void Reset(int theValue) {
  //if (!connected) return;

  angle=90;
  myPort.write(253);
}

public void Angle(int theValue) {

  sliderValue = theValue;
  angle = sliderValue;
}

public void Apply(int theValue) {
  angle = sliderValue;
  myPort.write(sliderValue);
}

public float degToRad(int a) {
  return a*(PI/180.f);
}

  public void settings() {  size(700, 700); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Remote_Clip" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
