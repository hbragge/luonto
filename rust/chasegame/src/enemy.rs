use piston_window::types::Color;
use piston_window::Context;
use piston_window::G2d;

use drawing::draw_block;

const ENEMY_COLOR: Color = [0.8, 0.25, 0.1, 1.0];

#[derive(Debug, Clone)]
struct Block {
    x: i32,
    y: i32,
}

pub struct Enemy {
    pos: Block,
    move_count: u32,
}

impl Enemy {
    pub fn new(init_x: i32, init_y: i32) -> Enemy {
        let pos: Block = Block {
            x: init_x,
            y: init_y,
        };

        Enemy {
            pos: pos,
            move_count: 0,
        }
    }

    pub fn draw(&self, con: &Context, g: &mut G2d) {
        draw_block(ENEMY_COLOR, self.pos.x, self.pos.y, con, g);
    }

    pub fn follow(&mut self, player_pos: (i32, i32)) {
        self.move_count += 1;
        if self.move_count % 2 == 0 {
            return;
        }
        if player_pos.0 > self.pos.x {
            self.pos.x += 1;
        } else if player_pos.0 < self.pos.x {
            self.pos.x -= 1;
        }
        if player_pos.1 > self.pos.y {
            self.pos.y += 1;
        } else if player_pos.1 < self.pos.y {
            self.pos.y -= 1;
        }
    }

    pub fn is_touching(&mut self, player_pos: (i32, i32)) -> bool {
        let dist_x = (self.pos.x - player_pos.0).abs();
        let dist_y = (self.pos.y - player_pos.1).abs();
        if dist_x + dist_y <= 1 {
            return true;
        } else {
            return false;
        }
    }
}
