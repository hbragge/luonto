fn do_combi(string: &[u8], res: &mut String, start: usize) {
    for i in start..string.len() {
        res.push(string[i] as char);   
        println!("{}", res);
        if i < string.len() - 1 {
            do_combi(string, res, i + 1);
        }
        res.remove(res.len() - 1);
    }
}

fn combi(string: &str) {
    let mut res: String = "".to_string();
    do_combi(string.as_bytes(), &mut res, 0);
}

fn main() {
    combi("abc");
}
