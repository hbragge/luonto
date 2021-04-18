mod account;

fn main() {
    use account::account::Storage;
    use account::account::Account;
    let mut acc0 = Account {
        owner: String::from("Hannu"),
        id: 0,
        balance: 150,
    };
    let mut acc1 = Account {
        owner: String::from("Jooseppi"),
        id: 1,
        balance: 20,
    };
    if acc1.transfer_from(&mut acc0, 150) {
        println!("success {} {}", acc0.balance, acc1.balance);
    } else {
        println!("failed");
    }
}
