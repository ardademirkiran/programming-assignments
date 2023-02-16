import sys

def check_command(command_elements):
    valid_commands = {"ANU":1, "DEU":1, "FPF":2, "SF":2, "ANF":2, "CF":1, "DEF":2}
    if len(command_elements) == 0 or len(command_elements) > 3:
        return 0
    if command_elements[0] not in valid_commands.keys():
        return 0
    if len(command_elements) !=  valid_commands[command_elements[0]] + 1:
        return 0
    
    return 1



def get_users(users_file_txt):
    users_dict = {}
    users_file = open(users_file_txt, "r")
    users = users_file.readlines()
    for user in users:
        user_info = user.split(":")
        user_name = user_info[0]
        user_friends = user_info[1].split()
        users_dict[user_name] = user_friends
        users_file.close()
    return users_dict
users_dict = get_users(sys.argv[1])

def add_new_users(user_name):
    global users_dict
    if user_name in users_dict.keys():
        return "ERROR: Wrong input type! for 'ANU’!--This user already exists!!\n"
    else:
        users_dict[user_name] = []
        return "User '{}' has been added to the social network successfully\n".format(user_name)

def delete_user(user_name):
    global users_dict
    if user_name not in users_dict.keys():
        return "ERROR: Wrong input type! for 'DEU'!--There is no user named '{}'!\n".format(user_name)
    else:
        users_dict.pop(user_name)
        for relation in users_dict.values():
            if user_name in relation:
                relation.remove(user_name)
        return "User '{}' and his/her all relations have been removed successfully\n".format(user_name)

def add_new_friend(user_name1, user_name2):
    global users_dict
    if user_name1 not in users_dict.keys() and user_name2 not in users_dict.keys():
        return "ERROR: Wrong input type! for 'ANF'!--No user named '{}' and '{}' found!\n".format(user_name1, user_name2)
    elif user_name1 not in users_dict.keys():
        return "ERROR: Wrong input type! for 'ANF'!--No user named '{}' found!!\n".format(user_name1)
    elif user_name2 not in users_dict.keys():
        return "ERROR: Wrong input type! for 'ANF'!--No user named '{}' found!!\n".format(user_name2)
    elif user_name1 in users_dict[user_name2] and user_name2 in users_dict[user_name1]:
        return "ERROR:A relation between '{}' and '{}' already exists!!\n".format(user_name1, user_name2)
    else:
        users_dict[user_name1].append(user_name2)
        users_dict[user_name2].append(user_name1)
        return "Relation between '{}' and '{}' has been added successfully\n".format(user_name1, user_name2)
        
def delete_existing_friend(user_name1, user_name2):
    global users_dict
    if user_name1 not in users_dict.keys() and user_name2 not in users_dict.keys():
        return "ERROR: Wrong input type! for 'DEF'!--No user named '{}' and '{}' found!\n".format(user_name1, user_name2)
    elif user_name1 not in users_dict.keys():
        return "ERROR: Wrong input type! for 'DEF'!--No user named '{}' found!\n".format(user_name1)
    elif user_name2 not in users_dict.keys():
        return "ERROR: Wrong input type! for 'DEF'!--No user named '{}' found!\n".format(user_name2)
    elif user_name1 not in users_dict[user_name2] and user_name2 not in users_dict[user_name1]:
        return "ERROR: No relation between '{}' and '{}' found!!\n".format(user_name1, user_name2)
    else:
        users_dict[user_name1].remove(user_name2)
        users_dict[user_name2].remove(user_name1)
        return "Relation between '{}' and '{}' has been deleted successfully\n".format(user_name1, user_name2)

def count_friend(user_name):
    global users_dict
    if user_name not in users_dict.keys():
        return "ERROR: Wrong input type! for 'CF'!--No user named '{}' found!\n".format(user_name)
    else:
        return "User ‘{}’ has {} friends\n".format(user_name, len(users_dict[user_name]))

def find_possible_friends(user_name, max_distance):
    global users_dict
    pos_friends = []
    if max_distance.isdigit() == False:
        return "ERROR: Maximum distance must be an integer!\n"
    if int(max_distance) not in range(1,4):
        return "ERROR: Maximum distance is out of range!\n"
    if user_name not in users_dict.keys():
        return "ERROR: Wrong input type! for 'FPF'!--No user named '{}' found!\n".format(user_name)
    else:
        for distance in range(1, int(max_distance) + 1):
            if distance == 1:
                pos_friends.extend(users_dict[user_name])
            if distance == 2:
                for friend in users_dict[user_name]:
                    pos_friends.extend(users_dict[friend])
            if distance == 3:
                for friend in users_dict[user_name]:
                    for d2_friend in users_dict[friend]:
                        pos_friends.extend(users_dict[d2_friend])
        pos_friends = set(pos_friends)
        pos_friends = list(pos_friends)
        pos_friends.sort()
        if user_name in pos_friends:
            pos_friends.remove(user_name)

        text = "These possible friends:{}\n".format(pos_friends)
        return "User ‘{}’ has {} possible friends when maximum distance is {}\n{}".format(user_name, len(pos_friends), max_distance, text)

def find_suggested_friend(user_name, md):
    global users_dict
    friends_md = []
    suggested_friends = []
    if md.isdigit() == False:
        return "ERROR: Mutually Degree must be an integer!\n"
    if int(md) <= 1 or int(md) >= 4:
        return "ERROR: Mutually Degree cannot be less than 1 or greater than 4\n"
    if user_name not in users_dict.keys():
        return "ERROR: Wrong input type! for 'SF'!--No user named '{}' found!!\n".format(user_name)
    if len(users_dict[user_name]) < int(md):
        return "ERROR:'{}' doesn't have enough number of friends for SF.\n".format(user_name)
    else:
        for friend in users_dict[user_name]:
            for distanced_friends in users_dict[friend]:
                friends_md.append(distanced_friends)
        for friend in friends_md:
            if friends_md.count(friend) >= int(md) and friend != user_name and friend not in users_dict[user_name]:
                suggested_friends.append(friend)
        suggested_friends_set = set(suggested_friends)
        suggested_friends_list = list(suggested_friends_set)
        suggested_friends_list.sort()
        text = "Suggestion List for '{}' (when MD is {}):\n".format(user_name, md)
        for sug_friend in suggested_friends_list:
            text += "'{}' has {} mutual friends with '{}'\n".format(user_name, suggested_friends.count(sug_friend), sug_friend)
        
        text += "The suggested friends for ‘{}’:{}\n".format(user_name, str(sorted(suggested_friends_set)).replace("[", "").replace("]", ""))
        return text
command_file = open(sys.argv[2], "r")
commands = command_file.readlines()
command_file.close()
output_file = open("output.txt", "a")
for command in commands:
    command = command.strip("\n").split()
    if check_command(command) == 0:
        output_file.write("You typed an incorrect command!\n")
        continue
    if command[0] == "ANU":
        output_file.write(add_new_users(command[1]))
    elif command[0] == "DEU":
        output_file.write(delete_user(command[1]))
    elif command[0] == "ANF":
        output_file.write(add_new_friend(command[1], command[2]))
    elif command[0] == "DEF":
        output_file.write(delete_existing_friend(command[1], command[2]))
    elif command[0] == "CF":
        output_file.write(count_friend(command[1]))
    elif command[0] == "FPF":
        output_file.write(find_possible_friends(command[1], command[2]))
    elif command[0] == "SF":
        output_file.write(find_suggested_friend(command[1], command[2]))
    else:
        output_file.write("You typed an incorrect command!\n")
output_file.close()


