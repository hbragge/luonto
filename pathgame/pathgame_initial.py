from dataclasses import dataclass

XLEN = 4
YLEN = 5


@dataclass
class P:
    x: int
    y: int


class Board:
    def __init__(self):
        # start with empty board:
        # [
        #   [None, None, None, None, None], = 1st column, ie. self.board[0][y]
        #   [None, None, None, None, None], = 2nd column, ie. self.board[1][y] ...
        #   [None, None, None, None, None],
        #   [None, None, None, None, None]
        # ]
        self.board = [[None]*YLEN for _ in range(0, XLEN)]

    def insert(self, p, e):
        if p.x < 0 or p.y < 0 or p.x > XLEN or p.y > YLEN:
            print("add: invalid input parameters")
            return
        self.board[p.x][p.y] = e

    def print_board(self):
        print("")
        print("   ", end="")
        for x in range(XLEN):
            print(f"{x} ", end="")
        print("")
        for y in range(YLEN):
            print(f" {y} ", end="")
            for x in range(XLEN):
                p = self.board[x][y]
                if p:
                    print(f"{p} ", end="")
                else:
                    print("  ", end="")
            print("")

    def move(self, p1, p2):
        if p1.x < 0 or p1.y < 0 or p2.x < 0 or p2.y < 0 or p1.x > (XLEN - 1) or p1.y > (YLEN - 1) or p2.x > (XLEN - 1) or p2.y > (YLEN - 1):
            print("move: invalid input parameters")
            return
        if p1.x == p2.x and p1.y == p2.y:
            print(f"error: source and dest same {p1.x},{p1.y}")
            return
        # TODO


b = Board()
b.insert(P(0, 0), ".")
b.insert(P(1, 0), ".")
b.insert(P(2, 0), "R")
b.insert(P(3, 0), "R")

b.insert(P(0, 4), "G")
b.insert(P(1, 4), "R")
b.insert(P(2, 4), "G")
b.insert(P(3, 4), "G")

b.insert(P(1, 1), ".")
b.insert(P(1, 2), ".")
b.insert(P(2, 2), "R")
b.insert(P(1, 3), "G")

b.print_board()
#   0 1 2 3
# 0 . . R R
# 1   .
# 2   . R
# 3   G
# 4 G R G G

b.move(P(2, 2), P(0, 0))
b.print_board()
#   0 1 2 3
# 0 R . R R
# 1   .
# 2   . .
# 3   G
# 4 G R G G

b.move(P(1, 3), P(2, 2))
b.print_board()
#   0 1 2 3
# 0 R . R R
# 1   .
# 2   . G
# 3   .
# 4 G R G G

b.move(P(1, 4), P(1, 0))
b.print_board()
#   0 1 2 3
# 0 R R R R
# 1   .
# 2   . G
# 3   .
# 4 G . G G

b.move(P(2, 2), P(1, 4))
b.print_board()
#   0 1 2 3
# 0 R R R R
# 1   .
# 2   . .
# 3   .
# 4 G G G G
