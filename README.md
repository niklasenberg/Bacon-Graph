# Bacon Graph

A graph based solution to the varying [degrees of Kevin Bacon.](https://en.wikipedia.org/wiki/Six_Degrees_of_Kevin_Bacon)

## Description

Developed as a project for an algorithm class, this text-based application loads a text-file of a specific format, where \<a\> symbolizes and actor and \<t\> symbolizes a movie this actor has starred in. These strings are then established as nodes in the Bacon Graph which then are used via Breadth-First-Search to determine a Bacon-number for a user asked actor. There is also additional functionality added, in being able to change the actor which to determine a number for (i.e DeNiro-number or DiCaprio-number). 

The input needs to consist of a .txt-file with the following format:

...  
\<a\>'El Toro', Baltazar  
\<t\>Operación narcóticos (1991)  
\<a\>'El Viti'  
\<t\>Los tarantos (1963)  
\<a\>'Finchie''Coveney, Finbar  
\<t\>The Brotherhood (2014)  
...  
 
The code has been tested for 2,835,629 actors and 811,167 movies, providing a load time of roughly 8 seconds on a standard laptop.

### Requirements

* JDK 12+
* Junit 5

## Authors

[Niklas Enberg](mailto:nickeen95@gmail.com?subject=Bacon%20Graph)

