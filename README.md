# EGrep clone

## Installation and Execution
```shell
    # Install java 8
    $ sudo apt-get update
    $ sudo apt-get install openjdk-8-jdk
    # Check if it is installed
    $ java -version
```
Should return something like this:
```text
openjdk version "1.8.0_242"
OpenJDK Runtime Environment (build 1.8.0_242-b09)
OpenJDK 64-Bit Server VM (build 25.242-b09, mixed mode)
```
and to init, compile and run you can type:
```shell
    # Initialisation
    $ ant init
    # Compile source files into output/classes
    $ ant compile
    # to execute the egrep command on a text file
    $ ant run -Dregex="${VALUE}" -Dfile=${PATH_TO_FILE}
```
or you can just execute the script `myegrep` by typing:
```shell
    $ ./myegrep "${VALUE}" ${PATH_TO_FILE}
```

## Clean the folder
```shell
    $ ant clean
```
## Documentation (WIP)
The documentation is also available on
and can be generated with:
```shell
    $ ant doc
```