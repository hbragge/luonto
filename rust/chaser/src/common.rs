#[derive(Debug, Clone, Copy, PartialEq)]
pub struct Block {
    pub x: u32,
    pub y: u32,
}

pub fn follow_pos(curr: Block, dest: Block) -> Block {
    let mut res = curr;
    if dest.x > res.x {
        res.x += 1;
    } else if dest.x < res.x {
        res.x -= 1;
    }
    if dest.y > res.y {
        res.y += 1;
    } else if dest.y < res.y {
        res.y -= 1;
    }
    res
}

pub fn dist(c1: u32, c2: u32) -> u32 {
    if c1 > c2 {
        return c1 - c2;
    } else {
        return c2 - c1;
    }
}
