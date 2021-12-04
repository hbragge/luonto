pub struct Account {
    pub owner: String,
    pub id: u32,
    pub balance: u32,
}

pub trait Storage {
    fn withdraw(&mut self, amount: u32);
    fn deposit(&mut self, amount: u32);
    fn transfer_from(&mut self, from: &mut Account, amount: u32) -> Result<(), ()>;
}

impl Storage for Account {
    fn withdraw(&mut self, amount: u32) {
        if self.balance >= amount {
            self.balance -= amount;
        }
    }

    fn deposit(&mut self, amount: u32) {
        self.balance += amount;
    }

    fn transfer_from(&mut self, from: &mut Account, amount: u32) -> Result<(), ()> {
        if from.balance >= amount {
            println!("transferring {} to {} ({}) from {} ({})",
                     amount, self.owner, self.id, from.owner, from.id);
            from.withdraw(amount);
            self.deposit(amount);
            Ok(())
        } else {
            println!("error occurred");
            Err(())
        }
    }
}
