import { Review } from '../Review';
import { MessageService } from 'primeng/api';
import {Component, Input} from '@angular/core';
import { ButtonModule } from 'primeng/button';
import { RatingModule, RatingRateEvent } from 'primeng/rating';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { BookService } from '../service/book.service';
import {ToastModule} from "primeng/toast";
import {Book} from "../Book";

@Component({
  selector: 'review',
  standalone: true,
  imports: [RatingModule, ButtonModule, FormsModule, InputTextareaModule, ReactiveFormsModule, ToastModule],
  // providers: [MessageService],
  templateUrl: './review.component.html',
  styleUrl: './review.component.css'
})
export class ReviewComponent {

  constructor(private service: BookService, private messageService: MessageService){

  }

  @Input() book: Book | undefined

  loading = false;

  review: Review = {
    userId: 1, //todo HARDKODIRANO
    value: 0,
    description: "",
    dateReviewed: new Date()
  };

  isDisabled = true

  onRate(e: RatingRateEvent){

    this.review = {
      ...this.review,
      value: e.value
    }

    this.isDisabled = false
  }

  onSubmit(e: Event, input: string){
    this.loading = true
    this.review = {
      ...this.review,
      description: input,
      dateReviewed: new Date()
    }
    this.addReview(this.book?.id ?? -1, this.review)
  }

  addReview(id: number, review: Review){
    this.service.addReview(id, review).subscribe(
      {
        next: (res) => this.messageService.add({
          severity: 'success',
          summary: 'Successful',
          detail: 'Review Added',
          life: 3000,
        }),
        error: (err) => this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: 'Error: Review couldn\'t be added',
          life: 3000,
        }),
        complete: () => this.loading = false
      }
    )
  }




}
