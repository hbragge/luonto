use piston_window::rectangle;
use piston_window::types::Color;
use piston_window::Context;
use piston_window::G2d;

const BLOCK_SZ: f64 = 26.0;

pub fn to_gui_coord(game_coord: u32) -> f64 {
    (game_coord as f64) * BLOCK_SZ
}

pub fn to_gui_coord_u32(game_coord: u32) -> u32 {
    to_gui_coord(game_coord) as u32
}

pub fn draw_block(color: Color, x: u32, y: u32, con: &Context, g: &mut G2d) {
    let gui_x = to_gui_coord(x);
    let gui_y = to_gui_coord(y);

    rectangle(
        color,
        [gui_x, gui_y, BLOCK_SZ, BLOCK_SZ],
        con.transform,
        g,
    );
}

pub fn draw_rectange(
    color: Color,
    start_x: u32,
    start_y: u32,
    width: u32,
    height: u32,
    con: &Context,
    g: &mut G2d,
) {
    let gui_start_x = to_gui_coord(start_x);
    let gui_start_y = to_gui_coord(start_y);

    rectangle(
        color,
        [
            gui_start_x,
            gui_start_y,
            BLOCK_SZ * (width as f64),
            BLOCK_SZ * (height as f64),
        ],
        con.transform,
        g,
    );
}
