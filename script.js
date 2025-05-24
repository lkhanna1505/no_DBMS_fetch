// Fetch and display notes from data.json
fetch('data.json')
    .then(response => response.json())
    .then(data => {
        const container = document.getElementById('notes-container');
        data.forEach(note => {
            const card = document.createElement('div');
            card.className = 'note-card';

            card.innerHTML = `
        <div class="note-heading">${note.heading}</div>
        <div class="note-date">Last edited: ${note.editedDate}</div>
        <div class="note-text">${note.editedText}</div>
      `;

            container.appendChild(card);
        });
    })
    .catch(error => {
        document.getElementById('notes-container').textContent = 'Failed to load notes.';
        console.error('Error loading JSON:', error);
    });
