# NoteApp

A simple Java Swing application for adding and publishing notes to a `data.json` file using Gson.  
This project demonstrates basic GUI programming, JSON handling, file I/O, and simple Git automation in Java.

---

## Features

- Enter a note heading and text via a user-friendly GUI.
- Add multiple notes to a buffer.
- Publish all buffered notes to `data.json` with the current date.
- Notes are persisted in JSON format, compatible with web frontends.
- **Publish** will automatically commit and push changes to your GitHub repository (see [Git Integration Caveats](#git-integration-caveats)).

---

## Requirements

- Java 8 or higher
- [Gson library](https://github.com/google/gson) (`gson-2.10.1.jar` or newer)
- Git installed and configured on your system
- The project directory must be a git repository with a remote set up and credentials configured

---

## Setup

1. **Clone this repository:**

    ```
    git clone "the repo url"
    cd your-repo
    ```

2. **Download the Gson JAR:**
    - Download from [Maven Central](https://repo1.maven.org/maven2/com/google/code/gson/gson/2.10.1/gson-2.10.1.jar)
    - Place `gson-2.10.1.jar` in your project directory.

3. **Compile the code:**

    ```
    javac -cp ".;gson-2.10.1.jar" NoteApp.java
    ```

4. **Run the application:**

    ```
    java -cp ".;gson-2.10.1.jar" NoteApp
    ```

---

## Usage

- Enter a **Heading** and **Text** in the application window.
- Click **Add** to write the note directly to `data.json`.
- Click **Publish** to commit and push all changes in `data.json` to your GitHub repository.
- The file `data.json` will be created/updated in the project directory.

---

## File Structure

```
.
├── NoteApp.java
├── gson-2.10.1.jar
├── data.json
├── README.md
├── requirements.txt
└── .gitignore
```

---

## Example `data.json`

``` json
[
{
"heading": "First Note",
"editedDate": "2025-05-24",
"editedText": "This is the first note's content."
},
{
"heading": "Second Note",
"editedDate": "2025-05-23",
"editedText": "Here is some more text for the second note!"
}
]
```

---

## Git Integration Caveats

If you see an error like this when clicking **Publish**:

``` text
Git operation failed: Command failed: git push
To https://github.com/lkhanna1505/no_DBMS_fetch.git
! [rejected] master -> master (fetch first)
error: failed to push some refs to 'https://github.com/lkhanna1505/no_DBMS_fetch.git'
hint: Updates were rejected because the remote contains work that you do not
hint: have locally. This is usually caused by another repository pushing to
hint: the same ref. If you want to integrate the remote changes, use
hint: 'git pull' before pushing again.
hint: See the 'Note about fast-forwards' in 'git push --help' for details.
```


**What does this mean?**  
This error occurs when your local repository is behind the remote repository (someone else has pushed changes you don't have locally).

**How to fix:**  
1. Pull the latest changes and rebase your work:
    ``` bash
    git pull --rebase
    ```
    or
    ``` bash
    git pull
    ```
2. Resolve any conflicts if prompted.
3. Push your changes again:
    ``` bash
    git push
    ```

**Tip:**  
You can update the application's publish logic to run `git pull --rebase` before `git push` for automatic integration and to avoid this error.

---

**Note:**  
Do not use `git push --force` unless you are sure you want to overwrite remote changes.  
Always prefer pulling and rebasing to keep your history and collaborators' work intact.



