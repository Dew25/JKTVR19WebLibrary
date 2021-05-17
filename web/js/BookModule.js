class BookModule{
    printFormAddBook(){
        document.getElementById('context').innerHTML=
        `<h3 class="w-100 text-center my-5 ">Добавить новую книгу</h3>
        <form id="bookForm" method="POST" enctype="multipart/form-data">
        <div class="row w-50 my-2 mx-auto">
          <div class="col-4 text-end">
              Название книги 
          </div>
          <div class="col-8 text-start ">
            <input class="w-100" type="text" name="name" id="name">
          </div>
        </div>
        <div class="row w-50 my-2 mx-auto">
          <div class="col-4 text-end">
            Автор книги 
          </div>
          <div class="col-8 text-start">  
            <input class="col-8" type="text" name="author" id="author">
          </div>
        </div>
        <div class="row w-50 my-2 mx-auto">
          <div class="col-4 text-end">   
              Год издания книги 
          </div>
          <div class="col-8 text-start">  
            <input class="col-4" type="text" name="publishedYear" id="publishedYear">
          </div>
        </div>
        <div class="row w-50 my-2 mx-auto">
          <div class="col-4 text-end">
              Загрузите обложку 
          </div>
          <div class="col-8 text-start">     
              <input class="form-control col" type="file" name="file" id="file-cover">
          </div>
        </div>
        <div class="row w-50 my-2 mx-auto">
          <div class="col-4 text-end">
              Текст книги 
          </div>
          <div class="col-8 text-start">     
            <input class="form-control" type="file" name="file" id="file-text">
          </div>
        </div>
        <div class="row w-50 my-2 mx-auto">
        <div class="col-4 text-end">
        </div>
        <div class="col-8 text-start mt-3">     
          <input class="w-50 bg-primary text-white" type="submit" id="btnAddBook" name="submit" value="Добавить">
        </div>
      </div>
      </form>
        `;
        document.getElementById('btnAddBook').onclick = function(){
            bookModule.createBook();
        }
    }
    async createBook(){
        let response = await fetch('createBookJson',{
            method: 'POST',
            body: new FormData(document.getElementById('bookForm'))
        });
        if(response.ok){
            const result = await response.json();
            document.getElementById('info').innerHTML=result.info;
            let books = JSON.parse(sessionStorage.getItem('books'));
            if(books === null){
                books = [];
            }
            books.push(result.book);
            sessionStorage.setItem('books',JSON.stringify(books));
            console.log("books: ");
            for(let book of books){
                console.log(book.name +', ')
            }
        }else{
            document.getElementById('info').innerHTML='Ошибка сервера';
        }
    }
  async printListBooks() {
    const listBooks = await bookModule.getListBooks();
    let context = document.getElementById('context');
    context.innerHTML = '<h3 class="w-100 text-center my-5 ">Список книг</h3>';
    let divForCards = document.createElement('div');
    divForCards.classList.add('w-100');
    divForCards.classList.add('d-flex');
    divForCards.classList.add('justify-content-center');
    context.insertAdjacentElement('beforeend', divForCards);
    for (let book of listBooks) {
      let card = document.createElement('div');
      card.classList.add('card');
      card.classList.add('m-2');
      card.style.cssText = 'max-width: 12rem; max-height: 25rem; border: 0';
      let img = document.createElement('img');
      img.classList.add('card-img-top');
      img.style.cssText = 'max-width: 12rem; max-height: 15rem';
      img.setAttribute('src', 'insertFile/'+book.picture.url);
      card.insertAdjacentElement('beforeend', img);
      card.insertAdjacentHTML('beforeend',
        `<div class="card-body">
            <h5 class="card-title">${book.name}</h5>
            <p class="card-text">${book.author}</p>
            <p class="card-text">${book.publishedYear}</p>
            <p class="card-text">ISBN: ${book.isbn}</p>
            <p class="card-text">Цена: ${book.price}</p>
            <p class="card-text"> 
              <span id='btnReadBook${book.id}' class="btn btn-primary">Читать</span>
              <span id='btnBuyBook${book.id}' class="btn btn-primary">Купить</span>
          </p>
          </div>`
          );
      divForCards.insertAdjacentElement('beforeend', card);
      document.getElementById('btnReadBook'+book.id).onclick = function () {
        bookModule.readBook(book.id);
      }
      document.getElementById('btnBuyBook'+book.id).onclick = function () {
        bookModule.buyBook(book.id);
      }
    }
  }
  async getListBooks() {
    let response = await fetch('listBooksJson', {
      method: 'GET',
      headers: { 'Content-Type': 'application/json;charset:utf-8' }
    });
    if (response.ok) {
      let result = await response.json();
      return result;
    } else {
      document.getElementById('info').innerHTML = 'Ошибка сервера';
      return null;
    }
  }
  readBook(id) {
    alert('bookId=' + id);
  }
  buyBook(id) {
    alert('bookId=' + id);
  }
}
let bookModule = new BookModule();
export {bookModule};