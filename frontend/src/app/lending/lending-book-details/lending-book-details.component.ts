import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Book} from "../../Book";
import {AccordionModule} from "primeng/accordion";
import {BookCard} from "../../Book-Card";
import {JsonPipe} from "@angular/common";
import {BookLendingDetails} from "../../BookLendingDetails";
import {TagModule} from "primeng/tag";
import {ButtonModule} from "primeng/button";
import {convertOutputFile} from "@angular-devkit/build-angular/src/tools/esbuild/utils";

@Component({
  selector: 'app-lending-book-details',
  standalone: true,
  imports: [
    AccordionModule,
    JsonPipe,
    TagModule,
    ButtonModule
  ],
  templateUrl: './lending-book-details.component.html',
  styleUrl: './lending-book-details.component.css'
})
export class LendingBookDetailsComponent implements OnInit{
  ngOnInit(): void {
      console.log(this.book)
    console.log(this.prevCallback)
  }
  @Input() book: BookLendingDetails | undefined;
  @Input() prevCallback: EventEmitter<any> | undefined;
  @Output() chosenBookCopy = new EventEmitter<number>();

  chosenBook(id: number) {
    this.chosenBookCopy.emit(id)
    this.prevCallback?.emit()
  }

}
