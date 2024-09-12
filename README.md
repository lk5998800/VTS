# Visual Transit Simulator: Project Iteration 3

## The Visual Transit Simulator Software

In the project iterations, you will be working on a visual transit simulator (VTS) software. The current VTS software models vehicle transit around the University of Minnesota campus. Specifically, the software simulates the behavior of vehicles and passengers on campus. The VTS software currently supports four main types of vehicles: small buses, large buses, electric trains, and diesel trains. Vehicles provide service for a line. A line is made by two routes: an outbound and an inbound route. Vehicles go along a route, make stops, and pick up/drop off passengers. The simulation operates over a certain number of time units. In each time unit, the VTS software updates the state of the simulation by creating passengers at stops, moving vehicles along the routes, allowing a vehicle to pick up passengers at a stop, etc. The simulation is configured using a *configuration* file that specifies the simulation lines, the stops of the routes, and how likely it is that a passenger will show up at a certain stop at each time unit. Routes must be defined in pairs, that is, there should be both an outbound and inbound route and the routes should be specified one after the other. The ending stop of the outbound route should be at the same location as the starting stop of the inbound route and the ending stop of the inbound route should be at the same location as the starting stop of the outbound route. However, stops between the starting and ending stops of outbound and inbound routes can be at different locations. After a vehicle has passed a stop, it is possible for passengers to show up at stops that the vehicle has already passed. For this reason, the simulator supports the creation of multiple vehicles and these vehicles will go and pick up the new passengers. Each vehicle has its own understanding of its own route, but the stops have relationships with multiple vehicles serving the same line. Vehicles do not make more than one trip in the line they serve. When a vehicle finishes both of its routes (outbound and inbound), the vehicle exits the simulation.

