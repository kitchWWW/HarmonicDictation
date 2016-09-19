#!/bin/bash
echo "starting Java compile..."
javac runner.java
echo "starting Java run..."
java runner
cd output/
echo "starting Lilypond compile..."
/Applications/Lilypond.app/Contents/Resources/bin/lilypond output.ly
convert output.pdf output.png
sips -s format png output.pdf --out output.png
open output.png
open output.midi
cd ..