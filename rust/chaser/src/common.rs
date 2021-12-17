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
