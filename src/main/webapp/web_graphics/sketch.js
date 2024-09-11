const key = 'pk.eyJ1IjoibWZhenppbmkiLCJhIjoiY2t1bHZmcTNoMXBtdTJxbXh1ang4d3FiMyJ9.wjTQc1rIYO_mGc8lT69jtA';

var mapImg; // UMN Image generated from myMap
var myMap; // Map from mappa
var imageWidth;
var imageHeight;
let imageX = 250; // Top left position, in pixels, of image
let imageY = 1; // Top left position, in pixels, of image

const updateTime = 1000; // miliseconds
var totalUpdates = 0; // How many times have we called update on the sim
var elapsedTime;
var startTime;

var socket;
var connected;

var vehicles = [];
var stops = [];
var routes = [];

var numLines;
var lines = [];
var lineTimeOffsetsSliders = [];
var lineTimeOffsets = [];

var numTimeStepsSlider;
var numTimeSteps;

var startButton;
var started = false;

var simInfoYRectPos = 1; // Magic numbers for GUI elements
var simInfoYPos = 15;
var numTimeStepsTextYPos = 50;
var numTimeStepsSliderYPos = 65;
var lineTimeOffsetsYInitTextPos = 120;
var lineTimeOffsetsYInitPos = 140;
var vehiclesTimeOffsetsYOffset = 100;
var runRectYPos = 500;
var runYPos = 515;
var startYPos = 550;

var lineDropDown;
var observedVehicleText ="";

// Data for vis. Matches data_structs.h in C++
function Position(longitude, latitude) {
    this.longitude = longitude;
    this.latitude = latitude;
}
function SmallBus(id, position, numPasengers, capacity, co2, color) {
    this.id = id;
    this.position = position;
    this.numPassengers = numPassengers;
    this.capacity = capacity;
    this.co2 = co2;
    this.color = color;
}
function LargeBus(id, position, numPasengers, capacity, co2, color) {
    this.id = id;
    this.position = position;
    this.numPassengers = numPassengers;
    this.capacity = capacity;
    this.co2 = co2;
    this.color = color;
}
function ElectricTrain(id, position, numPasengers, capacity, co2, color) {
    this.id = id;
    this.position = position;
    this.numPassengers = numPassengers;
    this.capacity = capacity;
    this.co2 = co2;
    this.color = color;
}
function DieselTrain(id, position, numPasengers, capacity, co2, color) {
    this.id = id;
    this.position = position;
    this.numPassengers = numPassengers;
    this.capacity = capacity;
    this.co2 = co2;
    this.color = color;
}
function Stop(id, position, numPeople) {
    this.id = id;
    this.position = position;
    this.numPeople = numPeople;
}
function Route(id, stopIndices) {
    this.id = id;
    this.stopIndices = stopIndices;
}
function Line(id, name, type) {
    this.id = id;
    this.name = name;
    this.type = type;
}
function Color(r, g, b, a) {
  this.red = r;
  this.green = g;
  this.blue = b;
  this.alpha = a;
}


