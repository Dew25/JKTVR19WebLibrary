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
            bookModule.creteBook();
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
    printListBooks(){
        console.log('Сработал метод printListBook()');
        document.getElementById('info').innerHTML = '&nbsp;'
        document.getElementById('context').innerHTML = ''

    }
}
let bookModule = new BookModule();
export {bookModule};