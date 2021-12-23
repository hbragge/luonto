use crate::common::*;
use crate::drawing::draw_block;
use piston_window::types::Color;
use piston_window::Context;
use piston_window::G2d;
use soloud::*;

const ENEMY_COLOR: Color = [0.8, 0.25, 0.1, 1.0];

static REACT1: &[u8] = include_bytes!("../resources/react1.wav");
static REACT2: &[u8] = include_bytes!("../resources/react2.wav");
static REACT3: &[u8] = include_bytes!("../resources/react3.wav");
static REACT4: &[u8] = include_bytes!("../resources/react4.wav");
static REACT5: &[u8] = include_bytes!("../resources/react5.wav");

pub struct Enemy {
    pos: Block,
    init_pos: Block,
    move_count: u32,
    game_count: u32,
    yelling: bool,
    sl: Soloud,
    wav: audio::Wav,
}

impl Enemy {
    pub fn new(init_x: u32, init_y: u32) -> Enemy {
        let pos: Block = Block {
            x: init_x,
            y: init_y,
        };

        Enemy {
            pos: pos.clone(),
            init_pos: pos,
            move_count: 0,
            game_count: 0,
            yelling: false,
            sl: Soloud::default().unwrap(),
            wav: audio::Wav::default(),
        }
    }

    pub fn restart(&mut self) {
        self.pos = self.init_pos.clone();
        self.move_count = 0;
        self.game_count += 1;
        self.yelling = false;
    }

    pub fn draw(&self, con: &Context, g: &mut G2d) {
        draw_block(ENEMY_COLOR, self.pos.x, self.pos.y, con, g);
    }

    pub fn position(&self) -> Block {
        self.pos
    }

    pub fn next_position(&self, player_pos: Block) -> Block {
        follow_pos(self.pos, player_pos)
    }

    pub fn follow(&mut self, player_pos: Block) {
        if self.move_count == 0 {
            self.start_react_sound();
        }
        if self.yelling {
            self.play_react_sound();
        }
        if self.move_count == u32::max_value() {
            self.move_count = 1;
        } else {
            self.move_count += 1;
        }
        if self.move_count % 2 == 0 {
            return;
        }
        self.pos = follow_pos(self.pos, player_pos)
    }

    pub fn is_touching(&self, player_pos: Block) -> bool {
        let dist_x = dist(self.pos.x, player_pos.x);
        let dist_y = dist(self.pos.y, player_pos.y);
        if dist_x + dist_y <= 1 {
            return true;
        } else {
            return false;
        }
    }

    fn start_react_sound(&mut self) {
        match self.game_count % 5 {
            4 => self.wav.load_mem(REACT5).unwrap(),
            3 => self.wav.load_mem(REACT4).unwrap(),
            2 => self.wav.load_mem(REACT3).unwrap(),
            1 => self.wav.load_mem(REACT2).unwrap(),
            _ => self.wav.load_mem(REACT1).unwrap(),
        }

        self.sl.play(&self.wav);
        self.yelling = true;
    }

    fn play_react_sound(&mut self) {
        if self.sl.voice_count() > 0 {
            std::thread::sleep(std::time::Duration::from_millis(10));
        } else {
            self.yelling = false;
        }
    }
}
