# sandpile-dynamical-system
Java code to simulate variants on the Abelian sandpile

The Abelian sandpile is a dynamical system where grains of sand pile up on nodes of a lattice until the node gets too full, at which point the grains spread evenly among its neighbors. For more details, see e.g. https://en.wikipedia.org/wiki/Abelian_sandpile_model

This simulator is capable of emulating the system on four different types of lattice, each of which can be scaled to different heights and widths: a rectangular table, a (horizontally and vertically symmetric) hexagonal table, a rectangular cylinder, and a rectangular torus with a single hole for grains to fall through. In each case, the simulator allows you to start the table with each node pre-loaded with a specified number of chips; to put a pile of chips on the clearly-marked "central" node and keep toppling nodes until the system stabilizes; or to compute and display the identity element of the group of stable configurations on that particular table.

The display window has a different color for each possible depth of node (on these tables, at least - other lattice shapes allow more grains on a node without toppling), specified in the SandpileControlPanel.java class. By default, a node with 0 grains on it will be white, with additional grains taking it through gray, yellow, and pink, until unstable nodes are colored red.
