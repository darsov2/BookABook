import { Component, Input, OnInit } from '@angular/core';
import { BookCardComponent } from '../book-card/book-card.component';
import { BookCard } from '../Book-Card';
import { CarouselModule } from 'primeng/carousel';
import { RouterLink } from '@angular/router';
@Component({
  selector: 'carousel-book-card',
  standalone: true,
  imports: [BookCardComponent, CarouselModule, RouterLink],
  templateUrl: './carousel-book-card.component.html',
  styleUrl: './carousel-book-card.component.css'
})
export class CarouselBookCardComponent implements OnInit{
  
  responsiveOptions: any[] | undefined;
  @Input() books: BookCard[] | any
  
  ngOnInit(): void {
    this.responsiveOptions = [
      {
          breakpoint: '1199px',
          numVisible: 1,
          numScroll: 1
      },
      {
          breakpoint: '991px',
          numVisible: 2,
          numScroll: 1
      },
      {
          breakpoint: '767px',
          numVisible: 1,
          numScroll: 1
      }
  ];
  }

}
