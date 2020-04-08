## This application determines if two cities are connected. 

Two cities are considered connected if there’s a series of roads that can be traveled from one city to another.

List of roads is available in a file (city.txt). The file contains a list of city pairs (one pair per line, comma separated), which indicates that there’s a road between those cities. It will be deployed as a Spring Boot App and expose one endpoint: [http://localhost:8080/connected?origin=city1&destination=city2](http://localhost:8080/connected?origin=city1&destination=city2).
Your program should respond with **yes** if city1 is connected to city2, **no** if city1 is not connected to city2.
Any unexpected input should result in a **no** response.

**For Example:**
*city.txt * content is:

```
Boston, New York
Philadelphia, Newark
Newark, Boston
Trenton, Albany
```

* city `Boston` has exactly 2 roads connecting cities `New York` and `Newark`. 
* cities `Trenton` or `Albany` are not reachable from any of `New York`,`Newark`,`Philadelphia` & `Boston`
* `Boston` is connected to `Philadelphia` via `Newark`
*  `New York` is connected to `Philadelphia` via `Boston` via `Newark`

### Clone
```sh
git clone https://github.com/jdodla/connected-roads.git
```
### Run the application

```sh
cd connected-roads
```

```sh
./gradlew bootRun
```

### Show the city list and the direct connected cities 

[http://localhost:8080/](http://localhost:8080/) 


### Play with it

Example `New York` and `Trenton` _are not_ connected:

[http://localhost:8080/connected?origin=New York&destination=Trenton](http://localhost:8080/connected?origin=New York&destination=Trenton) (result **no**)

Example `New York` and `Danbury` _are_ connected:

[http://localhost:8080/connected?origin=New York&destination=Danbury](http://localhost:8080/connected?origin=New York&destination=Danbury) (result **yes**)

Example `Boston` and `Albany` _are not_ connected

[http://localhost:8080/connected?origin=Boston&destination=Albany](http://localhost:8080/connected?origin=Boston&destination=Albany) (result **no**)

Example `A` and `B` _are not_ listed

[http://localhost:8080/connected?origin=A&destination=B](http://localhost:8080/connected?origin=A&destination=B) (result **no**)
