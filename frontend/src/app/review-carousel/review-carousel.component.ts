import { Component, Input, OnInit } from '@angular/core';
import { CarouselModule } from 'primeng/carousel';
import { ButtonModule } from 'primeng/button';
import { TagModule } from 'primeng/tag'
import { ReviewListingComponent } from '../review-listing/review-listing.component';
import { Review } from '../Review';
import { BookService } from '../service/book.service';
import { Book } from '../Book';
import { ReviewLoaderComponent } from '../loaders/review-loader/review-loader.component';

@Component({
  selector: 'review-carousel',
  standalone: true,
  imports: [CarouselModule, ButtonModule, TagModule, ReviewListingComponent, ReviewLoaderComponent],
  templateUrl: './review-carousel.component.html',
  styleUrl: './review-carousel.component.css'
})
export class ReviewCarouselComponent implements OnInit {

  constructor(private service: BookService){}
  
  @Input() book: Book | undefined

  reviews: Review[] | any
  loading = true
  
  ngOnInit(): void {

    this.service.getReviewsByBook(this.book?.id!).subscribe({
    
      next: res => this.reviews = res,
      error: err => console.log(err),
      complete: () => this.loading = false 
    }
    )
  }
}
