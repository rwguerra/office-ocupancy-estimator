# ocupancy_estimator
[JAVA] Create estimators, which, from data collected by sensors, be able to determine the number of people in an office

# GOALS

_The objective of the project is to create estimators, which, from data collected by sensors, are able to determine the number of people in the office in figure 1. The name of the sensors appears in green. To hear the code better a javadoc has been generated and is accessible via \ Project Final \ dist \ javadoc \ index.html_

![](RackMultipart20201008-4-18mx0s7_html_6bb2822920c48070.png)

_Figure 1 - Plan of the office under consideration_

# Summary

[GOALS](#goals)

[ARCHITECTURE](#architecture)

[GUI PACKAGE](#gui-package)

[DATA PACKAGE](#data-package)

[FITTERS PACKAGE](#fitters-package)

[RESULT AND CONCLUSION](#result-and-conclusion)

# ARCHITECTURE

The object classes have been coded as generally as possible, that is to say as independent as possible from the other classes, for the sake of readability, but also for the sake of reuse.

As a result, our main classes are almost empty (usually something like the new class ()) and our class files are small (200 lines max, but smaller is usually better).

Figure 2 shows the UML class diagram of the project.

![](RackMultipart20201008-4-18mx0s7_html_46b46ec1c53332f8.png)

_Figure 2 - UML Class Diagram_

Our project is divided into three &quot;packages&quot;, the data package is intended for processing sensor data, and estimator calculations. The gui package takes care of the graphical interface for plotting the data curves and the results of the estimators, and also contains the &quot;main&quot; class of the project, and the fitters package takes care of the calculations of estimate.

# GUI PACKAGE

## MainFrame.java

The MainFrame class is used to launch the project.

## PlotFactory.java

The Plotter class is used to create the window containing which allows you to select the different curves that you want to draw and a button that allows you to launch the plotting of the curves.

It is an extension of the JFrame class which contains two JPanels, one with an array of JCheckBox which contains the name of the variables as well as the results of the estimators, and the other with the buttons described above. These are associated with two &quot;listeners&quot; which allow you to perform the desired actions.

![](RackMultipart20201008-4-18mx0s7_html_5cd8aab91c52689e.png)

_Figure 3 - Initial GUI_

## PlotButtonListener.java

This class is responsible for managing the plot button, it works with an action listener that checks what data and estimators need to be plotted.

![](RackMultipart20201008-4-18mx0s7_html_fe22cd40f305b2f3.png)


_Figure 4 - GUI with plot_

# DATA PACKAGE

## DataContainer.java

The &quot;DataContainer&quot; classes which allow to store and process the data from the various sensors and &quot;ReadCSV&quot; which allows to read the data from the Excel file used in the DataContainer have been given to us, we are therefore going to develop the other classes and their operations.

## DataContainersWithProcessing.java

This class is an extension of DataContainers, it also contains the objects allowing to obtain the estimate of the number of people by two different methods and store them.

## SimulatedAnneling.java

This class is responsible for performing calculations in a multidimensional improvement optimization algorithm with diversification for the CO2 estimator. Unfortunately, this estimator does not work.

# FITTERS PACKAGE

## LaptopOfficeModel.java

This class is responsible for the first estimator, we use a simple comparison with an &quot;if&quot; loop, and we add the sum of the estimates in a new variable. The consumption of each of the 4 laptops is measured by a power meter (variable &#39;power\_laptopX\_zoneY&#39;). If the average consumption is greater than the standby consumption (15W), someone is considered to be working on the computer.

## MotionOfficeModel

In this class we use the results of the first estimator (power laptop) to obtain the value of the coefficient with the DichotmicScaler class presented below. The coefficient is then multiplied with the data from the motion sensor and thus obtain an estimate of the number of people.

## DichotomicScaler.java

Rightly classifies the occupation based on movement. In order to determine the best proportional coefficient, a simple dichotomy can be made, considering estimates based on laptop consumption as actual occupations. Scale the coefficient to minimize the error between laptop consumption and occupancy estimators based on motion detection.

## CO2OfficeModel.java

This class is responsible for performing calculations in a multidimensional improvement optimization algorithm with diversification for the CO2 estimator. Unfortunately, this estimator does not work.

# RESULT AND CONCLUSION

To get started, practical considerations must be taken into account. Note that a communication power meter costs around 80 euros, a movement detector at 120 euros, a contact sensor for door or window 50 euros and a CO2 sensor around 250 euros. According to figure 1 we have four power sensors with a total cost of 320 euros. We also have two motion sensors, each at a cost of 120, for a total of 240 euros. Finally, the door and window sensors as well as the CO2 sensors have a total cost of 600 euros to implement.

It should also be remembered that in order to make a correct estimation by movement, it is necessary to find the proportional coefficient based on the consumption of laptops, that is, to implement the estimation by movement, we need the energy consumption sensors.

In summary, we will have the following costs for the implementation of the estimation systems (Table 1).

| **Estimate by** | **Number of sensors** | **Total value** |
| --- | --- | --- |
| Power Consumption | 4 | € 320.00 |
| Motion | 6 | € 560.00 |
| CO2 | 4 | € 600.00 |

_Table 1 - Total costs_

Unfortunately, we were unable to code the CO2-based occupancy estimate. This solution was therefore abandoned due to complexity and cost.

On the other hand, it is difficult to analyze the estimation graphs based on consumption and movement (figures 5 and 6) to know which was the most precise in the estimation, because we do not have concrete information on the actual hour-by-hour occupancy of the office.

![](RackMultipart20201008-4-18mx0s7_html_9247609b0b879947.png)

_Figure 5 - Estimate by consumption of laptops_

![](RackMultipart20201008-4-18mx0s7_html_ce41380df8a0e3d9.png)

_Figure 6 - Estimation by Motion_

In this way, we think of using the method of consumption of laptops to estimate the occupation of the office in order to have the cheapest cost, to be simpler and easier to implement.
