import { BookAvailability } from './../BookAvailability';
import { Component, Input, OnInit } from '@angular/core';
import { AccordionModule } from 'primeng/accordion';
import { ButtonModule } from 'primeng/button';
import { Book } from '../Book';
import { BookService } from '../service/book.service';
import { FormsModule } from '@angular/forms';


@Component({
  selector: 'accordion',
  standalone: true,
  imports: [ButtonModule, AccordionModule, ButtonModule, FormsModule],
  templateUrl: './accordion.component.html',
  styleUrl: './accordion.component.css'
})

export class AccordionComponent implements OnInit {

  constructor(private service: BookService){

  }

  @Input() book: Book | undefined
  bookAvailability: BookAvailability[] | undefined

  loading = false;
  icon = "pi pi-heart"
  label = "Add to Wish list"
  description = "This book is currently unavailable! Please come back later or Add to wishlist!"
  isInWishList = false;
  loadingButton = false;
  isDisabled = false;
  btn_icon = "";
  btn_sev = "";
  loading_btn = false;
  btn_disabled = false;
  reservationLabel = "Reserve"

  getStatusColor(statusCode: number): string {
    if (statusCode === 0) {
      this.btn_icon = "pi pi-info-circle"
      this.btn_sev = "warning"
      return '#f97316';
    } else if (statusCode === 1) {
      this.btn_icon = "pi pi-check-circle"
      this.btn_sev = "success"
      return '#14A44D';
    } else {
      this.btn_icon = "pi pi-info-circle"
      this.btn_sev = "warning"
      return 'green';
    }
  }

  setButtonProperties(){
    if (this.isInWishList){
      this.icon = "pi pi-heart-fill"
      this.label = "Already added to Wish list"
      this.description = "This book is currently unavailable! Please come back later!"
      this.isDisabled = true
    }
  }


  onReserve(storeId: number){
    this.loading_btn = true
    this.service.reserveBook(this.book?.id!, storeId)
      .subscribe(res => {
        this.loading_btn = false;
        this.btn_disabled = true;
        console.log(res)
      }) 
  }
  

  ngOnInit(): void {
    this.service.getBookAvailability(this.book?.id!!)
      .subscribe(
        res => this.bookAvailability = res
      )

      this.service.bookExistsInWishlist(this.book?.id!!)
        .subscribe(
          res => {
            console.log(res)
            this.isInWishList = res
            this.setButtonProperties()
          }
        )

      this.service.reservationExist(this.book?.id!)
        .subscribe(res => {
          if (res){
            this.reservationLabel = "Reserved"
            this.btn_disabled = res
          }
        })

  }

  onSubmit(){
    this.loadingButton = true
    this.service.addBookToWishlist(this.book?.id!!)
      .subscribe(
        res => {
          console.log(res)
          this.loadingButton = false
        }
      )
  }

}
