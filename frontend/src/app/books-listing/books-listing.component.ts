import { Component, OnInit } from '@angular/core';
import { Book } from '../Book';
import { BookService } from '../service/book.service';
import { Observable, Subject } from 'rxjs';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { MultiSelectModule } from 'primeng/multiselect';
import { RippleModule } from 'primeng/ripple';
import { ToastModule } from 'primeng/toast';
import { TableModule } from 'primeng/table';
import { ToolbarModule } from 'primeng/toolbar';
import { RatingModule } from 'primeng/rating';
import { InputTextModule } from 'primeng/inputtext';
import { InputNumberModule } from 'primeng/inputnumber';
import { DropdownModule } from 'primeng/dropdown';
import { DialogModule } from 'primeng/dialog';
import { RadioButtonModule } from 'primeng/radiobutton';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { FileUploadModule } from 'primeng/fileupload';
import { TagModule } from 'primeng/tag';
import { MessageService } from 'primeng/api';
import { Category } from '../Category';
import { Author } from '../Author';
import { Publisher } from '../Publisher';
import { TableLoaderComponent } from '../loaders/table-loader/table-loader.component';

@Component({
  selector: 'app-books-listing',
  standalone: true,
  imports: [
    CommonModule,
    TableModule,
    FileUploadModule,
    FormsModule,
    ButtonModule,
    RippleModule,
    ToastModule,
    ToolbarModule,
    RatingModule,
    InputTextModule,
    InputTextareaModule,
    DropdownModule,
    RadioButtonModule,
    InputNumberModule,
    DialogModule,
    TagModule,
    MultiSelectModule,
    TableLoaderComponent
  ],
  templateUrl: './books-listing.component.html',
  styleUrl: './books-listing.component.css',
})
export class BooksListingComponent implements OnInit {
  productDialog: boolean = false;
  isLoading: Boolean = true;

  deleteProductDialog: boolean = false;

  deleteProductsDialog: boolean = false;

  books$: Observable<Book[]> = this.bookService.getBooks();
  books: Book[] | undefined;

  categories$: Observable<Category[]> = this.bookService.getAvailableCategories();
  categories: Category[] | undefined;

  authors$: Observable<Author[]> = this.bookService.getAvailableAuthors();
  authors: Category[] | undefined;

  publishers$: Observable<Publisher[]> = this.bookService.getAvailablePublishers();
  publishers: Publisher[] | undefined;

  refreshEvent = this.bookService.refreshEvent;

  book: Book = {
    isbn: '',
    name: '',
    description: '',
    imgUrl: '',
    publisher: '',
    id: -1,
    categories: [],
    authors: [],
    averageRating: 0,
    numPages: 0,
    year: 0
  };

  emptyBook: Book = {
    isbn: '',
    name: '',
    description: '',
    imgUrl: '',
    publisher: '',
    id: -1,
    categories: [],
    authors: [],
    averageRating: 0,
    numPages: 0,
    year: 0
  };

  selectedBooks: Book[] = [];

  submitted: boolean = false;

  cols: any[] = [];

  statuses: any[] = [];

  rowsPerPageOptions = [5, 10, 20];

  constructor(
    private bookService: BookService,
    private messageService: MessageService
  ) {}

  ngOnInit() {
    this.isLoading = true;
    this.fetchData();

    this.cols = [
      { field: 'id', header: 'ID' },
      { field: 'name', header: 'Name' },
      { field: 'imgUrl', header: 'Image' },
      { field: 'isbn', header: 'ISBN' },
      { field: 'categories', header: 'Categories' },
      { field: 'rating', header: 'Rating' },
      { field: 'authors', header: 'Authors' },
    ];


    this.statuses = [
      { label: 'INSTOCK', value: 'instock' },
      { label: 'LOWSTOCK', value: 'lowstock' },
      { label: 'OUTOFSTOCK', value: 'outofstock' },
    ];

    this.books$.subscribe((x) => {
      this.books = x;
      this.isLoading = false;
    });

    this.categories$.subscribe(x => {
      this.categories = x;
    })

    this.publishers$.subscribe(x => {
      this.publishers = x;
    })

    this.authors$.subscribe(x => {
      this.authors   = x;
    })

    this.refreshEvent.subscribe(() => this.fetchData())

    this.fields = this.cols.map(x => x.header)
  }

