import {Component, OnInit} from '@angular/core';
import {Observable, Subject} from 'rxjs';
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
import {Author} from '../Author';
import {TableLoaderComponent} from '../loaders/table-loader/table-loader.component';
import {AuthorService} from "../service/author.service";

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
  // providers: [MessageService],
  templateUrl: './authors-listing.component.html',
  styleUrl: './authors-listing.component.css',
})
export class AuthorsListingComponent implements OnInit {
  productDialog: boolean = false;
  isLoading: Boolean = true;

  deleteProductDialog: boolean = false;

  deleteProductsDialog: boolean = false;

  authors$: Observable<Author[]> = this.authorService.getAuthors();
  authors: Author[] | undefined;

  refreshEvent = this.authorService.refreshEvent;

  emptyAuthor: Author = {
    id: -1,
    name: '',
    lastName: '',
    surname: ''
  };

  author: Author = {...this.emptyAuthor}

  selectedAuthors: Author[] = [];

  submitted: boolean = false;

  cols: any[] = [];

  statuses: any[] = [];

  rowsPerPageOptions = [5, 10, 20];

  constructor(
    private authorService: AuthorService,
    private messageService: MessageService
  ) {
  }

  ngOnInit() {
    this.isLoading = true;

    this.cols = [
      {field: 'id', header: 'ID'},
      {field: 'name', header: 'Name'},
      {field: 'lastName', header: 'lastName'},
    ];

    this.authors$.subscribe(x => {
      this.authors = x;
      this.isLoading = false;
    })

    this.refreshEvent.subscribe(() => this.fetchData())

    this.fields = this.cols.map(x => x.header)
  }

  fields: string[] = []

  fetchData() {

    this.authors$.subscribe(x => {
      this.authors = x;
    })

  }

  openNew() {
    this.author = {...this.emptyAuthor}
    this.submitted = false;
    this.productDialog = true;
  }

  deleteSelectedProducts() {
    this.deleteProductsDialog = true;
  }

  editProduct(author: Author) {
    this.author = {...author}
    this.productDialog = true;
  }

  deleteProduct(author: Author) {
    this.deleteProductDialog = true;
    this.author = {...author}
  }

  confirmDeleteSelected() {
    this.deleteProductsDialog = false;
    this.authors = this.authors?.filter((val) => !this.selectedAuthors.includes(val));
    this.messageService.add({
      severity: 'success',
      summary: 'Successful',
      detail: 'Products Deleted',
      life: 3000,
    });
    this.authors = [];
  }

  confirmDelete() {
    this.deleteProductDialog = false;
    this.authorService.deleteAuthor(this.author.id).subscribe(
      res => {
        this.refreshEvent.next();
        this.messageService.add({severity: 'success', detail: `${this.author.name} is successfully deleted!`})
      },
      err => {
        this.messageService.add({severity: 'error', detail: 'An error occured while commiting the action!'})
      }
    )
    this.author = {...this.author}
  }

  hideDialog() {
    this.productDialog = false;
    this.submitted = false;
  }

  saveProduct() {
    console.log(this.author);
    this.submitted = true;
    this.authorService.addAuthor(this.author).subscribe(
      res => {
        this.refreshEvent.next();
        this.messageService.add({severity: 'success', detail: 'The book is successfully saved!'})
      },
      err => {
        this.messageService.add({severity: 'error', detail: JSON.stringify(err)})
      }
    )


    this.productDialog = false;

    this.author = {...this.emptyAuthor};
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
