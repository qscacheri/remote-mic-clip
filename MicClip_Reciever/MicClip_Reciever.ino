

#include <Servo.h>
#include "BetterButton.h"

#define HC05 Serial1

int downButton = 32;
int upButton = 31;
int state;
Servo servo1;
// twelve servo objects can be created on most boards

int pos = 90;    // variable to store the servo position

void setup() {

  Serial.begin(9600);
  HC05.begin(9600);
  pinMode(upButton, INPUT);
  servo1.attach(33);
}

void loop() {

  if (HC05.available() >= 2) {


    if (!pos + 10 > 160 || !pos - 10 < 30) {
      state = HC05.read();
          Serial.println(state);
      if (state == 49) {
        pos += 10;
        servo1.write(pos);
      }

      else if (state == 50) {
        pos -= 10;
        servo1.write(pos);
      }

       else if (state == 51) {
        pos = 90;
        servo1.write(pos);
      }
//      Serial.println(pos);
    }
  }




}
