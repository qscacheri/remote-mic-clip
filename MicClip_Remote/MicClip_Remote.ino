

#include "BetterButton.h"

#define HC05 Serial1

int downButtonPin = 2;
int upButtonPin = 0;
BetterButton upButton(upButtonPin, 2);
BetterButton downButton(downButtonPin, 1);


void setup() {

  Serial.begin(9600);
  HC05.begin(9600);
  upButton.pressHandler(onPress);
  upButton.releaseHandler(onRelease);
  downButton.pressHandler(onPress);
  downButton.releaseHandler(down);

}

void loop() {
  upButton.process();
  downButton.process();

}

void onPress(int b) {

}

void onRelease(int b) {
  HC05.println(byte(1));

}

void down(int b) {
  HC05.println(byte(2));

}
