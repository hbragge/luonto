use piston_window::types::Color;
use piston_window::Context;
use piston_window::G2d;

use drawing::draw_block;

const PLAYER_COLOR: Color = [0.2, 0.65, 0.5, 1.0];

#[derive(Clone, Copy, PartialEq)]
pub enum Direction {
    Up,
    Down,
    Left,
    Right,
}

#[derive(Debug, Clone)]
struct Block {
    x: i32,
    y: i32,
}

pub struct Player {
    direction: Direction,
    pos: Block,
}

impl Player {
    pub fn new(init_x: i32, init_y: i32) -> Player {
        let pos: Block = Block {
            x: init_x,
            y: init_y,
        };

        Player {
            direction: Direction::Right,
            pos: pos,
        }
    }

    pub fn draw(&self, con: &Context, g: &mut G2d) {
        draw_block(PLAYER_COLOR, self.pos.x, self.pos.y, con, g);
    }

    pub fn move_forward(&mut self, dir: Option<Direction>) {
        match dir {
            Some(d) => self.direction = d,
            None => {}
        }

        let (last_x, last_y): (i32, i32) = (self.pos.x, self.pos.y);

        self.pos = match self.direction {
            Direction::Up => Block {x: last_x, y: last_y - 1},
            Direction::Down => Block {x: last_x, y: last_y + 1},
            Direction::Left => Block {x: last_x - 1, y: last_y},
            Direction::Right => Block {x: last_x + 1, y: last_y},
        };
    }

    pub fn player_direction(&self) -> Direction {
        self.direction
    }

    pub fn next_player_position(&self, dir: Option<Direction>) -> (i32, i32) {
        let (head_x, head_y): (i32, i32) = (self.pos.x, self.pos.y);

        let mut moving_dir = self.direction;
        match dir {
            Some(d) => moving_dir = d,
            None => {}
        }

        match moving_dir {
            Direction::Up => (head_x, head_y - 1),
            Direction::Down => (head_x, head_y + 1),
            Direction::Left => (head_x - 1, head_y),
            Direction::Right => (head_x + 1, head_y),
        }
    }
}
