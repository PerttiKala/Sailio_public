This folder contains source code for a little Arduino project I made on freetime.

Python file was ran by Raspberry pi. It makes an API call to get price of the electricity
and sends it to Arduino that is connected to it. Arduino is  running its own application
that waits for this data to arrive from the Raspberry, and then displays it on a 7 segment display. 
Code on Raspberry was ran automaticly at its startup, so that price of the electricity could be
gotten just by turning on the powersupply of the Raspberry.