The VTS software is divided into two main modules: the *visualization module* and the *simulator module*. The visualization module displays the state of the simulation in a browser, while the simulator module performs the simulation. The visualization module is a web client application that runs in a browser and it is written in Javascript and HTML. The visualization module code is inside the `<dir>/src/main/webapp/web_graphics` directory of this repo (where `<dir>` is the root directory of the repo). The simulator module is a web server application written in Java. The simulator module code is inside the `<dir>/src/main/java/edu/umn/cs/csci3081w/project` directory. The simulator module is divided into two parts: *model classes* and the *webserver classes*. The model classes model real-world entities (e.g., the concept of a vehicle) and the code is inside the `<dir>/src/main/java/edu/umn/cs/csci3081w/project/model` directory. The webserver classes include the code that orchestrates the simulation and is inside the `<dir>/src/main/java/edu/umn/cs/csci3081w/project/webserver` directory. The visualization module and the simulator module communicate with each other using [websockets](https://www.baeldung.com/java-websockets).

The user of the VTS software interacts with the visualization module using the browser and can specific how long the simulation will run (i.e., how many time units) and how often new vehicles will be added to a route in the simulation. The users also specifies when to start the simulation. The image below depicts the graphical user interface (GUI) of the VTS software.

![GUI of the VTS Software](/images/vts_iteration_1.png)

### VTS Software Details

#### Simulation Configuration
The simulation is based on the `<dir>/src/main/resources/config.txt` configuration file. The following excerpt of the configuration file defines a bus line.

```
LINE_START, BUS_LINE, Campus Connector

ROUTE_START, East Bound

STOP, Blegen Hall, 44.972392, -93.243774, .15
STOP, Coffman, 44.973580, -93.235071, .3
STOP, Oak Street at University Avenue, 44.975392, -93.226632, .025
STOP, Transitway at 23rd Avenue SE, 44.975837, -93.222174, .05
STOP, Transitway at Commonwealth Avenue, 44.980753, -93.180669, .05
STOP, State Fairgrounds Lot S-108, 44.983375, -93.178810, .01
STOP, Buford at Gortner Avenue, 44.984540, -93.181692, .01
STOP, St. Paul Student Center, 44.984630, -93.186352, 0

ROUTE_END

ROUTE_START, West Bound

STOP, St. Paul Student Center, 44.984630, -93.186352, .35
STOP, Buford at Gortner Avenue, 44.984482, -93.181657, .05
STOP, State Fairgrounds Lot S-108, 44.983703, -93.178846, .01
STOP, Transitway at Commonwealth Avenue, 44.980663, -93.180808, .01
STOP, Thompson Center & 23rd Avenue SE, 44.976397, -93.221801, .025
STOP, Ridder Arena, 44.978058, -93.229176, .05
STOP, Pleasant Street at Jones-Eddy Circle, 44.978366, -93.236038, .1
STOP, Bruininks Hall, 44.974549, -93.236927, .3
STOP, Blegen Hall, 44.972638, -93.243591, 0

ROUTE_END

LINE_END


STORAGE_FACILITY_START

SMALL_BUSES, 3
LARGE_BUSES, 2
ELECTRIC_TRAINS, 1
DIESEL_TRAINS, 5

STORAGE_FACILITY_END
```

The configuration line `LINE_START, BUS_LINE, Campus Connector` defines the beginning of the information belonging to a simulated line. The configuration line `ROUTE_START, East Bound` defines the beginning of the information defining the outbound route. (The outbound route is always defined before the inbound route). The subsequent configuration lines are the stops in the route. Each stop has a name, a latitude, a longitude, and the probability to generate a passenger at the stop. For example, for `STOP, Blegen Hall, 44.972392, -93.243774, .15`, `Blegen Hall` is the name of the stop, `44.972392` is the latitude, `-93.243774` is the longitude, and `.15` (i.e., `0.15`) is the probability to generate a passenger at the stop. The last stop in a route has a probability to generate a passenger always equal to zero. The information inside `STORAGE_FACILITY_START` and `STORAGE_FACILITY_END` provides the number of small buses, large buses, electric trains, and diesel trains available for the simulation.

#### Running the VTS Software
To run the VTS software, you have to first start the simulator module and then start the visualization module. To start the simulator module, go to `<dir>` and run `./gradlew appRun`. To start the visualization module, open a browser and paste this link `http://localhost:7777/project/web_graphics/project.html` in its search bar. To stop the simulator module, press the enter/return key in the terminal where you started the module. To stop the visualization module, close the tab of browser where you started the module. In rare occasions, you might experience some issues in starting the simulator module because a previous instance of the module is still running. To kill old instances, run `ps aux | grep gretty | awk '{print $2}' | xargs -L 1 kill` and this command will terminate previous instances. (The command is killing the web server container running the simulator module.) The command works on CSE lab machines.

#### Simulation Workflow
Because the VTS software is a web application, the software does not have a `main` method. When you load the visualization module in the browser, the visualization module opens a connection to the simulator module (using a websocket). The opening of the connection triggers the execution of the `WebServerSession.onOpen` method in the simulator module. When you click `Start` in the GUI of the visualization module, the module starts sending messages/commands to the simulator module. The messages/commands exchanged by the two modules are [JSON objects](https://www.w3schools.com/js/js_json_objects.asp). When you click on a vehicle, the detailed information of the vehicle is displayed in the visualization module. When you select a line from the drop down menu, you can simulate an issue on that line. You can see the messages/commands created by the visualization module insdie `<dir>/src/main/webapp/web_graphics/sketch.js`. The simulator module processes messages received by the visualization model inside the `WebServerSession.onMessage` method. The simulator module sends messages to the visualization module using the `WebServerSession.sendJson` method. Finally, once you start the simulation you can restart it only by reloading the visualization module in the browser (i.e., reloading the web page of the visualization module).

## Tasks and Deliverables
In this project iteration, you will need to further understand, extend, and test the VTS software. The tasks of this project iteration can be grouped into three types of activities: creating the software documentation, making software changes, and testing. The following table provides a summary of the tasks you need to perform in this project iteration. For each task, the table reports the task ID, the activity associated with the task, a summary description of the task, the deliverable associated with the task, and the major deliverable that includes the task deliverable.

| ID | Activity | Task Summary Description | Task Deliverable | Deliverable |
|---------|----------|--------------------------|------------------|----------------------|
| Task 1 | Software documentation | Update the UML class diagram for the model classes | UML Class Diagram | HTML Javadoc |
| Task 2 | Software documentation | Update the UML class diagram for the webserver classes | UML Class Diagram | HTML Javadoc |
| Task 3 | Software documentation | Create a Javadoc documentation for the code in the simulator module | HTML Javadoc | HTML Javadoc|
| Task 4 | Software documentation | Make sure that the code conforms to the Google Java code style guidelines | Source Code | Source Code |
| Task 5 | Software changes and Testing | Refactoring 1 - Remove `Vehicle.TESTING` and `Vehicle.testOutput` attributes from source and test code | Source and Test Code | Source and Test Code |
| Task 6 | Software changes | Feature 1 - Decorate vehicles using colors | Source Code | Source Code |
| Task 7 | Testing | Create tests for all the classes in the simulator module | Test Code | Test Code |

### Suggested Timeline for the Tasks

We suggest you define a tasks-oriented timeline for your team so that you can make progress throughout this project iteration. The schedule for the project iteration is tight, and it is important that the team keeps up with the project. We suggest the following timeline. However, you are free to define your own timeline. Make sure to properly distribute the tasks. All the major deliverables are due at the end of the project iteration. If you have questions on the management of your progress in the project iteration, please contact us on Piazza using the group we created and we will be happy to take it from there.

| Date | Milestone Description | Tasks |
|-----------------|-----|-----------------|
| 11/29/2021 at 8:00 am | Software documentation and testing | [Task 1], [Task 2], [Task 3], [Task 4], [Task 5], [Task 7] |
| 12/06/2021 at 8:00 am | Software changes and software testing | [Task 6], [Task 7] |
| 12/13/2021 at 8:00 am | Revision of the software documentation and software testing | [Task 1], [Task 2], [Task 3], [Task 4], [Task 5], [Task 6], [Task 7] |

### Tasks Detailed Description
This section details the tasks that your team **needs to perform** in this project iteration.

#### Task 1: Update the UML class diagram for the model classes
In this task, you should update the UML class diagram of the model classes (i.e., classes in the `<dir>/src/main/java/edu/umn/cs/csci3081w/project/model` directory) to account for the changes/additions required in this project iteration. If necessary, provide a description inside `<dir>/doc/overview.html` to clarify the subtleties that are essential to understand the diagram and the design decisions you made.

You should update the diagram image in `<dir>/doc/diagrams`. You can generate the HTML Javadoc documentation, which includes this diagram, by running `./gradlew clean javadoc`.

#### Task 2: Update the UML class diagram for the webserver classes
In this task, you should update the UML class diagram of the webserver classes (i.e., classes in the `<dir>/src/main/java/edu/umn/cs/csci3081w/project/webserver` directory) to account for the changes/additions required in this project iteration. If necessary, provide a description inside `<dir>/doc/overview.html` to clarify the subtleties that are essential to understand the diagram and the design decisions you made.

You should update the diagram image in `<dir>/doc/diagrams`. You can generate the HTML Javadoc documentation, which includes this diagram, by running `./gradlew clean javadoc`.

#### Task 3: Create a Javadoc documentation for the code in the simulator module
You should create Javadoc comments according to the Google Java code style guidelines we provided. In other words, add comments where the Google Java code style guidelines require your team to do so. You can generate the HTML Javadoc documentation by running `./gradlew javadoc` (or `./gradlew clean javadoc`).

#### Task 4: Make sure that the code conforms to the Google Java code style guidelines
Consistency in code organization, naming conventions, file structure, and formatting makes code easier to read and integrate. In this project, the team will follow the Google Java code style guidelines. These guidelines are provided in the `<dir>/config/checkstyle/google_checks.xml` code style file. The team needs to make sure that the code produced in this project iteration (both source and test code) complies with the rules in `<dir>/config/checkstyle/google_checks.xml`. Both source and test code should conform to the rules. You can check if the code conforms to the code style rules by running `./gradlew check` or (`./gradlew clean check`).

#### Task 5: Refactoring 1 - Remove `Vehicle.TESTING` and `Vehicle.testOutput` attributes from source and test code

In this project iteration, you will need to remove the `Vehicle.TESTING` and `Vehicle.testOutput` attributes for the source and test code. While removing these attributes, you will need to suitably update the code of the simulator module and preserve the functionality provided by exsisting tests. To preserve the functionality provided by exsisting tests, you will need to use test doubles. The tests that you needed to modify to perform this refactoring need to be in your submitted solution.

Your team needs to create a GitHub issue to indicate that your team is working on this refactoring (in this project iteration we are using issues to work on features). The title of the issues should be "Refactoring 1 - Remove Vehicle.TESTING and Vehicle.testOutput attributes from source and test code". This task needs to be performed in a branch called `Refactoring1`. Your team needs to create a GitHub pull request to merge the changes into main. When your team is happy with the solution to this task, one of the team members needs to merge the `Refactoring1` branch into the `main` branch.

#### Task 6: Feature 1 - Decorate vehicles using colors

In this version of the simulator, a vehicle's color should be defined by the simulator module using the decorator pattern. Colors should be expressed in the [RGB format](https://www.w3schools.com/css/css_colors_rgb.asp). A small bus needs to be colored colored in maroon (R=122, G=0, B=25), a large bus needs to be colored in pink (R=239, G=130, B=238), an electric train needs to be colored in green (R=60, G=179, B=113), and a diesel train needs to be colored in yellow (R=255, G=204, B=51). The [alpha channel value](https://www.w3schools.com/css/css_colors_rgb.asp) associated with the colors is equal to 255 when vehicles are created. If a vehicle happens to be on a line which has an issues, the vehicle should be decorated to have a transparent version of the required color. To this end, you will need to use the [alpha channel value](https://www.w3schools.com/css/css_colors_rgb.asp) to express the value of the transparency and this value needs to be equal to 155 (the RGB values will stay the same). The transparency will help the user of the simulator identify which vehicles were serving a line when an issue happened on the line. This means that vehicles generated after the issue was resolved will not have a transparent color.

This feature needs to leverage the decorator pattern. All the classes associated with this feature need to be in the directory containing the model classes (i.e., inside `<dir>/src/main/java/edu/umn/cs/csci3081w/project/model`). All the changes to implement this feature need to be in the simulator module. Your team needs to create a GitHub issue to indicate that your team is working on this feature. The title of the issues should be "Feature 1 - Decorate vehicles using colors". This task needs to be performed in a branch called `Feature1`. Your team needs to create a GitHub pull request to merge the changes into main. When your team is happy with the solution to this task, one of the team members needs to merge the `Feature1` branch into the `main` branch.

#### Task 7: Create tests for all the classes
In this project iteration, your team needs to create JUnit test cases for all the classes in the simulator module. This means that all the model and web server classes should be tested. Your test cases need to achieve 90% branch coverage. Some of your test cases need to use test doubles. Specifically, you should create at least 10 test cases using test doubles. Your team does not need to create test cases for getter and setter methods. Your team has to document what each test is supposed to do by adding a Javadoc comment to the test. A sample set of test cases is provided in the `<dir>/src/test/java/edu/umn/cs/csci3081w/project/model` and `<dir>/src/test/java/edu/umn/cs/csci3081w/project/webserver` directories. In this task, your team can also reuse (and we encourage you to do so) the test cases that the team built during project iterations 1 and 2. We encourage your team to write the test cases before making any change to the codebase. When you add the new feature required by this project iteration, you should also add new test cases for the feature. All the test cases you create should pass. The semantics of the software should not be changed unless that is part of the feature that you need to implement in this project iteration. Your team can create test cases in any branch but the final set of test cases should be in the `main` branch. You can run the tests and measure coverage with the command `./gradlew clean test jacocoTestReport`.

#### Important Notes

To complete the tasks of this project iteration, your team can reuse any of the design documents, code, and tests that your team created in project iteration 2. However, beware of the following points:

* You might have added classes, methods, and attributes that are not present in this repo.
* You should preserve and extend the functionality provided by this repo.

## Submission
All the team members should submit the commit ID of the solution to this project iteration on Gradescope. The commit ID should be from the `main` branch of this repo. We use the commit ID to be sure that all team members agree on the final solution submitted. If you use a group submission, then only one commit ID is ok.

### General Submission Reminders
* Use GitHub issues and pull requests as you develop your solution.
* Use the branches we listed to produce your team solution.
* Make sure the files of your solution are in the repo.
* Do no add the content of the `build` directory to the repo.
* Make sure your code compiles.
* Make sure the code conforms to the Google Java code style guidelines we provided.
* Make sure the HTML Javadoc documentation can be generated using `./gradlew clean javadoc`
* Make sure all test cases pass.

## Assessment
The following list provides a breakdown of how this project iteration will be graded.

* Software documentation: 16 points
* Software changes: 32 points
* Testing: 52 points

## Resources

* [A Guide to the Java API for WebSocket](https://www.baeldung.com/java-websockets)
* [JSON objects](https://www.w3schools.com/js/js_json_objects.asp)
* [Google Maps](http://maps.google.com)
* [Reading and Writing CSVs in Java](https://stackabuse.com/reading-and-writing-csvs-in-java)
* [RGBA format](https://www.w3schools.com/css/css_colors_rgb.asp)
