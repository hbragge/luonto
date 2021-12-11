use piston_window::types::Color;
use piston_window::*;
extern crate minifb;
extern crate png;
use minifb::{ScaleMode, Window, WindowOptions};
use crate::common::{Block, follow_pos};
use crate::drawing::draw_rectange;
use crate::player::{Direction, Player};
use crate::enemy::Enemy;

const EDGE_COLOR: Color = [0.72, 0.74, 0.8, 1.0];
const END_COLOR: Color = [0.80, 0.30, 0.30, 0.6];

const TICK_PERIOD: f64 = 0.10; // seconds
const RESTART_TIME: f64 = 0.8;

fn _display_png() {
    use std::fs::File;
    // The decoder is a build for reader and can be used to set various decoding options
    // via `Transformations`. The default output transformation is `Transformations::EXPAND
    // | Transformations::STRIP_ALPHA`.
    let decoder = png::Decoder::new(File::open("resources/gameover.png").unwrap());
    let mut reader = decoder.read_info().unwrap();
    // Allocate the output buffer.
    let mut buf = vec![0; reader.output_buffer_size()];
    // Read the next frame. Currently this function should only called once.
    // The default options
    reader.next_frame(&mut buf).unwrap();
    // convert buffer to u32

    let u32_buffer: Vec<u32> = buf
        .chunks(4) // 4 for RBGA
        .map(|v| ((v[0] as u32) << 16) | ((v[1] as u32) << 8) | v[2] as u32)
        .collect();

    let mut window = Window::new(
        "Noise Test - Press ESC to exit",
        reader.info().width as usize,
        reader.info().height as usize,
        WindowOptions {
            resize: true,
            scale_mode: ScaleMode::Center,
            ..WindowOptions::default()
        },
    )
    .expect("Unable to open Window");

    while window.is_open() && !window.is_key_down(minifb::Key::Escape) {
        window
            .update_with_buffer(
                &u32_buffer,
                reader.info().width as usize,
                reader.info().height as usize,
            )
            .unwrap();
    }
}

pub struct Game {
    player: Player,
    enemy: Enemy,

    width: i32,
    height: i32,
    obs0_x: i32,
    obs0_y: i32,
    obs1_x: i32,
    obs1_y: i32,
    obs_height: i32,

    is_game_over: bool,
    // when game running, time is the time since the previous move,
    // otherwise time since the end of the game
    wait_time: f64,
}

impl Game {
    pub fn new(width: i32, height: i32) -> Game {
        Game {
            player: Player::new(2, 2),
            enemy: Enemy::new(width - 3, height - 3),
            wait_time: 0.0,
            width: width,
            height: height,
            obs0_x: width / 4,
            obs0_y: height / 4,
            obs1_x: 3 * (width / 4),
            obs1_y: height / 2,
            obs_height: height / 3,
            is_game_over: false,
        }
    }

    pub fn key_pressed(&mut self, key: Key) {
        if self.is_game_over {
            return;
        }

        let dir = match key {
            Key::Up => Some(Direction::Up),
            Key::Down => Some(Direction::Down),
            Key::Left => Some(Direction::Left),
            Key::Right => Some(Direction::Right),
            _ => None,
        };

        if dir == None || dir.unwrap() == self.player.direction() {
            return;
        }

        // check if hit the border
        self.update_player(dir);
    }

    pub fn draw(&self, con: &Context, g: &mut G2d) {
        //_display_png();
        self.player.draw(con, g);
        self.enemy.draw(con, g);

        draw_rectange(EDGE_COLOR, 0, 0, self.width, 1, con, g);
        draw_rectange(EDGE_COLOR, 0, self.height - 1, self.width, 1, con, g);
        draw_rectange(EDGE_COLOR, 0, 0, 1, self.height, con, g);
        draw_rectange(EDGE_COLOR, self.width - 1, 0, 1, self.height, con, g);

        draw_rectange(EDGE_COLOR, self.obs0_x, self.obs0_y, 1, self.obs_height, con, g);
        draw_rectange(EDGE_COLOR, self.obs1_x, self.obs1_y, 1, self.obs_height, con, g);

        if self.is_game_over {
            draw_rectange(END_COLOR, 0, 0, self.width, self.height, con, g);
        }
    }

    pub fn update(&mut self, delta_time: f64) {
        self.wait_time += delta_time;

        if self.is_game_over {
            if self.wait_time > RESTART_TIME {
                self.restart();
            }
            return;
        }

        // move player
        if self.wait_time > TICK_PERIOD {
            self.update_player(None);
        }
    }

    fn is_free(&self, pos: Block) -> bool {
        let within_borders = pos.x > 0 && pos.y > 0 && pos.x < self.width - 1 && pos.y < self.height - 1;
        let in_obs0 = pos.x == self.obs0_x && pos.y >= self.obs0_y && pos.y < (self.obs0_y + self.obs_height);
        let in_obs1 = pos.x == self.obs1_x && pos.y >= self.obs1_y && pos.y < (self.obs1_y + self.obs_height);
        within_borders && !(in_obs0 || in_obs1)
    }

    fn is_able_to_move(&self, dir: Option<Direction>) -> bool {
        self.is_free(self.player.next_position(dir))
    }

    fn is_enemy_able_to_move(&self, pos: Block) -> bool {
        self.is_free(self.enemy.next_position(pos))
    }

    fn follow_los(&self, pos: Block) -> Option<Block> {
        let player_pos = self.player.position();
        let new_pos = follow_pos(pos, player_pos);
        if new_pos == player_pos {
            return Some(player_pos)
        } else if self.is_free(new_pos) {
            return self.follow_los(new_pos)
        } else {
            return None
        }
    }

    fn is_player_in_los(&self, pos: Block) -> bool {
        let los = self.follow_los(pos);
        if let Some(_) = los {
            return true;
        } else {
            return false;
        }
    }

    fn update_player(&mut self, dir: Option<Direction>) {
        if self.is_able_to_move(dir) {
            self.player.move_forward(dir);
        }
        let curr_pos = self.player.position();
        if self.is_player_in_los(self.enemy.position()) && self.is_enemy_able_to_move(curr_pos) {
            self.enemy.follow(curr_pos);
        }
        if self.enemy.is_touching(curr_pos) {
            self.is_game_over = true;
        }
        self.wait_time = 0.0;
    }

    fn restart(&mut self) {
        self.player.restart();
        self.enemy.restart();
        self.wait_time = 0.0;
        self.is_game_over = false;
    }
}
