from enum import Enum, auto

class Curr(Enum):
    USD = auto()
    EUR = auto()

conv_table = {}
conv_table[(Curr.EUR, Curr.USD)] = 1.21
conv_table[(Curr.USD, Curr.EUR)] = 0.83

def convert(cur1, cur2, amount):
    return (conv_table.get((cur1, cur2)) * amount)

class Bank:
    def __init__(self, name, curr, bal):
        self.name = name
        self.curr = curr
        self.bal = bal

    def bal_in(self, curr):
        if self.curr == curr:
            return self.bal
        else:
            return convert(self.curr, curr, self.bal)

    def withdraw(self, amount):
        self.bal -= amount

    def deposit(self, amount):
        self.bal += amount

    # move amount to seller in their currency
    def buy_from(self, seller, amount):
        self.withdraw(convert(seller.curr, self.curr, amount))
        seller.deposit(amount)

    def print_bal(self):
        print(f'{self.name} {self.curr} {self.bal_in(Curr.USD)} {self.bal}')

banks = []
banks.append(Bank('Fed', Curr.USD, 1000.0))
banks.append(Bank('Ecb', Curr.EUR, 1000.0))

for b in banks:
    b.print_bal()

print("")
banks[1].buy_from(banks[0], 100.0)

for b in banks:
    b.print_bal()
