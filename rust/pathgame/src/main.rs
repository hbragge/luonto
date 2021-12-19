use std::fmt;

const X: usize = 4;
const Y: usize = 5;
static CROSS_COL: usize = 1;

#[derive(Clone, Copy, PartialEq)]
enum F {
    U,
    E,
    R,
    G,
}

#[derive(Clone, Copy, PartialEq)]
struct Pos {
    x: usize,
    y: usize,
}

impl fmt::Display for F {
    fn fmt(&self, f: &mut fmt::Formatter) -> fmt::Result {
        match *self {
            F::U => write!(f, "."),
            F::E => write!(f, "E"),
            F::R => write!(f, "R"),
            F::G => write!(f, "G"),
        }
    }
}

fn draw(b: &[[F; Y]]) {
    print!(" ");
    for x in 0..X {
        print!(" {}", x);
    }
    print!("\n0 ");
    for y in 0..Y {
        for x in 0..X {
            print!("{:} ", b[x][y]);
        }
        println!("");
        if y < Y - 1 {
            print!("{} ", y + 1);
        }
    }
    println!("");
}

fn find_path(b: &[[F; Y]], from: Pos, to: Pos) -> bool {
    if to.y > from.y {
        if b[from.x][from.y + 1] == F::U {
            // need crossing south
            if CROSS_COL > from.x {
                if b[from.x + 1][from.y] == F::E {
                    return find_path(
                        b,
                        Pos {
                            x: from.x + 1,
                            y: from.y,
                        },
                        to,
                    );
                } else {
                    return false;
                }
            } else if CROSS_COL < from.x {
                if b[from.x - 1][from.y] == F::E {
                    return find_path(
                        b,
                        Pos {
                            x: from.x - 1,
                            y: from.y,
                        },
                        to,
                    );
                } else {
                    return false;
                }
            } else {
                panic!("invalid board");
            }
        } else if b[from.x][from.y + 1] == F::E {
            return find_path(
                b,
                Pos {
                    x: from.x,
                    y: from.y + 1,
                },
                to,
            );
        } else {
            return false;
        }
    } else if to.y < from.y {
        if b[from.x][from.y - 1] == F::U {
            // need crossing north
            if CROSS_COL > from.x {
                if b[from.x + 1][from.y] == F::E {
                    return find_path(
                        b,
                        Pos {
                            x: from.x + 1,
                            y: from.y,
                        },
                        to,
                    );
                } else {
                    return false;
                }
            } else if CROSS_COL < from.x {
                if b[from.x - 1][from.y] == F::E {
                    return find_path(
                        b,
                        Pos {
                            x: from.x - 1,
                            y: from.y,
                        },
                        to,
                    );
                } else {
                    return false;
                }
            } else {
                panic!("invalid board");
            }
        } else if b[from.x][from.y - 1] == F::E {
            return find_path(
                b,
                Pos {
                    x: from.x,
                    y: from.y - 1,
                },
                to,
            );
        } else {
            return false;
        }
    } else if to.x > from.x {
        // correct row
        if b[from.x + 1][from.y] == F::E {
            return find_path(
                b,
                Pos {
                    x: from.x + 1,
                    y: from.y,
                },
                to,
            );
        } else {
            return false;
        }
    } else if to.x < from.x {
        if b[from.x - 1][from.y] == F::E {
            return find_path(
                b,
                Pos {
                    x: from.x - 1,
                    y: from.y,
                },
                to,
            );
        } else {
            return false;
        }
    } else {
        // correct row and column
        return true;
    }
}

fn do_move(b: &mut [[F; Y]], from: Pos, to: Pos) {
    if from == to {
        println!("from and to are the same");
        return;
    }
    if !(b[from.x][from.y] == F::R || b[from.x][from.y] == F::G) {
        println!("from has no piece");
        return;
    }
    if b[to.x][to.y] != F::E {
        println!("to not empty");
        return;
    }
    if !find_path(b, from, to) {
        println!("path not found");
        return;
    }
    b[to.x][to.y] = b[from.x][from.y];
    b[from.x][from.y] = F::E;
}

fn main() {
    let mut b: [[F; Y]; X] = [
        [F::R, F::U, F::U, F::U, F::G],
        [F::R, F::E, F::E, F::E, F::G],
        [F::R, F::U, F::E, F::U, F::G],
        [F::R, F::U, F::U, F::U, F::G],
    ];

    draw(&mut b);
    do_move(&mut b, Pos { x: 1, y: 4 }, Pos { x: 2, y: 2 });
    draw(&mut b);
    do_move(&mut b, Pos { x: 1, y: 0 }, Pos { x: 1, y: 4 });
    draw(&mut b);
    do_move(&mut b, Pos { x: 0, y: 0 }, Pos { x: 1, y: 3 });
    draw(&mut b);
    do_move(&mut b, Pos { x: 2, y: 2 }, Pos { x: 0, y: 0 });
    draw(&mut b);

    // invalid
    do_move(&mut b, Pos { x: 1, y: 0 }, Pos { x: 1, y: 1 });
    do_move(&mut b, Pos { x: 0, y: 1 }, Pos { x: 1, y: 1 });
    do_move(&mut b, Pos { x: 1, y: 3 }, Pos { x: 1, y: 3 });
    do_move(&mut b, Pos { x: 0, y: 4 }, Pos { x: 2, y: 2 });
    do_move(&mut b, Pos { x: 1, y: 4 }, Pos { x: 0, y: 4 });
    do_move(&mut b, Pos { x: 0, y: 0 }, Pos { x: 0, y: 1 });
}
