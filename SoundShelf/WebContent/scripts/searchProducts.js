function searchProducts(event) {
    event.preventDefault();
    const name = document.getElementById('productName').value;
    const artist = document.getElementById('productArtist').value;
    const genre = document.getElementById('productGenre').value;
    
    const url = `prodotti/searchProducts?name=${encodeURIComponent(name)}&artist=${encodeURIComponent(artist)}&genre=${encodeURIComponent(genre)}`;

    const xhr = new XMLHttpRequest();
    xhr.open('GET', url, true);
    xhr.setRequestHeader('Content-Type', 'application/json;charset=UTF-8');

    xhr.onload = function () {
        if (xhr.status === 200) {
            const response = JSON.parse(xhr.responseText);
            displayResults(response);
        } else {
            document.getElementById('errorMessage').textContent = 'Errore nella ricerca dei prodotti.';
            document.getElementById('errorMessage').style.display = 'block';
        }
    };

    xhr.onerror = function () {
        document.getElementById('errorMessage').textContent = 'Errore nella ricerca dei prodotti.';
        document.getElementById('errorMessage').style.display = 'block';
    };

    xhr.send();
}

function displayResults(products) {
    const resultsContainer = document.getElementById('results');
    resultsContainer.innerHTML = '';

    if (products.length === 0) {
        resultsContainer.innerHTML = '<p>Nessun prodotto trovato.</p>';
    } else {
        products.forEach(product => {
            const productElement = document.createElement('div');
            productElement.className = 'product-item';
            productElement.innerHTML = `
                <img src="${product.image}" alt="${product.name}">
                <div class="info">
                    <h3>${product.name}</h3>
                    <p><strong>Artista:</strong> ${product.artists.join(', ')}</p>
                    <p><strong>Genere:</strong> ${product.genres.join(', ')}</p>
                    <p><strong>Prezzo:</strong> €${product.salePrice}</p>
                </div>
            `;
            resultsContainer.appendChild(productElement);
        });
    }
}
