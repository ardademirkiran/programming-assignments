def paint(initial_position,final_position, direction, x, matrix, n):
    if direction == 0:
        for column in range(initial_position[1], initial_position[1] + x + 1):
            matrix[initial_position[0]][column % n] = "1"
    if direction == 2:
        if x > initial_position[1]:
            for column in range(final_position[1], final_position[1] + x + 1):
                matrix[initial_position[0]][column % n] = "1"
        else:
            for column in range(initial_position[1], initial_position[1] - x - 1, -1):
                matrix[initial_position[0]][column] = "1"
    if direction == 1:
        for row in range(initial_position[0], initial_position[0] + x + 1):
            matrix[row % n][initial_position[1]] = "1"
    if direction == 3:
        if x > initial_position[0]:
            for row in range(final_position[0], final_position[0] + x + 1):
                matrix[row % n][initial_position[1]] = "1"
        else:
            for row in range(initial_position[0], initial_position[0] - x - 1, -1):
                matrix[row][initial_position[1]] = "1"
    return [matrix, n]
            
def move(initial_position, direction, brush_position, x, n, matrix):
    if direction == 0:
        final_position = [initial_position[0], (initial_position[1] + x) % n]
    elif direction == 1:
        final_position = [(initial_position[0] + x) % n, initial_position[1]]
    elif direction == 2:
        final_position = [initial_position[0], (initial_position[1] - x) % n]
    elif direction == 3:
        final_position = [(initial_position[0] - x) % n, initial_position[1]]
    if brush_position == 1:
        paint(initial_position,final_position, direction, x, matrix, n)
        return final_position
    else:
        return final_position

def brush_down(position, matrix):
    brush_position = 1
    matrix[position[0]][position[1]] = "1"
    return [brush_position, matrix]

def brush_up():
    brush_position = 0
    return brush_position

def rotate(command, direction):
    if command == 0:
        direction = (direction + 1) % 4 
    elif command == 1:
        direction = (direction + 3) % 4
    elif command == 2:
        direction = (direction + 2) % 4
    return direction

def display_matrix(matrix):
    print((len(matrix) + 2) * "+")
    for row in range(len(matrix)):
        print("+", *matrix[row], "+", sep="")
    print((len(matrix) + 2) * "+")

def change_signs(matrix):
    for row in range(len(matrix)):
        for column in range(len(matrix)):
            if matrix[row][column] == "0":
                matrix[row][column] = " "
            elif matrix[row][column] == "1":
                matrix[row][column] = "*"
    display_matrix(matrix)

def confirm_com_list(com_list, flag):
    if flag == 1:
        if com_list[0] == "":
            return 0
    com_range = ["0", "1", "2", "3", "4", "5", "6", "7", "8"]
    for com in com_list:
        if com [0] != "5":
            if com not in com_range:
                return 0
        else:
            if len(com) < 3:
                return False
            if com[1] == "_":
                if com[2:].isdigit() == False :
                    return 0
            else:
                return 0
    return 1



def run_commands(command_list, matrix, position, brush_position, direction, n):
    for command in command_list:
        if command == "1":
            brush_position, matrix = brush_down(position, matrix)
        elif command[0] == "2":
            brush_position = brush_up()
        elif command[0] == "3":
            direction = rotate(0, direction)
        elif command[0] == "4":
            direction = rotate(1, direction)
        elif command[0] == "5":
            position = move(position, direction, brush_position, int(command[2:]), n, matrix)
        elif command == "6":
            brush_position = brush_up()
            position = move(position, direction, brush_position, 3, n, matrix)
        elif command[0] == "7":
            direction = rotate(2, direction)
        elif command[0] == "8":
            change_signs(matrix)
        elif command[0] == "0":
            exit()
    return [matrix, position, brush_position, direction]


def main():
    direction = 0
    position = [0, 0]
    brush_position = 0
    text = """<-----RULES----->
1. BRUSH DOWN
2. BRUSH UP
3. VEHICLE ROTATES RIGHT
4. VEHICLE ROTATES LEFT
5. MOVE UP TO X
6. JUMP
7. REVERSE DIRECTION
8. VIEW THE MATRIX
0. EXIT"""
    print(text)
    command_order = input("Please enter the commands with a plus (+) sign between them.\n")
    command_list = command_order.split("+")
    flag = 0
    while True:
        if command_list[0].isdigit() == True and int(command_list[0]) > 0 and len(command_list) > 0:
            break
        else:
            command_order = input("Matrix size must be a positive integer! Please try again!\n")
            command_list = command_order.split(("+"))
    map_size = int(command_list[0])
    matrix = [["0" for x in range(map_size)] for x in range(map_size)]
    command_list = command_list[1:]
    while True:
        if confirm_com_list(command_list, flag) == 1:
            matrix, position, brush_position, direction = run_commands(command_list, matrix, position, brush_position, direction, map_size)
            command_order = input("Please enter the commands with a plus (+) sign between them.\n")
            command_list = command_order.split("+")
            flag = 1
        else:
            command_order = input("You entered an incorrect command. Please try again!\n")
            command_list = command_order.split("+")
main()    
        
        
