extern crate piston_window;
extern crate png;
extern crate soloud;

mod drawing;
mod game;
mod common;
mod player;
mod enemy;

use piston_window::types::Color;
use piston_window::*;

use crate::drawing::to_gui_coord_u32;
use crate::game::Game;

const BACKGROUND_COLOR: Color = [0.2, 0.3, 0.4, 1.0];

fn main() {
    let (width, height) = (25, 25);

    let mut window: PistonWindow = WindowSettings::new(
        "Chaser game",
        [to_gui_coord_u32(width), to_gui_coord_u32(height)],
    )
    .exit_on_esc(true)
    .build()
    .unwrap();

    let mut game = Game::new(width, height);

    while let Some(event) = window.next() {
        if let Some(Button::Keyboard(key)) = event.press_args() {
            game.key_pressed(key);
        }

        window.draw_2d(&event, |c, g, _| {
            clear(BACKGROUND_COLOR, g);
            game.draw(&c, g);
        });

        event.update(|arg| {
            game.update(arg.dt);
        });
    }
}
