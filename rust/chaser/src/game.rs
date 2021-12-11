use piston_window::types::Color;
use piston_window::*;

use crate::drawing::draw_rectange;
use crate::player::{Direction, Player};
use crate::enemy::Enemy;

const EDGE_COLOR: Color = [0.72, 0.74, 0.8, 1.0];
const END_COLOR: Color = [0.80, 0.30, 0.30, 0.6];

const TICK_PERIOD: f64 = 0.15; // seconds
const RESTART_TIME: f64 = 0.8;

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

        if dir == None || dir.unwrap() == self.player.player_direction() {
            return;
        }

        // check if hit the border
        self.update_player(dir);
    }

    pub fn draw(&self, con: &Context, g: &mut G2d) {
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

    fn is_free(&self, x: i32, y: i32) -> bool {
        let within_borders = x > 0 && y > 0 && x < self.width - 1 && y < self.height - 1;
        let in_obs0 = x == self.obs0_x && y >= self.obs0_y && y < (self.obs0_y + self.obs_height);
        let in_obs1 = x == self.obs1_x && y >= self.obs1_y && y < (self.obs1_y + self.obs_height);
        within_borders && !(in_obs0 || in_obs1)
    }

    fn is_able_to_move(&self, dir: Option<Direction>) -> bool {
        let (next_x, next_y) = self.player.next_position(dir);
        self.is_free(next_x, next_y)
    }

    fn is_enemy_able_to_move(&self, pos: (i32, i32)) -> bool {
        let (next_x, next_y) = self.enemy.next_position(pos);
        self.is_free(next_x, next_y)
    }

    fn update_player(&mut self, dir: Option<Direction>) {
        if self.is_able_to_move(dir) {
            self.player.move_forward(dir);
        }
        let curr_pos = self.player.player_position();
        if self.is_enemy_able_to_move(curr_pos) {
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
