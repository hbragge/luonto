extern crate piston_window;
extern crate soloud;
extern crate image;
extern crate png;

mod drawing;
mod game;
mod common;
mod player;
mod enemy;

use piston_window::types::Color;
use piston_window::*;

use crate::drawing::to_gui_coord_u32;
use crate::game::Game;

const BACKGROUND_COLOR: Color = [0.1, 0.1, 0.1, 1.0];

fn main() {
    let (width, height) = (36, 36);
    let x_size = to_gui_coord_u32(width);
    let y_size = to_gui_coord_u32(height);

    let mut window: PistonWindow = WindowSettings::new("Chaser game", [x_size, y_size])
        .exit_on_esc(true)
        .build()
        .unwrap();

    let mut game = Game::new(width, height);

    use std::fs::File;
    let decoder = png::Decoder::new(File::open("resources/gameover.png").unwrap());
    let mut reader = decoder.read_info().unwrap();
    let mut buf = vec![0; reader.output_buffer_size()];
    reader.next_frame(&mut buf).unwrap();

    let mut texture_context = TextureContext {
        factory: window.factory.clone(),
        encoder: window.factory.create_command_buffer().into()
    };

    let mut img = image::ImageBuffer::new(x_size, y_size);
    let mut col = 0;
    let mut row = 0;
    for i in (0..buf.len()).step_by(4) {
        img.put_pixel(col, row, image::Rgba([buf[i], buf[i + 1], buf[i + 2], 127]));
        if col == reader.info().width - 1 {
            if row == reader.info().height - 1 {
                break;
            } else {
                col = 0;
                row += 1;
            }
        } else {
            col += 1;
        }
    }
    let tx = Texture::from_image(
        &mut texture_context,
        &img,
        &TextureSettings::new()).unwrap();

    let mut running = true;

    while let Some(event) = window.next() {
        if let Some(Button::Keyboard(key)) = event.press_args() {
            game.key_pressed(key);
        }

        window.draw_2d(&event, |c, g, _| {
            clear(BACKGROUND_COLOR, g);
            game.draw(&c, g);
            if !running {
                image(&tx, c.transform.scale(1.1, 1.3), g);
            }
        });

        event.update(|arg| {
            running = game.update(arg.dt);
        });
    }
}