function setupSocket() {
    try {
        // Handles commands sent up from C++
        socket.onmessage =function got_packet(msg) {

            var data = JSON.parse(msg.data);
            if (data.command == "initLines") {
                numLines = int(data.numLines);
                for (let i = 0; i < data.lines.length; i++) {
                    id = data.lines[i].id;
                    name = data.lines[i].name;
                    type = data.lines[i].type;
                    if (type == "BUS_LINE") {
                        lineDropDown.option(id+" "+name+" (Bus)");
                    }
                    else if (type == "TRAIN_LINE") {
                        lineDropDown.option(id+" "+name+" (Train)");
                    }
                    lines.push(new Line(id, name, type));
                }
                initLineSliders();
            }
            if (data.command == "updateVehicles") {

                vehicles = [];

                for (let i = 0; i < data.vehicles.length; i++) {
                    id = data.vehicles[i].id;
                    numPassengers = data.vehicles[i].numPassengers;
                    capacity = data.vehicles[i].capacity;

                    var longitude = data.vehicles[i].position.longitude;
                    var latitude = data.vehicles[i].position.latitude;
                    position = new Position(longitude, latitude);
                    co2 = data.vehicles[i].co2;
                    var colorR = data.vehicles[i]['color']['r'];
                    var colorG = data.vehicles[i]['color']['g'];
                    var colorB = data.vehicles[i]['color']['b'];
                    var colorAlpha = data.vehicles[i]['color']['alpha'];
                    var color = new Color(colorR, colorG, colorB, colorAlpha);
                    if (data.vehicles[i].type == "SMALL_BUS_VEHICLE") {
                        vehicles.push(new SmallBus(id, position, numPassengers, capacity, co2, color));
                    }
                    else if (data.vehicles[i].type == "LARGE_BUS_VEHICLE") {
                        vehicles.push(new LargeBus(id, position, numPassengers, capacity, co2, color));
                    }
                    else if (data.vehicles[i].type == "ELECTRIC_TRAIN_VEHICLE") {
                        vehicles.push(new ElectricTrain(id, position, numPassengers, capacity, co2, color));
                    }
                    else if (data.vehicles[i].type == "DIESEL_TRAIN_VEHICLE") {
                        vehicles.push(new DieselTrain(id, position, numPassengers, capacity, co2, color));
                    }
                }
            }
            if (data.command == "updateRoutes") {

                routes = [];

                for (let i = 0; i < data.routes.length; i++) {
                    id = data.routes[i].id;

                    route_stop_indices = [];

                    for (let j = 0; j < data.routes[i].stops.length; j++) {
                        stop_id = data.routes[i].stops[j].id;

                        let index = stops.findIndex(x => x.id == stop_id);
                        if (index == -1) {
                            numPeople = data.routes[i].stops[j].numPeople;

                            var longitude = data.routes[i].stops[j].position.longitude;
                            var latitude = data.routes[i].stops[j].position.latitude;
                            position = new Position(longitude, latitude);

                            stops.push(new Stop(stop_id, position, numPeople));

                            route_stop_indices.push(stops.length-1);
                        } else {
                            stops[index].numPeople = data.routes[i].stops[j].numPeople;
                            route_stop_indices.push(index);
                        }
                    }
                    routes.push(new Route(id, route_stop_indices));
                }
            }
            if (data.command == "observedVehicle") {
                observedVehicleText = data.text;
            }
            //used for testing purposes
            if (data.command == "debug"){
                console.log(data);
            }
        }
    } catch(exception) {
        alert('<p>Error' + exception);
    }

    connected = false;

    // Makes sure that our sim doesn't start until we are connected and have necessary info
    socket.onopen = function() {
        connected = true;
        socket.send(JSON.stringify({command: "initLines"}));
    }
}

function mapClick(event) {
    for (let i = 0; i < vehicles.length; i++) {
        var currLong = vehicles[i].position.longitude;
        var currLat = vehicles[i].position.latitude;
        var pos = myMap.latLngToPixel(currLat, currLong);
        pos.x = pos.x + imageX;
        pos.y = pos.y + imageY;

        // If we are over a vehicle
        if (abs(mouseX - pos.x) < 25 && abs(mouseY - pos.y) < 15) {
            socket.send(JSON.stringify({command: "registerVehicle", id: vehicles[i].id}));
            return;
        }
    }
}

function setup() {
    console.log(location.port)
    socket = new WebSocket("ws://" + location.hostname+(location.port ? ':'+location.port: '')+"/project/simulator", "web_server");
    setupSocket();

    vehicles = [];
    stops  = [];
    routes = [];

    canvas = createCanvas(windowWidth, windowHeight);

    textSize(12);

    numTimeStepsSlider = createSlider(1, 100, 50, 1);
    numTimeStepsSlider.position(10, numTimeStepsSliderYPos);
    numTimeStepsSlider.style('width', '200px');

    startButton = createButton('Start');
    startButton.position(10, startYPos);
    startButton.style('width', '200px');
    startButton.style('height', '20px');
    startButton.mousePressed(start);

    lineDropDown = createSelect();
    lineDropDown.position(10, startYPos + 30);
    lineDropDown.style("width", "200px");
    lineDropDown.style("height", "20px");
    lineDropDown.changed(dropDownSelect);
    lineDropDown.option('');

    // Image/map information
    const options = {
        lat: 44.9765,
        lng: -93.215,
        zoom: 13,
        width: 900,
        height: 600,
        scale: 1,
        pitch: 0,
        style: 'dark-v9',
    };
    imageWidth = options.width;
    imageHeight = options.height;
    const mappa = new Mappa('Mapbox', key);
    myMap = mappa.staticMap(options);
    mapImg = loadImage(myMap.imgUrl);
    document.getElementById("defaultCanvas0").onclick = mapClick;
}

