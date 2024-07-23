import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {ButtonModule} from "primeng/button";
import {DialogModule} from "primeng/dialog";
import {DropdownModule} from "primeng/dropdown";
import {FileUploadModule} from "primeng/fileupload";
import {InputTextModule} from "primeng/inputtext";
import {InputTextareaModule} from "primeng/inputtextarea";
import {MultiSelectModule} from "primeng/multiselect";
import {NgClass, NgIf} from "@angular/common";
import {PaginatorModule} from "primeng/paginator";
import {RatingModule} from "primeng/rating";
import {RippleModule} from "primeng/ripple";
import {MessageService, SharedModule} from "primeng/api";
import {TableLoaderComponent} from "../loaders/table-loader/table-loader.component";
import {Table, TableModule, TableRowCollapseEvent, TableRowExpandEvent} from "primeng/table";
import {TagModule} from "primeng/tag";
import {ToastModule} from "primeng/toast";
import {ToolbarModule} from "primeng/toolbar";
import {Observable} from "rxjs";
import {Book} from "../Book";
import {Category} from "../Category";
import {Author} from "../Author";
import {Publisher} from "../Publisher";
import {BookService} from "../service/book.service";
import {AvailableBook} from "../../AvailableBook";
import {Quantity} from "../Quantity";

@Component({
  selector: 'app-copies-listing',
  standalone: true,
  imports: [
    ButtonModule,
    DialogModule,
    DropdownModule,
    FileUploadModule,
    InputTextModule,
    InputTextareaModule,
    MultiSelectModule,
    NgIf,
    PaginatorModule,
    RatingModule,
    RippleModule,
    SharedModule,
    TableLoaderComponent,
    TableModule,
    TagModule,
    ToastModule,
    ToolbarModule,
    NgClass
  ],
  templateUrl: './copies-listing.component.html',
  styleUrl: './copies-listing.component.css'
})
export class CopiesListingComponent implements OnInit{
  productDialog: boolean = false;
  isLoading: Boolean = true;
  expandedRows = {};
  arr: { [key: number]: AvailableBook[] } = {};


  onRowExpand(event: TableRowExpandEvent) {
    const id = event.data.id;
    this.bookService.getCopiesForBook(id).subscribe(x => {
      this.availableBooks?.set(id, x)
      this.arr = { ...this.arr, [id]: x };
      this.cdr.detectChanges()
    })
  }

  onRowCollapse(event: TableRowCollapseEvent) {
  }

  deleteProductDialog: boolean = false;

  deleteProductsDialog: boolean = false;

  availableBooks: Map<number, AvailableBook[]> = new Map<number, AvailableBook[]>();

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

  copiesToAdd: Quantity = {
    book: this.book,
    quantity: 0,
    status: 'BRAND_NEW'
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

  statuses = [
    { id: 'BRAND_NEW', value: 'BRAND NEW' },
    { id: 'GOOD', value: 'GOOD' },
    { id: 'ACCEPTABLE', value: 'ACCEPTABLE' },
    { id: 'POOR', value: 'POOR' },
  ]

  selectedBooks: Book[] = [];


  submitted: boolean = false;

  cols: any[] = [];

  rowsPerPageOptions = [5, 10, 20];

  constructor(
    private bookService: BookService,
    private messageService: MessageService,
    private cdr: ChangeDetectorRef
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

  openNew(book: Book) {
    this.copiesToAdd = {
      book: book,
      quantity: 0,
      status: 'BRAND_NEW'
    }
    this.book = book
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
    this.bookService.addCopiesForBook(this.copiesToAdd.book.id, this.copiesToAdd.status, this.copiesToAdd.quantity).subscribe(
      res => {
        this.refreshEvent.next();
        this.messageService.add({ severity: 'success', detail: 'The copy is successfully saved!' })
      },
      err => {this.messageService.add({ severity: 'error', detail: 'An error occurred while saving the copies!' })}
    )


    this.productDialog = false;

    this.book = {...this.emptyBook};
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


  onGlobalFilter(table: Table, event: Event) {
      table.filterGlobal((event.target as HTMLInputElement).value, 'contains');
  }
}
