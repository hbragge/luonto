use piston_window::types::Color;
use piston_window::*;

use drawing::draw_rectange;
use player::{Direction, Player};
use enemy::Enemy;

const EDGE_COLOR: Color = [0.72, 0.74, 0.8, 1.0];
const END_COLOR: Color = [0.80, 0.30, 0.30, 0.6];

const TICK_PERIOD: f64 = 0.15; // seconds
const RESTART_TIME: f64 = 0.8;

pub struct Game {
    player: Player,
    enemy: Enemy,

    width: i32,
    height: i32,

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

        if dir.unwrap() == self.player.player_direction() {
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

    fn is_player_alive(&self, dir: Option<Direction>) -> bool {
        let (next_x, next_y) = self.player.next_player_position(dir);

        // check that player within border
        next_x > 0 && next_y > 0 && next_x < self.width - 1 && next_y < self.height - 1
    }

    fn update_player(&mut self, dir: Option<Direction>) {
        if self.is_player_alive(dir) {
            let next_pos = self.player.next_player_position(dir);
            self.player.move_forward(dir);
            self.enemy.follow(next_pos);
            if self.enemy.is_touching(next_pos) {
                self.is_game_over = true;
            }
        } else {
            self.is_game_over = true;
        }
        self.wait_time = 0.0;
    }

    fn restart(&mut self) {
        self.player = Player::new(2, 2);
        self.enemy = Enemy::new(self.width - 3, self.height - 3);
        self.wait_time = 0.0;
        self.is_game_over = false;
    }
}