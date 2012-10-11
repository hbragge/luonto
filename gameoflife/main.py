import sys
import pygame
import time
import threading
pygame.init()

WIN_WIDTH = 800
WIN_HEIGHT = 480

LEFT = 1
RIGHT = 0
LEFTBUTTON = 0
RIGHTBUTTON = 2

sq_size = 20
sq_table = [[0 for _ in range((WIN_WIDTH/sq_size))] for _ in range((WIN_HEIGHT/sq_size))]
sq_table_new = [[0 for _ in range((WIN_WIDTH/sq_size))] for _ in range((WIN_HEIGHT/sq_size))]

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

class ThreadClass(threading.Thread):
    def run(self):
        #print sq_table[0]
        testarr = [[0 for _ in range(5)] for _ in range(3)]
        testarr[0][1] = 1
        testarr[1][2] = 1
        testarr[2][3] = 1
        print testarr
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
