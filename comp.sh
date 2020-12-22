#! /bin/bash

#Copy over manifest, libs, and other resources.
rm -rf ./build/*
cp ./manifest.txt ./build
cp ./libs/commons-cli-1.4.jar ./build
cp ./libs/commons-lang3.jar ./build
cp ./libs/commons-text-1.9.jar ./build

#Compile .java(s), link JARs. (Note: you also need to specify these in the manifest.)
javac -d ./build -cp ":./libs/commons-cli-1.4.jar:./libs/commons-lang3.jar:./libs/commons-text-1.9.jar" *.java

#JAR all of the classes and JAR libs in ./build.
cd ./build
jar cfm RegexTool.jar manifest.txt MainCL.class commons-cli-1.4.jar commons-lang3.jar commons-text-1.9.jar
java -jar ./RegexTool.jar -s "this is\n a new line" -r ".*"
