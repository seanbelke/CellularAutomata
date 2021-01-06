# CellularAutomata
A graphical program that runs Conway's Game of Life using 1-dimensional cellular automata as input.  Allows the user to change the rule used as input as well as the color theme.

This was inspired by a YouTube video I watched by Elliot Waite called "Cellular Automata: Rule 30 fed as input to Conway's Game of Life" - Here is a link: 

https://www.youtube.com/watch?v=IK7nBOLYzdE

Conway's Game of Life involved an infinite grid of cells, all of which are either dead or alive.  At each iteration of the game, the cells are updated according to the following
rules.  These rules all concern a cell's "neighbors," which are the 8 cells that are directly next to that cell, including along the 4 diagonals.  The rules are: 

    1. If a cell has more than 3, or fewer than 2, living neighbors, it will be dead in the next iteration of the game. 
    2. If a cell has exactly 3 living neighbors, it will be alive in the next iteration of the game. 
    3. If a cell has exactly 2 living neighbors, its state will not change from this iteration of the game to the next.  In other words, it will be alive if it was alive already,
    and it will be dead if it was dead already.  

Conway's Game of Life leads to many interesting short-term interactions between cells as well as some cool long-term patterns.

In this project, I use 1-dimensional cellular automata as input (they feed into the "bottom row" of the Game of Life).  As mentioned above, Conway's Game of Life
theoretically operates on an infinite grid, but seeing as that's not possible to represent in a computer, a finite grid is used instead, so it has a bottom row.  


## 1D Cellular Automata

1-dimensional cellular automata operate under a system where each cell has three "neighbors" - itself, the cell to the left, and the cell to the right.  Since there are three 
neighbors, there are 2^3 = 8 configurations of dead/alive neighbors.  A "rule" for a 1-dimensional cellular automata specifies, for each of these 8 configurations, whether or 
not a cell with that configuration will be dead or alive on the next iteration.  Because of this, each rule can be represented as an 8-bit integer, where each bit indicates
whether or not the rule will give a cell life or death for that index' configuration.  That's confusing, so take rule 30 for example.  30 in binary (8 bits) is 00011110.  
Each of these 8 bits has an index.  The rightmost bit is index 0 (000 in binary), and the leftmost bit is index 7 (111 in binary).  So, since the bit at index 0 is 0, this indicates
that a cell with three dead neighbors (since the index 0 in binary is 000, i.e., <dead><dead><dead) will be dead in the next iteration.  Furthermore, since the bit at index 2 is 1, 
this indicates that if the only living neighbor of a cell is itself (since the index 2 in binary is 010, i.e., <dead><alive><dead>), then a cell will be living in the next 
iteration.

To use the 1D Cellular Automata (CA) as input for this project, each row of the CA is shifted up by one, and the next row is generated by applying the rule to each cell in the
row above.  This keeps happening, and eventually the top row of the input hits the bottom row of the section of the grid governed by Conway's Game Of Life, and this is where
they start to interact in really interesting ways.  

## Color

Normally, Cellular Automata is represented as a black-and-white grid of cells (since each cell is either dead or alive).  For this project, since I thought it would look
much prettier, I made living cells white and dead cells have different shades depending on how long they have been dead for.  This creates some really interesting patterns with
a lot of depth.  There are 6 color themes that the user can choose from in this project.  The default one, which I have named "lilac" was my attempt to recreate the colors
shown in the YouTube video linked above as closely as possible.

Hopefully that is enough background information for someone to understand the gist of this project.  In addition to being able to change the color theme used by the animation,
the user can change the rule being used as input to Conway's Game of Life by entering in a different number (refer to the explanation above about what each number means).  
After selecting a color theme and a rule number, the user should press the "Restart/Update Animation" button to see the new input being fed into Game of Life, using the new
color theme.  

## Other Notes

I have only tested this on a Windows PC, and since I'm using the System Look And Feel (The Java Cross-Platform L&F doesn't look very good in my opinion) it's possible that there
will be some slight misalignments when running this on a Mac or Linux machine, although I don't know for sure.  On Windows, everything should line up as intended.  
