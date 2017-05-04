[![Build Status](https://travis-ci.org/meteoorkip/GraphterEffects.svg?branch=master)](https://travis-ci.org/meteoorkip/GraphterEffects)

The manual and the releases can be found here: [github.com/meteoorkip/GraphterEffects/releases](https://github.com/meteoorkip/GraphterEffects/releases)

# Graphter Effects
### introducing Graafvis
Current graph visualization solutions lack the functionality to easily create rich visualizations
that intuitively show what these graphs represent. For the Design Project (TCS MOD11) we
have created the library and tool Graphter Effects that can generate these customized
visualizations using our domain-specific logic language Graafvis

## Graafvis input
*Input 1: a Graafvis script specifying layout rules for graph visualisation*

![River crossing example script](http://i.imgur.com/0j2vGD7.png "River crossing example script")

Graafvis may be considered as "shorthand Prolog with special predicates".
A .vis-script contains simple logic rules that define relations between two domains:
the input graph and the output visualization.

Specific predicates...
* describe information from the input graph (e.g. node, edge, degree, inShortestPath)
* specify consequences for the visualization (e.g. shape, image, left, distance, align)

## Graph input
*Input 2: a graph which contains information that can be visually represented*

![River crossing example graph](http://i.imgur.com/pPMu6nQ.png "River crossing example graph")

Thirteen graph import formats are currently supported, including DOT, GraphML and GXL.

## Process
Both the Graafvis script and the graph file are loaded and checked for errors.
They are then converted to Prolog rules and a Prolog library.

![Library design diagram](http://i.imgur.com/6w84s60.png "Library design diagram")

Three programming paradigms are involved:
### Logic
Using Prolog, the logic programming done in Graafvis is solved such that visualization solutions are found.
### Imperative
When during the Prolog solving process graph information is required,
it is retrieved at run time from the Prolog library using imperative Java code.
### Contraint
Numeric values such as position and dimensions are solved using Chocosolver:
a constraint logic programming interface.

The resulting visualization elements are afterwards bundled into an SVG file.

## Visualization output
*Output: a generated\* visualization using the script and the input graph above*

![Visualization output with constraints](http://i.imgur.com/L6mgRh3.jpg "Visualization output with constraints")
\*The constraints depicted with arrows and labels are added to the generated visualization for clarification purposes

The SVG image resulting from the Graphter Effects visualization process contains visualization elements
that abide the given Graafvis rules. It contains a representation of the graph with relatable and intuitive imagery.

## Product
Graphter Effects has proven to be a strong universal solution:
in Graafvis one can define both complex and simple visualisations in a quick and intuitive way.

The library includes...
* error feedback
* a vast number of visualization and graph predicates
* a user manual
* possible extension by third parties through Java implemented predicates

The tool includes...
* an integrated compiler (the library)
* a rich code editor with syntax highlighting
* debug tools for inspection of generated rules and visualization elements

## Contributors
### Developers
* Ron van Bree
* Lindsay Kempen
* Sven Konings
* Hans van der Laan
* Pim van Leeuwen

### Client & supervisor
Arend Rensink

## Contact
Website: [rebrand.ly/graphtereffects](https://rebrand.ly/graphtereffects)

Email: [graphtereffects@gmail.com](mailto:graphtereffects@gmail.com)