  fields: string[] = []

  fetchData() {
    this.books$.subscribe((x) => {
      this.books = x;
    });

    this.categories$.subscribe(x => {
      this.categories = x;
    })

    this.publishers$.subscribe(x => {
      this.publishers = x;
    })

    this.authors$.subscribe(x => {
      this.authors   = x;
    })

  }

  openNew() {
    this.book = {
      isbn: '',
      name: '',
      description: '',
      imgUrl: '',
      publisher: '',
      id: -1,
      categories: [],
      authors: [],
      averageRating: 0,
      numPages: 0,
      year: 0
    };
    this.submitted = false;
    this.productDialog = true;
  }

  deleteSelectedProducts() {
    this.deleteProductsDialog = true;
  }

  editProduct(book: Book) {
    this.book = {
      ...book,
      categories: book.categories.map(x => this.categories?.find(cat => cat.name === x)?.id ?? 0),
      authors: book.authors.map(x => this.authors?.find(cat => cat.name.includes(x.toString()))?.id ?? 0)}
    console.log(this.book)
    this.productDialog = true;
  }

  deleteProduct(book: Book) {
    this.deleteProductDialog = true;
    this.book = { ...book, categories: book.categories.map(x => this.categories?.find(cat => cat.name === x)?.id.toString() ?? "")};
  }

  confirmDeleteSelected() {
    this.deleteProductsDialog = false;
    this.books = this.books?.filter((val) => !this.selectedBooks.includes(val));
    this.messageService.add({
      severity: 'success',
      summary: 'Successful',
      detail: 'Books Successfully Deleted',
      life: 3000,
    });
    this.selectedBooks = [];
  }

  confirmDelete() {
    this.deleteProductDialog = false;
    this.bookService.deleteBook(this.book.id).subscribe(
      res => {
        this.refreshEvent.next();
        this.messageService.add({ severity: 'success', detail: `${this.book.name} is successfully deleted!` })
      },
      err => {this.messageService.add({ severity: 'error', detail: 'An error occured while commiting the action!' })}
    )
    this.book = { ...this.emptyBook }
  }

  hideDialog() {
    this.productDialog = false;
    this.submitted = false;
  }

  saveProduct() {
    console.log(this.book);
    this.submitted = true;
    this.bookService.addBook(this.book).subscribe(
      res => {
        this.refreshEvent.next();
        this.messageService.add({ severity: 'success', detail: 'The book is successfully saved!' })
      },
      err => {this.messageService.add({ severity: 'error', detail: JSON.stringify(err) })}
    )


    this.productDialog = false;

    this.book = {...this.emptyBook};
    // this.submitted = true;
    // if (this.book?.name?.trim()) {
    //     if (this.product.id) {
    //         // @ts-ignore
    //         this..inventoryStatus = this.product.inventoryStatus.value ? this.product.inventoryStatus.value : this.product.inventoryStatus;
    //         this.products[this.findIndexById(this.product.id)] = this.product;
    //         this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Product Updated', life: 3000 });
    //     } else {
    //         this.product.id = this.createId();
    //         this.product.code = this.createId();
    //         this.product.image = 'product-placeholder.svg';
    //         // @ts-ignore
    //         this.product.inventoryStatus = this.product.inventoryStatus ? this.product.inventoryStatus.value : 'INSTOCK';
    //         this.products.push(this.product);
    //         this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Product Created', life: 3000 });
    //     }
    //     this.products = [...this.products];
    //     this.productDialog = false;
    //     this.product = {};
    // }
  }

  findIndexById(id: string): number {
    let index = -1;
    // for (let i = 0; i < this.products.length; i++) {
    //     if (this.products[i].id === id) {
    //         index = i;
    //         break;
    //     }
    // }

    return index;
  }

  createId(): string {
    let id = '';
    const chars =
      'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    for (let i = 0; i < 5; i++) {
      id += chars.charAt(Math.floor(Math.random() * chars.length));
    }
    return id;
  }

  // onGlobalFilter(table: Table, event: Event) {
  //     table.filterGlobal((event.target as HTMLInputElement).value, 'contains');
  // }
}
