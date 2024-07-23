import { Component, Input, OnInit } from '@angular/core';
import { FieldsetModule } from 'primeng/fieldset';
import { AvatarModule } from 'primeng/avatar';
import { Review } from '../Review';
import { FormsModule } from '@angular/forms';
import { RatingModule } from 'primeng/rating';


@Component({
  selector: 'review-listing',
  standalone: true,
  imports: [FieldsetModule, AvatarModule, RatingModule, FormsModule],
  templateUrl: './review-listing.component.html',
  styleUrl: './review-listing.component.css'
})
export class ReviewListingComponent implements OnInit {
  
  
  ngOnInit(): void {
    console.log(this.review);
    this.value = this.review?.value
  }

  value: number | undefined;
  
  @Input() review: Review | undefined

  

}
