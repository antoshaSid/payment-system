import random
import string

def generate_name():
    return ''.join(random.choices(string.ascii_letters, k=8))

# Enter the number of users you want to generate
def generate_balance():
    return random.randint(0, 10000)

def generate_users(num_users):
    users = []
    for i in range(1, num_users + 1):
        name = generate_name()
        balance = generate_balance()
        users.append((i, name, balance))
    return users

def generate_sql(users):
    with open('insert_user_balances.sql', 'w') as f:
        f.write("INSERT INTO USER_BALANCES (id, name, balance) VALUES\n")
        for i, user in enumerate(users):
            f.write(f"    ({user[0]}, '{user[1]}', {user[2]})")
            if i < len(users) - 1:
                f.write(",\n")
            else:
                f.write(";\n")

def main():
    num_users = 100000
    users = generate_users(num_users)
    generate_sql(users)

if __name__ == "__main__":
    main()
