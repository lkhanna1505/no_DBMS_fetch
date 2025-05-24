text
# NoteApp

A simple Java Swing application for adding and publishing notes to a `data.json` file using Gson.  
This project demonstrates basic GUI programming, JSON handling, and file I/O in Java.

---

## Features

- Enter a note heading and text via a user-friendly GUI.
- Add multiple notes to a buffer.
- Publish all buffered notes to `data.json` with the current date.
- Notes are persisted in JSON format, compatible with web frontends.

---

## Requirements

- Java 8 or higher
- [Gson library](https://github.com/google/gson) (`gson-2.10.1.jar` or newer)

---

## Setup

1. **Clone this repository:**
git clone "the repo url"
cd your-repo

text

2. **Download the Gson JAR:**
- Download from [Maven Central](https://repo1.maven.org/maven2/com/google/code/gson/gson/2.10.1/gson-2.10.1.jar)
- Place `gson-2.10.1.jar` in your project directory.

3. **Compile the code:**
javac -cp ".;gson-2.10.1.jar" NoteApp.java

text

4. **Run the application:**
java -cp ".;gson-2.10.1.jar" NoteApp

text

---

## Usage

- Enter a **Heading** and **Text** in the application window.
- Click **Add** to buffer the note.
- Click **Publish** to write all buffered notes to `data.json`.
- The file `data.json` will be created/updated in the project directory.

---

## File Structure

.
├── NoteApp.java
├── gson-2.10.1.jar
├── data.json
├── README.md
├── requirements.txt
└── .gitignore

text

---

## Example `data.json`

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

text

---