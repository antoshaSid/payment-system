import random
import json

def generate_balance():
    return random.randint(0, 10000)

def generate_json_file(file_name, num_fields):
    data = {}
    for i in range(1, num_fields + 1):
        key = f"{i}"
        data[key] = generate_balance()

    with open(file_name, 'w') as json_file:
        json.dump(data, json_file, indent=4)

if __name__ == "__main__":
    file_name = "user_balances.json"
    num_fields = 1000000
    generate_json_file(file_name, num_fields)
    print(f"Generated {file_name} with {num_fields} fields.")
