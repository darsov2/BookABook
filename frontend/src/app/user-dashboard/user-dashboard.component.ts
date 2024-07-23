import {Component, OnInit} from '@angular/core';
import {PanelModule} from "primeng/panel";
import {CardModule} from "primeng/card";
import {CarouselBookCardComponent} from "../carousel-book-card/carousel-book-card.component";
import {BookCard} from "../Book-Card";
import {BookService} from "../service/book.service";
import {NgIf} from "@angular/common";
import {RouterLink} from "@angular/router";
import {Book} from "../Book";
import {AvatarModule} from "primeng/avatar";
import {AuthorService} from "../service/author.service";
import {Author} from "../Author";
import {Category} from "../Category";
import {StoreDetails} from "../StoreDetails";
import {LibraryService} from "../service/library.service";

@Component({
  selector: 'app-user-dashboard',
  standalone: true,
  imports: [
    PanelModule,
    CardModule,
    CarouselBookCardComponent,
    NgIf,
    RouterLink,
    AvatarModule
  ],
  templateUrl: './user-dashboard.component.html',
  styleUrl: './user-dashboard.component.css'
})
export class UserDashboardComponent implements OnInit {
  ngOnInit(): void {
    this.bookService.getUserLoansAsCards().subscribe(x => this.loanBooks = x);
    this.bookService.getPopularBooks().subscribe(x => this.popularBooks = x);
    this.authorService.getPopularAuthors().subscribe(x => this.popularAuthors = x);
    this.bookService.getPopularCategories().subscribe(x => this.popularCategories = x);
    this.librariesService.getPopularStores().subscribe(x => this.popularStores = x);
  }

  constructor(private bookService: BookService,
              private authorService: AuthorService,
              private librariesService: LibraryService) {
  }

  loanBooks: BookCard[] | undefined;
  popularBooks: BookCard[] | undefined;
  popularAuthors: Author[] | undefined;
  popularCategories: Category[] | undefined;
  popularStores: StoreDetails[] | undefined;


}
