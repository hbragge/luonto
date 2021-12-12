package main

import "fmt"

const X = 4
const Y = 5
const CROSS_COL = 1

type Field int

const (
	F_U Field = 0
	F_E Field = 1
	F_R Field = 2
	F_G Field = 3
)

var b = [X][Y]Field{
	{F_R, F_U, F_U, F_U, F_G},
	{F_R, F_E, F_E, F_E, F_G},
	{F_R, F_U, F_E, F_U, F_G},
	{F_R, F_U, F_U, F_U, F_G},
}

func field_to_str(f Field) string {
	switch f {
	case F_U:
		return "."
	case F_E:
		return "o"
	case F_R:
		return "R"
	default:
		return "G"
	}

}

type pos struct {
	x int
	y int
}

func draw() {
	fmt.Printf(" ")
	for x := 0; x < X; x++ {
		fmt.Printf(" %d", x)
	}
	fmt.Printf("\n0 ")
	for y := 0; y < Y; y++ {
		for x := 0; x < X; x++ {
			fmt.Printf(field_to_str(b[x][y]))
			fmt.Printf(" ")
		}
		fmt.Printf("\n")
		if y < Y-1 {
			fmt.Printf("%d ", y+1)
		}
	}
	fmt.Printf("\n")
}

func find_path(from pos, to pos) bool {
	if to.y > from.y {
		if b[from.x][from.y+1] == F_U {
			// need crossing south
			if CROSS_COL > from.x {
				if b[from.x+1][from.y] == F_E {
					return find_path(pos{from.x + 1, from.y}, to)
				} else {
					return false
				}
			} else if CROSS_COL < from.x {
				if b[from.x-1][from.y] == F_E {
					return find_path(pos{from.x - 1, from.y}, to)
				} else {
					return false
				}
			} else {
				panic("invalid board")
			}
		} else if b[from.x][from.y+1] == F_E {
			return find_path(pos{from.x, from.y + 1}, to)
		} else {
			return false
		}
	} else if to.y < from.y {
		if b[from.x][from.y-1] == F_U {
			// need crossing north
			if CROSS_COL > from.x {
				if b[from.x+1][from.y] == F_E {
					return find_path(pos{from.x + 1, from.y}, to)
				} else {
					return false
				}
			} else if CROSS_COL < from.x {
				if b[from.x-1][from.y] == F_E {
					return find_path(pos{from.x - 1, from.y}, to)
				} else {
					return false
				}
			} else {
				panic("invalid board")
			}
		} else if b[from.x][from.y-1] == F_E {
			return find_path(pos{from.x, from.y - 1}, to)
		} else {
			return false
		}
	} else if to.x > from.x {
		// correct row
		if b[from.x+1][from.y] == F_E {
			return find_path(pos{from.x + 1, from.y}, to)
		} else {
			return false
		}
	} else if to.x < from.x {
		if b[from.x-1][from.y] == F_E {
			return find_path(pos{from.x - 1, from.y}, to)
		} else {
			return false
		}
	} else {
		// correct row and column
		return true
	}
}

func move(from pos, to pos) {
	if from == to {
		fmt.Printf("from and to are the same\n")
		return
	}
	if !(b[from.x][from.y] == F_R || b[from.x][from.y] == F_G) {
		fmt.Printf("from has no piece\n")
		return
	}
	if b[to.x][to.y] != F_E {
		fmt.Printf("to not empty\n")
		return
	}
	if !find_path(from, to) {
		fmt.Printf("path not found\n")
		return
	}
	b[to.x][to.y] = b[from.x][from.y]
	b[from.x][from.y] = F_E
}

func main() {
	draw()
	move(pos{1, 4}, pos{2, 2})
	draw()
	move(pos{1, 0}, pos{1, 4})
	draw()
	move(pos{0, 0}, pos{1, 3})
	draw()
	move(pos{2, 2}, pos{0, 0})
	draw()

	// invalid
	move(pos{1, 0}, pos{1, 1})
	move(pos{0, 1}, pos{1, 1})
	move(pos{1, 3}, pos{1, 3})
	move(pos{0, 4}, pos{2, 2})
	move(pos{1, 4}, pos{0, 4})
	move(pos{0, 0}, pos{0, 1})
}