function dropDownSelect() {
  if (started && connected) {
      let item = lineDropDown.value();
      identifier = item.split(' ')[0];
      if(identifier != ''){
        socket.send(JSON.stringify({ command: "lineIssue", id: identifier }));
      }
  }
}

function draw() {
    if (started && connected) {
        update();
    }
    render();
    drawGui();
    drawObservedInfo();
    drawInfo();
}

function update() {
    // Send down commands to C++
    socket.send(JSON.stringify({command: "getRoutes"}));
    socket.send(JSON.stringify({command: "getVehicles"}));

    // Only update every specified timestep
    elapsedTime = millis() - startTime;
    if (elapsedTime > updateTime && totalUpdates < numTimeSteps) {
        socket.send(JSON.stringify({command: "update"}));
        startTime = millis();
        totalUpdates++;
    }
}

function render() {
    clear();

    image(mapImg, imageX, imageY);

    push();
    stroke(0);
    // draw routes
    for (let i = 0; i < routes.length; i++) {
        for (let j = 0; j < routes[i].stopIndices.length-1; j++) {
            var currLong1 = stops[routes[i].stopIndices[j]].position.longitude;
            var currLat1 = stops[routes[i].stopIndices[j]].position.latitude;
            var pos1 = myMap.latLngToPixel(currLat1, currLong1); // draw on image from lat long
            pos1.x = pos1.x + imageX;
            pos1.y = pos1.y + imageY;

            var currLong2 = stops[routes[i].stopIndices[j+1]].position.longitude;
            var currLat2 = stops[routes[i].stopIndices[j+1]].position.latitude;
            var pos2 = myMap.latLngToPixel(currLat2, currLong2);
            pos2.x = pos2.x + imageX;
            pos2.y = pos2.y + imageY;

            strokeWeight(4);
            line(pos1.x, pos1.y, pos2.x, pos2.y);
        }
    }
    pop();

    // draw stops
    for (let i = 0; i < stops.length; i++) {
        var currLong = stops[i].position.longitude;
        var currLat = stops[i].position.latitude;
        var pos = myMap.latLngToPixel(currLat, currLong);
        pos.x = pos.x + imageX;
        pos.y = pos.y + imageY;

        fill(255);
        ellipse(pos.x, pos.y, 25, 25);

        push();
        fill(0);
        textAlign(CENTER);
        textSize(14);
        text(stops[i].id, pos.x, pos.y);
        pop();
    }

    // draw vehicles
    for (let i = 0; i < vehicles.length; i++) {
        var currLong = vehicles[i].position.longitude;
        var currLat = vehicles[i].position.latitude;
        var pos = myMap.latLngToPixel(currLat, currLong);
        var color = vehicles[i].color;
        pos.x = pos.x + imageX;
        pos.y = pos.y + imageY;

        push();
        fill(color.red, color.green, color.blue, color.alpha);
        rectMode(CENTER);
        rect(pos.x, pos.y, 45, 30, 20);

        textAlign(CENTER, CENTER);
        fill(0);
        textSize(14);
        text(vehicles[i].id, pos.x, pos.y);
        pop();
    }
}

