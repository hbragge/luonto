use piston_window::types::Color;
use piston_window::Context;
use piston_window::G2d;
use crate::drawing::draw_block;
use crate::common::{Block};

const PLAYER_COLOR: Color = [0.2, 0.65, 0.5, 1.0];

#[derive(Clone, Copy, PartialEq)]
pub enum Direction {
    Up,
    Down,
    Left,
    Right,
}

pub struct Player {
    direction: Direction,
    pos: Block,
    init_pos: Block,
}

impl Player {
    pub fn new(init_x: i32, init_y: i32) -> Player {
        let pos: Block = Block {
            x: init_x,
            y: init_y,
        };

        Player {
            direction: Direction::Right,
            pos: pos.clone(),
            init_pos: pos,
        }
    }

    pub fn restart(&mut self) {
        self.direction = Direction::Right;
        self.pos = self.init_pos.clone();
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

    pub fn direction(&self) -> Direction {
        self.direction
    }

    pub fn position(&self) -> Block {
        self.pos
    }

    pub fn next_position(&self, dir: Option<Direction>) -> Block {
        let mut moving_dir = self.direction;
        if let Some(d) = dir {
            moving_dir = d;
        }

        let mut res = self.pos;
        match moving_dir {
            Direction::Up => res.y -= 1,
            Direction::Down => res.y += 1,
            Direction::Left => res.x -= 1,
            Direction::Right => res.x += 1,
        }
        res
    }
}
