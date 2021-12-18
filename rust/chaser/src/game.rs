use crate::common::{follow_pos, Block};
use crate::drawing::{draw_block, draw_rectange};
use crate::enemy::Enemy;
use crate::player::{Direction, Player};
use piston_window::types::Color;
use piston_window::*;
use rand::Rng;

const EDGE_COLOR: Color = [0.72, 0.74, 0.8, 1.0];
const GOLD_COLOR: Color = [0.9, 0.9, 0.1, 1.0];
const TICK_PERIOD: f64 = 0.08; // seconds
const RESTART_TIME: f64 = 1.2;

pub struct Game {
    player: Player,
    enemy: Enemy,
    gold: Block,

    width: u32,
    height: u32,
    obs0_x: u32,
    obs0_y: u32,
    obs1_x: u32,
    obs1_y: u32,
    obs_height: u32,
    score: u32,

    is_game_over: bool,
    // when game running, time is the time since the previous move,
    // otherwise time since the end of the game
    wait_time: f64,
    rng: rand::rngs::ThreadRng,
}

impl Game {
    pub fn new(width: u32, height: u32) -> Game {
        Game {
            player: Player::new(2, 2),
            enemy: Enemy::new(width - 3, height - 3),
            gold: Block { x: 5, y: 5 },
            wait_time: 0.0,
            width: width,
            height: height,
            obs0_x: width / 4,
            obs0_y: height / 4,
            obs1_x: 3 * (width / 4),
            obs1_y: height / 2,
            obs_height: height / 3,
            score: 0,
            is_game_over: false,
            rng: rand::thread_rng(),
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
        self.player.draw(con, g);
        self.enemy.draw(con, g);

        draw_block(GOLD_COLOR, self.gold.x, self.gold.y, con, g);
        draw_rectange(EDGE_COLOR, 0, 0, self.width, 1, con, g);
        draw_rectange(EDGE_COLOR, 0, self.height - 1, self.width, 1, con, g);
        draw_rectange(EDGE_COLOR, 0, 0, 1, self.height, con, g);
        draw_rectange(EDGE_COLOR, self.width - 1, 0, 1, self.height, con, g);

        draw_rectange(
            EDGE_COLOR,
            self.obs0_x,
            self.obs0_y,
            1,
            self.obs_height,
            con,
            g,
        );
        draw_rectange(
            EDGE_COLOR,
            self.obs1_x,
            self.obs1_y,
            1,
            self.obs_height,
            con,
            g,
        );
    }

    pub fn update(&mut self, delta_time: f64) -> bool {
        self.wait_time += delta_time;

        if self.is_game_over {
            if self.wait_time > RESTART_TIME {
                self.restart();
            }
            return false;
        }

        // move player
        if self.wait_time > TICK_PERIOD {
            self.update_player(None);
        }
        true
    }

    pub fn get_score(&self) -> u32 {
        self.score
    }

    fn is_free(&self, pos: Block) -> bool {
        let within_borders =
            pos.x > 0 && pos.y > 0 && pos.x < self.width - 1 && pos.y < self.height - 1;
        let in_obs0 =
            pos.x == self.obs0_x && pos.y >= self.obs0_y && pos.y < (self.obs0_y + self.obs_height);
        let in_obs1 =
            pos.x == self.obs1_x && pos.y >= self.obs1_y && pos.y < (self.obs1_y + self.obs_height);
        within_borders && !(in_obs0 || in_obs1)
    }

    fn is_able_to_move(&self, dir: Option<Direction>) -> bool {
        self.is_free(self.player.next_position(dir))
    }

    fn is_enemy_able_to_move(&self, pos: Block) -> bool {
        self.is_free(self.enemy.next_position(pos))
    }

    fn random_pos(&mut self) -> Block {
        loop {
            let pos = Block {
                x: self.rng.gen_range(0..(self.width - 1)),
                y: self.rng.gen_range(0..(self.height - 1)),
            };
            if self.is_free(pos) {
                return pos;
            }
        }
    }

    fn follow_los(&self, pos: Block) -> Option<Block> {
        let player_pos = self.player.position();
        let new_pos = follow_pos(pos, player_pos);
        if new_pos == player_pos {
            return Some(player_pos);
        } else if self.is_free(new_pos) {
            return self.follow_los(new_pos);
        } else {
            return None;
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
        if curr_pos == self.gold {
            self.score += 1;
            self.gold = self.random_pos();
        }
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
