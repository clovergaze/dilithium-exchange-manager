# Dilithium Exchange Manager
A manager for the Dilithium Exchange in [Star Trek Online](http://startrekonline.com).

## Installation
To use the program you can download or clone this repository and build it or you can download a prebuilt binary version from [here](http://www.infokin.org/index.php?id=6).

## Start
If you downloaded a .ZIP file extract it and navigate to the executable .JAR file.

In Windows it is sufficient to just double click the .JAR file.
You can also start the program by typing the following inside the folder with the .JAR file:

~~~bash
java -jar <filename>.jar
~~~

## How to build
[Maven](https://maven.apache.org/) is used to build the program from source code. 
Just execute the following Maven goal to build the binary version:

~~~bash
mvn jfx:jar
~~~

## Bugs & Issues
If there are problems with this program or you find any bugs, then please feel free to create a new issue on the
[corresponding GitHub page](https://github.com/clovergaze/dilithium-exchange-manager/issues).

## Author
Johannes Hillert ([GitHub](https://github.com/clovergaze))

## License
Copyright (c) 2018 Johannes Hillert. Licensed under the MIT license, see the included LICENSE file for details.