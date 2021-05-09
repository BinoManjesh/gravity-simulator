# Gravity Simulator

Place planets anywhere and set their velocities and sizes. Each planet will exert a force on every other planet according to Newton's Law of Gravitation. If planets touch, they will collide inelastically.

This project was made using [libGDX](https://libgdx.com/). See the libGDX Javadocs [here](https://libgdx.badlogicgames.com/ci/nightlies/docs/api/).

## How to run
Download and run [gravity_simulator.exe](https://github.com/BinoManjesh/gravity-simulator/releases/download/1/gravity_simulator.exe). This file consists of the compiled source code and jars in the libs folder bundled into an exe using [Launch4j](http://launch4j.sourceforge.net/).

Or, after downloading this repository you can compile and run the code on your computer. run.bat contains the commands to do this. This may not work if you use jdk 9 or above.
## Controls
* Drag the mouse with left click on an empty region to move the camera
* Scroll the mouse to zoom in/out
* Press the space bar to pause/resume the simulation
* Right click to place a planet
* Click a planet to select it. It will turn gray.
* Press +/- to increase/decrease the radius (and as a result, mass) of the selected planet
* Press backspace to delete the selected planet
* Drag a planet with left click to move it
* Drag a planet with right click to set its velocity (Green lines indicate velocity)
* Press ctrl + backspace to delete all planets