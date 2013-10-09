import sys
import pygame
import time
import threading
pygame.init()

#WIN_WIDTH = 800
#WIN_HEIGHT = 480

WIN_WIDTH = 100
WIN_HEIGHT = 80

LEFT = 1
RIGHT = 0
LEFTBUTTON = 0
RIGHTBUTTON = 2

sq_size = 20
sq_table = [[0 for _ in range((WIN_WIDTH/sq_size))] for _ in range((WIN_HEIGHT/sq_size))]
sq_table_new = [[0 for _ in range((WIN_WIDTH/sq_size))] for _ in range((WIN_HEIGHT/sq_size))]

num_rows = len(sq_table)
num_cols = len(sq_table[0])

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
            sq_table[sq_y][sq_x] = 1
            window.fill((215, 215, 0), (startx+1, starty+1, sq_size-1, sq_size-1))
        else:
            sq_table[sq_y][sq_x] = 0
            window.fill((120, 120, 120), (startx+1, starty+1, sq_size-1, sq_size-1))
        print sq_table
        pygame.display.flip()

#def neighbours(table, x, y, x_max, y_max):
#    n = 0
#    if x != 0:
#        if

def neighbours(table, center):
    #print "len:"
    #print(len(table[0])) # cols
    #print(len(table)) # rows
    print table[0]
    count = 0
    print "center: "
    print center
    for i in range (0, len(table)):
        "+1"
        count += table[i].count(1)
    if center == 1:
        "-1"
        count -= 1
    return count

class ThreadClass(threading.Thread):
    def run(self):
        #print sq_table[0]
        testarr = [[0 for _ in range(3)] for _ in range(3)]
        #       Y  X
        testarr[0][1] = 1
        testarr[1][2] = 1
        testarr[2][2] = 1
        print testarr
        print neighbours(testarr, testarr[1][1])
        print [row[0:3] for row in sq_table[0:3]]
        print len(sq_table[0]) # cols 5
        print len(sq_table) # rows 4
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
                    xstop = x + 3
                    xind = 0
                if x == num_cols-2:
                    xstart = x - 1
                    xstop = x + 2
                    xind = 0
                if x == num_cols-1:
                    xstart = x - 2
                    xstop = x + 1
                    xind = 2
                if y == 0:
                    ystart = y
                    ystop = y + 3
                    yind = 0
                if y == num_rows-2:
                    ystart = y - 1
                    ystop = y + 2
                    yind = 0
                if y == num_rows-1:
                    ystart = y - 2
                    ystop = y + 1
                    yind = 2
                #subarr = [row[x:x+xrange] for row in sq_table[y:y+yrange]]
                subarr = [row[xstart:xstop] for row in sq_table[ystart:ystop]]
                n = neighbours(subarr, subarr[yind][xind])
                print n
        #for x in enumerate(sq_table):
            # enumerate returns tuple (i, val)
         #   for y in enumerate(sq_table[x[0]]):
                #print sq_table[x[0]][y[0]]
                
#                n = neighbours(sq_table, x[0], y[0], len(sq_table[0]), len(sq_table)) # XXX verify len
#                if                    
        print "start1"

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
