# Clarawr User Guide
# Introduction
> RAWR! Welcome to Clarawr! This guide will walk you through key features and functionality of the Clarawr app,
helping you get started and make the most of its capabilities.

## Product Screenshot
![Ui.png](Ui.png)

# ⚡️ Quick Start Guide
Welcome to Clarawr, your fun, interactive and playful task manager! Here's how to get started:
1. Clone the repository from [https://github.com/brvndonkoh/ip]
2. Navigate to the project directory on your CLI and run ````gradlew.bat clean shadowJar````
3. You will find the **Clarawr** JAR file in the ````build/libs```` folder 
4. To run the application: ````java -jar build\libs\Clarawr.jar````


# ➕ Adding Tasks
## 🎯 Todo Tasks
To add a to-do task, use the **todo** command. This is perfect for simple tasks that don't
have specific deadline.

### Command Syntax
````
todo [task description]
````

### For example:
````
todo buy groceries
````

### Expected Outcome:
````
Better feed me as I/ve added this task: [T][ ] buy groceries
````
Clarawr will add the to-do task to your list, and it will be marked as not yet
completed by default.

## 🎯 Deadline Tasks
In Clarawr, adding deadlines to your tasks is simple. To add a task with a deadline, use the 
**deadline** command. Make sure to specify both the description of the task and the deadline time.

### Command Syntax
````
deadline [task description] /by [YYYY-MM-DD HHmm]
````

### For example:
````
deadline cs2103 assignment /by 2025-01-01 2359
````

### Expected Outcome:
````
HEHE, I've added this task: [D][ ] cs2103 assignment by: Jan-01-2025 23:59
````
## 🎯 Event Tasks
For tasks related to specific events or appointments, use the **event** command. 
These tasks are great for things that occur at specific times.

### Command Syntax
````
event [task description] /from [YYYY-MM-DD HHmm] /to [YYYY--MM-DD HHmm]
````

### For example:
````
event team meeting /from 2025-02-02 1200 /to 2025-02-02 1400
````

### Expected Outcome:
````
RAWR! I've added this task: [E][ ] team meeting from: Feb-02-2025 12:00 to: Feb-02-2025 14:00
````
# 📄 Listing All Tasks
To view all tasks in your list, use the list command. This will display every task, 
whether it’s a to-do, event, or deadline, along with its current status (done or undone).

### Command Syntax
````
list
````

### For example:
````
list
````

### Expected Outcome:
````
1. [T][ ] buy groceries
2. [D][ ] cs2103 assignment by: Jan-01-2025 23:59
3. [E][ ] team meeting from: Feb-02-2025 12:00 to: Feb-02-2025 14:00
````
# ✅ Mark as Done/Undone
To mark a task as done or undone, use the mark/unmark command followed by the index of the task

### Command Syntax
````
mark/unmark [index of task]
````
### For example:
````
mark 1
````
### Expected Outcome:
````
Wow someone's productive. I've marked this task as done: [T][X] buy groceries
````
### For example:
````
unmark 1
````
### Expected Outcome:
````
Why did you even mark it as done in the first place? I've marked this task as not done yet: [T][ ] buy groceries
````
# 🗑❌ Deleting/Removing Tasks
If you no longer need a task, you can remove it from your list using the delete command. 
Once deleted, the task cannot be recovered, so use this command carefully.

### Command Syntax
````
delete/remove [index of task]
````
### For example:
````
delete 1
````
### Expected Outcome:
````
*BURP* I've eaten this task hehe: [T][X] buy groceries
````
# 🔍 Find Tasks by Keyword
To search for tasks that match a specific keyword, use the find command. 
This allows you to quickly find tasks related to a specific keyword.

### Command Syntax
````
find [keyword]
````
### For example:
````
find meeting
````
### Expected Outcome:
````
Here are the matching tasks: 
 [E][ ] team meeting from: Feb-02-2025 12:00 to: Feb-02-2025 14:00
````
# 🔍 Find Tasks by Date
To search for tasks that match a date, use the listbydate command.
This allows you to quickly find tasks happening on that date.

### Command Syntax
````
listbydate [YYYY-MM-DD]
````
### For example:
````
listbydate 2025-02-02
````
### Expected Outcome:
````
Tasks on 2025-02-02: 
 [E][ ] team meeting from: Feb-02-2025 12:00 to: Feb-02-2025 14:00
````
# Error Handling
>When you encounter an error, fret not! Clarawr will kindly tell you what went wrong, 
> and you can simply re-type your command with the correct input. It's all part of the process, no worries! 😊


# Customisation & Extra Features 
### 🚀 Can I prioritise tasks?
>Not yet, but stay tuned for updates!

### ♻️ Can I add recurring tasks?
>Currently, tasks must be added manually each time.

### 🔄 Can I undo a deletion?
>Nope! Once a task is deleted, it's gone. RAWR carefully!
 
# Troubleshooting & FAQ
### 🤔 Why is my deadline not showing?
>Make sure you used the correct format: ````YYYY-MM-DD HHmm.````


### 🧐 Can I edit a task instead of deleting it?
> Not yet! You'll need to delete it and re-add a new one.


## ***You're now a Clarawr pro! 🎉 Go forth and conquer your tasks… or I might just chomp them for you! 🦁***