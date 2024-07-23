import {Component, OnInit} from '@angular/core';
import {Book} from '../Book';
import {BookService} from '../service/book.service';
import {Observable, Subject, switchMap, tap} from 'rxjs';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {ButtonModule} from 'primeng/button';
import {MultiSelectModule} from 'primeng/multiselect';
import {RippleModule} from 'primeng/ripple';
import {ToastModule} from 'primeng/toast';
import {TableModule} from 'primeng/table';
import {ToolbarModule} from 'primeng/toolbar';
import {RatingModule} from 'primeng/rating';
import {InputTextModule} from 'primeng/inputtext';
import {InputNumberModule} from 'primeng/inputnumber';
import {DropdownModule} from 'primeng/dropdown';
import {DialogModule} from 'primeng/dialog';
import {RadioButtonModule} from 'primeng/radiobutton';
import {InputTextareaModule} from 'primeng/inputtextarea';
import {FileUploadModule} from 'primeng/fileupload';
import {TagModule} from 'primeng/tag';
import {MessageService} from 'primeng/api';
import {Category} from '../Category';
import {Author} from '../Author';
import {Publisher} from '../Publisher';

@Component({
  selector: 'app-lendings-listing',
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
    TableLoaderComponent,
    RouterLink,
    AvatarModule
  ],
  templateUrl: './user-reservations.component.html',
  styleUrl: './user-reservations.component.css',
})
export class UserReservationsComponent implements OnInit {
  productDialog: boolean = false;
  isLoading: Boolean = true;

  deleteProductDialog: boolean = false;

  deleteProductsDialog: boolean = false;

  books: ReservedBookDetails[] | undefined;


  book: ReservedBookDetails | undefined;

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

  selectedBooks: LentBookDetails[] = [];

  submitted: boolean = false;

  cols: any[] = [];
  user: UserResponse | undefined;

  statuses: any[] = [];

  rowsPerPageOptions = [5, 10, 20];

  constructor(
    private bookService: BookService,
    private messageService: MessageService,
    private userService: UserService
  ) {
  }

  ngOnInit() {
    this.isLoading = true;

    this.userService.getPrincipal().pipe(
      tap(res => this.user = res),
      switchMap(() => this.bookService.getUserReservations(this.user?.id))
    ).subscribe(x => {
      console.log(x)
      this.books = x
      this.isLoading = false
    });


    this.cols = [
      {field: 'id', header: 'ID'},
      {field: 'name', header: 'Book Name'},
      {field: 'imgUrl', header: 'Image'},
      {field: 'isbn', header: 'ISBN'},
      {field: 'dateFrom', header: 'Reserved on'},
      {field: 'dateTo', header: 'Reserved to'},
      {field: 'store', header: 'Store'},
    ];

    this.fields = this.cols.map(x => x.header)
  }

  fields: string[] = []



  deleteProduct(book: ReservedBookDetails) {
    this.deleteProductDialog = true;
    this.book = book
    // this.book = { ...book, categories: book.categories.map(x => this.categories?.find(cat => cat.name === x)?.id.toString() ?? "")};
  }

  confirmDelete() {
    this.deleteProductDialog = false;
    if (this.book) {
      this.bookService.cancelReservation(this.book.id).subscribe(
        next => this.messageService.add({
          severity: 'success',
          summary: 'Successful',
          detail: 'Books Successfully returned',
          life: 3000,
        }),
        error => this.messageService.add({
          severity: 'error',
          summary: 'Erorr',
          detail: 'There was a problem while updateing the book state',
          life: 3000,
        })
      )
      this.book = undefined
    }
  }

  hideDialog() {
    this.productDialog = false;
    this.submitted = false;
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

import {TableLoaderComponent} from '../loaders/table-loader/table-loader.component';
import {LentBookDetails} from "../LentBookDetails";
import {RouterLink} from "@angular/router";
import {AvatarModule} from "primeng/avatar";
import {UserService} from "../service/user.service";
import {UserResponse} from "../UserResponse";
import {ReservedBookDetails} from "../ReservedBookDetails";
