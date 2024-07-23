import {Component, Input, OnInit} from '@angular/core';
import { BookCardComponent } from '../book-card/book-card.component';
import { ActivatedRoute, Router } from '@angular/router';
import { BookService } from '../service/book.service';
import { BookCard } from '../Book-Card';
import { CarouselBookCardComponent } from '../carousel-book-card/carousel-book-card.component';
import { KeyValuePipe } from '@angular/common';
import { SearchComponent } from '../search/search.component';
import {routes} from "../app.routes";
@Component({
  selector: 'list-books',
  standalone: true,
  imports: [CarouselBookCardComponent, KeyValuePipe, SearchComponent],
  templateUrl: './list-books.component.html',
  styleUrl: './list-books.component.css'

})
export class ListBooksComponent implements OnInit {

  @Input() listType: string | undefined;
  loading = true;
  topBookByLetters: Map<String, BookCard[]> | undefined

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private service: BookService,
    ) {
    route.data.subscribe(x => {
      this.listType = x['listType']
    })
  }


  ngOnInit(): void {
    console.log(this.listType)
    this.service.getTopBooksByLetter()
    .subscribe(
      res => {
        this.topBookByLetters = res
        this.loading = false
      }
    )
  }

}
