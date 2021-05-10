class BookModule{
    printListBooks(){
        console.log('Сработал метод printListBook()');
        document.getElementById('info').innerHTML = '&nbsp;'
        document.getElementById('context').innerHTML = ''

    }
}
let bookModule = new BookModule();
export {bookModule};