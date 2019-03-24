import controlP5.*;
import processing.serial.*;
Serial myPort;
ControlP5 cp5;
String hc05 = "/dev/tty.DSDTECHHC-05-DevB";
String [] portName; 
PImage mic;
int angle = 90;
float counter = 0.0;
int maroon = #6F054D;
int active =  #920766;
boolean connected = false;

PFont f;
ControlFont cf1;


void setup() {

  PImage[] upImg = {loadImage("data/up_arrow.png"), loadImage("data/up_arrow_over.png"), loadImage("data/up_arrow_active.png")};
  PImage[] downImg = {loadImage("data/down_arrow.png"), loadImage("data/down_arrow_over.png"), loadImage("data/down_arrow_active.png")};
  mic = loadImage("data/mic2.png");

  f = createFont("helvetica neue", 30, true);
  cf1 = new ControlFont(createFont("helvetica neue", 20, true));


  textFont(f, 40);

  cp5 = new ControlP5(this);
  portName = Serial.list();
  println(portName);
  //fullScreen();
  size(700, 700);
  background(0);


  cp5.addButton("up")
    .setPosition(100, 500)
    .updateDisplayMode(CENTER)
    .setSize(200, 200)
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
}

void draw() {
  background(0);

  textAlign(CENTER, CENTER);
  textFont(f, 20);
  text("Status: "+(connected?"connected":"not connected"), 350, 100);
  if (connected==false) {
    try {
      myPort = new Serial(this, hc05, 9600);
      println("connected");
      connected=true;
      myPort.write(51);
      myPort.write(13);
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


void up(int theValue) {
  if (!connected) return;
  println("up");
  myPort.write(50);
  myPort.write(13);
  //if (angle+10>180) return;

  angle-=10;

  println(angle);
}

void down(int theValue) {
  if (!connected) return;

  println("down");
  myPort.write(49);
  myPort.write(13);
  //if (angle-10<0) return;
  angle+=10;

  println(angle);
}

void Reset(int theValue) {
  //if (!connected) return;

  angle=90;
  myPort.write(51);
  myPort.write(13);
}


float degToRad(int a) {
  return a*(PI/180.);
}
