@echo off
cd src
javac -d "../build" main/Main.java
cd ..
java -cp build main.Main