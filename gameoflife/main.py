#!/usr/bin/python

# gameoflife - Simple Conway's game of life

__author__ = "Henri Bragge"
__version__ = "$Revision: 0.1 $"
__date__ = "$Date: 2013/10/10 19:40:01 $"
__copyright__ = "BSD"
__license__ = "BSD"

import sys
import pygame
import threading
from copy import deepcopy
pygame.init()

WIN_WIDTH = 800
WIN_HEIGHT = 480

#WIN_WIDTH = 100
#WIN_HEIGHT = 80

LEFT = 1
RIGHT = 0
LEFTBUTTON = 0
RIGHTBUTTON = 2

sq_size = 20
curr_table = [[0 for _ in range((WIN_WIDTH/sq_size))] for _ in range((WIN_HEIGHT/sq_size))]
new_table = [[0 for _ in range((WIN_WIDTH/sq_size))] for _ in range((WIN_HEIGHT/sq_size))]

num_rows = len(curr_table)
num_cols = len(curr_table[0])

window = pygame.display.set_mode((WIN_WIDTH, WIN_HEIGHT))
window.fill((120, 120, 120))

# draw a line - see http://www.pygame.org/docs/ref/draw.html for more 
for i in range(sq_size, WIN_WIDTH, sq_size):
    pygame.draw.line(window, (180, 180, 180), (i, 0), (i, WIN_HEIGHT))

for i in range(sq_size, WIN_WIDTH, sq_size):
    pygame.draw.line(window, (180, 180, 180), (0, i), (WIN_WIDTH, i))

# draw it to the screen
pygame.display.flip()

def sq_fill(button, pos):
        x, y = e.pos
        sq_x = x/sq_size
        sq_y = y/sq_size
        # sq_x/sq_y is an integer, so startx/starty will be rounded to a multiple of sq_size
        startx = sq_x*sq_size
        starty = sq_y*sq_size
        if button == LEFT:
            curr_table[sq_y][sq_x] = 1
            window.fill((215, 215, 0), (startx+1, starty+1, sq_size-1, sq_size-1))
        else:
            curr_table[sq_y][sq_x] = 0
            window.fill((120, 120, 120), (startx+1, starty+1, sq_size-1, sq_size-1))
        pygame.display.flip()

def neighbours(table, center):
    count = 0
    for i in range (0, len(table)):
        count += table[i].count(1)
    if center == 1:
        count -= 1
    return count

def refresh_table(table):
    for y in range(0, num_rows):
        for x in range(0, num_cols):
            sq_x = x
            sq_y = y
            startx = sq_x*sq_size
            starty = sq_y*sq_size
            if table[y][x] == 1:
                window.fill((215, 215, 0), (startx+1, starty+1, sq_size-1, sq_size-1))
            else:
                window.fill((120, 120, 120), (startx+1, starty+1, sq_size-1, sq_size-1))
    pygame.display.flip()

class ThreadClass(threading.Thread):
    def run(self):
        global curr_table
        global new_table

        for y in range(0, num_rows):
            for x in range(0, num_cols):
                xstart = x - 1
                ystart = y - 1
                xstop = x + 2
                ystop = y + 2
                xind = 1
                yind = 1
                if x == 0:
                    xstart = x
                    xstop = x + 2
                    xind = 0
                if x == num_cols-2:
                    xstart = x - 1
                    xstop = x + 2
                    xind = 0
                if x == num_cols-1:
                    xstart = x - 1
                    xstop = x + 1
                    xind = 1
                if y == 0:
                    ystart = y
                    ystop = y + 2
                    yind = 0
                if y == num_rows-2:
                    ystart = y - 1
                    ystop = y + 2
                    yind = 0
                if y == num_rows-1:
                    ystart = y - 1
                    ystop = y + 1
                    yind = 1
                subarr = [row[xstart:xstop] for row in curr_table[ystart:ystop]]
                n = neighbours(subarr, subarr[yind][xind])
                if n < 2:
                    new_table[y][x] = 0
                elif n == 2 and curr_table[y][x] == 1:
                    new_table[y][x] = 1
                elif n == 3:
                    new_table[y][x] = 1
                elif n > 3:
                    new_table[y][x] = 0
                else:
                    new_table[y][x] = 0

        curr_table = deepcopy(new_table)
        refresh_table(curr_table)

while True: 
    for e in pygame.event.get(): 
        if e.type == pygame.QUIT: 
            sys.exit(0)
        elif e.type == pygame.MOUSEBUTTONDOWN:
            sq_fill(e.button, e.pos)
        elif e.type == pygame.MOUSEMOTION:
            if e.buttons[LEFTBUTTON]:
                sq_fill(LEFT, e.pos)
            elif e.buttons[RIGHTBUTTON]:
                sq_fill(RIGHT, e.pos)
        elif e.type == pygame.KEYDOWN and e.unicode == u'\r':
            t = ThreadClass()
            t.start()
        else:
            print e