function drawGui() {
    // GUI rect
    fill(255, 255, 255, 50);
    rect(1, simInfoYRectPos, 205, 27);
    rect(1, runRectYPos, 205, 27);

    fill(0);

    push();
    textSize(16);
    textAlign(CENTER, CENTER);
    text('Sim Info', 102, simInfoYPos);
    text('Controls', 102, runYPos);
    pop();

    textAlign(LEFT, CENTER);
    text('Number of time steps to run:  ' + numTimeStepsSlider.value(), 10, numTimeStepsTextYPos, 200);
    if(lineTimeOffsetsSliders.length==numLines){
        for (let i = 0; i < numLines; i++) {
            if(lineTimeOffsetsSliders[i]!=undefined && lineTimeOffsetsSliders[i]!=null){
                if(lines[i].type == "BUS_LINE"){
                    var sliderText = 'Time steps between busses for line ' + lines[i].name + ': ' +  lineTimeOffsetsSliders[i].value();
                    text(sliderText, 10, lineTimeOffsetsYInitTextPos + (vehiclesTimeOffsetsYOffset * i), 200, 30);
                }
                else if (lines[i].type == "TRAIN_LINE"){
                    var sliderText = 'Time steps between trains for line ' + lines[i].name + ': ' +  lineTimeOffsetsSliders[i].value();
                    text(sliderText, 10, lineTimeOffsetsYInitTextPos + (vehiclesTimeOffsetsYOffset * i), 200, 30);
                }
            }
        }
    }
}

function doNothing(){
}

function start() {
    startButton.mousePressed(doNothing);
    for (let i = 0; i < lineTimeOffsetsSliders.length; i++) {
        lineTimeOffsets[i] = lineTimeOffsetsSliders[i].value();
    }
    numTimeSteps = numTimeStepsSlider.value();
    socket.send(JSON.stringify({command: "start", numTimeSteps: numTimeSteps, timeBetweenVehicles: lineTimeOffsets}));
    started = true;
    elapsedTime = millis();
    startTime = millis();
}

function initLineSliders() {
    for (let i = 0; i < numLines; i++) {
        lineTimeOffsetsSliders[i] = createSlider(1, 10, 5, 1);
        lineTimeOffsetsSliders[i].position(10, lineTimeOffsetsYInitPos + (vehiclesTimeOffsetsYOffset * i) + 30);
        lineTimeOffsetsSliders[i].style('width', '200px');
    }
}

function drawInfo() {
    // draw vehicle info when moused over
    for (let i = 0; i < vehicles.length; i++) {
        var currLong = vehicles[i].position.longitude;
        var currLat = vehicles[i].position.latitude;
        var pos = myMap.latLngToPixel(currLat, currLong);
        pos.x = pos.x + imageX;
        pos.y = pos.y + imageY;

        // if we are over the vehicle
        if (abs(mouseX - pos.x) < 25 && abs(mouseY - pos.y) < 15) {
            fill(0, 0, 0, 50);
            rect(mouseX - 120, mouseY - 55, 120, 55);
            fill(255);
            text("Num passengers: " + vehicles[i].numPassengers, mouseX - 115, mouseY - 45);
            text("Capacity: " + vehicles[i].capacity, mouseX - 115, mouseY - 30);
            text("CO2: " + vehicles[i].co2, mouseX - 115, mouseY - 15);
            return;
        }
    }
    // draw stop info when moused over
    for (let i = 0; i < stops.length; i++) {
        var currLong = stops[i].position.longitude;
        var currLat = stops[i].position.latitude;
        var pos = myMap.latLngToPixel(currLat, currLong);
        pos.x = pos.x + imageX; //(windowWidth - imageWidth) / 2;
        pos.y = pos.y + imageY; //(windowHeight - imageHeight) / 2;

        if (abs(mouseX - pos.x) < 13 && abs(mouseY - pos.y) < 13) {
            fill(0, 0, 0, 50);
            rect(mouseX - 120, mouseY - 40, 120, 20);
            fill(255);
            text("People waiting: " + stops[i].numPeople, mouseX - 115, mouseY - 30);
            break;
        }
    }
}

function drawObservedInfo() {
    text(observedVehicleText,1+imageWidth+270+5, simInfoYRectPos+200);
}
