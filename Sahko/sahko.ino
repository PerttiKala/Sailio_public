
int BTN = 0;
int B = 8;
int A = 12;
int F = 11;
int G = 10;
int DOT = 9;
int REL = 13;
int C = 7;
int D = 6;
int E = 5;
int D1 = 4;
int D2 = 3;
int D3 = 2;
int D4 = 1;


void setup() {

  for (int a= 0; a < 14; a++) {
    pinMode(a, OUTPUT);
  }
  pinMode(BTN, INPUT);
  
  Serial.begin(9600);
}

void ssd(int i, int pos) {
 
 
  if (i == 1) {
    digitalWrite(A, HIGH);
    digitalWrite(F, HIGH);
    digitalWrite(G, HIGH);
    digitalWrite(E, HIGH);
    digitalWrite(D, HIGH);
    digitalWrite(B, LOW);
    digitalWrite(C, LOW);
 
  }
 
  if (i == 2) {
    digitalWrite(A, LOW);
    digitalWrite(F, HIGH);
    digitalWrite(G, LOW);
    digitalWrite(E, LOW);
    digitalWrite(D, LOW);
    digitalWrite(B, LOW);
    digitalWrite(C, HIGH);
 
  }
 
  if (i == 8) {
    digitalWrite(A, LOW);
    digitalWrite(F, LOW);
    digitalWrite(G, LOW);
    digitalWrite(E, LOW);
    digitalWrite(D, LOW);
    digitalWrite(B, LOW);
    digitalWrite(C, LOW);
 
  }
 
if (i == 3) {
    digitalWrite(A, LOW);
    digitalWrite(F, HIGH);
    digitalWrite(G, LOW);
    digitalWrite(E, HIGH);
    digitalWrite(D, LOW);
    digitalWrite(B, LOW);
    digitalWrite(C, LOW);
 
  }
 
if (i == 4) {
    digitalWrite(A, HIGH);
    digitalWrite(F, LOW);
    digitalWrite(G, LOW);
    digitalWrite(E, HIGH);
    digitalWrite(D, HIGH);
    digitalWrite(B, LOW);
    digitalWrite(C, LOW);
 
  }
 
 if (i == 5) {
    digitalWrite(A, LOW);
    digitalWrite(F, LOW);
    digitalWrite(G, LOW);
    digitalWrite(E, HIGH);
    digitalWrite(D, LOW);
    digitalWrite(B, HIGH);
    digitalWrite(C, LOW);
 
  }
 
 if (i == 6) {
    digitalWrite(A, LOW);
    digitalWrite(F, LOW);
    digitalWrite(G, LOW);
    digitalWrite(E, LOW);
    digitalWrite(D, LOW);
    digitalWrite(B, HIGH);
    digitalWrite(C, LOW);
 
  }
 
 if (i == 7) {
    digitalWrite(A, LOW);
    digitalWrite(F, HIGH);
    digitalWrite(G, HIGH);
    digitalWrite(E, HIGH);
    digitalWrite(D, HIGH);
    digitalWrite(B, LOW);
    digitalWrite(C, LOW);
 
  }
 
 if (i == 9) {
    digitalWrite(A, LOW);
    digitalWrite(F, LOW);
    digitalWrite(G, LOW);
    digitalWrite(E, HIGH);
    digitalWrite(D, LOW);
    digitalWrite(B, LOW);
    digitalWrite(C, LOW);
 
  }
 
  if (i == 0) {
    digitalWrite(A, LOW);
    digitalWrite(F, LOW);
    digitalWrite(G, HIGH);
    digitalWrite(E, LOW);
    digitalWrite(D, LOW);
    digitalWrite(B, LOW);
    digitalWrite(C, LOW);
 
  }
 
  if (pos == 2) {
    digitalWrite(DOT, LOW);
  }
 
  else {
    digitalWrite(DOT, HIGH);
  }
 
    digitalWrite(pos, HIGH);
    digitalWrite(pos, LOW); 
}

long i1 = 0;
long i2 = 0;
int i3 = 0;
int i4 = 0;
int flag = 1;
char list[10] = {'0', '0', '0', '0', '0'};
int counter = 0;
char data = 'G';

void loop() {

  
  if(Serial.available() > 0)  {

      Serial.print(data);
      char data = Serial.read();
      list[counter] = data;

      if (counter < 10) {
        counter++;
      }
      
      
      
  }
    

    i1 = list[0] - '0';
    
    i2 = list[1] - '0';
    
    ssd(i1, 4); 
    ssd(i2, 3);
    ssd(i3, 2);
    ssd(i4, 1);

}
