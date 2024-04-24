# Laboratory simulation

## Introduction
This project is a multithread simulation of a laboratory. In the laboratory there are organisms and assistants, who need to feed the organisms. Each organism has stamina (0-10) which lowers with time. Organism dies when the stamina reaches 0. Organism eat automatically when there is food avaliable. Every assistant has a container with food (0-50) and they move up and down to chceck if organism has food. If amount of food is lower than 5 assistant fills up the container to 10 units.  When the amount of food in the container is getting low the assistant fills their container in the distributor. Only one assistant can use the distributor at a time.

## Technologies
* JDK version: 11
* JavaFX version: 17.0.2x
* Gradle
