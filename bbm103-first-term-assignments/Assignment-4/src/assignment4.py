import sys
cells_to_remove = []
bombs = []
points_dict = {"B":9,"G":8,"W":7,"Y":6,"R":5,"P":4,"O":3,"D":2,"F":1,"X":0," ":0}
score = 0
def create_map(file_name):
    map = []
    map_file = open(file_name, "r")
    map_lines = map_file.read().split("\n")
    for line in map_lines:
        map.append(line.split())
    return map

def find_cells(row, column):
    global map, cells_to_remove
    flag = 0
    for i_row in [row + 1, row - 1]:
        if i_row >= 0 and i_row < len(map):
            if map[i_row][column] == map[row][column] and [i_row, column] not in cells_to_remove:
                cells_to_remove.append([i_row, column])
                flag = 1
        else:
            continue
    for i_column in [column + 1, column - 1]:
        if i_column >= 0 and i_column < len(map[0]):
            if map[row][i_column] == map[row][column] and [row, i_column] not in cells_to_remove:
                cells_to_remove.append([row, i_column])
                flag = 1
        else:
            continue
    if flag == 1:
        for cell in cells_to_remove:
            find_cells(cell[0], cell[1])
    else:
        return 0

def boom(row, column):
    global cells_to_remove, map, bombs
    flag = 0
    for column_b in range(len(map[row])):
        if map[row][column_b] == "X" and [row, column_b] not in bombs:
            bombs.append([row, column_b])
            flag = 1
        if [row, column_b] not in cells_to_remove:
            cells_to_remove.append([row, column_b])
    for row_b in range(len(map)):
        if map[row_b][column] == "X" and [row_b, column] not in bombs:
            bombs.append([row_b, column])
            flag = 1
        if [row_b, column] not in cells_to_remove:
            cells_to_remove.append([row_b, column])
    if flag == 1:
        for bomb in bombs:
            boom(bomb[0], bomb[1])
    else:
        return 0

def remove_cells():
    global cells_to_remove, score
    for cell in cells_to_remove:
        score += points_dict[map[cell[0]][cell[1]]]
        map[cell[0]][cell[1]] = " "

def reshape_map():
    global map
    for column in range(len(map[0])):
        new_column = []
        for row in range(len(map)):
            if map[row][column] == " ":
                new_column.append(map[row][column])
        for row in range(len(map)):
            if map[row][column] != " ":
                new_column.append(map[row][column])
        for index in range(len(map)):
            map[index][column] = new_column[index]

def remove_empty_row():
    rows_to_remove = []
    for line_index in range(len(map)):
        if map[line_index] == [" " for x in range(len(map[0]))]:
            rows_to_remove.append(line_index)
    for row in reversed(rows_to_remove):
        map.pop(row)
            

def remove_empty_column():
    global map
    columns_to_remove = []
    try:
        for column in range(len(map[0])):
            flag = 0
            for row in range(len(map)):
                if map[row][column] != " ":
                    flag = 1
            if flag == 0:
                columns_to_remove.append(column)
        for column in reversed(columns_to_remove):
            for row in range(len(map)):
                map[row].pop(column)
    except IndexError:
        return 0

def check_map():
    global map, cells_to_remove
    for row in range(len(map)):
        for column in range(len(map[0])):
            if map[row][column] == " ":
                continue
            elif map[row][column] == "X":
                return 1
            find_cells(row, column)
            if len(cells_to_remove) > 0:
                cells_to_remove = []
                return 1
    return 0

def display_map():
    global map
    print("")
    for line in map:
        print(*line)

def check_input(input_list):
    global map
    if len(input_list) != 2:
        return False
    if input_list[0].isdigit() != True or input_list[1].isdigit() != True:
        return False
    if int(input_list[0]) >= len(map) or int(input_list[1]) >= len(map[0]):
        return False
    return True
    
    
map = create_map(sys.argv[1])
while True:
    cells_to_remove = []
    bombs = []
    reshape_map()
    remove_empty_row()
    remove_empty_column()
    display_map()
    print("\nYour score is: {}".format(score))
    if check_map() == 0:
        print("\nGame Over")
        exit()
    else:
        command_input = input("\nPlease enter a row and column number: ")
        command_input_elements = command_input.split()
        if check_input(command_input_elements) == True:
            row = int(command_input_elements[0])
            column = int(command_input_elements[1])
            if map[row][column] == "X":
                boom(row, column)
            else:
                find_cells(row, column)
            remove_cells()
        else:
            print("\nPlease enter a valid size!")




    
    

            
        

